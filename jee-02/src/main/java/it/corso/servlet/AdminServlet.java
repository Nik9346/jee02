package it.corso.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("out") != null)
		{
			request.getSession().invalidate(); // distrugge la sessione e gli attribbuti della sessione
			request.getSession().removeAttribute("adminLogin"); // mantiene la sessione attiva ma rimuove solo lo specifico attributo
			response.sendRedirect("/jee-02");
 			return; //per interrompere la logica del metodo facciamo un return sul metodo void
		}
		if(request.getSession().getAttribute("adminLogin") == null) //se nella richiesta non è presente l'attributo, allora reindirizziamo l'utente al login (se vuole forzare accesso dalla barra di navigazione)
			response.sendRedirect("login");
		else {			
			request.getServletContext().getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
