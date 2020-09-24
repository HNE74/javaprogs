package de.hne.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;   

    @Column(name = "name")
    private String name;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "overview")
    private String overview;

    @Column(name = "date_of_publish")
    private Date dateOfPublish;

    @Column(name = "date_of_repring")
    private Date dateOfReprint;

    @Column(name = "acknowledgement")
    private String acknowledgement;

    @Column(name = "copyright")
    private String copyright;

    @Column(name = "fact")
    private String fact;

    @Column(name = "printed_by")
    private String printedBy;

    @Column(name = "cover_designer")
    private String coverDesigner;

    @Column(name = "cover_photo_url")
    private String coverPhotoUrl;

    @Column(name = "price")
    private double price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Date getDateOfPublish() {
		return dateOfPublish;
	}

	public void setDateOfPublish(Date dateOfPublish) {
		this.dateOfPublish = dateOfPublish;
	}

	public Date getDateOfReprint() {
		return dateOfReprint;
	}

	public void setDateOfReprint(Date dateOfReprint) {
		this.dateOfReprint = dateOfReprint;
	}

	public String getAcknowledgement() {
		return acknowledgement;
	}

	public void setAcknowledgement(String acknowledgement) {
		this.acknowledgement = acknowledgement;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

	public String getPrintedBy() {
		return printedBy;
	}

	public void setPrintedBy(String printedBy) {
		this.printedBy = printedBy;
	}

	public String getCoverDesigner() {
		return coverDesigner;
	}

	public void setCoverDesigner(String coverDesigner) {
		this.coverDesigner = coverDesigner;
	}

	public String getCoverPhotoUrl() {
		return coverPhotoUrl;
	}

	public void setCoverPhotoUrl(String coverPhotoUrl) {
		this.coverPhotoUrl = coverPhotoUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
