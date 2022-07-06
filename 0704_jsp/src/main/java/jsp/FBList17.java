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

@WebServlet("/FBList17")
public class FBList17 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private FB17DAO dao;
    public FBList17() {
        super();
        dao = new FB17DAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		if(cmd == null) cmd = "list";
		
		if( cmd.equals("detail") ) {
			
			String no = request.getParameter("no");
			BoardDTO dto = null;
			
			try {
				dto = dao.detail(no);
			} catch (SQLException e) {
				e.printStackTrace();
				// 나중에 코딩예정.
				return;
			}
			request.setAttribute("free_board_detail", dto);
			RequestDispatcher disp = request.getRequestDispatcher("./jsp/17fb_detail.jsp");
			disp.forward(request, response);
			
		} else if( cmd.equals("list") ) {//list
			
			ArrayList<BoardDTO> list = null;
			
			try {
				list = dao.list();
			} catch (SQLException e) {
				e.printStackTrace();
				
				SuccessMsgDTO errDTO = new SuccessMsgDTO();
				errDTO.setMessage("DBMS 오류");
				errDTO.setUrl("./main.jsp");
				errDTO.setUrlName("메인으로 바로가기");
				
				request.getSession().setAttribute("msg_dto", errDTO);
				response.sendRedirect("./jsp/fail_page.jsp");
				
				return ;
			}//catch
			
			request.setAttribute("free_board_list", list);
			RequestDispatcher disp = request.getRequestDispatcher("./jsp/17fb_list.jsp");
			disp.forward(request, response);
			
		}
		
	}//doGet

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 입력 DB 한글 깨짐으로 인해 입력data(request)를 setCharacterEncoding 한다.
		//0. cmd에 따라서 분기한다.
		String cmd = request.getParameter("cmd");
		if(cmd.equals("write")) {
			//1. BoardDTO에 request.getParameter - 3개 담는다.
			BoardDTO dto = new BoardDTO();
			dto.setBtitle( request.getParameter("title"));
			dto.setBwriter( request.getParameter("writer"));
			dto.setBcnts( request.getParameter("cnts"));
			//2. int successCount = dao.write(dto);
			int successCount = 0;
			//3. exception 처리 -> 메세지 처리
			try {
				successCount = dao.write(dto);
			} catch (SQLException e) {
				e.printStackTrace();
				SuccessMsgDTO msgDTO = new SuccessMsgDTO();
				msgDTO.setMessage("DBMS 오류");
				msgDTO.setUrl("./main.jsp");
				msgDTO.setUrlName("메인을 바로 가기");
				request.getSession().setAttribute("msg_dto", msgDTO );
				response.sendRedirect("./jsp/fail_page.jsp");
				return;// exception 발생시 프로그램 멈춤
			
			
			}
			if (successCount == 0 ) {
				//4. successCount == 0 처리 -> 메세지 처리
				SuccessMsgDTO msgDTO = new SuccessMsgDTO();
				msgDTO.setMessage("게시판 글쓰기가 실패 하였습니다.");
				msgDTO.setUrl("../FBList17");//실행시점이 jsp 폴더 내부
				msgDTO.setUrlName("게시판 목록으로 바로가기");
				request.getSession().setAttribute("msg_dto", msgDTO );
				response.sendRedirect("./jsp/fail_page.jsp");
				
			} else if( successCount == 1) {
				//5. successCount == 1 처리 -> list 호출
				response.sendRedirect("./FBList17");//실행 시점이 jsp 패키지 내부
				
			}
		} else if( cmd.equals("update") ) {
			
		}
		
	}//doPost

}
/*
drop sequence mno_seq;
create sequence mno_seq
start with 1
increment by 1
maxvalue 99999
nocycle;

drop table freeboard;
PURGE RECYCLEBIN;
create table freeboard(
bno number(5,0) primary key
, btitle varchar2(150)
, bwriter varchar2(60)
, bcnts varchar2(1500)
, bdate date
);
*/