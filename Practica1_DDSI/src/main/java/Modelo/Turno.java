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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "TURNO")
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t"),
    @NamedQuery(name = "Turno.findByIdTurno", query = "SELECT t FROM Turno t WHERE t.idTurno = :idTurno"),
    @NamedQuery(name = "Turno.findByFechaHora", query = "SELECT t FROM Turno t WHERE t.fechaHora = :fechaHora"),
    @NamedQuery(name = "Turno.findByCapacidad", query = "SELECT t FROM Turno t WHERE t.capacidad = :capacidad"),
    @NamedQuery(name = "Turno.findByIdGuia", query = "SELECT t FROM Turno t WHERE t.guia.idGuia = :idGuia")
})
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idTurno")
    private String idTurno;
    @Basic(optional = false)
    @Column(name = "fechaHora")
    private LocalDateTime fechaHora;
    @Basic(optional = false)
    @Column(name = "capacidad")
    private short capacidad;
    @JoinColumn(name = "idAventura", referencedColumnName = "idAventura")
    @ManyToOne(optional = false)
    private Aventura aventura;
    @JoinColumn(name = "idGuia", referencedColumnName = "idGuia")
    @ManyToOne(optional = false)
    private Guia guia;
    @OneToMany(mappedBy = "turno")
    private Set<Reserva> reservas = new HashSet<Reserva>();

    public Turno() {
    }

    public Turno(String idTurno) {
        this.idTurno = idTurno;
    }

    public Turno(String idTurno, LocalDateTime fechaHora, short capacidad) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.capacidad = capacidad;
    }

    public Turno(String idTurno, LocalDateTime fechaHora, short capacidad, Aventura aventura, Guia guia) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.capacidad = capacidad;
        this.aventura = aventura;
        this.guia = guia;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public short getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(short capacidad) {
        this.capacidad = capacidad;
    }

    public Aventura getAventura() {
        return aventura;
    }

    public void setAventura(Aventura aventura) {
        this.aventura = aventura;
    }

    public Guia getGuia() {
        return guia;
    }

    public void setGuia(Guia guia) {
        this.guia = guia;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurno != null ? idTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.idTurno == null && other.idTurno != null) || (this.idTurno != null && !this.idTurno.equals(other.idTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Turno[ idTurno=" + idTurno + " ]";
    }

}
