package com.ecommerce.backend.auth.controller;

import com.ecommerce.backend.auth.config.JWTTokenHelper;
import com.ecommerce.backend.auth.entities.User;
import com.ecommerce.backend.auth.service.OAuth2Service;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/oauth2")
@Slf4j
public class OAuth2Controller {
    @Autowired
    OAuth2Service oAuth2Service;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @GetMapping("/success")
    public void callbackOAuth2(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response) throws IOException {

        String userName = oAuth2User.getAttribute("email");
        User user = oAuth2Service.getUser(userName);
        if (user == null) {
            user = oAuth2Service.createUser(oAuth2User, "google");
        }

        String token = jwtTokenHelper.generateToken(user.getUsername());

        log.info("Generated Token: {} ", token);

        response.sendRedirect("http://localhost:5173/oauth2/callback?token=" + token);
//        response.sendRedirect("http://localhost:5173/oauth2/callback?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8));

    }
}
