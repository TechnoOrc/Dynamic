package kr.co.ictedu.board.member;

import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.ictedu.util.dto.SearchDTO;

@Controller
@RequestMapping( value = "/board/member" )
public class MemberBoardController {

	private final static Logger logger = LoggerFactory.getLogger(MemberBoardController.class);

	@Autowired
	private MemberBoardService service;

	
	@RequestMapping( value = "/list", method = RequestMethod.GET )
	public String list( SearchDTO dto, String userWantPage, Model model ) {
		//logger.info(dto.toString());
		if( userWantPage == null || userWantPage.equals("") ) userWantPage = "1";//if
		int totalCount = 0, startPageNum = 1, endPageNum = 10, lastPageNum = 1;
		totalCount = service.searchListCount( dto );
		if( totalCount > 10 ) {
			lastPageNum = ( totalCount / 10 ) + ( totalCount % 10 > 0 ? 1 : 0);
		}//if
		if( userWantPage.length() >= 2 ) {
			String frontNum = userWantPage.substring(0, userWantPage.length()-1);//125 -> 12
			startPageNum = Integer.parseInt(frontNum) * 10 + 1;//121
			endPageNum = ( Integer.parseInt(frontNum) + 1 ) * 10;//130
			
			String backNum = userWantPage.substring( userWantPage.length()-1, userWantPage.length() );//120
			if( backNum.equals("0") ) {
				startPageNum = startPageNum - 10;//111 
				endPageNum = endPageNum - 10;//120
			}//if
		}//if
		if( endPageNum > lastPageNum ) endPageNum = lastPageNum;//if
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("userWantPage", userWantPage);
		model.addAttribute("lastPageNum", lastPageNum);

		dto.setLimitNum( ( Integer.parseInt(userWantPage) - 1 ) * 10 );
		List<MemberBoardDTO> list = null;
		list = service.searchList( dto );
		//logger.info(list.toString());
		model.addAttribute("list", list);
		model.addAttribute("search_dto", dto);
		return "/board/member/list";//jsp file name
	}//list

	
	@RequestMapping( value = "/update", method = RequestMethod.POST )
	public void update( MemberBoardDTO dto, PrintWriter out ) {
		//dbms??? ???????????? update : FreeBoardService.update(dto) -> FreeBoardDAO.update(dto)
		//						-> freeboard-mapper.xml(namespace : FreeBoardMapper.update)
		int successCount = 0;
		//logger.info(dto.toString());
		successCount = service.update(dto);
		out.print(successCount);
		out.close();
	}//update
	
	@RequestMapping( value = "/update_form", method = RequestMethod.GET )
	public String updateForm( String board_no, Model model ) {
		MemberBoardDTO dto = null;
		dto = service.detail(board_no);
		model.addAttribute("detail_dto", dto);
		//logger.info(dto.toString());
		return "/board/member/update_form";//jsp file name
	}//updateForm
	
	
	
	
	@RequestMapping( value = "/delete", method = RequestMethod.GET )
	public void delete( MemberBoardDTO dto, PrintWriter out) {
		//dbms?????? ????????? ????????? delete : FreeBoardService.delete(board_no) -> FreeBoardDAO.delete(board_no)
		//						-> freeboard-mapper.xml(namespace : FreeBoardMapper.delete)
		System.out.println("delete");
		//logger.info(dto.toString());
		int successCount = 0;
		successCount = service.delete( dto );
		out.print(successCount);
		out.close();
	}//delete
	
	
	@RequestMapping( value = "/detail", method = RequestMethod.GET )
	public String detail( String board_no, Model model ) {
		//dbms?????? ????????? ????????? select : FreeBoardService.detail(board_no) -> FreeBoardDAO.detail(board_no)
		//						-> freeboard-mapper.xml(namespace : FreeBoardMapper.detail)
		MemberBoardDTO dto = service.detail(board_no);
		logger.info(dto.toString());
		model.addAttribute("detail_dto", dto);
		return "/board/member/detail";//jsp file name
	}//detail
	
	
	@RequestMapping( value = "/write", method = RequestMethod.POST )
	public void write( MemberBoardDTO dto, PrintWriter out ) {
		//dbms??? ???????????? insert : FreeBoardService.write(dto) -> FreeBoardDAO.write(dto)
		//						-> freeboard-mapper.xml(namespace : FreeBoardMapper.write)
		//logger.info(dto.toString());
		int successCount = 0;
		successCount = service.write(dto);
		out.print(successCount);
		out.close();
	}//write

	@RequestMapping( value = "/write_form", method = RequestMethod.GET )
	public String writeForm() {
		return "/board/member/write_form";//jsp ?????? ??????
	}//writeForm
	
	
}//class

/*
DROP TABLE `memberboard`;
CREATE TABLE `memberboard` (
  `board_no` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) DEFAULT NULL,
  `mno` int DEFAULT NULL,
  `contents` varchar(1500) DEFAULT NULL,
  `view_cnt` int DEFAULT NULL,
  `write_date` datetime DEFAULT NULL,
  PRIMARY KEY (`board_no`)
);
*/
