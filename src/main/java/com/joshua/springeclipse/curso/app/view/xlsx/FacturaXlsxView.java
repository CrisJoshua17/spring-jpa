package com.joshua.springeclipse.curso.app.view.xlsx;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.joshua.springeclipse.curso.app.models.entity.Factura;
import com.joshua.springeclipse.curso.app.models.entity.ItemFactura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

    	
    	response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
    	
    	
        // Asegúrate de que `factura` esté correctamente casteada
        Factura factura = (Factura) model.get("factura");

        // Usa el Workbook proporcionado (que debería ser XSSFWorkbook)
        Sheet sheet = workbook.createSheet("Factura");
        
        MessageSourceAccessor mensajes = getMessageSourceAccessor();
        

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));

        // Datos del cliente
        sheet.createRow(1).createCell(0).setCellValue(factura.getCliente().getName() + " " + factura.getCliente().getLastName());
        sheet.createRow(2).createCell(0).setCellValue(factura.getCliente().getEmail());

        // Datos de la factura
        sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura"));
        sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId());
        sheet.createRow(6).createCell(0).setCellValue("Descripcion: " + factura.getDescripcion());
        sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreateAt().toString()); // Asegúrate de formatear la fecha correctamente
    
    
        CellStyle theaderStyle = workbook.createCellStyle();
        CellStyle tbodyStyle = workbook.createCellStyle();
        
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);
        
        
        
        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue("Producto");
        header.createCell(1).setCellValue("Precio");
        header.createCell(2).setCellValue("Cantidad");
        header.createCell(3).setCellValue("Total");
        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);
        
        
        int rownum =10;
        
        for(ItemFactura item : factura.getItems()) {
        	
        	Row fila = sheet.createRow(rownum++);
        Cell cell = fila.createCell(0);
        	cell.setCellValue(item.getProducto().getNombre());
        	cell.setCellStyle(tbodyStyle);
        	cell = fila.createCell(1);
        	cell.setCellValue(item.getProducto().getPrecio());
        	cell.setCellStyle(tbodyStyle);
        	cell = fila.createCell(2);
        	cell.setCellValue(item.getCantidad());
        	cell.setCellStyle(tbodyStyle);
        	cell = fila.createCell(2);
        	cell.setCellValue(item.calcularImporte());
        	cell.setCellStyle(tbodyStyle);
        }
        
        
        Row filaTotal = sheet.createRow(rownum);
        filaTotal.createCell(2).setCellValue("Gran Total: ");
        filaTotal.createCell(3).setCellValue(factura.getTotal());
    }


    
	
	}

	
	

