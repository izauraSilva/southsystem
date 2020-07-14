package com.southsystem.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Items {
	
	private String code;
	private Integer quantity;
	private Float price;	

}
