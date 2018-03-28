package nl.whitehorses.hellobeer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import nl.whitehorses.hellobeer.domain.enumeration.BeerType;

/**
 * A Beer.
 */
@Entity
@Table(name = "beer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Beer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private BeerType type;

    @ManyToOne
    private Brewery brewery;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Beer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeerType getType() {
        return type;
    }

    public Beer type(BeerType type) {
        this.type = type;
        return this;
    }

    public void setType(BeerType type) {
        this.type = type;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public Beer brewery(Brewery brewery) {
        this.brewery = brewery;
        return this;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
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
        Beer beer = (Beer) o;
        if (beer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Beer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
