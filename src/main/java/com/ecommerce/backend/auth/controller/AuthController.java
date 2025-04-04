package com.ecommerce.backend.auth.controller;

import com.ecommerce.backend.auth.config.JWTTokenHelper;
import com.ecommerce.backend.auth.dto.LoginRequest;
import com.ecommerce.backend.auth.dto.RegistrationRequest;
import com.ecommerce.backend.auth.dto.RegistrationResponse;
import com.ecommerce.backend.auth.dto.UserToken;
import com.ecommerce.backend.auth.entities.User;
import com.ecommerce.backend.auth.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication =
                    UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUserName(), loginRequest.getPassword());

            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);

            if (authenticationResponse.isAuthenticated()) {
                User user = (User) authenticationResponse.getPrincipal();

                if (!user.isEnabled()) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                // generate JWT Token
                String token = jwtTokenHelper.generateToken(user.getEmail());
                UserToken userToken = UserToken.builder().token(token).build();
                return new ResponseEntity<>(userToken, HttpStatus.OK);
            }

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        RegistrationResponse registrationResponse = registrationService.createUser(request);

        return new ResponseEntity<>(registrationResponse,
                registrationResponse.getCode() == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> map) {
        String userName = map.get("userName");
        String code = map.get("code");

        User user = (User) userDetailsService.loadUserByUsername(userName);
        if (null != user && user.getVerificationCode().equals(code)) {
            registrationService.verifyUser(userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // adding by my own
    @GetMapping("/status")
    public ResponseEntity<?> checkAuthStatus(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return ResponseEntity.ok().body("User is authenticated: " + userDetails.getUsername());
        }
        return ResponseEntity.status(401).body("User is not authenticated");
    }
}
