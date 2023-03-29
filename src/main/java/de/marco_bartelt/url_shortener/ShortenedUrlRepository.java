package de.marco_bartelt.url_shortener;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrl, String> {

    Optional<ShortenedUrl> findByShortId(String shortId);
}