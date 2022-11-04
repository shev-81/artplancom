package com.example.core.services;

import com.example.core.entities.Animal;
import com.example.core.repositories.AnimalsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalsService {

    @Value("${miniO.container}")
    String bucket;

    private final DataFileService <Animal> fileService;

    private final AnimalsRepository animalsRepository;

    public AnimalsService(@Qualifier("DataFileServiceMiniO") DataFileService<Animal> fileService, AnimalsRepository animalsRepository) {
        this.fileService = fileService;
        this.animalsRepository = animalsRepository;
    }

    public List<Animal> findByUserName (String name) {
        return animalsRepository.findByUserName(name);
    }

    public Optional<Animal> findById(Long id) {
        return animalsRepository.findById(id);
    }

    public void delAnimalById(Long id) {
        animalsRepository.deleteById(id);
    }

    public Animal createNewAnimal(Animal animal){
        fileService.putObject(animal);
        return animalsRepository.save(animal);
    }

    public Animal save(Animal animal){
        return animalsRepository.save(animal);
    }
}
