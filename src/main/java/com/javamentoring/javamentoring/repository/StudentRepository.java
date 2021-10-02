package com.javamentoring.javamentoring.repository;


import com.javamentoring.javamentoring.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
