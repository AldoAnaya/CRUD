package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Entities.Transaction;
import com.ProyectoCiclo3.CRUD.Service.EmployeeService;
import com.ProyectoCiclo3.CRUD.Service.EnterpriseService;
import com.ProyectoCiclo3.CRUD.Service.TransactionService;
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
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EnterpriseService enterpriseService;

    //Controlador para ver es decir la lista
    @GetMapping("transaction/list")
    private String verTransaction(Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                List<Transaction> listTransaction = transactionService.verTransaction();
                model.addAttribute("listTransaction",listTransaction);
                return "IngresosYEgresos/listarTransaction";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }

    //Controlador para nueva transaccion
    @GetMapping("/transaction/nuevo/")
    private String nuevaTransaction(Model model, @AuthenticationPrincipal OidcUser principal){ // En el metodo para ver el formulario tienes que agregar un model, al que se le va a pasar un objeto, es decir la transaccion, este será leido en el form
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                Transaction transactionToModel = new Transaction();
                model.addAttribute("transactionToModel", transactionToModel);
                List<Enterprise> enterpriseToModel = enterpriseService.verEnterprise();
                model.addAttribute("enterpriseToModel", enterpriseToModel);
                List<Employee> employeeToModel = employeeService.verEmployees();
                model.addAttribute("employeeToModel", employeeToModel);
                return "IngresosYEgresos/nuevaTransaction";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }

    //Controlador para guardar
    @PostMapping("/transaction")
    private String guardarTransaction(@ModelAttribute("transactionToModel") Transaction transaction,  @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer

                // Aqui empieza el metodo del negocio
                transactionService.nuevaTransaction(transaction);
                return "redirect:/transaction/list";
            }else {
                return "/";
            }
        }else{
            return "login";
        }
    }

    //Contralador para borrar
    @GetMapping("transaction/borrar/{id}")
    private String eliminarTransaction(@PathVariable("id") Long id, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer

                // Aqui empieza el metodo del negocio
                transactionService.eliminarTransaction(id);
                return "redirect:/transaction/list";
            }else {
                return "/";
            }
        }else{
            return "login";
        }
    }

    //Controlador para llamar al formulario de modificar transaccion
    @GetMapping("/transaction/modificar/{id}")
    private String formularioModificarTransaction(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer
                Employee employeeAuth = employeeService.findEmployeeByEmail(principal.getEmail());
                model.addAttribute("employeeAuth", employeeAuth);

                // Aqui empieza el metodo del negocio
                Transaction transaction = transactionService.verTransactionId(id);
                model.addAttribute("transaction", transaction);
                return "IngresosYEgresos/modificarTransaction";
            }else {
                model.addAttribute("employee", null);
                return "/";
            }
        }else{
            return "login";
        }
    }

    @PatchMapping("/transaction/actualizar/{id}")
    private RedirectView transactionModificar(@PathVariable("id") Long id, @ModelAttribute("transaction") Transaction transaction,  @AuthenticationPrincipal OidcUser principal){
        if(principal!=null){
            //Buscamos a nuestro objeto en la base de datos y si esta registrado, sabiendo que es diferente de null, procedemos a la logica del negocio
            if(employeeService.findEmployeeByEmail(principal.getEmail()) != null){
                // logica de seguridad, recibir el empleado por medio del email de auth, buscarlo y enviarlo al front, para verificar valores como el rol, y saber que puede hacer

                // Aqui empieza el metodo del negocio
                transactionService.modificarTransaction(id,transaction);
                return new RedirectView("/transaction/list");
            }else {
                return new RedirectView("/");
            }
        }else{
            return new RedirectView("login");
        }
    }
}
