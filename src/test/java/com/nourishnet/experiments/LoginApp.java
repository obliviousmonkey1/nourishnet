package com.nourishnet.experiments;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.nourishnet.DataStructures;
import com.nourishnet.ResourceLoader;
import com.nourishnet.SerializeJsonData;
import com.nourishnet.Tools;
import com.nourishnet.UserManager;
import com.nourishnet.User;

public class LoginApp extends Application {

    // Constants for styling
    private static final Font TITLE_FONT = new Font("Arial", 90);
    private static final String TITLE_COLOUR = "#81AB7F";  // Darker green
    private static final String BACKGROUND_COLOUR = "#AFC4B0";  // Lighter green
    private static final String PANEL_COLOUR = "#ACB7AB"; // Panel colour
    private static final String BUTTON_CLICKED_COLOUR = "rgba(129, 171, 127, 0.8)"; // Light green with 80% opacity
    private static final String BUTTON_DEFAULT_COLOUR = "#ACB7AB"; // Light blue
    private Button lastClickedButton;

    private User user;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Creating the main layout
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web(BACKGROUND_COLOUR), CornerRadii.EMPTY, Insets.EMPTY)));

        Label nourishnetLabel = new Label("NourishNet");
        nourishnetLabel.setFont(TITLE_FONT);
        nourishnetLabel.getStyleClass().add("label-nourishnet"); // Apply CSS class
        BorderPane.setAlignment(nourishnetLabel, javafx.geometry.Pos.CENTER);
        borderPane.setTop(nourishnetLabel);

        // Creating the left side (profiles)
        VBox profileBox = new VBox();
        profileBox.setSpacing(10);
        profileBox.setPadding(new Insets(20, 10, 20, 10)); // Reduced bottom padding to 20
        profileBox.setStyle("-fx-background-color: " + PANEL_COLOUR); // Set panel colour
        BorderPane.setMargin(profileBox, new Insets(10, 10, 10, 10)); // Margin to separate from the edge
        BorderPane.setAlignment(profileBox, Pos.TOP_LEFT); // Align to top left


        // Creating the bottom left invisible box
        Region invisibleBox = new Region();
        invisibleBox.setPrefSize(100, 100);
        borderPane.setBottom(invisibleBox);

        
        List<DataStructures.StringImageIdPair> profiles = UserManager.getUserProfiles();
        int maxButtons = Math.min(profiles.size(), 7); // Maximum 6 buttons
        for (int i = 0; i < maxButtons; i++) {
            DataStructures.StringImageIdPair profile = profiles.get(i);
            Button profileButton = new Button();
            VBox profileContent = new VBox(); // VBox to stack image and label

            // Convert ImageIcon to Image
            Image profileImage = convertToFXImage(profile.getImage());
            ImageView imageView = new ImageView(profileImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect

            // Profile name label
            Label nameLabel = new Label(profile.getText());
            nameLabel.getStyleClass().add("label-profile-name-text"); // Apply CSS class


            // Stack image and label vertically
            profileContent.getChildren().addAll(imageView, nameLabel);
            profileContent.setAlignment(javafx.geometry.Pos.CENTER); // Center content

            
            profileContent.getStyleClass().add("button-profile"); // Apply CSS class

            profileButton.setGraphic(profileContent);
            profileButton.getStyleClass().add("button-profile"); // Apply CSS class
            profileButton.setMaxHeight(Double.MAX_VALUE); // Make buttons take up full height

            profileButton.setOnAction(e -> {

                User tempUser = ResourceLoader.loadUser(UserManager.getUserJsonPath(profile.getId()));
                // Handle profile button click
                if (lastClickedButton != null) {
                    lastClickedButton.setStyle("-fx-background-color: " + BUTTON_DEFAULT_COLOUR); // Reset previous button color
                }
                lastClickedButton = profileButton;
                if (tempUser.getHasPassword()) {
                    System.out.println("Has Password");
                    displayPasswordField(primaryStage, true, tempUser);
                } else {
                    // No password required, directly perform login action
                    System.out.println("no password");

                    displayPasswordField(primaryStage, false, tempUser);

                }
                // Change button color on click
                profileButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");

                //profileButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR);
            });
            profileBox.getChildren().add(profileButton);
        }
        borderPane.setLeft(profileBox);

        Button createUserButton = new Button("Create New User");
        createUserButton.setOnAction(e -> {
            
            UserManager.clearTemporaryProfileImageHolder();

            createNewUserScreen(primaryStage);
        });

        // Add the create user button to the bottom of the BorderPane
        borderPane.setBottom(createUserButton);

    
        // Wrap the profileBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(profileBox);
        scrollPane.setFitToWidth(true); // Make sure the width fits the content
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar
        borderPane.setLeft(scrollPane);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Apply CSS file
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); // Set to full screen
        primaryStage.show();
    }


    private void createNewUserScreen(Stage primaryStage){

        BorderPane borderPane = (BorderPane) primaryStage.getScene().getRoot();
        borderPane.getChildren().clear();

        primaryStage.setTitle("Create User Profile");

        // Title Label
        Label titleLabel = new Label("NourishNet");
        titleLabel.getStyleClass().add("label-nourishnet");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(10));

        ImageIcon icon = new ImageIcon("/Users/parzavel/Documents/GitHub/nourishnet/Data/Users/default.png");
        Image profileImage = convertToFXImage(icon);
        ImageView imageView = new ImageView(profileImage);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect

        // Creating the center layout for user input fields
        VBox inputFieldsBox = new VBox(10);
        inputFieldsBox.setAlignment(Pos.CENTER);

        // Name Field
        HBox nameBox = new HBox(10);
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Date of Birth Fields
        Label dayLabel = new Label("Day:");
        Label monthLabel = new Label("Month:");
        Label yearLabel = new Label("Year:");

        // Day ComboBox
        ComboBox<String> dayComboBox = new ComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.getItems().add(String.valueOf(i));
        }

        // Month ComboBox
        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        // Year ComboBox
        ComboBox<String> yearComboBox = new ComboBox<>();
        for (int i = 1900; i <= 2024; i++) {
            yearComboBox.getItems().add(String.valueOf(i));
        }

        // Layout
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(dayLabel, dayComboBox, monthLabel, monthComboBox, yearLabel, yearComboBox);

        // Create sliders for weight and height
        Slider weightSlider = new Slider(40, 150, 75); // min, max, default
        Slider heightSlider = new Slider(120, 250, 170); // min, max, default

        // Set labels for weight and height sliders
        Label weightLabel = new Label("Weight: ");
        Label heightLabel = new Label("Height: ");

        // Set slider labels to display integer values
        weightSlider.setShowTickLabels(true);
        weightSlider.setMajorTickUnit(10);
        heightSlider.setShowTickLabels(true);
        heightSlider.setMajorTickUnit(10);

        // Set slider orientation to horizontal
        weightSlider.setOrientation(Orientation.HORIZONTAL);
        heightSlider.setOrientation(Orientation.HORIZONTAL);

        // Add sliders and labels to the layout
        VBox weightBox = new VBox(weightLabel, weightSlider);
        VBox heightBox = new VBox(heightLabel, heightSlider);

        // Method to set label color based on slider value
        weightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int intValue = (int) newVal.intValue();
            weightLabel.setText("Weight: " + intValue + " kg");
            setLabelColor(weightLabel, intValue);
        });

        heightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int intValue = (int) newVal.intValue();
            heightLabel.setText("Height: " + intValue + " cm");
        });

        // Upload Photo Button
        // Upload Photo Button
        Button uploadPhotoButton = new Button("Upload Photo");
        uploadPhotoButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an image file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                try {
                    System.out.println(selectedFile.toURI().toURL().toExternalForm());
                    Image image = new Image(selectedFile.toURI().toURL().toExternalForm());

                    try{
                        BufferedImage img = ImageIO.read(selectedFile);
                        Tools.loadImage("", img);
                        imageView.setImage(image); // Update the ImageView with the new image

                    } catch (IOException ex){
                        ex.printStackTrace();
                    }


                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        // Submit Button
        Button submitButton = new Button("Submit Information");
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String day = dayComboBox.getValue();
            String month = monthComboBox.getValue();
            String year = yearComboBox.getValue(); 
            int yearIndex = yearComboBox.getSelectionModel().getSelectedIndex();
            double weight = weightSlider.getValue();
            double height = heightSlider.getValue();

            // Create a label to display the submitted information
            Label submittedInfoLabel = new Label("Submitted Information:\n" +
                    "Name: " + name + "\n" +
                    "Date of Birth: " + day + " " + month + " " + year + "\n" +
                    "Weight: " + weight + " kg\n" +
                    "Height: " + height + " cm");
            
            // System.out.println("Submitted Information:\n" +
            // "Name: " + name + "\n" +
            // "Date of Birth: " + day + " " + month + " " + year + "\n" +
            // "Weight: " + weight + " kg\n" +
            // "Height: " + height + " cm");

            // Add the label to the layout
            inputFieldsBox.getChildren().add(submittedInfoLabel);
            
            User tempUser = new User();
            tempUser.setUsername(name);

            int intWeight = (int) weight; // Cast to int directly
            int intHeight = (int) height; // Cast to int directly
            tempUser.setWeight(intWeight);
            tempUser.setHeight(intHeight);

            System.out.println(yearIndex);

            int[] DOB = {Integer.parseInt(day), Integer.parseInt(month), yearIndex+1};
            tempUser.setDOB(DOB);

            SerializeJsonData.serializeNewUser(tempUser);
            UserManager.moveNewUserProfileToUserFolder(tempUser.getId());

            user = tempUser;

            displayMainScreen(primaryStage);


        });

        // Add all input fields and buttons to the layout
        inputFieldsBox.getChildren().addAll(imageView, nameBox, hbox, weightBox, heightBox, uploadPhotoButton, submitButton);

        // Error Label
        Label errorLabel = new Label("Please fill in all fields.");
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        // Set up the layout
        borderPane.setTop(titleLabel);
        borderPane.setCenter(inputFieldsBox);
        borderPane.setBottom(errorLabel);
        borderPane.setMargin(inputFieldsBox, new Insets(20));

        // Load CSS file
        String cssFile = getClass().getResource("styles.css").toExternalForm();
        borderPane.getStylesheets().add(cssFile);

        // Create scene and set it to the stage
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setLabelColor(Label label, int value) {
        label.getStyleClass().removeAll("red-label", "level-1", "level-2", "level-3");
    
        if (value > 90 && value <= 100) {
            label.getStyleClass().addAll("red-label", "level-1");
        } else if (value > 100 && value <= 110) {
            label.getStyleClass().addAll("red-label", "level-2");
        } else if (value > 110) {
            label.getStyleClass().addAll("red-label", "level-3");
        }
    }
    


    private void displayPasswordField(Stage primaryStage, boolean hasPassword, User tempUser) {


        // Creating the password field popup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); // Smaller padding
        grid.setVgap(10);
        grid.setHgap(10);
    
        // Background with transparency
        grid.setStyle("-fx-background-color: " + PANEL_COLOUR);
    
    
        BorderPane.setMargin(grid, new Insets(400)); // Smaller margin
    
        Label profileLabel = new Label("Profile: " + tempUser.getUsername());
        profileLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black;"); // Set font size and color

        GridPane.setConstraints(profileLabel, 0, 0);
        GridPane.setColumnSpan(profileLabel, 2); // Spanning across two columns
    
        if (hasPassword) {
            Label passwordLabel = new Label("Password:");
            passwordLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black;"); // Set text color
            GridPane.setConstraints(passwordLabel, 0, 1);
    
            PasswordField passwordInput = new PasswordField();
            passwordInput.setPrefWidth(400); // Increase password field width
            passwordInput.setPrefHeight(80); // Increase password field height
            GridPane.setConstraints(passwordInput, 1, 1);
    
            Button loginButton = new Button("Login");
            loginButton.setPrefWidth(200); // Increase button width
            loginButton.setPrefHeight(40); // Increase button height
            GridPane.setConstraints(loginButton, 1, 2);
            GridPane.setColumnSpan(loginButton, 2); // Spanning across two columns
            loginButton.setOnAction(e -> {
                // Handle login button click
                String password = passwordInput.getText();
                if(password.equals(tempUser.getPassword())){
                    user = tempUser;
                    displayMainScreen(primaryStage);
                }else{
                    passwordInput.setStyle("-fx-background-color: red");

                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Adjust duration as needed (e.g., 2 seconds)
                    pause.setOnFinished(event -> {
                        // Set the background color back to normal
                        passwordInput.setStyle("-fx-background-color: white"); // Assuming the default background color is white
                    });
                    pause.play(); // Start the pause transition
                }
            });
    
            grid.getChildren().addAll(profileLabel, passwordLabel, passwordInput, loginButton);
            ((BorderPane) primaryStage.getScene().getRoot()).setCenter(grid);
    
        } else {
            Button loginButton = new Button("Login");
            loginButton.setPrefWidth(200); // Increase button width
            loginButton.setPrefHeight(40); // Increase button height
            GridPane.setConstraints(loginButton, 2, 1);
            GridPane.setColumnSpan(loginButton, 2); // Spanning across two columns
            loginButton.setOnAction(e -> {
                // Handle login button click
                user = tempUser;                
                displayMainScreen(primaryStage);
            });
    
            grid.getChildren().addAll(profileLabel, loginButton);
            ((BorderPane) primaryStage.getScene().getRoot()).setCenter(grid);
    
        }
    }
    
    

    private Image convertToFXImage(javax.swing.ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private void displayMainScreen(Stage primaryStage) {
        // Clear the existing content of the BorderPane
        BorderPane borderPane = (BorderPane) primaryStage.getScene().getRoot();
        borderPane.getChildren().clear();
    
        // Create components for the mock main screen
        Label welcomeLabel = new Label("Welcome to the Main Screen!" + " " + user.getUsername());
        welcomeLabel.setFont(new Font(24));
        Button logoutButton = new Button("Logout");

        welcomeLabel.getStyleClass().add("label-welcome-text"); // Apply CSS class
    
        // Handle logout button click
        logoutButton.setOnAction(e -> {
            // Perform logout action
            // For now, let's just close the application
            animateClose(primaryStage);

            //primaryStage.close();
        });
    
        // Add components to the BorderPane
        VBox mainScreenContent = new VBox();
        mainScreenContent.getChildren().addAll(welcomeLabel, logoutButton);
        mainScreenContent.setAlignment(Pos.CENTER);
        borderPane.setCenter(mainScreenContent);
    }

    // Method to animate closing of the stage
    private void animateClose(Stage stage) {
        // Create a fade transition to gradually reduce the opacity of the stage
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Create a scale transition to gradually scale down the stage
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), stage.getScene().getRoot());
        scaleTransition.setToX(0.5);
        scaleTransition.setToY(0.5);

        // Sequentially play both transitions
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            scaleTransition.play();
            scaleTransition.setOnFinished(event1 -> stage.close());
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
