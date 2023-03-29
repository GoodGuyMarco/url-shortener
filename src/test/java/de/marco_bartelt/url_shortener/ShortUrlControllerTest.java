package de.marco_bartelt.url_shortener;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UrlShortenerController.class)
public class ShortUrlControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ShortenedUrlRepository repository;

  @Test
  public void testPost() throws Exception {
    CreateShortenedUrlRequest request = new CreateShortenedUrlRequest();
    request.original = "https://marco-bartelt.de";
    request.description = "A website made with Gatsby and Tailwind CSS.";

    String json = new ObjectMapper().writeValueAsString(request);

    mockMvc
        .perform(post("/url/").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isCreated());
  }

  @Test
  public void testPostWithValidationErrors() throws Exception {
    CreateShortenedUrlRequest request = new CreateShortenedUrlRequest();
    String json = new ObjectMapper().writeValueAsString(request);

    mockMvc
        .perform(post("/url/").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testDelete() throws Exception {
    when(repository.findById("short_url_id")).thenReturn(Optional.of(new ShortenedUrl()));

    mockMvc.perform(delete("/url/{id}", "short_url_id")).andExpect(status().isOk());
  }

  @Test
  public void testDeleteWithNotFound() throws Exception {
    when(repository.findById("not_existing_id")).thenReturn(Optional.empty());

    mockMvc.perform(delete("/url/{id}", "not_existing_id")).andExpect(status().isNotFound());
  }

  @Test
  public void testGet() throws Exception {
    ShortenedUrl url = new ShortenedUrl();
    url.setId("id");
    url.setShortId("short_id");
    url.setOriginal("https://marco-bartelt.de");
    url.setDescription("Lorem ipsum dolor amet.");
    url.setClickCount(0);

    when(repository.findById("id")).thenReturn(Optional.of(url));

    MvcResult result =
        mockMvc.perform(get("/url/{id}", "id")).andExpect(status().isOk()).andReturn();

    String actual = result.getResponse().getContentAsString();
    String expectation = new ObjectMapper().writeValueAsString(url);

    Assertions.assertThat(actual).isEqualTo(expectation);
  }

  @Test
  public void testGetWithNotFound() throws Exception {
    when(repository.findById("not_existing_id")).thenReturn(Optional.empty());

    mockMvc.perform(get("/url/{id}", "not_existing_id")).andExpect(status().isNotFound());
  }

  @Test
  public void testGetAll() throws Exception {
    ShortenedUrl a = new ShortenedUrl();
    a.setId("id");
    a.setShortId("short_id");
    a.setOriginal("https://marco-bartelt.de");
    a.setDescription("Lorem ipsum dolor amet.");
    a.setClickCount(0);

    ShortenedUrl b = new ShortenedUrl();
    b.setId("id");
    b.setShortId("short_id");
    b.setOriginal("https://marco-bartelt.de");
    b.setDescription("Lorem ipsum dolor amet.");
    b.setClickCount(0);

    List<ShortenedUrl> urls = List.of(a, b);

    when(repository.findAll()).thenReturn(urls);

    MvcResult result = mockMvc.perform(get("/url/")).andExpect(status().isOk()).andReturn();

    String actual = result.getResponse().getContentAsString();
    String expectation = new ObjectMapper().writeValueAsString(urls);

    Assertions.assertThat(actual).isEqualTo(expectation);
  }
}
