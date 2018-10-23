package com.cysewska.advertisement.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.cysewska.advertisement.domain.enumeration.ECategory;

import com.cysewska.advertisement.domain.enumeration.ECollege;

import com.cysewska.advertisement.domain.enumeration.EDepartment;

/**
 * A Advertisement.
 */
@Entity
@Table(name = "advertisement")
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_date")
    private String date;

    @NotNull
    @Column(name = "author")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ECategory category;


    @Enumerated(EnumType.STRING)
    @Column(name = "college")
    private ECollege college;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private EDepartment department;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "jhi_link")
    private String link;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Advertisement title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Advertisement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public Advertisement date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public Advertisement author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ECategory getCategory() {
        return category;
    }

    public Advertisement category(ECategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public ECollege getCollege() {
        return college;
    }

    public Advertisement college(ECollege college) {
        this.college = college;
        return this;
    }

    public void setCollege(ECollege college) {
        this.college = college;
    }

    public EDepartment getDepartment() {
        return department;
    }

    public Advertisement department(EDepartment department) {
        this.department = department;
        return this;
    }

    public void setDepartment(EDepartment department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public Advertisement email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public Advertisement link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Advertisement picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Advertisement pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
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
        Advertisement advertisement = (Advertisement) o;
        if (advertisement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), advertisement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Advertisement{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", author='" + getAuthor() + "'" +
            ", category='" + getCategory() + "'" +
            ", college='" + getCollege() + "'" +
            ", department='" + getDepartment() + "'" +
            ", email='" + getEmail() + "'" +
            ", link='" + getLink() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            "}";

    }

}
