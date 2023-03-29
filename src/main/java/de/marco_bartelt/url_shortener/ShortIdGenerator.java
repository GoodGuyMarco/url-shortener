package de.marco_bartelt.url_shortener;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

// TODO: Chat GPT did this, lul.
public class ShortIdGenerator {
  private static final SecureRandom random = new SecureRandom();

  public static String generate() {
    long timestamp = new Date().getTime();
    byte[] randomBytes = new byte[6];
    random.nextBytes(randomBytes);
    byte[] data = new byte[10];
    data[0] = (byte) (timestamp >> 56);
    data[1] = (byte) (timestamp >> 48);
    data[2] = (byte) (timestamp >> 40);
    data[3] = (byte) (timestamp >> 32);
    data[4] = (byte) (timestamp >> 24);
    data[5] = (byte) (timestamp >> 16);
    data[6] = (byte) (timestamp >> 8);
    data[7] = (byte) timestamp;
    data[8] = randomBytes[0];
    data[9] = randomBytes[1];
    return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
  }
}
