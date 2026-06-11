
//      中本
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.MuttersDAO;

/**
 * Servlet implementation class EditMutterServlet
 */
@WebServlet("/EditMutterServlet")
public class EditMutterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMutterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	// 	doGet(request, response);
		 request.setCharacterEncoding("UTF-8");
		    int id = Integer.parseInt(request.getParameter("id"));
		    String text = request.getParameter("text");

		    MuttersDAO dao = new MuttersDAO();
		    dao.updateMutterText(id, text);

		    response.sendRedirect("Main");
		
		
	}

}
