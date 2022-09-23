package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Service.EmployeeService;
import com.ProyectoCiclo3.CRUD.Service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class EnterpriseController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    EmployeeService employeeService;
    //VER todas las empresas
    @GetMapping("/enterprise/list")
    private String verEnterprises(Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                List<Enterprise> listEnterpriseModel = enterpriseService.verEnterprise();
                model.addAttribute("listEnterpriseModel",listEnterpriseModel);
                return "Empresas/listarEmpresas";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }

    // VER INTERFAZ PARA CREAR UNA NUEVA EMPRESA
    @GetMapping("/enterprise/nueva/")
    public String interfazEnterpriseNueva(Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                Enterprise enterprise = new Enterprise();
                model.addAttribute("enterprise", enterprise);
                return "Empresas/nuevaEmpresa";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }

    //CREAR O GUARDAR
    @PostMapping("/enterprise")
    private String crearEnterprise(@ModelAttribute("enterprise") Enterprise enterprise, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer

                // Aqui empieza el metodo del negocio
                enterpriseService.crearEnterprise(enterprise);
                return "redirect:/enterprise/list";
            }else {
                return "/";
            }
        }else{
            return "login";
        }
    }


    //ELIMINAR
    @GetMapping("/enterprise/borrar/{id}")
    private String eliminarEnterprise(@PathVariable("id") Long id, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer

                // Aqui empieza el metodo del negocio
                enterpriseService.eliminarEnterprise(id);
                return "redirect:/enterprise/list";
            }else {
                return "/";
            }
        }else{
            return "login";
        }
    }

    //METODO PARA LLAMAR EL FORMULARIO DE MODIFICAR
    @GetMapping("/enterprise/modificar/{id}")
    private String verEditarEmpresa(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                Enterprise enterpriseModel = enterpriseService.verEnterpriseId(id);
                model.addAttribute("enterpriseModel",enterpriseModel);
                return "Empresas/modificarEmpresa";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }

    //MODIFICAR
    @PostMapping("/enterprise/actualizar/{id}")
    private RedirectView modificarEnterprise(@PathVariable("id") Long id, @ModelAttribute("enterpriseModel") Enterprise enterprise, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer

                // Aqui empieza el metodo del negocio
                enterpriseService.modificarEnterprise(id,enterprise);
                return new RedirectView("/enterprise/list");
            }else {
                return new RedirectView("/");
            }
        }else{
            return new RedirectView("login");
        }
    }
}
