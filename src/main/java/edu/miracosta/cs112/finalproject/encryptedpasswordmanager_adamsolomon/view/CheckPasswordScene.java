package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view;

import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.controller.Controller;
import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.MainScene.passwordList;
import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.MainScene.selectedCredential;

public class CheckPasswordScene extends Scene {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private Controller controller = Controller.getInstance();
    private ImageView checkPasswordIV = new ImageView();
    private Scene previousScene;

    //label,textfield,and button
    private Label passCheckLabel = new Label("Enter password");
    private TextField passCheckTF = new TextField();
    private Label passCheckErrLabel = new Label("Error: Invalid password");
    private Button confirm = new Button("Confirm");
    private Button cancel = new Button("Cancel");

    //can't use child getters with credential type
    private Credential loadObject = selectedCredential;
    private String loadUsername = selectedCredential.getUserName();
    private String loadPassword = selectedCredential.getPassword();

    //load child objects
   private String loadSiteName;
   private String loadKey;

   //final display label
   private String message = "";

    private Label displayDecrypted = new Label(message);

    private GridPane pane = new GridPane();


    //TODO last thing to do is save encryption keys for persistent use
    //TODO also make logo
    public CheckPasswordScene(Scene previousScene)
    {
        super(new StackPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;
        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));
        checkPasswordIV.setFitWidth(WIDTH);
        pane.add(checkPasswordIV, 0,0,3,1);

        pane.add(passCheckLabel, 0, 1);
        pane.add(passCheckTF, 1, 1);
        pane.add(passCheckErrLabel, 2, 1);
        passCheckErrLabel.setTextFill(Color.RED);
        passCheckErrLabel.setVisible(false);
        pane.add(confirm, 0, 2);
        confirm.setOnAction(e -> confirmPass());
        pane.add(cancel, 1, 2); //TODO rename to return
        cancel.setOnAction(e -> cancel());
        pane.add(displayDecrypted, 1, 5);
        displayDecrypted.setMinWidth(Region.USE_PREF_SIZE);
        //displayDecrypted.setVisible(false);
        //pane.add(new Label(message), 0, 3); //todo remove

        passwordList = controller.getPassword();

        loadData();

        this.setRoot(pane);
    }

    public boolean confirmPass()
    {
        String passField = passCheckTF.getText();
        //System.out.println(passField);
        //MasterPassword getPass = passwordList.get(0); //TODO reinstitute if going back to old method
        String lastPass = passwordList.getPassword();
        //String lastPass = getPass.getPassword(); TODO remove
        //System.out.println(lastPass);

        if (passField.equals(lastPass)) {
            decryptData();
            return true;
        }
        else
        {
           // System.out.println("Passwords do not match");
            return false;
        }
    }

    public void cancel()
    {
        ViewNavigator.loadScene("Password Manager", previousScene);
    }

    public void loadData()
    {
        if (loadObject instanceof LoginInfo)
        {
           loadSiteName = ((LoginInfo) loadObject).getSiteName();
        }
        else if (loadObject instanceof Crypto)
        {
            loadKey = ((Crypto) loadObject).getPrivateKey();
        }
    }

    public void decryptData()
    {
        try {
            MasterKey mk = controller.getMasterKey();
            String algorithm = mk.getAlgorithm();
            SecretKey key = mk.getSecretKey();
            IvParameterSpec ivParameterSpec = mk.getIvParameterSpec();
            if (loadObject instanceof LoginInfo) {

                String decryptUsername = AESUtil.decrypt(algorithm, loadUsername, key,ivParameterSpec);
                String decryptPassword = AESUtil.decrypt(algorithm, loadPassword, key, ivParameterSpec);
                String decryptSiteName = AESUtil.decrypt(algorithm, loadSiteName, key, ivParameterSpec);
                //displayDecrypted = new Label("Username: " + decryptUsername + ", Password: " + decryptPassword + ", Site Name: " + decryptSiteName);
                message = "Username: " + decryptUsername + ", Password: " + decryptPassword + ", Site Name: " + decryptSiteName;
                displayDecrypted.setText(message);
                //pane.add(new Label("Hello"), 0, 3);
                //pane.add(new Label(message), 0, 3);
                //System.out.println(message);
                //displayDecrypted.setVisible(true);
            } else if (loadObject instanceof Crypto) {
                String decryptUsername = AESUtil.decrypt(algorithm, loadUsername, key,ivParameterSpec);
                String decryptPassword = AESUtil.decrypt(algorithm, loadPassword, key, ivParameterSpec);
                String decryptKey = AESUtil.decrypt(algorithm, loadKey, key, ivParameterSpec);
                //displayDecrypted = new Label("Username: " + decryptUsername + ", Password: " + decryptPassword + ", Private Key: " + decryptKey);
                message = "Username: " + decryptUsername + ", Password: " + decryptPassword + ", Private Key: " + decryptKey;
                displayDecrypted.setText(message);
                pane.add(new Label("Hello"), 0, 3);
                //pane.add(new Label(message), 0, 3);
                //System.out.println(message);
                //displayDecrypted.setVisible(true);
            }
        } catch (Exception e)
        {
            //System.err.println("Error (CheckPasswordScene): " + e.getMessage());
        }
    }
}
