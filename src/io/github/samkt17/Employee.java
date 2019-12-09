package io.github.samkt17;

/**
 * The Employee class creates employee objects to store into the database.
 * author SamTK17
 */
class Employee {

  private final String name;
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

  private void checkName(String name) {
    if (name.contains(" ")) {
      setEmail(name);
      setUsername(name);
    } else {
      username = "default";
      email = "user@oracleacademy.Test";
    }
  }

  private void setEmail(String name) {

    String[] fullName = name.split(" ");
    String firstName = fullName[0].toLowerCase();
    String lastName = fullName[1].toLowerCase();

    email = firstName + "." + lastName + "@oracleacademy.Test";
  }

  private void isValidPassword(String password) {
    String regex = "((?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{3,})";

    if(password.matches(regex)){
      this.password = password;
    } else {
      this.password = "pw";
    }
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
