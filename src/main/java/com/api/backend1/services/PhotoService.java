package com.api.backend1.services;


import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.PhotoModel;
import com.api.backend1.models.ProductModel;
import com.api.backend1.repositories.PhotoRepository;
import com.api.backend1.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {

    //ponto de injeção

    @Autowired
    PhotoRepository photoRepository;

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


        @Transactional
        public void deletePhoto(UUID id) throws ResourceNotFoundException {

            //busca a foto pelo id
            Optional<PhotoModel> optionalPhoto = photoRepository.findById(id);

            // verifica se a foto existe
            if (optionalPhoto.isEmpty()) {
                throw new ResourceNotFoundException("Photo not found with id: " + id);
            }


            // exclui a foto encontrada
            PhotoModel photo = optionalPhoto.get();
            photoRepository.deleteById(photo.getId());
        }
}
