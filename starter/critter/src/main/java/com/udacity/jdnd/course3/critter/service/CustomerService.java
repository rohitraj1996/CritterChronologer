package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);
    List<Customer> getAll();
    Customer getById(Long customerId);
    Customer getOwnerByPet(Long petId);
}
