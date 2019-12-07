package io.github.samkt17;

/**
 * This class extends Product and Implements MultimediaControl and lets us access the device.
 *
 * author SamTK17
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private final String supportedAudioFormats;
  private final String supportedPlaylistFormats;

  AudioPlayer(
      String name,
      String manufacturer,
      String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }


  @Override
  public void play() {
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }

  @Override
  public String toString() {
    return super.toString() + "\n" + "Supported Audio Formats: " + supportedAudioFormats
        + "\n" + "Supported Playlist Formats: " + supportedPlaylistFormats;

  }
}
