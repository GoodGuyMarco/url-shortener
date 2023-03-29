package de.marco_bartelt.url_shortener;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateShortenedUrlRequest {
  @URL public String original;

  @NotBlank public String description;
}
