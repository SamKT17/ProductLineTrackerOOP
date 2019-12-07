package io.github.samkt17;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class connects us to the database.
 *
 * @author - samthomas
 */
class DatabaseHandler {

  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/ProductionDB";

  // Database credentials
  private static final String USER = "";
  Connection conn;

  /** This method registers, connects and create a statement used to access the database. */
  public void initializeDB() throws FileNotFoundException {

    Scanner sc = new Scanner(new File("Properties.txt"));
    final java.lang.String PASS = sc.nextLine();

    try {
      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
