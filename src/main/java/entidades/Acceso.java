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
@Table(name="accesos", schema="gbp_operacional")
public class Acceso {
	
	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_acceso", nullable=false)
	private long idAcceso;
	
	@Column(name="codigo_acceso", nullable=false)
	private String codigoAcceso;
	
	@Column(name="descripcion_acceso")
	private String descripcionAcceso;
	
    @OneToMany(mappedBy="acceso")
    List<Usuario> usuariosConAcceso;     

    
    
	public long getIdAcceso() {
		return idAcceso;
	}

	public void setIdAcceso(long idAcceso) {
		this.idAcceso = idAcceso;
	}

	public String getCodigoAcceso() {
		return codigoAcceso;
	}

	public void setCodigoAcceso(String codigoAcceso) {
		this.codigoAcceso = codigoAcceso;
	}

	public String getDescripcionAcceso() {
		return descripcionAcceso;
	}

	public void setDescripcionAcceso(String descripcionAcceso) {
		this.descripcionAcceso = descripcionAcceso;
	}

	
	
	//CONSTRUCTORES
	public Acceso() {
		super();
	}

	public Acceso(long id_acceso, String codigo_acceso, String descripcion_acceso) {
		super();
		
		this.idAcceso = id_acceso;
		this.codigoAcceso = codigo_acceso;
		this.descripcionAcceso = descripcion_acceso;
	}

	@Override
	public String toString() {
		return "Acceso [idAcceso=" + idAcceso + ", codigoAcceso=" + codigoAcceso + ", descripcionAcceso="
				+ descripcionAcceso + "]";
	}	
	
}
