package lib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionFactoryUtil {
	
	
	private static void checkCon() {
		// Check Master Connection
		String dbUrl = "jdbc:mysql://localhost:3306/cps";
		String dbClass = "com.mysql.jdbc.Driver";
		String query = "Select distinct(table_name) from INFORMATION_SCHEMA.TABLES";
		String username = "root";
		String password = "";
		try {

			Class.forName(dbClass);
			Connection connection = DriverManager.getConnection(dbUrl,username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				// If Slave is not working select master again.
				System.out.println("Master Config File Chosen By Hibernate ");
				Configuration cfg = new Configuration().configure("lib/hibernateMaster.cfg.xml");
				sesRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
				sesFactory = cfg.buildSessionFactory(sesRegistry);
			}
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// IF master is not working
			System.out.println("Slave Config File Chosen By Hibernate");
			Configuration cfg = new Configuration().configure("lib/hibernateSlave.cfg.xml");
			sesRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
			sesFactory = cfg.buildSessionFactory(sesRegistry);
		}
	}

	
	private static SessionFactory sesFactory;
	private static ServiceRegistry sesRegistry;
	
	static{
		try{
			//This is the initial system database check. Select the master database to this
			Configuration cfg= new Configuration().configure("lib/hibernateMaster.cfg.xml");
			sesRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
			sesFactory=cfg.buildSessionFactory(sesRegistry);
			
			try{
				Session session = SessionFactoryUtil.getSessionFactory().openSession();
				Transaction tx = session.beginTransaction();
				System.out.println("Connected to Master Database Server");
			}
			catch(Throwable ex){
				cfg= new Configuration().configure("lib/hibernateSlave.cfg.xml");
				sesRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
				sesFactory=cfg.buildSessionFactory(sesRegistry);
				System.out.println("Connected to Slave Database Server");
			}
		}
		catch(Throwable ex){
			System.out.println("Master & Slave Database Error.");
			System.err.println("Initial SessionFactory Creation Failed"+ex);
			throw new ExceptionInInitializerError(ex);
		}
	}	
	
	public static SessionFactory getSessionFactory() {
		checkCon();
	    return sesFactory;
	}
	
}
