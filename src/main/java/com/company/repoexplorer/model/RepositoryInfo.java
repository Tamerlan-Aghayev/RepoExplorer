package com.company.repoexplorer.model;


import java.util.List;

public record RepositoryInfo(String name, String ownerLogin, List<BranchInfo> branches) {

}