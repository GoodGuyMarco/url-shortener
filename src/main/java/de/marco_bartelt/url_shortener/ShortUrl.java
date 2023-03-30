package de.marco_bartelt.url_shortener;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class ShortUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String original;

  @Column(unique = true)
  private String shortId;

  private String description;

  @ColumnDefault("0")
  private Integer clickCount = 0;

  private LocalDate createdAt;

  public ShortUrl() {}

  public ShortUrl(String original, String description) {
    this.original = original;
    this.description = description;
    this.shortId = ShortIdGenerator.generate();
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
