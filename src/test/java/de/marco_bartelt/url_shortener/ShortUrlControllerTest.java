package de.marco_bartelt.url_shortener;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ShortUrlController.class)
public class ShortUrlControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ShortUrlService service;

  @Test
  public void testPost() throws Exception {
    CreateShortUrlRequest request = new CreateShortUrlRequest();
    request.original = "https://marco-bartelt.de";
    request.description = "A website made with Gatsby and Tailwind CSS.";

    String json = new ObjectMapper().writeValueAsString(request);

    mockMvc
        .perform(post("/url/").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isCreated());
  }

  @Test
  public void testPostWithValidationErrors() throws Exception {
    CreateShortUrlRequest request = new CreateShortUrlRequest();
    String json = new ObjectMapper().writeValueAsString(request);

    mockMvc
        .perform(post("/url/").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testDelete() throws Exception {
    when(service.delete("short_url_id")).thenReturn(new ShortUrl());

    mockMvc.perform(delete("/url/{id}", "short_url_id")).andExpect(status().isOk());
  }

  @Test
  public void testDeleteWithNotFound() throws Exception {
    when(service.delete("not_existing_id")).thenReturn(null);

    mockMvc.perform(delete("/url/{id}", "not_existing_id")).andExpect(status().isNotFound());
  }

  @Test
  public void testGet() throws Exception {
    ShortUrl url = new ShortUrl();
    url.setId("id");
    url.setShortId("short_id");
    url.setOriginal("https://marco-bartelt.de");
    url.setDescription("Lorem ipsum dolor amet.");
    url.setClickCount(0);

    when(service.getById("id")).thenReturn(url);

    MvcResult result =
        mockMvc.perform(get("/url/{id}", "id")).andExpect(status().isOk()).andReturn();

    String actual = result.getResponse().getContentAsString();
    String expectation = new ObjectMapper().writeValueAsString(url);

    Assertions.assertThat(actual).isEqualTo(expectation);
  }

  @Test
  public void testGetWithNotFound() throws Exception {
    when(service.getById("not_existing_id")).thenReturn(null);

    mockMvc.perform(get("/url/{id}", "not_existing_id")).andExpect(status().isNotFound());
  }

  @Test
  public void testGetAll() throws Exception {
    ShortUrl a = new ShortUrl();
    a.setId("id");
    a.setShortId("short_id");
    a.setOriginal("https://marco-bartelt.de");
    a.setDescription("Lorem ipsum dolor amet.");
    a.setClickCount(0);

    ShortUrl b = new ShortUrl();
    b.setId("id");
    b.setShortId("short_id");
    b.setOriginal("https://marco-bartelt.de");
    b.setDescription("Lorem ipsum dolor amet.");
    b.setClickCount(0);

    List<ShortUrl> urls = List.of(a, b);

    when(service.getAll()).thenReturn(urls);

    MvcResult result = mockMvc.perform(get("/url/")).andExpect(status().isOk()).andReturn();

    String actual = result.getResponse().getContentAsString();
    String expectation = new ObjectMapper().writeValueAsString(urls);

    Assertions.assertThat(actual).isEqualTo(expectation);
  }
}
