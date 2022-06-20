package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void delteById(Integer id);
	Department findBYId(Integer id);
	List<Department> findAll();
	
}
