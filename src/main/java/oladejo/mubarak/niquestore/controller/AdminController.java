package oladejo.mubarak.niquestore.controller;
import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final CustomerServiceImpl userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
