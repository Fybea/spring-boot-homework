package fybea.springmvc.controller;

import fybea.springmvc.model.PetDto;
import fybea.springmvc.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping()
    public List<PetDto> getPets() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    public PetDto getPetById(@PathVariable Long id) {
        return petService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto pet) {
        petService.save(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@RequestBody @Valid PetDto pet, @PathVariable Long id) {
        petService.update(pet);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetDto> deletePet(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.ok().build();
    }

}
