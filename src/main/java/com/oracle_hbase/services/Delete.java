package com.oracle_hbase.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.oracle_hbase.dao.HBaseDao;
import com.oracle_hbase.dao.OracleDao;

/**
 * Servlet implementation class Delete
 */
@MultipartConfig
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String db = request.getParameter("db");
		Part filePart = request.getPart("file"); 

	    InputStream fileContent = filePart.getInputStream();
	    if (db.contentEquals("oracle")) {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
		    long start_setup = System.nanoTime();
		    try {
				OracleDao.createConnectionfordeleteWord();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    long stop_setup = System.nanoTime();
		    int stt=0;
		    long start_load = System.nanoTime();
		    while(reader.ready()) {
		        String line = reader.readLine();
		        if (line.contentEquals("") == true) 
		        	break;
		        try {
					OracleDao.deleteWord(line);
					stt = stt + 1;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    long stop_load = System.nanoTime();
		    try {
				OracleDao.closeConnectionfordeleteWord();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    double setup_time = (stop_setup - start_setup)/1000000000.0;
		    double load_time = (stop_load - start_load)/1000000000.0;
		    request.setAttribute("total_words","T???ng s??? t??? ???? xo?? th??nh c??ng l??: " + Integer.toString(stt));
		    request.setAttribute("load_time","Th???i gian x??a d??? li???u ra kh???i Oracle l??: " + String.format("%.5f", load_time) + " s");
		    request.setAttribute("setup_time","Th???i gian thi???t l???p k???t n???i v???i Oracle l??: " + String.format("%.5f", setup_time) + " s");
	        RequestDispatcher rd=request.getRequestDispatcher("delete");
	        rd.forward(request, response);
	        return;
	    }
	    else {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
		    long start_setup = System.nanoTime();
		    HBaseDao.createConnection();
		    long stop_setup = System.nanoTime();
	    	long start_load = System.nanoTime();
	    	int stt=0;
		    while(reader.ready()) {
		        String line = reader.readLine();
		        if (line.contentEquals("") == true) 
		        	break;
		    	stt = stt + 1;
		        //HBaseDao.deleteWord(line);
		    	HBaseDao.deleteWord_col(line);
		    }
		    long stop_load = System.nanoTime();
		    HBaseDao.closeConnection();
		    double setup_time = (stop_setup - start_setup)/1000000000.0;
		    double load_time = (stop_load - start_load)/1000000000.0;
		    request.setAttribute("total_words","T???ng s??? t??? ???? x??a th??nh c??ng l??: " + Integer.toString(stt));
		    request.setAttribute("load_time","Th???i gian x??a d??? li???u ra kh???i HBase l??: " + String.format("%.5f", load_time) + " s");
		    request.setAttribute("setup_time","Th???i gian thi???t l???p k???t n???i v???i HBase l??: " + String.format("%.5f", setup_time) + " s");
	        RequestDispatcher rd=request.getRequestDispatcher("delete");
	        rd.forward(request, response);
	        return;
	    }
	}
}
