package com.oracle_hbase.dao;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.oracle_hbase.model.Oracle;

import oracle.jdbc.OracleTypes;


public class OracleDao {
	final private static String driverName="oracle.jdbc.driver.OracleDriver";
	final private static String oracleURL="jdbc:oracle:thin:@//localhost:1521/orclpdb1";
	final private static String oracleUsername="duy_oracle";
	final private static String oraclePass="duy_oracle";
	private static final String putWord = "BEGIN PUT_WORD(?,?); END;";
	private static final String deleteWord = "BEGIN DELETE_WORD(?); END;";
	private static final String getWord = "select WORD, stt from DICTIONARY where WORD LIKE ?";
	private static Connection conn;
	private static CallableStatement cs;
	
	//Select user by name 
	public static void createConnectionforputWord() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(oracleURL,oracleUsername,oraclePass);
		cs = conn.prepareCall(putWord);
	}
	public static void putWord(String word, int stt) throws SQLException {
		cs.setInt(1, stt);
		cs.setString(2, word);
		cs.execute();
	}
	public static void closeConnectionfordeleteWord() throws SQLException {
		cs.close();
		conn.close();
	}
	
	public static void createConnectionfordeleteWord() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(oracleURL,oracleUsername,oraclePass);
		cs = conn.prepareCall(deleteWord);
	}
	public static void deleteWord(String word) throws SQLException {
		cs.setString(1, word);
		cs.execute();
	}
	public static void closeConnectionforputWord() throws SQLException {
		cs.close();
		conn.close();
	}
	
	public static List<Oracle> getWord(String word_in) throws SQLException, IOException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(oracleURL,oracleUsername,oraclePass);
		
		PreparedStatement ps = conn.prepareStatement(getWord);
		ps.setString(1,word_in+"%");
		ResultSet rs = ps.executeQuery();
		List<Oracle> list = new ArrayList<>();
		while (rs.next()) {
			String word_out = rs.getString(1);
			int stt_out = rs.getInt(2);
			Oracle new_oracle = new Oracle();
			new_oracle.set_enWord(word_out);
			new_oracle.setStt(stt_out);
			list.add(new_oracle);
		}
		ps.close();
		conn.close();
		return list;
	}
	
}
