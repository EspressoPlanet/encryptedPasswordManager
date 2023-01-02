package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model;

import java.io.Serializable;
import java.util.Objects;

//DONE once AESutil is created, implement in class
public class Crypto extends Credential implements Serializable, AESUtil {
    private String mPrivateKey;

    public Crypto(String userName, String password, String privateKey) {
        super(userName, password);
        mPrivateKey = privateKey;
    }

    public String getPrivateKey() {
        return mPrivateKey;
    }

    public void setPrivateKey(String privateKey) {
        mPrivateKey = privateKey;
    }

    @Override
    public String toString() {
        return "Crypto{" +
                "PrivateKey='" + mPrivateKey + '\'' +
                ", UserName='" + mUserName + '\'' +
                ", Password='" + mPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Crypto crypto = (Crypto) o;
        return Objects.equals(mPrivateKey, crypto.mPrivateKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mPrivateKey);
    }
}
