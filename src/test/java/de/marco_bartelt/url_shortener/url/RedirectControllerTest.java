package de.marco_bartelt.url_shortener.url;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.marco_bartelt.url_shortener.RedirectController;
import de.marco_bartelt.url_shortener.ShortenedUrl;
import de.marco_bartelt.url_shortener.ShortenedUrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(RedirectController.class)
public class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortenedUrlRepository repository;

    @Test
    public void testRedirect() throws Exception {
        ShortenedUrl url = new ShortenedUrl();
        url.setOriginal("https://marco-bartelt.de");
        url.setShortId("short_id");
        url.setClickCount(0);

        when(repository.findByShortId("short_id")).thenReturn(Optional.of(url));

        this.mockMvc.perform(get("/short_id"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://marco-bartelt.de"));
    }

    @Test
    public void testNotFound() throws Exception {
        when(repository.findByShortId("not_existing_short_id")).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/not_existing_short_id"))
                .andExpect(status().isNotFound());
    }
}
