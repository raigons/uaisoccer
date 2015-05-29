package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseModel;
import com.thoughtworks.uaisoccer.common.IdentifiedEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Championship extends BaseModel implements IdentifiedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    protected boolean deepEquals(Object obj) {
        Championship other = (Championship)obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }

    @Override
    protected int deepHashCode() {
        return Objects.hash(this.id, this.name);
    }
}