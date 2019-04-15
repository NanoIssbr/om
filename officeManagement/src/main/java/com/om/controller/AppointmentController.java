package com.om.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.om.domain.Appointment;
import com.om.domain.Patient;
import com.om.domain.Users;
import com.om.services.AppointmentService;
import com.om.services.PatientService;
import com.om.services.UserService;
import com.om.utils.ServiceUtils;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createAppointment(Model model, @RequestParam(value = "cp") String patientCIN) {
		Appointment appointment = new Appointment();
		Patient patient = patientService.findPatientBycinPatient(ServiceUtils.decrypt(patientCIN));
		appointment.setPatient(patient);
		appointment.setDescription("Controle 3");
		model.addAttribute("appointment", appointment);
		model.addAttribute("dateString", "");
		return "appointment";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment, @ModelAttribute("dateString") String date, Model model) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date d1 = format1.parse(date);
		appointment.setDate(d1);
		Patient patient = patientService.findPatientBycinPatient(appointment.getPatient().getCinPatient());
		appointment.setPatient(patient);
		appointmentService.createAppointment(appointment);
		return "redirect:/mainPage";
	}
	
	@RequestMapping("/appointments")
	public String getAllApointments(Model model, Principal principal) {
		List<Appointment> listAppointments = appointmentService.findAll();
		model.addAttribute("allApointments", listAppointments);
		return "appointments";
	}
}
