package com.joshua.springeclipse.curso.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.joshua.springeclipse.curso.app.models.services.IUploadFileService;
import com.joshua.springeclipse.curso.app.models.services.UploadFileServiceImpl;

@SpringBootApplication
public class SpringbootDataJpaApplication {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	IUploadFileService uploadFileService;
	

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDataJpaApplication.class, args);
		System.out.print("jala");
	}

}
