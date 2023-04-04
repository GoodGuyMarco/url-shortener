package de.marco_bartelt.url_shortener.entity;

import de.marco_bartelt.url_shortener.service.ShortIdGenerator;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class ShortUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotNull private String original;

  @NotNull
  @Column(unique = true)
  private String shortId;

  @NotNull private String description;

  @NotNull
  @ColumnDefault("0")
  private Integer clickCount = 0;

  @NotNull private LocalDate createdAt;

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
