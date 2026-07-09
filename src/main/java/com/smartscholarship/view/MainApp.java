package com.smartscholarship.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage stage;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        ProfileView profileView =
                new ProfileView(this);

        scene = new Scene(
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

    public void showProfile() {
        ProfileView profileView =
                new ProfileView(this);

        scene.setRoot(
                profileView.getView()
        );
    }

    public void showScholarshipExplore() {
        ScholarshipExploreView exploreView =
                new ScholarshipExploreView(this);

        scene.setRoot(
                exploreView.getView()
        );
    }
}