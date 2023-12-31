package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Product;
import oladejo.mubarak.niquestore.data.model.Role;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final UserServiceImpl userService;
    private final ProductRepo productRepo;

    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(ProductDto productDto) {
        AppUser foundUser = userService.findByEmail(productDto.getVendorEmail());
        if(!foundUser.getRole().contains(Role.VENDOR)) {throw new NiqueStoreException("Only vendors can add product");
        }
        Product product = new Product();
        product.setName(productDto.getProductName());
        product.setCategory(productDto.getCategory());
        product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setProductImageUrl(product.getProductImageUrl());
        product.setStoreAddress(productDto.getStoreAddress());
        product.setVendor(foundUser);
        return productRepo.save(product);
    }

    @Override
    public Product findProduct(String productId) {
        return productRepo.findById(productId).orElseThrow(()-> new NiqueStoreException("product not found"));
    }

    @Override
    public Product editProduct(String productId, ProductDto productDto) {
        Product foundProduct = findProduct(productId);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(productDto, foundProduct);
        return productRepo.save(foundProduct);
    }

    @Override
    public void deleteProduct(String productId) {
        Product foundProduct = findProduct(productId);
        productRepo.delete(foundProduct);
    }

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Page<Product> findProductsByPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepo.findAll(pageable);
    }

    @Override
    public List<Product> searchProductByName(String productName) {
        return productRepo.findByNameIgnoreCase(productName);
    }

    @Override
    public List<Product> searchProductByCategory(String category) {
        return productRepo.findByCategoryIgnoreCase(category);
    }

    @Override
    public List<Product> findProductByVendor(String vendorEmail) {
        AppUser foundVendor = userService.findByEmail(vendorEmail);
        return productRepo.findByVendor(foundVendor);
    }
    @Override
    public List<Product> findProductByKeyword(String keyword) {
        return findAllProducts()
                .stream()
                .filter(product -> product.getName().equalsIgnoreCase(keyword))
                .filter(product -> product.getCategory().equalsIgnoreCase(keyword))
                .collect(Collectors.toList());
//        List<Product> products = new ArrayList<>();
//        for (Product product: findAllProducts()) {
//            if (product.getCategory().equalsIgnoreCase(keyword) ||
//                    product.getName().equalsIgnoreCase(keyword)) products.add(product);
//        }
//        return products;

    }
}
