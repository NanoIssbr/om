package com.om.services;

import java.util.List;

import com.om.domain.MoyenPaiement;

public interface MoyenPaiementService {
	MoyenPaiement createMoyenPaiement(MoyenPaiement mp);

    List<MoyenPaiement> findAll();

    MoyenPaiement findMoyenPaiement(Long id);
    
    MoyenPaiement findMoyenPaiementBymoyenPaiement(String mp);
}
