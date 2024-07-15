package it.corso.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//localhost:4545/jee-02/login
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getAttribute("userLogin") != null)
			response.sendRedirect("user");
		else if (request.getSession().getAttribute("adminLogin") != null)
			response.sendRedirect("admin"); // se nell'attributo è presente una chiave adminLogin allora si reindirizza
											// all'area riservata amministratore
		else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recuperiamo i parametri e otteniamo esito della verifica
		String esitoLogin = ControlloLogin(request.getParameter("role"), request.getParameter("username"),
				request.getParameter("password"));

		// reindirizzamenti
		if (esitoLogin.startsWith("user")) {
			creaCookieLoginUtente(esitoLogin, response);
			response.sendRedirect("user"); // reindirizza l'utente alla pagina specificata
		} else if (esitoLogin.startsWith("admin")) {
			HttpSession session = request.getSession(); // ci mette a disposizione un oggetto con il quale interagire
														// per la sessione corrente
			session.setAttribute("adminLogin", esitoLogin); // salviamo sulla session l'attributo dell'amministratore
			response.sendRedirect("admin");
		} else
			response.sendRedirect("login?error"); // avremmo potuto usare anche il doGet ma con una
													// richiesta refreshamo 

	}

	// metodo per valutare le credenziali e comporre una stringa risultato
	private String ControlloLogin(String... dati) {
		if (dati[0].equals("u")) { // controllo login per utente
			if (dati[1].equals("user") && dati[2].equals("user")) {
				return "user@" + dati[1];
			} else {
				return "negato";
			}
		} else { // controllo login per amministratore
			if (dati[1].equals("admin") && dati[2].equals("admin"))
				return "admin@" + dati[1];
			else
				return "negato";
		}
	}

	// metodo per creare un coockie che gestisca il login utente
	private void creaCookieLoginUtente(String contenuto, HttpServletResponse response) {
		Cookie cookie = new Cookie("userLogin", contenuto);
		cookie.setMaxAge(3600); // imposto il tempo di durata del coockie
		cookie.setPath("/jee-02"); // setta il percorso del coockie
		response.addCookie(cookie);
	}
}
