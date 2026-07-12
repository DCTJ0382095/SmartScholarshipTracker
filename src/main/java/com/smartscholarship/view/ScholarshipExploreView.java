package com.smartscholarship.view;

import com.smartscholarship.controller.ScholarshipExploreController;
import com.smartscholarship.model.Scholarship;
import com.smartscholarship.service.APIService;
import com.smartscholarship.service.ClassificationService;
import com.smartscholarship.service.EligibilityService;
import com.smartscholarship.util.CurrentStudent;
import com.smartscholarship.model.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import java.util.Comparator;
import java.util.List;

public class ScholarshipExploreView {

    private final BorderPane root;
    private final MainApp mainApp;
    private final ScholarshipExploreController controller;
    private final TextField searchField;
    private final GridPane scholarshipGrid;

    private String selectedCategory = "All";
    private boolean showAll = false;

    private static final Color PURPLE = Color.web("#6237BE");
    private static final Color DARK_PURPLE = Color.web("#32156F");
    private static final Color TEXT_PURPLE = Color.web("#4A2A91");
    private static final Color ELIGIBLE_GREEN = Color.web("#39C98A");
    private static final Color NOT_ELIGIBLE_GREY = Color.web("#999999");

    private final List<Scholarship> scholarships = new ArrayList<>();
    private final APIService apiService = new APIService();
    private final EligibilityService eligibilityService = new EligibilityService();
    private final ClassificationService classificationService;

    public ScholarshipExploreView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.controller = new ScholarshipExploreController();
        this.root = new BorderPane();
        this.searchField = new TextField();
        this.scholarshipGrid = new GridPane();
        APIService apiService = new APIService();
        classificationService = new ClassificationService();
        scholarships.addAll(apiService.fetchScholarships());
        controller.setRefreshAction(this::refreshScholarships);
        buildUI();
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: #FFFFFF;");
        root.setTop(createNavigationBar());

        ScrollPane scrollPane = new ScrollPane(createPageContent());

        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background: #FFFFFF;" +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);
        refreshScholarships();
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

        Button menuButton = createIconButton(createMenuIcon());

        Label brandLabel = new Label("SmartScholar");
        brandLabel.setTextFill(Color.WHITE);
        brandLabel.setFont(
                Font.font("Arial", FontWeight.BOLD, 25)
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button homeButton = createActiveHomeButton();
        Button applicationButton =
                createIconButton(createClipboardIcon());
        Button notificationButton =
                createIconButton(createBellIcon());
        Button profileButton =
                createIconButton(
                        createPersonIcon(Color.WHITE, 22)
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
        VBox page = new VBox(22);

        page.setPadding(
                new Insets(38, 70, 55, 70)
        );

        Label title = new Label(
                "Find Scholarships You’re Eligible For"
        );

        title.setTextFill(TEXT_PURPLE);

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        30
                )
        );

        Node searchBar = createSearchBar();
        VBox categorySection = createCategorySection();

        scholarshipGrid.setHgap(22);
        scholarshipGrid.setVgap(18);
        scholarshipGrid.setAlignment(Pos.TOP_CENTER);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(245);
        column1.setPrefWidth(245);
        column1.setMaxWidth(245);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(245);
        column2.setPrefWidth(245);
        column2.setMaxWidth(245);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setMinWidth(245);
        column3.setPrefWidth(245);
        column3.setMaxWidth(245);

        scholarshipGrid.getColumnConstraints().setAll(
                column1,
                column2,
                column3
        );

        Button showAllButton = new Button("Show All⌄");

        showAllButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #6237BE;" +
                        "-fx-font-size: 15px;" +
                        "-fx-cursor: hand;"
        );

        showAllButton.setOnAction(event -> {
            showAll = !showAll;

            showAllButton.setText(
                    showAll
                            ? "Show Less⌃"
                            : "Show All⌄"
            );

            refreshScholarships();
        });

        HBox showAllBox = new HBox(showAllButton);
        showAllBox.setAlignment(Pos.CENTER);

        page.getChildren().addAll(
                title,
                searchBar,
                categorySection,
                scholarshipGrid,
                showAllBox
        );

        return page;
    }

    private Node createSearchBar() {
        StackPane searchContainer = new StackPane();
        searchContainer.setMaxWidth(600);

        searchField.setPromptText(
                "Search scholarships..."
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
                        controller.handleSearch(searchField)
        );

        return searchContainer;
    }

    private VBox createCategorySection() {
        VBox section = new VBox(10);

        Label categoryLabel = new Label("Category");

        categoryLabel.setTextFill(
                Color.web("#7C6A9C")
        );

        categoryLabel.setFont(
                Font.font("Arial", 15)
        );

        HBox buttons = new HBox(12);
        ToggleGroup categoryGroup = new ToggleGroup();

        ToggleButton all =
                createCategoryButton("All", categoryGroup);
        ToggleButton merit =
                createCategoryButton("Merit", categoryGroup);
        ToggleButton financialAid =
                createCategoryButton("Financial Aid", categoryGroup);
        ToggleButton excellence =
                createCategoryButton("Excellence", categoryGroup);

        all.setSelected(true);

        buttons.getChildren().addAll(
                all,
                merit,
                financialAid,
                excellence
        );

        section.getChildren().addAll(
                categoryLabel,
                buttons
        );

        return section;
    }

    private ToggleButton createCategoryButton(
            String text,
            ToggleGroup group
    ) {
        ToggleButton button = new ToggleButton(text);

        button.setToggleGroup(group);
        button.setStyle(getCategoryButtonStyle(false));

        button.selectedProperty().addListener(
                (observable, oldValue, selected) -> {
                    button.setStyle(
                            getCategoryButtonStyle(selected)
                    );

                    if (selected) {
                        selectedCategory = text;
                        controller.handleCategoryFilter(text);
                    }
                }
        );

        return button;
    }

    private String getCategoryButtonStyle(boolean selected) {
        if (selected) {
            return
                    "-fx-background-color: #6237BE;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 18;" +
                            "-fx-border-radius: 18;" +
                            "-fx-padding: 6 15 6 15;" +
                            "-fx-cursor: hand;";
        }

        return
                "-fx-background-color: white;" +
                        "-fx-text-fill: #6237BE;" +
                        "-fx-border-color: #6237BE;" +
                        "-fx-border-radius: 18;" +
                        "-fx-background-radius: 18;" +
                        "-fx-padding: 6 15 6 15;" +
                        "-fx-cursor: hand;";
    }

    private void refreshScholarships() {
        scholarshipGrid.getChildren().clear();

        String keyword =
                searchField.getText()
                        .trim()
                        .toLowerCase();

        List<Scholarship> filtered =
                scholarships.stream()
                        .filter(scholarship -> {
                            if (selectedCategory.equals("All")) {
                                return true;
                            }
                            return getCategoryText(scholarship)
                                    .toLowerCase()
                                    .contains(selectedCategory.toLowerCase());
                        })
                        .filter(scholarship ->
                                keyword.isEmpty()
                                        || scholarship.getTitle()
                                        .toLowerCase()
                                        .contains(keyword)
                                        || scholarship.getSponsorName()
                                        .toLowerCase()
                                        .contains(keyword)
                        ).toList();

        int limit =
                showAll
                        ? filtered.size()
                        : Math.min(9, filtered.size());

        for (int i = 0; i < limit; i++) {
            int column = i % 3;
            int row = i / 3;

            scholarshipGrid.add(
                    createScholarshipCard(
                            filtered.get(i)
                    ),
                    column,
                    row
            );
        }

        if (filtered.isEmpty()) {
            Label emptyLabel =
                    new Label("No scholarships found.");

            emptyLabel.setTextFill(
                    Color.web("#777777")
            );

            emptyLabel.setFont(
                    Font.font("Arial", 15)
            );

            scholarshipGrid.add(
                    emptyLabel,
                    0,
                    0,
                    3,
                    1
            );
        }
    }

    private VBox createScholarshipCard(
            Scholarship scholarship
    ) {
        VBox card = new VBox();

        card.setMinSize(245, 270);
        card.setPrefSize(245, 270);
        card.setMaxSize(245, 270);

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #B79BEF;" +
                        "-fx-border-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 6, 0, 1, 4);" +
                        "-fx-cursor: hand;"
        );

        HBox awardHeader = new HBox(6);

        awardHeader.setAlignment(Pos.CENTER);
        awardHeader.setMinHeight(48);
        awardHeader.setPrefHeight(48);
        awardHeader.setMaxHeight(48);

        awardHeader.setPadding(
                new Insets(8, 10, 8, 10)
        );

        awardHeader.setStyle(
                "-fx-background-color: #F1EAFE;" +
                        "-fx-background-radius: 12 12 0 0;" +
                        "-fx-border-color: transparent transparent #B79BEF transparent;"
        );

        Label upTo = new Label("Up to");

        upTo.setTextFill(
                Color.web("#6F5A98")
        );

        upTo.setFont(
                Font.font("Arial", 10)
        );

        Label amount =
                new Label(scholarship.getAward());

        amount.setTextFill(PURPLE);

        amount.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        25
                )
        );

        awardHeader.getChildren().addAll(
                upTo,
                amount
        );

        VBox body = new VBox(8);

        body.setPadding(
                new Insets(12, 14, 12, 14)
        );

        body.setMinHeight(222);
        body.setPrefHeight(222);
        body.setMaxHeight(222);

        Label name =
                new Label(scholarship.getTitle());

        name.setTextFill(PURPLE);

        name.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        17
                )
        );

        name.setWrapText(true);
        name.setMinHeight(42);
        name.setPrefHeight(42);
        name.setMaxHeight(42);
        name.setMaxWidth(Double.MAX_VALUE);

        Label description =
                new Label(scholarship.getDescription());

        description.setTextFill(
                Color.web("#777777")
        );

        description.setFont(
                Font.font("Arial", 11)
        );

        description.setWrapText(true);
        description.setMinHeight(58);
        description.setPrefHeight(58);
        description.setMaxHeight(58);
        description.setMaxWidth(Double.MAX_VALUE);

        Region bodySpacer = new Region();
        VBox.setVgrow(bodySpacer, Priority.ALWAYS);

        HBox organisationRow = new HBox(6);

        organisationRow.setAlignment(
                Pos.CENTER_RIGHT
        );

        organisationRow.setMinHeight(18);
        organisationRow.setPrefHeight(18);
        organisationRow.setMaxHeight(18);

        Label by =
                new Label(
                        "by " + scholarship.getSponsorName()
                );

        by.setTextFill(
                Color.web("#777777")
        );

        by.setFont(
                Font.font("Arial", 9)
        );

        Circle organisationIcon =
                new Circle(
                        5,
                        Color.web("#D5D5D5")
                );

        organisationRow.getChildren().addAll(
                by,
                organisationIcon
        );

        Student student = CurrentStudent.getStudent();

        boolean eligible = false;

        if (student != null) {
            eligible = eligibilityService.isEligible(student, scholarship);
        }

        Label eligibility;

        if (eligible) {
            eligibility = new Label("✓ Eligible");
            eligibility.setTextFill(ELIGIBLE_GREEN);
        } else {
            eligibility = new Label("Not Eligible");
            eligibility.setTextFill(NOT_ELIGIBLE_GREY);
        }

        eligibility.setStyle(
                "-fx-font-size: 12px;"
        );

        eligibility.setFont(Font.font("Arial", 12));

        eligibility.setAlignment(Pos.CENTER);
        eligibility.setMaxWidth(Double.MAX_VALUE);
        eligibility.setMinHeight(20);
        eligibility.setPrefHeight(20);
        eligibility.setMaxHeight(20);

        body.getChildren().addAll(
                name,
                description,
                bodySpacer,
                organisationRow,
                eligibility
        );

        card.getChildren().addAll(
                awardHeader,
                body
        );

        card.setOnMouseClicked(event ->
                openScholarshipDetails(scholarship)
        );

        return card;
    }

    private void openScholarshipDetails(
            Scholarship scholarship
    ) {
        ScholarshipDetailsView detailsView =
                new ScholarshipDetailsView(
                        root.getScene().getWindow(),
                        scholarship
                );

        detailsView.show();
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

    private Button createActiveHomeButton() {
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
                createHomeIcon(DARK_PURPLE)
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

    private Node createHomeIcon(Color color) {
        Group group = new Group();

        Polyline roof = new Polyline(
                2.0, 11.0,
                11.0, 2.0,
                20.0, 11.0
        );

        roof.setFill(Color.TRANSPARENT);
        roof.setStroke(color);
        roof.setStrokeWidth(1.8);

        Path house = new Path(
                new MoveTo(5, 10),
                new LineTo(5, 21),
                new LineTo(17, 21),
                new LineTo(17, 10)
        );

        house.setFill(Color.TRANSPARENT);
        house.setStroke(color);
        house.setStrokeWidth(1.8);

        Rectangle door =
                new Rectangle(9, 15, 4, 6);

        door.setFill(Color.TRANSPARENT);
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

    private Node createSearchIcon() {
        Group group = new Group();

        Circle circle =
                new Circle(8, 8, 5);

        circle.setFill(Color.TRANSPARENT);

        circle.setStroke(
                Color.web("#6F6288")
        );

        circle.setStrokeWidth(1.5);

        Line handle =
                new Line(12, 12, 16, 16);

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

    private String getCategoryText(Scholarship scholarship) {
        return classificationService.classify(scholarship).stream().map(category ->
                        category.name().replace("_", " ")).reduce((a, b) -> a + ", " + b).orElse("Uncategorized");
    }

    public BorderPane getView() {
        return root;
    }
}