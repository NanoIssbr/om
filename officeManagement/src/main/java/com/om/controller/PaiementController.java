package com.om.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.om.domain.Paiement;
import com.om.domain.Patient;
import com.om.services.PaiementService;
import com.om.services.PatientService;
import com.om.utils.ServiceUtils;

@Controller
@RequestMapping("/pai")
public class PaiementController {

	@Autowired
	private PaiementService paiService;
	
	@Autowired
	private PatientService patientService;
	
	@RequestMapping(value = "/allbyC", method = RequestMethod.GET)
	public String createAppointment(Model model, @RequestParam(value = "cp") String patientCIN) {
		Patient patient = patientService.findPatientBycinPatient(ServiceUtils.decrypt(patientCIN));
		List<Paiement> paiements= paiService.finPaiementBycinPatient(ServiceUtils.decrypt(patientCIN));
		if(paiements != null && !paiements.isEmpty()) {
			for(Paiement paiement : paiements) {
				paiement.setPatient(patient);
			}
		}
		model.addAttribute("paiements", paiements);
		return "paiements";
	}

	
}
