/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "AVENTURA")
@NamedQueries({
    @NamedQuery(name = "Aventura.findAll", query = "SELECT a FROM Aventura a"),
    @NamedQuery(name = "Aventura.findByIdAventura", query = "SELECT a FROM Aventura a WHERE a.idAventura = :idAventura"),
    @NamedQuery(name = "Aventura.findByNombre", query = "SELECT a FROM Aventura a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Aventura.findByTipo", query = "SELECT a FROM Aventura a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "Aventura.findByDificultad", query = "SELECT a FROM Aventura a WHERE a.dificultad = :dificultad"),
    @NamedQuery(name = "Aventura.findByDuracion", query = "SELECT a FROM Aventura a WHERE a.duracion = :duracion"),
    @NamedQuery(name = "Aventura.findByPrecio", query = "SELECT a FROM Aventura a WHERE a.precio = :precio"),
    @NamedQuery(name = "Aventura.findByEdadMinima", query = "SELECT a FROM Aventura a WHERE a.edadMinima = :edadMinima")})
public class Aventura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idAventura")
    private String idAventura;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "dificultad")
    private String dificultad;
    @Basic(optional = false)
    @Column(name = "duracion")
    private short duracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "precio")
    private BigDecimal precio;
    @Basic(optional = false)
    @Column(name = "edadMinima")
    private short edadMinima;
    @OneToMany(mappedBy = "aventura")
    private Set<Turno> turnos=new HashSet<Turno>();

    public Aventura() {
    }

    public Aventura(String idAventura) {
        this.idAventura = idAventura;
    }

    public Aventura(String idAventura, String nombre, String tipo, String dificultad, short duracion, BigDecimal precio, short edadMinima) {
        this.idAventura = idAventura;
        this.nombre = nombre;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.duracion = duracion;
        this.precio = precio;
        this.edadMinima = edadMinima;
    }

    public String getIdAventura() {
        return idAventura;
    }

    public void setIdAventura(String idAventura) {
        this.idAventura = idAventura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public short getDuracion() {
        return duracion;
    }

    public void setDuracion(short duracion) {
        this.duracion = duracion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public short getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(short edadMinima) {
        this.edadMinima = edadMinima;
    }

    public Set<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAventura != null ? idAventura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aventura)) {
            return false;
        }
        Aventura other = (Aventura) object;
        if ((this.idAventura == null && other.idAventura != null) || (this.idAventura != null && !this.idAventura.equals(other.idAventura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Aventura[ idAventura=" + idAventura + " ]";
    }
    
}
