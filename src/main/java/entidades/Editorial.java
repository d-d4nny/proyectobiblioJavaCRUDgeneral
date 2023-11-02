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
@Table(name="editoriales", schema="gbp_operacional")
public class Editorial {
	
	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_editorial", nullable=false)
	private long idEditorial;
	
	@Column(name="nombre_editorial")
	private String nombreEditorial;

    @OneToMany(mappedBy="editorial")
    List<Libro> editorialesLibro; 
    
    
    //CONSTRUCTORES
    public Editorial() {
    	super();
    }


	public Editorial(long idEditorial, String nombreEditorial) {
		super();
		
		this.idEditorial = idEditorial;
		this.nombreEditorial = nombreEditorial;
	}
    
    
}
