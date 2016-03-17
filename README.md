# これは何

Java環境で AES256 を受け入れるかをチェックするツール。

Java 8 で動作検証済。

    $ java -version
    java version "1.8.0_65"
    Java(TM) SE Runtime Environment (build 1.8.0_65-b17)
    Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)


# ビルド方法

    $ javac CheckAesEncryption.java

# 使い方

AES256をサポートしている環境での実行例

    $ java CheckAesEncryption
    AES128 seems supported.
    AES256 seems supported.

AES256をサポートしていない環境での実効例

    $ java CheckAesEncryption
    AES128 seems supported.
    java.security.InvalidKeyException: Illegal key size or default parameters
    	at javax.crypto.Cipher.checkCryptoPerm(Cipher.java:1026)
    	at javax.crypto.Cipher.implInit(Cipher.java:801)
    	at javax.crypto.Cipher.chooseProvider(Cipher.java:864)
    	at javax.crypto.Cipher.init(Cipher.java:1249)
    	at javax.crypto.Cipher.init(Cipher.java:1186)
    	at CheckAesEncryption.check(CheckAesEncryption.java:70)
    	at CheckAesEncryption.run(CheckAesEncryption.java:61)
    	at CheckAesEncryption.main(CheckAesEncryption.java:92)
    AES256 seems NOT supported.

# 背景

Oracle JDK・JREではアメリカから他国への「強い」暗号化方式の輸出に関する規制から、
デフォルトのJDK・JREで使用できる暗号化方式が意図的に弱いものに制限されている。
本ツールに即して言えば、標準インストール直後ではAES256が使用出来ない。

ただし、日本はこの輸出規制には引っかからない。

 * http://software.fujitsu.com/jp/manual/manualfiles/M090025/B1FN5943/01Z200/B5943-b-02-00.html

この規制による暗号強度を無制限のものに直すため、
別途"Unlimited Strength Java(TM) Cryptography Extension Policy Files"
というものをOracleが提供している。

よって、暗号規制上問題がないのであればこのポリシーファイルと呼ばれるものを
所定の手続きでインストールしておくとAES256を使えるようになる。

同パッケージのREADME.txtより抜粋する。

> Due to import control restrictions of some countries, the version of
> the JCE policy files that are bundled in the Java Runtime Environment,
> or JRE(TM), 8 environment allow "strong" but limited cryptography to be
> used. This download bundle (the one including this README file)
> provides "unlimited strength" policy files which contain no
> restrictions on cryptographic strengths.


JDK 8に対応するパッケージは例えば以下にある (2016-03-17現在)

 * http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

標準ではAES256が使えないという事実は、
Javaを用いたセキュリティ製品で重要な成約になることもあるため、
事前にインストールしておくことが望ましいかもしれない。

一例として、SAMLのIdP実装の一つであるShibboleth IdPではOracle JDKを採用する場合には
このポリシーファイルの更新も行うように事前に指示がある。

 * https://wiki.shibboleth.net/confluence/display/IDP30/Installation
 * https://wiki.shibboleth.net/confluence/display/IDP30/SystemRequirements

> If using the recommended Oracle JDK,
> make sure you've installed the Java Cryptography Extension (JCE)
> Unlimited Strength Jurisdiction Policy Files
> (see http://www.oracle.com/technetwork/java/javase/downloads/index.html, towards the bottom).
> If you don't do this, your deployment will be unable to make use of cryptographic algorithms
> such as AES with 256-bit keys which may be required for interoperability with some SPs.

このプロジェクトの CheckAesEncryption.java は上記の暗号化周りの事情を踏まえた上で、
AES256 readyなJavaであるかどうかを軽く確認したい時に使う目的で作成された。


# 注意

このツールはJava環境がAES256のセキュリティ標準に照らして正しい実装をおこなっているかを
テストするツール「ではない」。
単に「AES256」と指定して暗号化・復号化し、エラーが出なければサポートしているように見えるという
応答を返すだけである。

よって本ツールは、Oracle Javaに関する上述の制約に対応して、
ポリシーファイルがインストールされているか否かを軽く確認する程度にしか役に立たず、
AES256の実装が適正かといった、実装上のより厳密な検証を行うのには当然使えない。


# 参考

本ツールはGoogle検索の結果出てきた以下のページの説明を元に作成された

 * http://pieceofnostalgy.blogspot.jp/2012/01/java-256bitaes.html
 * http://techbooster.jpn.org/andriod/application/6629/

てくぶじゃないか。

# ライセンス

参照したソフトウェアコードについては不明。
本ツールは MIT ライセンスということにしておく。
