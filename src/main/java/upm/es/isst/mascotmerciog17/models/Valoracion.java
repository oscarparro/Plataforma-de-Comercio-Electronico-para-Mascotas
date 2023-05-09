package upm.es.isst.mascotmerciog17.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="valoracion_id")
    private Integer id;

    private String nombreCliente; 

    private int calificacion;

    private String comentario;

    @Lob
    private byte[] foto;

    private Integer establecimientoId;

    /*@ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;*/

    public Valoracion(){}

    public Valoracion(Integer id, String nombreCliente, int calificacion, String comentario, byte[] foto, Integer establecimientoId) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.foto=foto;
        this.establecimientoId = establecimientoId;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Integer getEstablecimientoId() {
        return establecimientoId;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setEstablecimientoId(Integer establecimientoId) {
        this.establecimientoId = establecimientoId;
    }
}
