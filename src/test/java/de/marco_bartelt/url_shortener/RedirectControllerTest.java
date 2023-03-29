package de.marco_bartelt.url_shortener;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RedirectController.class)
public class RedirectControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ShortUrlRepository repository;

  @Test
  public void testRedirect() throws Exception {
    ShortUrl url = new ShortUrl();
    url.setOriginal("https://marco-bartelt.de");
    url.setShortId("short_id");
    url.setClickCount(0);

    when(repository.findByShortId("short_id")).thenReturn(Optional.of(url));

    mockMvc
        .perform(get("/short_id"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("https://marco-bartelt.de"));
  }

  @Test
  public void testNotFound() throws Exception {
    when(repository.findByShortId("not_existing_short_id")).thenReturn(Optional.empty());

    mockMvc.perform(get("/not_existing_short_id")).andExpect(status().isNotFound());
  }
}
