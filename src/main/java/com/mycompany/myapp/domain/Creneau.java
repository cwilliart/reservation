package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Creneau.
 */
@Entity
@Table(name = "creneau")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Creneau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "heure")
    private String heure;

    @ManyToMany(mappedBy = "creneaus")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeure() {
        return heure;
    }

    public Creneau heure(String heure) {
        this.heure = heure;
        return this;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Creneau reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Creneau addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.getCreneaus().add(this);
        return this;
    }

    public Creneau removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.getCreneaus().remove(this);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Creneau creneau = (Creneau) o;
        if (creneau.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creneau.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Creneau{" +
            "id=" + getId() +
            ", heure='" + getHeure() + "'" +
            "}";
    }
}
