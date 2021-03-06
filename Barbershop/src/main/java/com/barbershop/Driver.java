package com.barbershop;

import com.barbershop.dao.BaseDao;
import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.User;

public class Driver {

	public static void main(String[] args) {

		UserDaoPostgres teBaseDao = new UserDaoPostgres();
		// System.out.println(teBaseDao.findAll());
		// User testUser = new User("Mike", "Smith", "222-222-1111","mike@smith.com",
		// "Customer", "1234");
		// User testUser = new User(3,"Sue", "Storm", "444-333-2222","sue@storm.com",
		// "Customer", "1234");
		// teBaseDao.update(testUser);
		// teBaseDao.create(testUser);
		// teBaseDao.deleteById(3);
		//System.out.println(teBaseDao.isExist("ifdf@shobaki.com"));
		
		//System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1234"));
		//System.out.println(teBaseDao.getUserInfo("mike@smith.net", "1234"));
		System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1111"));
	}

}
