package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Service.EmployeeService;
import com.ProyectoCiclo3.CRUD.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    private String login(Model model, @AuthenticationPrincipal OidcUser principal){ // eso que recibe el controlador es basicamente el objeto que va a tener los datos de autenticacion que meta en auth0
       if(principal!=null){
           if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
               Employee employee = employeeService.findEmployeeByEmail(principal.getEmail());
               System.out.println(principal.getEmail());
               model.addAttribute("employee", employee);
           }else {
               model.addAttribute("employee", null);
           }
       }
        return "login";
    }

    @GetMapping("/index")
    private String index(Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                Employee employee = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employee", employee);
            }else {
                model.addAttribute("employee", null);
            }
        }
        return "index";
    }
}
