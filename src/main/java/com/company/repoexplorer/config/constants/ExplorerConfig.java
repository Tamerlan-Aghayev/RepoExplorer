package com.company.repoexplorer.config.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("repo")
public final class ExplorerConfig {
    public final static int MAX_PAGE_SIZE =100;
}