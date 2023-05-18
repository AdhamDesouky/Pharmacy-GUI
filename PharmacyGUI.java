package com.example.pharmacygui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;

public class PharmacyGUI extends Application {
    private final ArrayList<Drug> drugs = new ArrayList<>();
    private double totalSales;
    private int capacity;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AZ Pharmacy");

        // Create UI elements
        Label capacityLabel = new Label("Capacity:");
        TextField capacityTextField = new TextField();
        Button confirmCapacityButton = new Button("Confirm");
        Button addDrugButton = new Button("Add Drug");
        Button removeDrugButton = new Button("Remove Drug");
        Button placeOrderButton = new Button("Place Order");
        Button getTotalSalesButton = new Button("Get Total Sales");
        Button exitButton = new Button("Exit");
        TextArea outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(Font.font("Arial", 14));

        // Set button styles
        confirmCapacityButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        addDrugButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        removeDrugButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        placeOrderButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        getTotalSalesButton.setStyle("-fx-background-color: #673AB7; -fx-text-fill: white;");
        exitButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white;");

        // Set event handlers
        confirmCapacityButton.setOnAction(e -> {
            try {
                confirmCapacity(capacityTextField);
            } catch (Exception ex) {
                // Handle and display the exception
                outputTextArea.setText("Error: " + ex.getMessage());
            }
        });

        addDrugButton.setOnAction(e -> {
            try {
                addDrug();
            } catch (Exception ex) {
                // Handle and display the exception
                outputTextArea.setText("Error: " + ex.getMessage());
            }
        });

        removeDrugButton.setOnAction(e -> {
            try {
                removeDrug();
            } catch (Exception ex) {
                // Handle and display the exception
                outputTextArea.setText("Error: " + ex.getMessage());
            }
        });

        placeOrderButton.setOnAction(e -> {
            try {
                placeOrder();
            } catch (Exception ex) {
                // Handle and display the exception
                outputTextArea.setText("Error: " + ex.getMessage());
            }
        });

        getTotalSalesButton.setOnAction(e -> {
            try {
                getTotalSales(outputTextArea);
            } catch (Exception ex) {
                // Handle and display the exception
                outputTextArea.setText("Error: " + ex.getMessage());
            }
        });

        exitButton.setOnAction(e -> {
            try {
                exit();
            } catch (Exception ex) {
                // Handle and display the exception
                outputTextArea.setText("Error: " + ex.getMessage());
            }
        });

        // Layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(
                new HBox(10, capacityLabel, capacityTextField, confirmCapacityButton),
                new HBox(10, addDrugButton, removeDrugButton),
                new HBox(10, placeOrderButton, getTotalSalesButton, exitButton),
                outputTextArea
        );

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void confirmCapacity(TextField capacityTextField) {
        // Retrieve the capacity text entered by the user
        String capacityText = capacityTextField.getText();

        try {
            // Parse the capacity text as an integer
            capacity = Integer.parseInt(capacityText);

            // The parsing was successful, and the capacity value is now stored in the 'capacity' variable

            // Perform any required actions with the capacity value, such as updating the system or displaying a confirmation message

            // For example, let's display a confirmation message:
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Capacity Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("The capacity has been set to: " + capacity);
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // The parsing failed because the capacity text was not a valid integer

            // Handle invalid input by displaying an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Capacity");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid integer value for capacity.");
            alert.showAndWait();
        } catch (Exception e) {
            // Handle any other exceptions that may occur during the processing
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }


    private void addDrug() {
        // Check if there is available capacity for adding a new drug
        if (drugs.size() >= capacity) {
            // Display an error message if the pharmacy has reached its capacity
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Capacity Exceeded");
            alert.setHeaderText(null);
            alert.setContentText("The pharmacy has reached its capacity for adding new drugs.");
            alert.showAndWait();
            return;
        }

        // Create a dialog for adding a new drug
        Dialog<Drug> dialog = new Dialog<>();
        dialog.setTitle("Add Drug");

        // Set the button types (OK and Cancel)
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the drug ID, name, price, and quantity fields
        TextField idTextField = new TextField();
        TextField nameTextField = new TextField();
        TextField priceTextField = new TextField();
        TextField quantityTextField = new TextField();
        ComboBox<Drug.Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(Drug.Category.values());

        // Set the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idTextField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameTextField, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceTextField, 1, 2);
        grid.add(new Label("Quantity:"), 0, 3);
        grid.add(quantityTextField, 1, 3);
        grid.add(new Label("Category:"), 0, 4);
        grid.add(categoryComboBox, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the drug ID field by default
        Platform.runLater(idTextField::requestFocus);

        // Convert the result to a drug object when the Add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    // Parse the input values and create a new Drug object
                    int id = Integer.parseInt(idTextField.getText());
                    String name = nameTextField.getText();
                    double price = Double.parseDouble(priceTextField.getText());
                    int quantity = Integer.parseInt(quantityTextField.getText());
                    Drug.Category category = categoryComboBox.getValue();

                    return new Drug(name, id, price, category, quantity);
                } catch (NumberFormatException e) {
                    // Display an error message if the input is invalid
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter valid numeric values for ID, price, and quantity.");
                    alert.showAndWait();
                }
            }
            return null;
        });

        // Show the dialog and process the result
        dialog.showAndWait().ifPresent(drug -> {
            // Add the drug to the drugs list
            drugs.add(drug);

            // Display a confirmation message or perform any other required actions
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Drug Added");
            alert.setHeaderText(null);
            alert.setContentText("Drug '" + drug.getName() + "' added successfully!");
            alert.showAndWait();
        });
    }


    private void removeDrug() {
        // Create a dialog for selecting a drug to remove
        Dialog<Drug> dialog = new Dialog<>();
        dialog.setTitle("Remove Drug");

        // Set the button types (OK and Cancel)
        ButtonType removeButton = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButton, ButtonType.CANCEL);

        // Create a TextField for entering the drug ID
        TextField idTextField = new TextField();

        // Set the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.add(new Label("Enter Drug ID:"), 0, 0);
        grid.add(idTextField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the drug ID TextField by default
        Platform.runLater(idTextField::requestFocus);

        // Convert the result to a drug object when the Remove button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeButton) {
                try {
                    // Parse the input ID and find the corresponding drug
                    int id = Integer.parseInt(idTextField.getText());
                    for (Drug drug : drugs) {
                        if (drug.getId() == id) {
                            return drug;
                        }
                    }

                    // If no matching drug is found, display an error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Drug Not Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No drug with ID " + id + " found.");
                    alert.showAndWait();
                } catch (NumberFormatException e) {
                    // Display an error message if the input is invalid
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid numeric value for Drug ID.");
                    alert.showAndWait();
                }
            }
            return null;
        });

        // Show the dialog and process the result
        dialog.showAndWait().ifPresent(drug -> {
            // Remove the selected drug from the drugs list
            drugs.remove(drug);

            // Display a confirmation message or perform any other required actions
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Drug Removed");
            alert.setHeaderText(null);
            alert.setContentText("Drug '" + drug.getName() + "' removed successfully!");
            alert.showAndWait();
        });
    }

    private void placeOrder() {
        // Create a dialog for placing the order
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Place Order");

        // Set the button types (Place Order and Cancel)
        ButtonType placeOrderButton = new ButtonType("Place Order", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(placeOrderButton, ButtonType.CANCEL);

        // Create a ComboBox for selecting the drug ID
        ComboBox<Integer> idComboBox = new ComboBox<>();

        // Add the drug IDs to the ComboBox
        for (Drug drug : drugs) {
            idComboBox.getItems().add(drug.getId());
        }

        // Create a Spinner for selecting the quantity
        Spinner<Integer> quantitySpinner = new Spinner<>(1, Integer.MAX_VALUE, 1);

        // Disable the Place Order button if the drug or quantity is not selected
        Node placeOrderButtonNode = dialog.getDialogPane().lookupButton(placeOrderButton);
        placeOrderButtonNode.setDisable(true);

        // Validate if the drug and quantity are selected before enabling the Place Order button
        idComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                placeOrderButtonNode.setDisable(newValue == null || quantitySpinner.getValue() == null));

        quantitySpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                placeOrderButtonNode.setDisable(newValue == null || idComboBox.getValue() == null));

        // Set the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.add(new Label("Select Drug ID:"), 0, 0);
        grid.add(idComboBox, 1, 0);
        grid.add(new Label("Enter Quantity:"), 0, 1);
        grid.add(quantitySpinner, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the drug ComboBox by default
        Platform.runLater(idComboBox::requestFocus);

        // Convert the result to the selected quantity when the Place Order button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == placeOrderButton) {
                return quantitySpinner.getValue();
            }
            return null;
        });

        // Show the dialog and process the result
        dialog.showAndWait().ifPresent(quantity -> {
            Integer selectedId = idComboBox.getValue();
            if (selectedId != null) {
                // Find the drug with the selected ID
                Drug selectedDrug = null;
                for (Drug drug : drugs) {
                    if (drug.getId() == selectedId) {
                        selectedDrug = drug;
                        break;
                    }
                }

                if (selectedDrug != null) {
                    String drugFoundMessage = "Drug found: " + selectedDrug.getName();
                    showAlert(Alert.AlertType.INFORMATION, "Drug Found", drugFoundMessage);

                    double price = selectedDrug.getPrice();
                    boolean prescription = (selectedDrug.getCategory() == Drug.Category.PRESCRIPTION);

                    if (prescription) {
                        // Prompt the user for a prescription confirmation
                        Alert prescriptionAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        prescriptionAlert.setTitle("Prescription Required");
                        prescriptionAlert.setHeaderText("This drug requires a prescription.");
                        prescriptionAlert.setContentText("Do you have a prescription?");
                        Optional<ButtonType> result = prescriptionAlert.showAndWait();
                        if (result.isPresent() && result.get() != ButtonType.OK) {
                            // Show a message indicating the prescription is required
                            showAlert(Alert.AlertType.INFORMATION, "Prescription Required",
                                    "Kindly, a prescription is required to buy this drug.");
                            return;
                        }
                    }

                    if (selectedDrug.getCategory() == Drug.Category.COSMETICS) {
                        // Apply a 20% markup for cosmetic products
                        price *= 1.2;
                    }

                    double total = price * quantity;
                    if (quantity <= selectedDrug.getQuantity()) {
                        // Update the drug quantity and total sales
                        selectedDrug.setQuantity(selectedDrug.getQuantity() - quantity);
                        totalSales += total;

                        String orderPlacedMessage = "Order placed successfully.\nTotal amount: " + total;
                        showAlert(Alert.AlertType.INFORMATION, "Order Placed", orderPlacedMessage);
                    } else {
                        // Show an error dialog if the requested quantity is not available
                        showAlert(Alert.AlertType.ERROR, "Insufficient Quantity",
                                "Insufficient quantity available for the selected drug.");
                    }
                } else {
                    // Show an error dialog if the selected drug is not found
                    showAlert(Alert.AlertType.ERROR, "Drug Not Found",
                            "No drug with ID " + selectedId + " found.");
                }
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    private void getTotalSales(TextArea outputTextArea) {
        double dailySales = calculateTotalDailySales();
        //totalSales += dailySales;

        outputTextArea.appendText("Total Sales for the Day: $" + dailySales + "\n");
        //outputTextArea.appendText("Total Sales Overall: $" + totalSales + "\n");
    }

    private double calculateTotalDailySales() {
        double dailySales = 0.0;
        for (Drug drug : drugs) {
            double drugSales = drug.getPrice() * drug.getQuantity();
            dailySales += drugSales;
        }
        return dailySales;
    }


    private void exit() {
        Platform.exit();
    }


    public static class Drug {
        private final String name;
        private final int id;
        private final double price;
        private final Category category;
        private int quantity;

        public Drug(String name, int id, double price, Category category, int quantity) {
            this.name = name;
            this.id = id;
            this.price = price;
            this.category = category;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        public Category getCategory() {
            return category;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public enum Category {
            COSMETICS, PRESCRIPTION, OTHER
        }
    }
}