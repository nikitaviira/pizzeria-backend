package com.pizzeria.pizzaapp.domain.user;

import com.pizzeria.pizzaapp.config.exceptions.ServiceException;
import com.pizzeria.pizzaapp.dto.LoginRequestDto;
import com.pizzeria.pizzaapp.dto.LoginResponseDto;
import com.pizzeria.pizzaapp.dto.RegisterRequestDto;
import com.pizzeria.pizzaapp.dto.UserDto;
import com.pizzeria.pizzaapp.util.PasswordHash;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static com.pizzeria.pizzaapp.util.CredentialsValidator.validateCredentials;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordHash passwordHash;
  private final JwtGenerator jwtGenerator;

  public UserService(UserRepository userRepository, PasswordHash passwordHash, JwtGenerator jwtGenerator) {
    this.userRepository = userRepository;
    this.passwordHash = passwordHash;
    this.jwtGenerator = jwtGenerator;
  }

  public LoginResponseDto register(RegisterRequestDto requestDto) {
    validateCredentials(requestDto);

    Optional<User> foundUser = userRepository.findByEmail(requestDto.email);
    if (foundUser.isPresent()) {
      throw new ServiceException("user-already-exists");
    }

    User user = createNewUser(requestDto);
    return new LoginResponseDto(jwtGenerator.generate(user), new UserDto(user));
  }

  public LoginResponseDto login(LoginRequestDto requestDto) {
    User user = findAndValidatePassword(requestDto.email, requestDto.password);
    return new LoginResponseDto(jwtGenerator.generate(user), new UserDto(user));
  }

  private User findAndValidatePassword(String email, String password) {
    User user = find(email);
    if (!passwordHash.validatePassword(password, user.passwordHash)) {
      throw invalidCredentials();
    }
    return user;
  }

  private User find(String email) {
    return userRepository.findByEmail(email).orElseThrow(this::invalidCredentials);
  }

  private User createNewUser(RegisterRequestDto requestDto) {
    User newUser = newUser(requestDto);
    return userRepository.save(newUser);
  }

  private User newUser(RegisterRequestDto requestDto) {
    User user = new User();
    user.email = requestDto.email.toLowerCase();
    user.firstName = requestDto.firstName.toLowerCase();
    user.lastName = requestDto.lastName.toLowerCase();
    user.passwordHash = passwordHash.createHash(requestDto.password);
    user.roles = Set.of(User.Role.USER);
    user.createdAt = Instant.now();
    return user;
  }

  private ServiceException invalidCredentials() {
    return new ServiceException("wrong-credentials");
  }
}
