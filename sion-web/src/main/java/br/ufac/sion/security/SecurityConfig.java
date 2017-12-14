/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
            .antMatchers("/login.xhtml", "/recuperaSenha.xhtml", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
            .antMatchers("/home.xhtml", "/AcessoNegado.xhtml", "/seguranca/**").authenticated()
            .antMatchers("/cargos/**", "/concursos/**", "/localidades/**", "/niveis/**", "/funcionarios/**", "/setores/**", 
                    "/vagas/**").hasAnyRole("ADMINISTRADORES")
            .antMatchers("/empresas/**", "/contas/**").hasAnyRole("ADMINISTRADORES","FINANCEIRO")
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