package org.ektqa.seguridadbus.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ektqa.commons.TestProperties;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.ektqa.commons.SystemConstants;

public class SeguridadPdfReporter {
	private Map<String,String> testImgs;
	private Map<String,String> errorItems; //Key: Error description / Value: Error Image
	private PdfWriter writer;
	
	private List<String> functionalTests;
//	private List<Map<String,String>> dataSet;
	private String testTitle;
	private String fileName;
	private String imgsGeneratedPath;
	private TestProperties testProps;
	private SystemConstants sysConst;
	
	private Font standardFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	//private Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private Font subtitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private Font whiteSubtFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.WHITE);
	private Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private BaseColor sectionTapeBg = new BaseColor(114, 163, 118);
	public SeguridadPdfReporter() {
		// TODO Auto-generated constructor stub
	}
	
	public SeguridadPdfReporter(String tTit, TestProperties tProp, List<String> fTests, List<Map<String,String>> dset) {
		this.functionalTests = fTests;
//		this.dataSet = dset;
		this.testTitle = tTit;
		this.testProps = tProp;
		//Generates File Name under predefined Format as:
		//	|->'WebApp'+ServiceName+'TestResults.pdf'
		testImgs = new HashMap<String, String>();
		errorItems = new HashMap<String, String>();
		this.fileName = "WebApp"+tTit.split("-")[0].split(":")[1].trim() + "TestResults.pdf";
	}

	public void generatePdfForTestResults() throws Exception{
		Document pdfDoc = new Document(PageSize.A4);
		FileOutputStream  stream = new FileOutputStream(this.imgsGeneratedPath+this.fileName);
		writer = PdfWriter.getInstance(pdfDoc, stream);
		writer.setCloseStream(false);
		//Pdf Report FrontPage 
		pdfDoc.open();
		addMetaData(pdfDoc);
		
		pdfDoc.newPage();
		//Test Content
		addIntroduction(pdfDoc);
		addGeneralContent(pdfDoc);
		printTestCases(pdfDoc);
		
//		writer.close();
		printFrontPage(stream, pdfDoc);
		pdfDoc.close();
		writer.close();
		stream.close();
	}
	
	private void printFrontPage(FileOutputStream stream, Document document) throws Exception{
		PdfPTable frontPageTab = new PdfPTable(3);
		frontPageTab.setWidths(new int[]{1,2,2});
		frontPageTab.setWidthPercentage(100);
		
		PdfPCell cell = new PdfPCell(loadGenericImage("LogoProcesosCalidad.png"), true);
		cell.setBorder(Rectangle.NO_BORDER);
		frontPageTab.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		frontPageTab.addCell(cell);
		
		cell  = new PdfPCell(loadGenericImage("LogoAcertumBank.png"), true);
		cell.setBorder(Rectangle.NO_BORDER);
		frontPageTab.addCell(cell);
		document.add(frontPageTab);
		
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		frontPageTab.addCell(cell);
		cell.setFixedHeight(340f);
		frontPageTab = new PdfPTable(1);
		frontPageTab.setWidthPercentage(120);
		cell  = new PdfPCell(loadGenericImage("LogoTituloEncabezado.png"), true);
		cell.setBorder(Rectangle.NO_BORDER);
		frontPageTab.addCell(cell);
		document.add(frontPageTab);
		
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		frontPageTab.addCell(cell);
		cell.setFixedHeight(340f);
		frontPageTab = new PdfPTable(1);
		frontPageTab.setWidthPercentage(120);
		cell  = new PdfPCell(loadGenericImage("LogoPiePagina.png"), true);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setFixedHeight(480f);
		frontPageTab.addCell(cell);
		document.add(frontPageTab);
		
	}
	
	private void addMetaData(Document document) {
		document.addTitle(this.testTitle);
	    document.addSubject("Test Report for WebAppSeguridad");
	    document.addKeywords("Java, PDF, iTextReport");
	    document.addAuthor("Tsune Maldonado");
	    document.addCreator("Tsune Maldonado");
	}
	
	public void addIntroduction(Document document) throws DocumentException, IOException{
		Paragraph introCont = new Paragraph();
		introCont.add(new Chunk("Objetivo", whiteSubtFont).setBackground(sectionTapeBg, 
				2f, 3f, PageSize.A4.getWidth(), 12f));
		addEmptyLine(introCont, 2);
		introCont.setAlignment(Element.ALIGN_JUSTIFIED);
		introCont.setSpacingAfter(50);
		introCont.add(new Paragraph("Este documento tiene como propósito, presentar los resultados de la ejecución "
				+ "del módulo de Alta de Empleados de la Aplicacion SeguridadSwitchPAR", standardFont));
		addEmptyLine(introCont, 2);
		
		introCont.add(new Paragraph("Datos de Ambiente", subtitleFont));
		addEmptyLine(introCont, 2);
		introCont.add(new Paragraph("-\t URL: "+testProps.getUrl(), standardFont));
		introCont.add(new Paragraph("-\t Usuario: "+testProps.getUserName(), standardFont));
		introCont.add(new Paragraph("-\t Browser: "+testProps.getBrowserPath(), standardFont));
		introCont.add(new Paragraph("-\t Version: "+testProps.getBrowVersion(), standardFont));
		introCont.add(new Paragraph("-\t Sistema Operativo: "+testProps.getOperativeSystem(), standardFont));
		introCont.add(new Paragraph("-\t Hora Inicio Prueba: "+testProps.getCurrentTime(), standardFont));
		addEmptyLine(introCont, 2);
		
		document.add(introCont);
		
//		Rectangle linkLocation = new Rectangle(img.getAbsoluteX(), img.getAbsoluteY(), 
//			img.getAbsoluteX() + img.getScaledWidth(), img.getAbsoluteY()+img.getScaledHeight());
//		PdfAnnotation link = PdfAnnotation.createLink(this.writer, linkLocation, 
//		PdfAnnotation.HIGHLIGHT_INVERT, document.getPageNumber(), new PdfDestination(1));
//		this.writer.addAnnotation(link, 1);
	}
	
