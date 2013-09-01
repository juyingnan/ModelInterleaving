package parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLParser {
	private DocumentBuilderFactory documentBuilderFactory; 
	private DocumentBuilder documentBuilder; 
	private Document document;
	
	public XMLParser(String filepath){
		try { 
			this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
			this.documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
			this.document = this.documentBuilder.parse(filepath);
		}catch(Exception e) { 
			System.out.println("XMLParser init Error£º " + e.getMessage()); 
		}
	}
	
	public DocumentBuilderFactory getDocumentBuilderFactory() {
		return documentBuilderFactory;
	}
	
	public void setDocumentBuilderFactory(
			DocumentBuilderFactory documentBuilderFactory) {
		this.documentBuilderFactory = documentBuilderFactory;
	}
	
	public DocumentBuilder getDocumentBuilder() {
		return documentBuilder;
	}
	
	public void setDocumentBuilder(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	} 
}
