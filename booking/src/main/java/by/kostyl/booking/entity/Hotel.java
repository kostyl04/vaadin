package by.kostyl.booking.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Hotel extends AbstractEntity {

	@Column
	@NotNull
	private String name;
	@Column
	@NotNull
	private String address;
	@Version
	@Column(name = "OPTLOCK", columnDefinition = "BIGINT default '0'")
	private Long optlock;
	@Column
	@NotNull
	private Integer rating;
	@Column(name = "OPERATES_FROM")
	@NotNull
	private Long operatesFrom;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_category_hotel"))
	private Category category;
	@Column
	@NotNull
	private String url;
	@Column
	private String description;

	public Hotel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Long getOperatesFrom() {
		return operatesFrom;
	}

	public void setOperatesFrom(Long operatesFrom) {
		this.operatesFrom = operatesFrom;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOptlock() {
		return optlock;
	}

	public Hotel(String name, String address, Integer rating, Long operatesFrom, Category category, String url,
			String description) {
		super();
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.operatesFrom = operatesFrom;
		this.category = category;
		this.url = url;
		this.description = description;
	}
	public boolean isPersisted(){
		return getId()!=null;
	}
}