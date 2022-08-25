package com.detasad.lwm2m.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestDto implements Serializable{
	
	String endpoint;
	int port;

}
