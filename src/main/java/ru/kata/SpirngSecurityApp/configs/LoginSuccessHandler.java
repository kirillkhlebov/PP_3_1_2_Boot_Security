package ru.kata.SpirngSecurityApp.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kata.SpirngSecurityApp.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal();

        String redirectURL;

        if (user.hasRole("ROLE_ADMIN")) {
            redirectURL = "/admin/admin";
        } else {
            redirectURL = "/user";
        }

        response.sendRedirect(redirectURL);
    }
}
