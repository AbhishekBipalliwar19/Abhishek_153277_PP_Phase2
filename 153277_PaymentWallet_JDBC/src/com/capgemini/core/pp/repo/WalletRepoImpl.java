package com.capgemini.core.pp.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.capgemini.core.pp.beans.Customer;
import com.capgemini.core.pp.beans.Wallet;
import com.capgemini.core.pp.exception.InvalidInputException;



public class WalletRepoImpl implements WalletRepo{
	Customer customer;
	Connection connection;
//	private Map<String, Customer> data=new HashMap<String, Customer>(); 
//	public WalletRepoImpl(Map<String, Customer> data) {
//		this.data = data;
//	}

	
	public WalletRepoImpl() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr" ,"hr");
		} catch (Exception e) {
		}
	}


	@Override
	public boolean save(Customer customer) throws InvalidInputException {
			try {
				String insertQuery = "insert into Customer2 values(?,?,?)";
				PreparedStatement stmt = connection.prepareStatement(insertQuery);
				stmt.setString(1, customer.getMobileNo());
				stmt.setString(2, customer.getName());
				stmt.setBigDecimal(3, customer.getWallet().getBalance());
				stmt.execute();
				return true;
			} catch (SQLException e) {
				System.err.println("Phone number already exists");
			} catch (Exception e) {
				System.out.println("Incorrect input");
			}
			return false;
	}

	@Override
	public Customer findOne(String mobileNo) throws InvalidInputException{
		try {
			String searchQuery = "select * from Customer2 where MOBILENUMBER=?";
			PreparedStatement pstmt = connection.prepareStatement(searchQuery);
			pstmt.setString(1, mobileNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setMobileNo(rs.getString(1));
				customer.setName(rs.getString(2));
				customer.setWallet(new Wallet(rs.getBigDecimal(3)));
				return customer;
			}
		} catch (Exception e) {
			
		}
		return customer;
	
	}
	
	public Customer update(Customer customer) throws InvalidInputException{
		String updateQuery = "update customer2 set balance=? where mobilenumber=?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(updateQuery);
			pstmt.setBigDecimal(1, customer.getWallet().getBalance());
			pstmt.setString(2, customer.getMobileNo());
			pstmt.executeQuery();
			return customer;
		} catch (Exception e) {
			
		}
		return customer;
		
	}
	
	public boolean truncate() {
		try {
			String truncateQuery = "truncate table customer2";
			Statement pstmt = connection.createStatement();
			pstmt.executeUpdate(truncateQuery);
			return true;
		} catch (SQLException e) {
			
		}
	return false;
	}
	
}
