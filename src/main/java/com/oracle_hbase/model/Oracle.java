package com.oracle_hbase.model;


public class Oracle {
	private String en_word;
	private String vi_word;
	private int stt;

	public Oracle(Oracle oracle) {
		this.en_word = oracle.en_word;
		this.vi_word = oracle.vi_word;
		this.stt = oracle.stt;
	}
	public Oracle() {
		this.en_word = "";
		this.stt = 0;
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

	public int getStt() {
		return stt;
	}
	public void setStt(int stt) {
		this.stt = stt;
	}
}
