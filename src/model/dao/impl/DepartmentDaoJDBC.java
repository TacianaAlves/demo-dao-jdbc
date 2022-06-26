package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import db.DB;
import db.DbException;
import model.entities.Department;

public class DepartmentDaoJDBC {

	private Connection conn;
	public DepartmentDaoJDBC(Connection conn) {
	this.conn = conn;
	}
	
	public void insert(Department obj) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO Department (Name) VALUES (? ) " );
			//stmt.setInt(1, obj.getId());
			stmt.setString(1,obj.getNome());
			stmt.executeUpdate();
			
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stmt);
		}
	}
	public  void buscar() {
		
	}
	public void update() {
		
	}
	public  void delete() {
		
	}
}
