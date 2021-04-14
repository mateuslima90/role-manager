package io.mtkh.rolemanager.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class RoleDTO {

    private String id;

    @NotNull(message = "Name cannot be null")
    @NotBlank
    @Size(min = 4)
    private String roleName;

    private String description;

    private Set<Transaction> transactions;

    public RoleDTO() {

    }

    public RoleDTO(String id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.transactions = new HashSet();
    }

    public RoleDTO(String id, String roleName, String description, Set<Transaction> transactions) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
