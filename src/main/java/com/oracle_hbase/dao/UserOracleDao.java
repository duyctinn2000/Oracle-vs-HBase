package com.oracle_hbase.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oracle_hbase.common.UserType;
import com.oracle_hbase.model.User;

import oracle.jdbc.OracleTypes;

public class UserOracleDao {
	final private static String driverName="oracle.jdbc.driver.OracleDriver";
	final private static String oracleURL="jdbc:oracle:thin:@//localhost:1521/orclpdb1";
	final private static String oracleUsername="duy_oracle";
	final private static String oraclePass="duy_oracle";
	private static final String SELECT_USER_BY_USER_NAME = "BEGIN GET_ACCOUNT(?,?,?); END;";
	
	//Same as selectUserByUserName
	public static User getUserFromUsername(String username) throws SQLException {
		//return UserData.getInstance().getUserFromUsername(username);
		return selectUserByUserName(username);
	}
	
	//Select user by name 
	public static User selectUserByUserName(String userName) throws SQLException {
		User user= new User();
		try {
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(oracleURL,oracleUsername,oraclePass);
			CallableStatement cs=conn.prepareCall(SELECT_USER_BY_USER_NAME);
			cs.setString(1, userName);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.execute();
			String password = cs.getString(2);
			String usertype = cs.getString(3);
			user.setUsername(userName);
			user.setPassword(password);
			for (UserType iterator: UserType.values()) {
				if (iterator.toString().equals(usertype)) {
					user.setUsertype(iterator);
					break;
				}
			}
			conn.close();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return user;
	}
	
}
