package de.marco_bartelt.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;

@RestController()
public class RedirectController {

    @Autowired
    private ShortenedUrlRepository repository;

    @GetMapping("/{shortId}")
    public ResponseEntity<Object> redirect(@PathVariable String shortId) {
        Optional<ShortenedUrl> url = repository.findByShortId(shortId);

        if (url.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Shortened URL by ID '%s' not found", shortId)
            );
        }

        ShortenedUrl instance = url.get();
        instance.setClickCount(instance.getClickCount() + 1);
        repository.save(instance);

        // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/302
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url.get().getOriginal()))
                .build();
    }
}
