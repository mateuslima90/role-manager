package io.mtkh.rolemanager.configuration;

import io.mtkh.rolemanager.domain.Role;
import io.mtkh.rolemanager.domain.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class RoleMapper {

    private static RoleMapper roleMapper;

    private static ModelMapper modelMapper;

    public static RoleMapper getInstance() {
        if(roleMapper == null) {
            modelMapper = new ModelMapper();
            roleMapper = new RoleMapper();
        }
        return roleMapper;
    }

    public Role toModel(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    public RoleDTO toDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
