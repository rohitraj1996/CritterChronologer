package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.service.EmployeeServiceImpl;
import com.udacity.jdnd.course3.critter.service.PetServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PetServiceImpl petService;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    EmployeeServiceImpl employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return this.convertToDto(this.customerService.save(this.convertToEntity(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return this.customerService.getAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return this.convertToDto(this.customerService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return this.convertToDto(this.employeeService.save(this.convertToEntity(employeeDTO)));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return this.convertToDto(this.employeeService.getById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.employeeService.setAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return this.employeeService.getAvailability(employeeDTO.getDate(), employeeDTO.getSkills()).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        List<Long> petIds = customerDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();

        // Set pets
        if (petIds != null) {
            petIds.stream().filter(petId -> petId != 0L).map(this.petService::getById).forEach(pets::add);
        }
        customer.setPets(pets);
        return customer;
    }

    private CustomerDTO convertToDto(Customer customer) {
        CustomerDTO customerDTO = this.modelMapper.map(customer, CustomerDTO.class);
        List<Pet> pets = customer.getPets();

        // Set pet ids
        if (pets != null) {
            customerDTO.setPetIds(pets.stream().filter(Objects::nonNull)
                    .map(Pet::getId).collect(Collectors.toList()));
        }
        return customerDTO;
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        return this.modelMapper.map(employeeDTO, Employee.class);
    }

    private EmployeeDTO convertToDto(Employee employee) {
        return this.modelMapper.map(employee, EmployeeDTO.class);
    }

}
