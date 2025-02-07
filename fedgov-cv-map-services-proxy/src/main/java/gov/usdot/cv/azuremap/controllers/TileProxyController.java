package gov.usdot.cv.azuremap.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin(origins = "${allowed.origins}")
@RequestMapping("/azuremap/api")
public class TileProxyController {
  private static final Logger logger = LogManager.getLogger(TileProxyController.class);
  private final RestTemplate restTemplate;

  private final String apiKey;
  private final String tilesetUrl;

  public TileProxyController(RestTemplateBuilder restTemplateBuilder, @Value("${azure.map.api.key}") String apiKey,
      @Value("${azure.map.tileset.url}") String tilesetUrl) {
    this.restTemplate = restTemplateBuilder.build();
    this.apiKey = apiKey;
    this.tilesetUrl = tilesetUrl;
  }

  @GetMapping("/proxy/tileset/{z}/{x}/{y}")
  public ResponseEntity<byte[]> getTile(@PathVariable int z, @PathVariable int x, @PathVariable int y) {
    String url = String.format(tilesetUrl, z, x, y, apiKey);
    logger.info("Fetching tile from URL: {}", url);
    // Make the request to Azure Maps
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "image/png");
    HttpEntity<String> entity = new HttpEntity<>(headers);

    // Fetch the tile from Azure Maps
    ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

    // Return the tile data back to the client
    return ResponseEntity.ok()
        .header("Content-Type", "image/png")
        .body(response.getBody());

  }
}
