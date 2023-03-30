package de.marco_bartelt.url_shortener;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
@RequestMapping("/url")
public class ShortUrlController {

  private final ShortUrlService service;

  public ShortUrlController(ShortUrlService service) {
    this.service = service;
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@Valid @RequestBody CreateShortUrlRequest body) {
    service.create(body.original, body.description);
  }

  @GetMapping("/{id}")
  public ShortUrl get(@PathVariable String id) {
    ShortUrl url = service.getById(id);

    if (url == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, String.format("Short URL by ID '%s' not found", id));
    }

    return url;
  }

  @GetMapping("/")
  public Iterable<ShortUrl> list() {
    return service.getAll();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    ShortUrl url = service.delete(id);

    if (url == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, String.format("Short URL by ID '%s' not found", id));
    }
  }
}
