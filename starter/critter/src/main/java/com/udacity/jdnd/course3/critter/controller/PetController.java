package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired private ModelMapper modelMapper;
    @Autowired private PetServiceImpl petService;
    @Autowired private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet newPet = this.convertToEntity(petDTO);
        Pet savedPet = this.petService.save(newPet);
        return this.convertToDto(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = this.petService.getById(petId);
        return this.convertToDto(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.petService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.petService.getByOwnerId(ownerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Pet convertToEntity(PetDTO petDTO) {

        Customer customer = new Customer();
        Pet pet = this.modelMapper.map(petDTO, Pet.class);
        long customerId = petDTO.getOwnerId();
        if (customerId != 0L) {
            customer = this.customerService.getById(customerId);
        }
        pet.setCustomer(customer);

        return pet;
    }

    private PetDTO convertToDto(Pet pet) {
        PetDTO petDTO = this.modelMapper.map(pet, PetDTO.class);
        Customer customer = pet.getCustomer();

        if (customer != null) {
            petDTO.setOwnerId(customer.getId());
        }
        return petDTO;
    }
}
