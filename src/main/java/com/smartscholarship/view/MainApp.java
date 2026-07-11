package com.smartscholarship.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) {
        ProfileView profileView = new ProfileView(this);

        scene = new Scene(
                profileView.getView(),
                1100,
                800
        );

        stage.setTitle("SmartScholar");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(650);
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
        ScholarshipExploreView scholarshipExploreView =
                new ScholarshipExploreView(this);

        scene.setRoot(
                scholarshipExploreView.getView()
        );
    }

    public void showApplications() {
        ApplicationView applicationView =
                new ApplicationView(this);

        scene.setRoot(
                applicationView.getView()
        );
    }

    public void showNotifications() {
        NotificationView notificationView =
                new NotificationView(this);

        scene.setRoot(
                notificationView.getView()
        );
    }

    public void showAdminDemo() {
        AdminDemoView adminDemoView =
                new AdminDemoView(this);

        scene.setRoot(
                adminDemoView.getView()
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}