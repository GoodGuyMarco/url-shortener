package de.marco_bartelt.url_shortener.controller;

import java.net.URI;

import de.marco_bartelt.url_shortener.entity.ShortUrl;
import de.marco_bartelt.url_shortener.service.ShortUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
public class RedirectController {

  private final ShortUrlService service;

  public RedirectController(ShortUrlService service) {
    this.service = service;
  }

  @GetMapping("/{shortId}")
  public ResponseEntity<Object> redirect(@PathVariable String shortId) {
    ShortUrl url = service.getByShortId(shortId);

    if (url == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, String.format("URL by short ID '%s' not found", shortId));
    }

    service.visit(url);

    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/302
    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getOriginal())).build();
  }
}
