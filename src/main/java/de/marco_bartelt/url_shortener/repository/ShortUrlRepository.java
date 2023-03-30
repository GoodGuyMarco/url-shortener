package de.marco_bartelt.url_shortener.repository;

import java.util.Optional;

import de.marco_bartelt.url_shortener.entity.ShortUrl;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, String> {

  Optional<ShortUrl> findByShortId(String shortId);
}
