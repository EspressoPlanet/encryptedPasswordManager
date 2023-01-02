package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class Model {
    //TODO remove all csv files when found issue
    //TODO write MasterPassword to an entirely differnet file
    //public static final String CSVFILE = "adamcsv.csv";

    public static final String BINARY_FILE = "Credentials.dat";
    public static final String PASSWORD_FILE = "MasterPassword.dat";
    public static final String MASTER_KEY_FILE = "MasterKey.dat";

//    public static SecretKey key;
//    //TODO help from professor Paulding: how to make key, ivParameterSpec, and algorithm persistent all in one file
//    static {
//        try {
//            key = AESUtil.generateKey(128);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }




    public static boolean binaryFileHasData()
    {
        File binaryFile = new File(BINARY_FILE);
        //File passwordFile = new File(PASSWORD_FILE);
        return binaryFile.exists() && binaryFile.length() > 5;
    }

//    public static ObservableList<MasterPassword> populateListFromPasswordFile()
//    {
//        ObservableList<MasterPassword> allPasswords = FXCollections.observableArrayList();
//        try {
//            ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(PASSWORD_FILE));
//            MasterPassword[] tempArray =(MasterPassword[]) fileReader.readObject();
//            for (int i = 0; i < tempArray.length; i++) {
//                allPasswords.add(tempArray[i]);
//            }
//            fileReader.close();
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Error: (model.popfrombinary)" + e.getMessage());//todo remove (model)
//        }
//        return allPasswords;
//    }
    public static MasterPassword populateListFromPasswordFile()
    {
        MasterPassword masterPassword = null;
        try {
            ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(PASSWORD_FILE));
            masterPassword = (MasterPassword) fileReader.readObject();
            fileReader.close();
        } catch (IOException | ClassNotFoundException e)
        {
           // System.err.println("Error: (Model.popfrommasterPasssword) " + e.getMessage());
        }
        return masterPassword;
    }


    public static MasterKey populateListFromSecretsFile()
    {
       MasterKey masterKey = null;
        try {
            ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(MASTER_KEY_FILE));
            masterKey = (MasterKey) fileReader.readObject();

            fileReader.close();

        } catch (IOException | ClassNotFoundException e) {
            //System.err.println("Error: (Model.popfrommasterkey) " + e.getMessage());
        }
        return masterKey;
    }

    public static ObservableList<Credential> populateListFromBinaryFile()
    {
        ObservableList<Credential> allCredentials = FXCollections.observableArrayList();
        try {
            ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(BINARY_FILE));
            Credential[] tempArray =(Credential[]) fileReader.readObject();
            for (int i = 0; i < tempArray.length; i++) {
                allCredentials.add(tempArray[i]);
            }
            fileReader.close();
        } catch (IOException | ClassNotFoundException e) {
            //System.err.println("Error: (model.popfrombinary)" + e.getMessage());//todo remove (model)
        }
        return allCredentials;

    }
//Done remove this and all references to CSV
//    public static ObservableList<Credential> populateFromCSVFile(){
//
//        ObservableList<Credential> credentials = FXCollections.observableArrayList();
//
//        String username;
//        String password;
//        String key;
//        String line;
//        String [] parts;
//
//        try {
//            Scanner fileScanner = new Scanner(new File(CSVFILE));
//            fileScanner.nextLine(); // skip 1st line
//            while(fileScanner.hasNextLine()){
//                line = fileScanner.nextLine();
//                parts = line.split(",");
//                username = parts[1];
//                password = parts[2];
//                key = parts[3];
//                credentials.add(new Crypto(username, password, key));
//            }
//            fileScanner.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//        return credentials;
//    }
//changing ObservableList<Credential> to ObservableList<Object>
    public static boolean writeDataToBinaryFile(ObservableList<Credential> allCredentialsList)
    {
        //TODO made changes to incorporate writePasswordToBinaryFile into this one, no success
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(BINARY_FILE));
            Credential[] tempArray = new Credential[allCredentialsList.size()];
            allCredentialsList.toArray(tempArray);
            fileWriter.writeObject(tempArray);
            //MasterPassword[] tempArray2 = new MasterPassword[password.size()];
           // password.toArray(tempArray2);
           // fileWriter.writeObject(tempArray2);
            fileWriter.close();
            return true;

        } catch (IOException e) {
           // System.err.println("Error " + e.getMessage());
            return true;
        }
    }

//    public static boolean writePasswordToBinaryFile(ObservableList<MasterPassword> password)
//    {
//        try{    //test, changing temp array name: update, no change
//            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(PASSWORD_FILE));
//            MasterPassword[] tempArray2 = new MasterPassword[password.size()];
//            password.toArray(tempArray2);
//            fileWriter.writeObject(tempArray2);
//            fileWriter.close();
//            return true;
//        } catch (IOException e) {
//            System.err.println("Error (Model.writePass)" + e.getMessage());//todo take out (model) when done
//            return true;
//        }
//    }

    public static boolean writePasswordToBinaryFile(MasterPassword masterPassword) {
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(PASSWORD_FILE));
            fileWriter.writeObject(masterPassword);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            //System.err.println("Error (Model.writePassToBinary) " + e.getMessage());
            return true;
        }
    }

    public static boolean writeMasterKeyToBinaryFile(MasterKey masterKey)
    {
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(MASTER_KEY_FILE));
            fileWriter.writeObject(masterKey);
            fileWriter.close();
            return true;
        } catch (IOException e) {
           // System.err.println("Error (Model.writeMasterkey) " + e.getMessage());
            return true;
        }
    }


    public static boolean masterKeyFileHasData() {
        File masterKeyFile = new File(MASTER_KEY_FILE);
        return masterKeyFile.exists() && masterKeyFile.length() > 5;
    }

    public static boolean passwordFileHasData() {
        File passwordFile = new File(PASSWORD_FILE);
        return passwordFile.exists() && passwordFile.length() > 5;
    }
}
