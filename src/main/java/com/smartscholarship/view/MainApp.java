package com.smartscholarship.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        ProfileView profileView = new ProfileView();

        Scene scene = new Scene(
                profileView.getView(),
                1100,
                800
        );

        stage.setTitle("SmartScholar");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(700);
        stage.show();
    }
}