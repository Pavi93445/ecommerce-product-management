package com.telusko.ecom_proj.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telusko.ecom_proj.model.Product;
import com.telusko.ecom_proj.repo.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    // Get all products
    public List<Product> getAllProducts() {
        return repo.findAll();
        
    }

    // Get product by ID
    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    // Add a new product
    public Product addProduct(Product product, MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                product.setImageName(imageFile.getOriginalFilename());
                product.setImageType(imageFile.getContentType());
                product.setImageData(imageFile.getBytes());
            }
            return repo.save(product);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing image file", e);
        }
    }

	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
		product.setImageData(imageFile.getBytes());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		return repo.save(product);
	}

	public void deleteProduct(int id) {
      repo.deleteById(id);		
	}

	public List<Product> searchProducts(String keyword) {

		return repo.searchProducts(keyword);
	}
}
