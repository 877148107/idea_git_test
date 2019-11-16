package com.company.test;

public class FiltereEmployeeAge implements MyPredicate<Employee> {
    @Override
    public boolean filterInfo(Employee employee) {
        return employee.getAge()<30;
    }
}
