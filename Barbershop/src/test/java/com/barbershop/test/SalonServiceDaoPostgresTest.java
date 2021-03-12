package com.barbershop.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.barbershop.dao.SalonServiceDaoPostgres;
import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.SalonService;
import com.barbershop.pojo.User;
import com.barbershop.util.ConnectionFactoryPostgres;

@ExtendWith(MockitoExtension.class)
class SalonServiceDaoPostgresTest {

	@Mock
	private Connection fakeConnection;

	private static SalonServiceDaoPostgres salonServiceDaoPostgres;
	private static SalonService service1;
	private static SalonService service2;

	private static Connection realConnection;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		realConnection = ConnectionFactoryPostgres.getConnection("TestingDb");
	}

	@BeforeEach
	private void setUp() {
		salonServiceDaoPostgres = new SalonServiceDaoPostgres();
		service1 = new SalonService("KIDS CUT", "Child's haircut are for 14 and under", "30 min", 20.00f);
		service2 = new SalonService("FACE SHAVE", "Includes hot twoel, skin cleaner.", "35 min", 25.00f);
	}

	@AfterEach
	private void tearDown() {
		salonServiceDaoPostgres.setConnection(realConnection);
		salonServiceDaoPostgres.deleteAll();
	}

	@Test
	void testCreate() throws SQLException {

		String sql = "insert into salon_service (service_name, description, duration, price)"
				+ " values (? , ? , ? , ?)";

		PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareStatement(sql)).thenReturn(spy);

		salonServiceDaoPostgres.setConnection(fakeConnection);

		// call the create cart method that we are testing
		salonServiceDaoPostgres.create(service1);

		// verifying all the correct methods are being called on our REAL stmt
		// this can only work because we are spying on the stmt

		verify(spy).setString(1, service1.getServiceName());
		verify(spy).setString(2, service1.getDescription());
		verify(spy).setString(3, service1.getDuration());
		verify(spy).setFloat(4, service1.getPrice());

		verify(spy).execute();

		// making a second call to the db, to ensure that the cart was actually created
		PreparedStatement checkStmt = realConnection
				.prepareStatement("select * from salon_service where service_name = 'KIDS CUT'");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}

	@Test
	void testFindAll() throws SQLException {

		salonServiceDaoPostgres.setConnection(realConnection);

		// call the create cart method that we are testing
		salonServiceDaoPostgres.create(service2);

		// creating a real stmt to be able to actually communicate with our db
		String sql = "select * from salon_service order by service_id asc";

		PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareStatement(sql)).thenReturn(spy);

		salonServiceDaoPostgres.setConnection(fakeConnection);
		// call the create cart method that we are testing
		salonServiceDaoPostgres.findAll();

		verify(spy).executeQuery();

		PreparedStatement checkStmt = realConnection.prepareStatement("select * from salon_service where service_name = 'FACE SHAVE'");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}

	@Test
	void testUpdate() throws SQLException {
		
		// creating a real stmt to be able to actually communicate with our db
		String sql = "update salon_service set service_name = ?, description = ?, duration = ?, "
				+ "price = ? where service_id = ?";

		// Calling a function
		PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareStatement(sql)).thenReturn(spy);

		salonServiceDaoPostgres.setConnection(fakeConnection);
		// call the create cart method that we are testing
		service1 = new SalonService(1, "NAME", "Here is a description", "45 min", 35.00f);
		salonServiceDaoPostgres.update(service1);

		verify(spy).setString(1, service1.getServiceName());
		verify(spy).setString(2, service1.getDescription());
		verify(spy).setString(3, service1.getDuration());
		verify(spy).setFloat(4, service1.getPrice());
		verify(spy).setInt(5, service1.getServiceId());
	
		verify(spy).execute();

		// making a second call to the db, to ensure that the cart was actually created
		PreparedStatement checkStmt = realConnection
				.prepareStatement("select * from salon_service where service_name = 'NAME'");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}

	@Test
	void testDeleteById() {
	}

	@Test
	void testGetSalonServiceByName() {
	}

	@Test
	void testGetSalonServiceById() {
	}

}
