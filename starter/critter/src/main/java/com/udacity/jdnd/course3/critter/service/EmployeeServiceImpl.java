package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee getById(Long id) {
        return this.employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public void setAvailability(Long id, Set<DayOfWeek> daysAvailable) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAvailability(LocalDate date, Set<EmployeeSkill> skillSet) {
        return this.employeeRepository.findByDaysAndSkills(date.getDayOfWeek(), skillSet, skillSet.size());
    }
}
