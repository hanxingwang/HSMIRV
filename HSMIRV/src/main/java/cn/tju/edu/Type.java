package cn.tju.edu;


import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class Type {
	private Set<OWLClassExpression> types;
	
	public Type() {
		types = new HashSet<OWLClassExpression>();
	}
	
	public String toString() {
		return types.toString();
	}

	public Set<OWLClassExpression> getTypes() {
		return types;
	}

	public void setTypes(Set<OWLClassExpression> types) {
		this.types = types;
	}
	
	public void add(OWLClassExpression entity) {
		if(!types.contains(entity))
			types.add(entity);
	}
	
	public boolean equals(Object object) {
		if(object instanceof Type) {
			Type newType = (Type)object;
			Set<OWLClassExpression> list = newType.getTypes();
			
			if(list.size() != this.getTypes().size()) {
				return false;
			}
			
			if(list.containsAll(this.getTypes()) && this.getTypes().containsAll(list))
				return true;
			else
				return false;
		} else 
			return false;
	}
}
