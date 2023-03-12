package com.api.backend1.services;


import com.api.backend1.models.PhotoModel;
import com.api.backend1.repositories.PhotoRepository;
import com.api.backend1.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {

    //ponto de injeção
    final
    PhotoRepository photoRepository;
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

        public String uploadImage(MultipartFile file) throws IOException {

            PhotoModel ImageSave = photoRepository.save(PhotoModel.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .image(ImageUtils.compressImage(file.getBytes())).build());

            if (ImageSave != null){
                return "photo uploaded successfully : " + file.getOriginalFilename();
            }

            return null;
        }

        public byte[] downloadImage (String name){
            Optional<PhotoModel> imageDownload = photoRepository.findByName(name);
            byte[] images = ImageUtils.decompressImage(imageDownload.get().getImage());
            return images;
        }

        public List<PhotoModel> findAll() {
            return photoRepository.findAll();
        }

        public Optional<PhotoModel> findById(UUID id) {
            return photoRepository.findById(id);
        }

        @Transactional
        public void delete(PhotoModel photoModel) {
            photoRepository.delete(photoModel);
        }
}
