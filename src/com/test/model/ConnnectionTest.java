package com.test.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.java.model.ConnectionDB;
import com.java.util.ComputerDBException;

public class ConnnectionTest {
	ConnectionDB connectiondb;

	@Before
	public void setUp() throws ComputerDBException {
		connectiondb = ConnectionDB.getInstance(ConnectionDB.SET_ENV_TEST);
	}

	@Test
	public void testGetConnectionNotNull() {

		Assert.assertNotNull(connectiondb);
		Assert.assertNotNull(connectiondb.getConnection());
	}

	@Test
	public void testGetConnection() throws ComputerDBException {
		ConnectionDB deuxiemeConnection;
		deuxiemeConnection = ConnectionDB.getInstance();
		Assert.assertTrue(connectiondb.equals(deuxiemeConnection));
	}
}
