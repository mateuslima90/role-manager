package io.mtkh.rolemanager.service;

import io.mtkh.rolemanager.configuration.RoleMapper;
import io.mtkh.rolemanager.domain.Role;
import io.mtkh.rolemanager.domain.RoleDTO;
import io.mtkh.rolemanager.domain.Transaction;
import io.mtkh.rolemanager.exception.RoleAlreadyExistsException;
import io.mtkh.rolemanager.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper = RoleMapper.getInstance();

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDTO getRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .map(roleMapper::toDTO)
                .orElseThrow();
    }

    public List<RoleDTO> getAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO createRole(RoleDTO role) {
        verifyIfExistRole(role.getRoleName());

        Role r = roleMapper.toModel(role);
        Role savedRole = roleRepository.save(r);
        return roleMapper.toDTO(savedRole);
    }

    public RoleDTO updateRole(RoleDTO role) throws Exception {
        Role r = roleMapper.toModel(role);
        RoleDTO persistedRole = getRoleByRoleName(r.getRoleName());

        if(persistedRole.getId() == null){
            throw new Exception("Role not found with this roleName");
        }

        Role newRole = new Role();
        newRole.setId(persistedRole.getId());
        newRole.setRoleName(persistedRole.getRoleName());
        newRole.setDescription(role.getDescription());
        return roleMapper.toDTO(roleRepository.save(newRole));
    }

    public Role updateTransactionInRoleByRoleName(Transaction transaction, String roleName){
        Role role = this.roleRepository.findByRoleName(roleName).orElseThrow();
        role.addTransaction(transaction);
        return this.roleRepository.save(role);
    }

    public void deleteTransactionInRole(String transactionName, String roleName) {
        Role role = this.roleRepository.findByRoleName(roleName).orElseThrow();
        Set<Transaction> transactionList = role.getTransactions().stream()
                .filter(transaction -> !transaction.getTransactionName().equals(transactionName))
                .collect(Collectors.toSet());
        role.setTransactions(transactionList);
        this.roleRepository.save(role);
    }

    private void verifyIfExistRole(String roleName) {
        this.roleRepository.findByRoleName(roleName)
                .ifPresent(role -> { throw new RoleAlreadyExistsException(role.getRoleName()); });
    }
}
