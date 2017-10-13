package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @Column(name = "descriptif")
    private String descriptif;

    @NotNull
    @Column(name = "titre", nullable = false)
    private String titre;

    @ManyToOne
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "reservation_creneau",
               joinColumns = @JoinColumn(name="reservations_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="creneaus_id", referencedColumnName="id"))
    private Set<Creneau> creneaus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Reservation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public Reservation descriptif(String descriptif) {
        this.descriptif = descriptif;
        return this;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public String getTitre() {
        return titre;
    }

    public Reservation titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public User getUser() {
        return user;
    }

    public Reservation user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Creneau> getCreneaus() {
        return creneaus;
    }

    public Reservation creneaus(Set<Creneau> creneaus) {
        this.creneaus = creneaus;
        return this;
    }

    public Reservation addCreneau(Creneau creneau) {
        this.creneaus.add(creneau);
        creneau.getReservations().add(this);
        return this;
    }

    public Reservation removeCreneau(Creneau creneau) {
        this.creneaus.remove(creneau);
        creneau.getReservations().remove(this);
        return this;
    }

    public void setCreneaus(Set<Creneau> creneaus) {
        this.creneaus = creneaus;
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
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", descriptif='" + getDescriptif() + "'" +
            ", titre='" + getTitre() + "'" +
            "}";
    }
}
