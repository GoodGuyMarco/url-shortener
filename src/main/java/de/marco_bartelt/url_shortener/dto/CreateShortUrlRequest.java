package de.marco_bartelt.url_shortener.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateShortUrlRequest {

  @URL
  @Max(255)
  public String original;

  @NotBlank
  @Max(255)
  public String description;
}
