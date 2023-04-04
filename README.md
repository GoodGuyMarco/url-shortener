### Format files using Google Java Format
```bash
java -jar ./google-java-format-1.16.0-all-deps.jar -i src/main/java/de/marco_bartelt/url_shortener/* src/test/java/de/marco_bartelt/url_shortener/*
```

### Build with Maven

```bash
./mvnw package
```

### TODOs for Production
- [ ] Restrict creation of URLs only for authenticated users (?)
- [ ] "CAPTCHA" to tell human and bots apart
- [ ] Implement mechanisms for rate-limiting
- [ ] Prevent brute force attacks
- [ ] Prevent usage with harmful URLs by using a blacklist
- [ ] Documentation of endpoints with OpenAPI
- [ ] CORS