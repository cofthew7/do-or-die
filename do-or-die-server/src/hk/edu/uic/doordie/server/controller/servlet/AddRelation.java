package hk.edu.uic.doordie.server.controller.servlet;


import hk.edu.uic.doordie.server.model.dao.RelationDAO;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AddRelation
 */
public class AddRelation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRelation() {
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
		int friendId = Integer.parseInt(request.getParameter("friendId"));

		// for output json
		PrintWriter out = response.getWriter();
		
		RelationDAO rd = new RelationDAO();
		try {
			boolean isSuccess = rd.addRelation(myId, friendId);
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
