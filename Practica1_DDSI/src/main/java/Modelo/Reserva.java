/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "RESERVA")
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
    @NamedQuery(name = "Reserva.findByIdReserva", query = "SELECT r FROM Reserva r WHERE r.idReserva = :idReserva"),
    @NamedQuery(name = "Reserva.findByFechaReserva", query = "SELECT r FROM Reserva r WHERE r.fechaReserva = :fechaReserva"),
    @NamedQuery(name = "Reserva.findByImporte", query = "SELECT r FROM Reserva r WHERE r.importe = :importe"),
    @NamedQuery(name = "Reserva.findByEstado", query = "SELECT r FROM Reserva r WHERE r.estado = :estado"),
    @NamedQuery(name = "Reserva.findByValoracion", query = "SELECT r FROM Reserva r WHERE r.valoracion = :valoracion")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idReserva")
    private String idReserva;
    @Basic(optional = false)
    @Column(name = "fechaReserva")
    private LocalDateTime fechaReserva;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "importe")
    private BigDecimal importe;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Column(name = "valoracion")
    private Short valoracion;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "idTurno", referencedColumnName = "idTurno")
    @ManyToOne(optional = false)
    private Turno turno;

    public Reserva() {
    }

    public Reserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public Reserva(String idReserva, LocalDateTime fechaReserva, BigDecimal importe, String estado) {
        this.idReserva = idReserva;
        this.fechaReserva = fechaReserva;
        this.importe = importe;
        this.estado = estado;
    }

    public Reserva(String idReserva, LocalDateTime fechaReserva, BigDecimal importe, String estado,
            Short valoracion, Cliente cliente, Turno turno) {
        this.idReserva = idReserva;
        this.fechaReserva = fechaReserva;
        this.importe = importe;
        this.estado = estado;
        this.valoracion = valoracion;
        this.cliente = cliente;
        this.turno = turno;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Short getValoracion() {
        return valoracion;
    }

    public void setValoracion(Short valoracion) {
        this.valoracion = valoracion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserva != null ? idReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.idReserva == null && other.idReserva != null) || (this.idReserva != null && !this.idReserva.equals(other.idReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Reserva[ idReserva=" + idReserva + " ]";
    }

}
