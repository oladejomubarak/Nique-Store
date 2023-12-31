package oladejo.mubarak.niquestore.controller;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.dto.response.ApiResponse;
import oladejo.mubarak.niquestore.data.model.Order;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping("place")
    public ResponseEntity<?> orderProduct(@RequestBody OrderProductRequest orderProductRequest){
        try{
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("You order has been placed successfully, pls proceed to payment")
                    .data(orderService.orderProduct(orderProductRequest))
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("find/{orderId}")
    public ResponseEntity<?> findOrder(@PathVariable String orderId){
        try{
            return ResponseEntity.ok(orderService.findOrder(orderId));
        }catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable String id){
        try{
            orderService.cancelOrder(id);
            return ResponseEntity.ok("Your order has been successfully canceled");
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("cart/add")
    @PatchMapping("cart/add")
    public ResponseEntity<?> addToCart(@RequestBody OrderProductRequest orderProductRequest){
        try{
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Order has been added to cart")
                    .data(orderService.addOrderToCart(orderProductRequest))
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("cart/remove/{customerEmail}")
    public ResponseEntity<?> removeFromCart(@PathVariable String customerEmail, @RequestParam String orderId){
        try{
            orderService.removeOrderFromCart(customerEmail, orderId);
            return ResponseEntity.ok("Order has been removed from cart");
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("cart/checkout/{customerEmail}")
    public ResponseEntity<?> checkout(@PathVariable String customerEmail){
        try{
            return ResponseEntity.ok(orderService.checkout(customerEmail));
        } catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
