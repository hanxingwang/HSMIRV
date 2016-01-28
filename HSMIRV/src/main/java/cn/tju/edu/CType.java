package cn.tju.edu;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;

import uk.ac.manchester.cs.owl.owlapi.OWLObjectInverseOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class CType {
	private Set<Type> cTypes;

	public CType() {
		this.cTypes = new HashSet<Type>();
	}

	public Set<Type> getcTypes() {
		return cTypes;
	}

	public void setcTypes(Set<Type> cTypes) {
		this.cTypes = cTypes;
	}

	public String toString() {
		return cTypes.toString();
	}
	
	public boolean containsAll(CType another) {
		Set<Type> anotherTypes = another.getcTypes();
		
		for(Type type : anotherTypes) {
//			if(!this.getcTypes().contains(type))
//				return false;
			boolean flag = false;
			
			for(Type ty : this.getcTypes()) {
				if(type.equals(ty)) {
					flag = true;
					break;
				}
			}
			
			if(!flag)
				return false;
			
		}
		
		return true;
	}
	
	public CType getContainProperty(OWLClassExpression property) {
		CType cType = new CType();
		Set<Type> types = new HashSet<Type>();
		
		for(Type type : this.getcTypes()) {
			if(type.getTypes().contains(property)) {
				types.add(type);
			}
		}
		
		cType.addTypes(types);
		
		return cType;
	}

	public boolean isRCSatisfiable() {
		Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();

		for (Type type : cTypes) {
			for (OWLClassExpression cls : type.getTypes()) {
				if (!classes.contains(cls)) {
					classes.add(cls);
				}
			}
		}

		for (OWLClassExpression cls : classes) {
			if (cls instanceof OWLObjectSomeValuesFromImpl) {
				OWLObjectSomeValuesFromImpl someValuesFrom = (OWLObjectSomeValuesFromImpl) cls;

				OWLObjectSomeValuesFromImpl newSomeValuesFrom = null;
				if (someValuesFrom.getProperty() instanceof OWLObjectPropertyImpl) {
					newSomeValuesFrom = new OWLObjectSomeValuesFromImpl(
							someValuesFrom.getProperty().getInverseProperty(), someValuesFrom.getFiller());
				}

				if (someValuesFrom.getProperty() instanceof OWLObjectInverseOfImpl) {
					OWLObjectInverseOfImpl inverseProperty = (OWLObjectInverseOfImpl) someValuesFrom.getProperty();
					newSomeValuesFrom = new OWLObjectSomeValuesFromImpl(inverseProperty.getInverseProperty(),
							someValuesFrom.getFiller());
				}

				if (!classes.contains(newSomeValuesFrom))
					return false;
			}
		}

		return true;
	}
	
	public CType minus(CType mincType) {
		CType cType = new CType();
		
		Set<Type> tys1 = this.getcTypes();
		Set<Type> tys2 = mincType.getcTypes();
		
		Set<Type> minTys = new HashSet<Type>();
		
		for(Type type : tys1) {
			if(!tys2.contains(type))
				minTys.add(type);
		}
		
		cType.addTypes(minTys);
		
		return cType;
	}
	
	public Type getRcType() {
		Type rc = new Type();
		
		Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();

		for (Type type : cTypes) {
			for (OWLClassExpression cls : type.getTypes()) {
				if (!classes.contains(cls)) {
					classes.add(cls);
				}
			}
		}

		for (OWLClassExpression cls : classes) {
			if (cls instanceof OWLObjectSomeValuesFromImpl) {
				OWLObjectSomeValuesFromImpl someValuesFrom = (OWLObjectSomeValuesFromImpl) cls;

				OWLObjectSomeValuesFromImpl newSomeValuesFrom = null;
				if (someValuesFrom.getProperty() instanceof OWLObjectPropertyImpl) {
					newSomeValuesFrom = new OWLObjectSomeValuesFromImpl(
							someValuesFrom.getProperty().getInverseProperty(), someValuesFrom.getFiller());
				}

				if (someValuesFrom.getProperty() instanceof OWLObjectInverseOfImpl) {
					OWLObjectInverseOfImpl inverseProperty = (OWLObjectInverseOfImpl) someValuesFrom.getProperty();
					newSomeValuesFrom = new OWLObjectSomeValuesFromImpl(inverseProperty.getInverseProperty(),
							someValuesFrom.getFiller());
				}

				if (!classes.contains(newSomeValuesFrom))
					rc.add(newSomeValuesFrom);
			}
		}
		
		return rc;
	}

	public void addTypes(Set<Type> types) {
		for (Type type : types) {
			// if(!this.cTypes.contains(type))
			// this.cTypes.add(type);
			boolean flag = false;
			for (Type ty : cTypes) {
				if (ty.equals(type)) {
					flag = true;
					break;
				}
			}

			if (!flag) {
				cTypes.add(type);
			}
		}
	}

	public void addTypes(Type type) {
		boolean flag = false;
		for (Type ty : cTypes) {
			if (ty.equals(type)) {
				flag = true;
				break;
			}
		}

		if (!flag) {
			cTypes.add(type);
		}

	}

	public boolean equals(Object object) {
		if (object instanceof CType) {
			CType another = (CType) object;
			Set<Type> types = another.getcTypes();

			if (types.size() != this.cTypes.size())
				return false;

			if (types.containsAll(this.getcTypes()) && this.getcTypes().containsAll(types))
				return true;
			else
				return false;
		} else
			return false;
	}
}
