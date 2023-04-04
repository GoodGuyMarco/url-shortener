package de.marco_bartelt.url_shortener.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateShortUrlRequest {
  @URL
  @Size(max = 255)
  public String original;

  @NotBlank
  @Size(max = 255)
  public String description;
}
