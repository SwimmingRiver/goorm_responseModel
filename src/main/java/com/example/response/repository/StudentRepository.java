package com.example.response.repository;

import com.example.response.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {
    Set<Student> students = new HashSet<>();

    public void add(Student student){students.add(student);}

    public List<Student> getAll(){
        return students.stream()
                .sorted()
                .collect(Collectors.toList());
    }
    public List<Student> get(int grade){
        return students.stream()
                .filter(student -> student.getGrade() == grade)
                .collect(Collectors.toList());
    }
}