	public void addGeneralContent(Document document) throws DocumentException{
		Paragraph generalCont = new Paragraph();
		
		generalCont.add(new Chunk("Casos de Pruebas Funcionales - General", whiteSubtFont).setBackground(sectionTapeBg, 
				2f, 3f, PageSize.A4.getWidth(), 12f));
		addEmptyLine(generalCont, 1);
		for(String item : functionalTests){
			generalCont.add(new Paragraph("-\t"+item, standardFont));
		}
		
		generalCont.add(new Paragraph("Captura de Pantalla Login", subtitleFont));
		addEmptyLine(generalCont, 1);
		generalCont.add((addImage(loadGenericImage(sysConst.LOGIMG_NAME + sysConst.PNG_EXT), true)));
		Chunk linkToImg = new Chunk("Ver Fuente");
		linkToImg.setAction(new PdfAction(this.imgsGeneratedPath 
				+ sysConst.SCRNSHT_DESTINY 
				+ sysConst.LOGIMG_NAME 
				+ sysConst.PNG_EXT, "External File"));
		generalCont.add(linkToImg);
		linkToImg.getAttributes();
		addEmptyLine(generalCont, 2);
		
		document.add(generalCont);
		if(writer.getPageNumber() < 2){
			document.newPage();
		}
		
		document.add(new Chunk("Resultados Ciclo de Pruebas Funcionales", whiteSubtFont).setBackground(sectionTapeBg, 
				2f, 3f, PageSize.A4.getWidth(), 12f));
	}
	
	private void printTestCases(Document document) throws DocumentException, IOException{
		Paragraph testItem;
		int c = 0;
		for(Map.Entry<String, String> item : this.testImgs.entrySet()){
			testItem = new Paragraph();
			testItem.add(new Paragraph("Entrada de Registro: " 
				+ item.getKey().substring(sysConst.IMG_PREFIX.length(), item.getKey().length()), 
				standardFont));
			addEmptyLine(testItem, 1);
			testItem.add(addImage(loadGenericImage(item.getValue()), true));
			Chunk linkToImg = new Chunk("Ver Fuente");
			linkToImg.setAction(new PdfAction(this.imgsGeneratedPath + sysConst.SCRNSHT_DESTINY + item.getValue()));
			testItem.add(linkToImg);
			addEmptyLine(testItem, 2);
			c++;
			if((float)(c % 2) == 0.0f){
				document.newPage();
			}
			document.add(testItem);
		}
	}
	
	private PdfPTable addImage(Image img, boolean isTestImage){
		PdfPTable imgTab = new PdfPTable(1);
		if(img != null){
			PdfPCell cell = new PdfPCell(img, true);
			cell.setBorder(Rectangle.NO_BORDER);
			imgTab.addCell(cell);
		} else{
			imgTab.addCell(new Paragraph("Invalid Image. See IOException at Log", this.redFont));
		}
		imgTab.setHorizontalAlignment(Element.ALIGN_CENTER);
		if(isTestImage){
			imgTab.setWidthPercentage(95);
		} else{
			imgTab.setWidthPercentage(78);
		}
		return imgTab;
	}
	
	private Image loadGenericImage(String imageName){
		try{
			return Image.getInstance(this.imgsGeneratedPath + sysConst.SCRNSHT_DESTINY + imageName);
		} catch(IOException ioEx){
			System.out.println("Exception at WebAppSeguridadPdfWorker/loadImage >>"+ioEx);
			return null;
		} catch(BadElementException beEx){
			System.out.println("Exception at WebAppSeguridadPdfWorker/loadImage >>"+beEx);
			return null;
		}
	}
	
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}	
	
	public void pushErrorDetails(String imgPath, String errorDesc){
		errorItems.put(errorDesc, imgPath);
	}
	
	public void pushTestImage(String key, String imgPath){
		testImgs.put(key, imgPath);
	}
	
	public void printErrors(){
		for(Map.Entry<String, String> errorItem: errorItems.entrySet()){
			System.out.println("Image Generated at: " + this.imgsGeneratedPath + errorItem.getKey()+errorItem.getValue());
		}
		for(Map.Entry<String, String> imgItem: testImgs.entrySet()){
			System.out.println("Image Generated at: " + this.imgsGeneratedPath + imgItem.getValue());
		}
	}

	public void setImgsGeneratedPath(String imgsGanreatedPath) {
		imgsGeneratedPath = imgsGanreatedPath;
	}
}
