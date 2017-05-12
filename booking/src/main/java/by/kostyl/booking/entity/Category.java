package by.kostyl.booking.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Category extends AbstractEntity {

	@Column
	@NotNull
	private String name;
	@Column(name = "OPTLOCK",columnDefinition="BIGINT default '0'")
	private Long optlock;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category(String name) {
		this.name = name;
	}

	public Category() {
	}

	@Override
	public String toString() {
		return name;
	}

	

}
