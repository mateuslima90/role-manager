package io.mtkh.rolemanager.controller;

import io.mtkh.rolemanager.domain.Role;
import io.mtkh.rolemanager.domain.UpdateRole;
import io.mtkh.rolemanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/transaction")
    public Role createTransaction(@RequestBody UpdateRole uRole) {
        return roleService.updateTransactionInRoleByRoleName(uRole.getTransaction(), uRole.getRoleName());
    }

    @DeleteMapping(value = "/transaction/{transactionName}/role/{roleName}")
    public void deleteTransactionInRole(@PathVariable("transactionName") String transactionName,
                                        @PathVariable("roleName") String roleName) {
        roleService.deleteTransactionInRole(transactionName, roleName);
    }
}
