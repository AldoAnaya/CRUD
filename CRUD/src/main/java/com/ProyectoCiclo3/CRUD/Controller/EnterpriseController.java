package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class EnterpriseController {
    @Autowired
    EnterpriseService enterpriseService;
    //VER TODO
    @GetMapping("/enterprise/list")
    private String verEnterprises(Model model){
        List<Enterprise> listEnterpriseModel = enterpriseService.verEnterprise();
        model.addAttribute("listEnterpriseModel",listEnterpriseModel);
        return "Empresas/listarEmpresas";
    }

    // VER INTERFAZ PARA CREAR UNA NUEVA EMPRESA
    @GetMapping("/enterprise/nueva/")
    public String interfazEnterpriseNueva(Model model){
        Enterprise enterprise = new Enterprise();
        model.addAttribute("enterprise", enterprise);
        return "Empresas/nuevaEmpresa";
    }

    //CREAR O GUARDAR
    @PostMapping("/enterprise")
    private String crearEnterprise(@ModelAttribute("enterprise") Enterprise enterprise){
        enterpriseService.crearEnterprise(enterprise);
        return "redirect:/enterprise/list";
    }

    //ELIMINAR
    @GetMapping("/enterprise/borrar/{id}")
    private String eliminarEnterprise(@PathVariable("id") Long id){
        enterpriseService.eliminarEnterprise(id);
        return "redirect:/enterprise/list";
    }
    //MODIFICAR
    @PatchMapping("/enterprise/{id}")
    private void modificarEnterprise(@PathVariable("id") Long id,@RequestBody Enterprise enterprise){
        enterpriseService.modificarEnterprise(id,enterprise);
    }
}
