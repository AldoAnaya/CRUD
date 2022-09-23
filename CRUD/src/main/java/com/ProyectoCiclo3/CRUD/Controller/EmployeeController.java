package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Entities.Profile;
import com.ProyectoCiclo3.CRUD.Service.EmployeeService;
import com.ProyectoCiclo3.CRUD.Service.EnterpriseService;
import com.ProyectoCiclo3.CRUD.Service.ProfileService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    private String verEmpleados(Model model, @AuthenticationPrincipal OidcUser principal){
        // Este if es para cuando no hay nadie registrado no mande error, si no que lo mande a registrar
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employee = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employee", employee);

                // Aqui empieza el metodo del negocio
                List<Employee> listEmployee = employeeService.verEmployees();
                model.addAttribute("listEmployee",listEmployee);
                return "Employee/listarUsuarios";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }

    }

    // metodo para mostrar el formulario de guardar, hay que tener en cuenta que como es OnoToOne toca mandarle los modelos de los dos entidades
    @GetMapping("/employee/nuevo")
    private String nuevoEmployee(Model model, @AuthenticationPrincipal OidcUser principal){
        // Este if es para cuando no hay nadie registrado no mande error, si no que lo mande a registrar
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                Employee employee = new Employee();
                Profile profile = new Profile();
                List<Enterprise> enterprises = enterpriseService.verEnterprise();
                model.addAttribute("employee",employee);
                model.addAttribute("profile", profile);
                model.addAttribute("enterprises",enterprises);
                return "Employee/nuevoUsuario";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }
    // Metodo guardar este como tal ya recibe los modelos y tiene que pasarle la entidad a la otra que no la fila id de la relacion
    @PostMapping("/employee")
    private String guardarEmployee(@ModelAttribute("employee") Employee employee, @ModelAttribute("profile") Profile profile, @AuthenticationPrincipal OidcUser principal){
        if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
            // Aqui empieza el metodo del negocio
            employeeService.crearEmployee(employee);
            profile.setEmployee(employee);
            profileService.crearProfile(profile);
            return "redirect:/employee/list";
        }else {
            return "/";
        }


    }
    //Metodo para moodificar (OJO) El metodo modificar requiere que enviemos primero al usuario a una inerfaz html donde muesre los datos que tiene el empleado en este caso, se envian esos datos por medio de un model
    @GetMapping("/employee/modificar/{id}")
    private String verIdModificarEmployee(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                Employee employee = employeeService.verEmployeeId(id);
                Profile profile = employee.getProfile();
                model.addAttribute("employee", employee);
                model.addAttribute("profile", profile);
                return "Employee/modificarUsuario";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }

    //Metodo para modificar, luego debemos pasar esos elementos

    @PostMapping("/employee/actualizar/{id}")
    private String modificarEmployee (@AuthenticationPrincipal OidcUser principal,@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee,  @ModelAttribute("profile") Profile profile){
        if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){

            employeeService.modificarEmployee(id, employee, profile);
            return "redirect:/employee/list";
        }else {
            return "/";
        }

    }

    // Metodo para eliminar
    @GetMapping("/employee/borrar/{id}")
    private String eliminarEmployee (@PathVariable("id") Long id, @AuthenticationPrincipal OidcUser principal){
        if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){

            employeeService.eliminarEmployee(id);
            return "redirect:/employee/list";
        }else {
            return "/";
        }

    }
}


