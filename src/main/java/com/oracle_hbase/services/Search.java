package com.oracle_hbase.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle_hbase.dao.HBaseDao;
import com.oracle_hbase.dao.OracleDao;
import com.oracle_hbase.model.HBase;
import com.oracle_hbase.model.Oracle;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String word_in = request.getParameter("word_in");
		String db = request.getParameter("db");
		if (db.contentEquals("oracle")) {
			try {
				long start = System.nanoTime();
				List<Oracle> word_list = OracleDao.getWord(word_in);
				long end = System.nanoTime();
			    float elapsedTime = (end - start)/1000000000;
			    request.setAttribute("search_time","Thời gian tìm kiếm trong Oracle là: " + String.format("%.5f", elapsedTime) + " s");
				request.setAttribute("word_list", word_list);
				request.setAttribute("word", word_in);
				request.setAttribute("search_total", "Tổng số từ tìm thấy là: " + Integer.toString(word_list.size()));
		        RequestDispatcher rd=request.getRequestDispatcher("search");
		        rd.forward(request, response);
		        return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			HBaseDao.createConnection();
			long start = System.nanoTime();
			List<HBase> word_list = HBaseDao.getWord(word_in);
			long end = System.nanoTime();
		    double elapsedTime = (end - start)/1000000000.0;
		    request.setAttribute("search_time","Thời gian tìm kiếm trong HBase là: " + String.format("%.5f", elapsedTime) + " s");
			request.setAttribute("word_list", word_list);
			request.setAttribute("word", word_in);
			request.setAttribute("search_total", "Tổng số từ tìm thấy là: " + Integer.toString(word_list.size()));
			HBaseDao.closeConnection();
	        RequestDispatcher rd=request.getRequestDispatcher("search");
	        rd.forward(request, response);
	        return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
