package de.marco_bartelt.url_shortener;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrl, String> {

  Optional<ShortenedUrl> findByShortId(String shortId);
}
