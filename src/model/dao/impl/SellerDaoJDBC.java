package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao  {

	private Connection conn;
	public SellerDaoJDBC(Connection conn) {
	this.conn = conn;
		
	}
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findBYId(Integer id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("Select seller. * ,department.Name as DepName ");
			sb.append("From seller Inner Join department ");
			sb.append("On seller.DepartmentId = department.Id ");	
			sb.append("Where seller.Id = ? ");
			
			stmt = conn.prepareStatement(sb.toString());
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			
			if(rs.next()) {
				Department dp = instantiateDepartment(rs);
				Seller  s = instantiateSeller(rs, dp);
				
				return s;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(stmt);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller s = new Seller();
		s.setId(rs.getInt("Id"));
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBaseSalary(rs.getDouble("BaseSalary"));
		s.setBirthDate(rs.getDate("BirthDate"));
		s.setDepartment(dp);
		return s;
	}
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setNome(rs.getString("DepName"));
		return dp;
	}
	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
