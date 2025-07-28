package dev.afecioru.annotations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("manager")
public class Manager {
    private final Employee employee;

    public Manager(
        @Qualifier("john")
        Employee employee
    ) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "employee=" + employee +
                '}';
    }
}
