package com.joshua.springeclipse.curso.app.view.csv;

import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.joshua.springeclipse.curso.app.models.entity.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("listar")
public class ClienteCsvView extends AbstractView {

	
	
	public ClienteCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {

		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename= \"clientes.csv\"");
		response.setContentType(getContentType());
			Page<Cliente> clientes =(Page<Cliente>) model.get("clientes");
	
	
			
	
			ICsvBeanWriter  beanWriter = new CsvBeanWriter(response.getWriter(), 
					CsvPreference.STANDARD_PREFERENCE);
			
			String[] header = {"id","name","lastName","email","createAt"};
			beanWriter.writeHeader(header);
			
			for(Cliente cliente : clientes) {
				beanWriter.write(cliente, header);
			}
			
			beanWriter.close();
	}
	

}
