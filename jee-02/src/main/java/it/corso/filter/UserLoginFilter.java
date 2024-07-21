package it.corso.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "login", urlPatterns = {"/login","/user"}) //definiamo il pattern per il quale deve funzionare il filtro
public class UserLoginFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = -2647818367324253175L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;// castiamo a httpServletRequest
		Cookie[] cookies = req.getCookies(); // ci dichiariamo un array di oggetti cookie e li prendiamo dalla richiesta
		for(Cookie c: cookies)
			if(c.getName().equals("userLogin")) {
				req.setAttribute("userLogin", c.getValue()); //settiamo l'attributo della richiesta passando userLogin e il valore del cookie
				break;
			}
		//si lascia sempre come ultima istruzione
		chain.doFilter(request, response);
	}
}