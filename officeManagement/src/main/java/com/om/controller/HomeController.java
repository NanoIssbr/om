/**
 * 
 */
package com.om.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.om.dao.RoleDao;
import com.om.domain.Appointment;
import com.om.domain.MoyenPaiement;
import com.om.domain.Patient;
import com.om.domain.Users;
import com.om.domain.security.Role;
import com.om.domain.security.UserRole;
import com.om.services.AppointmentService;
import com.om.services.MoyenPaiementService;
import com.om.services.PatientService;
import com.om.services.UserService;

/**
 * @author ibrayche
 *
 */
@Controller
public class HomeController {

	private static final String[] moyensPaiement = { "ESPECE", "CARTE BANCAIRE", "CHEQUE" };

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private MoyenPaiementService mpService;

	@RequestMapping("/index")
	public String index() {
		createMoyenPaiements();
		return "index";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		Users user = new Users();

		model.addAttribute("user", user);

		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("user") Users user, Model model) {

		if (userService.checkUserExists(user.getUsername(), user.getEmail())) {

			if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}

			if (userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists", true);
			}

			return "signup";
		} else {
			Set<UserRole> userRoles = new HashSet<>();
			Role role = roleDao.findByName("ROLE_USER");
			System.out.println("role object : " + role.toString());
			userRoles.add(new UserRole(user, role));

			userService.createUser(user, userRoles);

			return "redirect:/";
		}
	}

	@RequestMapping("/mainPage")
	public String userFront(Principal principal, Model model) {
		List<Appointment> listAppointments = appointmentService.getFuturAppointments();
		model.addAttribute("allApointments", listAppointments);
		List<Patient> listPatients = patientService.findAll();
		model.addAttribute("allPatients", listPatients);
		return "mainPage";
	}

	public void createMoyenPaiements() {

		System.out.println("start init moyen paiement , dans la liste " + Arrays.toString(moyensPaiement));
		System.out.println("check if moyen de paiement exist indeed create it");
		Date sysDate = new Date();
		for (int i = 0; i < moyensPaiement.length; i++) {
			System.out.println("checking " + moyensPaiement[i]);
			MoyenPaiement mp = mpService.findMoyenPaiementBymoyenPaiement(moyensPaiement[i]);
			if (mp != null) {
				System.out.println("checking " + moyensPaiement[i] + " || exist");
			} else {
				System.out.println("checking " + moyensPaiement[i] + " || not existe, creating");
				MoyenPaiement mpToCreate = new MoyenPaiement(moyensPaiement[i], sysDate);
				mpService.createMoyenPaiement(mpToCreate);
			}

		}
		System.out.println("END checking");
	}

}
