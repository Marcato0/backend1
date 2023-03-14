package com.api.backend1.services;


import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.PhotoModel;
import com.api.backend1.models.ProductModel;
import com.api.backend1.repositories.PhotoRepository;
import com.api.backend1.repositories.ProductRepository;
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

    @Autowired
    ProductRepository productRepository;

    @Transactional
    /**
     * Faz upload de uma imagem para o banco de dados e
     * retorna uma mensagem de sucesso caso seja bem sucedido.
     *
     * @param file O arquivo de imagem a ser enviado para o banco.
     * @return Uma mensagem de sucesso contendo o nome do arquivo enviado.
     * @throws IOException Se ocorrer algum erro ao ler ou salvar o arquivo de imagem.
     */
    public String uploadImage(MultipartFile file) throws IOException {

        // Salva a imagem no banco de dados
        PhotoModel ImageSave = photoRepository.save(PhotoModel.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .image(ImageUtils.compressImage(file.getBytes())).build());

        // Verifica se a imagem foi salva com sucesso
        if (ImageSave != null) {
                return "photo uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }


    /**
     * Busca uma imagem no db com base no nome fornecido.
     *
     * @param name O nome da imagem a ser buscada.
     * @return Um array de bytes contendo a imagem buscada.
     * @throws ResourceNotFoundException Se a imagem com o nome fornecido não for encontrada.
     */
    public byte[] downloadImage (String name){
        // Procura a imagem pelo nome no banco de dados
        Optional<PhotoModel> imageDownload = photoRepository.findByName(name);

        // Converte a imagem para um array de bytes e retorna
        byte[] images = ImageUtils.decompressImage(imageDownload.get().getImage());
        return images;
    }


     /**
      * Associa um produto a uma ou mais fotos.
      * @param productId ID do produto a ser associado às fotos.
      * @param photoIds Lista de IDs das fotos a serem associadas ao produto.
      * @return String com mensagem de sucesso da operação.
      * @throws ResourceNotFoundException Se o produto não for encontrado pelo ID fornecido ou se alguma das fotos
      * não for encontrada ou já estiver associada a outro produto.
      */
    public String addProductToPhotos(UUID productId, List<UUID> photoIds) throws ResourceNotFoundException {

        // Busca o produto pelo id
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Busca as fotos pelo id e FK de produto null
        List<PhotoModel> photos = photoRepository.findByIdInAndProductIsNull(photoIds);

        // Verifica se todas as fotos foram encontradas, caso algum produto associado ou se não for encontrado nenhuma foto retorna um erro
        if (photos.size() != photoIds.size()) {
            throw new ResourceNotFoundException("Some photos were not found or already have a product associated with them.");
        }

        // Adiciona a FK do produto às fotos encontradas
        photos.forEach(photo -> photo.setProduct(product));

        // Salva as alterações no banco de dados
        photoRepository.saveAll(photos);

        return "Product added to photos successfully.";
    }


    @Transactional
     /**
      * Atualiza o produto associado a uma foto.
      * @param photoId ID da foto a ser atualizada.
      * @param productId ID do produto a ser associado à foto.
      * @throws ResourceNotFoundException Se a foto ou o produto não forem encontrados pelos IDs fornecidos.
      */
    public void updateProductInPhoto(UUID photoId, UUID productId) throws ResourceNotFoundException {

        // Encontra a foto pelo ID
        PhotoModel photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found with id: " + photoId));

        // Encontra o produto pelo ID
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Atualiza o produto associado à foto
        photo.setProduct(product);

        // Salva a foto com o produto atualizado no banco de dados
        photoRepository.save(photo);
    }

    /**
     * Retorna todas as fotos cadastradas.
     *
     * @return uma lista com  todas as fotos cadastradas.
     */
    public List<PhotoModel> findAll() {
            return photoRepository.findAll();
    }

    @Transactional
    /**
     * Método responsável por deletar um foto pelo ID
     * @param id ID do foto a ser deletado
     * @throws ResourceNotFoundException Exceção lançada caso a foto não seja encontrado
     */
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
