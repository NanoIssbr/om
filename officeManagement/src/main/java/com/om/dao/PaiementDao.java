package com.om.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.om.domain.Paiement;

public interface PaiementDao extends CrudRepository<Paiement, Long> {

    List<Paiement> findAll();
    List<Paiement> findPaiementBycinPatient(String cin);
}
