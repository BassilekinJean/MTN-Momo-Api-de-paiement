package com.paie.bassilekinjean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paie.bassilekinjean.model.PaiementTransaction;

public interface TransactionRepository extends JpaRepository<PaiementTransaction,Long>{

}
