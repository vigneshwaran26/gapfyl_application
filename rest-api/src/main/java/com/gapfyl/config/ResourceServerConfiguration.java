package com.gapfyl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "gapfyl";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/api/1.0/registrations/**").permitAll()
                .antMatchers("/api/1.0/users/forgot-password").permitAll()
                .antMatchers("/api/1.0/users/reset-password/**").permitAll()
                .antMatchers("/api/1.0/products/fetch/**").permitAll()
                .antMatchers("/api/1.0/courses/fetch/**").permitAll()
                .antMatchers("/api/1.0/courses/search/**").permitAll()
                .antMatchers("/api/1.0/lookups/fetch/**").permitAll()
                .antMatchers("/api/1.0/ratings/fetch/**").permitAll()
                .antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**").permitAll()
                .anyRequest().authenticated();
    }
}
