/**
 * 
 */
package com.om.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.om.domain.Patient;
import com.om.domain.PatientMeasures;
import com.om.services.PatientService;
import com.om.utils.ServiceUtils;

import antlr.StringUtils;

/**
 * @author ibrayche
 *
 */
@Controller
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createPatient(Model model) {
		Patient patient = new Patient();
		PatientMeasures measure = new PatientMeasures();
		model.addAttribute("measure", measure);
		model.addAttribute("patient", patient);
		return "patient";
	}
	
	@RequestMapping(value = "/addMeasure", method = RequestMethod.GET)
	public String addMeasureToPatient(Model model, @RequestParam(value = "cp") String patientCIN) {
		Patient patient = patientService.findPatientBycinPatient(ServiceUtils.decrypt(patientCIN));
		PatientMeasures measure = new PatientMeasures();
		patient.setPm(measure);
		model.addAttribute("measure", measure);
		model.addAttribute("patient", patient);
		return "patient";
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPatientPost(@ModelAttribute("patient") Patient patient, @ModelAttribute("measure") PatientMeasures measure, @ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = format1.parse(date);
		patient.setDateNaissancePatient(d1);
		if(patient.getPatientId() == null && patientService.checkIfCINEwist(patient.getCinPatient())) {
			model.addAttribute("cinExists", true);
			return "patient";
		}
		if(measure.getLength() != null && !measure.getLength().isEmpty() && measure.getWeight() != null) {
			List<PatientMeasures> measures = null;
			if (patientService.findPatientBycinPatient(patient.getCinPatient()) != null) {
				measures = patientService.findPatientBycinPatient(patient.getCinPatient()).getPatientMeasure();
			}
			if(measures == null) {
				measures = new ArrayList<PatientMeasures>();
			}
			measure.setDateMeasure(new Date());
			measure.setPatient(patient);
			measures.add(measure);
			patient.setPatientMeasure(measures);
			patient.setCinEncrypted(ServiceUtils.encrypt(patient.getCinPatient()));
			patient.setPm(measure);
		}
			
		patientService.createPatient(patient);
		return "redirect:/mainPage";
		
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String allPatientGet(Model model) throws ParseException {
		List<Patient> allPatients = patientService.findAll();
		if(allPatients != null && !allPatients.isEmpty()) {
			for(Patient patient : allPatients) {
				if(patient.getCinEncrypted() != null) {
					System.out.println("****************** " + ServiceUtils.decrypt(patient.getCinEncrypted()));
				}
				if(patient.getPatientMeasure() != null && !patient.getPatientMeasure().isEmpty()) {
					Collections.reverse(patient.getPatientMeasure());
					patient.setPm(patient.getPatientMeasure().get(0));
				}
			}
		}
		model.addAttribute("allPatients", allPatients);
		return "patients";
	}
	
}
