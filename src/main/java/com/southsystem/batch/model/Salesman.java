package com.southsystem.batch.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Salesman {
	
	private String code;
	private String cpf;
	private String name;
	private BigDecimal salary;	

}
