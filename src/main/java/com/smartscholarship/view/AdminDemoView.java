package com.smartscholarship.view;

import com.smartscholarship.controller.AdminDemoController;
import com.smartscholarship.model.ScholarshipApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class AdminDemoView {

    private final BorderPane root;
    private final MainApp mainApp;
    private final AdminDemoController controller;

    private final TextField searchField;
    private final VBox pendingContainer;
    private final VBox historyContainer;

    private boolean showAllHistory = false;

    private static final Color DARK_PURPLE = Color.web("#32156F");
    private static final Color TEXT_PURPLE = Color.web("#4A2A91");
    private static final Color SOFT_PURPLE = Color.web("#9A78B6");

    public AdminDemoView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.controller = new AdminDemoController();
        this.root = new BorderPane();
        this.searchField = new TextField();
        this.pendingContainer = new VBox(14);
        this.historyContainer = new VBox();

        controller.setRefreshAction(this::refreshAll);

        buildUI();
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: #FFFFFF;");
        root.setTop(createNavigationBar());

        ScrollPane scrollPane = new ScrollPane(createPageContent());

        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background: #FFFFFF;" +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);

        refreshAll();
    }

    private HBox createNavigationBar() {
        HBox navBar = new HBox();

        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(0, 28, 0, 28));
        navBar.setSpacing(20);
        navBar.setPrefHeight(82);

        navBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #673AB7, #32156F);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.22), 5, 0, 0, 2);"
        );

        Button menuButton = createActiveMenuButton();

        Label brandLabel = new Label("SmartScholar");

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

        Button homeButton = createIconButton(
                createHomeIcon(Color.WHITE)
        );

        Button applicationButton = createIconButton(
                createClipboardIcon()
        );

        Button notificationButton = createIconButton(
                createBellIcon()
        );

        Button profileButton = createIconButton(
                createPersonIcon(
                        Color.WHITE,
                        22
                )
        );

        menuButton.setOnAction(event ->
                mainApp.showAdminDemo()
        );

        homeButton.setOnAction(event ->
                mainApp.showScholarshipExplore()
        );

        applicationButton.setOnAction(event ->
                mainApp.showApplications()
        );

        notificationButton.setOnAction(event ->
                mainApp.showNotifications()
        );

        profileButton.setOnAction(event ->
                mainApp.showProfile()
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

    private VBox createPageContent() {
        VBox page = new VBox(18);

        page.setPadding(
                new Insets(32, 55, 50, 55)
        );

        page.setStyle(
                "-fx-background-color: white;"
        );

        HBox titleRow = new HBox(20);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Admin Demo");

        title.setTextFill(TEXT_PURPLE);

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        30
                )
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Node searchBar = createSearchBar();

        titleRow.getChildren().addAll(
                title,
                spacer,
                searchBar
        );

        Label manageTitle = new Label(
                "Manage Application Status"
        );

        manageTitle.setTextFill(SOFT_PURPLE);

        manageTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        18
                )
        );

        Region manageDivider = createDivider();

        Label historyTitle = new Label(
                "Application History"
        );

        historyTitle.setTextFill(SOFT_PURPLE);

        historyTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        18
                )
        );

        Region historyDivider = createDivider();

        VBox historyBox = createHistoryBox();

        page.getChildren().addAll(
                titleRow,
                manageTitle,
                manageDivider,
                pendingContainer,
                createGap(10),
                historyTitle,
                historyDivider,
                historyBox
        );

        return page;
    }

    private Node createSearchBar() {
        StackPane searchContainer = new StackPane();

        searchContainer.setMinWidth(270);
        searchContainer.setPrefWidth(270);
        searchContainer.setMaxWidth(270);

        searchField.setPromptText(
                "Search application..."
        );

        searchField.setPrefHeight(38);

        searchField.setStyle(
                "-fx-background-color: #E7E2EF;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-padding: 0 45 0 18;" +
                        "-fx-font-size: 12px;"
        );

        Node searchIcon = createSearchIcon();

        StackPane.setAlignment(
                searchIcon,
                Pos.CENTER_RIGHT
        );

        StackPane.setMargin(
                searchIcon,
                new Insets(0, 16, 0, 0)
        );

        searchContainer.getChildren().addAll(
                searchField,
                searchIcon
        );

        searchField.textProperty().addListener(
                (observable, oldValue, newValue) ->
                        refreshPendingApplications()
        );

        return searchContainer;
    }

    private void refreshAll() {
        refreshPendingApplications();
        refreshHistory();
    }

    private void refreshPendingApplications() {
        pendingContainer.getChildren().clear();

        List<ScholarshipApplication> applications =
                controller.searchPendingApplications(
                        searchField.getText()
                );

        for (ScholarshipApplication application : applications) {
            pendingContainer.getChildren().add(
                    createPendingCard(application)
            );
        }

        if (applications.isEmpty()) {
            VBox emptyBox = new VBox(6);

            emptyBox.setAlignment(Pos.CENTER);

            emptyBox.setPadding(
                    new Insets(25)
            );

            emptyBox.setStyle(
                    "-fx-background-color: #FAF8FF;" +
                            "-fx-background-radius: 12;" +
                            "-fx-border-color: #D8C8F5;" +
                            "-fx-border-radius: 12;"
            );

            Label emptyTitle = new Label(
                    "No pending applications"
            );

            emptyTitle.setTextFill(TEXT_PURPLE);

            emptyTitle.setFont(
                    Font.font(
                            "Arial",
                            FontWeight.BOLD,
                            15
                    )
            );

            Label emptyText = new Label(
                    "There are no applications matching your search."
            );

            emptyText.setTextFill(
                    Color.web("#8C8299")
            );

            emptyText.setFont(
                    Font.font("Arial", 11)
            );

            emptyBox.getChildren().addAll(
                    emptyTitle,
                    emptyText
            );

            pendingContainer.getChildren().add(
                    emptyBox
            );
        }
    }

    private VBox createPendingCard(
            ScholarshipApplication application
    ) {
        VBox card = new VBox(12);

        card.setPadding(
                new Insets(15, 18, 15, 18)
        );

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #B79BEF;" +
                        "-fx-border-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.20), 5, 0, 1, 3);"
        );

        HBox userRow = new HBox(10);

        userRow.setAlignment(
                Pos.CENTER_LEFT
        );

        Circle avatar = new Circle(
                10,
                Color.web("#D8D8D8")
        );

        Label userLabel = new Label(
                application.getApplicant().getName()
        );

        userLabel.setTextFill(TEXT_PURPLE);

        userLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        16
                )
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label timeLabel = new Label(
                "Just now"
        );

        timeLabel.setPadding(
                new Insets(4, 12, 4, 12)
        );

        timeLabel.setStyle(
                "-fx-background-color: #EDEDED;" +
                        "-fx-background-radius: 14;" +
                        "-fx-font-size: 9px;" +
                        "-fx-text-fill: #999999;"
        );

        userRow.getChildren().addAll(
                avatar,
                userLabel,
                spacer,
                timeLabel
        );

        GridPane details = createApplicationDetails(
                application
        );

        HBox actions = createActionButtons(
                application
        );

        card.getChildren().addAll(
                userRow,
                details,
                actions
        );

        return card;
    }

    private GridPane createApplicationDetails(
            ScholarshipApplication application
    ) {
        GridPane details = new GridPane();

        details.setHgap(28);
        details.setVgap(9);

        ColumnConstraints labelColumn =
                new ColumnConstraints();

        labelColumn.setMinWidth(120);
        labelColumn.setPrefWidth(120);

        ColumnConstraints valueColumn =
                new ColumnConstraints();

        valueColumn.setHgrow(Priority.ALWAYS);

        details.getColumnConstraints().addAll(
                labelColumn,
                valueColumn
        );

        details.add(
                createDetailLabel("Scholarship"),
                0,
                0
        );

        details.add(
                createDetailValue(
                        application.getScholarship().getTitle()
                ),
                1,
                0
        );

        details.add(
                createDetailLabel("Application ID"),
                0,
                1
        );

        details.add(
                createDetailValue(
                        application.getApplicationID()
                ),
                1,
                1
        );

        details.add(
                createDetailLabel("Current Status"),
                0,
                2
        );
        details.add(createStatusBadge(application.getStatus().name()), 1, 2);
        return details;
    }

    private HBox createActionButtons(
            ScholarshipApplication application
    ) {
        HBox actions = new HBox(12);

        actions.setAlignment(
                Pos.CENTER_RIGHT
        );

        Button profileButton = new Button(
                "View User Profile"
        );

        profileButton.setPrefWidth(130);
        profileButton.setPrefHeight(31);

        profileButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-color: #B79BEF;" +
                        "-fx-border-radius: 16;" +
                        "-fx-text-fill: #4A2A91;" +
                        "-fx-font-size: 10px;" +
                        "-fx-cursor: hand;"
        );

        profileButton.setOnAction(event ->
                showUserProfile(application)
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button approveButton = new Button(
                "Approve"
        );

        approveButton.setPrefWidth(80);
        approveButton.setPrefHeight(32);

        approveButton.setStyle(
                "-fx-background-color: #6237BE;" +
                        "-fx-background-radius: 17;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );

        approveButton.setOnAction(event ->
                controller.approveApplication(
                        application.getApplicationID()
                )
        );

        Button declineButton = new Button(
                "Decline"
        );

        declineButton.setPrefWidth(80);
        declineButton.setPrefHeight(32);

        declineButton.setStyle(
                "-fx-background-color: #D8C9F7;" +
                        "-fx-background-radius: 17;" +
                        "-fx-text-fill: #6237BE;" +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );

        declineButton.setOnAction(event ->
                controller.declineApplication(
                        application.getApplicationID()
                )
        );

        actions.getChildren().addAll(
                profileButton,
                spacer,
                approveButton,
                declineButton
        );

        return actions;
    }

    private VBox createHistoryBox() {
        VBox outer = new VBox();

        outer.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #B79BEF;" +
                        "-fx-border-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.20), 5, 0, 1, 3);"
        );

        Button viewMoreButton = new Button(
                "View More..."
        );

        viewMoreButton.setMaxWidth(
                Double.MAX_VALUE
        );

        viewMoreButton.setPrefHeight(28);

        viewMoreButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #6237BE;" +
                        "-fx-font-size: 9px;" +
                        "-fx-cursor: hand;"
        );

        viewMoreButton.setOnAction(event -> {
            showAllHistory = !showAllHistory;

            viewMoreButton.setText(
                    showAllHistory
                            ? "View Less..."
                            : "View More..."
            );

            refreshHistory();
        });

        outer.getChildren().addAll(
                historyContainer,
                viewMoreButton
        );

        return outer;
    }

    private void refreshHistory() {
        historyContainer.getChildren().clear();

        List<ScholarshipApplication> history =
                controller.getApplicationHistory();

        int limit = showAllHistory
                ? history.size()
                : Math.min(2, history.size());

        for (int i = 0; i < limit; i++) {
            historyContainer.getChildren().add(
                    createHistoryRow(
                            history.get(i)
                    )
            );
        }
    }

    private HBox createHistoryRow(
            ScholarshipApplication application
    ) {
        HBox row = new HBox();

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        row.setPadding(
                new Insets(9, 16, 9, 16)
        );

        row.setMinHeight(54);
        row.setPrefHeight(54);

        row.setStyle(
                "-fx-border-color: transparent transparent #D5C7F6 transparent;" +
                        "-fx-border-width: 0 0 1 0;"
        );

        Circle avatar = new Circle(
                10,
                Color.web("#D8D8D8")
        );

        HBox.setMargin(
                avatar,
                new Insets(0, 14, 0, 0)
        );

        VBox userInfo = new VBox(2);

        userInfo.setAlignment(
                Pos.CENTER_LEFT
        );

        userInfo.setMinWidth(145);
        userInfo.setPrefWidth(145);
        userInfo.setMaxWidth(145);

        Label userLabel = new Label(
                application.getApplicant().getName()
        );

        userLabel.setTextFill(TEXT_PURPLE);

        userLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        13
                )
        );

        userLabel.setMinWidth(145);
        userLabel.setPrefWidth(145);
        userLabel.setMaxWidth(145);

        Label scholarshipLabel = new Label(
                application.getScholarship().getTitle()
        );

        scholarshipLabel.setTextFill(
                Color.web("#8C8299")
        );

        scholarshipLabel.setFont(
                Font.font("Arial", 8)
        );

        scholarshipLabel.setMinWidth(145);
        scholarshipLabel.setPrefWidth(145);
        scholarshipLabel.setMaxWidth(145);

        scholarshipLabel.setTextOverrun(
                OverrunStyle.ELLIPSIS
        );

        userInfo.getChildren().addAll(
                userLabel,
                scholarshipLabel
        );

        StackPane statusColumn = new StackPane();

        statusColumn.setAlignment(
                Pos.CENTER_LEFT
        );

        statusColumn.setMinWidth(90);
        statusColumn.setPrefWidth(90);
        statusColumn.setMaxWidth(90);

        Label statusBadge = createStatusBadge(
                application.getStatus().name()
        );

        statusBadge.setMinWidth(74);
        statusBadge.setPrefWidth(74);
        statusBadge.setMaxWidth(74);

        statusColumn.getChildren().add(
                statusBadge
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button reviewButton = new Button(
                "Review  ▶"
        );

        reviewButton.setMinWidth(85);
        reviewButton.setPrefWidth(85);
        reviewButton.setMaxWidth(85);

        reviewButton.setAlignment(
                Pos.CENTER_RIGHT
        );

        reviewButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #6237BE;" +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );

        reviewButton.setOnAction(event ->
                showApplicationReview(application)
        );

        row.getChildren().addAll(
                avatar,
                userInfo,
                statusColumn,
                spacer,
                reviewButton
        );

        return row;
    }

    private Label createDetailLabel(
            String text
    ) {
        Label label = new Label(text);

        label.setTextFill(TEXT_PURPLE);

        label.setFont(
                Font.font("Arial", 13)
        );

        return label;
    }

    private Label createDetailValue(
            String text
    ) {
        Label label = new Label(text);

        label.setTextFill(SOFT_PURPLE);

        label.setFont(
                Font.font("Arial", 13)
        );

        return label;
    }

    private Label createStatusBadge(
            String status
    ) {
        String backgroundColor;

        if ("Approved".equalsIgnoreCase(status)) {
            backgroundColor = "#60D8A8";
        } else if ("Rejected".equalsIgnoreCase(status)) {
            backgroundColor = "#FF6875";
        } else {
            backgroundColor = "#F9C84B";
        }

        Label badge = new Label(status);

        badge.setAlignment(
                Pos.CENTER
        );

        badge.setMinWidth(74);
        badge.setPrefWidth(74);
        badge.setMaxWidth(74);

        badge.setMinHeight(24);
        badge.setPrefHeight(24);
        badge.setMaxHeight(24);

        badge.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-font-size: 9px;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 0;"
        );

        return badge;
    }

    private void showUserProfile(
            ScholarshipApplication application
    ) {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("User Profile");

        alert.setHeaderText(
                application.getApplicant().getName()
        );

        alert.setContentText(
                "Application ID: "
                        + application.getApplicationID()
                        + "\nScholarship: "
                        + application.getScholarship().getTitle()
                        + "\nStatus: "
                        + application.getStatus()
        );

        alert.showAndWait();
    }

    private void showApplicationReview(
            ScholarshipApplication application
    ) {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(
                "Application Review"
        );

        alert.setHeaderText(
                application.getApplicationID()
        );

        alert.setContentText(
                "User: "
                        + application.getApplicant().getName()
                        + "\nScholarship: "
                        + application.getScholarship().getTitle()
                        + "\nStatus: "
                        + application.getStatus()
                        + "\nUpdated: "
                        + "Just Now"
        );

        alert.showAndWait();
    }

    private Region createDivider() {
        Region divider = new Region();

        divider.setPrefHeight(1);

        divider.setMaxWidth(
                Double.MAX_VALUE
        );

        divider.setStyle(
                "-fx-background-color: #CBB9F5;"
        );

        return divider;
    }

    private Region createGap(
            double height
    ) {
        Region gap = new Region();

        gap.setPrefHeight(height);

        return gap;
    }

    private Button createIconButton(
            Node icon
    ) {
        Button button = new Button();

        button.setGraphic(icon);

        button.setMinSize(42, 42);
        button.setPrefSize(42, 42);
        button.setMaxSize(42, 42);

        button.setAlignment(
                Pos.CENTER
        );

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        return button;
    }

    private Button createActiveMenuButton() {
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
                createMenuIcon(DARK_PURPLE)
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

    private Node createMenuIcon(
            Color color
    ) {
        VBox lines = new VBox(5);

        lines.setAlignment(
                Pos.CENTER
        );

        for (int i = 0; i < 3; i++) {
            Rectangle line = new Rectangle(
                    24,
                    3
            );

            line.setArcWidth(2);
            line.setArcHeight(2);
            line.setFill(color);

            lines.getChildren().add(line);
        }

        return lines;
    }

    private Node createHomeIcon(
            Color color
    ) {
        Group group = new Group();

        Polyline roof = new Polyline(
                2.0, 11.0,
                11.0, 2.0,
                20.0, 11.0
        );

        roof.setFill(
                Color.TRANSPARENT
        );

        roof.setStroke(color);
        roof.setStrokeWidth(1.8);

        Path house = new Path(
                new MoveTo(5, 10),
                new LineTo(5, 21),
                new LineTo(17, 21),
                new LineTo(17, 10)
        );

        house.setFill(
                Color.TRANSPARENT
        );

        house.setStroke(color);
        house.setStrokeWidth(1.8);

        Rectangle door = new Rectangle(
                9,
                15,
                4,
                6
        );

        door.setFill(
                Color.TRANSPARENT
        );

        door.setStroke(color);
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

        Rectangle board = new Rectangle(
                3,
                5,
                16,
                18
        );

        board.setArcWidth(3);
        board.setArcHeight(3);

        board.setFill(
                Color.TRANSPARENT
        );

        board.setStroke(
                Color.WHITE
        );

        board.setStrokeWidth(1.8);

        Rectangle clip = new Rectangle(
                7,
                2,
                8,
                5
        );

        clip.setArcWidth(3);
        clip.setArcHeight(3);

        clip.setFill(
                Color.TRANSPARENT
        );

        clip.setStroke(
                Color.WHITE
        );

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

        bell.setFill(
                Color.TRANSPARENT
        );

        bell.setStroke(
                Color.WHITE
        );

        bell.setStrokeWidth(1.8);

        Line bottom = new Line(
                4,
                17,
                20,
                17
        );

        bottom.setStroke(
                Color.WHITE
        );

        bottom.setStrokeWidth(1.8);

        group.getChildren().addAll(
                bell,
                bottom
        );

        return group;
    }

    private Node createPersonIcon(
            Color color,
            double size
    ) {
        Group group = new Group();

        Circle head = new Circle(
                12,
                7,
                4
        );

        head.setFill(
                Color.TRANSPARENT
        );

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

        shoulders.setFill(
                Color.TRANSPARENT
        );

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

    private Node createSearchIcon() {
        Group group = new Group();

        Circle circle = new Circle(
                8,
                8,
                5
        );

        circle.setFill(
                Color.TRANSPARENT
        );

        circle.setStroke(
                Color.web("#6F6288")
        );

        circle.setStrokeWidth(1.5);

        Line handle = new Line(
                12,
                12,
                16,
                16
        );

        handle.setStroke(
                Color.web("#6F6288")
        );

        handle.setStrokeWidth(1.5);

        group.getChildren().addAll(
                circle,
                handle
        );

        return group;
    }

    public BorderPane getView() {
        return root;
    }
}