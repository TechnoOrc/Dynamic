package jsp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RE10")
public class RE10 extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	private RE10DAO dao;
    public RE10() {
        super();
        dao = new RE10DAO();
    }//constructor

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. request.getParameter 사용자가 보낸 이름을 받는다.
		String enm = request.getParameter("ename");
		//2. DAO를 통해 사용자가 보낸 이름에 적합한 결과를 받는다.
		ArrayList<EmpDTO> list = null;
		try {
			list = dao.nameLikeSearch(enm);
		} catch ( SQLException e) {
			e.printStackTrace();
			//3. 에러가 있다면 사용자에게 에러를 공지한다.
			//3-1. 세션에 에러 메세지를 입력.
			request.getSession().setAttribute("err_msg", "DBMS 오류 - 잠시 후 다시 시도해 주세요.");
			//3-2. 에러 페이지를 호출. -> 에러페이지에서 에러 메세지를 출력한다.
			response.sendRedirect("./jsp/10RE_err_page.jsp");
			return;//프로그램 종료.
		}
		//4. 에러 없이 결과가 나왔다면 사용자에게 보낸다.
		request.setAttribute("empNameLikeSearchResult", list);
		RequestDispatcher disp 
			= request.getRequestDispatcher("./jsp/10RE_result.jsp");
		disp.forward(request, response);
	}//doGet

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}//class
