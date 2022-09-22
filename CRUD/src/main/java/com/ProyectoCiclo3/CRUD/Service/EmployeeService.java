package com.ProyectoCiclo3.CRUD.Service;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Profile;
import com.ProyectoCiclo3.CRUD.Repository.EmployeeRepository;
import com.ProyectoCiclo3.CRUD.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProfileRepository profileRepository;

    public void crearEmployee(Employee employee){
        employeeRepository.save(employee);
    }
    public List<Employee> verEmployees(){
            List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Employee verEmployeeId(Long id){
        Optional<Employee> employeeId = employeeRepository.findById(id);
        return employeeId.orElse(null);
    }

    public void eliminarEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    public  void modificarEmployee(Long id,Employee employeeModify, Profile profileModify){
            Optional<Employee> employeeInDB = employeeRepository.findById(id);
            Profile profileInDB = employeeInDB.get().getProfile();
            employeeModify.setId(employeeInDB.get().getId());
            profileModify.setId(profileInDB.getId());
            employeeRepository.save(employeeModify);
            profileModify.setEmployee(employeeModify);
            profileRepository.save(profileModify);
    }

    public String findEmployeeByEmail(String email){
        if(employeeRepository.findByEmail(email) == null){
            return "no";
        }else {
            Employee employee = employeeRepository.findByEmail(email);
            String emailEmployeeDB = employee.getEmail();
            return emailEmployeeDB;
        }

    }
}
