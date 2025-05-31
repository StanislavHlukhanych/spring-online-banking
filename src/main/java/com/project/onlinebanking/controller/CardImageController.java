package com.project.onlinebanking.controller;

import com.project.onlinebanking.dto.SaveImageByName;
import com.project.onlinebanking.service.CardImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class CardImageController {
    private static final String DIR = System.getProperty("user.dir") + "/assets/card_images/";
    private final CardImageService cardImageService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Long>> getAllImagesUrl() {
        List<Long> imagesUrls = cardImageService.getAllImages();
        return ResponseEntity.ok(imagesUrls);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getImageUrl(@PathVariable Long id) throws IOException {
        byte[] image = cardImageService.getImageById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @PostMapping("/save-all")
    public ResponseEntity<String> saveAllImagesUrlToDb() {
        try {
            cardImageService.uploadAllImagesUrlToDb(Paths.get(DIR));
            return ResponseEntity.ok("All images saved to database");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving images: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveImageUrlToDb(@RequestBody SaveImageByName saveImageByName) {
        Path path = Paths.get(DIR, saveImageByName.getImageName());
        cardImageService.uploadImageUrlToDb(path);
        return ResponseEntity.ok("Image URL saved to database");
    }
}
