/*
 * Daisuke Miyakawa <d.miyakawa t gmail dot com>
 *
 * A simple program that checks if your java environment
 * accepts "AES128" / "AES256" encryption or not.
 *
 * Basic usage:
 *
 * $ javac CheckAesEncryption.java
 * $ java CheckAesEncryption
 *
 * Note:
 * This program may be just incorrect.
 * Please be careful if you plan to use this.
 *
 * Specifically, this program says "AES256 is supported" when
 # the Java's methods accept a key with specified length (128 / 256)
 * during encryption and decryption. This program itself does not
 * check bytestream that is "allegedly" encrypted is really encrypted.
 * This kind of "check" does not assure anything acutally.
 * For example, your environment MAY just truncate a given key and
 * use the first 128 bytes even when "AES256" is requeted.
 *
 * Based on the codes in:
 * http://pieceofnostalgy.blogspot.jp/2012/01/java-256bitaes.html
 * http://techbooster.jpn.org/andriod/application/6629/
 *
 * License: MIT (assuming codes above have compatible licenses :-)
 *
 */

import java.io.PrintStream;
import java.util.Arrays;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CheckAesEncryption
{
    private String _rawText;
    private String _password;

    public CheckAesEncryption() {
        this("Text to be encrypted Here. 日本語もいけるよあーあー",
             "samplepassword");
    }
    
    public CheckAesEncryption(String rawText, String password) {
        this._rawText = rawText;
        this._password = password;
    }
    
    public void run() {
        String resultForAES128 =
            check(128) ? "AES128 seems supported." : "AES128 seems NOT supported.";
        System.out.println(resultForAES128);

        String resultForAES258 =
            check(256) ? "AES256 seems supported." : "AES256 seems NOT supported.";
        System.out.println(resultForAES258);
    }

    public boolean check(int strength) {
        boolean successful = false;
        try {
            Key key = createKey(_password, strength);
            Cipher encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encrypter.init(Cipher.ENCRYPT_MODE, key);
            IvParameterSpec iv = new IvParameterSpec(encrypter.getIV());
            Cipher decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decrypter.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] raw = _rawText.getBytes();
            byte[] encrypted = encrypter.doFinal(raw);
            byte[] decrypted = decrypter.doFinal(encrypted);
            successful = Arrays.equals(raw, decrypted);
        } catch (Exception ex) {
            ex.printStackTrace(new PrintStream(System.out));
        }
        return successful;
    }
    
    public Key createKey(String password, int bitNum) {
        SecureRandom random = new SecureRandom(password.getBytes());
        byte buff[] = new byte[bitNum >> 3];
        random.nextBytes(buff);
        return new SecretKeySpec(buff, "AES");
    }

    public static void main(String[] args) {
        new CheckAesEncryption().run();
    }
}
