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
@Table(name="generos", schema="gbp_operacional")
public class Genero {
	
	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_genero", nullable=false)
	private long idGenero;
	
	@Column(name="nombre_genero")
	private String nombreGenero;
	
	@Column(name="descripcion_genero")
	private String descripcionGenero;

    @OneToMany(mappedBy="genero")
    List<Libro> generosLibro;

    //CONSTRUCTORES
    public Genero() {
    	super();
    }

	public Genero(long idGenero, String nombreGenero, String descripcionGenero) {
		super();
		
		this.idGenero = idGenero;
		this.nombreGenero = nombreGenero;
		this.descripcionGenero = descripcionGenero;
	} 

    
    
}
