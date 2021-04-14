package io.mtkh.rolemanager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Set;

@Document
public class Role {

    @Id
    private String id;

    @Indexed(unique = true)
    private String roleName;

    private String description;

    private Set<Transaction> transactions;

    public Role(){

    }

    public Role(String id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.transactions = new HashSet<>();
    }

    public Role(String id, String roleName, String description, Set<Transaction> transactions) {
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

    public void addTransaction(Transaction transaction) {
        if(transactions == null) {
            this.transactions = new HashSet();
            this.transactions.add(transaction);
        }
        this.transactions.add(transaction);
    }
}
