package servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.List;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ImplGeneralCRUD {

	public static void insertObject(EntityManager em, Object entityObject) {
        em.getTransaction().begin();
        em.persist(entityObject);
        em.getTransaction().commit();
        System.out.println("Objeto insertado con éxito.");
    }

	public static <T> void selectObjectByField(EntityManager em, T entityObject, String fieldName, Object value) {
        String className = entityObject.getClass().getSimpleName();
        String queryString = "SELECT e FROM " + className + " e WHERE e." + fieldName + " = :value";
        
        Query query = em.createQuery(queryString);
        query.setParameter("value", value);

        try {
            Object result = query.getSingleResult();
            System.out.println("Objeto encontrado: " + result.toString());
        } catch (Exception e) {
            System.out.println("Objeto no encontrado.");
        }
    }

    public static <T> void updateObject(EntityManager em, T entityObject) {
    	Scanner scanner = new Scanner(System.in);
        em.getTransaction().begin();

        System.out.println("Objeto actual:");
        System.out.println(entityObject);

        Field[] fields = entityObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println("¿Desea cambiar el campo '" + field.getName() + "'? (S/N)");
            String answer = scanner.nextLine().toUpperCase();
            if (answer.equals("S")) {
                System.out.println("Ingrese el nuevo valor para '" + field.getName() + "':");
                String value = scanner.nextLine();
                try {
                    field.setAccessible(true);
                    if (!value.isEmpty()) {
                        Object convertedValue = convertStringToFieldType(value, field.getType());
                        field.set(entityObject, convertedValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        em.merge(entityObject);
        em.getTransaction().commit();
        System.out.println("Objeto actualizado con éxito.");

        scanner.close();
    }

    public static <T> void deleteObject(EntityManager em, T entityObject) {
        em.getTransaction().begin();
        em.remove(em.contains(entityObject) ? entityObject : em.merge(entityObject));
        em.getTransaction().commit();
        System.out.println("Objeto eliminado con éxito.");
    }
    
    
    private static Object convertStringToFieldType(String value, Class<?> fieldType) {
        if (fieldType == Long.class || fieldType == long.class) {
            return Long.parseLong(value);
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(value);
        } else if (fieldType == String.class) {
            return value;
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (fieldType == Calendar.class) {
            // Aquí puedes adaptar la lógica para convertir String a Calendar
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(dateFormat.parse(value));
                return calendar;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else if (List.class.isAssignableFrom(fieldType)) {
            String[] elements = value.split(",");
            return List.of(elements);
        } else {
            return value;
        }
    }
}



