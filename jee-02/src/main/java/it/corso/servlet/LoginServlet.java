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
		else if (request
				.getSession()
				.getAttribute("adminLogin") != null)
			response.sendRedirect("admin"); // se nell'attributo è presente una chiave adminLogin allora si reindirizza
											// all'area riservata amministratore
		else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recuperiamo i parametri mediante l'oggetto HttpServlet Request e otteniamo esito della verifica
		String esitoLogin = ControlloLogin(
				request.getParameter("role"), //prendiamo il ruolo quindi o (u-a)
				request.getParameter("username"), //prendiamo username
				request.getParameter("password"));//prendiamo password

		// reindirizzamenti
		if (esitoLogin.startsWith("user")) { //se l'esito del controllo, la stringa ritornata inizia per user
			creaCookieLoginUtente(esitoLogin, response);
			response.sendRedirect("user"); // reindirizza l'utente alla pagina specificata
		} else if (esitoLogin.startsWith("admin")) { //se l'esito del controllo, la stringa ritornata inizia per admin
			HttpSession session = request.getSession(); // ci mette a disposizione un oggetto con il quale interagire
														// per la sessione corrente, la session a differenza dei cookie ci permette di salvare anche cose diverse da Stringhe
			session.setAttribute("adminLogin", esitoLogin); // salviamo sulla session l'attributo dell'amministratore
			response.sendRedirect("admin");
		} else
			response.sendRedirect("login?error"); // avremmo potuto usare anche il doGet ma con una
													// richiesta refreshamo 

	}

	// metodo per valutare le credenziali e comporre una stringa risultato
	private String ControlloLogin(String... dati) {//gli facciamo accettare un array di stringhe contentente i parametri in ingresso del form
		if (dati[0].equals("u")) { // controllo login per utente
			if (dati[1].equals("user") && dati[2].equals("user")) { //se dati 1 è ugule alla stringa user e dati 2 è uguale alla stringa user
				return "user@" + dati[1]; //ritorno user@user
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
	private void creaCookieLoginUtente(String contenuto, HttpServletResponse response) { //contenuto che viene archiviato può essere solo una stringa HttpServletResponse permette di passare il cookie
		Cookie cookie = new Cookie("userLogin", contenuto); //jakarta.servelet.http
		cookie.setMaxAge(3600); // imposto il tempo di durata del coockie
		cookie.setPath("/jee-02"); // setta il percorso del coockie
		response.addCookie(cookie);
	}
}
