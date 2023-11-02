package servicios;

import java.util.ArrayList;
import java.util.List;

import entidades.Autor;
import entidades.Coleccion;
import entidades.Editorial;
import entidades.Genero;
import entidades.Libro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ImplCrearLibroAutor {
	
	public void crearLibroAutor() {
	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
	    EntityManager em = emf.createEntityManager();
	
	    Autor autor1 = new Autor(1,"pepe","juan");
	
	    Autor autor2 = new Autor(2,"juan","pepe");
	
	    
	    em.getTransaction().begin();
		em.persist(autor1);
		em.persist(autor2);
		em.getTransaction().commit();
	    
	    List<Autor> autores = new ArrayList<>();
	    
	    autores.add(autor1);
	    autores.add(autor2);
	
	    Libro libro = new Libro(
				1,
				"23123ha1",
				"el libro de pepe juan",
				"1",
				23,
				new Editorial(1, "Panini"),
				new Genero(1,"ficcion","genero de ficcion"),
				new Coleccion(1,"coleccion1"),
				autores
				);
	
	    em.getTransaction().begin();
	    em.persist(libro);
	    em.getTransaction().commit();
	    //em.flush();
	    //em.clear();

	}
}
