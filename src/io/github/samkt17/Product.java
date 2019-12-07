package io.github.samkt17;

/**
 * This class creates all the products.
 *
 * author SamTK17
 */
public abstract class Product implements Item {
  private int id;
  private ItemType type;
  private String manufacturer;
  private String name;

  Product(String name, String manufacturer, ItemType type) {
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

  public void setType(ItemType type) {
    this.type = type;
  }

  public ItemType getType() {
    return type;
  }

  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type;
  }
}

/**
 * The widget class extends the product class and it is what we use to access the product class.
 *
 * author SamTK17
 */
class Widget extends Product {

  Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
  }
}
