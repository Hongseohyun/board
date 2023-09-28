package cs.dit.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 모든 요청 처리 :  ( "/" ) : 모든 클라이언트 요청이 처리가 된다.
// 특정 요청 처리 : /aaa, /bbb
// 확장자 url 패턴 : ("*.do") 이런식으로하면 grouping
// .jsp나 .jspx로 하면 바로 그파일로 이동됨 다른 서블릿이 관리하기 때문

@MultipartConfig(
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 50
)



@WebServlet(urlPatterns = ( "*.do" )) 
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String viewPage= null;
		
		String uri = request.getRequestURI(); //요청시 만들어진 request객체를 잡아서 볼 수 있음
//		System.out.println(uri);	//출력 : /board-student/list.do	문자열이기때문에 중요한 'list' 부분만 추출
//		
//		System.out.println(uri.lastIndexOf("/"));
//		System.out.println(uri.lastIndexOf(".do"));
		
		String com = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf(".do"));
		
//		System.out.println(com);
		
		
		
		if(com !=null && com.trim().equals("list")) { //비어있지않고, 빈문자열 잘라주면 더 나음
			BoardService service = new BListService();	//BListService 타입으로 선언해도되지만 BoardService로 하는게 나음 왜지?
			service.execute(request, response);
			//실행하고 리스트로 가기
			viewPage = "/WEB-INF/view/list.jsp";
		}else if(com !=null && com.trim().equals("insertForm")) {
			// 처리할거없음 보여주기만 하면됨
			viewPage = "/WEB-INF/view/insertForm.jsp";
		}else if(com !=null && com.trim().equals("insert")) {
			BoardService service = new BInsertService();	
			service.execute(request, response);
			viewPage = "/WEB-INF/view/list.do";
		}else if(com !=null && com.trim().equals("updateForm")) {
			BoardService service = new BSelectOneService();	
			service.execute(request, response);
			viewPage = "/WEB-INF/view/updateForm.jsp";
		}else if(com !=null && com.trim().equals("update")) {
			BoardService service = new BUpdateService();	
			service.execute(request, response);
			viewPage = "/WEB-INF/view/list.do";
		}else if(com !=null && com.trim().equals("delete")) {
			BoardService service = new BDeleteService();	
			service.execute(request, response);
		
			viewPage = "/WEB-INF/view/list.do";
		}else if (com != null && com.trim().equals("index")) {
			viewPage = "/WEB-INF/view/index.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
