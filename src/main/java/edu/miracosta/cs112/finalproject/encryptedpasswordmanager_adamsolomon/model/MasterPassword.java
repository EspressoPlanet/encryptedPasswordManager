package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model;

import java.io.Serializable;
import java.util.Objects;

public class MasterPassword implements Serializable {

    private String mPassword;

    public MasterPassword(String password) {
        mPassword = password;
    }



    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterPassword that = (MasterPassword) o;
        return Objects.equals(mPassword, that.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPassword);
    }

    @Override
    public String
    toString() {
        return "MasterPassword{" +
                "mPassword='" + mPassword + '\'' +
                '}';
    }
}
