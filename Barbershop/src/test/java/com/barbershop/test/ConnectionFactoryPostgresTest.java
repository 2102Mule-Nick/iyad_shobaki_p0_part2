package com.barbershop.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.barbershop.util.ConnectionFactoryPostgres;

class ConnectionFactoryPostgresTest {

	@Test
	void testGetConnection() {
		assertNotNull(ConnectionFactoryPostgres.getConnection(), "Connection to DB should be created.");
	}

}
