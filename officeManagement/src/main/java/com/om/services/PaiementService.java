package com.om.services;

import java.util.List;

import com.om.domain.Paiement;

public interface PaiementService {
	Paiement createPaiement(Paiement paiement);
	List<Paiement> finPaiementBycinPatient(String cin);
}
