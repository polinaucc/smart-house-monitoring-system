package ua.polina.smart_house_monitoring_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ua.polina.smart_house_monitoring_system.AccessDeniedHandlerCustom;
import ua.polina.smart_house_monitoring_system.entity.Role;

/**
 * The type Spring security web app config.
 */
@Primary
@Configuration
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {
    /**
     * Instantiates a new Spring security web app config.
     */
    public SpringSecurityWebAppConfig() {
        super();
    }

    /**
     * Configurers the HttpSecurity.
     *
     * @param httpSecurity the HttpSecurity to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable();

        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "auth/login", "/logout")
                .permitAll();

        httpSecurity
                .authorizeRequests()
                .antMatchers("/owner/**")
                .hasAuthority(Role.OWNER.getAuthority());

        httpSecurity
                .authorizeRequests()
                .antMatchers("/resident/**")
                .hasAnyAuthority(Role.OWNER.getAuthority(), Role.RESIDENT.getAuthority());

        httpSecurity
                .authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority(Role.ADMIN.getAuthority());

        httpSecurity
                .authorizeRequests()
                .and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerCustom("/wrong-page"));

        httpSecurity
                .authorizeRequests()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .failureUrl("/auth/login?error")
                .defaultSuccessUrl("/auth/default-success", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login?logout");
    }
}
