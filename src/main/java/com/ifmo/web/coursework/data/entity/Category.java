package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Category {
    private Integer categoryId;
    private String name;
    private Collection<Artifact> artifactsByCategoryId;

    @Id
    @Column(name = "category_id", nullable = false)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId) &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name);
    }

    @OneToMany(mappedBy = "categoryByCategoryId")
    public Collection<Artifact> getArtifactsByCategoryId() {
        return artifactsByCategoryId;
    }

    public void setArtifactsByCategoryId(Collection<Artifact> artifactsByCategoryId) {
        this.artifactsByCategoryId = artifactsByCategoryId;
    }
}
