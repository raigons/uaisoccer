package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.BaseModel;
import com.thoughtworks.uaisoccer.common.IdentifiedEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(indexes = {@Index(name = "key_index", columnList = "key", unique = true)})
public class Team extends BaseModel implements IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_sequence")
    private Long id;

    @Column(length = 150)
    private String name;

    @Column(length = 100)
    private String key;

    private Boolean enabled = true;

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

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    protected int deepHashCode() {
        return Objects.hash(id, name, key, enabled);
    }

    public boolean deepEquals(Object obj) {
        Team other = (Team)obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.name, other.name) &&
                Objects.equals(this.key, other.key) &&
                Objects.equals(this.enabled, other.enabled);
    }
}
