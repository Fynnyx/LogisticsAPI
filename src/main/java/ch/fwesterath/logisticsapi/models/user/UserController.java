package ch.fwesterath.logisticsapi.models.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("s")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("s/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("s/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("s/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
