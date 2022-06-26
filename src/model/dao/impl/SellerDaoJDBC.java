package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

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
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+  "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, obj.getName());
			stmt.setString(2, obj.getEmail());
			stmt.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			stmt.setDouble(4,obj.getBaseSalary());
			stmt.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stmt);
		}
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId =? WHERE Id = ? " );
			stmt.setString(1, obj.getName());
			stmt.setString(2, obj.getEmail());
			stmt.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			stmt.setDouble(4,obj.getBaseSalary());
			stmt.setInt(5, obj.getDepartment().getId());
			stmt.setInt(6, obj.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stmt);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM seller WHERE Id= ? " );
			stmt.setInt(1,id);
			
			int rows = stmt.executeUpdate();
			if(rows == 0) {
				System.out.println("Esse id não existe");
			}else {
				System.out.println("Delete complited");
				
			}
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(stmt);
		}
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			
			
			sb.append("Select seller. * ,department.Name as DepName ");
			sb.append("From seller Inner Join department ");
			sb.append("On seller.DepartmentId = department.Id ");	
			sb.append("ORDER BY Name ");
			
			stmt = conn.prepareStatement(sb.toString());
			
		
			rs = stmt.executeQuery();
			
			List<Seller>list =new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			
			while(rs.next()) {
				
				Department dp = map.get(rs.getInt("DepartmentId"));
				
				if(dp == null) {
					
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
					
				}
						
				Seller obj = instantiateSeller(rs, dp);
				list.add(obj);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(stmt);
			DB.closeResultSet(rs);
		}
	}
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			
			
			sb.append("Select seller. * ,department.Name as DepName ");
			sb.append("From seller Inner Join department ");
			sb.append("On seller.DepartmentId = department.Id ");	
			sb.append("Where DepartmentId = ? ");
			sb.append("ORDER BY Name ");
			
			stmt = conn.prepareStatement(sb.toString());
			
			stmt.setInt(1, department.getId());
			rs = stmt.executeQuery();
			
			List<Seller>list =new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			
			while(rs.next()) {
				
				Department dp = map.get(rs.getInt("DepartmentId"));
				
				if(dp == null) {
					
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
					
				}
						
				Seller obj = instantiateSeller(rs, dp);
				list.add(obj);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(stmt);
			DB.closeResultSet(rs);
		}
		
	}

}
