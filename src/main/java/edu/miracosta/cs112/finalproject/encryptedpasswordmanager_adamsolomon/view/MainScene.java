package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view;

import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.controller.Controller;
import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

//import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.Model.algorithm;
//import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.Model.ivParameterSpec;
import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.SetPasswordScene.newPass;

//import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.SetPasswordScene.newPass;

public class MainScene extends Scene{

    //TODO create logo
    //controller
    private static Controller controller = Controller.getInstance();

    //masterpassword exists
    //public static boolean masterPassExists = false;
    //TODO going to try putting newPass inside SetPasswordScene
    //public static MasterPassword newPass = new MasterPassword(null);
    //public static MasterPassword newPass;




    //variable declarations
    public static final int WIDTH = 900;
    public static final int HEIGHT = 500;
    private ImageView credentialIV = new ImageView();


    //combo box
    private ComboBox<String> credentialTypeCB = new ComboBox<>();

    //username/password fields
    private TextField credentialUNTF = new TextField();
    private Label credentialUNErrLabel = new Label("Username required");
    private TextField credentialPassTF = new TextField();
    private Label credentialPassErrLabel = new Label("Password required");

    //third label
    private Label thirdLabel = new Label("Site Name");
    private TextField thirdTF = new TextField();
    private Label thirdTFErrLabel = new Label("Site name is required");

    //List View
    private ListView<Credential> credentialLV = new ListView<>();
    private ObservableList<Credential> credentialsList = null;
    //public static ObservableList<MasterPassword> passwordList = null;
    private ArrayList<Object> secretsList = null;
    //public static ObservableList<MasterPassword> passwordList = FXCollections.observableArrayList();
    public static MasterPassword passwordList = controller.getPassword();

    //password controller
    //private ObservableList<MasterPassword> passwordList = null;


    //buttons
    private Button addButton = new Button("Add credential");
    private Button removeButton = new Button("Remove credential");

    public Button getSetPasswordButton() {
        return setPasswordButton;
    }

    public Button getResetPasswordButton() {
        return resetPasswordButton;
    }

    private Button setPasswordButton = new Button("Set password");
    private Button resetPasswordButton = new Button("Reset password");
    private Button decryptButton = new Button("Decrypt");

    //button error labels
    private Label setPasswordErrLabel = new Label("Error: Password already set. Click reset to reset password");
    private Label resetPasswordErrLabel = new Label("Error: No password set");

    //check box
    CheckBox box = new CheckBox("Check to encrypt");


    //method var declerations
    public static Credential selectedCredential;

    //etc
    private String[] types ={"Site","Crypto"};


    //private String passExists = passwordList.get(0).toString();
    public boolean passExistsFlag = false; //false = no password, true = there is password



    //TODO create button for reset password
    //TODO create method to ensure reset password button only works if masterPassExists = true


//TODO moved to Model to write to new binary
    //encryption
//    public static SecretKey key;
//
//    static {
//        try {
//            key = AESUtil.generateKey(128);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static IvParameterSpec ivParameterSpec = AESUtil.generateIv();
//    public static String algorithm = "AES/CBC/PKCS5Padding";


    public static LoginInfo loginObject;
    public static Crypto cryptoObject;





