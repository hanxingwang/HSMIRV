package cn.tju.edu;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;


/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class DH implements dFunction {

//	public int getDistance(Set<OWLClassExpression> t1, Set<OWLClassExpression> t2) {
//		// TODO Auto-generated method stub
//		int sum = 0;
//		
//		for(OWLEntity t : t1) {
//			if(!t2.contains(t))
//				sum ++;
//		}
//		
//		for(OWLEntity t : t2) {
//			if(!t1.contains(t))
//				sum ++;
//		}
//		
//		return sum;
//	}

	public int getDistance(Type t1, Type t2) {
		// TODO Auto-generated method stub
		int sum = 0;
		
		Set<OWLClassExpression> type1 = t1.getTypes();
		Set<OWLClassExpression> type2 = t2.getTypes();
		
		for(OWLClassExpression type : type1) {
			if(!type2.contains(type))
				sum ++;
		}
		
		for(OWLClassExpression type : type2) {
			if(!type1.contains(type))
				sum ++;
		}
		
		return sum;
	}

}
