package com.smartscholarship.view;

import com.smartscholarship.controller.ApplicationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

import java.util.ArrayList;
import java.util.List;

public class ApplicationView {

    private final BorderPane root;
    private final MainApp mainApp;
    private final ApplicationController controller;
    private final TextField searchField;
    private final VBox applicationRows;
    private final List<ApplicationDemo> applications;

    private String selectedStatus = "All";

    private static final String PURPLE = "#6237C7";
    private static final String DARK_PURPLE = "#351577";
    private static final String TEXT_PURPLE = "#4A2A91";
    private static final String LIGHT_PURPLE = "#F5F1FF";
    private static final String BORDER_PURPLE = "#B99AF2";

    public ApplicationView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.controller = new ApplicationController();
        this.root = new BorderPane();
        this.searchField = new TextField();
        this.applicationRows = new VBox();
        this.applications = new ArrayList<>();

        createDemoApplications();
        controller.setRefreshAction(this::refreshApplications);
        buildUI();
    }

    private void createDemoApplications() {
        applications.add(new ApplicationDemo(
                "xxx Scholarship Name",
                "Excellence",
                "Pending"
        ));

        applications.add(new ApplicationDemo(
                "xxx Scholarship Name",
                "Financial Aid",
                "Rejected"
        ));

        applications.add(new ApplicationDemo(
                "xxx Scholarship Name",
                "Merit",
                "Approved"
        ));

        applications.add(new ApplicationDemo(
                "xxx Scholarship Name",
                "Merit",
                "Approved"
        ));
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: white;");
        root.setTop(createNavigationBar());
        root.setCenter(createPageContent());
    }

    private HBox createNavigationBar() {
        HBox navBar = new HBox(26);

        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(0, 35, 0, 35));
        navBar.setPrefHeight(102);
        navBar.setMinHeight(102);
        navBar.setMaxHeight(102);

        navBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #673AB7, #32156F);"
        );

        Button menuButton = createNavButton(createMenuIcon());

        Label brandLabel = new Label("SmartScholar");
        brandLabel.setTextFill(Color.WHITE);
        brandLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button homeButton = createNavButton(createHomeIcon(Color.WHITE));
        Button applicationButton = createActiveApplicationButton();
        Button notificationButton = createNavButton(createBellIcon());
        Button profileButton = createNavButton(createPersonIcon(Color.WHITE));

        homeButton.setOnAction(event ->
                mainApp.showScholarshipExplore()
        );

        applicationButton.setOnAction(event ->
                mainApp.showApplications()
        );

        profileButton.setOnAction(event ->
                mainApp.showProfile()
        );

        notificationButton.setOnAction(event ->
                mainApp.showNotifications()
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

        page.setPadding(new Insets(50, 80, 70, 80));
        page.setSpacing(20);
        page.setAlignment(Pos.TOP_LEFT);
        page.setStyle("-fx-background-color: white;");

        HBox titleRow = new HBox();
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("My Applications");
        title.setTextFill(Color.web(TEXT_PURPLE));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 38));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        StackPane searchBar = createSearchBar();

        titleRow.getChildren().addAll(
                title,
                spacer,
                searchBar
        );

        VBox filterSection = createFilterSection();

        Region divider = new Region();
        divider.setMinHeight(1);
        divider.setPrefHeight(1);
        divider.setMaxHeight(1);
        divider.setStyle("-fx-background-color: #D8C8F5;");

        VBox table = createApplicationTable();

        VBox.setMargin(divider, new Insets(5, 0, 0, 0));
        VBox.setMargin(table, new Insets(0, 20, 0, 20));

        page.getChildren().addAll(
                titleRow,
                filterSection,
                divider,
                table
        );

        return page;
    }

    private StackPane createSearchBar() {
        StackPane container = new StackPane();

        container.setMinWidth(300);
        container.setPrefWidth(300);
        container.setMaxWidth(300);

        container.setMinHeight(48);
        container.setPrefHeight(48);
        container.setMaxHeight(48);

        searchField.setPromptText("Search...");
        searchField.setMinHeight(48);
        searchField.setPrefHeight(48);
        searchField.setMaxHeight(48);

        searchField.setStyle(
                "-fx-background-color: #E8E2F0;" +
                        "-fx-background-radius: 24;" +
                        "-fx-border-radius: 24;" +
                        "-fx-border-color: transparent;" +
                        "-fx-padding: 0 48 0 22;" +
                        "-fx-font-family: Arial;" +
                        "-fx-font-size: 14px;" +
                        "-fx-prompt-text-fill: #A79DB4;" +
                        "-fx-text-fill: #5A4A72;"
        );

        Node searchIcon = createSearchIcon();

        StackPane.setAlignment(searchIcon, Pos.CENTER_RIGHT);
        StackPane.setMargin(searchIcon, new Insets(0, 18, 0, 0));

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

    private VBox createFilterSection() {
        VBox section = new VBox(12);

        Label categoryLabel = new Label("Category");
        categoryLabel.setTextFill(Color.web("#8D73A9"));
        categoryLabel.setFont(
                Font.font("Arial", FontWeight.NORMAL, 20)
        );

        HBox buttons = new HBox(14);
        buttons.setAlignment(Pos.CENTER_LEFT);

        ToggleGroup group = new ToggleGroup();

        ToggleButton all = createFilterButton("All", group);
        ToggleButton pending = createFilterButton("Pending", group);
        ToggleButton approved = createFilterButton("Approved", group);
        ToggleButton rejected = createFilterButton("Rejected", group);

        all.setSelected(true);

        buttons.getChildren().addAll(
                all,
                pending,
                approved,
                rejected
        );

        section.getChildren().addAll(
                categoryLabel,
                buttons
        );

        return section;
    }

    private ToggleButton createFilterButton(
            String text,
            ToggleGroup group
    ) {
        ToggleButton button = new ToggleButton(text);

        button.setToggleGroup(group);
        button.setMinHeight(38);
        button.setPrefHeight(38);
        button.setMaxHeight(38);

        button.setStyle(getFilterStyle(false));

        button.selectedProperty().addListener(
                (observable, oldValue, selected) -> {
                    button.setStyle(getFilterStyle(selected));

                    if (selected) {
                        selectedStatus = text;
                        controller.handleStatusFilter(text);
                    }
                }
        );

        return button;
    }

    private String getFilterStyle(boolean selected) {
        if (selected) {
            return
                    "-fx-background-color: " + PURPLE + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-color: " + PURPLE + ";" +
                            "-fx-padding: 3 18 3 18;" +
                            "-fx-font-family: Arial;" +
                            "-fx-font-size: 16px;" +
                            "-fx-cursor: hand;";
        }

        return
                "-fx-background-color: white;" +
                        "-fx-text-fill: " + PURPLE + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: " + PURPLE + ";" +
                        "-fx-border-width: 1.5;" +
                        "-fx-padding: 3 18 3 18;" +
                        "-fx-font-family: Arial;" +
                        "-fx-font-size: 16px;" +
                        "-fx-cursor: hand;";
    }

    private VBox createApplicationTable() {
        VBox table = new VBox(0);

        table.setMaxWidth(Double.MAX_VALUE);

        table.setStyle(
                "-fx-background-color: " + LIGHT_PURPLE + ";" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: " + BORDER_PURPLE + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 14;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.22), 7, 0, 1, 4);"
        );

        GridPane header = createTableGrid();

        header.setAlignment(Pos.CENTER);
        header.setMinHeight(74);
        header.setPrefHeight(74);
        header.setMaxHeight(74);

        header.setStyle(
                "-fx-background-color: " + PURPLE + ";" +
                        "-fx-background-radius: 14 14 0 0;"
        );

        StackPane scholarshipHeader = createHeaderCell("Scholarship");
        StackPane categoryHeader = createHeaderCell("Category");
        StackPane statusHeader = createHeaderCell("Status");

        header.add(scholarshipHeader, 0, 0);
        header.add(categoryHeader, 1, 0);
        header.add(statusHeader, 2, 0);

        applicationRows.setSpacing(0);
        applicationRows.setFillWidth(true);

        table.getChildren().addAll(
                header,
                applicationRows
        );

        return table;
    }

    private GridPane createTableGrid() {
        GridPane grid = new GridPane();

        ColumnConstraints scholarshipColumn = new ColumnConstraints();
        scholarshipColumn.setPercentWidth(50);
        scholarshipColumn.setHgrow(Priority.ALWAYS);

        ColumnConstraints categoryColumn = new ColumnConstraints();
        categoryColumn.setPercentWidth(25);
        categoryColumn.setHgrow(Priority.ALWAYS);

        ColumnConstraints statusColumn = new ColumnConstraints();
        statusColumn.setPercentWidth(25);
        statusColumn.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(
                scholarshipColumn,
                categoryColumn,
                statusColumn
        );

        grid.setMaxWidth(Double.MAX_VALUE);

        return grid;
    }

    private StackPane createHeaderCell(String text) {
        StackPane cell = new StackPane();
        cell.setAlignment(Pos.CENTER);

        cell.setMinHeight(74);
        cell.setPrefHeight(74);
        cell.setMaxHeight(74);
        cell.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(
                Font.font("Arial", FontWeight.NORMAL, 18)
        );
        label.setAlignment(Pos.CENTER);
        label.setPadding(Insets.EMPTY);

        cell.getChildren().add(label);

        GridPane.setHgrow(cell, Priority.ALWAYS);
        GridPane.setVgrow(cell, Priority.ALWAYS);

        return cell;
    }

    private void refreshApplications() {
        applicationRows.getChildren().clear();

        String keyword = searchField
                .getText()
                .trim()
                .toLowerCase();

        List<ApplicationDemo> filtered = applications.stream()
                .filter(application ->
                        selectedStatus.equals("All")
                                || application.status.equals(selectedStatus)
                )
                .filter(application ->
                        keyword.isEmpty()
                                || application.scholarship
                                .toLowerCase()
                                .contains(keyword)
                                || application.category
                                .toLowerCase()
                                .contains(keyword)
                                || application.status
                                .toLowerCase()
                                .contains(keyword)
                )
                .toList();

        for (int i = 0; i < filtered.size(); i++) {
            applicationRows.getChildren().add(
                    createApplicationRow(
                            filtered.get(i),
                            i == filtered.size() - 1
                    )
            );
        }

        if (filtered.isEmpty()) {
            StackPane emptyBox = new StackPane();

            emptyBox.setMinHeight(68);
            emptyBox.setPrefHeight(68);
            emptyBox.setMaxHeight(68);

            Label emptyLabel = new Label("No applications found.");
            emptyLabel.setTextFill(Color.web("#8D82A0"));
            emptyLabel.setFont(Font.font("Arial", 15));

            emptyBox.getChildren().add(emptyLabel);
            applicationRows.getChildren().add(emptyBox);
        }
    }

    private GridPane createApplicationRow(
            ApplicationDemo application,
            boolean lastRow
    ) {
        GridPane row = createTableGrid();

        row.setAlignment(Pos.CENTER);
        row.setMinHeight(68);
        row.setPrefHeight(68);
        row.setMaxHeight(68);

        String radius = lastRow ? "0 0 14 14" : "0";

        row.setStyle(
                "-fx-background-color: " + LIGHT_PURPLE + ";" +
                        "-fx-background-radius: " + radius + ";" +
                        "-fx-border-color: transparent transparent #D8C8F5 transparent;" +
                        "-fx-border-width: 0 0 1 0;"
        );

        StackPane scholarshipCell = createBodyCell(
                application.scholarship,
                Pos.CENTER_LEFT,
                false
        );

        scholarshipCell.setPadding(new Insets(0, 0, 0, 24));

        StackPane categoryCell = createBodyCell(
                application.category,
                Pos.CENTER,
                true
        );

        StackPane statusCell = new StackPane(
                createStatusLabel(application.status)
        );

        statusCell.setAlignment(Pos.CENTER);
        statusCell.setMinHeight(68);
        statusCell.setPrefHeight(68);
        statusCell.setMaxHeight(68);
        statusCell.setMaxWidth(Double.MAX_VALUE);

        row.add(scholarshipCell, 0, 0);
        row.add(categoryCell, 1, 0);
        row.add(statusCell, 2, 0);

        GridPane.setHgrow(scholarshipCell, Priority.ALWAYS);
        GridPane.setHgrow(categoryCell, Priority.ALWAYS);
        GridPane.setHgrow(statusCell, Priority.ALWAYS);

        GridPane.setVgrow(scholarshipCell, Priority.ALWAYS);
        GridPane.setVgrow(categoryCell, Priority.ALWAYS);
        GridPane.setVgrow(statusCell, Priority.ALWAYS);

        return row;
    }

    private StackPane createBodyCell(
            String text,
            Pos alignment,
            boolean bold
    ) {
        StackPane cell = new StackPane();

        cell.setAlignment(alignment);
        cell.setMinHeight(68);
        cell.setPrefHeight(68);
        cell.setMaxHeight(68);
        cell.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(text);

        label.setTextFill(
                bold
                        ? Color.web(TEXT_PURPLE)
                        : Color.web("#654A91")
        );

        label.setFont(
                Font.font(
                        "Arial",
                        bold ? FontWeight.BOLD : FontWeight.NORMAL,
                        15
                )
        );

        label.setAlignment(alignment);
        label.setPadding(Insets.EMPTY);

        cell.getChildren().add(label);

        return cell;
    }

    private Label createStatusLabel(String status) {
        Label label = new Label(status);

        label.setTextFill(Color.WHITE);
        label.setFont(
                Font.font("Arial", FontWeight.NORMAL, 13)
        );
        label.setAlignment(Pos.CENTER);

        label.setMinWidth(116);
        label.setPrefWidth(116);
        label.setMaxWidth(116);

        label.setMinHeight(38);
        label.setPrefHeight(38);
        label.setMaxHeight(38);

        String backgroundColor;

        switch (status) {
            case "Approved" -> backgroundColor = "#63DDB0";
            case "Rejected" -> backgroundColor = "#FF6878";
            default -> backgroundColor = "#F7C94D";
        }

        label.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 0;"
        );

        return label;
    }

    private Button createNavButton(Node icon) {
        Button button = new Button();

        button.setGraphic(icon);
        button.setAlignment(Pos.CENTER);

        button.setMinSize(54, 54);
        button.setPrefSize(54, 54);
        button.setMaxSize(54, 54);

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        return button;
    }

    private Button createActiveApplicationButton() {
        Button button = new Button();

        StackPane circle = new StackPane();

        circle.setMinSize(58, 58);
        circle.setPrefSize(58, 58);
        circle.setMaxSize(58, 58);

        circle.setStyle(
                "-fx-background-color: #E8DFF7;" +
                        "-fx-background-radius: 50;"
        );

        Node icon = createClipboardIcon(Color.web(DARK_PURPLE));
        icon.setScaleX(1.45);
        icon.setScaleY(1.45);

        circle.getChildren().add(icon);

        button.setGraphic(circle);

        button.setMinSize(62, 62);
        button.setPrefSize(62, 62);
        button.setMaxSize(62, 62);

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

        Rectangle door = new Rectangle(8, 14, 4, 5);
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

        Rectangle board = new Rectangle(3, 5, 14, 16);
        board.setArcWidth(3);
        board.setArcHeight(3);
        board.setFill(Color.TRANSPARENT);
        board.setStroke(color);
        board.setStrokeWidth(1.6);

        Rectangle clip = new Rectangle(6, 2, 8, 5);
        clip.setArcWidth(3);
        clip.setArcHeight(3);
        clip.setFill(Color.TRANSPARENT);
        clip.setStroke(color);
        clip.setStrokeWidth(1.5);

        group.getChildren().addAll(
                board,
                clip
        );

        return group;
    }

    private Node createBellIcon() {
        Group group = new Group();

        Path bell = new Path(
                new MoveTo(3, 15),
                new CubicCurveTo(5, 13, 5, 11, 5, 8),
                new CubicCurveTo(5, 4, 8, 2, 10, 2),
                new CubicCurveTo(13, 2, 16, 4, 16, 8),
                new CubicCurveTo(16, 11, 16, 13, 18, 15),
                new ClosePath()
        );

        bell.setFill(Color.TRANSPARENT);
        bell.setStroke(Color.WHITE);
        bell.setStrokeWidth(1.6);

        Line bottom = new Line(3, 15, 18, 15);
        bottom.setStroke(Color.WHITE);
        bottom.setStrokeWidth(1.6);

        group.getChildren().addAll(
                bell,
                bottom
        );

        group.setScaleX(1.45);
        group.setScaleY(1.45);

        return group;
    }

    private Node createPersonIcon(Color color) {
        Group group = new Group();

        Circle head = new Circle(10, 6, 3.5);
        head.setFill(Color.TRANSPARENT);
        head.setStroke(color);
        head.setStrokeWidth(1.8);

        Path shoulders = new Path(
                new MoveTo(3, 19),
                new CubicCurveTo(3, 14, 6, 12, 10, 12),
                new CubicCurveTo(14, 12, 17, 14, 17, 19),
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

        Circle circle = new Circle(6, 6, 3.5);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.web("#76678F"));
        circle.setStrokeWidth(1.2);

        Line handle = new Line(8.5, 8.5, 11.5, 11.5);
        handle.setStroke(Color.web("#76678F"));
        handle.setStrokeWidth(1.2);

        group.getChildren().addAll(
                circle,
                handle
        );

        group.setScaleX(1.4);
        group.setScaleY(1.4);

        return group;
    }

    private static class ApplicationDemo {

        private final String scholarship;
        private final String category;
        private final String status;

        private ApplicationDemo(
                String scholarship,
                String category,
                String status
        ) {
            this.scholarship = scholarship;
            this.category = category;
            this.status = status;
        }
    }

    public BorderPane getView() {
        return root;
    }
}