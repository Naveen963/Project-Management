package com.projectmanagement.config;

import com.projectmanagement.model.AppRole;
import com.projectmanagement.model.Role;
import com.projectmanagement.model.User;
import com.projectmanagement.repository.RoleRepository;
import com.projectmanagement.repository.UserRepository;
import com.projectmanagement.security.jwt.AuthEntryPointJwt;
import com.projectmanagement.security.jwt.AuthTokenFilter;
import com.projectmanagement.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Set;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private DataSource dataSource;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizeHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return  authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf->csrf.disable())
                .exceptionHandling(exception->exception.authenticationEntryPoint(unauthorizeHandler))

                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(requests->
                                requests.requestMatchers("/api/auth/**").permitAll()
                                        .requestMatchers("/v3/api-docs/**").permitAll()
                                        .requestMatchers("/h2-console/**").permitAll()
                                        .requestMatchers("/swagger-ui/**").permitAll()
//                    .requestMatchers("/api/public/**").permitAll()
                                        .requestMatchers("/api/admin/**").permitAll()
                                        .requestMatchers("/api/test/**").permitAll()
                                        .requestMatchers("/images/**").permitAll()
                                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());


        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()
                )
        );

        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web-> web.ignoring()
                .requestMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
        );
    }


    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Retrieve or create roles
            Role adminRole = roleRepository.findByRoleName(AppRole.ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Role developerRole = roleRepository.findByRoleName(AppRole.DEVELOPER)
                    .orElseGet(() -> {
                        Role newDeveloperRole = new Role(AppRole.DEVELOPER);
                        return roleRepository.save(newDeveloperRole);
                    });

            Role testerRole = roleRepository.findByRoleName(AppRole.TESTER)
                    .orElseGet(() -> {
                        Role newtesterRole = new Role(AppRole.TESTER);
                        return roleRepository.save(newtesterRole);
                    });

            Set<Role> developerRoles = Set.of(developerRole);
            Set<Role> testerRoles = Set.of(testerRole);
            Set<Role> adminRoles = Set.of(developerRole, testerRole, adminRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("developer")) {
                User user1 = new User("developer", "developer@example.com", passwordEncoder.encode("password1"));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("tester")) {
                User seller1 = new User("tester", "tester@example.com", passwordEncoder.encode("password2"));
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("developer").ifPresent(developer -> {
                developer.setRoles(developerRoles);
                userRepository.save(developer);
            });

            userRepository.findByUserName("tester").ifPresent(tester -> {
                tester.setRoles(testerRoles);
                userRepository.save(tester);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });
        };
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//    @Bean
//    public CommandLineRunner initData(UserDetailsService userDetailsService){
//
//        return args->{
//            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;
//            UserDetails user1 = User.withUsername("user1")
//                    .password(passwordEncoder().encode("password1"))
//                    .roles("USER")
//                    .build();
//            UserDetails admin = User.withUsername("admin")
//                    .password(passwordEncoder().encode("admin"))
//                    .roles("ADMIN")
//                    .build();
//            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//            userDetailsManager.createUser(user1);
//            userDetailsManager.createUser(admin);
//        };
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}


