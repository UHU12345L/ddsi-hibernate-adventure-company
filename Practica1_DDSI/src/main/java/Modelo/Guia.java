/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "GUIA")
@NamedQueries({
    @NamedQuery(name = "Guia.findAll", query = "SELECT g FROM Guia g"),
    @NamedQuery(name = "Guia.findByIdGuia", query = "SELECT g FROM Guia g WHERE g.idGuia = :idGuia"),
    @NamedQuery(name = "Guia.findByNombre", query = "SELECT g FROM Guia g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Guia.findByApellidos", query = "SELECT g FROM Guia g WHERE g.apellidos = :apellidos"),
    @NamedQuery(name = "Guia.findByTelefono", query = "SELECT g FROM Guia g WHERE g.telefono = :telefono"),
    @NamedQuery(name = "Guia.findByEmail", query = "SELECT g FROM Guia g WHERE g.email = :email"),
    @NamedQuery(name = "Guia.findByFechaAlta", query = "SELECT g FROM Guia g WHERE g.fechaAlta = :fechaAlta")})
public class Guia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idGuia")
    private String idGuia;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "fechaAlta")
    private Date fechaAlta;
    @OneToMany(mappedBy = "guia")
    private Set<Turno> turnos = new HashSet<Turno>();

    public Guia() {
    }

    public Guia(String idGuia) {
        this.idGuia = idGuia;
    }

    public Guia(String idGuia, String nombre, String apellidos, String telefono, String email, Date fechaAlta) {
        this.idGuia = idGuia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.fechaAlta = fechaAlta;
    }

    public String getIdGuia() {
        return idGuia;
    }

    public void setIdGuia(String idGuia) {
        this.idGuia = idGuia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
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
        hash += (idGuia != null ? idGuia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Guia)) {
            return false;
        }
        Guia other = (Guia) object;
        if ((this.idGuia == null && other.idGuia != null) || (this.idGuia != null && !this.idGuia.equals(other.idGuia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Guia[ idGuia=" + idGuia + " ]";
    }

}
