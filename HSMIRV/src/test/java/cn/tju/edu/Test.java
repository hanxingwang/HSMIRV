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
		File TBox = new File("src/main/resources/Ontology/BadFood.owl");
		dFunction bh = new DH();
		fFunction sf = new MF();
		
		Main main = new Main(TBox, bh, sf);
		
		main.setTBox(TBox);
		
		try {
			System.err.println(main.HSMIRV());
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
