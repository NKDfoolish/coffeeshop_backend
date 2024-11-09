package com.coffeeshop.mycoffee.service;

import com.coffeeshop.mycoffee.dto.productdto.request.ProductCreationRequest;
import com.coffeeshop.mycoffee.dto.productdto.request.ProductUpdateRequest;
import com.coffeeshop.mycoffee.dto.productdto.response.ProductResponse;
import com.coffeeshop.mycoffee.entity.Category;
import com.coffeeshop.mycoffee.entity.Product;
import com.coffeeshop.mycoffee.exception.AppException;
import com.coffeeshop.mycoffee.exception.ErrorCode;
import com.coffeeshop.mycoffee.mapper.ProductMapper;
import com.coffeeshop.mycoffee.repository.CategoryRepository;
import com.coffeeshop.mycoffee.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;
    S3Client s3Client;

    @Value("${amazonProperties.bucketName}")
    @NonFinal
    private String BUCKET_NAME;


    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(ProductCreationRequest request) {
        if (productRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        Product product = productMapper.toProduct(request);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        product.setCategory(category);

//        if (imageFile != null && !imageFile.isEmpty()) {
//            String imageUrl = saveProductImage(imageFile, product.getId());
//            product.setImageUrl(imageUrl);
//        }

        try {
            product = productRepository.save(product);
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductResponse> getProducts(){
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(String productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        // Cập nhật `name` nếu `request.name` không phải là null
        if (request.getName() != null) {
            product.setName(request.getName());
        }

        // Cập nhật `price` nếu `request.price` không phải là null
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        // Cập nhật `category` nếu `request.categoryId` không phải là null
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
            product.setCategory(category);
        }

//        if (imageFile != null && !imageFile.isEmpty()) {
//            String imageUrl = saveProductImage(imageFile, productId);
//            product.setImageUrl(imageUrl);
//        }

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(String productId){
        productRepository.deleteById(productId);
    }

    public String saveProductImage(MultipartFile imageFile, String productId) throws IOException {
        if (imageFile.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_IMAGE);
        }

        String key = "products/" + productId + ".jpg";
        String fullPath = "https://coffeeshopuit.s3.ap-northeast-1.amazonaws.com/" + key;

        // Ensure the directory exists
        Path tempDir = Paths.get("images/temp");
        if (!Files.exists(tempDir)) {
            Files.createDirectories(tempDir);
        }

        // Save the file to a temporary location
        Path tempFile = Files.createTempFile(tempDir, "temp", imageFile.getOriginalFilename());
        Files.write(tempFile, imageFile.getBytes(), StandardOpenOption.CREATE);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .acl("public-read")
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromFile(tempFile));

            if (response.sdkHttpResponse().isSuccessful()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
                product.setImageUrl(fullPath);
                productRepository.save(product);

                // Delete the temporary file
                Files.delete(tempFile);

                return s3Client.utilities().getUrl(builder -> builder.bucket(BUCKET_NAME).key(key)).toExternalForm();
            } else {
                log.error("Failed to upload image to S3. Response: {}", response);
                throw new AppException(ErrorCode.IMAGE_SAVE_FAILED);
            }
        }
        catch (Exception e) {
            log.error("Error saving product image to S3", e);
            throw new AppException(ErrorCode.IMAGE_SAVE_FAILED);
        }
    }

}
