package ru.kata.SpirngSecurityApp.services;

import org.springframework.stereotype.Service;
import ru.kata.SpirngSecurityApp.models.Role;
import ru.kata.SpirngSecurityApp.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public boolean isExist(String name) {
        return roleRepository.findByName(name).isPresent();
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
