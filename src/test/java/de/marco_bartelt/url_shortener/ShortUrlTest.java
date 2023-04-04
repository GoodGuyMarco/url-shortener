package de.marco_bartelt.url_shortener;

import de.marco_bartelt.url_shortener.entity.ShortUrl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortUrlTest {

  @Test
  public void testVisit() {
    ShortUrl url = new ShortUrl();
    Assertions.assertThat(url.getClickCount()).isEqualTo(0);

    url.visit();
    Assertions.assertThat(url.getClickCount()).isEqualTo(1);
  }
}
