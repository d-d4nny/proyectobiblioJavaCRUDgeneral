package entidades;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="libros", schema="gbp_operacional")
public class Libro {	
	

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_libro", nullable=false)
	private long idLibro;
	
	@Column(name="isbn_libro", nullable=false)
	private String isbnLibro;
	
	@Column(name="titulo_libro")
	private String tituloLibro;
	
	@Column(name="edicion_libro")
	private String edicionLibro;
	
	@Column(name="cantidad_libro")
	private int cantidadLibro;
	
	@ManyToOne
    @JoinColumn(name="id_editorial")
    Editorial editorial;
	
	@ManyToOne
    @JoinColumn(name="id_genero")
    Genero genero;
	
	@ManyToOne
    @JoinColumn(name="id_coleccion")
    Coleccion coleccion;
	
	@ManyToMany
    @JoinTable(
        name = "libro_autor", schema="gbp_operacional",
        joinColumns = @JoinColumn(name = "id_libro"),
        inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private List<Autor> autores;
	
	@ManyToMany(mappedBy = "libros")
    private List<Prestamo> prestamos;
	
	//CONSTRUCTORES
	public Libro() {
		super();
	}

	public Libro(long idLibro, String isbnLibro, String tituloLibro, String edicionLibro, int cantidadLibro, Editorial editorial,
			Genero genero, Coleccion coleccion, List<Autor> autores) {
		super();
		
		this.idLibro = idLibro;
		this.isbnLibro = isbnLibro;
		this.tituloLibro = tituloLibro;
		this.edicionLibro = edicionLibro;
		this.cantidadLibro = cantidadLibro;
		this.editorial = editorial;
		this.genero = genero;
		this.coleccion = coleccion;
		this.autores = autores;
	}
}
