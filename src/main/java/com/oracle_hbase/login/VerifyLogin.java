package com.oracle_hbase.login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oracle_hbase.dao.UserOracleDao;
import com.oracle_hbase.dao.UserHBaseDao;
import com.oracle_hbase.model.User;


@WebServlet("/authentication")
public class VerifyLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private boolean verify(String password, User user) {
        if (user == null || !user.getPassword().equals(password)) return false;
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = null;
		//user = UserDao.getUserFromUsername(username);
        try {
			user = UserOracleDao.getUserFromUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (!verify(password, user)) {
            //redirect to login
            request.setAttribute("error", true);
            RequestDispatcher rd=request.getRequestDispatcher("/login");
            rd.forward(request, response);
            return;
        }
        //Create a log in session
        HttpSession session = request.getSession();
        session.setAttribute("username", user.getUsername());
        session.setAttribute("stt_oracle", 1000000);
        session.setAttribute("stt_hbase", 1000000);
        response.sendRedirect(request.getContextPath());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}
