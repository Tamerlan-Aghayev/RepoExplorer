package com.company.repoexplorer.model.client;

import java.util.List;

public record ClientDTO() {

    public record Repository(
        String name,
        boolean fork,
        Owner owner,
        List<Branch> branches
    ) {}

    public record Owner(
        String login
    ) {}

    public record Branch(
        String name,
        Commit commit
    ) {}

    public record Commit(
        String sha
    ) {}
}