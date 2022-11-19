package com.msstore.DTO;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThongKeDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String month;
	
	private String totalCode;
	
	private String totalProduct;
	
	private String minPrice;
	
	private String maxPrice;
	
	private String mediumPrice;
	
	private String totalPrice;
	
}
