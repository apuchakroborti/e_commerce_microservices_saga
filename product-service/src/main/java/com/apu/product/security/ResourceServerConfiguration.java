//package com.apu.product.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);
//
//    private static final String[] AUTH_WHITELIST = {
//            // -- swagger ui
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v2/api-docs",
//            "/webjars/**"
//    };
//    private static final String RESOURCE_ID = "service-api";
//    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
//    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
//    private static final String SECURED_PATTERN = "/api/**";
//    private static final String PUBLIC_URL = "/api/forgot-password/**";
//    private static final String CHECK_URL = "/api/login/**";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        logger.info("resources called");
//        resources.resourceId(RESOURCE_ID);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        logger.info("http called");
////        http.cors().and();
//        http.csrf().disable().requestMatchers()
//                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
//                .antMatchers(PUBLIC_URL).permitAll()
//                .antMatchers(CHECK_URL).permitAll()
//                .antMatchers("/api/provident-fund/**").permitAll()
//                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
//                .anyRequest().access(SECURED_READ_SCOPE);
//    }
//}
