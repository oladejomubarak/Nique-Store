package oladejo.mubarak.niquestore.controller;
import oladejo.mubarak.niquestore.data.dto.request.LoginRequest;
import oladejo.mubarak.niquestore.data.dto.request.UserDto;
import oladejo.mubarak.niquestore.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl userService;


}
