package com.smartscholarship.view;

import com.smartscholarship.controller.ProfileController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ProfileView {

    private final BorderPane root;
    private final ProfileController controller;
    private final MainApp mainApp;

    private final TextField nameField;
    private final Spinner<Integer> ageSpinner;
    private final TextField gmailField;
    private final TextField mobileField;
    private final TextField schoolField;
    private final TextField fieldOfStudyField;
    private final TextField gpaField;
    private final TextField incomeField;

    private final CheckBox privacyCheckBox;
    private final CheckBox notificationCheckBox;

    private static final Color PURPLE =
            Color.web("#6237BE");

    private static final Color TEXT_PURPLE =
            Color.web("#4A2A91");

    public ProfileView(MainApp mainApp) {

        this.mainApp = mainApp;
        this.controller = new ProfileController();
        this.root = new BorderPane();

        nameField = createTextField("Type...");
        ageSpinner = new Spinner<>(16, 100, 18);
        gmailField = createTextField("Type...");
        mobileField = createTextField("Type...");
        schoolField = createTextField("Type...");
        fieldOfStudyField = createTextField("Type...");
        gpaField = createTextField("Type...");
        incomeField = createTextField("Type...");

        privacyCheckBox = new CheckBox(
                "I agree to the Privacy Policy and the processing of my personal information."
        );

        notificationCheckBox = new CheckBox(
                "I agree to receive notifications about application status updates."
        );

        buildUI();
    }

    private void buildUI() {

        root.setStyle(
                "-fx-background-color: #FFFFFF;"
        );

        root.setTop(
                createNavigationBar()
        );

        ScrollPane scrollPane =
                new ScrollPane(
                        createProfileContent()
                );

        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background: #FFFFFF;" +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);
    }

    private HBox createNavigationBar() {

        HBox navBar = new HBox();

        navBar.setAlignment(Pos.CENTER_LEFT);

        navBar.setPadding(
                new Insets(0, 28, 0, 28)
        );

        navBar.setSpacing(20);
        navBar.setPrefHeight(82);

        navBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #673AB7, #32156F);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.22), 5, 0, 0, 2);"
        );

        Button menuButton =
                createIconButton(
                        createMenuIcon()
                );

        Label brandLabel =
                new Label("SmartScholar");

        brandLabel.setTextFill(Color.WHITE);

        brandLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        25
                )
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button homeButton =
                createIconButton(
                        createHomeIcon()
                );

        Button applicationButton =
                createIconButton(
                        createClipboardIcon()
                );

        Button notificationButton =
                createIconButton(
                        createBellIcon()
                );

        Button profileButton =
                createActiveProfileButton();

        homeButton.setOnAction(event ->
                mainApp.showScholarshipExplore()
        );

        applicationButton.setOnAction(event ->
                mainApp.showApplications()
        );

        notificationButton.setOnAction(event ->
                System.out.println(
                        "Notifications screen coming later"
                )
        );

        profileButton.setOnAction(event ->
                System.out.println(
                        "Already on Profile screen"
                )
        );

        navBar.getChildren().addAll(
                menuButton,
                brandLabel,
                spacer,
                homeButton,
                applicationButton,
                notificationButton,
                profileButton
        );

        return navBar;
    }

    private Button createIconButton(Node icon) {

        Button button = new Button();

        button.setGraphic(icon);

        button.setMinSize(42, 42);
        button.setPrefSize(42, 42);
        button.setMaxSize(42, 42);

        button.setAlignment(Pos.CENTER);

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        return button;
    }

    private Node createMenuIcon() {

        VBox lines = new VBox(5);
        lines.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {

            Rectangle line =
                    new Rectangle(24, 3);

            line.setArcWidth(2);
            line.setArcHeight(2);
            line.setFill(Color.WHITE);

            lines.getChildren().add(line);
        }

        return lines;
    }


    private Node createHomeIcon() {

        Group group = new Group();

        Polyline roof = new Polyline(
                2.0, 11.0,
                11.0, 2.0,
                20.0, 11.0
        );

        roof.setFill(Color.TRANSPARENT);
        roof.setStroke(Color.WHITE);
        roof.setStrokeWidth(1.8);

        Path house = new Path(
                new MoveTo(5, 10),
                new LineTo(5, 21),
                new LineTo(17, 21),
                new LineTo(17, 10)
        );

        house.setFill(Color.TRANSPARENT);
        house.setStroke(Color.WHITE);
        house.setStrokeWidth(1.8);

        Rectangle door =
                new Rectangle(9, 15, 4, 6);

        door.setFill(Color.TRANSPARENT);
        door.setStroke(Color.WHITE);
        door.setStrokeWidth(1.5);

        group.getChildren().addAll(
                roof,
                house,
                door
        );

        return group;
    }

    private Node createClipboardIcon() {

        Group group = new Group();

        Rectangle board =
                new Rectangle(3, 5, 16, 18);

        board.setArcWidth(3);
        board.setArcHeight(3);
        board.setFill(Color.TRANSPARENT);
        board.setStroke(Color.WHITE);
        board.setStrokeWidth(1.8);

        Rectangle clip =
                new Rectangle(7, 2, 8, 5);

        clip.setArcWidth(3);
        clip.setArcHeight(3);
        clip.setFill(Color.TRANSPARENT);
        clip.setStroke(Color.WHITE);
        clip.setStrokeWidth(1.6);

        group.getChildren().addAll(
                board,
                clip
        );

        return group;
    }


    private Node createBellIcon() {

        Group group = new Group();

        Path bell = new Path(
                new MoveTo(4, 17),

                new CubicCurveTo(
                        6, 15,
                        6, 13,
                        6, 9
                ),

                new CubicCurveTo(
                        6, 4,
                        9, 2,
                        12, 2
                ),

                new CubicCurveTo(
                        15, 2,
                        18, 4,
                        18, 9
                ),

                new CubicCurveTo(
                        18, 13,
                        18, 15,
                        20, 17
                ),

                new ClosePath()
        );

        bell.setFill(Color.TRANSPARENT);
        bell.setStroke(Color.WHITE);
        bell.setStrokeWidth(1.8);

        Line bottom =
                new Line(4, 17, 20, 17);

        bottom.setStroke(Color.WHITE);
        bottom.setStrokeWidth(1.8);

        group.getChildren().addAll(
                bell,
                bottom
        );

        return group;
    }


    private Button createActiveProfileButton() {

        Button button = new Button();

        StackPane circle = new StackPane();

        circle.setMinSize(42, 42);
        circle.setPrefSize(42, 42);
        circle.setMaxSize(42, 42);

        circle.setStyle(
                "-fx-background-color: #E6DDF7;" +
                        "-fx-background-radius: 50;"
        );

        circle.getChildren().add(
                createPersonIcon(
                        Color.web("#32156F"),
                        22
                )
        );

        button.setGraphic(circle);

        button.setMinSize(46, 46);
        button.setPrefSize(46, 46);
        button.setMaxSize(46, 46);

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        return button;
    }


    private VBox createProfileContent() {

        VBox page = new VBox(25);

        page.setPadding(
                new Insets(45, 70, 60, 70)
        );

        Label title = new Label("Profile");

        title.setTextFill(TEXT_PURPLE);

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        34
                )
        );

        HBox mainContent = new HBox(55);
        mainContent.setAlignment(Pos.TOP_LEFT);

        VBox avatarSection =
                createAvatarSection();

        GridPane formGrid =
                createFormGrid();

        HBox.setHgrow(
                formGrid,
                Priority.ALWAYS
        );

        mainContent.getChildren().addAll(
                avatarSection,
                formGrid
        );

        page.getChildren().addAll(
                title,
                mainContent
        );

        return page;
    }

    private VBox createAvatarSection() {

        VBox avatarBox = new VBox();

        avatarBox.setAlignment(
                Pos.TOP_CENTER
        );

        avatarBox.setPrefWidth(190);

        StackPane avatarStack =
                new StackPane();

        avatarStack.setPrefSize(170, 170);
        avatarStack.setMinSize(170, 170);
        avatarStack.setMaxSize(170, 170);

        Circle avatarCircle =
                new Circle(76);

        avatarCircle.setFill(
                Color.web("#D9D9D9")
        );

        avatarCircle.setStroke(
                Color.web("#BDBDBD")
        );

        avatarCircle.setStrokeWidth(2);

        Node largePersonIcon =
                createPersonIcon(
                        Color.web("#777777"),
                        58
                );

        Pane overlayPane = new Pane();
        overlayPane.setPickOnBounds(false);

        StackPane editBadge =
                createEditBadge();


        editBadge.setLayoutX(126);
        editBadge.setLayoutY(111);

        overlayPane.getChildren().add(
                editBadge
        );

        avatarStack.getChildren().addAll(
                avatarCircle,
                largePersonIcon,
                overlayPane
        );

        avatarBox.getChildren().add(
                avatarStack
        );

        return avatarBox;
    }

    private StackPane createEditBadge() {

        StackPane badge = new StackPane();

        badge.setMinSize(42, 42);
        badge.setPrefSize(42, 42);
        badge.setMaxSize(42, 42);

        Circle background =
                new Circle(21);

        background.setFill(PURPLE);

        Node pencil =
                createPencilIcon();

        badge.getChildren().addAll(
                background,
                pencil
        );

        badge.setStyle(
                "-fx-cursor: hand;"
        );

        return badge;
    }

    private Node createPencilIcon() {

        Group group = new Group();

        Rectangle body =
                new Rectangle(4, 9, 16, 4);

        body.setArcWidth(2);
        body.setArcHeight(2);
        body.setFill(Color.TRANSPARENT);
        body.setStroke(Color.WHITE);
        body.setStrokeWidth(1.5);

        Polygon tip = new Polygon(
                20.0, 9.0,
                24.0, 11.0,
                20.0, 13.0
        );

        tip.setFill(Color.TRANSPARENT);
        tip.setStroke(Color.WHITE);
        tip.setStrokeWidth(1.3);

        group.getChildren().addAll(
                body,
                tip
        );

        group.setRotate(-42);

        return group;
    }


    private Node createPersonIcon(
            Color color,
            double size
    ) {

        Group group = new Group();

        Circle head =
                new Circle(12, 7, 4);

        head.setFill(Color.TRANSPARENT);
        head.setStroke(color);
        head.setStrokeWidth(2.2);

        Path shoulders = new Path(
                new MoveTo(4, 22),

                new CubicCurveTo(
                        4, 15,
                        8, 13,
                        12, 13
                ),

                new CubicCurveTo(
                        16, 13,
                        20, 15,
                        20, 22
                ),

                new ClosePath()
        );

        shoulders.setFill(Color.TRANSPARENT);
        shoulders.setStroke(color);
        shoulders.setStrokeWidth(2.2);

        group.getChildren().addAll(
                head,
                shoulders
        );

        double scale = size / 24.0;

        group.setScaleX(scale);
        group.setScaleY(scale);

        return group;
    }


    private GridPane createFormGrid() {

        GridPane grid = new GridPane();

        grid.setHgap(45);
        grid.setVgap(20);

        ColumnConstraints leftColumn =
                new ColumnConstraints();

        leftColumn.setPrefWidth(290);
        leftColumn.setHgrow(Priority.ALWAYS);

        ColumnConstraints rightColumn =
                new ColumnConstraints();

        rightColumn.setPrefWidth(290);
        rightColumn.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(
                leftColumn,
                rightColumn
        );

        grid.add(
                createFieldBox(
                        "Name",
                        nameField
                ),
                0,
                0
        );

        grid.add(
                createFieldBox(
                        "Age",
                        ageSpinner
                ),
                1,
                0
        );

        grid.add(
                createFieldBox(
                        "Gmail",
                        gmailField
                ),
                0,
                1
        );

        grid.add(
                createFieldBox(
                        "Mobile Number",
                        mobileField
                ),
                1,
                1
        );

        grid.add(
                createFieldBox(
                        "School",
                        schoolField
                ),
                0,
                2
        );

        grid.add(
                createFieldBox(
                        "Field of Study",
                        fieldOfStudyField
                ),
                1,
                2
        );

        grid.add(
                createFieldBox(
                        "GPA",
                        gpaField
                ),
                0,
                3
        );

        grid.add(
                createFieldBox(
                        "Household Income (USD/year)",
                        incomeField
                ),
                1,
                3
        );

        VBox consentBox =
                createConsentSection();

        grid.add(
                consentBox,
                0,
                4,
                2,
                1
        );

        return grid;
    }


    private VBox createConsentSection() {

        VBox consentBox =
                new VBox(8);

        consentBox.setPadding(
                new Insets(20, 0, 0, 0)
        );

        privacyCheckBox.setStyle(
                "-fx-text-fill: #777777;" +
                        "-fx-font-size: 12px;"
        );

        notificationCheckBox.setStyle(
                "-fx-text-fill: #777777;" +
                        "-fx-font-size: 12px;"
        );

        Button submitButton =
                new Button("Submit");

        submitButton.setStyle(
                "-fx-background-color: #6237BE;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 25;" +
                        "-fx-padding: 12 30 12 30;" +
                        "-fx-cursor: hand;"
        );


        submitButton.setOnAction(event -> {

            controller.handleSubmit(
                    nameField,
                    ageSpinner,
                    gmailField,
                    mobileField,
                    schoolField,
                    fieldOfStudyField,
                    gpaField,
                    incomeField,
                    privacyCheckBox,
                    notificationCheckBox
            );

            mainApp.showScholarshipExplore();
        });

        VBox.setMargin(
                submitButton,
                new Insets(18, 0, 0, 0)
        );

        consentBox.getChildren().addAll(
                privacyCheckBox,
                notificationCheckBox,
                submitButton
        );

        return consentBox;
    }

    private VBox createFieldBox(
            String labelText,
            Node input
    ) {

        VBox box = new VBox(8);

        Label label =
                new Label(labelText);

        label.setTextFill(
                Color.web("#5B5277")
        );

        label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        17
                )
        );

        if (input instanceof TextField textField) {
            textField.setMaxWidth(
                    Double.MAX_VALUE
            );
        }

        if (input instanceof Spinner<?> spinner) {
            spinner.setPrefWidth(100);
            spinner.setEditable(false);
        }

        box.getChildren().addAll(
                label,
                input
        );

        return box;
    }

    private TextField createTextField(
            String promptText
    ) {

        TextField field =
                new TextField();

        field.setPromptText(promptText);
        field.setPrefHeight(42);

        field.setStyle(
                "-fx-background-color: #F5F5F7;" +
                        "-fx-border-color: #A9A3B7;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 0 14 0 14;" +
                        "-fx-font-size: 13px;"
        );

        return field;
    }

    public BorderPane getView() {
        return root;
    }
}