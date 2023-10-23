package com.clientmanagement.app.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cms_cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	// nullable = false | There should be no cart without a user
	// unique = true | Every user must have only one cart in the table
	// updatable = false | Cart Items should belongs to one user non-transfarable
	@Column(nullable = false, unique = true, updatable = false)
	private String username;

	@ManyToMany
	@JoinTable(name = "cart_item_tbl", joinColumns = @JoinColumn(name = "cart_id_pk"), inverseJoinColumns = @JoinColumn(name = "item_id_pk"))
	private Set<Item> items = new HashSet<>();

	public void addItem(Item item) {
		// first add this item to the cart object
		this.items.add(item);
	}

	// If business key is present then use it for the hashcode and equals
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return Objects.equals(username, other.username);
	}

	// Make sure do not add association here
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cart [id=").append(id)
				.append(", username=").append(username).append("]");
		return builder.toString();
	}

}
