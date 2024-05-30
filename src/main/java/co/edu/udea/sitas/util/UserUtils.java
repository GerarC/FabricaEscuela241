package co.edu.udea.sitas.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserUtils {

    private UserUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>("Mensaje: " + message, httpStatus);
    }
}
