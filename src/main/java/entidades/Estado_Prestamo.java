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
@Table(name="estados_prestamos", schema="gbp_operacional")
public class Estado_Prestamo {

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_estado_prestamo", nullable=false)
	private long idAcceso;
	
	@Column(name="codigo_estado_prestamo", nullable=false)
	private String codigoEstadoPrestamo;
	
	@Column(name="descripcion_estado_prestamo")
	private String descripcionEstadoPrestamo;
	
	@OneToMany(mappedBy="estadoPrestamo")
    List<Prestamo> estadoDePrestamos; 
	
	//CONSTRUCTORES
	public Estado_Prestamo() {
		super();
	}

	public Estado_Prestamo(String codigo_estado_prestamo, String descripcion_estado_prestamo) {
		super();

		this.codigoEstadoPrestamo = codigo_estado_prestamo;
		this.descripcionEstadoPrestamo = descripcion_estado_prestamo;
	}
	
	
}
