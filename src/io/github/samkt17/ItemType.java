package io.github.samkt17;

/**
 * ItemType enum is used to help us keep track of the different types of devices we have.
 *
 * @author - samthomas
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  public final String code;

  ItemType(String code) {
    this.code = code;
  }
}
