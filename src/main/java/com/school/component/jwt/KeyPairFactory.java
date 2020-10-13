//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.jwt;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import org.springframework.core.io.ClassPathResource;

public class KeyPairFactory {
    private KeyStore keyStore;

    public KeyPairFactory() {
    }

    KeyPair create(String keyPath, String keyAlias, String keyPass) {
        ClassPathResource resource = new ClassPathResource(keyPath);
        char[] password = keyPass.toCharArray();

        try {
            synchronized(this) {
                if (this.keyStore == null) {
                    synchronized(this) {
                        this.keyStore = KeyStore.getInstance("jks");
                        this.keyStore.load(resource.getInputStream(), password);
                    }
                }
            }

            RSAPrivateCrtKey privateCrtKey = (RSAPrivateCrtKey)this.keyStore.getKey(keyAlias, password);
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(privateCrtKey.getModulus(), privateCrtKey.getPublicExponent());
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(rsaPublicKeySpec);
            return new KeyPair(publicKey, privateCrtKey);
        } catch (CertificateException | IOException | NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | InvalidKeySpecException var12) {
            throw new IllegalStateException("can not load keys from store" + resource, var12);
        }
    }
}
