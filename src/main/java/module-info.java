module edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon to javafx.fxml;
    exports edu.miracosta.cs112.finalproject.encryptedpasswordmanager_adamsolomon.view;
}