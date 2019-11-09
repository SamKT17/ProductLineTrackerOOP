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
  Connection conn;

  /** This method registers, connects and create a statement used to access the database. */
  public void initializeDB() {
    try {
      Class.forName(JDBC_DRIVER);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
