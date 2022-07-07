package com.example.core.converters;


import com.example.core.dtos.AnimalDto;
import com.example.core.entities.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalConverter {
    public Animal dtoToEntity(AnimalDto animalDto) {
        return new Animal(animalDto.getId(),animalDto.getUsername(),animalDto.getViewAnimal(),animalDto.getDateBorn(),animalDto.getGender(),animalDto.getNickName());
    }

    public AnimalDto entityToDto(Animal animal) {
        return new AnimalDto(animal.getId(), animal.getUsername(), animal.getViewAnimal(), animal.getDateBorn(), animal.getGender(), animal.getNickName());
    }
}
