package com.example.core.converters;


import com.example.core.dtos.AnimalDto;
import com.example.core.entities.Animal;
import com.example.core.services.DataFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AnimalConverter {

    private final DataFileService<Animal> imgService;

    public Animal dtoToEntity(AnimalDto animalDto) {
        return new Animal(animalDto.getId(),animalDto.getUsername(),animalDto.getViewAnimal(),animalDto.getDateBorn(),animalDto.getGender(),animalDto.getNickName());
    }

    public AnimalDto entityToDto(Animal animal) {
        return new AnimalDto(animal.getId(), animal.getUsername(), animal.getViewAnimal(), animal.getDateBorn(), animal.getGender(), animal.getNickName(), imgService.getUrlObject(animal));
    }
}
