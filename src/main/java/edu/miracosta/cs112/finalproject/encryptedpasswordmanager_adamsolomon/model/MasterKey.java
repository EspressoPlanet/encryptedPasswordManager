package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model;

import javax.crypto.spec.IvParameterSpec;
import java.io.Serializable;

public class MasterKey implements Serializable {
    private javax.crypto.SecretKey mSecretKey;
    private byte[] mIv;
    private String mAlgorithm;

    public MasterKey(javax.crypto.SecretKey secretKey, byte[] iv, String algorithm) {
        mSecretKey = secretKey;
        mIv = iv;
        mAlgorithm = algorithm;
    }

    public javax.crypto.SecretKey getSecretKey() {
        return mSecretKey;
    }

    public IvParameterSpec getIvParameterSpec() {
        return new IvParameterSpec(mIv);
    }

    public String getAlgorithm() {
        return mAlgorithm;
    }

    @Override
    public String toString() {
        return "Key{" +
                "SecretKey=" + mSecretKey +
                ", IVParameterSpec=" + getIvParameterSpec() +
                ", Algorithm='" + mAlgorithm + '\'' +
                '}';
    }
}
