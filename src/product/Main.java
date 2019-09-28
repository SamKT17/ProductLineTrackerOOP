package product;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This applications is a production tracker.
 *
 *  @Author - SamKT17
 * */
public class Main extends Application {

  /**
   * The Start Methods.
   *
   * @param primaryStage The primary stage
   * @throws Exception Any problem
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("ProLineGUI.fxml"));
    primaryStage.setTitle("Product Line Tracker");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
  }



  public static void main(String[] args) {
    launch(args);
  }
}