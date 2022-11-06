package com.example.core.services;

import com.example.core.converters.AnimalConverter;
import com.example.core.dtos.AnimalDto;
import com.example.core.entities.Animal;
import com.example.core.exceptions.ResourceNotFoundException;
import com.example.core.repositories.AnimalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnimalsService {

    @Value("${miniO.container}")
    String bucket;

    private final DataFileService <Animal> imgService;
    private final AnimalConverter animalConverter;
    private final AnimalsRepository animalsRepository;

    public List<AnimalDto> findByUserName (String name) {
        List<Animal> list = animalsRepository.findByUserName(name);
        List<AnimalDto> dtoList = list.stream().map(animalConverter::entityToDto).toList();
        return dtoList;
    }

    public AnimalDto findById(Long id) {
        Animal animal = animalsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found."));
        AnimalDto animalDto = animalConverter.entityToDto(animal);
        return animalDto;
    }

    public void delAnimalById(Long id) {
        animalsRepository.deleteById(id);
    }

    public Animal createNewAnimal(Animal animal){
        imgService.putObject(animal);
        return animalsRepository.save(animal);
    }

    public Animal save(Animal animal){
        return animalsRepository.save(animal);
    }
}
