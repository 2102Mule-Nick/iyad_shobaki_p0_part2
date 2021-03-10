package com.barbershop.test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.barbershop.dao.AppointmentDaoPostgres;
import com.barbershop.pojo.Appointment;
import com.barbershop.util.ConnectionFactoryPostgres;

@ExtendWith(MockitoExtension.class)
class AppointmentDaoPostgresTest {

	@Mock
	private Connection connection;

	// private static final String DATABASE_ENV = "TestingDb";
	public static AppointmentDaoPostgres daoPostgres;
	public static Appointment appointment1;
	public static Appointment appointment2;
	public static Appointment appointment3;

	@BeforeEach
	private void setUp() {
		daoPostgres = new AppointmentDaoPostgres();
		appointment1 = new Appointment(LocalDate.now().plusDays(1), LocalTime.now(), 1, 1);
		appointment2 = new Appointment(LocalDate.now().plusDays(2), LocalTime.now(), 1, 1);
		appointment3 = new Appointment(LocalDate.now().plusDays(3), LocalTime.now(), 1, 1);
	}

	@AfterEach
	private void tearDown() {
		daoPostgres.deleteAll();
	}

	@Test
	void testCreate() throws SQLException {

		// creating a real stmt to be able to actually communicate with our db
		String sql = "insert into appointment (appointment_date, appointment_time, user_id, service_id) values (? , ? , ? , ?)";

		Connection realConnection = ConnectionFactoryPostgres.getConnection();

		//PreparedStatement realStmt = realConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		 PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		//when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(spy);
		 when(connection.prepareStatement(sql)).thenReturn(spy);

		// call the create cart method that we are testing
		daoPostgres.create(appointment1);

		// verifying all the correct methods are being called on our REAL stmt
		// this can only work because we are spying on the stmt
		verify(spy).setDate(1, java.sql.Date.valueOf(appointment1.getAppointmentDate()));
		verify(spy).setTime(2, java.sql.Time.valueOf(appointment1.getAppointmentTime()));
		verify(spy).setInt(3, appointment1.getUserId());
		verify(spy).setInt(4, appointment1.getServiceId());

		verify(spy).execute();

		// making a second call to the db, to ensure that the cart was actually created
		PreparedStatement checkStmt = ConnectionFactoryPostgres.getConnection()
				.prepareStatement("select * from appointment where user_id = 1");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());

		// assertTrue(daoPostgres.create(appointment1));
	}

	@Test
	void testFindAll() {
		daoPostgres.create(appointment1);
		daoPostgres.create(appointment2);
		daoPostgres.create(appointment3);

		assertEquals(3, daoPostgres.findAll().size());
	}

	@Test
	void testUpdate() {
		// daoPostgres.create(appointment1);
		// get appointment by user_id to get the appointment id
		// then updated
		// appointment1 = new Appointment(ID NEED TO GET IT FIRST,
		// LocalDate.now().plusDays(1), LocalTime.now().plusHours(2),1,1);

		// assertTrue(daoPostgres.update(appointment1));
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
