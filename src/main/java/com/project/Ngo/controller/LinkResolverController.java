package com.project.Ngo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LinkResolverController {

    @PostMapping("/resolve-link")
    public ResponseEntity<?> resolveLink(@RequestBody Map<String, String> request) {
        String shortLink = request.get("shortLink");

        try {
            // Follow the redirect to resolve the full URL
            HttpURLConnection connection = (HttpURLConnection) new URL(shortLink).openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.connect();

            String resolvedLink = connection.getHeaderField("Location");

            if (resolvedLink == null) {
                return ResponseEntity.badRequest().body("Could not resolve the link");
            }

            return ResponseEntity.ok(Map.of("resolvedLink", resolvedLink));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error resolving link");
        }
    }
}
