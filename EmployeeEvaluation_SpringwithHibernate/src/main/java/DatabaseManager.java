/**This class has Employee and HibernateTemplate as instance vriables.
 * So it stores the state of them to do various actions to fetch and 
 * store data in database.
 */

package main.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

//@Service("dbManager")
public class DatabaseManager {
	
	public DatabaseManager(){
		
	}
	
	//method to get id of employee
	public static int getId(){
		int id=0; 
		id = emp.getId();
		return id;
	}
	
	//method to get name of employee
	public static String getName(){
		String name="";
		name = emp.getName();
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
		return rat;
	}
	
	//Saves the data stored in 'emp' instance to the database.
	public void saveUserData(){
		template.save(emp);
	}
	
	//method to set rating of an employee
	public void setRating(int rat){
		emp.setRating(rat);
		template.update(emp);
	}
	
	/*This method sets employee as the instance variable
	 * which can be used for various actions like getting values from
	 * the instance.
	 */
	public void setEmployee(Employee employee){
		emp = employee;
	}
	
	/*This method assignes the id input by user to idVer
	 * so that the instance variable idVer can be used in
	 * verifyUser() method.
	 */
	public void setIdToBeVerified(int id){
		idVer = id;
	}
	
	/*This method verifies the user by returning boolean
	 * which can be used in 'if' statement to take flow of control.
	 */
	public boolean verifyUser(){
		boolean verified = false;
		if((emp =(Employee) template.get(Employee.class, idVer))!=null){
			
			verified=true;
		}
		return verified;
	}
	
	/*This method returns the employee instance of a particular id
	 * which contains other details like 'rating' which can be easily
	 * obtained using getter method of Employee class
	 */
	public Employee getRatingOfEmployee(int id){
		if(template.get(Employee.class, id)!=null){
			emp=template.get(Employee.class, id);
			return emp;
		}
		else{
			return null;
		}
	}
	
	/*The HibernateTemplate class is responsible for interacting
	 * with the database. So This class should carry an instance
	 * variable called 'template', which acts like 'session',
	 * to fetch and input data into database.
	 */
	public void setTemplate(HibernateTemplate temp) {  
	    template = temp;
	}  
	//method to delete employee  
	public void deleteEmployee(Employee e){  
	    template.delete(e);  
	}  
	//method to return one employee of given id  
	public Employee getById(int id){  
	    Employee e=(Employee)template.get(Employee.class,id);  
	    return e;  
	}  
	//method to return all employees  
	public List<Employee> getEmployees(){  
	    List<Employee> list=new ArrayList<Employee>();  
	    list=template.loadAll(Employee.class);  
	    return list;  
	}  
	
	private static Employee emp;
	private static int idVer;
	private static HibernateTemplate template;
}
