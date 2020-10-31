package ehb.applicationframeworkwebshop.SecurityConfig;

import ehb.applicationframeworkwebshop.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //so spring security can work
public class SecurityConfig extends WebSecurityConfigurerAdapter { //overrides some methodes to specify the config of spring security

    @Autowired
    CustomUserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/register**",
                        "/js/**",
                        "/css/**",                     //says wich url does not need auth
                        "/img/**", "/images/**",
                        "/webjars/**",
                        "/",
                        "/home","/categorie","/search").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") //maps /login to login
                .defaultSuccessUrl("/?login", true)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout");
    }

    @Bean
    public BCryptPasswordEncoder pwndEncoder(){
        return new BCryptPasswordEncoder();
    } //changes encrypts plain text password

    @Bean
    public DaoAuthenticationProvider authenticationProvider() { //give explanation
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //sets the custom userservice in the authprovider
        auth.setPasswordEncoder(pwndEncoder()); //sets bcrypt as encryptor
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider()); //set the changes made above
    }
}
