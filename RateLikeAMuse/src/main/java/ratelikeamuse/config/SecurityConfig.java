package ratelikeamuse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita a configuração de segurança web do Spring
public class SecurityConfig {

    // Essa função criptografa as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cadeia de filtros de segurança do Spring Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desabilitar CSRF é comum para aplicações web simples, mas estude sobre em produção
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // Permite acesso PÚBLICO à página inicial, registro, e arquivos estáticos (CSS, imagens)
                .requestMatchers("/", "/register", "/css/**", "/images/**").permitAll()
                
                // APENAS USUÁRIOS COM A FUNÇÃO "ADMIN" PODEM ACESSAR ESTAS URLS
                .requestMatchers("/movies/new", "/movies/save", "/movies/edit/**", "/movies/delete/**").hasRole("ADMIN")
                
                // Exige que o usuário esteja AUTENTICADO para acessar qualquer outra página
                .anyRequest().authenticated()
            )
            // Habilita e configura o formulário de login gerenciado pelo Spring Security
            .formLogin(form -> form
                // Diz ao Spring que nossa página de login está na URL "/" (raiz do site)
                .loginPage("/")
                // A URL que o formulário HTML envia os dados para é "/login"
                .loginProcessingUrl("/login")
                // Se o login for bem-sucedido, o usuário é enviado para "/movies"
                .defaultSuccessUrl("/movies", true)
                // Se o login falhar, o usuário volta para a raiz com um parâmetro de erro
                .failureUrl("/?error=true")
                .permitAll() // Permite que todos acessem a página de login
            )
            // Habilita e configura o logout gerenciado pelo Spring Security
            .logout(logout -> logout
                // A URL para acionar o logout será "/logout"
                .logoutUrl("/logout")
                // Após o logout, envia o usuário para a raiz com um parâmetro de sucesso
                .logoutSuccessUrl("/?logout")
                .permitAll()
            );

        return http.build();
    }
}