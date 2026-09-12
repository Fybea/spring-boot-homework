package fybea.springmvc.service;

import fybea.springmvc.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {


    private final Map<Long, UserDto> users = new HashMap<>();
    private Long nextId = 1L;


    public UserDto findById(Long id) {
        return users.get(id);
    }

    public List<UserDto> findAll() {
        return new ArrayList<>(users.values());
    }

    public UserDto save(UserDto user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
        user.setPets(new ArrayList<>());
        return user;
    }

    public UserDto update(UserDto user) {
        users.put(user.getId(), user);
        return user;
    }

    public void delete(Long id) {
        users.remove(id);
    }
}
