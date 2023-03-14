package com.api.backend1.controllers;

import com.api.backend1.dtos.ProductDto;
import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.PhotoModel;
import com.api.backend1.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    PhotoService photoService;


    @PostMapping
    /**
     * Método público que recebe um quivo de imagem via RequestParam
     * e enviar para o serviço de fotos realizar o upload
     */
    public ResponseEntity<?> uploadImage (@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = photoService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @PostMapping("/product/{productId}/photos")
    /**
     * Este método trata o upload de imagens para um produto específico
     */
    public ResponseEntity<String> addProductToPhotos(@PathVariable UUID productId,
                                                     @RequestBody List<UUID> photoIds) throws ResourceNotFoundException {
        String message = photoService.addProductToPhotos(productId, photoIds);
        return ResponseEntity.ok(message);
    }


    @GetMapping
    /**
     * Método público que retorna uma lista de objetos PhotoModel.
     *
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a lista de objetos PhotoModel.
     */
    public ResponseEntity<List<PhotoModel>> getAllPhoto(){
        return ResponseEntity.status(HttpStatus.OK).body(photoService.findAll());
    }

    @GetMapping("/{name}")
    /**
     * Método público que recebe um valor 'name' e envia para o service de downloadImage onde vai mostrar a imagem.
     *
     * @param name valor a ser pesquisado no campo 'name' dos objetos PhotoModel.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a imagem.
     */
    public ResponseEntity<?> downloadImage(@PathVariable String name){
        byte[] imageDownload = photoService.downloadImage(name);
        return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType
                            .valueOf("image/png"))
                .body(imageDownload);
    }


    @DeleteMapping("/{id}")
    /**
     * Método público que recebe o ID de um objeto PhotoModel e o remove do banco de dados.
     *
     * @param id ID do objeto CultureModel a ser removido.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a mensagem 'Image deleted successfully.'.
     */
    public ResponseEntity<Object> deleteImage(@PathVariable UUID id) throws ResourceNotFoundException {
        photoService.deletePhoto(id);
        return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully.");
    }


    @PutMapping("/photos/{id}/product")
    /**
     * Método público que recebe o ID de um objeto PhotoModel e um objeto ProductDto enviado pelo cliente via HTTP request body.
     * O método atualiza o campo product_fk do objeto CultureModel com base nas informações contidas no objeto ProductDto.
     *
     * @param id ID do objeto PhotoModel a ser atualizado.
     * @param cultureDto objeto ProductDto enviado pelo cliente via HTTP request body.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta "Product updated successfully"
     * caso a foto ou produto não for encontardo retornar erro NOT_FOUND e a mensagem dizendo o que não foi encontrado.
     */
    public ResponseEntity<String> updateProductInPhoto(@PathVariable("id") UUID photoId,
                                                       @RequestBody ProductDto productDTO) {
        try {
            photoService.updateProductInPhoto(photoId, productDTO.getId());
            return ResponseEntity.ok("Product updated successfully for photo with id " + photoId);
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
