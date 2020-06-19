package ar.com.ada.api.empresa.entities;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;


@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @Column(name = "categoria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    @Column(name = "sueldo_base")
	private BigDecimal sueldoBase;
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Empleado> empleados = new ArrayList<>();



//Constructores
    public Categoria(int id, String nombre, BigDecimal sueldoBase) {
        this.id = id;
        this.nombre = nombre;
        this.sueldoBase = sueldoBase;
    }



    //Getters y Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getSueldoBase() {
		return sueldoBase;
	}
	public void setSueldoBase(BigDecimal sueldoBase) {
		this.sueldoBase = sueldoBase;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

   

  
    
}