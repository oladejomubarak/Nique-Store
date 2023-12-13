package oladejo.mubarak.niquestore.controller;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.service.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VendorController {
    private final ProductServiceImpl productService;

    @PostMapping("add-product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto){
        try {
            return ResponseEntity.ok(productService.addProduct(productDto));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("update-product/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable String productId, @RequestBody ProductDto productDto){
        try {
            return ResponseEntity.ok(productService.editProduct(productId, productDto));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
