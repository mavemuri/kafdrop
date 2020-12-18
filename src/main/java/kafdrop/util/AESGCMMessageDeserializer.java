
package kafdrop.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.Cipher;
import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.InvalidKeyException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import org.apache.commons.codec.binary.Hex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Security;

import kafdrop.config.AESGCMConfiguration;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

import org.springframework.beans.factory.annotation.Value;

public class AESGCMMessageDeserializer implements MessageDeserializer {

    private static final Logger LOG = LoggerFactory.getLogger(AESGCMMessageDeserializer.class);

    private String keyFilePath="/certs/msgkey";

    private final String aesKeyFilePath;

    private final byte[] msgKey;

    public AESGCMMessageDeserializer(String aesKeyFilePath) {
        this.aesKeyFilePath = aesKeyFilePath;
        this.msgKey = this.getKey();
    }

    @Override
    public String deserializeMessage(ByteBuffer buffer) {
        try {
            byte[] encryptedBytes = new byte[buffer.remaining()];
            buffer.get(encryptedBytes, 0, encryptedBytes.length);

            SecureRandom secureRandom= new SecureRandom();
            byte[] iv = new byte[12];
            System.arraycopy(encryptedBytes, 0, iv, 0, 12);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec secretKeySpec = new SecretKeySpec(msgKey, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
            return new String(cipher.doFinal(encryptedBytes, 12, encryptedBytes.length - 12));

        }
        catch (Exception e) {
            LOG.error("Decryption error", e);
        }
        return new String("");
    }

    private byte[] getKey() {
        try {
            LOG.info("Reading AES Key stored at", aesKeyFilePath);
            String key = Files.readString(Path.of(aesKeyFilePath));
            return hashedKey(key);
        }
        catch (Exception f) {
            LOG.error("Key file not found", f);
            return new byte[0];
        }
    }

    private byte[] hashedKey(String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(key.getBytes());
            return Hex.encodeHexString(md5.digest()).getBytes();
        }
        catch(Exception e) {
            LOG.error("Unable to generate MD5", e);
            return new byte[0];
        }
    }
}
