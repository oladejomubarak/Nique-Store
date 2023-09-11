package oladejo.mubarak.niquestore.controller;
import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.CreateVendorRequest;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.service.AdminServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PatchMapping("add-vendor")
    public ResponseEntity<?> registerVendor(@RequestBody CreateVendorRequest createVendorRequest){
        try{
            adminService.registerVendor(createVendorRequest);
            return ResponseEntity.ok("The user has successfully been registered as vendor");
        }catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("remove-vendor")
    public ResponseEntity<?> removeVendor(@RequestBody CreateVendorRequest createVendorRequest){
        try{
            adminService.removeVendor((createVendorRequest));
            return ResponseEntity.ok("The user has successfully been unregistered as vendor");
        }catch (NiqueStoreException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
