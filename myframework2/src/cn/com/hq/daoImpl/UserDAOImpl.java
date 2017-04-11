package cn.com.hq.daoImpl;

import java.util.List;

import cn.com.hq.dao.UserDAO;
import cn.com.hq.entity.Account;

public class UserDAOImpl implements UserDAO {
	public void saveUser(Account u) {
	}

	public void updateUser(Account u) {
	}

	public void deleteUser(Account u) {
		System.out.println("user deleted!");
	}

	public Account getUser(Account u) {
		if ("admin".endsWith(u.getName()) && "admin123".equals(u.getPassWord())) {
			u.setAge(18);
			return u;
		}
		return null;
	}

	@Override
	public List<Account> queryAccountByName(String name) {
		return null;
	}
}
