package com.southsystem.batch.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Sales {
	
	private String code;
	private Long id;
	private List<Items> items;
	private String salesman;

}
