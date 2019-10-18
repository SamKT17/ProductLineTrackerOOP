package product;

public class MoviePlayer extends Product implements MultimediaControl {

  private Screen screen;
  private MonitorType monitorType;

  public MoviePlayer(String name, String manufacturer,
       Screen screen, MonitorType monitorType) {
    super("VISUAL", manufacturer, name);
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
