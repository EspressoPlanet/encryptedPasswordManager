package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model;

import java.io.Serializable;
import java.util.Objects;

public class Credential implements Serializable {
    protected String mUserName;
    protected String mPassword;

    public Credential(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
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
        Credential that = (Credential) o;
        return Objects.equals(mUserName, that.mUserName) && Objects.equals(mPassword, that.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserName, mPassword);
    }

    @Override
    public String toString() {
        return "Credential[" +
                "Username='" + mUserName + '\'' +
                ", Password='" + mPassword + '\'' +
                ']';
    }
}
