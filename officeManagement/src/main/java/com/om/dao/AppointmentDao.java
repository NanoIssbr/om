package com.om.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.om.domain.Appointment;

public interface AppointmentDao extends CrudRepository<Appointment, Long> {

    List<Appointment> findAll();
}
