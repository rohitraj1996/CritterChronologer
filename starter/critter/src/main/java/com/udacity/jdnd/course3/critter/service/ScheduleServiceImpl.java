package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CustomerService customerService;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, CustomerService customerService) {
        this.scheduleRepository = scheduleRepository;
        this.customerService = customerService;
    }

    @Override
    public Schedule save(Schedule schedule) {
        return this.scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAll() {
        return this.scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getSchedulesForPet(Long petId) {
        return this.scheduleRepository.findSchedulesByPetsId(petId);
    }

    @Override
    public List<Schedule> getSchedulesForEmployee(Long employeeId) {
        return this.scheduleRepository.findSchedulesByEmployeesId(employeeId);
    }

    @Override
    public List<Schedule> getSchedulesForCustomer(Long customerId) {
        Customer customer = this.customerService.getById(customerId);
        return customer.getPets().stream().map(pet -> this.scheduleRepository.findSchedulesByPetsId(pet.getId()))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }
}
