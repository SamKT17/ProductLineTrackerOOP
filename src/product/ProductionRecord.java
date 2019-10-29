package product;

import java.util.Date;

public class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date date = new Date();

  public ProductionRecord(int productID) {
    productionNumber = 0;
    serialNumber = "0";
    date = new Date();
  }

  public ProductionRecord(int productionNumber, int productID, String serialNumber, Date date) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.date = date;
  }

  public ProductionRecord(Product product, int countOfItemType) {
    String sNum1 = product.getManufacturer().substring(0,3);
    String sNum2 = product.getItemType().code;
    String sNum3 = String.format("%05d", countOfItemType);

    serialNumber = sNum1 + sNum2 + sNum3;
  }

  public int getProductionNum() {
    return productionNumber;
  }

  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public String getSerialNum() {



    return serialNumber;
  }

  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Date getProdDate() {
    return date;
  }

  public void setProdDate(Date date) {
    this.date = date;
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
