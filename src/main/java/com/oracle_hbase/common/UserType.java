package com.oracle_hbase.common;

public enum UserType {
	ORACLE, HBASE;
	
	private String string;
	
	static {
		ORACLE.string = "oracle";
		HBASE.string = "hbase";
	}
	
	public String toString() {
		return string;
	}
}
