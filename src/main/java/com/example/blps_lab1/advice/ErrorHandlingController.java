package com.example.blps_lab1.advice;

import com.example.blps_lab1.dto.response.ErrorResponse;
import com.example.blps_lab1.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class,
            UsernameNotFoundException.class, BadCredentialsException.class,
            UserAlreadyExistException.class, RoleNotFoundException.class, DishNotFoundException.class,
            CuisineNotFoundException.class, TasteNotFoundException.class, RecipeNotFoundException.class,
            NotOwnerException.class, IngredientNotFoundException.class, DishAlreadyExistException.class,
            TasteAlreadyExistException.class, IngredientAlreadyExistException.class, CuisineAlreadyExistException.class
    })

    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
