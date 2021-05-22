package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT distinct e FROM Employee e inner join e.skills es inner join e.daysAvailable ed WHERE es IN :employeeSkills AND ed IN :dayOfWeek GROUP BY e HAVING COUNT (es) = :skillCount")
    List<Employee> findByDaysAndSkills(DayOfWeek dayOfWeek, Set<EmployeeSkill> employeeSkills, long skillCount);
}
