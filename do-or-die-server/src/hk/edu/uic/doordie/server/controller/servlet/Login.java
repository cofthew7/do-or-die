package hk.edu.uic.doordie.server.controller.servlet;

import hk.edu.uic.doordie.server.controller.util.JSONPackager;
import hk.edu.uic.doordie.server.model.dao.TodoDAO;
import hk.edu.uic.doordie.server.model.dao.UserDAO;
import hk.edu.uic.doordie.server.model.vo.Todo;
import hk.edu.uic.doordie.server.model.vo.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// for output json
		PrintWriter out = response.getWriter();
		
		// verify account
		UserDAO ud = new UserDAO();
		try {
			User user = ud.login(email, password);
			if(user != null) {
				// get my todos
				TodoDAO td = new TodoDAO();
				List<Todo> todoList = td.getMyTodos(user.getId());
				
				// packing json
				JSONPackager jp = new JSONPackager();
				JSONObject myInfoAndTodos = jp.packageMyInfoAndTodos(user, todoList);
				
				out.write(myInfoAndTodos.toString());
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
