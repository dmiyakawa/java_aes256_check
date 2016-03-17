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

Oracle JDK・JREでは「一部の国」の暗号輸出に関する規制から、
デフォルトのJDK・JREの暗号強度が下げられている。
典型的には、標準インストール直後ではAES256がデフォルトで使用出来ない。

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

これはJavaを用いたセキュリティ製品でたまに重要な成約になる。
例えばSAMLのIdP実装の一つであるShibboleth IdPもその一つ

 * https://wiki.shibboleth.net/confluence/display/IDP30/Installation
 * https://wiki.shibboleth.net/confluence/display/IDP30/SystemRequirements

> If using the recommended Oracle JDK,
> make sure you've installed the Java Cryptography Extension (JCE)
> Unlimited Strength Jurisdiction Policy Files
> (see http://www.oracle.com/technetwork/java/javase/downloads/index.html, towards the bottom).
> If you don't do this, your deployment will be unable to make use of cryptographic algorithms
> such as AES with 256-bit keys which may be required for interoperability with some SPs.

# 参考

本ツールはGoogle検索の結果出てきた以下のページの説明を元に作成された

 * http://pieceofnostalgy.blogspot.jp/2012/01/java-256bitaes.html
 * http://techbooster.jpn.org/andriod/application/6629/

なんだよてくぶかよ。

# ライセンス

参照したソフトウェアコードについては不明。
本ツールは MIT ライセンスということにしておく。
