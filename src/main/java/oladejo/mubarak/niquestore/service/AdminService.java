package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.model.AppUser;

import java.util.List;

public interface AdminService {
    void saveUser();
    void registerVendor(String email);
    List<AppUser> getAllUsers();
}
