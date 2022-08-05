package fr.fms.apitrainings.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.fms.apitrainings.entities.Users;
import fr.fms.apitrainings.security.JwtUtils;
import fr.fms.apitrainings.security.payload.JwtResponse;
import fr.fms.apitrainings.security.payload.LoginRequest;
import fr.fms.apitrainings.service.ImplUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ImplUserService implUserService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(JwtUtils.SECRET);
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);
        Users users = implUserService.findByEmail(user.getUsername());
        JwtResponse response = new JwtResponse(accessToken, users);
        return ResponseEntity.ok(response);
    }
}
