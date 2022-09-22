package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Entities.Profile;
import com.ProyectoCiclo3.CRUD.Service.EmployeeService;
import com.ProyectoCiclo3.CRUD.Service.EnterpriseService;
import com.ProyectoCiclo3.CRUD.Service.ProfileService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProfileService profileService;
    @Autowired
    EnterpriseService enterpriseService;

    @GetMapping("/employee/list")
    private String verEmpleados(Model model){
        List<Employee> listEmployee = employeeService.verEmployees();
        model.addAttribute("listEmployee",listEmployee);
        return "Employee/listarUsuarios";
    }

    // metodo para mostrar el formulario de guardar, hay que tener en cuenta que como es OnoToOne toca mandarle los modelos de los dos entidades
    @GetMapping("/employee/nuevo")
    private String nuevoEmployee(Model model){
        Employee employee = new Employee();
        Profile profile = new Profile();
        List<Enterprise> enterprises = enterpriseService.verEnterprise();
        model.addAttribute("employee",employee);
        model.addAttribute("profile", profile);
        model.addAttribute("enterprises",enterprises);
        return "Employee/nuevoUsuario";
    }
    // Metodo guardar este como tal ya recibe los modelos y tiene que pasarle la entidad a la otra que no la fila id de la relacion
    @PostMapping("/employee")
    private String guardarEmployee(@ModelAttribute("employee") Employee employee, @ModelAttribute("profile") Profile profile){
        employeeService.crearEmployee(employee);
        profile.setEmployee(employee);
        profileService.crearProfile(profile);
        return "redirect:/employee/list";
    }
    //Metodo para moodificar (OJO) El metodo modificar requiere que enviemos primero al usuario a una inerfaz html donde muesre los datos que tiene el empleado en este caso, se envian esos datos por medio de un model
    @GetMapping("/employee/modificar/{id}")
    private String verIdModificarEmployee(@PathVariable("id") Long id, Model model){
        Employee employee = employeeService.verEmployeeId(id);
        Profile profile = employee.getProfile();
        model.addAttribute("employee", employee);
        model.addAttribute("profile", profile);
        return "Employee/modificarUsuario";
    }

    //Metodo para modificar, luego debemos pasar esos elementos

    @PostMapping("/employee/actualizar/{id}")
    private String modificarEmployee (@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee,  @ModelAttribute("profile") Profile profile){
        employeeService.modificarEmployee(id, employee, profile);
        return "redirect:/employee/list";
    }

    // Metodo para eliminar
    @GetMapping("/employee/borrar/{id}")
    private String eliminarEmployee (@PathVariable("id") Long id){
        employeeService.eliminarEmployee(id);
        return "redirect:/employee/list";
    }
}


