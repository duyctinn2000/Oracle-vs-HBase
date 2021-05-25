package com.oracle_hbase.model;

public class HBase {
	private String en_word;
	private String vi_word;
	private String stt;

	public HBase(HBase hbase) {
		this.en_word = hbase.en_word;
		this.vi_word = hbase.vi_word;
		this.stt = hbase.stt;
	}
	public HBase() {
		this.en_word = "";
		this.stt = "";
		this.vi_word = "";
	}
	
	public String get_enWord() {
		return en_word;
	}
	public void set_enWord(String en_word) {
		this.en_word = en_word;
	}
	public String get_viWord() {
		return vi_word;
	}
	public void set_viWord(String vi_word) {
		this.vi_word = vi_word;
	}

	public String getStt() {
		return stt;
	}
	public void setStt(String stt_out) {
		this.stt = stt_out;
	}
}
