package hk.edu.uic.doordie.server.controller.servlet;

import hk.edu.uic.doordie.server.controller.util.JSONPackager;
import hk.edu.uic.doordie.server.model.dao.TodoDAO;
import hk.edu.uic.doordie.server.model.dao.UserDAO;
import hk.edu.uic.doordie.server.model.vo.Todo;
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

/**
 * Servlet implementation class My_Friends_Todos
 */
public class GetMyFriendsTodos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyFriendsTodos() {
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
		
		TodoDAO td = new TodoDAO();
		UserDAO ud = new UserDAO();
		try {
			List<Todo> todoList = td.getMyMonitoringTodos(myId);
			List<User> userList = new LinkedList<User>();
			if(todoList != null) {
				for(Todo todo:todoList) {
					User user = ud.getUser(todo.getUid());
					userList.add(user);
				}
				
				JSONPackager jp = new JSONPackager();
				JSONArray todos = jp.packageMyFriendsTodos(todoList, userList);
				
				out.write(todos.toString());
				out.flush();
			} else {
				out.write("false");
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
