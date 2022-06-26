package application;

import model.dao.DaoFactory;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;

public class Program2 {
	public static void main(String[] args) {
		
		
		System.out.println("\n==== TEST 1: department insert=====");
		Department dp = new Department(null, "Ti");
		DepartmentDaoJDBC departmentDaoJdbc = DaoFactory.createDepartmentDao();
		
		departmentDaoJdbc.insert(dp);
		
		System.out.println("Inserted!");
	}
	

	
	
}
