package edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view;

import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.controller.Controller;
import edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.model.Model;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class View extends Application {

    //TODO try setting setPasswordScene both here and in Controller, see which works better
    //TODO binaryFileHasData automatically creates the binary file, try creating binary elsewhere
    public void start(Stage primaryStage) throws Exception
    {
        //Done remove opening of setPasswwordScene
        ViewNavigator.setStage(primaryStage);
        //if (Model.binaryFileHasData())
        primaryStage.getIcons().add(new Image("lockIcon.png"));
        ViewNavigator.loadScene("Encrypted Password Manager", new MainScene());

        //Done uncomment out when setPasswordScene is finished
       // else
         //  ViewNavigator.loadScene("Set Password",new SetPasswordScene());
    }
    public void stop() throws Exception{
        Controller.getInstance().saveData();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
