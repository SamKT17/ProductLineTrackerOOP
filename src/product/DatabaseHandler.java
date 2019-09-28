package product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/ProductionDB";

  //  Database credentials
  private static final String USER = "";
  private static final String PASS = "";
  private Connection conn;
  Statement stmt;

  /**
   * This method registers, connects and create a statement used to access the database.
   */
  public void initializeDB() {
    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // STEP 3: Execute a query
      stmt = conn.createStatement();

    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
