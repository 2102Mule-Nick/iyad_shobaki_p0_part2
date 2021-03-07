package com.barbershop.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.barbershop.dao.AppointmentDaoPostgres;
import com.barbershop.pojo.Appointment;

class AppointmentDaoPostgresTest {

	public static AppointmentDaoPostgres daoPostgres;
	public static Appointment appointment1;
	public static Appointment appointment2;
	public static Appointment appointment3;
	
	@BeforeEach
	private void setUp() {
		daoPostgres = new AppointmentDaoPostgres();
		appointment1 = new Appointment(LocalDate.now(), LocalTime.now(), 1, 1);
		appointment2 = new Appointment(LocalDate.now(), LocalTime.now(), 1, 1);
		appointment3 = new Appointment(LocalDate.now(), LocalTime.now(), 1, 1);
	}
	
	@AfterEach
	private void tearDown() {
		daoPostgres.deleteAll();
	}
	
	@Test
	void testFindAll() {
		daoPostgres.create(appointment1);
		daoPostgres.create(appointment2);
		daoPostgres.create(appointment3);
		
		assertEquals(3, daoPostgres.findAll().size());
	}

	@Test
	void testCreate() {		
		assertTrue(daoPostgres.create(appointment1));
	}

	@Test
	void testUpdate() {
		//daoPostgres.create(appointment1);
		// get appointment by user_id to get the appointment id
		// then updated
		//appointment1 = new Appointment(ID NEED TO GET IT FIRST, LocalDate.now().plusDays(1), LocalTime.now().plusHours(2),1,1);
		
		//assertTrue(daoPostgres.update(appointment1));
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllAppointmentsByUserId() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllAppointmentsTimeByDate() {
		fail("Not yet implemented");
	}
}
