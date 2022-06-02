import java.io.Serializable;

@SuppressWarnings("unused")
public class Address implements Serializable {
  private int pin;
  private String street;
  private String city;
  private String state;

  public Address(int pin, String street, String city, String state) {
    this.pin = pin;
    this.street = street;
    this.city = city;
    this.state = state;
  }

  public int getPin() {
    return pin;
  }

  public void setPin(int pin) {
    this.pin = pin;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Address{" +
        "pin=" + pin +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        '}';
  }
}
