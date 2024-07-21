package it.corso.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("out") != null) // se nella richiesta che arriva c'è un parametro con scritto out (user?out)
		{
			logoutUtente(request, response);
			response.sendRedirect("/jee-02");
			return; // dopo fatto il redirect per evitare di passare agli altri passaggi if else invochiamo il return vuoto per interrompere la logica del metodo
		}
		if(request.getAttribute("userLogin") == null) //se non esiste nessun cookie(userLogin == null) e l'utente sta cercando di forzare l'entrata, reindirizziamo al form login
			response.sendRedirect("login");
		else {			
			request.getServletContext().getRequestDispatcher("/WEB-INF/view/user.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//metodo per gestire la cancellazione del cookie
	private void logoutUtente(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies(); // otteniamo tutti i cookie associati alla richiesta
		for(Cookie c: cookies) //iteriamo su tutti i cookie per trovare quello giusto
			if(c.getName().equals("userLogin"))
			{
				c.setMaxAge(0); // gli passiamo una scadenza a 0 secondi
				c.setPath("/jee-02");
				response.addCookie(c); //sovrascrivo il cookie esistente con i stessi valori ma con una durata minima di 0 secondi
				break;
			}
	}

}
