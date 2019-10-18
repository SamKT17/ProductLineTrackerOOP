package product;

public class Screen implements ScreenSpec {
  private String resolution;
  private int refreshRate, responseTime;

  Screen(String resolution, int refreshRate, int responseTime){
    this.refreshRate = refreshRate;
    this.resolution = resolution;
    this.responseTime = responseTime;
  }

  public String toString() {
    return "Screen:" + "\n" + "Resolution: " + resolution + "\n" +
        "Refresh rate: " + refreshRate + "\n" + "Response time: " + responseTime;
  }

  @Override
  public String getResolution() {
    return this.resolution;
  }

  @Override
  public int getRefreshRate() {
    return 0;
  }

  @Override
  public int getResponseTime() {
    return 0;
  }
}
