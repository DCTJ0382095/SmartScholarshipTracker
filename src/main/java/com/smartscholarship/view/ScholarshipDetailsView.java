package com.smartscholarship.view;

import com.smartscholarship.controller.ScholarshipDetailsController;
import com.smartscholarship.model.Scholarship;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ScholarshipDetailsView {

    private final Stage stage;
    private final ScholarshipDetailsController controller;
    private final Scholarship scholarship;

    private static final String PURPLE = "#6237BE";
    private static final String DARK_PURPLE = "#4A2A91";
    private static final String SOFT_PURPLE = "#7C6A9C";
    private static final String BORDER_PURPLE = "#B79BEF";

    public ScholarshipDetailsView(
            Window owner,
            Scholarship scholarship
    ) {
        this.stage = new Stage();
        this.controller = new ScholarshipDetailsController();
        this.scholarship = scholarship;

        buildStage(owner);
    }

    private void buildStage(Window owner) {
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Scholarship Information");
        stage.setResizable(false);

        VBox content = createContent();

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background: white;" +
                        "-fx-background-color: white;" +
                        "-fx-border-color: transparent;"
        );

        Scene scene = new Scene(
                scrollPane,
                660,
                720
        );

        stage.setScene(scene);
    }

    private VBox createContent() {
        VBox page = new VBox(18);

        page.setPadding(
                new Insets(28, 34, 32, 34)
        );

        page.setStyle(
                "-fx-background-color: white;"
        );

        HBox topRow = createTopRow();
        HBox summaryBox = createSummaryBox();

        VBox descriptionSection = createTextSection(
                "Description",
                scholarship.getDescription()
        );

        VBox eligibilitySection = createEligibilitySection();
        VBox contactSection = createContactSection();
        HBox actionButtons = createActionButtons();

        page.getChildren().addAll(
                topRow,
                summaryBox,
                descriptionSection,
                eligibilitySection,
                contactSection,
                actionButtons
        );

        return page;
    }

    private HBox createTopRow() {
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.TOP_LEFT);

        VBox titleBox = new VBox(5);

        Label title = new Label(
                valueOrDefault(
                        scholarship.getTitle(),
                        "Scholarship Information"
                )
        );

        title.setTextFill(
                Color.web(DARK_PURPLE)
        );

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        27
                )
        );

        title.setWrapText(true);
        title.setMaxWidth(500);

        Label organisationLabel = new Label(
                "by " + valueOrDefault(
                        scholarship.getSponsorName(),
                        "Organisation unavailable"
                )
        );

        organisationLabel.setTextFill(
                Color.web(SOFT_PURPLE)
        );

        organisationLabel.setFont(
                Font.font("Arial", 13)
        );

        titleBox.getChildren().addAll(
                title,
                organisationLabel
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeButton = new Button("×");

        closeButton.setTextFill(
                Color.web(DARK_PURPLE)
        );

        closeButton.setFont(
                Font.font(
                        "Arial",
                        FontWeight.NORMAL,
                        25
                )
        );

        closeButton.setMinSize(38, 38);
        closeButton.setPrefSize(38, 38);
        closeButton.setMaxSize(38, 38);

        closeButton.setStyle(
                "-fx-background-color: #F1EAFE;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        closeButton.setOnAction(
                event -> stage.close()
        );

        topRow.getChildren().addAll(
                titleBox,
                spacer,
                closeButton
        );

        return topRow;
    }

    private HBox createSummaryBox() {
        HBox summary = new HBox();

        summary.setAlignment(Pos.CENTER);

        summary.setPadding(
                new Insets(18, 20, 18, 20)
        );

        summary.setStyle(
                "-fx-background-color: #F4EFFF;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: " + BORDER_PURPLE + ";" +
                        "-fx-border-radius: 14;"
        );

        VBox awardBox = createSummaryItem(
                "Award Amount",
                valueOrDefault(
                        scholarship.getAward(),
                        "Not specified"
                )
        );

        Region divider1 = createVerticalDivider();

        VBox deadlineBox = createSummaryItem(
                "Deadline",
                valueOrDefault(
                        scholarship.getDeadline(),
                        "Not specified"
                )
        );

        Region divider2 = createVerticalDivider();

        VBox categoryBox = createSummaryItem(
                "Category",
                valueOrDefault(
                        scholarship.getAwardType(),
                        "Not specified"
                )
        );

        HBox.setHgrow(
                awardBox,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                deadlineBox,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                categoryBox,
                Priority.ALWAYS
        );

        summary.getChildren().addAll(
                awardBox,
                divider1,
                deadlineBox,
                divider2,
                categoryBox
        );

        return summary;
    }

    private VBox createSummaryItem(
            String heading,
            String value
    ) {
        VBox box = new VBox(5);

        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);

        Label headingLabel = new Label(heading);

        headingLabel.setTextFill(
                Color.web(SOFT_PURPLE)
        );

        headingLabel.setFont(
                Font.font("Arial", 11)
        );

        Label valueLabel = new Label(value);

        valueLabel.setTextFill(
                Color.web(PURPLE)
        );

        valueLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        15
                )
        );

        valueLabel.setWrapText(true);
        valueLabel.setAlignment(Pos.CENTER);

        box.getChildren().addAll(
                headingLabel,
                valueLabel
        );

        return box;
    }

    private Region createVerticalDivider() {
        Region divider = new Region();

        divider.setMinWidth(1);
        divider.setPrefWidth(1);
        divider.setMaxWidth(1);

        divider.setMinHeight(45);
        divider.setPrefHeight(45);
        divider.setMaxHeight(45);

        divider.setStyle(
                "-fx-background-color: #D8C8F5;"
        );

        return divider;
    }

    private VBox createTextSection(
            String heading,
            String text
    ) {
        VBox section = new VBox(8);

        Label headingLabel = createSectionHeading(
                heading
        );

        Label textLabel = new Label(
                valueOrDefault(
                        text,
                        "Information not available."
                )
        );

        textLabel.setTextFill(
                Color.web("#6F6578")
        );

        textLabel.setFont(
                Font.font("Arial", 13)
        );

        textLabel.setWrapText(true);
        textLabel.setMaxWidth(Double.MAX_VALUE);

        section.getChildren().addAll(
                headingLabel,
                textLabel
        );

        return section;
    }

    private VBox createEligibilitySection() {
        VBox section = new VBox(10);

        Label heading = createSectionHeading(
                "Eligibility Requirements"
        );

        VBox requirementBox = new VBox(8);

        requirementBox.setPadding(
                new Insets(15, 18, 15, 18)
        );

        requirementBox.setStyle(
                "-fx-background-color: #FAF8FF;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #D8C8F5;" +
                        "-fx-border-radius: 12;"
        );

        requirementBox.getChildren().addAll(
                createRequirementRow(
                        "Requirements",
                        scholarship.getRequirements()
                ),
                createRequirementRow(
                        "GPA Requirement",
                        scholarship.getGpaRequirement()
                ),
                createRequirementRow(
                        "Majors",
                        scholarship.getMajors()
                ),
                createRequirementRow(
                        "Enrollment Level",
                        scholarship.getEnrollmentLevel()
                ),
                createRequirementRow(
                        "Geographic Restrictions",
                        scholarship.getGeographicRestrictions()
                )
        );

        section.getChildren().addAll(
                heading,
                requirementBox
        );

        return section;
    }

    private HBox createRequirementRow(
            String heading,
            String value
    ) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.TOP_LEFT);

        Label bullet = new Label("•");

        bullet.setTextFill(
                Color.web(PURPLE)
        );

        bullet.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        16
                )
        );

        VBox textBox = new VBox(2);

        Label headingLabel = new Label(heading);

        headingLabel.setTextFill(
                Color.web(DARK_PURPLE)
        );

        headingLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        12
                )
        );

        Label valueLabel = new Label(
                valueOrDefault(
                        value,
                        "Not specified"
                )
        );

        valueLabel.setTextFill(
                Color.web("#746A7D")
        );

        valueLabel.setFont(
                Font.font("Arial", 12)
        );

        valueLabel.setWrapText(true);
        valueLabel.setMaxWidth(520);

        textBox.getChildren().addAll(
                headingLabel,
                valueLabel
        );

        row.getChildren().addAll(
                bullet,
                textBox
        );

        return row;
    }

    private VBox createContactSection() {
        VBox section = new VBox(9);

        Label heading = createSectionHeading(
                "Contact & Application"
        );

        VBox box = new VBox(8);

        box.setPadding(
                new Insets(15, 18, 15, 18)
        );

        box.setStyle(
                "-fx-background-color: #F4EFFF;" +
                        "-fx-background-radius: 12;"
        );

        Label organisationLabel = new Label(
                "Organisation: " +
                        valueOrDefault(
                                scholarship.getSponsorName(),
                                "Not specified"
                        )
        );

        organisationLabel.setTextFill(
                Color.web("#655A70")
        );

        organisationLabel.setFont(
                Font.font("Arial", 12)
        );

        Label contactLabel = new Label(
                "Contact: " +
                        valueOrDefault(
                                scholarship.getSponsorUrl(),
                                "Not available"
                        )
        );

        contactLabel.setTextFill(
                Color.web("#655A70")
        );

        contactLabel.setFont(
                Font.font("Arial", 12)
        );

        contactLabel.setWrapText(true);

        Label applicationLabel = new Label(
                "Application: " +
                        valueOrDefault(
                                scholarship.getApplyUrl(),
                                "Not available"
                        )
        );

        applicationLabel.setTextFill(
                Color.web("#655A70")
        );

        applicationLabel.setFont(
                Font.font("Arial", 12)
        );

        applicationLabel.setWrapText(true);

        box.getChildren().addAll(
                organisationLabel,
                contactLabel,
                applicationLabel
        );

        section.getChildren().addAll(
                heading,
                box
        );

        return section;
    }

    private HBox createActionButtons() {
        HBox buttons = new HBox(12);

        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button contactButton = new Button(
                "Contact Organisation"
        );

        contactButton.setMinHeight(42);
        contactButton.setPrefHeight(42);

        contactButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: " + PURPLE + ";" +
                        "-fx-border-color: " + PURPLE + ";" +
                        "-fx-border-radius: 22;" +
                        "-fx-background-radius: 22;" +
                        "-fx-padding: 0 20 0 20;" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;"
        );

        contactButton.setOnAction(
                event ->
                        controller.handleContact(
                                scholarship.getSponsorUrl()
                        )
        );

        Button applyButton = new Button(
                "Apply Now"
        );

        applyButton.setMinHeight(42);
        applyButton.setPrefHeight(42);

        applyButton.setStyle(
                "-fx-background-color: " + PURPLE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 22;" +
                        "-fx-padding: 0 28 0 28;" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;"
        );

        applyButton.setOnAction(
                event ->
                        controller.handleApply(
                                scholarship
                        )
        );

        buttons.getChildren().addAll(
                contactButton,
                applyButton
        );

        return buttons;
    }

    private Label createSectionHeading(
            String text
    ) {
        Label heading = new Label(text);

        heading.setTextFill(
                Color.web(DARK_PURPLE)
        );

        heading.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        17
                )
        );

        return heading;
    }

    private String valueOrDefault(
            String value,
            String fallback
    ) {
        if (value == null || value.isBlank()) {
            return fallback;
        }

        return value;
    }

    public void show() {
        stage.showAndWait();
    }
}