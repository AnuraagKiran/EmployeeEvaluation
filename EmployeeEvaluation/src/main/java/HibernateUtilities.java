package main.java;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtilities {

	private SessionFactory sessionFactory=null;
	
	

	public SessionFactory getSessionFactory() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();

		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println("Problem Creating SessionFactory!");
		}
		return sessionFactory;
	}

}
