package io.mtkh.rolemanager.builder;

import io.mtkh.rolemanager.domain.Transaction;
import lombok.Builder;

@Builder
public class TransactionsBuilder {

    @Builder.Default
    private String transactionName = "t1";

    @Builder.Default
    private String description = "create a customer";

    @Builder.Default
    private String risk = "High";

    public Transaction toModel() {
        return new Transaction(transactionName, description, risk);
    }
}
