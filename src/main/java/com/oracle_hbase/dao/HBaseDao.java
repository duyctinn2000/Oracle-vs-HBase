package com.oracle_hbase.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.oracle_hbase.model.HBase;


public class HBaseDao {
	
	//Same as selectUserByUserName
	private static Connection connection;
	private static Table table;
	
	//Select user by name 
	public static void createConnection() throws IOException {
		
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum","localhost");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		connection = ConnectionFactory.createConnection(conf);
		table = connection.getTable(TableName.valueOf("dictionary"));

	}
	public static void putWord(String word, int s) throws IOException {		
		Put	put = new Put(Bytes.toBytes(word));
	   	put.addColumn(Bytes.toBytes("stt"),Bytes.toBytes("index"),Bytes.toBytes(Integer.toString(s)));
		table.put(put);
	}
	
	public static void deleteWord(String word) throws IOException {		
		Delete	delete = new Delete(Bytes.toBytes(word));
		table.delete(delete);
	}
	
	public static List<HBase> getWord(String word_in) throws IOException {
		List<HBase> list = new ArrayList<>();
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("stt"),Bytes.toBytes("index"));
		scan.setRowPrefixFilter(Bytes.toBytes(word_in));
		ResultScanner rs = table.getScanner(scan);
		try {
		  for (Result r = rs.next(); r != null; r = rs.next()) {
			String word_out = Bytes.toString(r.getRow());
			String stt_out = Bytes.toString(r.getValue("stt".getBytes(), "index".getBytes()));
			HBase new_hbase = new HBase();
			new_hbase.set_enWord(word_out);
			new_hbase.setStt(stt_out);
			list.add(new_hbase);
		  }
		} finally {
		  rs.close();  // always close the ResultScanner!
		}
		return list;
	}
	
	public static void putWord_col(String word, int s) throws IOException {		
		Put	put = new Put(Bytes.toBytes(Integer.toString(s)));
	   	put.addColumn(Bytes.toBytes("wordlist"),Bytes.toBytes("word"),Bytes.toBytes(word));
		table.put(put);
	}
	
	public static void deleteWord_col(String word) throws IOException {		
		Scan scan = new Scan();
		scan.setLimit(1);
		scan.addColumn(Bytes.toBytes("wordlist"), Bytes.toBytes("word"));
		ValueFilter vf = new ValueFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes(word)));
		scan.setFilter(vf);
		ResultScanner rs = table.getScanner(scan);
		Result r = rs.next(); 
		String stt_out = Bytes.toString(r.getRow());
		Delete delete = new Delete(Bytes.toBytes(stt_out));
		table.delete(delete);
		rs.close();  // always close the ResultScanner
	}
	
	public static void closeConnection() throws IOException {
		table.close();
		connection.close();
	}
	
	public static List<HBase> getWord_col(String word_in) throws IOException {
		List<HBase> list = new ArrayList<>();
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("wordlist"), Bytes.toBytes("word"));
		BinaryPrefixComparator comp = new BinaryPrefixComparator(word_in.getBytes()); 
		ValueFilter vf = new ValueFilter(CompareOperator.EQUAL, comp);
		scan.setFilter(vf);
		ResultScanner rs = table.getScanner(scan);
		try {
		  for (Result r = rs.next(); r != null; r = rs.next()) {
			String stt_out = Bytes.toString(r.getRow());
			String word_out = Bytes.toString(r.getValue("wordlist".getBytes(), "word".getBytes()));
			HBase new_hbase = new HBase();
			new_hbase.set_enWord(word_out);
			new_hbase.setStt(stt_out);
			list.add(new_hbase);
		  }
		} finally {
		  rs.close();  // always close the ResultScanner!
		}
		return list;
		
	}

}
