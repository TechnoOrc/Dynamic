package login.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.dao.LoginDAO2;
import login.dto.MemberDTO2;

@WebServlet("/Login2")
public class Login2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private LoginDAO2 dao;  
    public Login2() {
        super();
        dao = new LoginDAO2();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}//doget

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	MemberDTO2 dto = new MemberDTO2();
	dto.setMid( request.getParameter("mid") );
	dto.setMpwd( request.getParameter("mpwd") );
	
	int loginYN = 0;
	RequestDispatcher disp = request.getRequestDispatcher("./ErrMsg2");
	
	try {
		loginYN = dao.loginCheck(dto);
	} catch ( SQLException e) {
		e.printStackTrace();
		request.setAttribute("error_message", "DMS 오류 <br><br> 잠시 후 다시 시도해 주세요.");
		disp.forward(request, response);
		return;
	}
	
	if ( loginYN == 0 ) {
		request.setAttribute("error_message","로그인 실패<br><br> 아이디 또는 패스워드를 확인 하세요.");
		disp.forward(request, response);
	} else if( loginYN == 1 ) {
		HttpSession session = request.getSession();
		session.setAttribute("lid", dto.getMid());
		response.sendRedirect("./LoginComplete2");
	}
	
	}//doGet

}
