package com.ProyectoCiclo3.CRUD.Service;

import com.ProyectoCiclo3.CRUD.Entities.Employee;
import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Entities.Transaction;
import com.ProyectoCiclo3.CRUD.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    //nuevaTransaction
    public void nuevaTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
    //VerTransaction
    public List<Transaction> verTransaction(){
        List<Transaction> listTransaction = transactionRepository.findAll();
        return listTransaction;
    }
    //verTransactionId
    public Transaction verTransactionId(Long id){
        Optional<Transaction> transactionId = transactionRepository.findById(id);
        return transactionId.orElse(null);
    }
    //eliminarTransaction
    public void eliminarTransaction(Long id){
        transactionRepository.deleteById(id);
    }
    //modificarTransaction
    public void modificarTransaction(Long id, Transaction transactionModificada){
        Optional<Transaction> transactionInDB = transactionRepository.findById(id);
        Employee employee = transactionInDB.get().getEmployee();
        Enterprise enterprise = transactionInDB.get().getEnterprise();
        transactionModificada.setId(id);
        transactionModificada.setEmployee(employee);
        transactionModificada.setEnterprise(enterprise);
        transactionRepository.save(transactionModificada);

    }
}
