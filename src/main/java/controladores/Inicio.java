package controladores;

import java.util.Calendar;
import java.util.Scanner;

import entidades.Acceso;
import entidades.Coleccion;
import entidades.Editorial;
import entidades.Genero;
import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import servicios.ImplGeneralCRUD;


public class Inicio {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);
    	
        
        Acceso acceso = new Acceso(1, "Usu","Acceso usuarios biblioteca");
      	Acceso acceso2 = new Acceso(2, "Emp","Empleados biblioteca");
          	
      	em.getTransaction().begin();
      	em.persist(acceso);
      	em.persist(acceso2);
      	em.getTransaction().commit();
        
        
      	// Insertar un nuevo usuario
  		Usuario nuevoUsuario = new Usuario(
            "5435",
            "antobn",
            "juan",
            "32131232",
            "danitbp123@gmail.com",
            "4321",
            false,
            null, // fchFinBloqueo
            Calendar.getInstance(), // fchAltaUsuario
            null, // fchBajaUsuario
            new Acceso(2, "Emp","Empleados biblioteca") // Acceso
        );

        ImplGeneralCRUD.insertObject(em, nuevoUsuario);

        // Seleccionar usuario por su DNI
        System.out.println("Ingrese el DNI del usuario a buscar:");
        String dniBuscado = scanner.nextLine();
        Usuario usuarioEncontrado = new Usuario();
        ImplGeneralCRUD.selectObjectByField(em, usuarioEncontrado, "dniUsuario", dniBuscado);

        // Actualizar datos de un usuario
        System.out.println("Ingrese el ID del usuario a actualizar:");
        long idActualizar = Long.parseLong(scanner.nextLine());
        Usuario usuarioActualizar = em.find(Usuario.class, idActualizar);
        // Aquí podrías actualizar los campos del usuario (nombre, DNI, etc.)
        ImplGeneralCRUD.updateObject(em, usuarioActualizar);

        // Eliminar un usuario por su ID
        //System.out.println("Ingrese el ID del usuario a eliminar:");
        //long idEliminar = Long.parseLong(scanner.nextLine());
        //Usuario usuarioEliminar = em.find(Usuario.class, idEliminar);
        //ImplGeneralCRUD.deleteObject(em, usuarioEliminar);

        em.close();
        emf.close();
        scanner.close();
        
        
    	//Acceso acceso = new Acceso(1, "Usu","Acceso usuarios biblioteca");
		//Acceso acceso2 = new Acceso(2, "Emp","Empleados biblioteca");
    	
	    //em.getTransaction().begin();
		//em.persist(acceso);
		//em.persist(acceso2);
		//em.getTransaction().commit();
		
		//ImplUsuarioCRUD usuCrud = new ImplUsuarioCRUD();
		
		
		//Editorial editorial = new Editorial(1, "Panini");
		//Genero genero = new Genero(1,"ficcion","genero de ficcion");
		//Coleccion coleccion = new Coleccion(1,"coleccion1");
		
		//em.getTransaction().begin();
		//em.persist(editorial);
		//em.persist(genero);
		//em.persist(coleccion);
		//em.getTransaction().commit();
		
		//ImplCrearLibroAutor newLibAut = new ImplCrearLibroAutor();
		
		//em.getTransaction().begin();
		//usuCrud.menuUserCRUD();
		//newLibAut.crearLibroAutor();
	    //em.getTransaction().commit();
		//em.close();
        //emf.close();
	}

}
