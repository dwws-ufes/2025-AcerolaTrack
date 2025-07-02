package br.ufes.progweb.acerolatrack.core.service.impl;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Endpoint
@AnonymousAllowed // permite chamadas anônimas; remova se quiser bloquear usuários não logados
public class CurrentUserService {

    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            return new UserInfo(authentication.getName());
        } else {
            return null; // ou lance exceção se preferir
        }
    }

    public record UserInfo(String username) {}
}
