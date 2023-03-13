package com.api.backend1.controllers;


import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.PhotoModel;
import com.api.backend1.services.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/photo")
public class PhotoController {

    final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<?> uploadImage (@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = photoService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping
    public ResponseEntity<List<PhotoModel>> getAllPhoto(){
        return ResponseEntity.status(HttpStatus.OK).body(photoService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> downloadImage(@PathVariable String name){
        byte[] imageDownload = photoService.downloadImage(name);
        return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType
                            .valueOf("image/png"))
                .body(imageDownload);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteImage(@PathVariable UUID id) throws ResourceNotFoundException {
        photoService.deletePhoto(id);
        return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully.");
    }



}
