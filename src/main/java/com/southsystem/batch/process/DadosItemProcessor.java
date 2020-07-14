package com.southsystem.batch.process;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.southsystem.batch.model.Client;
import com.southsystem.batch.model.File;
import com.southsystem.batch.model.Items;
import com.southsystem.batch.model.Sales;
import com.southsystem.batch.model.Salesman;

@Service
public class DadosItemProcessor<T> implements ItemProcessor<File, T>  {
		
	ArrayList<Items> listItems;	

	@SuppressWarnings("unchecked")
	@Override
	public T process(File item) throws Exception {
		
		listItems = new ArrayList<Items>();
		
		switch (item.getField1()) {
			case "001":				
				return (T) new Salesman(item.getField1(), item.getField2(), item.getField3(), new BigDecimal(item.getField4()));
			case "002":				
				return (T) new Client(item.getField1(), item.getField2(), item.getField3(), item.getField4());
			case "003":		
				List<String> tokens = Arrays.stream(item.getField3().split(",")).collect(Collectors.toList());				
				tokens.forEach(i-> this.addItems(i));
				return (T) new Sales(item.getField1(), Long.valueOf(item.getField2()), listItems, item.getField4());				
			default:
				throw new InvalidParameterException("Record invalid");
		}	
	}
	
	private void addItems(String item) {
		String[] output = item.split("-");
		Items items =  new Items(output[0],Integer.valueOf(output[1]),Float.valueOf(output[2].replace("]", "")));
		listItems.add(items);
	}	
}
