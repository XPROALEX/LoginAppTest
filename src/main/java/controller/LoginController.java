package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ottengo dalla jsp i parametri necessari al login
		String emailOrUsername = request.getParameter("emailOrUsername");
		String password = request.getParameter("password");

		// creazione oggetto userDAO per poter sfruttare i metodi di check
		UserDAO userDAO = new UserDAO();

		// verifica presenza utente in piattaforma (verifica su username o email cambia
		// poco)
		if ((userDAO.getUser(emailOrUsername).getUsername() == null)) {
			// imposto il messaggio di errore da visualizzare in pagina
			request.setAttribute("errorMessage", "USER NOT FOUND");

			// reindirizzamento alla pagina login.jsp in caso di errore con consegente invio
			// di variabili
			request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
		}

		// check della password legata all utente
		if (!userDAO.loginChecker(emailOrUsername, password)) {
			// imposto il messaggio di errore da visualizzare in pagina
			request.setAttribute("errorMessage", "WRONG CREDENTIALS");

			// reindirizzamento alla pagina login.jsp in caso di errore con consegente invio
			// di variabili
			request.getRequestDispatcher("jsp/login.jsp").forward(request, response);

		}

		// verifica finale se tutto é corretto
		if (userDAO.loginChecker(emailOrUsername, password)) {
			// imposto le variabili da utilizzare nelle jsp necessarie per completare
			// l'esercizio
			request.setAttribute("username", userDAO.getUser(emailOrUsername).getUsername());
			request.setAttribute("usersList", userDAO.getUsersList());

			// reindirizzamento alla pagina welcome ed invio di variabili necessarie al
			// completamento dell'esercizio
			request.getRequestDispatcher("jsp/welcome.jsp").forward(request, response);

		}

	}

}
