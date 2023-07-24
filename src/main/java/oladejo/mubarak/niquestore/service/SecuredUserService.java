package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.model.SecuredUser;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecuredUserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser foundUser = userRepo.findUserByEmailIgnoreCase(username).orElse(null);

        return new SecuredUser(foundUser);
    }
}
