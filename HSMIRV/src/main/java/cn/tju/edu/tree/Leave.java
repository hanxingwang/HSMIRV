package cn.tju.edu.tree;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class Leave implements Tree{
	Tree parent;
	OWLClassExpression args;

	public OWLClassExpression getArgs() {
		return args;
	}

	public void setArgs(OWLClassExpression args) {
		this.args = args;
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public Tree getParent() {
		return this.parent;
	}
	
	public boolean equal(Object object) {
		if(object instanceof Leave) {
			Leave anotherLeave = (Leave)object;
			
			if(this.getArgs().equals(anotherLeave.getArgs()))
				return true;
			else 
				return false;
		} else 
			return false;
	}
	
	public Leave clone() {
		Leave newLeave = new Leave();
		newLeave.setParent(this.parent);
		
		if(this.args instanceof OWLClassImpl) {
			OWLClassImpl oldArgs = (OWLClassImpl)this.args;
			OWLClassImpl newArgs = new OWLClassImpl(oldArgs.getIRI());
			
			newLeave.setArgs(newArgs);
		}
		
		if(this.args instanceof OWLObjectComplementOfImpl) {
			OWLObjectComplementOfImpl oldArgs = (OWLObjectComplementOfImpl)this.args;
			OWLClassImpl newClassImpl = (OWLClassImpl)oldArgs.getOperand();
			OWLObjectComplementOfImpl newArgs = (OWLObjectComplementOfImpl)newClassImpl.getObjectComplementOf();
			
			newLeave.setArgs(newArgs);
		}
		
		if(this.args instanceof OWLObjectSomeValuesFromImpl) {
			OWLObjectSomeValuesFromImpl oldArgs = (OWLObjectSomeValuesFromImpl)this.args;
			OWLObjectPropertyExpression property = (OWLObjectPropertyExpression)oldArgs.getProperty();
			
			OWLClassImpl thing = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory().getOWLThing();
			
			OWLObjectSomeValuesFromImpl newArgs = new OWLObjectSomeValuesFromImpl(property, thing);
			
			newLeave.setArgs(newArgs);
		}
		
		if(this.args instanceof OWLObjectAllValuesFromImpl) {
			OWLObjectAllValuesFromImpl oldArgs = (OWLObjectAllValuesFromImpl)this.args;
			OWLObjectPropertyExpression property = (OWLObjectPropertyExpression)oldArgs.getProperty();
			
			OWLClassImpl nothing = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory().getOWLNothing();
			
			OWLObjectAllValuesFromImpl newArgs = new OWLObjectAllValuesFromImpl(property, nothing);
			
			newLeave.setArgs(newArgs);
		}
		
		return newLeave;
	}

	public void visite(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visite(this);
	}
}
