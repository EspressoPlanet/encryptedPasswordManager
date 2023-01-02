package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model;

import java.io.Serializable;
import java.util.Objects;

public class LoginInfo extends Credential implements Serializable, AESUtil {
    private String mSiteName;

    public LoginInfo(String userName, String password, String siteName) {
        super(userName, password);
        mSiteName = siteName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LoginInfo loginInfo = (LoginInfo) o;
        return Objects.equals(mSiteName, loginInfo.mSiteName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mSiteName);
    }

    @Override
    public String toString() {
        return "Login Info[" +
                "SiteName='" + mSiteName + '\'' +
                ", UserName='" + mUserName + '\'' +
                ", Password='" + mPassword + '\'' +
                ']';
    }

    public String getSiteName() {
        return mSiteName;
    }

    public void setSiteName(String siteName) {
        mSiteName = siteName;
    }
}
