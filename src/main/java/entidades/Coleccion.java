package entidades;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="colecciones", schema="gbp_operacional")
public class Coleccion {
	
	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_coleccion", nullable=false)
	private long idColeccion;
	
	@Column(name="nombre_coleccion")
	private String nombreColeccion;

    @OneToMany(mappedBy="coleccion")
    List<Libro> coleccionesLibro; 
    
    //CONSTRUCTORES
    public Coleccion() {
    	super();
    }

	public Coleccion(long idColeccion, String nombreColeccion) {
		super();
		
		this.idColeccion = idColeccion;
		this.nombreColeccion = nombreColeccion;
	}
}
