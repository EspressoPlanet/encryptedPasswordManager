package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.controller;

import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.*;
import javafx.collections.ObservableList;

import javax.crypto.SecretKey;
import java.io.File;
import java.security.NoSuchAlgorithmException;

import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.Model.BINARY_FILE;

public class Controller {
    private static Controller theInstance;

    private ObservableList<Credential> mAllCredentialsList;
    //private ObservableList<Object> mAllCredentialsList;
    //private ObservableList<MasterPassword> mPasswordList;
    private MasterPassword mMasterPassword;
   // private ObservableList<String> mSecretsList;

    private  MasterKey mMasterKey;

    private Controller() {}

    //TODO
    //Done remove use of CSV file
    public static Controller getInstance() {
        if (theInstance == null)
        //TODO completely rewrite if/else
        {
            theInstance = new Controller();
            if (Model.masterKeyFileHasData())
            {
                theInstance.mMasterKey = Model.populateListFromSecretsFile();
            }
            else {
              SecretKey key = null;
                    try {
                        key = AESUtil.generateKey(128);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                 theInstance.mMasterKey = new MasterKey(key, AESUtil.generateIv(), "AES/CBC/PKCS5Padding");
            }
            if (Model.passwordFileHasData())
            {
                theInstance.mMasterPassword = Model.populateListFromPasswordFile();
            }
            else {
                theInstance.mMasterPassword = new MasterPassword(null);
            }

           if (Model.binaryFileHasData()) {
               theInstance.mAllCredentialsList = Model.populateListFromBinaryFile();
               //theInstance.mPasswordList = Model.populateListFromPasswordFile();
           }
          else
               {
                   //File binaryFile = new File(BINARY_FILE);
                   theInstance.mAllCredentialsList = Model.populateListFromBinaryFile();
              // theInstance.mAllCredentialsList = Model.populateListFromBinaryFile();
            //theInstance.mPasswordList = Model.populateListFromPasswordFile();
        }


        }
        return theInstance;
    }

    public ObservableList<Credential> getAllCredentials() {
        return mAllCredentialsList;
    }

    public void saveData() {
        Model.writeDataToBinaryFile(mAllCredentialsList);
        Model.writePasswordToBinaryFile(mMasterPassword);
        Model.writeMasterKeyToBinaryFile(mMasterKey);
    }

    public MasterPassword getPassword()
    {
        return mMasterPassword;
    }

    public void setMasterPassword(MasterPassword masterPassword) {
        mMasterPassword = masterPassword;
    }

    public MasterKey getMasterKey() {
        return mMasterKey;
    }
}
