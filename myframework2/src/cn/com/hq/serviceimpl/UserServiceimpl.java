package cn.com.hq.serviceimpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import cn.com.hq.dao.Dao;
import cn.com.hq.dao.UserDAO;
import cn.com.hq.entity.Account;

public class UserServiceimpl implements cn.com.hq.service.UserService {
	private UserDAO userDAO;

	@Override
	public List<Account> queryAccountByName(String name) {
		List<Account> accountList = userDAO.queryAccountByName(name);
		if(accountList.size()>0){
			return accountList;
		}else{
			return null;
		}
		
	}
	@Override
	public void savaOrUpdateAccount(Account a) {
		if(userDAO.queryAccountByName(a.getName()).size()>0){
			userDAO.updateUser(a);
		}else{
			userDAO.saveUser(a);
		}
		
	}
	@Override
	public boolean  createAccount(Account a){
		String sql = "INSERT INTO `ACCOUNT`(`accountid`, `name`, `password`, `age`, `address`, `email`, `phone`, `description`)"
				+ " VALUES ("
				+ "'" + a.getAccountid() +"',"
				+ "'" + a.getName() +"',"
				+ "'" + a.getPassWord() +"',"
				+ "" + a.getAge() +","
				+ "'" + a.getAddress() +"',"
				+ "'" + a.getEmail() +"',"
				+ "'" + a.getPhone() +"',"
				+ "'" + a.getDescription() + 
				"')";
		Connection connection =  Dao.getCommonConnection();
		Statement stmt;
		boolean result = false;
		try {
			stmt = connection.createStatement();
			result = stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
       return result;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
}
