package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Entities.Transaction;
import com.ProyectoCiclo3.CRUD.Service.EmployeeService;
import com.ProyectoCiclo3.CRUD.Service.EnterpriseService;
import com.ProyectoCiclo3.CRUD.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private String verTransaction(Model model){
        List<Transaction> listTransaction = transactionService.verTransaction();
        model.addAttribute("listTransaction",listTransaction);
        return "IngresosYEgresos/listarTransaction";
    }

    //Controlador para nueva transaccion
    @GetMapping("/transaction/nuevo/")
    private String nuevaTransaction(Model model){ // En el metodo para ver el formulario tienes que agregar un model, al que se le va a pasar un objeto, es decir la transaccion, este será leido en el form
        Transaction transactionToModel = new Transaction();
        model.addAttribute("transactionToModel", transactionToModel);
        List<Enterprise> enterpriseToModel = enterpriseService.verEnterprise();
        model.addAttribute("enterpriseToModel", enterpriseToModel);
        List<Employee> employeeToModel = employeeService.verEmployees();
        model.addAttribute("employeeToModel", employeeToModel);
        return "IngresosYEgresos/nuevaTransaction";
    }

    //Controlador para guardar
    @PostMapping("/transaction")
    private String guardarTransaction(@ModelAttribute("transactionToModel") Transaction transaction){
        transactionService.nuevaTransaction(transaction);
        return "redirect:/transaction/list";
    }

    //Contralador para borrar
    @GetMapping("transaction/borrar/{id}")
    private String eliminarTransaction(@PathVariable("id") Long id){
        transactionService.eliminarTransaction(id);
        return "redirect:/transaction/list";
    }

    //Controlador para llamar al formulario de modificar transaccion
    @GetMapping("/transaction/modificar/{id}")
    private String formularioModificarTransaction(@PathVariable("id") Long id, Model model){
        Transaction transaction = transactionService.verTransactionId(id);
        model.addAttribute("transaction", transaction);
        return "IngresosYEgresos/modificarTransaction";
    }

    @PatchMapping("/transaction/actualizar/{id}")
    private String transactionModificar(@PathVariable("id") Long id, @ModelAttribute("transaction") Transaction transaction){
       transactionService.modificarTransaction(id,transaction);
        return "redirect:/transaction/list";
    }



}
