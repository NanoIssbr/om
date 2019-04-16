package com.om.services.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.om.dao.PaiementDao;
import com.om.domain.Paiement;
import com.om.services.PaiementService;

@Service
public class PaiementServiceImpl implements PaiementService {

	@Autowired
	private PaiementDao paiDao;
	@Override
	public Paiement createPaiement(Paiement paiement) {
		return paiDao.save(paiement);
	}
	@Override
	public List<Paiement> findPaiementBycinPatient(String cin) {
		return paiDao.findPaiementBycinPatient(cin);
	}

	
}
