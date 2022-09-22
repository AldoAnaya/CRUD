package com.ProyectoCiclo3.CRUD.Service;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Repository.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EnterpriseService {

    @Autowired
    EnterpriseRepository enterpriseRepository;

    public void crearEnterprise(Enterprise enterprise){
        enterpriseRepository.save(enterprise);
    }
    public List<Enterprise> verEnterprise(){
        List<Enterprise> listEnterprise = enterpriseRepository.findAll();
        return listEnterprise;
    }
    public Optional<Enterprise> verEnterpriseId(Long id){
        Optional<Enterprise> enterpriseId = enterpriseRepository.findById(id);
        return enterpriseId;
    }
    public void eliminarEnterprise(Long id){

        enterpriseRepository.deleteById(id);
    }
    public void modificarEnterprise(Long id, Enterprise modificadoEnterprise){
        Optional<Enterprise> enterpriseInDB = enterpriseRepository.findById(id);
        if(enterpriseInDB.isEmpty()){
            Enterprise enterpriseInSave = modificadoEnterprise;
            enterpriseInSave.setId(enterpriseInDB.get().getId());
            enterpriseInSave.setUpdatedAt(LocalDate.now());
            enterpriseRepository.save(enterpriseInSave);
        }
    }
}
