package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Try extends Application {

    @Override
    public void start(Stage primaryStage) {
//        // Create a progress bar
//    	VBox but = new VBox();
//        ProgressBar progressBar = new ProgressBar();
//        // Set the initial progress value (0.0 to 1.0)
//        progressBar.setProgress(0.0);
//
//        // Create a layout and add the progress bar
//        VBox vbox = new VBox(progressBar);
//        vbox.getChildren().add(but);
//
//        // Create a scene and set it on the stage
//        Scene scene = new Scene(vbox, 200, 200);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Progress Bar Example");
//        primaryStage.show();
//
//        // Simulate progress by updating the progress value
//        Thread thread = new Thread(() -> {
//            try {
//                for (double progress = 0.0; progress <= 1.0; progress += 0.1) {
//                    // Update the progress value on the JavaFX application thread
//                    double finalProgress = progress;
//                    javafx.application.Platform.runLater(() -> progressBar.setProgress(finalProgress));
//                    Thread.sleep(1000); // Sleep for 1 second
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        // Start the progress simulation thread
//        thread.start();
    	 VBox vbox = new VBox();
         
         // Apply the CSS style to the VBox and its children
         
         // Create child nodes with labels
         Label label1 = new Label("Label 1");
         Label label2 = new Label("Label 2");
         
         vbox.getChildren().addAll(label1, label2);
         
         Scene scene = new Scene(vbox, 200, 200);
         primaryStage.setScene(scene);
         primaryStage.show();
	      
    	
    }

    public static void main(String[] args) {
        launch(args);
    }
}

