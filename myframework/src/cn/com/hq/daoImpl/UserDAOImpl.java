package cn.com.hq.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.com.hq.dao.UserDAO;
import cn.com.hq.entity.Account;
import cn.com.hq.mapper.AccountMapper;

public class UserDAOImpl implements UserDAO {
	@Autowired
	AccountMapper<Account> mapper;
	public void saveUser(Account u) {
		mapper.add(u);
	}

	public void updateUser(Account u) {
		mapper.edit(u);
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
	
	public AccountMapper<Account> getMapper() {
		return mapper;
	}

	public void setMapper(AccountMapper<Account> mapper) {
		this.mapper = mapper;
	}
	public List<Account> queryAccountByName(String name){
		return mapper.queryAccountByName(name);
	}
}
