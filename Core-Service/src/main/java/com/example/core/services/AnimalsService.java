package com.example.core.services;

import com.example.core.entities.Animal;
import com.example.core.repositories.AnimalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalsService {

    private final AnimalsRepository animalsRepository;

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
        return animalsRepository.save(animal);
    }

    public Animal save(Animal animal){
        return animalsRepository.save(animal);
    }
}
