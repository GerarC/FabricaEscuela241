package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.constants.UserConstants;
import co.edu.udea.sitas.domain.model.User;
import co.edu.udea.sitas.persistence.UserRepository;
import co.edu.udea.sitas.security.CustomerDetailsService;
import co.edu.udea.sitas.security.jwt.JwtUtil;
import co.edu.udea.sitas.services.UserService;
import co.edu.udea.sitas.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceJPA(UserRepository userRepository, AuthenticationManager authenticationManager, CustomerDetailsService customerDetailsService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customerDetailsService = customerDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Registro interno de un usuario {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userRepository.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userRepository.save(getUserFromMap(requestMap));
                    return UserUtils.getResponseEntity("Usuario registrado con éxito", HttpStatus.CREATED);
                } else {
                    return UserUtils.getResponseEntity("El usuario con ese email ya existe", HttpStatus.BAD_REQUEST);
                }
            } else {
                return UserUtils.getResponseEntity(UserConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            log.error("Error en el registro de usuario", exception);
            return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Dentro de login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (authentication.isAuthenticated()) {
                if (customerDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<>("{\"token\":\"" + jwtUtil.generateToken(customerDetailsService.getUserDetail().getEmail(), customerDetailsService.getUserDetail().getRole()) + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Usuario no activado\"}", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception exception) {
            log.error("Error en login", exception);
            return new ResponseEntity<>("{\"message\":\"Credenciales incorrectas\"}", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("{\"message\":\"Credenciales incorrectas\"}", HttpStatus.BAD_REQUEST);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("nombre") &&
                requestMap.containsKey("numeroDeContacto") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setNombre(requestMap.get("nombre"));
        user.setNumeroDeContacto(requestMap.get("numeroDeContacto"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
