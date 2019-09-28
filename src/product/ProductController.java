package product;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProductController implements Initializable {
  /**
   * This method starts up at the start of the project.
   *
   * @param location -
   * @param resources -
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    populateExistingProTA();
    initializeCbo();
  }

  @FXML
  private Button addProduct;

  @FXML
  private TextField productNameTextBox;

  @FXML
  private TextField manufacturerTextBox;

  @FXML
  private ChoiceBox<?> itemTypeComboBox;

  @FXML
  private TextArea existingProductsTextArea;

  @FXML
  private TextField chooseProductTextBox;

  @FXML
  private ComboBox<String> chooseQuantityComboBox;

  @FXML
  private Button recordProductionBtn;

  @FXML
  private TextArea productionLogTextBox;

  /**
   * This method handles when the add product button is pressed.
   *
   * @param event - this is the action of the mouse clicking
   */
  @FXML
  void addProductBtnClicked(MouseEvent event) {

    DatabaseHandler dbh = new DatabaseHandler();
    dbh.initializeDB();

    try {
      String sql = "INSERT INTO PRODUCT (type, manufacturer, name) "
          + "VALUES ('AUDIO', 'Apple', 'iPod')";
      dbh.stmt.executeUpdate(sql);
      dbh.stmt.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method and the data from existing products to the existing text area.
   */
  private void populateExistingProTA() {
    DatabaseHandler dbh = new DatabaseHandler();
    dbh.initializeDB();

    try {
      String sql = "SELECT * FROM PRODUCT";
      ResultSet rs = dbh.stmt.executeQuery(sql);

      while (rs.next()) {
        for (int i = 1; i <= 4; i++) {
          existingProductsTextArea.appendText(rs.getString(i) + "\t");
        }
        existingProductsTextArea.appendText("\n");
      }
      dbh.stmt.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method puts the values 1-10 into the choose quantity combobox.
   */
  private void initializeCbo() {
    ObservableList<String> options =
        FXCollections.observableArrayList(
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10"
        );
    chooseQuantityComboBox.setItems(options);

    chooseQuantityComboBox.setEditable(true);
    chooseQuantityComboBox.getSelectionModel().selectFirst();
  }

}


