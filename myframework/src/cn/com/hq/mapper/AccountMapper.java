package cn.com.hq.mapper;

import java.util.List;

import cn.com.hq.entity.Account;

public interface AccountMapper<T extends Account>  extends BaseSqlMapper<T> {
    
    public List<T> getAllAccount();
    public List<T> queryAccountByName(String name);
}
