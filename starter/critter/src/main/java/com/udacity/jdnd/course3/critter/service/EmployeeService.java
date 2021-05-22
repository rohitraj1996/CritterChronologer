package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeService {

    Employee save(Employee employee);
    List<Employee> getAll();
    Employee getById(Long id);
    void setAvailability(Long id, Set<DayOfWeek> daysAvailable);
    List<Employee> getAvailability(LocalDate date, Set<EmployeeSkill> skillSet);
}
