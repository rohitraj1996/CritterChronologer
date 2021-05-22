package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeServiceImpl;
import com.udacity.jdnd.course3.critter.service.PetServiceImpl;
import com.udacity.jdnd.course3.critter.service.ScheduleServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    PetServiceImpl petService;

    @Autowired
    ScheduleServiceImpl scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return this.convertToDto(this.scheduleService.save(this.convertToEntity(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return this.scheduleService.getAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return this.scheduleService.getSchedulesForPet(petId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return this.scheduleService.getSchedulesForEmployee(employeeId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return this.scheduleService.getSchedulesForCustomer(customerId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private Schedule convertToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        // Set employees
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().filter(employeeId -> employeeId != 0L)
                .map(this.employeeService::getById).collect(Collectors.toList()));

        // Set pets
        schedule.setPets(scheduleDTO.getPetIds().stream().filter(petId -> petId != 0L)
                .map(this.petService::getById).collect(Collectors.toList()));

        return schedule;
    }

    private ScheduleDTO convertToDto(Schedule schedule) {
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);

        // Set employee Ids
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().filter(Objects::nonNull)
                .map(Employee::getId).collect(Collectors.toList()));
        // Set pet Ids
        scheduleDTO.setPetIds(schedule.getPets().stream().filter(Objects::nonNull)
                .map(Pet::getId).collect(Collectors.toList()));

        return scheduleDTO;
    }
}
