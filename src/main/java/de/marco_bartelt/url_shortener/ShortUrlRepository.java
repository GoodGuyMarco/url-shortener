package de.marco_bartelt.url_shortener;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, String> {

  Optional<ShortUrl> findByShortId(String shortId);
}
