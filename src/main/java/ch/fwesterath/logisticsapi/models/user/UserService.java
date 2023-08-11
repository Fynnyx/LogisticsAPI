package ch.fwesterath.logisticsapi.models.user;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public User getUserById(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "User not found with id " + id);
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "User not found with username " + username);
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public User createUser(User user) {
        try {
            // Hash the user's password before saving
            user.setPasswordHash(user.getPassword());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public User updateUser(User user) {
        try {
            // Check if the user exists
            Long userId = user.getId();
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "User not found with id " + userId);
            }

            // Hash the updated password before saving
            user.setPasswordHash(user.getPassword());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                userRepository.deleteById(id);
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "User not found with id " + id);
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Boolean existsByUsername(String username) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            return optionalUser.isPresent();
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Boolean existsById(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            return optionalUser.isPresent();
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
