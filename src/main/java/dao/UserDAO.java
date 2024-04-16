package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.User;

public class UserDAO {

	public boolean loginChecker(String emailOrUsername, String password) {
		// ottengo l'utemte tramite il metofo getUser usando una stringa
		User user = getUser(emailOrUsername);
		// faccio un check dei valori
		// 1= utente nullo
		// 2=password nulla
		// 3= check finale tra passowrd insertia da input e password presente in DB
		return user != null && user.getPassword() != null && user.getPassword().equals(password);
	}

	// Metodo per ottenere utente tramite emai o username tramite query sql
	public User getUser(String emailOrUsername) {
		// valori nulli per poi inizializzarli nel try e chiuderli in un finally (senza
		// questo si incanta l applicativo)
		Context ctx = null;
		Connection con = null;
		// creazione utente vuoto
		User user = new User();
		//inizio blocco try
		try {
			//connessione al DB tramite valori presenti in server.xml e context.xml
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
			con = ds.getConnection();

			//stringa sql conm un OR per fare la ricerca su alemno di una delle due colonne 
			String sqlQuery = "SELECT * FROM user WHERE email=? OR username=?";

			//inizio blocco try numero 2 per eseguire la query
			try (PreparedStatement stm = con.prepareStatement(sqlQuery)) {
				stm.setString(1, emailOrUsername);
				stm.setString(2, emailOrUsername);
				try (ResultSet rs = stm.executeQuery()) {
					//se la ricerca da esito positivo inserisce i valori nei set
					if (rs.next()) {
						user.setId(rs.getLong("id"));
						user.setUsername(rs.getString("username"));
						user.setPassword(rs.getString("password"));
						user.setEmail(rs.getString("email"));
					}
				}
			}

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally { //blocco finaly per la chiusura della connessione al DB, presa dall'esercizio precedente
			try {
				con.close();
				ctx.close();
			} catch (SQLException e) {
				System.out.println("Exception in closing DB resources");
			} catch (NamingException e) {
				System.out.println("Exception in closing Context");
			}

		}

		return user;
	}

	//creazione della lista di utenmti presenti in piattaforma
	public List<User> getUsersList() {
		//come prima creazione dei valori nulli per poi chiuderli in un finally
		Context ctx = null;
		Connection con = null;
		
		//creazione arraylist vuoto		
		List<User> usersList = new ArrayList<User>();
	

		//inizio blocco try
		try {

			//collegamento al DB
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
			con = ds.getConnection();

			Statement stm = con.createStatement();

			String sqlQuery = "SELECT * FROM user";
			
			//inzio secondo blocco try per esecuzione query
			try (ResultSet rs = stm.executeQuery(sqlQuery)) {

				//inzio del ciclo while per inserire finche presenti nell DB utenti all interno dell arraylist
				while (rs.next()) {

					User user = new User();
					user.setId(rs.getLong("id"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					usersList.add(user);
				}

			}

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally { //chiusura collegamento al DB 
			try {
				con.close();
				ctx.close();
			} catch (SQLException e) {
				System.out.println("Exception in closing DB resources");
			} catch (NamingException e) {
				System.out.println("Exception in closing Context");
			}

		}

		//ritorno della lista utenti 
		return usersList;
	}
}
