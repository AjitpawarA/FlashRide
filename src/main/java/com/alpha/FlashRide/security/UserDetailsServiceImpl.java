package com.alpha.FlashRide.security;

import com.alpha.FlashRide.Repository.UserrRepo;
import com.alpha.FlashRide.entity.Userr;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserrRepo userrRepository;

    public UserDetailsServiceImpl(UserrRepo userrRepository) {
        this.userrRepository = userrRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mobno) {

        long mobile;
        try {
            mobile = Long.parseLong(mobno);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid mobile number");
        }

        Userr user = userrRepository.findByMobno(mobile)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with mobile: " + mobno));

        return User.builder()
                .username(String.valueOf(user.getMobno()))
                .password(user.getPassword()) // already encoded
                .roles(user.getRole())        // CUSTOMER / DRIVER / ADMIN
                .build();
    }
}
