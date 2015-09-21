/**This class has Employee as instance variable.
 * So it stores the state of it to do various actions to fetch and 
 * store data in database with the help of session factory.
 */

package main.java;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DatabaseManager {
	
	public DatabaseManager(){
		
	}
	
	/*Constructor to set an instance of employee
	 * so that it can be used for further actions 
	 * like adding data to database.
	 */
	public DatabaseManager(Employee employee){
		this();
		session = new HibernateUtilities().getSessionFactory().openSession();
		tx = session.beginTransaction();
		emp = employee;
		
		
	}
	
	/*Constructor to set an instance of employee and Id to be verified 
	 * so that this class instance is used mainly to
	 * verify authenticity of an user trying to log in.
	 */
	public DatabaseManager(int enteredId,Employee employee){
		this();
		session = new HibernateUtilities().getSessionFactory().openSession();
		tx = session.beginTransaction();
		idVer=enteredId;
		emp = employee;
	}
	
	//method to get id of employee
	public static int getId(){
		int id=0;
		id = emp.getId();
		tx.commit();
		session.close();
		return id;
	}
	
	//method to get name of employee
	public static String getName(){
		String name="";
		name = emp.getName();
		tx.commit();
		session.close();
		return name;
	}
	
	//method to get password of employee
	public String getPassword(){
		String password="";
		password = emp.getPassword();
		return password;
	}
	
	//method to get rating of employee.
	public int getRating(){
		int rat=0;
		rat = emp.getRating();
		session.close();
		return rat;
	}
	
	//Saves the data stored in 'emp' instance to the database.
	public void saveUserData(){
		
		session.save(emp);
		tx.commit();
		session.close();
	}
	
	public void setRating(int rat){
		tx = session.beginTransaction();
		emp.setRating(rat);
		session.update(emp);
		tx.commit();
		session.close();
	}
	
	/*This method verifies the user by returning boolean
	 * which can be used in 'if' statement to take flow of control.
	 */
	public boolean verifyUser(){
		boolean verified = false;
		if(session.get(Employee.class, idVer)!=null){
			emp=session.get(Employee.class, idVer);
			verified=true;
		}
		tx.commit();
		return verified;
	}
	
	/*This method returns the employee instance of a particular id
	 * which contains other details like 'rating' which can be easily
	 * obtained using getter method of Employee class
	 */
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
