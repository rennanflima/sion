/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author rennan.lima
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AppUserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
        jsfLoginEntry.setLoginFormUrl("/login.xhtml");
        jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());

        JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
        jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
        jsfDeniedEntry.setContextRelative(true);

        http
            .csrf().disable()
            .headers().frameOptions().sameOrigin()
            .and()
        
        .authorizeRequests()
            .antMatchers("/login.xhtml", "/recuperaSenha.xhtml", "/Erro.xhtml", "/javax.faces.resource/**", "/alterarSenha.xhtml", "/cadastroCandidato.xhtml", "/404.xhtml").permitAll()
            .antMatchers("/dashboard.xhtml", "/AcessoNegado.xhtml","/seguranca/**").authenticated()
            .antMatchers("/candidatos/**","/concursos/**" ).hasAnyRole("CANDIDATO")
            .antMatchers("/**").denyAll()
            .and()
        
        .formLogin()
            .loginPage("/login.xhtml")
            .failureUrl("/login.xhtml?invalid=true")
            .and()

        .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .and()

        .exceptionHandling()
            .accessDeniedPage("/AcessoNegado.xhtml")
            .authenticationEntryPoint(jsfLoginEntry)
            .accessDeniedHandler(jsfDeniedEntry);        
                
    }
}
