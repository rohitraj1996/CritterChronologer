package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule save(Schedule schedule);
    List<Schedule> getAll();
    List<Schedule> getSchedulesForPet(Long petId);
    List<Schedule> getSchedulesForEmployee(Long employeeId);
    List<Schedule> getSchedulesForCustomer(Long customerId);
}
