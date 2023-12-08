package com.workintech.library_automation.models.People;

import com.workintech.library_automation.enums.Department;

public class Student extends Person {

    private Department department;

    public Student(long id, String firstName, String lastName, Department department) {
        super(id, firstName, lastName);
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() + ", department=" + department;
    }

}
