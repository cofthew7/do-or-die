package hk.edu.uic.doordie.server.controller.servlet;

import hk.edu.uic.doordie.server.controller.util.JSONPackager;
import hk.edu.uic.doordie.server.model.dao.TodoDAO;
import hk.edu.uic.doordie.server.model.dao.UserDAO;
import hk.edu.uic.doordie.server.model.vo.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddTodo
 */
public class AddTodo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTodo() {
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
		String name = request.getParameter("name");
		Timestamp deadline = Timestamp.valueOf(request.getParameter("deadline"));
		int isMonitored = Integer.parseInt(request.getParameter("isMonitored"));
		int isFinished = Integer.parseInt(request.getParameter("isFinished"));
		int uid = Integer.parseInt(request.getParameter("uid"));

		// for output json
		PrintWriter out = response.getWriter();
		
		TodoDAO td = new TodoDAO();
		try {
			int key = td.addTodo(name, deadline, isMonitored, isFinished, uid);
			if(key > 0) {
				out.write(String.valueOf(key));
				out.flush();
			} else {
				out.println("false");
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
