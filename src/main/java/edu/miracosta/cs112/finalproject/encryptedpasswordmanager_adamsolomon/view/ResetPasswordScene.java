package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view;

import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.controller.Controller;
import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.Credential;
import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.MasterPassword;
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
import java.util.Objects;

import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.SetPasswordScene.newPass;

//import static edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view.MainScene.newPass;

public class ResetPasswordScene extends Scene {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private Controller controller = Controller.getInstance();

    private ImageView resetPasswordIV = new ImageView();

    //buttons
    private Button confirm = new Button("Confirm");
    private Button cancel = new Button("Cancel");

    //fields
    private Label prevPassLabel = new Label("Previous password");
    private TextField prevPassTF = new TextField();
    private Label prevPassErrLabel = new Label("Error: please enter previous password");
    private Label newPassfirstLabel = new Label("Enter new password");
    private TextField newPassFirstTF = new TextField();
    private Label newPassFirstErrLabel = new Label("Error: please enter previous password");
    private Label newPassSecondLabel = new Label("Enter new password");
    private TextField newPassSecondTF = new TextField();
    private Label newPassSecondErrLabel = new Label("Error: please enter previous password");
    private Label passwordMismatchLabel = new Label("Error: Passwords do not match");
    //password list
    //private ObservableList<MasterPassword> passwordList = null;
    private MasterPassword passwordList;



    //scene loader
    private Scene previousScene;

    public ResetPasswordScene(Scene previousScene)
    {
        super(new StackPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;
        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));
        resetPasswordIV.setFitWidth(WIDTH);
        pane.add(resetPasswordIV, 0,0,3,1);

        //add fields and labels
        pane.add(prevPassLabel, 0, 1);
        pane.add(prevPassTF, 1, 1);
        pane.add(prevPassErrLabel, 2, 1);
        prevPassErrLabel.setTextFill(Color.RED);
        prevPassErrLabel.setVisible(false);

        pane.add(newPassfirstLabel, 0, 2);
        pane.add(newPassFirstTF, 1, 2);
        pane.add(newPassFirstErrLabel, 2, 2);
        newPassFirstErrLabel.setTextFill(Color.RED);
        newPassFirstErrLabel.setVisible(false);

        pane.add(newPassSecondLabel, 0, 3);
        pane.add(newPassSecondTF, 1, 3);
        pane.add(newPassSecondErrLabel, 2, 3);
        newPassSecondErrLabel.setTextFill(Color.RED);
        newPassSecondErrLabel.setVisible(false);

        pane.add(passwordMismatchLabel, 0, 5);
        passwordMismatchLabel.setTextFill(Color.RED);
        passwordMismatchLabel.setVisible(false);

        //setup controller
        passwordList = controller.getPassword();
       // System.out.println("print passwordList " + passwordList); //TODO remove


        pane.add(confirm, 0, 4);
        confirm.setOnAction(e -> {
            try {
                confirmPass();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
        pane.add(cancel, 1, 4);
        cancel.setOnAction(e -> cancel());

        //System.out.println(newPass.getPassword()); //TODO remove when done testing
        this.setRoot(pane);

    }

    public boolean confirmPass() throws NoSuchAlgorithmException {
        String prevPass = prevPassTF.getText();
        prevPassErrLabel.setVisible(prevPass.isEmpty());
        String newPassFirst = newPassFirstTF.getText();
        newPassFirstErrLabel.setVisible(newPassFirst.isEmpty());
        String newPassSecond = newPassSecondTF.getText();
        newPassSecondErrLabel.setVisible(newPassSecond.isEmpty());

        //String getPass = newPass.getPassword(); //does not work
//        MasterPassword getPass2 = passwordList.get(0);
//        String lastPass = getPass2.getPassword();
        String getPass = passwordList.getPassword();

        if (prevPassErrLabel.isVisible() || newPassFirstErrLabel.isVisible() || newPassSecondErrLabel.isVisible())
            return false;
        //TODO changing (getPass, prevPass) to (lastPass, prevPass)
        if (!Objects.equals(getPass, prevPass)) {
            prevPassErrLabel.setVisible(true);
            return false;
        }

        if (newPassFirst.equals(newPassSecond))
        {
            //newPass.setPassword(newPassFirst);
            //MasterPassword updatedPass = new MasterPassword(newPassSecond);
            //passwordList.set(0, updatedPass);
            passwordList.setPassword(newPassFirst);
            loadMain();
            return true;
        }
        else {
            passwordMismatchLabel.setVisible(true);
            return false;
        }

    }

    public void loadMain() throws NoSuchAlgorithmException {
        ViewNavigator.loadScene("Password Manager", previousScene);
    }

    public void cancel()
    {
        ViewNavigator.loadScene("Password Manager", previousScene);
    }

}
