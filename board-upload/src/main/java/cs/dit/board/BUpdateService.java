package cs.dit.board;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class BUpdateService implements BoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("utf-8");

	    int bcode = Integer.parseInt(request.getParameter("bcode"));
	    String subject = request.getParameter("subject");
	    String content = request.getParameter("content");
	    String writer = request.getParameter("writer");
	    Date regDate = Date.valueOf(request.getParameter("regDate"));

	    String filename = request.getParameter("filename");
	    String dir = null;

	    String contentType = request.getContentType();
	    if (contentType != null && contentType.startsWith("multipart/")) {
	    	dir = request.getServletContext().getRealPath("/uploadfiles");
	    }
	    File f = new File(dir);
	    if (!f.exists()) {
	    	f.mkdir();
	    }

	    Collection<Part> parts = request.getParts();

	    for (Part p : parts) {
	    	if (p.getHeader("Content-Disposition").contains("filename=")) {
	    		System.out.println("p:"+p);
	    		if (p.getSize() > 0) {
	    			filename = p.getSubmittedFileName();
	    			String filePath = dir + File.separator + filename;
	    			p.write(filePath);
	    			p.delete();
	    		}
	    	}
	    }

	    // BoardDto에 업데이트할 게시물 정보와 파일 이름 설정
	    BoardDto dto = new BoardDto(bcode, subject, content, writer, null, filename);
	    BoardDao dao = new BoardDao();
	    dao.update(dto);
	}
}
