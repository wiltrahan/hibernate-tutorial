package com.luv2code.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Student;

public class QueryStudentDemo {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE); //or whatever level you need
			
		//create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			
			//start a transaction
			session.beginTransaction();
			
			//query students
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			
			//display the students
			displayStudents(theStudents);
			
			//query students: lastName="Dough"
			theStudents = session.createQuery("from Student s where s.lastName='Dough'").getResultList();
			
			//display the students
			System.out.println("\n\nStudents with last name of Dough:");
			displayStudents(theStudents);
			
			//query students: lastName='Dough' or firstName='Donald'
			theStudents =
					session.createQuery("from Student s where"
							+ " s.lastName='Dough' OR s.firstName='Donald'").getResultList();
			System.out.println("\n\nStudents with lastName='Dough' or firstName='Donald'");
			displayStudents(theStudents);
			
			theStudents = session.createQuery("from Student s where" 
			+ " s.email LIKE '%us.gov'").getResultList();
			System.out.println("\n\n us.gov email");
			displayStudents(theStudents);
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
		
	}

	private static void displayStudents(List<Student> theStudents) {
		for(Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}
	}

}
