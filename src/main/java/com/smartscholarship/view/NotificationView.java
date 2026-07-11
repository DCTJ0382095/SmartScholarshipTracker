package com.smartscholarship.view;

import com.smartscholarship.controller.NotificationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

import java.util.ArrayList;
import java.util.List;

public class NotificationView {

    private final BorderPane root;
    private final MainApp mainApp;
    private final NotificationController controller;
    private final TextField searchField;
    private final VBox todayRows;
    private final VBox earlierRows;
    private final List<NotificationDemo> notifications;

    private boolean showAllEarlier = false;

    private static final String PURPLE = "#6237C7";
    private static final String DARK_PURPLE = "#351577";
    private static final String TEXT_PURPLE = "#4A2A91";
    private static final String SOFT_PURPLE = "#8D73A9";
    private static final String BORDER_PURPLE = "#B99AF2";

    public NotificationView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.controller = new NotificationController();
        this.root = new BorderPane();
        this.searchField = new TextField();
        this.todayRows = new VBox(0);
        this.earlierRows = new VBox(0);
        this.notifications = new ArrayList<>();

        createDemoNotifications();
        controller.setRefreshAction(this::refreshNotifications);
        buildUI();
        refreshNotifications();
    }

    private void createDemoNotifications() {
        notifications.add(new NotificationDemo(
                "Application Approved ✓",
                "Congratulations! Your application for the xxx Scholarship...",
                "10 min ago",
                "Today"
        ));

        notifications.add(new NotificationDemo(
                "Application Submitted",
                "We confirmed that your application has been submitted...",
                "3 hr ago",
                "Today"
        ));

        notifications.add(new NotificationDemo(
                "Application Rejected",
                "Unfortunately, your application for the xxx Scholarship...",
                "mm/dd/yy",
                "Earlier"
        ));

        notifications.add(new NotificationDemo(
                "Application Submitted",
                "We confirmed that your application has been submitted...",
                "mm/dd/yy",
                "Earlier"
        ));

        notifications.add(new NotificationDemo(
                "Application Approved ✓",
                "Congratulations! Your application status has been updated...",
                "mm/dd/yy",
                "Earlier"
        ));

        notifications.add(new NotificationDemo(
                "Application Submitted",
                "Your scholarship application has been received successfully...",
                "mm/dd/yy",
                "Earlier"
        ));
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: white;");
        root.setTop(createNavigationBar());

        VBox pageContent = createPageContent();

        ScrollPane scrollPane = new ScrollPane(pageContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background: white;" +
                        "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);
    }

    private HBox createNavigationBar() {
        HBox navBar = new HBox(26);

        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(0, 28, 0, 28));
        navBar.setPrefHeight(82);
        navBar.setMinHeight(82);
        navBar.setMaxHeight(82);

        navBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #673AB7, #32156F);"
        );

        Button menuButton = createNavButton(createMenuIcon());

        Label brandLabel = new Label("SmartScholar");
        brandLabel.setTextFill(Color.WHITE);
        brandLabel.setFont(
                Font.font("Arial", FontWeight.BOLD, 25)
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button homeButton = createNavButton(
                createHomeIcon(Color.WHITE)
        );

        Button applicationButton = createNavButton(
                createClipboardIcon(Color.WHITE)
        );

        Button notificationButton =
                createActiveNotificationButton();

        Button profileButton = createNavButton(
                createPersonIcon(Color.WHITE)
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

        menuButton.setOnAction(event ->
                mainApp.showAdminDemo()
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
        VBox page = new VBox();

        page.setPadding(new Insets(32, 55, 50, 55));
        page.setSpacing(18);
        page.setAlignment(Pos.TOP_LEFT);
        page.setStyle("-fx-background-color: white;");
        page.setFillWidth(true);

        HBox titleRow = new HBox();
        titleRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(6);

        Label title = new Label("Notifications");
        title.setTextFill(Color.web(TEXT_PURPLE));
        title.setFont(
                Font.font("Arial", FontWeight.BOLD, 30)
        );

        Label subtitle = new Label(
                "Stay updated on your application status"
        );

        subtitle.setTextFill(Color.web(SOFT_PURPLE));
        subtitle.setFont(
                Font.font("Arial", FontWeight.NORMAL, 18)
        );

        titleBox.getChildren().addAll(
                title,
                subtitle
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        StackPane searchBar = createSearchBar();

        titleRow.getChildren().addAll(
                titleBox,
                spacer,
                searchBar
        );

        VBox todaySection = createTodaySection();
        VBox earlierSection = createEarlierSection();

        page.getChildren().addAll(
                titleRow,
                todaySection,
                earlierSection
        );

        return page;
    }

    private StackPane createSearchBar() {
        StackPane container = new StackPane();

        container.setMinWidth(270);
        container.setPrefWidth(270);
        container.setMaxWidth(270);

        container.setMinHeight(38);
        container.setPrefHeight(38);
        container.setMaxHeight(38);

        searchField.setPromptText("Search notification...");
        searchField.setMinHeight(38);
        searchField.setPrefHeight(38);
        searchField.setMaxHeight(38);

        searchField.setStyle(
                "-fx-background-color: #E8E2F0;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: transparent;" +
                        "-fx-padding: 0 48 0 22;" +
                        "-fx-font-family: Arial;" +
                        "-fx-font-size: 12px;" +
                        "-fx-prompt-text-fill: #A79DB4;" +
                        "-fx-text-fill: #5A4A72;"
        );

        Node searchIcon = createSearchIcon();

        StackPane.setAlignment(
                searchIcon,
                Pos.CENTER_RIGHT
        );

        StackPane.setMargin(
                searchIcon,
                new Insets(0, 18, 0, 0)
        );

        container.getChildren().addAll(
                searchField,
                searchIcon
        );

        searchField.textProperty().addListener(
                (observable, oldValue, newValue) ->
                        controller.handleSearch(searchField)
        );

        return container;
    }

    private VBox createTodaySection() {
        VBox section = new VBox(12);

        Label heading = new Label("Today");
        heading.setTextFill(Color.web(SOFT_PURPLE));
        heading.setFont(
                Font.font("Arial", FontWeight.NORMAL, 20)
        );

        Region divider = createDivider();

        configureNotificationBox(todayRows);

        section.getChildren().addAll(
                heading,
                divider,
                todayRows
        );

        return section;
    }

    private VBox createEarlierSection() {
        VBox section = new VBox(12);

        section.setPadding(
                new Insets(12, 0, 0, 0)
        );

        Label heading = new Label("Earlier");
        heading.setTextFill(Color.web(SOFT_PURPLE));
        heading.setFont(
                Font.font("Arial", FontWeight.NORMAL, 20)
        );

        Region divider = createDivider();

        configureNotificationBox(earlierRows);

        section.getChildren().addAll(
                heading,
                divider,
                earlierRows
        );

        return section;
    }

    private void configureNotificationBox(VBox box) {
        box.setMinWidth(650);
        box.setPrefWidth(650);
        box.setMaxWidth(650);

        box.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: " + BORDER_PURPLE + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 14;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.20), 7, 0, 1, 4);"
        );
    }

    private Region createDivider() {
        Region divider = new Region();

        divider.setMinHeight(1);
        divider.setPrefHeight(1);
        divider.setMaxHeight(1);

        divider.setStyle(
                "-fx-background-color: #D8C8F5;"
        );

        return divider;
    }

    private void refreshNotifications() {
        todayRows.getChildren().clear();
        earlierRows.getChildren().clear();

        String keyword = searchField
                .getText()
                .trim()
                .toLowerCase();

        List<NotificationDemo> todayFiltered =
                notifications.stream()
                        .filter(notification ->
                                notification.section.equals("Today")
                        )
                        .filter(notification ->
                                matchesSearch(notification, keyword)
                        )
                        .toList();

        List<NotificationDemo> earlierFiltered =
                notifications.stream()
                        .filter(notification ->
                                notification.section.equals("Earlier")
                        )
                        .filter(notification ->
                                matchesSearch(notification, keyword)
                        )
                        .toList();

        if (todayFiltered.isEmpty()) {
            todayRows.getChildren().add(
                    createEmptyRow("No notifications found.")
            );
        } else {
            for (int i = 0; i < todayFiltered.size(); i++) {
                boolean first = i == 0;
                boolean last = i == todayFiltered.size() - 1;

                todayRows.getChildren().add(
                        createNotificationRow(
                                todayFiltered.get(i),
                                first,
                                last
                        )
                );
            }
        }

        if (earlierFiltered.isEmpty()) {
            earlierRows.getChildren().add(
                    createEmptyRow(
                            "No earlier notifications found."
                    )
            );
        } else {
            int visibleCount = showAllEarlier
                    ? earlierFiltered.size()
                    : Math.min(2, earlierFiltered.size());

            boolean hasViewMore =
                    earlierFiltered.size() > 2;

            for (int i = 0; i < visibleCount; i++) {
                boolean first = i == 0;
                boolean last =
                        !hasViewMore &&
                                i == visibleCount - 1;

                earlierRows.getChildren().add(
                        createNotificationRow(
                                earlierFiltered.get(i),
                                first,
                                last
                        )
                );
            }

            if (hasViewMore) {
                earlierRows.getChildren().add(
                        createViewMoreRow(
                                showAllEarlier
                                        ? "View Less..."
                                        : "View More..."
                        )
                );
            }
        }
    }

    private boolean matchesSearch(
            NotificationDemo notification,
            String keyword
    ) {
        return keyword.isEmpty()
                || notification.title
                .toLowerCase()
                .contains(keyword)
                || notification.message
                .toLowerCase()
                .contains(keyword)
                || notification.time
                .toLowerCase()
                .contains(keyword);
    }

    private HBox createNotificationRow(
            NotificationDemo notification,
            boolean firstRow,
            boolean lastRow
    ) {
        HBox row = new HBox(18);

        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(
                new Insets(0, 22, 0, 22)
        );

        row.setMinHeight(76);
        row.setPrefHeight(76);
        row.setMaxHeight(76);
        row.setMaxWidth(Double.MAX_VALUE);

        String backgroundRadius;

        if (firstRow && lastRow) {
            backgroundRadius = "14";
        } else if (firstRow) {
            backgroundRadius = "14 14 0 0";
        } else if (lastRow) {
            backgroundRadius = "0 0 14 14";
        } else {
            backgroundRadius = "0";
        }

        String borderWidth = lastRow
                ? "0"
                : "0 0 1 0";

        row.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: " + backgroundRadius + ";" +
                        "-fx-border-color: transparent transparent #D8C8F5 transparent;" +
                        "-fx-border-width: " + borderWidth + ";"
        );

        Circle avatar = new Circle(16);
        avatar.setFill(Color.web("#D9D9D9"));

        VBox textBox = new VBox(3);
        textBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label(notification.title);

        title.setTextFill(
                Color.web("#5A4A72")
        );

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        17
                )
        );

        Label message = new Label(
                notification.message
        );

        message.setTextFill(
                Color.web("#8E8499")
        );

        message.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        12
                )
        );

        textBox.getChildren().addAll(
                title,
                message
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label time = new Label(notification.time);

        time.setTextFill(
                Color.web("#A39AAA")
        );

        time.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        10
                )
        );

        time.setAlignment(Pos.CENTER);

        time.setMinWidth(72);
        time.setPrefWidth(72);
        time.setMaxWidth(72);

        time.setMinHeight(24);
        time.setPrefHeight(24);
        time.setMaxHeight(24);

        time.setStyle(
                "-fx-background-color: #EEEEF0;" +
                        "-fx-background-radius: 14;" +
                        "-fx-padding: 0;"
        );

        row.getChildren().addAll(
                avatar,
                textBox,
                spacer,
                time
        );

        return row;
    }

    private StackPane createViewMoreRow(String text) {
        StackPane row = new StackPane();

        row.setAlignment(Pos.CENTER);

        row.setMinHeight(34);
        row.setPrefHeight(34);
        row.setMaxHeight(34);

        row.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 0 0 14 14;"
        );

        Button button = new Button(text);

        button.setTextFill(
                Color.web(PURPLE)
        );

        button.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        11
                )
        );

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        button.setOnAction(event -> {
            showAllEarlier = !showAllEarlier;
            controller.handleViewMore();
        });

        row.getChildren().add(button);

        return row;
    }

    private StackPane createEmptyRow(String text) {
        StackPane row = new StackPane();

        row.setMinHeight(76);
        row.setPrefHeight(76);
        row.setMaxHeight(76);

        row.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 14;"
        );

        Label label = new Label(text);

        label.setTextFill(
                Color.web("#8D82A0")
        );

        label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        14
                )
        );

        row.getChildren().add(label);

        return row;
    }

    private Button createNavButton(Node icon) {
        Button button = new Button();

        button.setGraphic(icon);
        button.setAlignment(Pos.CENTER);

        button.setMinSize(42, 42);
        button.setPrefSize(42, 42);
        button.setMaxSize(42, 42);

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        return button;
    }

    private Button createActiveNotificationButton() {
        Button button = new Button();

        StackPane circle = new StackPane();

        circle.setMinSize(42, 42);
        circle.setPrefSize(42, 42);
        circle.setMaxSize(42, 42);

        circle.setStyle(
                "-fx-background-color: #E8DFF7;" +
                        "-fx-background-radius: 50;"
        );

        Node icon = createBellIcon(
                Color.web(DARK_PURPLE)
        );

        circle.getChildren().add(icon);

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

    private Node createMenuIcon() {
        VBox lines = new VBox(6);
        lines.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            Rectangle line = new Rectangle(30, 3);
            line.setArcWidth(2);
            line.setArcHeight(2);
            line.setFill(Color.WHITE);
            lines.getChildren().add(line);
        }

        return lines;
    }

    private Node createHomeIcon(Color color) {
        Group group = new Group();

        Polyline roof = new Polyline(
                2.0, 10.0,
                10.0, 2.0,
                18.0, 10.0
        );

        roof.setFill(Color.TRANSPARENT);
        roof.setStroke(color);
        roof.setStrokeWidth(1.6);

        Path house = new Path(
                new MoveTo(4, 9),
                new LineTo(4, 19),
                new LineTo(16, 19),
                new LineTo(16, 9)
        );

        house.setFill(Color.TRANSPARENT);
        house.setStroke(color);
        house.setStrokeWidth(1.6);

        Rectangle door = new Rectangle(
                8,
                14,
                4,
                5
        );

        door.setFill(Color.TRANSPARENT);
        door.setStroke(color);
        door.setStrokeWidth(1.3);

        group.getChildren().addAll(
                roof,
                house,
                door
        );

        group.setScaleX(1.45);
        group.setScaleY(1.45);

        return group;
    }

    private Node createClipboardIcon(Color color) {
        Group group = new Group();

        Rectangle board = new Rectangle(
                3,
                5,
                14,
                16
        );

        board.setArcWidth(3);
        board.setArcHeight(3);
        board.setFill(Color.TRANSPARENT);
        board.setStroke(color);
        board.setStrokeWidth(1.6);

        Rectangle clip = new Rectangle(
                6,
                2,
                8,
                5
        );

        clip.setArcWidth(3);
        clip.setArcHeight(3);
        clip.setFill(Color.TRANSPARENT);
        clip.setStroke(color);
        clip.setStrokeWidth(1.5);

        group.getChildren().addAll(
                board,
                clip
        );

        group.setScaleX(1.45);
        group.setScaleY(1.45);

        return group;
    }

    private Node createBellIcon(Color color) {
        Group group = new Group();

        Path bell = new Path(
                new MoveTo(3, 15),
                new CubicCurveTo(
                        5, 13,
                        5, 11,
                        5, 8
                ),
                new CubicCurveTo(
                        5, 4,
                        8, 2,
                        10, 2
                ),
                new CubicCurveTo(
                        13, 2,
                        16, 4,
                        16, 8
                ),
                new CubicCurveTo(
                        16, 11,
                        16, 13,
                        18, 15
                ),
                new ClosePath()
        );

        bell.setFill(Color.TRANSPARENT);
        bell.setStroke(color);
        bell.setStrokeWidth(1.6);

        Line bottom = new Line(
                3,
                15,
                18,
                15
        );

        bottom.setStroke(color);
        bottom.setStrokeWidth(1.6);

        Circle clapper = new Circle(
                10.5,
                18,
                1.5
        );

        clapper.setFill(color);

        group.getChildren().addAll(
                bell,
                bottom,
                clapper
        );

        group.setScaleX(1.45);
        group.setScaleY(1.45);

        return group;
    }

    private Node createPersonIcon(Color color) {
        Group group = new Group();

        Circle head = new Circle(
                10,
                6,
                3.5
        );

        head.setFill(Color.TRANSPARENT);
        head.setStroke(color);
        head.setStrokeWidth(1.8);

        Path shoulders = new Path(
                new MoveTo(3, 19),
                new CubicCurveTo(
                        3, 14,
                        6, 12,
                        10, 12
                ),
                new CubicCurveTo(
                        14, 12,
                        17, 14,
                        17, 19
                ),
                new ClosePath()
        );

        shoulders.setFill(Color.TRANSPARENT);
        shoulders.setStroke(color);
        shoulders.setStrokeWidth(1.8);

        group.getChildren().addAll(
                head,
                shoulders
        );

        group.setScaleX(1.45);
        group.setScaleY(1.45);

        return group;
    }

    private Node createSearchIcon() {
        Group group = new Group();

        Circle circle = new Circle(
                6,
                6,
                3.5
        );

        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(
                Color.web("#76678F")
        );
        circle.setStrokeWidth(1.2);

        Line handle = new Line(
                8.5,
                8.5,
                11.5,
                11.5
        );

        handle.setStroke(
                Color.web("#76678F")
        );
        handle.setStrokeWidth(1.2);

        group.getChildren().addAll(
                circle,
                handle
        );

        group.setScaleX(1.4);
        group.setScaleY(1.4);

        return group;
    }

    private static class NotificationDemo {

        private final String title;
        private final String message;
        private final String time;
        private final String section;

        private NotificationDemo(
                String title,
                String message,
                String time,
                String section
        ) {
            this.title = title;
            this.message = message;
            this.time = time;
            this.section = section;
        }
    }

    public BorderPane getView() {
        return root;
    }
}