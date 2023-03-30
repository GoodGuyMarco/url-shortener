package de.marco_bartelt.url_shortener.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class ShortIdGenerator {

  public static String generate() {
    return NanoIdUtils.randomNanoId(
        NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 6);
  }
}
