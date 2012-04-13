package hk.edu.uic.doordie.server.controller.servlet;

import hk.edu.uic.doordie.server.controller.util.JSONPackager;
import hk.edu.uic.doordie.server.model.dao.UserDAO;
import hk.edu.uic.doordie.server.model.vo.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class Users
 */
public class GetUnknownUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUnknownUsers() {
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
		int myId = Integer.parseInt(request.getParameter("myId"));
		
		// for output json
		PrintWriter out = response.getWriter();
		
		UserDAO ud = new UserDAO();
		try {
			List<User> userList = ud.getUnknownUsers(myId);
			if(userList != null) {
				JSONPackager jp = new JSONPackager();
				JSONArray friends = jp.packageFriends(userList);
				
				out.write(friends.toString());
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
