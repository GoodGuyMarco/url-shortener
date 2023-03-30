package de.marco_bartelt.url_shortener;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService {

  @Autowired private ShortUrlRepository repository;

  public ShortUrl visit(ShortUrl url) {
    url.visit();
    repository.save(url);

    return url;
  }

  public ShortUrl create(String original, String description) {
    ShortUrl url = new ShortUrl(original, description);

    repository.save(url);

    return url;
  }

  public ShortUrl getById(String id) {
    Optional<ShortUrl> url = repository.findById(id);

    return url.orElse(null);
  }

  public ShortUrl getByShortId(String shortId) {
    Optional<ShortUrl> url = repository.findByShortId(shortId);

    return url.orElse(null);
  }

  public Iterable<ShortUrl> getAll() {
    return repository.findAll();
  }

  public ShortUrl delete(String id) {
    Optional<ShortUrl> url = repository.findById(id);

    if (url.isEmpty()) {
      return null;
    }

    ShortUrl instance = url.get();
    repository.delete(instance);

    return instance;
  }
}
