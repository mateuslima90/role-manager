package io.mtkh.rolemanager.domain;

public class Transaction {

    private String transactionName;

    private String description;

    private String risk;

    public Transaction(){

    }

    public Transaction(String transactionName, String description, String risk) {
        this.transactionName = transactionName;
        this.description = description;
        this.risk = risk;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }
}
