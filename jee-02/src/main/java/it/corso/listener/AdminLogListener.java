package it.corso.listener;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@WebListener
public class AdminLogListener implements HttpSessionAttributeListener, ServletContextListener { //deve implementare anche l'interfaccia ServletContextListener
	
	private static final Logger LOGGER = Logger.getLogger(AdminLogListener.class.getName()); // ci mette a disposizione lo strumento per creare il log
	
	//metodo per accesso al contesto web dell'applicazione
	@Override
	public void contextInitialized(ServletContextEvent sce)  { //quando l'attributo viene inizializzato
		try {
			ServletContext context = sce.getServletContext(); // riferimento al nostro path di lavoro
			File logDirectory = new File(context.getRealPath("/") + "WEB-INF/logs"); //recuperiamo il path con la funzione getRealPath alla quale passiamo / + il percorso dove mettere il file
			if(!logDirectory.exists()) //se la directory non esiste
				logDirectory.mkdir(); // andiamo a crearla
			FileHandler fileHandler = new FileHandler(logDirectory + "/admin_activities.log", true);//il riferimento punterà alla directory più il nostro file admin activities il secondo parametro boolean attiva l'append nel file
			fileHandler.setLevel(Level.ALL); // settiamo di prendere tutto (il log ha vari livelli)
			fileHandler.setFormatter(new SimpleFormatter()); // passiamo un tipo di formattazione standard
			LOGGER.addHandler(fileHandler);
		} catch (Exception e) {
			LOGGER.severe("impossibile creare il file: " + e.getMessage());
		}
	}
	
    public void attributeReplaced(HttpSessionBindingEvent se)  {  //attributo viene rimpiazzato
         // TODO Auto-generated method stub
    }

    
	public void attributeRemoved(HttpSessionBindingEvent se)  { //attributo viene cancellato 
        if(se.getName().equals("adminLogin")) // riusciamo ad ottenere il nome dell'attributo
        	LOGGER.info("logout admin: " +se.getValue()); // con getValue riusciamo a stampare il valore dell'attributo e a capire chi si è sloggato
    }

    public void attributeAdded(HttpSessionBindingEvent se)  {  //attributo viene aggiunto
    	if(se.getName().equals("adminLogin")) // riusciamo ad ottenere il nome dell'attributo
        	LOGGER.info("login admin: " +se.getValue()); // con getValue riusciamo a stampare il valore dell'attributo e a capire chi si è loggato
    }
	
}
