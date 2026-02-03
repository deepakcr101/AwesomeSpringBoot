package com.deepak.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.deepak.entities.User;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Application Started....");
		Configuration cfg = new Configuration();
		cfg.configure("comj/deepak/config/hibernate.cfg.xml");

		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		// Create a new User object
		User user = new User();
		user.setId(1);
		user.setName("Deepak");
		user.setEmail("deepa@gmail.com");
		user.setMobNo("1234567890");
		user.setAge(30);

		try {
			session.save(user);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		System.out.println("User saved successfully!");
	}
}
