package cn.com.hq.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BaseSqlMapper<T> extends SqlMapper {
    
    public void add(T entity) throws DataAccessException;
    
    public void edit(T entity) throws DataAccessException;
    
    public void remvoe(T entity) throws DataAccessException;
    
    public T get(T entity) throws DataAccessException;
    
    public List<T> getList(T entity) throws DataAccessException;
    
    public List<T> getListByName(String value) throws DataAccessException;
    
    public String getFormateDate(Map<String , String> dates) throws DataAccessException;
}
