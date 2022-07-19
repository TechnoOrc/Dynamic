package kr.co.ictedu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping ( value = "/emp" )//클래스의 경로
public class EmpController {

	private static final Logger logger = LoggerFactory.getLogger(EmpController.class);
	
	@Autowired // 자동으로 묶어준다.// 아래 EmpService service 클래스 로드(new)
	private EmpService service;
	
	@RequestMapping( value = "/detail", method = RequestMethod.GET )
	public String empDetail( String eno, Model model ) {
		EmpDTO dto = null;

		dto = service.empDetail( eno );
		
		model.addAttribute( "detail_dto",dto );
		return "emp_detail";//jsp 파일 이름. 
	}//empDetail
	
	
	@RequestMapping ( value = "/list", method = RequestMethod.GET )
	public String empList( Model model ) {// 메소드의 경로
		logger.info("여기가 실행 됩니다.");
		//EmpService service = new EmpService(); @Autowired로 인해 불 필요. 
		
		List<EmpDTO> list = null;
		
		list = service.empList();
		
		model.addAttribute("emp_dto_list", list);
		
		return "emp_list";//jsp 파일 이름.
	}//empList
	
}//class
