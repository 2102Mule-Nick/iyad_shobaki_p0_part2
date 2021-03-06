package com.barbershop.test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
	private Connection fakeConnection;

	private static AppointmentDaoPostgres daoPostgres;
	private static Appointment appointment1;
	private static Appointment appointment2;
	private static Appointment appointment3;

	private static Connection realConnection;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		realConnection = ConnectionFactoryPostgres.getConnection("TestingDb");
	}

	@BeforeEach
	private void setUp() {
		daoPostgres = new AppointmentDaoPostgres();
		appointment1 = new Appointment(LocalDate.now().plusDays(1), LocalTime.now(), 1, 1);
		appointment2 = new Appointment(LocalDate.now().plusDays(2), LocalTime.now(), 2, 1);
		appointment3 = new Appointment(LocalDate.now().plusDays(3), LocalTime.now(), 1, 1);
	}

	@AfterEach
	private void tearDown() {
		daoPostgres.setConnection(realConnection);
		daoPostgres.deleteAll();
	}

	@Test
	void testCreate() throws SQLException {

		// creating a real stmt to be able to actually communicate with our db
		String sql = "insert into appointment (appointment_date, appointment_time, user_id, service_id) values (? , ? , ? , ?)";

		PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareStatement(sql)).thenReturn(spy);

		daoPostgres.setConnection(fakeConnection);

		// call the create cart method that we are testing
		daoPostgres.create(appointment2);

		// verifying all the correct methods are being called on our REAL stmt
		// this can only work because we are spying on the stmt
		verify(spy).setDate(1, java.sql.Date.valueOf(appointment2.getAppointmentDate()));
		verify(spy).setTime(2, java.sql.Time.valueOf(appointment2.getAppointmentTime()));
		verify(spy).setInt(3, appointment2.getUserId());
		verify(spy).setInt(4, appointment2.getServiceId());

		verify(spy).execute();

		// making a second call to the db, to ensure that the cart was actually created
		PreparedStatement checkStmt = realConnection.prepareStatement("select * from appointment where user_id = 2");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());

	}

	@Test
	void testGetAllUsersAppointmentsDetails() throws SQLException {
		
		daoPostgres.setConnection(realConnection);

		// call the create cart method that we are testing
		daoPostgres.create(appointment1);
		
		// creating a real stmt to be able to actually communicate with our db
		String sql = "select a.appointment_id, ua.user_id, ua.first_name, ua.last_name, ua.email_address, ua.phone_number, "
				+ "ua.user_role, ss.service_name, ss.duration , ss.price, a.appointment_date , a.appointment_time "
				+ "from salon_service ss inner join appointment a on ss.service_id = a.service_id "
				+ "inner join user_acc ua on ua.user_id = a.user_id "
				+ "order by ua.email_address, a.appointment_date , a.appointment_time desc";

		PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareStatement(sql)).thenReturn(spy);

		daoPostgres.setConnection(fakeConnection);
		// call the create cart method that we are testing
		daoPostgres.getAllUsersAppointmentsDetails();


		verify(spy).executeQuery();
		
		PreparedStatement checkStmt = realConnection.prepareStatement(sql);

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}

	@Test
	void testUpdate() throws SQLException {

		
		
		// creating a real stmt to be able to actually communicate with our db
		// Date, time, service_id, appointment_id
		String sql = "select update_appointment(?,?,?,?);";

		// Calling a function
		CallableStatement realStmt = realConnection.prepareCall(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		CallableStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareCall(sql)).thenReturn(spy);

		daoPostgres.setConnection(fakeConnection);
		// call the create cart method that we are testing
		appointment1.setAppointmentId(22);
		appointment1.setAppointmentDate(LocalDate.parse("2021-05-05"));
		appointment1.setAppointmentTime(LocalTime.parse("03:00:00"));
		daoPostgres.update(appointment1);


		verify(spy).setDate(1, java.sql.Date.valueOf(appointment1.getAppointmentDate()));
		verify(spy).setTime(2, java.sql.Time.valueOf(appointment1.getAppointmentTime()));
		verify(spy).setInt(3, appointment1.getUserId());
		verify(spy).setInt(4, appointment1.getAppointmentId());
		verify(spy).execute();
		
		// making a second call to the db, to ensure that the cart was actually created
		PreparedStatement checkStmt = realConnection.prepareStatement("select * from appointment where appointment_date = '2021-05-05'");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}

	@Test
	void testFindAll() {
//		daoPostgres.create(appointment1);
//		daoPostgres.create(appointment2);
//		daoPostgres.create(appointment3);
//
//		assertEquals(3, daoPostgres.findAll().size());
	}

	@Test
	void testDeleteById() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetAllAppointmentsByUserId() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetAllAppointmentsTimeByDate() {
		//fail("Not yet implemented");
	}
}
