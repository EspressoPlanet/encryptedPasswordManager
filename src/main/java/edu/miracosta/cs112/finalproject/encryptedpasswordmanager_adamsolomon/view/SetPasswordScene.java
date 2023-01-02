package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view;

import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.controller.Controller;
import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.MasterPassword;
import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.Model;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.security.NoSuchAlgorithmException;

//import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.MainScene.newPass;

//import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.MainScene.newPass;

public class SetPasswordScene extends Scene {
    public static MasterPassword newPass = new MasterPassword(null);
    //public static MasterPassword newPass;



    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private Controller controller = Controller.getInstance();


    private ImageView setPasswordIV = new ImageView();

    private Label prompt = new Label("To use encrypted password manager, please set a master password");
    private Label firstPassLabel = new Label("Please enter a password");
    private TextField firstPassTF = new TextField();
    private Label firstPassErrLabel = new Label("Error: Text missing");
    private Label secondPassLabel = new Label("Confirm your password");
    private TextField secondPassTF = new TextField();
    private Label secondPassErrLabel = new Label("Error: Please text missing");
    private Label passErr = new Label("Passwords must match exactly");

    private Button confirm = new Button("Confirm");
    private Button cancel = new Button("Cancel");
    //password list
    //private ObservableList<MasterPassword> passwordList = null;
    private MasterPassword passwordList;

    private Scene previousScene;

    public SetPasswordScene(Scene previousScene)
    {
        //settings for scene
        super(new StackPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));
        setPasswordIV.setFitWidth(WIDTH);
        pane.add(setPasswordIV, 0,0,3,1);

        //set labels and fields
        pane.add(firstPassLabel, 0, 1);
        pane.add(firstPassTF,1,1);
        pane.add(firstPassErrLabel, 2, 1);
        firstPassErrLabel.setTextFill(Color.RED);
        firstPassErrLabel.setVisible(false);
        pane.add(secondPassLabel, 0, 2);
        pane.add(secondPassTF,1,2);
        pane.add(secondPassErrLabel, 2, 2);
        secondPassErrLabel.setTextFill(Color.RED);
        secondPassErrLabel.setVisible(false);
        pane.add(passErr, 2,3);
        passErr.setTextFill(Color.RED);
        passErr.setVisible(false);

        //setup controller
        //credentialsList = controller.getAllCredentials(); //TODO rework Controller to allow all objects
        passwordList = controller.getPassword();

        //set buttons
        pane.add(confirm, 0, 3);
        pane.add(cancel,1,3);

        //wire buttons to methods
        confirm.setOnAction(e -> {
            try {
                confirmPass();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
        cancel.setOnAction(e -> cancel());




        this.setRoot(pane);
    }

    public boolean confirmPass() throws NoSuchAlgorithmException {
        String firstPass = firstPassTF.getText();
        firstPassErrLabel.setVisible(firstPass.isEmpty());
        String secondPass = secondPassTF.getText();
        secondPassErrLabel.setVisible(secondPass.isEmpty());

        if (firstPassErrLabel.isVisible() || secondPassErrLabel.isVisible())
            return false;

        if (firstPass.equals(secondPass)) {
            //TODO instead of new MasterPassword, use the set feature
            //newPass = new MasterPassword(firstPass);
            //newPass.setPassword(firstPass);
            //passwordList.add(newPass);
            ((MainScene) previousScene).getSetPasswordButton().setDisable(true);
            ((MainScene) previousScene).getResetPasswordButton().setDisable(false);
            passwordList.setPassword(firstPass);
            //MainScene.masterPassExists = true;
            //System.out.println(newPass.getPassword());  //TODO remove when done testing
            Model.populateListFromBinaryFile(); //TODO why is this here?
           // System.out.println("print passwordList " + passwordList);

            loadMain();
            return true;
        }
        else {
            passErr.setVisible(true);
            return false;
        }
    }

    public void loadMain() throws NoSuchAlgorithmException {
        ViewNavigator.loadScene("Password Manager", previousScene);
    }

    //TODO recompile to return to previous scene
    public void cancel()
    {
        ViewNavigator.loadScene("Password Manager", previousScene);
    }

}
