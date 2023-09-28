package cs.dit.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

public class BListService implements BoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. BoardDao를 생성
		BoardDao dao = new BoardDao();
		
		int count = dao.recordCount();  //전체 레코드의 개수
		int numberOfRecords = 10;		// 한번에 가져올 레코드의 개수
		int numberOfPages = 5;
		String page = request.getParameter("p");
		
		int p = 1;				// 현재 페이지 번호
		if(page != null && !page.equals("")) {
			p = Integer.parseInt(page);
		}
		
		int startNum = p-(p-1)%numberOfPages;									//한 페이지의 첫 게시글 번호 
		int lastNum = (int)Math.ceil((float)count/(float)numberOfRecords);		//전체 페이지 숫자
//		System.out.println("count/nor="+((float)count/(float)numberOfRecords));
//		System.out.println("count = "+ count);
//		System.out.println("numberOfRecords = "+ numberOfRecords);
		//2. dao 해당 메소드를 호출
		ArrayList<BoardDto> dtos = dao.list(p, numberOfRecords);

		//3. 호출 결과 처리
		request.setAttribute("dtos", dtos);
		request.setAttribute("p",p);
		request.setAttribute("startNum", startNum);
		request.setAttribute("lastNum", lastNum);
		request.setAttribute("numberOfPages", numberOfPages);
		
//		System.out.println("p="+p);
//		System.out.println("startNum="+startNum);
//		System.out.println("lastNum="+lastNum);
	}

}

