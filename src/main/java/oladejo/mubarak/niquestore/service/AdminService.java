package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.dto.request.CreateVendorRequest;
import oladejo.mubarak.niquestore.data.model.AppUser;

import java.util.List;

public interface AdminService {
    void saveUser();
    void registerVendor(CreateVendorRequest createVendorRequest);

    void removeVendor(CreateVendorRequest  createVendorRequest);
    List<AppUser> getAllUsers();
}
