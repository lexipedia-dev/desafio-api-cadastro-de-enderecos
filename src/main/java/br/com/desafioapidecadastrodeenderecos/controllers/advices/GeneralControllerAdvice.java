package br.com.desafioapidecadastrodeenderecos.controllers.advices;

import br.com.desafioapidecadastrodeenderecos.controllers.UserController;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice(annotations = RestController.class)
public class GeneralControllerAdvice {

    private StringWriter sw = new StringWriter();
    private PrintWriter pw = new PrintWriter (sw);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ProblemDetail> methodArgumentNotValid(MethodArgumentNotValidException e){
        e.printStackTrace(pw);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(400), sw.toString());
        problemDetail.setTitle("some data is being sent incorrectly : " + e.getMessage());
        sw.getBuffer().delete(0, sw.getBuffer().length());
        return ResponseEntity.status(HttpStatus.valueOf(400)).body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ProblemDetail> constraintViolation(ConstraintViolationException e){
        e.printStackTrace(pw);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(400), sw.toString());
        problemDetail.setTitle(e.getMessage());
        sw.getBuffer().delete(0, sw.getBuffer().length());
        return ResponseEntity.status(HttpStatus.valueOf(400)).body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ProblemDetail> entityNotFound(EntityNotFoundException e){
        e.printStackTrace(pw);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(404), sw.toString());
        problemDetail.setTitle(e.getMessage());
        sw.getBuffer().delete(0, sw.getBuffer().length());
        return ResponseEntity.status(HttpStatus.valueOf(404)).body(problemDetail);
    }
}
