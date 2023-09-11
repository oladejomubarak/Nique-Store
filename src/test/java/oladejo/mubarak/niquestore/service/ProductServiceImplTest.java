package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.Product;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;
    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setProductName("television");
        productDto.setCategory("appliances");
        productDto.setQuantity(5);
        productDto.setPrice(150000);
        productDto.setVendorEmail("oladejomubarakade@gmail.com");

        product = new Product();
    }
    @Test void testThatProductCanBeAdded(){
        Product addedProduct = productService.addProduct(productDto);
        assertNotNull(addedProduct);
    }
    @Test void testThatAddedProductCanBeFound(){
        Product addedProduct = productService.findProduct("64fe861033b95049c08169f4");
        assertNotNull(addedProduct);
    }
    @Test void testThatProductDetailsCanBeEdited(){
        ProductDto productDto1 = new ProductDto();
        productDto1.setPrice(140000);
        productDto1.setProductName("tv");
        Product updatedProduct = productService.editProduct("64fe861033b95049c08169f4", productDto1);
        assertEquals("tv", updatedProduct.getName());
    }
    @Test void testThatProductCanBeDeleted(){
        ProductDto productDto1 = new ProductDto();
        productDto1.setProductName("laptop");
        productDto1.setVendorEmail("oladejomubarakade@gmail.com");
        Product addProduct = productService.addProduct(productDto1);
        productService.deleteProduct(addProduct.getId());
        assertThrows(NiqueStoreException.class, ()-> productService.findProduct(addProduct.getId()) );
        //assertEquals(addProduct, productService.findProduct(addProduct.getId()));
    }
}