package cn.tju.edu;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class OntologyManager {
	public OWLOntology loadOntology(String filePath)
			throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager
				.loadOntologyFromOntologyDocument(new File(filePath));

		return ontology;
	}
	
	public Set<OWLAxiom> getTBox(OWLOntology ontology) {
		return ontology.getTBoxAxioms(null);
	}
	
	public Set<OWLEntity> getTBoxSignature(OWLOntology ontology) throws OWLOntologyCreationException {
		Set<OWLEntity> TBoxSignature = new HashSet<OWLEntity>();
		
		Set<OWLAxiom> TBoxAxiom = ontology.getTBoxAxioms(null);
		
		for(OWLAxiom axiom : TBoxAxiom) {
			Set<OWLEntity> axiomSignature = axiom.getSignature();
			for(OWLEntity entity : axiomSignature) {
				if(!TBoxSignature.contains(entity)) {
					TBoxSignature.add(entity);
				}
			}
		}
		
		return TBoxSignature;
	}
}
