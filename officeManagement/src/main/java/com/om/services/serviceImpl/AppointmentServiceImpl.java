package com.om.services.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.om.dao.AppointmentDao;
import com.om.domain.Appointment;
import com.om.services.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;

	public Appointment createAppointment(Appointment appointment) {
		return appointmentDao.save(appointment);
	}

	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}

	public Appointment findAppointment(Long id) {
		return appointmentDao.findById(id).orElse(null);
	}

	public void confirmAppointment(Long id) {
		Appointment appointment = findAppointment(id);
		appointment.setConfirmed(true);
		appointmentDao.save(appointment);
	}
	
	public List<Appointment> getFuturAppointments(){
		List<Appointment> futurAppointments = new ArrayList<>();
		List<Appointment> allAppointments = findAll();
		Date sysDate = new Date();
		if(allAppointments != null && !allAppointments.isEmpty() ) {
			for(Appointment apnt : allAppointments) {
				if(apnt.getDate() == null) {
					continue;
				}
				if(apnt.getDate().compareTo(sysDate) >= 0) {
					futurAppointments.add(apnt);
				}
			}
		}
		return futurAppointments;
	}
}
