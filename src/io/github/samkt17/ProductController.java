package io.github.samkt17;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * This class is the main controller for the project and hold the logic for the GUI.
 *
 * author SamTK17
 */
public class ProductController implements Initializable {
  /**
   * This method starts up at the start of the project.
   *
   * @param location - the location used to resolve relative paths for the root object
   * @param resources - The resources used to localize the root object
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // initializes the combo box for the quantity
    initializeCbo();

    // initializes the the item type choice box
    initializeItemTypeChb();

    // initializes the previously created Products
    initializeProductListView();

    // This sets up the table view of all the products
    initializeTableView();

    // This sets-up the production log
    initializeProductionLog();
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

  @FXML private Label errorTextProduct;

  @FXML private Label errorTextProduce;

  private ObservableList<Product> productLine;

  /**
   * This method handles when the add product button is pressed.
   *
   * @param event - this is the action of the mouse clicking
   */
  @FXML
  void addProductBtnClicked(MouseEvent event) {

    errorTextProduct.setText("");

    // this sets up the database object
    DatabaseHandler dbh = new DatabaseHandler();
    try {
      dbh.initializeDB();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // gets the text from the text box
    String nameTB = productNameTextBox.getText();

    // gets the text from the text box
    String manufacturerTB = manufacturerTextBox.getText();

    // this gets the value from the text box
    ItemType itemType = itemTypeChoiceBox.getValue();

    String sql = "INSERT INTO PRODUCT (type, manufacturer, name) VALUES (?,?,?)";

    if (!productNameTextBox.getText().isEmpty()) {
      if (!manufacturerTextBox.getText().isEmpty()) {
        try {
          PreparedStatement ps = dbh.conn.prepareStatement(sql);

          ps.setString(1, itemType.code);
          ps.setString(2, manufacturerTB);
          ps.setString(3, nameTB);

          ps.executeUpdate();
          // dbh.conn.close();
          ps.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        populateTableView(nameTB, manufacturerTB, itemType);

        // this uses polymorphism and creates an object
        Product product = new Widget(nameTB, manufacturerTB, itemType);

        chooseProductListView.getItems().addAll(product);

        // this is the error handling and prevents an error to be thrown
      } else {
        errorTextProduct.setText("Manufacturer Field is empty.");
      }
    } else {
      errorTextProduct.setText("Product Name is empty.");
    }
  }

  /**
   * This method records production to the production log text box.
   *
   * @param event - this is the action of clicking the button.
   */
  @FXML
  void recordProduction(ActionEvent event) {

    errorTextProduce.setText("");

    // this gets the selected product from the list view
    Product info = chooseProductListView.getSelectionModel().getSelectedItem();

    String sql = "SELECT ID FROM PRODUCT WHERE NAME = ? AND TYPE = ? AND MANUFACTURER = ? ";

    DatabaseHandler dbh = new DatabaseHandler();
    try {
      dbh.initializeDB();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    // if there was nothing selected in the list view and the quantity is between 1-10
    if (!chooseProductListView.getSelectionModel().isEmpty()) {
      if (Integer.parseInt(chooseQuantityComboBox.getValue()) <= 10) {
        if (Integer.parseInt(chooseQuantityComboBox.getValue()) >= 1) {
          String name = info.getName();
          String manufacturer = info.getManufacturer();
          ItemType item = info.getType();

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

              for (int i = 0; i < numberOfProducts; i++) {

                // this is for getting/creating the serial number for the product
                ProductionRecord serial = new ProductionRecord(product, getProductionQuantity(id));

                ProductionRecord pr1 =
                    new ProductionRecord(
                        getProductionQuantity(id), id, serial.getSerialNum(), serial.getDate());

                // this sets the text of the production log
                productionLogTextBox.appendText(pr1.toString() + "\n");

                String sqlAdd =
                    "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM,"
                        + "DATE_PRODUCED) VALUES (?,?,?,?)";

                // used this to be able to store a java date to the database
                Timestamp ts = new Timestamp(new Date().getTime());

                try {
                  PreparedStatement ps2 = dbh.conn.prepareStatement(sqlAdd);
                  ps2.setInt(1, getProductionQuantity(id));
                  ps2.setInt(2, rs.getInt("ID"));
                  ps2.setString(3, serial.getSerialNum());
                  ps2.setTimestamp(4, ts);

                  ps2.executeUpdate();
                  ps2.close();
                } catch (SQLException e) {
                  e.printStackTrace();
                }
              }
            }
            ps.close();
            dbh.conn.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        } else {
          errorTextProduce.setText("Select a value between 1-10.");
        }
      } else {
        errorTextProduce.setText("Select a value between 1-10.");
      }
    } else {
      errorTextProduce.setText("Select a Product to Produce.");
    }
  }

  /**
   * This method displays the table view of what is being placed inside the text fields.
   *
   * @param name - name of the product
   * @param manufacturer - manufacturer of the product
   * @param itemType - what type of item it is
   */
  private void populateTableView(String name, String manufacturer, ItemType itemType) {
    productLine = FXCollections.observableArrayList();

    productLine.add(new Widget(name, manufacturer, itemType));

    tvProductTab.getItems().add(new Widget(name, manufacturer, itemType));
  }

  /** This method puts the values 1-10 into the choose quantity combobox. */
  private void initializeCbo() {
    // for combo boxes it needs an Observable List
    ObservableList<String> options =
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    chooseQuantityComboBox.setItems(options);

    // makes the combo box editable and it selects the first value
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
    try {
      dbh.initializeDB();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    String sql = "SELECT NAME, TYPE, MANUFACTURER FROM PRODUCT";

    try {

      Statement stmt = dbh.conn.createStatement();

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        String name = rs.getString("NAME");
        String type = rs.getString("TYPE");
        String manufacturer = rs.getString("MANUFACTURER");
        // int id = rs.getInt("ID");

        ItemType it = null;

        // I had to do this to switch it to an item type
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

        // this adds everything to the list view that was taken from the database
        chooseProductListView.getItems().addAll(product);
      }
      stmt.close();
      dbh.conn.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /** This method initializes the table view at the start of the program. */
  private void initializeTableView() {

    productLine = FXCollections.observableArrayList();

    DatabaseHandler dbh = new DatabaseHandler();
    try {
      dbh.initializeDB();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    String sql = "SELECT NAME, TYPE, MANUFACTURER FROM PRODUCT";

    try {

      Statement stmt = dbh.conn.createStatement();

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        String name = rs.getString("NAME");
        String type = rs.getString("TYPE");
        String manufacturer = rs.getString("MANUFACTURER");

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

        tvProductTab.setItems(productLine);
        productLine.add(new Widget(name, manufacturer, it));
      }

      productTabTv.setCellValueFactory(new PropertyValueFactory<>("name"));
      manufacturerTabTv.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
      productTypeTabTv.setCellValueFactory(new PropertyValueFactory<>("type"));

      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /** This method initializes the Production log for the previous products that were made. */
  private void initializeProductionLog() {
    String sql = "SELECT * FROM PRODUCTIONRECORD";

    DatabaseHandler dbh = new DatabaseHandler();
    try {
      dbh.initializeDB();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try {
      Statement stmt = dbh.conn.createStatement();

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {

        String prodNum = rs.getString("PRODUCTION_NUM");
        String prodId = rs.getString("PRODUCT_ID");
        String serialNum = rs.getString("SERIAL_NUM");
        Timestamp dateProduced = rs.getTimestamp("DATE_PRODUCED");

        ProductionRecord pr =
            new ProductionRecord(
                Integer.parseInt(prodNum), Integer.parseInt(prodId), serialNum, dateProduced);

        productionLogTextBox.appendText(pr.toString() + "\n");
      }
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is for getting the highest production number and adding one to it for the database.
   *
   * @param id - this is product id this is being added to the database
   * @return - this returns product number + 1
   */
  private static int getProductionQuantity(int id) {

    // this gets the max value in the column
    String sql = "SELECT MAX(PRODUCTION_NUM) FROM PRODUCTIONRECORD WHERE PRODUCT_ID = ?";

    DatabaseHandler dbh = new DatabaseHandler();
    try {
      dbh.initializeDB();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try {

      PreparedStatement ps = dbh.conn.prepareStatement(sql);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      // if a record exists
      if (rs.next()) {

        int proNum = rs.getInt(1);

        ps.close();
        dbh.conn.close();

        // adds one to the highest value so if i already produced 3 ipods then
        // the next time i produce an ipod it will make it the fourth ipod produced
        return proNum + 1;

        // this is if there are not any existing production records on the productr
      } else {
        ps.close();
        dbh.conn.close();
        return 1;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
