package hk.edu.uic.doordie.server.controller.servlet;

import hk.edu.uic.doordie.server.controller.util.JSONPackager;
import hk.edu.uic.doordie.server.model.dao.CommentDAO;
import hk.edu.uic.doordie.server.model.dao.UserDAO;
import hk.edu.uic.doordie.server.model.vo.Comment;
import hk.edu.uic.doordie.server.model.vo.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Comments
 */
public class GetComments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetComments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		// 获取请求参数
		int todoId = Integer.parseInt(request.getParameter("todoId"));
		
		// for output json
		PrintWriter out = response.getWriter();
		
		CommentDAO cd = new CommentDAO();
		UserDAO ud = new UserDAO();
		try {
			List<Comment> commentList = cd.getComments(todoId);
			List<User> userList = new LinkedList<User>();
			if(commentList != null) {
				for(Comment comment:commentList) {
					User user = ud.getUser(comment.getUid());
					userList.add(user);
				}
				
				JSONPackager jp = new JSONPackager();
				JSONArray comments = jp.packageComments(commentList, userList);
				
				out.write(comments.toString());
				out.flush();
			} else {
				out.write("null");
				out.flush();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