    public MainScene() throws NoSuchAlgorithmException {
        //setting main stage
        super(new StackPane(), WIDTH, HEIGHT);
        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));
        credentialIV.setFitWidth(WIDTH);
        pane.add(credentialIV, 0, 0, 3, 1);

        //combo box
        pane.add(new Label("Credential type: "), 0, 1);
        String[] types ={"Site","Crypto"};
        pane.add(credentialTypeCB, 1, 1);
        credentialTypeCB.getItems().addAll(types);
        credentialTypeCB.getSelectionModel().select(0);
        //TODO review whether need both lambdas
        credentialTypeCB.getSelectionModel().selectedIndexProperty().addListener((obsVal, oldVal, newVal) -> switchSelect(newVal.intValue()));
        credentialTypeCB.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> stringCBChoice(newVal));


        //username/password fields
        pane.add(new Label("Username"), 0,2);
        pane.add(credentialUNTF, 1,2);
        pane.add(new Label("Password"), 0, 3);
        pane.add(credentialPassTF, 1, 3);

        //third label fields, defualt site
        pane.add(thirdLabel,0, 4);
        pane.add(thirdTF,1,4);

        //list view
        credentialLV.setPrefWidth(WIDTH);
        pane.add(credentialLV, 0,8,3,1);
        credentialsList = controller.getAllCredentials();
        credentialLV.setItems(credentialsList);

        //password controller
        passwordList = controller.getPassword();
        //passwordList.add(newPass);



        //buttons
        //TODO add error labels for set and reset password buttons
        pane.add(addButton, 0, 5);  // adding feature is declared and used within stringCBChoice
        addButton.setOnAction(e -> {
            try {
                addLoginInfo();
            } catch (InvalidAlgorithmParameterException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchPaddingException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalBlockSizeException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            } catch (BadPaddingException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidKeyException ex) {
                throw new RuntimeException(ex);
            }
        });
        pane.add(removeButton, 1, 5);
        removeButton.setOnAction(e -> removeCredential());
        pane.add(setPasswordButton, 2, 1); //was 2,5
        setPasswordButton.setDisable(true);
        //setPasswordButton.setOnAction(e -> setPasswordLoader());
        setPasswordButton.setOnAction(e -> setCheckpass());
        pane.add(resetPasswordButton, 2, 5);
        resetPasswordButton.setOnAction(e -> resetCheckPass2());
        resetPasswordButton.setDisable(true);
        pane.add(decryptButton, 1, 6);
        decryptButton.setOnAction(e -> decryptLoader());//TODO create conditional in decryptLoader for nullpointerexception

        //check box
        //pane.add(box, 1, 6);

        //connect LV to remove button
        credentialLV.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> clickSelect(newVal));
        removeButton.setDisable(true);

        //System.out.println(newPass.getPassword());  //TODO remove when done testing
        //System.out.println("print passwordList " + passwordList); //todo remove when done testing


        //TODO remove later
