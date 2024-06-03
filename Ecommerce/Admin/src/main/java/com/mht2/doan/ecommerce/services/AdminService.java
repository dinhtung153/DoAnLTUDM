package com.mht2.doan.ecommerce.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.mht2.doan.ecommerce.dtos.AdminDto;
import com.mht2.doan.ecommerce.models.Admin;
import com.mht2.doan.ecommerce.repositories.AdminRepository;
import com.mht2.doan.ecommerce.repositories.RoleRepository;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}