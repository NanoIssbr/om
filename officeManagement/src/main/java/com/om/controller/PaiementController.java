package com.om.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.om.services.StorageService;
import com.om.utils.PdfGeneratorUtil;
import com.om.utils.ServiceUtils;

@Controller
@RequestMapping("/pai")
public class PaiementController {

	@Autowired
	private PaiementService paiService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PdfGeneratorUtil pdfGenaratorUtil;
	
	@Autowired
	private StorageService storageService;

	
	@RequestMapping(value = "/allbyC", method = RequestMethod.GET)
	public String getAllPaiementForPatient(Model model, @RequestParam(value = "cp") String patientCIN) throws Exception {
		List<Paiement> paiements = getAllPaiementByCIN(patientCIN);
		model.addAttribute("paiements", paiements);
		if(paiements != null && !paiements.isEmpty()) {
			String fileName = generatePaiReport(paiements);
			String[] tabCriterias = new String[1];
			tabCriterias[0] = fileName;
			List<String> listPaths = ServiceUtils.listUploadedFiles(tabCriterias, storageService);
			String pathToModel = listPaths.get(0);
			model.addAttribute("pathDoc", pathToModel);
		}
		return "paiements";
	}
	
	private List<Paiement> getAllPaiementByCIN(String patientCIN){
		Patient patient = patientService.findPatientBycinPatient(ServiceUtils.decrypt(patientCIN));
		List<Paiement> paiements= paiService.findPaiementBycinPatient(ServiceUtils.decrypt(patientCIN));
		if(paiements != null && !paiements.isEmpty()) {
			for(Paiement paiement : paiements) {
				paiement.setPatient(patient);
			}
		}
		return paiements;
	}
	
	@RequestMapping(value = "/paiReport", method = RequestMethod.GET)
	public String generatePaiReport(List<Paiement> paiements) throws Exception {
		Map<String, Object> mapData = new HashMap<>();
		mapData.put("paiements", paiements);
		return pdfGenaratorUtil.createPdf("paiementsTemplate", mapData);
		
		
	}

	
}
