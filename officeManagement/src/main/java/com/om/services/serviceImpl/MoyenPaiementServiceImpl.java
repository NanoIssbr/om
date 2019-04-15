package com.om.services.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.om.dao.MoyenPaiementDao;
import com.om.domain.MoyenPaiement;
import com.om.services.MoyenPaiementService;

@Service
public class MoyenPaiementServiceImpl implements MoyenPaiementService {

	@Autowired
	private MoyenPaiementDao mpDao;
	@Override
	public MoyenPaiement createMoyenPaiement(MoyenPaiement mp) {
		return mpDao.save(mp);
	}

	@Override
	public List<MoyenPaiement> findAll() {
		return mpDao.findAll();
	}

	@Override
	public MoyenPaiement findMoyenPaiement(Long id) {
		return mpDao.findById(id).orElse(null);
	}

	@Override
	public MoyenPaiement findMoyenPaiementBymoyenPaiement(String mp) {
		return mpDao.findMoyenPaiementBymoyenPaiement(mp);
	}

	
}
