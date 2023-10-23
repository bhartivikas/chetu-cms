package com.clientmanagement.app.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.clientmanagement.app.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomerRepository customerRepository;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// PUBLIC URL
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/signup")
						.permitAll()
						.anyRequest()
						.authenticated())
				.httpBasic(Customizer.withDefaults())
				// Custom Login Page
				.formLogin(form -> form
						.loginPage("/login")
						// After Successful login
						.defaultSuccessUrl("/home")
						.permitAll())
				// Logout Success URL
				.logout(logout -> logout
						.logoutSuccessUrl("/login"));
		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		// UserDetailsService is a class which return the User object
		// Here we are overriding #[UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;]
		// Here UserDetailsService is considered as a Functional interface
		return email -> {
			final var user = this.customerRepository
					.findByEmail(email)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
			return new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN")));
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		// It is used to encode and decode user secrete
		return new BCryptPasswordEncoder();
	}

}