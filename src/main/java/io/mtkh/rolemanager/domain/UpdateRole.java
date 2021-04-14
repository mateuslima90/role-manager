package io.mtkh.rolemanager.domain;

public class UpdateRole {

    private String roleName;

    private Transaction transaction;

    public UpdateRole(){

    }

    public UpdateRole(String roleName, Transaction transaction) {
        this.roleName = roleName;
        this.transaction = transaction;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
