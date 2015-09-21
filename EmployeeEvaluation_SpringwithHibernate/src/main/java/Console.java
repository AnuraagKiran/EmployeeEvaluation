/**This class runs the application on the console which interacts 
 * with other beans-DatabaseManager and Employee using spring framework.
 */


package main.java;

import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

public class Console {
	
private static Employee employee;
private static DatabaseManager dbManager;
private static ApplicationContext appContext;
private static HibernateTemplate temp;
private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args){
		
		System.gc();//invoking garbage cleaner
		
		//creating spring applicationContext
		appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		dbManager = appContext.getBean("dbManager", DatabaseManager.class);
		//getting HibernateTemplate instance using
		//applicationContext
		temp = new HibernateTemplate((SessionFactory) appContext.getBean("mysessionFactory"));
		System.out.println("//Welcome to Employee Self-Evaluation Portal//");
		System.out.println("Actions---> ");
		System.out.println("            press 1 for Registration");
		System.out.println("            press 2 for logging in");
		System.out.println("            press 3 for Login as Boss");
		System.out.println("            press 4 to exit");
		
		//takes input to prompt the cases.
		switch(input.nextInt()){
		
		case(1):
			registerEmployee();
			break;
		case(2):
			loginEmployee();
			break;
		
		case(3):
			loginBoss();
			break;
		case(4):
			System.exit(0);//closes the application
		
		}
	}
	
	
	/*This method registers the employee and 
	 * adds info to the database
	 */
	private static void registerEmployee(){
		employee = new Employee();
		String pw1="";
		String pw2="";
		String name="";
		int i;
		System.out.println("Enter your username: ");
		name = input.next();
		System.out.println("Enter your EmployeeId: ");
		i = input.nextInt();
		System.out.println("Enter your password: ");
		pw1 = input.next();
		System.out.println("Re-enter your password: ");
		pw2 = input.next();
		//loops if passwords entered won't match
		while(!(pw1.equals(pw2))){
			System.out.println("Passwords doesn't match! Try again");
			System.out.println("Enter your password: ");
			pw1 = input.next();
			System.out.println("Re-enter your password: ");
			pw2 = input.next();
		}
		employee.setName(name);
		employee.setPassword(pw1);
		employee.setId(i);
		dbManager.setEmployee(employee);
		dbManager.setTemplate(temp);
		dbManager.saveUserData();//saves data to database
		
		
	}
	
	/*This method promps user for employeeId and password 
	 * to login to the application.
	 */
	private static void loginEmployee(){
		int enteredId;
		String pass;
		employee = new Employee();
		System.out.println("Enter your employeeId: ");
		enteredId = input.nextInt();
		dbManager = appContext.getBean("dbManager", DatabaseManager.class);
		temp = new HibernateTemplate((SessionFactory) appContext.getBean("mysessionFactory"));
		dbManager.setIdToBeVerified(enteredId);
		dbManager.setEmployee(employee);
		dbManager.setTemplate(temp);
		if(dbManager.verifyUser()){
			System.out.println("Enter your password: ");
			pass = input.next();
			if(pass.equals(dbManager.getPassword())){
				System.out.println("You have succesfully logged in!");
				furtherLoginOptions("loggedinasemployee");
				
			}
			else{
				System.out.println("Your employeeId and Password doesn't match!");
				//main(null);
			}
		}
		else{
			System.out.println("Your EmployeeId doesn't exist in Database. Please Register.");
			//main(null);
		}
	}
	
	/*Method to login as Boss. The
	 * database has specific Id and password which 
	 * only boss knows.
	 */
	private static void loginBoss(){
		int enteredId;
		String pass;
		employee = new Employee();
		System.out.println("enter your ID");
		enteredId = input.nextInt();
		dbManager = appContext.getBean("dbManager", DatabaseManager.class);
		temp = new HibernateTemplate((SessionFactory) appContext.getBean("mysessionFactory"));
		dbManager.setIdToBeVerified(enteredId);
		dbManager.setEmployee(employee);
		dbManager.setTemplate(temp);
		if(dbManager.verifyUser()){
			System.out.println("Enter your password: ");
			pass = input.next();
			if(pass.equals(dbManager.getPassword())){
				System.out.println("You have succesfully logged in!");
				furtherLoginOptions("loggedinasboss");
				
			}
			else{
				System.out.println("Your Id and Password doesn't match!");
				//main(null);
			}
		}
		else{
			System.out.println("Your Id doesn't exist in Database or you are not a Boss!");
			//main(null);
		}
		
	}
	
	/*This method prompts for actions once the user has
	 * logged in.
	 */
	private static void furtherLoginOptions(String condition){
		if(condition.equals("loggedinasemployee")){
			System.out.println("Please press 1 for rating yourself");
			System.out.println("Incase you have already rated yourself, press 2 to view your rating");
			System.out.println("Else press 3 to exit");
			switch(input.nextInt()){
			
			case 1:
				System.out.println("Enter your rating on scale of 1 to 10. (No decimals)");
				while(!(input.hasNext())){
				}
				int rat = input.nextInt();
				dbManager.setRating(rat);
				break;
			case 2:
				System.out.println(dbManager.getRating());
				break;
			case 3:
				//main(null);
				break;
			
			}
		}
		
		if(condition.equals("loggedinasboss")){
			System.out.println("Press 1 to see rating of your employee");
			System.out.println("Else press 2 to exit");
			switch(input.nextInt()){
			
			case 1:
				System.out.println("Enter the employeeId");
				while(!(input.hasNext())){
				}
				int id = input.nextInt();
				employee = new Employee();
				dbManager = appContext.getBean("dbManager", DatabaseManager.class);
				temp = new HibernateTemplate((SessionFactory) appContext.getBean("mysessionFactory"));
				dbManager.setEmployee(employee);
				dbManager.setTemplate(temp);
				employee = dbManager.getRatingOfEmployee(id);
				if(employee!=null){
					System.out.println("Your employee with id: "+id+" rated self as "+employee.getRating()+"/10");
				}
				else{
					System.out.println("EmployeeId not found!");
				}
				break;
			case 2:
				//main(null);
				break;
			
			}
		}
	}

}
