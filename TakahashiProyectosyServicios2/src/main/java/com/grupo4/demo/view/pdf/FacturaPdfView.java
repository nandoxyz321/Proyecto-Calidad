package com.grupo4.demo.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.grupo4.demo.models.entity.DetalleGuia;
import com.grupo4.demo.models.entity.Guia;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("obras/verGuia")
public class FacturaPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Guia guia = (Guia) model.get("guia"); 
		
		PdfPTable table =new PdfPTable(1);
		table.setSpacingAfter(20);
		
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase("detalles de la guia"));
		cell.setBackgroundColor(new Color(185,29,23));
		cell.setPadding(8f);
		
		table.addCell(cell);
		table.addCell("código: "+guia.getCodigoGuia());
		table.addCell("destino: "+guia.getDestino());
		table.addCell("fecha de salida: "+guia.getFechaSalida().toString());
		table.addCell("fecha de llegada: "+guia.getFechallegada().toString());
		
		
		PdfPTable table2 =new PdfPTable(1);
		cell = new PdfPCell(new Phrase("detalles del la obra"));
		cell.setBackgroundColor(new Color(229,229,255));
		cell.setPadding(8f);
		
		table2.setSpacingAfter(20);
		table2.addCell(cell);
		table2.addCell("obra: "+guia.getObra().getNombreProyecto());
		table2.addCell("direccion: "+guia.getObra().getDireccion());
		table2.addCell("razon social: "+guia.getObra().getRazonSocialCliente());
		
		PdfPTable table3 =new PdfPTable(1);
		table3.setSpacingAfter(20);
		cell = new PdfPCell(new Phrase("detalles del trabajador"));
		cell.setBackgroundColor(new Color(242,0,0));
		cell.setPadding(8f);
		
		table3.addCell(cell);
		table3.addCell("trabajador encargado: "+guia.getTrabajador().getNombre()+" "+guia.getTrabajador().getApellido());
		table3.addCell("rol del trabajador: "+guia.getTrabajador().getRol());
		
		PdfPTable table5 = new PdfPTable(1);
		cell = new PdfPCell(new Phrase("artículos seleccionados en la guia"));
		cell.setBackgroundColor(new Color(102,102,102));
		cell.setPadding(8f);
		
		table5.addCell(cell);
		PdfPTable table4 = new PdfPTable(3);
		table4.setWidths(new float[] {1,1.5f,1});
		table4.addCell("id");
		table4.addCell("nombre");
		table4.addCell("cantidad");
		
		for(DetalleGuia detalle: guia.getDetalles()) {
			table4.addCell(detalle.getIdDetalleGuia().toString());
			table4.addCell(detalle.getArticulo().getNombre());
			table4.addCell(String.valueOf(detalle.getCantidad()));
		}
		
		cell = new PdfPCell();
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		table4.addCell(cell);
		
		document.add(table);
		document.add(table2);
		document.add(table3);
		document.add(table5);
		document.add(table4);
		
	}
	
	
	
}
