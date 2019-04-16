package com.om.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.om.domain.Consultation;
import com.om.domain.Document;
import com.om.domain.MoyenPaiement;
import com.om.domain.Paiement;
import com.om.domain.Patient;
import com.om.services.ConsultationService;
import com.om.services.DocumentService;
import com.om.services.MoyenPaiementService;
import com.om.services.PaiementService;
import com.om.services.PatientService;
import com.om.services.StorageService;
import com.om.utils.ServiceUtils;

@Controller
@RequestMapping("/consul")
public class ConsultationController {
	
	private static final String CONSUL_LABEL_FOR_DOCS = "CONSUL$";
	private static final String CNTRL_LABEL_FOR_DOCS = "CNTRL$";

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ConsultationService consulService;
	
	@Autowired 
	private MoyenPaiementService mpService;
	
	@Autowired
	private PaiementService paiService;
	
	@Autowired
	private DocumentService docService;

	@RequestMapping(value = "/addConsul", method = RequestMethod.GET)
	public String createConsultation(Model model, @RequestParam(value = "cp") String patientCIN) throws IOException {
		Patient patient = patientService.findPatientBycinPatient(ServiceUtils.decrypt(patientCIN));
		List<MoyenPaiement> moyensPaiements = mpService.findAll();
		Consultation consul = new Consultation();
		consul.setMoyensPaiements(moyensPaiements);
		consul.setPatient(patient);
		model.addAttribute("consultation", consul);
		//listUploadedFiles(model,patientCIN);
		return "consul";
	}
	
	@RequestMapping(value = "/allConsulsPatient", method = RequestMethod.GET)
	public String getAllConsultations(Model model, @RequestParam(value = "cp") String patientCIN) throws IOException {
		List<Consultation> listConsulsByPatient = consulService.findConsultationBycinPatient(ServiceUtils.decrypt(patientCIN));
		for(Consultation consul : listConsulsByPatient) {
			if(consul.getPaiement() == null) {
				Paiement pai = new Paiement();
				pai.setMontant(0.0);
				consul.setPaiement(pai);
			}
		}
//		listUploadedFiles(model,patientCIN);
		model.addAttribute("allConsultations", setListDocInConsul(listConsulsByPatient));
		return "consuls";
	}
	
	public List<Consultation> setListDocInConsul(List<Consultation> consuls){
		if(consuls != null && !consuls.isEmpty()) {
			for(Consultation consul : consuls) {
				List<String> listPaths = new ArrayList<>();
				if(consul.getDocs() != null && !consul.getDocs().isEmpty()) {
					if(consul.getDocs().contains(",")) {
						listPaths = Arrays.asList(consul.getDocs().split(","));
						System.out.println(1);
					}else{
						listPaths.add(consul.getDocs());
						System.out.println(2);
					}
					consul.setListDocs(listPaths);
					System.out.println(3);
				}
			}
		}
		
		return consuls;
	}
	
	@RequestMapping(value = "/addConsul", method = RequestMethod.POST)
	public String createConsultationPost(Model model, @RequestParam("file") MultipartFile[] files, @ModelAttribute("consultation") Consultation consul) throws IOException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String dateString = formatter.format(date);
		dateString = dateString.replace("/", "").replace(" ", "").replace(":", "");
		//set Patient
		Patient patient = patientService.findPatientBycinPatient(consul.getPatient().getCinPatient());
		consul.setPatient(patient);
		consul.setCinPatient(patient.getCinPatient());
		consul.setDateConsultation(new Date());
		Paiement pai = consul.getPaiement();
		pai.setDatePaiement(date);
		pai.setCinPatient(patient.getCinPatient());
		//set moyen de paiement
		MoyenPaiement mp = mpService.findMoyenPaiementBymoyenPaiement(pai.getMoyenPaiement());
		pai.setMoyenPaiementObj(mp);
		pai.setMoyenPaiement(mp.getMoyenPaiement());
		//save paiement
		pai = paiService.createPaiement(pai);
		//save consultation
		consul.setPaiement(pai);
		Consultation consulSaved = consulService.createConsultation(consul);
		//save files
		String[] tabGeneratedFilesNames = new String[files.length];
		String[] tabGeneratedFileName = new String[1];
		List<Document> listDocsToSave = new ArrayList<>();
		for(int i = 0 ; i<files.length;i++) {
			String generatedName = "";
			if(consulSaved.getIsControle()) {
				generatedName = CNTRL_LABEL_FOR_DOCS + consulSaved.getPatient().getCinPatient()+"-"+String.valueOf(consulSaved.getId() + "_" + dateString + i);
			}else {
				generatedName = CONSUL_LABEL_FOR_DOCS + consulSaved.getPatient().getCinPatient()+"-"+String.valueOf(consulSaved.getId() + "_" + dateString + i);
			}
			tabGeneratedFilesNames[i] = generatedName;
			tabGeneratedFileName[0] = generatedName;
			ServiceUtils.storeFile(files[i], generatedName, storageService);
			Document doc = new Document();
			List<String> listPathsDocs = ServiceUtils.listUploadedFiles(tabGeneratedFileName, storageService);
			doc.setDisplayName(files[i].getOriginalFilename());
			doc.setUrlDoc(listPathsDocs.get(0));
			doc.setCinPatient(consulSaved.getPatient().getCinPatient());
			doc.setIdConsultation(String.valueOf(consulSaved.getId()));
			doc.setConsultation(consulSaved);
			listDocsToSave.add(doc);
		}
		List<String> listPathsDocs = ServiceUtils.listUploadedFiles(tabGeneratedFilesNames, storageService);
		String allGeneratedNames = "";
		if(listPathsDocs != null && !listPathsDocs.isEmpty()) {
			allGeneratedNames = listPathsDocs.toString().replace(" ", "").replace("[", "").replace("]", "");
		}
		//save docs
		listDocsToSave = docService.createAllDocuments(listDocsToSave);
		consulSaved.setDocs(allGeneratedNames);
		consulSaved.setListDocuments(listDocsToSave);
		//save consultation with files paths
		consulService.createConsultation(consulSaved);
		return "redirect:/consul/allConsulsPatient?cp="+consulSaved.getCinPatient();
	}
	
//	@RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
	

}
