/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author harol
 */
@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCodiClie", query = "SELECT c FROM Cliente c WHERE c.codiClie = :codiClie"),
    @NamedQuery(name = "Cliente.findByAppaClie", query = "SELECT c FROM Cliente c WHERE c.appaClie = :appaClie"),
    @NamedQuery(name = "Cliente.findByApmaClie", query = "SELECT c FROM Cliente c WHERE c.apmaClie = :apmaClie"),
    @NamedQuery(name = "Cliente.findByNombClie", query = "SELECT c FROM Cliente c WHERE c.nombClie = :nombClie")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "codiClie")
    private String codiClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "appaClie")
    private String appaClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "apmaClie")
    private String apmaClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombClie")
    private String nombClie;

    public Cliente() {
    }

    public Cliente(String codiClie) {
        this.codiClie = codiClie;
    }

    public Cliente(String codiClie, String appaClie, String apmaClie, String nombClie) {
        this.codiClie = codiClie;
        this.appaClie = appaClie;
        this.apmaClie = apmaClie;
        this.nombClie = nombClie;
    }

    public String getCodiClie() {
        return codiClie;
    }

    public void setCodiClie(String codiClie) {
        this.codiClie = codiClie;
    }

    public String getAppaClie() {
        return appaClie;
    }

    public void setAppaClie(String appaClie) {
        this.appaClie = appaClie;
    }

    public String getApmaClie() {
        return apmaClie;
    }

    public void setApmaClie(String apmaClie) {
        this.apmaClie = apmaClie;
    }

    public String getNombClie() {
        return nombClie;
    }

    public void setNombClie(String nombClie) {
        this.nombClie = nombClie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiClie != null ? codiClie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codiClie == null && other.codiClie != null) || (this.codiClie != null && !this.codiClie.equals(other.codiClie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Cliente[ codiClie=" + codiClie + " ]";
    }
    
}
