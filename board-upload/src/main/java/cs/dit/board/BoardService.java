package cs.dit.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardService {
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	//파라미터에 request, response 객체를 다 끌고와야함, 데이터를 유지할려고
	//정의만 한거라서 body없음
	
}


