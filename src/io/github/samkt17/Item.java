package io.github.samkt17;

/**
 * This interface is the contract for the product class.
 * author SamTK17
 */
interface Item {

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();
}
