package com.fakebank.main.modules.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fakebank.main.modules.dto.Auth.AuthUserRequestDTO;
import com.fakebank.main.modules.dto.Auth.AuthUserResponseDTO;
import com.fakebank.main.modules.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthUserService {

    @Value("${security.token.secret.user}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserResponseDTO login(AuthUserRequestDTO userRequestDTO) throws AuthenticationException {
        var user = this.userRepository.findByEmail(userRequestDTO.email())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        var passwordMatches = this.passwordEncoder.matches(userRequestDTO.password(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException("Invalid password");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("jwt_issuer")
                .withSubject(user.getId().toString())
                .withClaim("roles", Arrays.asList("USER"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        return AuthUserResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();
    }
}
