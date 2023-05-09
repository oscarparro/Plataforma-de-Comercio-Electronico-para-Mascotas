package upm.es.isst.mascotmerciog17.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="establecimiento_id")
    private Integer id;

    @Column(nullable = false)
    private String nombre;   
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String servicios;
    @Column(nullable = false)
    private String horario;
    private String imagen;   
    @Column(nullable = false)
    private String descripcion;
    private Double latitud;
    private Double longitud;


   /* @ManyToOne(optional = false)
    @JoinColumn(name = "id_propietario", nullable = true)
    private Propietario propietario;*/

    public Establecimiento(){}

    // Constructor
    public Establecimiento(Integer id, String nombre, String direccion, String servicios, String horario, String imagen, String descripcion, Double latitud, Double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.servicios = servicios;
        this.horario = horario;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}

