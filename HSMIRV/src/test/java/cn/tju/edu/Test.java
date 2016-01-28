package cn.tju.edu;

import java.io.File;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class Test {	
	@org.junit.Test
	public void testLoadOntology() {
		File TBox = new File("src/main/resources/Ontology/simpleOnto2.owl");
		dFunction bh = new DD();
		fFunction sf = new SF();
		
		Main main = new Main(TBox, bh, sf);
		
		main.setTBox(TBox);
		
		try {
			main.HSMIRV();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
