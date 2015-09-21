package main.java;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

public class DatabaseManager {
	
	public DatabaseManager(){
		
	}
	
	public DatabaseManager(Employee employee){
		this();
		session = new HibernateUtilities().getSessionFactory().openSession();
		tx = session.beginTransaction();
		emp = employee;
		
		
	}
	
	public DatabaseManager(int enteredId,Employee employee){
		this();
		session = new HibernateUtilities().getSessionFactory().openSession();
		tx = session.beginTransaction();
		idVer=enteredId;
		emp = employee;
	}
	
	public static int getId(){
		int id=0;
		//SQLQuery query = session.createSQLQuery("select "+id+"from employees") ;
		
		id = emp.getId();

		tx.commit();
		
		session.close();
		//HibernateUtilities.getSessionFactory().close();
		return id;
	}
	
	public static String getName(){
		String name="";
		//SQLQuery query = session.createSQLQuery("select "+id+"from employees") ;
		
		name = emp.getName();

		tx.commit();
		
		session.close();
		//HibernateUtilities.getSessionFactory().close();
		return name;
	}
	
	public String getPassword(){
		String password="";
		//SQLQuery query = session.createSQLQuery("select "+id+"from employees") ;
		
		password = emp.getPassword();
		
		return password;
	}
	
	public int getRating(){
		int rat=0;
		//SQLQuery query = session.createSQLQuery("select "+id+"from employees") ;
		
		rat = emp.getRating();

		
		session.close();
		//HibernateUtilities.getSessionFactory().close();
		return rat;
	}
	
	public void saveUserData(){
		
		session.save(emp);
		tx.commit();
		session.close();
		//HibernateUtilities.getSessionFactory().close();
		
	}
	
	public void setRating(int rat){
		tx = session.beginTransaction();
		emp.setRating(rat);
		session.update(emp);
		tx.commit();
		session.close();
		//HibernateUtilities.getSessionFactory().close();
	}
	
	public boolean verifyUser(){
		boolean verified = false;
		/*SQLQuery query = session.createSQLQuery("select * from employees") ;
		List<Employee> allIds = query.list();
		for(int i=0;i<allIds.size();i++){
			emp =  allIds.get(i);
			if(idVer==emp.getId()){
				verified = true;
			}
		}*/
		if(session.get(Employee.class, idVer)!=null){
			emp=session.get(Employee.class, idVer);
			verified=true;
		}
		tx.commit();
		return verified;
	}
	
	public Employee getRatingOfEmployee(int id){
		session.beginTransaction();
		if(session.get(Employee.class, id)!=null){
			emp=session.get(Employee.class, id);
			tx.commit();
			return emp;
		}
		else{
			return null;
		}
	}

	private static Session session;
	private static Transaction tx;
	private static Employee emp;
	private static int idVer;
}
