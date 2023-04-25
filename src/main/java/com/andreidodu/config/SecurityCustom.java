//package com.andreidodu.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityCustom {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.cors().disable();
//        http.csrf().disable();
//        http
//                .authorizeRequests()
//                .requestMatchers("/test").authenticated()
//                .requestMatchers("/**").permitAll();
//        return http.build();
//    }
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("user")
////                .password("password")
////                .roles("USER")
////                .build();
////        return new InMemoryUserDetailsManager(user);
////    }
//}
//
