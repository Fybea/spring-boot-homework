package fybea.springmvc.controller;

import fybea.springmvc.model.UserDto;
import fybea.springmvc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto user, @PathVariable Long id) {
        userService.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
