package hk.edu.uic.doordie.server.controller.servlet;

import hk.edu.uic.doordie.server.model.dao.MonitoringDAO;
import hk.edu.uic.doordie.server.model.dao.RelationDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddMonitor
 */
public class AddMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMonitor() {
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
		int uid = Integer.parseInt(request.getParameter("uid"));

		// for output json
		PrintWriter out = response.getWriter();
		
		MonitoringDAO md = new MonitoringDAO();
		try {
			boolean isSuccess = md.addMonitor(todoId, uid);
			if(isSuccess) {
				out.write("true");
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
