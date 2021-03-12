package com.barbershop.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.SalonService;
import com.barbershop.pojo.User;
import com.barbershop.util.ConnectionFactoryPostgres;

@ExtendWith(MockitoExtension.class)
class UserDaoPostgresTest {


	@Mock
	private Connection fakeConnection;
	
	
	private static UserDaoPostgres userDaoPostgres;
	private static User user1;
	private static User user2;
	
	private static Connection realConnection;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		realConnection = ConnectionFactoryPostgres.getConnection("TestingDb");
	}

	@BeforeEach
	private void setUp() {
		userDaoPostgres = new UserDaoPostgres();
		user1 = new User("Kevin", "Cains", "111-222-3344","kevin@cains.com", "Pwd12345@");
		user2 = new User("Terra", "Dunne", "111-234-6666","terra@dunne.net", "Pwd12345@");
	}

	@AfterEach
	private void tearDown() {
		userDaoPostgres.setConnection(realConnection);
		userDaoPostgres.deleteAll();
	}


	@Test
	void testCreate() throws SQLException {
		
		String sql = "insert into user_acc (first_name, last_name, phone_number, email_address, user_role, user_password)"
				+ " values (? , ? , ? , ?, ? , ?)";

		PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareStatement(sql)).thenReturn(spy);

		userDaoPostgres.setConnection(fakeConnection);

		// call the create cart method that we are testing
		userDaoPostgres.create(user1);

		// verifying all the correct methods are being called on our REAL stmt
		// this can only work because we are spying on the stmt
		
		verify(spy).setString(1, user1.getFirstName());
		verify(spy).setString(2, user1.getLastName());
		verify(spy).setString(3, user1.getPhoneNumber());
		verify(spy).setString(4, user1.getEmailAddress());
		verify(spy).setString(5, user1.getRole());
		verify(spy).setString(6, user1.getPassword());
	

		verify(spy).execute();

		// making a second call to the db, to ensure that the cart was actually created
		PreparedStatement checkStmt = realConnection.prepareStatement("select * from user_acc where email_address = 'kevin@cains.com'");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}
	
	
	@Test
	void testFindAll() throws SQLException {
		
		userDaoPostgres.setConnection(realConnection);

		// call the create cart method that we are testing
		userDaoPostgres.create(user2);
		
		// creating a real stmt to be able to actually communicate with our db
		String sql = "select * from user_acc order by user_id asc";

		PreparedStatement realStmt = realConnection.prepareStatement(sql);

		// Spying on our real stmt, so that we can later verify the correct methods are
		// invoked
		PreparedStatement spy = Mockito.spy(realStmt);

		// setting up our Mock connection, to reaturn our real stmt, we are spying on
		// if we did not do this, and used a real connection on this test, the
		// connection would create
		// a new statement inside of our createCart method, and we could not spy on it
		when(fakeConnection.prepareStatement(sql)).thenReturn(spy);

		userDaoPostgres.setConnection(fakeConnection);
		// call the create cart method that we are testing
		userDaoPostgres.findAll();


		verify(spy).executeQuery();
		
		PreparedStatement checkStmt = realConnection.prepareStatement(sql);

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}
	
	@Test
	void testUpdate() throws SQLException {
		
		// creating a real stmt to be able to actually communicate with our db
		String sql = "update user_acc set first_name = ?, last_name = ?, phone_number = ?, "
				+ "email_address = ?, user_role = ?, user_password = ?  where user_id = ?";

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

		userDaoPostgres.setConnection(fakeConnection);
		// call the create cart method that we are testing
		user1 = new User(2, "Update Test", "Update Testing", "999-999-9999", "update@testing.com","Test Role", "NewPassword");
		userDaoPostgres.update(user1);

		verify(spy).setString(1, user1.getFirstName());
		verify(spy).setString(2,user1.getLastName());
		verify(spy).setString(3, user1.getPhoneNumber());
		verify(spy).setString(4, user1.getEmailAddress());
		verify(spy).setString(5, user1.getRole());
		verify(spy).setString(6, user1.getPassword());
		verify(spy).setInt(7, user1.getUserId());
	
		verify(spy).execute();

		// making a second call to the db, to ensure that the cart was actually created
		PreparedStatement checkStmt = realConnection
				.prepareStatement("select * from user_acc where email_address = 'update@testing.com'");

		ResultSet rs = checkStmt.executeQuery();

		assertTrue(rs.next());
	}
	
	@Test
	void testUpdateUerRole() {
		//fail("Not yet implemented");
	}


	@Test
	void testDeleteById() {
		//fail("Not yet implemented");
	}

	@Test
	void testIsExist() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetUserInfo() {
		//fail("Not yet implemented");
	}

	

}
