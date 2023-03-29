package de.marco_bartelt.url_shortener;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
@RequestMapping("/url")
public class UrlShortenerController {

  @Autowired private ShortenedUrlRepository repository;

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@Valid @RequestBody CreateShortenedUrlRequest body) {
    ShortenedUrl url = new ShortenedUrl(body.original, body.description);

    repository.save(url);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    Optional<ShortenedUrl> url = repository.findById(id);

    if (url.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, String.format("Shortened URL by ID '%s' not found", id));
    }

    repository.delete(url.get());
  }

  @GetMapping("/{id}")
  public ShortenedUrl get(@PathVariable String id) {
    Optional<ShortenedUrl> url = repository.findById(id);

    if (url.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, String.format("Shortened URL by ID '%s' not found", id));
    }

    return url.get();
  }

  @GetMapping("/")
  public Iterable<ShortenedUrl> list() {
    return repository.findAll();
  }
}
