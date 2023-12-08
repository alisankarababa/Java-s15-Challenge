package com.workintech.library_automation.models.People;

import com.workintech.library_automation.enums.Department;
import com.workintech.library_automation.enums.Rank;
import com.workintech.library_automation.models.People.Person;

public class FacultyMember extends Person {

    private Department department;
    private Rank rank;

    public FacultyMember(long id, String firstName, String lastName, Department department, Rank rank) {
        super(id, firstName, lastName);
        this.department = department;
        this.rank = rank;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return super.toString() + ", department=" + department + ", rank=" + rank;
    }

}
