package com.ProyectoCiclo3.CRUD.Service;

import com.ProyectoCiclo3.CRUD.Entities.Transaction;
import com.ProyectoCiclo3.CRUD.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        transactionModificada.setId(id);
        transactionRepository.save(transactionModificada);

    }
}
