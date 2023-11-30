package com.project.LWBS;
//import com.project.LWBS.domain.User;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class LwbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LwbsApplication.class, args);
//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("basicjpa");
//
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//		EntityTransaction entityTransaction = entityManager.getTransaction();
//
//		try {
//			entityTransaction.begin();
//			User userEntity = new User(1, "임세규", "skrheem0315", "18102101", "sk991116!", ".", "URL");
//
//			entityManager.persist(userEntity);
//
//			entityTransaction.commit();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			entityTransaction.rollback();
//		} finally {
//			entityManager.close();
//		}
//
//		entityManagerFactory.close();
	}


}
