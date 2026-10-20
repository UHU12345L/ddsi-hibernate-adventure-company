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
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "CLIENTE")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByFechaNacimiento", query = "SELECT c FROM Cliente c WHERE c.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByIdiomaPreferido", query = "SELECT c FROM Cliente c WHERE c.idiomaPreferido = :idiomaPreferido"),
    @NamedQuery(name = "Cliente.findByCategoria", query = "SELECT c FROM Cliente c WHERE c.categoria = :categoria")})
@NamedNativeQuery(name = "Cliente.findByIdiomaNativo", query = "SELECT * FROM CLIENTE WHERE idiomaPreferido = :idioma", resultClass = Cliente.class)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idCliente")
    private String idCliente;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "idiomaPreferido")
    private String idiomaPreferido;
    @Basic(optional = false)
    @Column(name = "categoria")
    private String categoria;
    @OneToMany(mappedBy = "cliente")
    private Set<Reserva> reservas = new HashSet<Reserva>();

    public Cliente() {
    }

    public Cliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(String idCliente, String nombre, String apellidos, LocalDate fechaNacimiento, String idiomaPreferido, String categoria) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.idiomaPreferido = idiomaPreferido;
        this.categoria = categoria;
    }

    public Cliente(String idCliente, String nombre, String apellidos, String telefono, LocalDate fechaNacimiento, String email, String idiomaPreferido, String categoria) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.idiomaPreferido = idiomaPreferido;
        this.categoria = categoria;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdiomaPreferido() {
        return idiomaPreferido;
    }

    public void setIdiomaPreferido(String idiomaPreferido) {
        this.idiomaPreferido = idiomaPreferido;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cliente[ idCliente=" + idCliente + " ]";
    }

}
