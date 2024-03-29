package io.github.samkt17;

import java.util.Date;

/**
 * This class is used for creating a new production record.
 * author SamTK17
 */
class ProductionRecord {

  private int productionNumber;
  private int productID;
  private final String serialNumber;
  private Date date = new Date();

  /**
   * This method takes is the default constructor.
   *
   * @param productID - this is id of the product given from the database
   */
  public ProductionRecord(int productID) {
    productionNumber = 0;
    serialNumber = "0";
    date = new Date();
  }

  /**
   * This method is the main constructor for the production record.
   *
   * @param productionNumber - number of a certain item to be produced.
   * @param productID - the id from the database.
   * @param serialNumber - composed of a few things and comes from the 3rd constructor.
   * @param date - date of when the product is being produced.
   */
  ProductionRecord(int productionNumber, int productID, String serialNumber, Date date) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
  }

  /**
   * This method sets the serial number.
   *
   * @param product - passes in the product object.
   * @param countOfItemType - passes in the amount of the product object they want to produce.
   */
  ProductionRecord(Product product, int countOfItemType) {
    String serialNumberMan = product.getManufacturer().substring(0, 3);
    String serialNumberType = product.getType().code;
    String serialNumberCount = String.format("%05d", countOfItemType);

    serialNumber = serialNumberMan + serialNumberType + serialNumberCount;
  }

  String getSerialNum() {
    return serialNumber;
  }

  Date getDate() {
    return new Date();
  }


  @Override
  public String toString() {
    return "Prod. Num: "
        + productionNumber
        + " Product ID: "
        + productID
        + " Serial Num: "
        + serialNumber
        + " Date: "
        + date;
  }
}
