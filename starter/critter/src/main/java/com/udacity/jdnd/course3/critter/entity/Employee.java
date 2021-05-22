package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends User{

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_skill", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "skill")
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "days_available", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany()
    @JoinTable(
            name = "employee_schedules",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    private Set<Schedule> schedules;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
