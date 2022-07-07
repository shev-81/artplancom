package com.example.core.controllers;

import com.example.core.converters.AnimalConverter;
import com.example.core.dtos.AnimalDto;
import com.example.core.entities.Animal;
import com.example.core.exceptions.ResourceNotFoundException;
import com.example.core.services.AnimalsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/animals")
public class AnimalsController {

    private final AnimalsService animalsService;
    private final AnimalConverter animalConverter;

    @GetMapping("/{id}")
    public AnimalDto getAnimalById(@PathVariable Long id) {
        Animal animal = animalsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal not found, id: " + id));
        return animalConverter.entityToDto(animal);
    }

    @GetMapping("/foruser/{user}")
    public List<AnimalDto> getAllAnimals(@PathVariable String user) {
        List<Animal> animalList = animalsService.findByUserName(user);
        return animalList.stream().map(animalConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping()
    public void createNewAnimal(@RequestBody AnimalDto animalDto){
        Animal animal = animalConverter.dtoToEntity(animalDto);
        animalsService.createNewAnimal(animal);
    }

    @PutMapping()
    public void updateAnimal(@RequestBody AnimalDto animalDto){
        Animal animal = animalConverter.dtoToEntity(animalDto);
        animalsService.save(animal);
    }

    @DeleteMapping("/{id}")
    public void delAnimal(@PathVariable Long id){
        animalsService.delAnimalById(id);
    }
}
