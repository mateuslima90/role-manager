package io.mtkh.rolemanager.controller;

import io.mtkh.rolemanager.domain.RoleDTO;
import io.mtkh.rolemanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/roles")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(value = "/role/{roleName}")
    public RoleDTO getRoleByRoleName(@PathVariable("roleName") String roleName) {
        return roleService.getRoleByRoleName(roleName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/role")
    public RoleDTO createRole(@Valid @RequestBody RoleDTO role) {
        return roleService.createRole(role);
    }

    @PutMapping(value = "/role")
    public RoleDTO updateRole(@RequestBody RoleDTO role) throws Exception {
        return roleService.updateRole(role);
    }
}
