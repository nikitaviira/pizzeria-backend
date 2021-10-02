package com.pizzeria.pizzaapp.util;

import com.pizzeria.pizzaapp.config.exceptions.ServiceException;
import com.pizzeria.pizzaapp.dto.RegisterRequestDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsValidator {
  @SuppressWarnings("checkstyle:LineLength")
  private static final String EMAIL_REGEX = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[A-Za-z0-9-]*[A-Za-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
  private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!/.,*()@%&]).{8,30}$";
  private static final String NAME_REGEX = "^[a-zA-Z\\s]+$";

  public static void validateCredentials(RegisterRequestDto requestDto) {
    validateName(requestDto.firstName);
    validateName(requestDto.lastName);
    validateEmail(requestDto.email);
    validatePassword(requestDto.password);
  }

  private static void validatePassword(String password) {
    Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
    if (password.length() < 8) {
      throw new ServiceException("password-too-short");
    } else if (password.length() > 30) {
      throw new ServiceException("password-too-long");
    } else {
      Matcher matcher = passwordPattern.matcher(password);
      if (!matcher.find()) {
        throw new ServiceException("password-at-least-one-cap-char-and-num");
      }
    }
  }

  private static void validateEmail(String email) {
    Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = emailPattern.matcher(email);
    if (!matcher.find()) {
      throw new ServiceException("email-wrong-format");
    }
  }

  private static void validateName(String name) {
    Pattern emailPattern = Pattern.compile(NAME_REGEX);
    Matcher matcher = emailPattern.matcher(name);
    if (!matcher.find()) {
      throw new ServiceException("name-wrong-format");
    }
  }
}
