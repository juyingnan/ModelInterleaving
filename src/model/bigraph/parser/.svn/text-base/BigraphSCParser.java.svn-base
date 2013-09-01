package model.bigraph.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.bigraph.Constraint;
import model.bigraph.Control;
import model.bigraph.Sorting;
import model.bigraph.SortingConstraint;
//import model.interleaving.util.ConfigInfoXMLParser;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class BigraphSCParser {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SortingConstraint parserXml(String filePath) { 
		SortingConstraint sc = new SortingConstraint();
		ArrayList<Sorting> srtList = new ArrayList<Sorting>();
		ArrayList<Constraint> cstList = new ArrayList<Constraint>();
		
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc;
		File file = new File(filePath);
		if(!file.exists()){
			return null;
		}
		try {
			doc = reader.read(file);
			List<org.dom4j.Node> sortings = doc.selectNodes("/Sortings-Constraints/Sorting");
			for(org.dom4j.Node sorting : sortings){
				Sorting s = new Sorting();
				ArrayList<Control> clist = new ArrayList<Control>();
				String name = sorting.selectSingleNode("./@name").getStringValue();
				s.setName(name);
				List controlNames = sorting.selectNodes("./Controls/Control/@name");
				Iterator cs = controlNames.iterator();
				while(cs.hasNext()){
					Control c = new Control();
					Attribute controlName = (Attribute)cs.next();
					String cns = controlName.getValue().toString();
					c.setName(cns);
					c.setSorting(s);
					clist.add(c);
				}
				s.setControls(clist);
				srtList.add(s);
			}
			
			List<org.dom4j.Node> constraints = doc.selectNodes("/Sortings-Constraints/Constraints/Constraint/@pattern");
			Iterator itc = constraints.iterator();
			while(itc.hasNext()){
				Constraint c = new Constraint();
				Attribute cstName = (Attribute)itc.next();
				String csts = cstName.getValue().toString();
				c.setName(csts);
				cstList.add(c);
			}
			sc.setSortings(srtList);
			sc.setConstraints(cstList);
			return sc;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* Test for parsing SortingConstraint
	public static void main(String[] args){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser("doc/ConfigInfo.xml");
		String scPath = configParser.getSortingConstraintPath();
		BigraphSCParser scParser = new BigraphSCParser();
		SortingConstraint sc = scParser.parserXml(scPath);
		for(Sorting s : sc.getSortings()){
			System.out.print("sorting " +  s.getName() + " : ");
			for(Control c : s.getControls()){
				System.out.print(c.getName() + " ");
			}
			System.out.print("\n");
		}
		for(Constraint c : sc.getConstraints()){
			System.out.println("Constraint " +  c.getName());
		}
	}
	*/
}
