package product;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.plaf.nimbus.State;

public class ProductController implements Initializable {
  /**
   * This method starts up at the start of the project.
   *
   * @param location -
   * @param resources -
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initializeCbo();
    initializeItemTypeChb();
    initializeProductListView();
  }

  @FXML private Button addProduct;

  @FXML private TextField productNameTextBox;

  @FXML private TextField manufacturerTextBox;

  @FXML private ChoiceBox<ItemType> itemTypeChoiceBox;

  @FXML private TableView<Product> tvProductTab;

  @FXML private TableColumn<?, ?> productTabTv;

  @FXML private TableColumn<?, ?> manufacturerTabTv;

  @FXML private TableColumn<?, ?> productTypeTabTv;

  @FXML private ListView<Product> chooseProductListView;

  @FXML private ComboBox<String> chooseQuantityComboBox;

  @FXML private Button recordProductionBtn;

  @FXML private TextArea productionLogTextBox;

  private ObservableList<Product> productLine;

  /**
   * This method handles when the add product button is pressed.
   *
   * @param event - this is the action of the mouse clicking
   */
  @FXML
  void addProductBtnClicked(MouseEvent event) {

    // this sets up the database object
    DatabaseHandler dbh = new DatabaseHandler();
    dbh.initializeDB();

    // gets the text from the text box
    String nameTB = productNameTextBox.getText();

    // gets the text from the text box
    String manufacturerTB = manufacturerTextBox.getText();

    // this gets the value from the text box
    ItemType itemType = itemTypeChoiceBox.getValue();

    String sql = "INSERT INTO PRODUCT (type, manufacturer, name) VALUES (?,?,?)";

    try { // insert.....('" + newProduct.getType() + "'.....
      PreparedStatement ps = dbh.conn.prepareStatement(sql);

      ps.setString(1, itemType.code);
      ps.setString(2, manufacturerTB);
      ps.setString(3, nameTB);

      ps.executeUpdate();
      ps.close();
      dbh.conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    populateTableView(nameTB, manufacturerTB, itemType);
  }

  /**
   * This method records production to the production log text box.
   *
   * @param event - this is the action of clicking the button.
   */
  @FXML
  void recordProduction(ActionEvent event) {

    Product info = chooseProductListView.getSelectionModel().getSelectedItem();

    String name = info.getName();
    String manufacturer = info.getManufacturer();
    ItemType item = info.getType();

    String sql = "SELECT ID FROM PRODUCT WHERE NAME = ? AND TYPE = ? AND MANUFACTURER = ? ";

    DatabaseHandler dbh = new DatabaseHandler();
    dbh.initializeDB();

    try {
      PreparedStatement ps = dbh.conn.prepareStatement(sql);

      ps.setString(1, name);
      ps.setString(2, item.code);
      ps.setString(3, manufacturer);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        int id = rs.getInt("ID");

        int numberOfProducts = Integer.parseInt(chooseQuantityComboBox.getValue());

        Product product = new Widget(name, manufacturer, item);

        int itemCount = 0;

        for (int i = 0; i < numberOfProducts; i++) {
          ProductionRecord pr = new ProductionRecord(product, itemCount++);

          ProductionRecord pr1 =
              new ProductionRecord(i + 1, id, pr.getSerialNum(), pr.getDate());

          productionLogTextBox.appendText(pr1.toString() + "\n");
        }
        ps.close();
        dbh.conn.close();
      }
      ps.close();
      dbh.conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  // insert.....('" + newProduct.getType() + "'.....

  // ArrayList<Product> productLine = new ArrayList<Product>(...);

  /**
   * This method displays the table view of what is being placed inside the text fields.
   *
   * @param name - name of the product
   * @param manufacturer - manufacturer of the product
   * @param itemType - what type of item it is
   */
  private void populateTableView(String name, String manufacturer, ItemType itemType) {
    productLine = FXCollections.observableArrayList();

    productTabTv.setCellValueFactory(new PropertyValueFactory<>("name"));
    manufacturerTabTv.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    productTypeTabTv.setCellValueFactory(new PropertyValueFactory<>("type"));

    tvProductTab.setItems(productLine);
    productLine.add(new Widget(name, manufacturer, itemType));
  }

  /** This method puts the values 1-10 into the choose quantity combobox. */
  private void initializeCbo() {
    ObservableList<String> options =
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    chooseQuantityComboBox.setItems(options);

    chooseQuantityComboBox.setEditable(true);
    chooseQuantityComboBox.getSelectionModel().selectFirst();
  }

  /** This method loads the choice box with what items there are. */
  private void initializeItemTypeChb() {
    for (ItemType it : ItemType.values()) {
      itemTypeChoiceBox.getItems().addAll(it);
    }
  }

  /** This method sets up the list with all the products that can be made. */
  private void initializeProductListView() {

    Widget product;

    DatabaseHandler dbh = new DatabaseHandler();
    dbh.initializeDB();

    String sql = "SELECT ID, NAME, TYPE, MANUFACTURER FROM PRODUCT";

    try {

      PreparedStatement ps;
      ps = dbh.conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String name = rs.getString("NAME");
        String type = rs.getString("TYPE");
        String manufacturer = rs.getString("MANUFACTURER");
        int id = rs.getInt("ID");

        ItemType it = null;

        switch (type) {
          case "AU":
            it = ItemType.AUDIO;
            break;
          case "VI":
            it = ItemType.VISUAL;
            break;
          case "AM":
            it = ItemType.AUDIO_MOBILE;
            break;
          case "VM":
            it = ItemType.VISUAL_MOBILE;
            break;
          default:
            System.out.println("There is not any item type picked.");
        }

        product = new Widget(name, manufacturer, it);

        chooseProductListView.getItems().add(product);
      }
      ps.close();
      dbh.conn.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
