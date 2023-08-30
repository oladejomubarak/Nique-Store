package oladejo.mubarak.niquestore.controller;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.Product;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.service.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping("products")
    public ResponseEntity<?> getProducts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Product> productsPage = productService.findProductsByPage(pageNo, pageSize);
        return ResponseEntity.ok(productsPage);
    }


    @GetMapping("product/{id}")
    public ResponseEntity<?> findProduct(@PathVariable String id){
        try {
            return ResponseEntity.ok(productService.findProduct(id));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("search-by-name")
    public ResponseEntity<?> searchProductByName(@RequestParam String productName){
    try {
        return ResponseEntity.ok(productService.searchProductByName(productName));
      } catch (NiqueStoreException e){
        return ResponseEntity.badRequest().body(e.getMessage());
     }
    }

    @GetMapping("search-by-category")
    public ResponseEntity<?> searchProductByCategory(@RequestParam String category){
        try {
            return ResponseEntity.ok(productService.searchProductByCategory(category));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("search-by-vendor")
    public ResponseEntity<?> searchProductByVendor(@RequestParam String vendorEmail){
        try {
            return ResponseEntity.ok(productService.findProductByVendor(vendorEmail));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("search-by-keyword")
    public ResponseEntity<?> searchProductByKeyword(@RequestParam String keyword){
        try {
            return ResponseEntity.ok(productService.findProductByKeyword(keyword));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
