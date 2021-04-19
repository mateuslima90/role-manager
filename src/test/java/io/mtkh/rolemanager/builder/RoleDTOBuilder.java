package io.mtkh.rolemanager.builder;

import io.mtkh.rolemanager.domain.RoleDTO;
import io.mtkh.rolemanager.domain.Transaction;
import lombok.Builder;

import java.util.Set;

@Builder
public class RoleDTOBuilder {

    @Builder.Default
    private String id = "123";

    @Builder.Default
    private String roleName = "developer";

    @Builder.Default
    private String description = "developer";

    private Set<Transaction> transactions;

    public RoleDTO toDTO() {
        return new RoleDTO(id, roleName, description);
    }
}
