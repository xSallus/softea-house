/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Table(name="t_products")
@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class Product {
	@Id @GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	private String sku;
	private double price;

	@Override
	public String toString() {
		final String base = "{id:%s,sku:%s,price:%s}";
	  return String.format(base, id, sku, price);
	}
}
