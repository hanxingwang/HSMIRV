package cn.tju.edu;

import java.io.File;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class Test {	
	@org.junit.Test
	public void testLoadOntology() {
		File TBox = new File("src/main/resources/Ontology/simpleOnto1.owl");
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
