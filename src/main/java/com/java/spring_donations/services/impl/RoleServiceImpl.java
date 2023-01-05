package com.java.spring_donations.services.impl;

import com.java.spring_donations.domain.Role;
import com.java.spring_donations.repositorys.RoleRepository;
import com.java.spring_donations.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
