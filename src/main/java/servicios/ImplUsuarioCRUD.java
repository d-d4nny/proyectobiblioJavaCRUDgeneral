package servicios;

import java.util.Calendar;
import java.util.Scanner;
import java.util.List;

import entidades.Acceso;
import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class ImplUsuarioCRUD {
	
	public void insertUser() {
		
        // Iniciar una transacción y guardar el usuario en la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
    	
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese los datos del nuevo usuario:");

        System.out.print("DNI del usuario (formato: 12345678A): ");
        String dni = scanner.nextLine();

        System.out.print("Nombre del usuario: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellidos del usuario: ");
        String apellidos = scanner.nextLine();

        System.out.print("Teléfono del usuario (9 números): ");
        String telefono = scanner.nextLine();

        System.out.print("Email del usuario: ");
        String email = scanner.nextLine();

        System.out.print("Clave del usuario: ");
        String clave = scanner.nextLine();


        // Crear una instancia de Usuario con los datos ingresados a través del constructor
        Usuario usuario = new Usuario(
            dni,
            nombre,
            apellidos,
            telefono,
            email,
            clave,
            false,
            null, // fchFinBloqueo
            Calendar.getInstance(), // fchAltaUsuario
            null, // fchBajaUsuario
            new Acceso(1, "Usu","Acceso usuarios biblioteca") // Acceso
        );

        try {
            transaction.begin();
            em.persist(usuario); // Guarda el nuevo usuario en la base de datos
            transaction.commit(); // Confirma la transacción
            System.out.println("Usuario agregado exitosamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Revierte la transacción si ocurre un error
            }
            System.err.println("Error al agregar usuario: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
	
	public void selectUser() {
		
		// Iniciar una transacción y guardar el usuario en la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
		
		Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Mostrar todos los usuarios");
            System.out.println("2. Mostrar usuario por DNI");
            System.out.println("3. Salir");
            System.out.println("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                	List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
                	
                	for (Usuario usuario : usuarios) {
                        detallesUsuario(usuario);
                    }
                    break;
                case 2:
                	boolean usuarioEncontrado = false;
                    
                    do {
                        System.out.println("Ingrese el DNI del usuario a buscar (o escriba 'volver' para regresar al menú): ");
                        String dni = scanner.nextLine();

                        if (dni.equalsIgnoreCase("volver")) {
                            // Si se ingresa 'volver', salimos del bucle
                            break;
                        }

                        try {
                            Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.dniUsuario = :dni", Usuario.class)
                                    .setParameter("dni", dni)
                                    .getSingleResult();

                            if (usuario != null) {
                                detallesUsuario(usuario);
                                usuarioEncontrado = true;
                            } else {
                                System.out.println("Usuario no encontrado. Intente de nuevo.");
                            }
                        } catch (NoResultException e) {
                            System.out.println("Usuario no encontrado. Intente de nuevo o escriba 'volver' para regresar al menú.");
                        }
                    } while (!usuarioEncontrado);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        }

        scanner.close();
    }
	
	
	public void updateUser() {
		
		// Iniciar una transacción y guardar el usuario en la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        
		Scanner scanner = new Scanner(System.in);
		boolean usuarioEncontrado = false;

	    do {
	        System.out.println("Ingrese el DNI del usuario a actualizar (o escriba 'volver' para regresar al menú): ");
	        String dni = scanner.nextLine();

	        if (dni.equalsIgnoreCase("volver")) {
	            // Si se ingresa 'volver', salimos del bucle
	            break;
	        }

	        try {
	            Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.dniUsuario = :dni", Usuario.class)
	                    .setParameter("dni", dni)
	                    .getSingleResult();

	            if (usuario != null) {
	                // Mostrar campos editables para actualizar
	            	System.out.println("Campos editables para el usuario con DNI: " + usuario.getDniUsuario());
	                System.out.println("1. Nombre: " + usuario.getNombreUsuario());
	                System.out.println("2. Apellidos: " + usuario.getApellidosUsuario());
	                System.out.println("3. Teléfono: " + usuario.getTlfUsuario());
	        	    System.out.println("4. Email: " + usuario.getEmailUsuario());
	        	    System.out.println("5. Clave: " + usuario.getClaveUsuario());
	        	    System.out.println("6. ¿Está bloqueado?: " + usuario.isEstaBloqueadoUsuario());
	        	    System.out.println("7. Fecha fin de bloqueo: " + (usuario.getFchFinBloqueo() != null ? usuario.getFchFinBloqueo().getTime().toString() : "No definida"));
	        	    System.out.println("8. Fecha de baja: " + (usuario.getFchBajaUsuario() != null ? usuario.getFchBajaUsuario().getTime().toString() : "No definida"));

	                // Actualizar campos seleccionados
	        	    System.out.println("Seleccione el número del campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	    String opcion;
	        	    do {
	        	        opcion = scanner.nextLine();

	        	        switch (opcion) {
	        	            case "1":
	        	                System.out.println("Ingrese el nuevo nombre del usuario:");
	        	                String nuevoNombre = scanner.nextLine();
	        	                usuario.setNombreUsuario(nuevoNombre);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;
	        	            case "2":
	        	                System.out.println("Ingrese los nuevos apellidos del usuario:");
	        	                String nuevosApellidos = scanner.nextLine();
	        	                usuario.setApellidosUsuario(nuevosApellidos);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;
	        	            case "3":
	        	                System.out.println("Ingrese el nuevo telefono del usuario:");
	        	                String nuevoTelefono = scanner.nextLine();
	        	                usuario.setTlfUsuario(nuevoTelefono);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;
	        	            case "4":
	        	                System.out.println("Ingrese el nuevo email del usuario:");
	        	                String nuevoEmail = scanner.nextLine();
	        	                usuario.setEmailUsuario(nuevoEmail);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;
	        	            case "5":
	        	                System.out.println("Ingrese la nueva clave del usuario:");
	        	                String nuevaClave = scanner.nextLine();
	        	                usuario.setClaveUsuario(nuevaClave);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;
	        	            case "6":
	        	                System.out.println("Ingrese si sigue bloqueado el usuario:");
	        	                System.out.println("¿Quiere bloquear al usuario? (Escriba 'SI' o 'NO'): ");
	        	                String estaBloqueado = scanner.nextLine().toUpperCase();
	        	                Boolean nuevoBloqueo = false;
	        	                do {
	        	                    if (estaBloqueado.equals("NO")) {
	        	                    	nuevoBloqueo = false;
	        	                    } else if (estaBloqueado.equals("SI")) {
	        	                    	nuevoBloqueo = true;
	        	                    } else {
	        	                        System.out.println("Por favor, escriba 'SI' o 'NO'.");
	        	                    }
	        	                } while (!estaBloqueado.equals("SI") && !estaBloqueado.equals("NO"));
	        	                usuario.setEstaBloqueadoUsuario(nuevoBloqueo);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;
	        	            case "7":
	        	                System.out.println("Modificar estado de bloqueo del usuario:");
        	                    System.out.println("¿El usuario está bloqueado? (Escriba 'SI' o 'NO'): ");
	        	                String estaFchFinBloqueo = scanner.nextLine().toUpperCase();
	        	                Calendar nuevoFchFinBloqueo = null;
	        	                do {
	        	                    if (estaFchFinBloqueo.equals("NO")) {
	        	                        nuevoFchFinBloqueo = null;
	        	                    } else if (estaFchFinBloqueo.equals("SI")) {
	        	                        nuevoFchFinBloqueo = Calendar.getInstance();
	        	                    } else {
	        	                        System.out.println("Por favor, escriba 'SI' o 'NO'.");
	        	                    }
	        	                } while (!estaFchFinBloqueo.equals("SI") && !estaFchFinBloqueo.equals("NO"));
	        	                usuario.setFchFinBloqueo(nuevoFchFinBloqueo);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;
	        	            case "8":
	        	            	System.out.println("Modificar estado de baja del usuario:");
	        	            	System.out.println("¿El usuario está dado de baja? (Escriba 'SI' o 'NO'): ");
        	                    String estaDadoBaja = scanner.nextLine().toUpperCase();
	        	                Calendar nuevoFchBaja = null;
	        	                do {
	        	                    if (estaDadoBaja.equals("NO")) {
	        	                        nuevoFchFinBloqueo = null;
	        	                    } else if (estaDadoBaja.equals("SI")) {
	        	                        nuevoFchFinBloqueo = Calendar.getInstance();
	        	                    } else {
	        	                        System.out.println("Por favor, escriba 'SI' o 'NO'.");
	        	                    }
	        	                } while (!estaDadoBaja.equals("SI") && !estaDadoBaja.equals("NO"));
	        	                usuario.setFchBajaUsuario(nuevoFchBaja);
	        	                System.out.println("Seleccione otro campo a actualizar (o escriba 'terminar' para finalizar): ");
	        	                break;	            
	        	            case "terminar":
	        	                break;
	        	            default:
	        	                System.out.println("Opción inválida. Intente de nuevo o escriba 'terminar' para finalizar.");
	        	        }
	        	    } while (!opcion.equalsIgnoreCase("terminar"));

	                // Realizar la actualización en la base de datos
	                em.getTransaction().begin();
	                em.merge(usuario);
	                em.getTransaction().commit();

	                System.out.println("Usuario actualizado con éxito:");
	                detallesUsuario(usuario);

	                usuarioEncontrado = true;
	            } else {
	                System.out.println("Usuario no encontrado. Intente de nuevo.");
	            }
	        } catch (NoResultException e) {
	            System.out.println("Usuario no encontrado. Intente de nuevo o escriba 'volver' para regresar al menú.");
	        }
	    } while (!usuarioEncontrado);
	}
	
	
	public void deleteUser() {	
		
		// Iniciar una transacción y guardar el usuario en la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        
		Scanner scanner = new Scanner(System.in);
		
	    boolean confirmacionBorrado = false;

	    do {
	        System.out.println("Ingrese el DNI del usuario a borrar (o escriba 'volver' para regresar al menú): ");
	        String dniABorrar = scanner.nextLine();

	        if (dniABorrar.equalsIgnoreCase("volver")) {
	            // Salir del bucle si se escribe 'volver'
	            break;
	        }

	        try {
	            Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.dniUsuario = :dni", Usuario.class)
	                    .setParameter("dni", dniABorrar)
	                    .getSingleResult();

	            if (usuario != null) {
	                System.out.println("El usuario con DNI " + dniABorrar + " será eliminado.");
	                System.out.println("Confirme la eliminación escribiendo de nuevo el DNI del usuario a borrar: ");
	                String confirmacion = scanner.nextLine();

	                if (confirmacion.equals(dniABorrar)) {
	                    em.getTransaction().begin();
	                    em.remove(usuario);
	                    em.getTransaction().commit();

	                    System.out.println("Usuario eliminado con éxito.");
	                    confirmacionBorrado = true;
	                } else {
	                    System.out.println("La confirmación no coincide. No se ha eliminado el usuario.");
	                    confirmacionBorrado = false;
	                }
	            } else {
	                System.out.println("Usuario no encontrado. Intente de nuevo.");
	            }
	        } catch (NoResultException e) {
	            System.out.println("Usuario no encontrado. Intente de nuevo o escriba 'volver' para regresar al menú.");
	        }
	    } while (!confirmacionBorrado);
	}
	
	
	private static void detallesUsuario(Usuario usuario) {
	    System.out.println("Usuario encontrado:");
	    System.out.println("ID: " + usuario.getIdUsuario());
	    System.out.println("DNI: " + usuario.getDniUsuario());
	    System.out.println("Nombre: " + usuario.getNombreUsuario());
	    System.out.println("Apellidos: " + usuario.getApellidosUsuario());
	    System.out.println("Teléfono: " + usuario.getTlfUsuario());
	    System.out.println("Email: " + usuario.getEmailUsuario());
	    System.out.println("Clave: " + usuario.getClaveUsuario());
	    System.out.println("¿Está bloqueado?: " + usuario.isEstaBloqueadoUsuario());
	    System.out.println("Fecha fin de bloqueo: " + (usuario.getFchFinBloqueo() != null ? usuario.getFchFinBloqueo().getTime().toString() : "No definida"));
	    System.out.println("Fecha de alta: " + (usuario.getFchAltaUsuario() != null ? usuario.getFchAltaUsuario().getTime().toString() : "No definida"));
	    System.out.println("Fecha de baja: " + (usuario.getFchBajaUsuario() != null ? usuario.getFchBajaUsuario().getTime().toString() : "No definida"));

	    // Acceso del usuario
	    if (usuario.getAcceso() != null) {
	        System.out.println("Codigo de acceso: " + usuario.getAcceso().getCodigoAcceso());
	        System.out.println("Descripción de acceso: " + usuario.getAcceso().getDescripcionAcceso());
	        // Otros detalles del Acceso, según la estructura de la entidad Acceso.
	    } else {
	        System.out.println("No se encontró información de acceso para este usuario.");
	    }

	    System.out.println("-----------------------------------");
	}
	
	
	public void menuUserCRUD() {
		Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("Menu:");
            System.out.println("1. Insertar Usuario");
            System.out.println("2. Buscar Usuario por ID");
            System.out.println("3. Actualizar Usuario");
            System.out.println("4. Eliminar Usuario");
            System.out.println("5. Salir");
            System.out.println("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                	insertUser();
                    break;
                case 2:
                    selectUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                	System.out.println("Volviendo al menu principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 5);
        scanner.close();
    }
}
