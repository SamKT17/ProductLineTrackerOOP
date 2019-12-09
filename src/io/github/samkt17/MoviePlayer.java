package io.github.samkt17;

/**
 * This class will be used to have access to the visual devices.
 * author SamTK17
 */
public class MoviePlayer extends Product implements MultimediaControl {

  private final Screen screen;
  private final MonitorType monitorType;

  /**
   *  This method is the constructor for the movie player class.
   *
   * @param name - name of the product
   * @param manufacturer - manufacturer of the product
   * @param screen - passes in the screen object
   * @param monitorType - passes in monitor object
   */
  public MoviePlayer(String name, String manufacturer,
       Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  @Override
  public String toString() {
    return super.toString() + screen + "\n" + "Monitor Type: " + monitorType + "\n";
  }

  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  @Override
  public void next() {
    System.out.println("Next movie");
  }
}
