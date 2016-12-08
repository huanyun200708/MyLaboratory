package cn.com.hq.dao;

import java.util.List;

import cn.com.hq.entity.Account;

public interface UserDAO {
	public void saveUser(Account u);

	public void updateUser(Account u);

	public void deleteUser(Account u);

	public Account getUser(Account u);
	
	public List<Account> queryAccountByName(String name);
}
