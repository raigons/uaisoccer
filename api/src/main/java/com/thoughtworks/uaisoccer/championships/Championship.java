package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseModel;
import com.thoughtworks.uaisoccer.common.IdentifiedEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Championship extends BaseModel implements IdentifiedEntity {

    @Id
    @SequenceGenerator(name = "championship_sequence", sequenceName = "championship_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "championship_sequence")
    private Long id;

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