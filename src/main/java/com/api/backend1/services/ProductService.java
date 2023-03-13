package com.api.backend1.services;


import com.api.backend1.dtos.ProductCultureDto;
import com.api.backend1.dtos.ProductDto;
import com.api.backend1.exceptions.DuplicateProductNameException;
import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.CultureModel;
import com.api.backend1.models.PhotoModel;
import com.api.backend1.models.ProductCultureModel;
import com.api.backend1.models.ProductModel;
import com.api.backend1.repositories.CultureRepository;
import com.api.backend1.repositories.PhotoRepository;
import com.api.backend1.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {

    //ponto de injeção
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private PhotoRepository photoRepository;



    @Transactional
    public ProductModel createProduct(ProductDto productDto) throws ResourceNotFoundException {
        // Verifica se já existe um produto com o mesmo nome
        if (productRepository.existsByName(productDto.getName())) {
            throw new DuplicateProductNameException("Name of product is already in use!");
        }

        // Cria uma nova instância de ProductModel
        var product = new ProductModel();

        // Copia as propriedades de productDto para a nova instância de ProductModel
        BeanUtils.copyProperties(productDto, product);

        // Define a data de registro como a data atual em UTC
        product.setRegistrationDate(LocalDate.now(ZoneId.of("UTC")));

        // Cria a lista de ProductCultureModel vazia para adicionar os itens depois
        List<ProductCultureModel> productCultureModels = new ArrayList<>();

        // Loop para criar uma nova entidade ProductCultureModel para cada item na lista de ProductCultureDto
        for (ProductCultureDto productCultureDto : productDto.getProductCultures()) {
            // Cria uma nova instância de ProductCultureModel
            var productCulture = new ProductCultureModel();

            // Copia a área do item da lista de ProductCultureDto para a nova instância de ProductCultureModel
            productCulture.setArea_size(productCultureDto.getAreaSize());

            // Busca a entidade CultureModel pelo id informado no ProductCultureDto
            Optional<CultureModel> cultureOptional = cultureRepository.findById(productCultureDto.getCultureId());

            // Verifica se a entidade CultureModel existe e a associa à nova instância de ProductCultureModel
            if (cultureOptional.isPresent()) {
                productCulture.setCulture(cultureOptional.get());
            } else {
                // Lança uma exceção caso a entidade CultureModel não exista
                throw new ResourceNotFoundException("Culture not found with id: " + productCultureDto.getCultureId());
            }

            // Associa a nova instância de ProductModel à nova instância de ProductCultureModel
            productCulture.setProduct(product);

            // Adiciona a nova instância de ProductCultureModel à lista de ProductCultureModel
            productCultureModels.add(productCulture);
        }

        // Associa cada instância de ProductCultureModel ao produto criado
        product.setProductCultures(productCultureModels);

        // Adiciona as fotos ao produto
        addPhotosToProduct(product, productDto.getProductPhotos());

        // Salva o novo produto no banco de dados
        return productRepository.save(product);
    }

    private void addPhotosToProduct(ProductModel product, List<UUID> photoIds) throws ResourceNotFoundException {
        if (photoIds != null) {
            List<PhotoModel> photos = new ArrayList<>();
            for (UUID photoId : photoIds) {
                Optional<PhotoModel> photoOptional = photoRepository.findById(photoId);
                if (photoOptional.isPresent()) {
                    photos.add(photoOptional.get());
                } else {
                    throw new ResourceNotFoundException("Photo not found with id: " + photoId);
                }
            }
            product.setPhotos(photos);
        }
    }


    // retorna uma lista de todos os produtos salvos no banco de dados
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductModel> findByNameContaining(String name) {

        //busca o produto pelo id
        List<ProductModel> products= productRepository.findByNameContaining(name);

        // verifica se o produto existe
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with name containing: " + name);
        }

        return products;
    }


    // exclui um produto com o ID fornecido
    @Transactional
    public void deleteProduct(UUID id) throws ResourceNotFoundException {

        //busca o produto pelo id
        Optional<ProductModel> optionalProduct = productRepository.findById(id);

        // verifica se o produto existe
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }


        // exclui o produto encontrado encontrado
        ProductModel product = optionalProduct.get();
        productRepository.deleteById(product.getId());
    }

    @Transactional
    public ProductModel updateProduct(UUID id, ProductDto productDto) throws ResourceNotFoundException {
        // Verifica se o produto com o ID fornecido existe no banco de dados
        Optional<ProductModel> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        // Copia as informações do ProductDto para o objeto ProductModel correspondente ao produto a ser atualizado
        ProductModel product = optionalProduct.get();
        UUID productId = product.getId(); // copia o identificador
        BeanUtils.copyProperties(productDto, product);
        product.setId(productId); // atualiza o identificador

        // Atualiza a data de registro do produto para a data atual em UTC
        product.setRegistrationDate(LocalDate.now(ZoneId.of("UTC")));

        // Limpa a lista de ProductCultureModel associada ao produto a ser atualizado
        product.getProductCultures().clear();

        // Loop para criar uma nova entidade ProductCultureModel para cada item na lista de ProductCultureDto
        for (ProductCultureDto productCultureDto : productDto.getProductCultures()) {
            // Cria uma nova instância de ProductCultureModel
            var productCulture = new ProductCultureModel();

            // Copia a área do item da lista de ProductCultureDto para a nova instância de ProductCultureModel
            productCulture.setArea_size(productCultureDto.getAreaSize());

            // Busca a entidade CultureModel pelo id informado no ProductCultureDto
            Optional<CultureModel> cultureOptional = cultureRepository.findById(productCultureDto.getCultureId());

            // Verifica se a entidade CultureModel existe e a associa à nova instância de ProductCultureModel
            if (cultureOptional.isPresent()) {
                productCulture.setCulture(cultureOptional.get());
            } else {
                // Lança uma exceção caso a entidade CultureModel não exista
                throw new ResourceNotFoundException("Culture not found with id: " + productCultureDto.getCultureId());
            }

            // Associa a nova instância de ProductModel à nova instância de ProductCultureModel
            productCulture.setProduct(product);

            // Adiciona a nova instância de ProductCultureModel à lista de ProductCultureModel
            product.getProductCultures().add(productCulture);
        }

        // Salva o produto atualizado no banco de dados
        return productRepository.save(product);

    }

    }
