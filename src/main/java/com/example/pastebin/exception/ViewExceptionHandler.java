package com.example.pastebin.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ViewExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundForView(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericForView(Exception ex, Model model) {
        model.addAttribute("message", "Something went wrong");
        return "error";
    }
}
