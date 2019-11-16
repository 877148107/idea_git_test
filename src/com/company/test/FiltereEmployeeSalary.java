package com.company.test;

public class FiltereEmployeeSalary implements MyPredicate<Employee> {
    @Override
    public boolean filterInfo(Employee employee) {
        return employee.getSalary()>5000;
    }
}
