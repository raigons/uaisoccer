package com.thoughtworks.uaisoccer.teams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.common.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Table(indexes = {@Index(name = "key_index", columnList = "key", unique = true),
                    @Index(name = "name_index", columnList = "name", unique = true)})
public class Team extends BaseModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    private String key;

    @Column(length = 150)
    @NotBlank
    @Pattern(regexp = "^(?![0-9].*$).*$", message = "cannot be numeric")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "teams")
    private List<Championship> championships;

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
