package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.BaseModel;
import com.thoughtworks.uaisoccer.common.IdentifiedEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "team")
public class Team extends BaseModel implements IdentifiedEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "key", length = 100)
    private String key;

    public Team() { }

    public Team(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected int deepHashCode() {
        return Objects.hash(id, name, key);
    }

    public boolean deepEquals(Object obj) {
        Team other = (Team)obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.name, other.name) &&
                Objects.equals(this.key, other.key);
    }
}