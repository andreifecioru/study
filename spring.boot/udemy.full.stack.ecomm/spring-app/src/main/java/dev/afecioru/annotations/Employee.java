package dev.afecioru.annotations;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Employee {
    private final String name;
    private final int age;

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