//        String passExists = passwordList.get(0).toString();
        checkPassExists();
        //System.out.println("passExistsFlag: " + passExistsFlag);


        this.setRoot(pane);
    }

    public void switchSelect(int intValue)
    {
        if (intValue == 0) {
            thirdLabel.setText("Site Name");
            thirdTFErrLabel.setText("Site name is required");
        }
        else if (intValue == 1) {
            thirdLabel.setText("Private Key");
            thirdTFErrLabel.setText("Private key is required");
        }
    }
    public void stringCBChoice(String newVal) {
        //TODO remove souts
        //if (newVal.equalsIgnoreCase(types[0]))
        if ("Site".equalsIgnoreCase(newVal))        // still unable to add site
        {
            addButton.setOnAction(e -> {
                try {
                    addLoginInfo();
                } catch (InvalidAlgorithmParameterException ex) {   //TODO find neater way to write, automade
                    throw new RuntimeException(ex);
                } catch (NoSuchPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalBlockSizeException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (BadPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            });
            //
            // System.out.println("add login");
        }

        else {
            addButton.setOnAction(e -> {
                try {
                    addCrypto();
                } catch (InvalidAlgorithmParameterException ex) {   //TODO rewrite to make pretty
                    throw new RuntimeException(ex);
                } catch (NoSuchPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalBlockSizeException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (BadPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            });
            //System.out.println("add crypto");
        }
    }

    public void removeCredential() {
        if (selectedCredential != null)
            credentialsList.remove(selectedCredential);
    }

    public void clickSelect(Credential newVal)
    {
        selectedCredential = newVal;
        //System.out.println(selectedCredential);
        removeButton.setDisable(selectedCredential == null);
    }


    //create objects


    public void addCrypto() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

            //get username / password / site name from fields
        MasterKey mk = controller.getMasterKey();
        String algorithm = mk.getAlgorithm();
        SecretKey key = mk.getSecretKey();
        IvParameterSpec ivParameterSpec = mk.getIvParameterSpec();

            String userName = credentialUNTF.getText();
            userName = AESUtil.encrypt(algorithm, userName, key, ivParameterSpec);
            credentialUNErrLabel.setVisible(userName.isEmpty());
            String passWord = credentialPassTF.getText();
            passWord = AESUtil.encrypt(algorithm, passWord, key, ivParameterSpec);
            credentialPassErrLabel.setVisible(passWord.isEmpty());
            String privateKey = thirdTF.getText();
            privateKey = AESUtil.encrypt(algorithm, privateKey, key, ivParameterSpec);

            thirdTFErrLabel.setVisible(privateKey.isEmpty());


            if (credentialUNErrLabel.isVisible() || credentialPassErrLabel.isVisible() || thirdTFErrLabel.isVisible())
                return;
            //add to list
            credentialsList.add(new Crypto(userName, passWord, privateKey));
            //TODO remove sout when done, only for testing
            //System.out.println("Added crypto");

    }

    public void addLoginInfo() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //get username / password / site name from fields
        MasterKey mk = controller.getMasterKey();
        String algorithm = mk.getAlgorithm();
        SecretKey key = mk.getSecretKey();
        IvParameterSpec ivParameterSpec = mk.getIvParameterSpec();
        String userName = credentialUNTF.getText();
        userName = AESUtil.encrypt(algorithm, userName, key, ivParameterSpec);
        credentialUNErrLabel.setVisible(userName.isEmpty());
        String passWord = credentialPassTF.getText();
        passWord = AESUtil.encrypt(algorithm, passWord, key, ivParameterSpec);
        credentialPassErrLabel.setVisible(passWord.isEmpty());
        String siteName = thirdTF.getText();
        siteName = AESUtil.encrypt(algorithm, siteName, key, ivParameterSpec);
        thirdTFErrLabel.setVisible(siteName.isEmpty());


        if (credentialUNErrLabel.isVisible() || credentialPassErrLabel.isVisible() || thirdTFErrLabel.isVisible())
            return;

        //add to list
        credentialsList.add(new LoginInfo(userName, passWord, siteName));
        //TODO remove sout when finished, only for testing
        //System.out.println("Added login");
    }

    public void setPasswordLoader()
    {
        ViewNavigator.loadScene("Set password", new SetPasswordScene(this));
        checkPassExists();

    }

    public void resetPasswordLoader()
    {
        ViewNavigator.loadScene("Reset password", new ResetPasswordScene(this));
    }

    public void resetCheckPass(boolean val)
    {
        if (val) resetPasswordLoader();
    }

    public void resetCheckPass2() //todo delete previous if this works to save password
    {
       // if (newPass.getPassword() != null)
        if (passExistsFlag)
            resetPasswordLoader();
    }

    public boolean setCheckpass()
    {
        //TODO need to make method to get data from indexed item of passwordList
        try {
            if (!passExistsFlag) {  //TODO change approach, set as variable in MainScene
            //if (passExists == null){
                setPasswordLoader();

                return true;
            } else {
//                System.out.println("passwordList not null");
//                System.out.println(passwordList);
                //setPasswordLoader();
                return false;
            }
        } catch (Exception e) {
           //System.out.println("Error (MainScene)" + e.getMessage());
            return true;
        }
    }

    public void checkPassExists()   //make more readable, currently catch sets the flag
    {
        try {

            //MasterPassword getPassMain = passwordList.get(0);
            //String getPassMain = passwordList.getPassword();
            //System.out.println("getPassMain: " + getPassMain);
            //String lastPass = getPassMain.getPassword(); //todo remove unless going back to old way
            if (passwordList.getPassword() == null) {
                passExistsFlag = false;
                resetPasswordButton.setDisable(true);
                setPasswordButton.setDisable(false);
                //System.out.println("passExistsFlag = " + passExistsFlag);
            }
            else {
                passExistsFlag = true;
                resetPasswordButton.setDisable(false);
                setPasswordButton.setDisable(true);
                //System.out.println("passExistsFlag = " + passExistsFlag);
            }
        } catch (Exception e) {
            //passExistsFlag = true;
            //System.out.println(passExistsFlag);//todo remove
            //System.err.println("Error (MainScene) " + e.getMessage());//todo remove
        }
    }

    public void decryptLoader()
    {
        try {
            if (selectedCredential == null) return;
            else
            ViewNavigator.loadScene("Check password", new CheckPasswordScene(this));
        } catch (NullPointerException e) {
            System.err.println("Error (mainscene): " + e.getMessage());
        }
    }



}
