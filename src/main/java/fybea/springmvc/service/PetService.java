package fybea.springmvc.service;

import fybea.springmvc.exception.ResourceNotFoundException;
import fybea.springmvc.model.PetDto;
import fybea.springmvc.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetService {

    private final UserService userService;

    private final Map<Long, PetDto> pets = new HashMap<>();
    private Long nextId = 1L;

    public PetService(UserService userService) {
        this.userService = userService;
    }


    public PetDto findById(Long id) {
        if (!pets.containsKey(id)) {
            throw new ResourceNotFoundException("Pet not found with id: " + id);
        }
        return pets.get(id);
    }

    public List<PetDto> findAll() {
        return new ArrayList<>(pets.values());
    }

    public PetDto save(PetDto pet) {
        UserDto userDto = userService.findById(pet.getUserId());
        userDto.getPets().add(pet);
        pet.setId(nextId++);
        pets.put(pet.getId(), pet);
        return pet;
    }

    public PetDto update(PetDto pet) {
        pets.put(pet.getId(), pet);
        return pet;
    }

    public void delete(Long id) {
        PetDto pet = pets.get(id);
        UserDto userDto = userService.findById(pet.getUserId());
        userDto.getPets().remove(pet);
        pets.remove(id);
    }
}
