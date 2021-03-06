package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/RE14")
public class RE14 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RE14DAO dao;
    
	public RE14() {
        super();
       dao = new RE14DAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}//doGet

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDTO dto = new MemberDTO();
		dto.setMid( request.getParameter("mid") );
		dto.setMpwd( request.getParameter("mpwd") );
		dto.setTel1( request.getParameter("tel1") );
		dto.setTel2( request.getParameter("tel2") );
		dto.setTel3( request.getParameter("tel3") );
		dto.setEmail1( request.getParameter("email1") );
		dto.setEmail2( request.getParameter("email2") );
		
		int successCount = dao.insertMember(dto);
		//dbms의 insert / delete / update는 성공 건수를 return 함.
		
		response.setCharacterEncoding("euc-kr");//response.getWriter() 위에 위치 해야한다.
		PrintWriter out = response.getWriter();
		
		if(successCount > 0) {
			out.print("<h1>회원 가입 성공</h1>");
			
		} else {
			out.print("<h1>회원 가입 실패</h1>");
			
		}
		out.close();
		
	}//doPost

}//class
