package com.detasad.lwm2m.controller;

import java.io.IOException;

import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.detasad.lwm2m.dto.RequestDto;
import com.detasad.lwm2m.service.Lwm2mClientService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/lwm2m/v1")
@Log4j2
public class Lwm2mClientController {
	
	@Autowired
	Lwm2mClientService lwm2mClientService;
	
	@PostMapping("/send")
	ResponseEntity<String> send( @RequestBody RequestDto request) {
		log.info("send called" + request.getEndpoint() + "Port : " + request.getPort());
		try {
			lwm2mClientService.send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Failed : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Failed : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidDDFFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Failed : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<>("Success" , HttpStatus.OK);
	}

}
