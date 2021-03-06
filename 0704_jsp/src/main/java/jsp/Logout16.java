package jsp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout16")
public class Logout16 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Logout16() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.getSession().invalidate();
		response.sendRedirect("./jsp/main.jsp");
		
/*		HttpSession session = request.getSession();
		session.invalidate();//모든 세션 삭제 //log out
		System.out.println("session invalidate complete");
		response.sendRedirect("http://localhost:8080/0704_jsp/main.jsp");
 */		
		
	}//doGet

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}