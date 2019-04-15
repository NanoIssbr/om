package com.om.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.om.domain.MoyenPaiement;

public interface MoyenPaiementDao extends CrudRepository<MoyenPaiement, Long> {

    List<MoyenPaiement> findAll();
    MoyenPaiement findMoyenPaiementBymoyenPaiement(String mp);
}
