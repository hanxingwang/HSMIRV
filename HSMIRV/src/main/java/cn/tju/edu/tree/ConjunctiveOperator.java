package cn.tju.edu.tree;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class ConjunctiveOperator extends BinaryOperator {

	public boolean equals(Object object) {
		if(object instanceof ConjunctiveOperator) {
			ConjunctiveOperator another = (ConjunctiveOperator)object;
			
			if(this.getLeftChild().equals(another.getLeftChild()) && this.getRightChild().equals(another.getRightChild()))
				return true;
			else 
				return false;
		} else
			return false;
	}
	
	public ConjunctiveOperator clone() {
		ConjunctiveOperator newCon = new ConjunctiveOperator();
		
		newCon.setLeftChild(this.getLeftChild().clone());
		newCon.setRightChild(this.getRightChild().clone());
		newCon.setParent(this.getParent());
		
		return newCon;
	}
	
	public void visite(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visite(this);
	}


}
