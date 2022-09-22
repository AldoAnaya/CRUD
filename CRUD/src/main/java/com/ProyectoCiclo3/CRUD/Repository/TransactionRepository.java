package com.ProyectoCiclo3.CRUD.Repository;

import com.ProyectoCiclo3.CRUD.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
