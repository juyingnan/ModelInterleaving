package path.bigraph;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerFactory; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult; 
import org.w3c.dom.Document; 
import org.w3c.dom.Element; 

public class PatternsWriter { 
	private Document document; 
	private Element patternRelationship;
	private Element interestedPatterns;
	private String outputPath; 
	
	public PatternsWriter(String outputPath) { 
		this.outputPath = outputPath;
		this.document = null;
		try {
			this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			this.document.setXmlVersion("1.0");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Element patterns = this.document.createElement("Patterns");
		patterns.setAttribute("name", "Patterns For BigMC");
		this.patternRelationship = this.document.createElement("PatternRelationship");
		this.interestedPatterns = this.document.createElement("IntrestedPatterns");
		patterns.appendChild(this.patternRelationship);
		patterns.appendChild(this.interestedPatterns);
		this.document.appendChild(patterns);
	} 
	
	public void addNextPattern(String currentPattern, String nextPattern){
		Element currentE = this.document.createElement("Pattern");
		currentE.setAttribute("details", currentPattern);
		Element nextE = this.document.createElement("NextPattern");
		nextE.setAttribute("details", nextPattern);
		currentE.appendChild(nextE);
		this.patternRelationship.appendChild(currentE);
	}
	
	public void addInterestedPattern(String pattern){
		Element intrestedPattern = this.document.createElement("IntrestedPattern");
		intrestedPattern.setAttribute("details", pattern);
		this.interestedPatterns.appendChild(intrestedPattern);
	}
	
	public void savePatternFile(){
		try { 
			TransformerFactory transformerFactory = TransformerFactory.newInstance(); 
			Transformer transformer = transformerFactory.newTransformer(); 
			DOMSource source = new DOMSource(this.document); 
			StreamResult result = new StreamResult(new java.io.File(this.outputPath));
			transformer.transform(source, result); 
		}catch(Exception e) { 
			System.out.println("PatternsWriter savePatternFile Error£º " + e.getMessage()); 
		}
	}
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Element getPatternRelationship() {
		return patternRelationship;
	}

	public void setPatternRelationship(Element patternRelationship) {
		this.patternRelationship = patternRelationship;
	}

	public Element getInterestedPatterns() {
		return interestedPatterns;
	}

	public void setInterestedPatterns(Element interestedPatterns) {
		this.interestedPatterns = interestedPatterns;
	}
} 
