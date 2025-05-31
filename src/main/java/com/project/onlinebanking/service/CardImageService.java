package com.project.onlinebanking.service;

import com.project.onlinebanking.entity.CardImage;
import com.project.onlinebanking.exception.ImageAlreadyExistException;
import com.project.onlinebanking.exception.ResourceNotFoundException;
import com.project.onlinebanking.repository.CardImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardImageService {
    private final CardImageRepository cardImageRepository;

    public List<Long> getAllImages() {
        List<CardImage> cardImages = cardImageRepository.findAll();
        return cardImages.stream()
                .map(CardImage::getId).toList();
    }

    public byte[] getImageById(Long id) throws IOException {
        CardImage cardImage = cardImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        File image = new File(cardImage.getImageUrl());

        return Files.readAllBytes(image.toPath());
    }

    public void uploadAllImagesUrlToDb(Path directory) throws IOException {
        if (Files.isDirectory(directory)) {
            List<String> images = cardImageRepository.findAll()
                    .stream()
                    .map(CardImage::getImageUrl).toList();

            Files.list(directory).forEach(file -> {
                if (!images.contains(file.toString())) {
                    CardImage cardImage = new CardImage();
                    cardImage.setImageUrl(file.toString());
                    cardImageRepository.save(cardImage);
                }
            });
        }
    }

    public void uploadImageUrlToDb(Path path) {
        List<String> images = cardImageRepository.findAll()
                .stream()
                .map(CardImage::getImageUrl).toList();

        if (!Files.exists(path)) {
            throw new ResourceNotFoundException("Image not found");
        }

        if (images.contains(path.toString())) {
            throw new ImageAlreadyExistException("Image already exists in the database");
        }

        CardImage cardImage = new CardImage();
        cardImage.setImageUrl(path.toString());
        cardImageRepository.save(cardImage);
    }
}
