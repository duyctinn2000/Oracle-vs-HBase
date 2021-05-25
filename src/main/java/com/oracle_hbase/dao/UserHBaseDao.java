package com.oracle_hbase.dao;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.oracle_hbase.common.UserType;
import com.oracle_hbase.model.User;

public class UserHBaseDao {
	
	//Same as selectUserByUserName
	public static User getUserFromUsername(String username) throws IOException {
		//return UserData.getInstance().getUserFromUsername(username);
		return selectUserByUserName(username);
	}
	
	//Select user by name 
	public static User selectUserByUserName(String userName) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum","localhost");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		Connection connection = ConnectionFactory.createConnection(conf);
		
		Table table = connection.getTable(TableName.valueOf("account"));
		Get	get = new Get(Bytes.toBytes(userName));
		
		Result result = table.get(get);
	    
		User user= new User();
		
		String u_pass = Bytes.toString(result.getValue("info".getBytes(), "pass".getBytes()));
		String u_type = Bytes.toString(result.getValue("info".getBytes(), "usertype".getBytes()));
		
		user.setUsername(userName);
		user.setPassword(u_pass.toString());
		for (UserType iterator: UserType.values()) {
			if (iterator.toString().equals(u_type.toString())) {
				user.setUsertype(iterator);
				break;
			}
		}
		connection.close();
		return user;
		
	}
}