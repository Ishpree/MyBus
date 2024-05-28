package bus.example.busticket.config;

import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.web.context.SecurityContextHolderFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    SecurityContext securityContext;

    //Spring Security's DaoAuthenticationProvider is responsible for password verification
//    When you configure your DaoAuthenticationProvider with the passwordEncoder bean, Spring Security injects
//    that encoder into the provider.
//During the authentication process, the DaoAuthenticationProvider retrieves the user's hashed password from the
// fetched UserDetails object.
//It then delegates the password comparison task to the injected passwordEncoder.
//The BCryptPasswordEncoder implements the PasswordEncoder interface in Spring Security.
//This interface defines a single method: matches(CharSequence rawPassword, String encodedPassword).
//When the DaoAuthenticationProvider calls this method, it passes the user-entered password (in plain text) as the
// rawPassword and the user's hashed password retrieved from UserDetails as the encodedPassword.
//The BCryptPasswordEncoder internally performs the comparison using the bcrypt hashing algorithm.
// It checks if the user-entered password, after being hashed with the same salt used for the original password during registration,
// matches the stored hashed password.
// After authentication, it sends an authenication object to the controller automatically
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(customUserDetailsService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationProvider... providers) throws Exception {
//        return new ProviderManager(providers);
//    }


    //    http.formLogin() allows you to configure various aspects of form-based login through its chained methods.It automatically
    //triggers the dao authentication provider.
//    loginPage(String url): Sets the URL of the login page where users can access the form to enter their credentials.
//   permitAll(): Makes the login page accessible to anyone without requiring authentication. This is
//   essential because users need to reach the login page to initiate the authentication process.
    // .requestMatchers("/api/Registration").permitAll()
//                        .anyRequest().authenticated()); this statement means only the
    //request /api/Registration will be permitted without any authentication, any other request
    //will need authentication
    //This means Spring Security doesn't rely on sessions to store authentication information(the session management part)
    //Spring Security uses the SecurityContextHolder to store the current security context.
    //During successful login, the authentication details are set in the security context using SecurityContextHolder.
    //securitycontextpersistence filter is not there by default in the latest version, needs to be manually used
    //I have used the securitycontextholderfilter instead
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)).authorizeRequests((requests) -> requests
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).
                securityContext((securityContext) -> securityContext
                .requireExplicitSave(true)
        ).

        csrf(AbstractHttpConfigurer::disable).cors(cors -> cors.configurationSource(corsConfigurationSource()));
        //        http.formLogin(form -> form
//               .successHandler(new CustomAuthenticationSuccessHandler()).permitAll());
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration ccfg = new CorsConfiguration();
                ccfg.setAllowedOrigins(Arrays.asList("http://localhost:3002"));
                ccfg.setAllowedMethods(Collections.singletonList("*"));
                ccfg.setAllowCredentials(true);
                ccfg.setAllowedHeaders(Collections.singletonList("*"));
                ccfg.setExposedHeaders(Arrays.asList("Authorization"));
                ccfg.setMaxAge(3600L);
                return ccfg;

            }
        };

        }


    }

//
