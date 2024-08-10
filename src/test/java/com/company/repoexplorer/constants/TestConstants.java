package com.company.repoexplorer.constants;

import org.springframework.stereotype.Component;

@Component
public class TestConstants {
    public final static String VALID_USERNAME="torvalds";
    public final static String INVALID_USERNAME="nonGithubUser";
    public final static String REPO_NAME="Linux";
    public final static int ALL_REPO_COUNT=7;
    public final static int NON_FORK_REPO_COUNT=4;
    public final static int BRANCH_COUNT=7;
}

