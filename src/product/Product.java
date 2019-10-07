package product;

public abstract class Product implements Item {
  private int id;
  private String type;
  private String manufacturer;
  private String name;

  Product(String type, String manufacturer, String name) {
    this.type = type;
    this.manufacturer = manufacturer;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type;
  }
}

class Widget extends Product {

  Widget(String type, String manufacturer, String name) {
    super(type, manufacturer, name);
  }
}
// insert.....('" + newProduct.getType() + "'.....

// ArrayList<Product> productLine = new ArrayList<Product>(...);
