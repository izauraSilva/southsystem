package com.southsystem.batch.writer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import com.southsystem.batch.model.Client;
import com.southsystem.batch.model.Sales;
import com.southsystem.batch.model.Salesman;

@Service
public class DadosItemWriter<T> implements ItemWriter<T> { 
	
	int qtdClient=0;
	int qtdSalesman=0;
	
	Float valueSales = 0F;
	
	Map<String, Float> mapSalesman = new HashMap<>();
	
	Map<Long, Float> map = new HashMap<>();
   
	@Override
    public void write(List<? extends T> items) throws Exception {	
		
		
        for (T item : items) { 
        	
        	if (item instanceof Client) { 
        		qtdClient++;
        	};  
        	
        	if (item instanceof Salesman) { 
        		qtdSalesman++;
        	}; 
        	
        	if (item instanceof Sales) {
        		((Sales) item).getItems().stream().forEach((i) -> valueSales = Float.sum(valueSales, i.getPrice()));        		
        		mapSalesman.put(((Sales) item).getSalesman(), valueSales);  
        		map.put(((Sales) item).getId(), valueSales); 
        		valueSales = 0F;        		
        	}

        } 
        
        //mapSalesman.forEach((k, v) -> System.out.println(String.format("key: %s | value: %s", k, v)));
        
        Object minSales = Collections.min(mapSalesman.entrySet(), Map.Entry.comparingByValue()).getKey();     
        
        Object maxSales = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();     
        
        System.out.println("===> ID da venda mais cara: " + maxSales);
        System.out.println("===> O pior vendedor: " + minSales);
        System.out.println("===> Quantidade de clientes no arquivo de entrada: " + qtdClient);
        System.out.println("===> Quantidade de vendedor no arquivo de entrada: " + qtdSalesman);
        
    } 
}
