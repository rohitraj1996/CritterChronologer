package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Pet save(Pet pet) {
        Pet savedPed =  this.petRepository.save(pet);
        Customer customer = savedPed.getCustomer();

        if (customer != null) {
            if (customer.getPets() != null) {
                customer.getPets().add(savedPed);
            }
            this.customerRepository.save(customer);
        }

        return savedPed;
    }

    @Override
    public List<Pet> getAll() {
        return this.petRepository.findAll();
    }

    @Override
    public Pet getById(Long id) {
        return this.petRepository.findById(id).orElseThrow(PetNotFoundException::new);
    }

    @Override
    public List<Pet> getByOwnerId(Long id) {
        List<Pet> pets = this.petRepository.findByCustomerId(id);

        if (pets == null || pets.isEmpty()) {
            throw new PetNotFoundException();
        }

        return pets;
    }
}
