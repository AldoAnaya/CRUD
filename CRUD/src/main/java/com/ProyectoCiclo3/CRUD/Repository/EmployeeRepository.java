package com.ProyectoCiclo3.CRUD.Repository;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmail(String Email);
}
