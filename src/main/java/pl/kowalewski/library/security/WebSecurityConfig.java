package pl.kowalewski.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import pl.kowalewski.library.user.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserService userDetailsService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> {
            try {
                authz
                        .requestMatchers(HttpMethod.GET, "/bootstrap/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/fontawesome/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/css/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/webfonts/**").permitAll()
                        .anyRequest().authenticated();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        http.formLogin(login -> login
                                .loginPage("/login").loginProcessingUrl("/perform_login").defaultSuccessUrl("/", false).permitAll())
                        .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll()).sessionManagement(management -> management.sessionFixation().migrateSession().enableSessionUrlRewriting(false));

        return http.build(); 
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
}
