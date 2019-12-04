package io.github.SamKT17;

public class Employee {

  private String name;
  private String username;
  private String password;
  private String email;

  Employee(String name, String password) {
    this.name = name;
    checkName(name);
    isValidPassword(password);
  }

  private void setUsername(String username) {

    String initial = username.substring(0, 1).toLowerCase();

    String[] fullName = name.split(" ");
    String lastName = fullName[1].toLowerCase();

    this.username = initial + lastName;
  }

  private boolean checkName(String name) {
    if (name.contains(" ")) {
      setEmail(name);
      setUsername(name);
      return true;
    } else {
      username = "default";
      email = "user@oracleacademy.Test";
      return false;
    }
  }

  private void setEmail(String name) {

    String[] fullName = name.split(" ");
    String firstName = fullName[0].toLowerCase();
    String lastName = fullName[1].toLowerCase();

    email = firstName + "." + lastName + "@oracleacademy.Test";
  }

  private boolean isValidPassword(String password) {
    String regex = "((?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{3,})";

    if(password.matches(regex)){
      this.password = password;
    } else {
      this.password = "pw";
    }
    return true;
  }

  @Override
  public String toString() {
    return "Employee Details\n"
        + "Name: "
        + name
        + "\n"
        + "Username: "
        + username
        + "\n"
        + "Email: "
        + email
        + "\n"
        + "Initial Password: "
        + password;
  }
}
