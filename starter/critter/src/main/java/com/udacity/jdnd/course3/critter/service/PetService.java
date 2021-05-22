package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.List;

public interface PetService {

    Pet save(Pet pet);
    List<Pet> getAll();
    Pet getById(Long id);
    List<Pet> getByOwnerId(Long id);
}
