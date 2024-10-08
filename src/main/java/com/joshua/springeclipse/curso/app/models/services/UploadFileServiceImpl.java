package com.joshua.springeclipse.curso.app.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final static String UPLOADS_FOLDER ="C:\\Users\\crist\\Desktop\\Trabajos\\Programas java\\uploads";

	
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		Resource recurso= null;
		
			recurso = new UrlResource(pathFoto.toUri());
			if(!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("Error no se puede cargar la imagen");
			}
		
		
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String rootPath = UPLOADS_FOLDER;
		
			byte[] bytes =file.getBytes();
			Path rutaCompleta = Paths.get(rootPath + "//"+ file.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
			
		
		return rootPath;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
	
	
		return false;
	}

	
	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();

	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());

	}

	@Override
	public void init() {
	    Path uploadPath = Paths.get(UPLOADS_FOLDER);
	    try {
	        Files.createDirectory(uploadPath);
	    } catch (FileAlreadyExistsException e) {
	        // Directory already exists, no need to recreate it.
	        System.out.print("Directory '{}' already exists, skipping creation.");
	    } catch (IOException e) {
	        // Handle other exceptions that might occur during directory creation.
	        throw new RuntimeException("Failed to initialize upload service", e);
	    }
	}
}