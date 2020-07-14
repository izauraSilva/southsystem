package com.southsystem.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
	
	private String code;
	private String cnpj;
	private String name;
	private String area;

}
