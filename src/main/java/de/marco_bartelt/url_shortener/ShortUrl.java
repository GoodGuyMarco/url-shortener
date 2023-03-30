package de.marco_bartelt.url_shortener;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class ShortUrl {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String original;

  private String shortId;

  private String description;

  private Integer clickCount;

  private LocalDate createdAt;

  public ShortUrl() {}

  public ShortUrl(String original, String description) {
    this.original = original;
    this.shortId = ShortIdGenerator.generate();
    this.description = description;
    this.clickCount = 0;
    this.createdAt = LocalDate.now();
  }

  public void visit() {
    this.clickCount += 1;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOriginal() {
    return original;
  }

  public void setOriginal(String original) {
    this.original = original;
  }

  public String getShortId() {
    return shortId;
  }

  public void setShortId(String shortId) {
    this.shortId = shortId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getClickCount() {
    return clickCount;
  }

  public void setClickCount(Integer clickCount) {
    this.clickCount = clickCount;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }
}
