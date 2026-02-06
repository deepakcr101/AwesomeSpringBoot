package com.deepak.springtestingdemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    public SecurityWebFilterChain  securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http.
              a
    }
}
