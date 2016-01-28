package cn.tju.edu.tree;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class DisjointiveOperator extends BinaryOperator{
	public boolean equals(Object object) {
		if(object instanceof DisjointiveOperator) {
			DisjointiveOperator another = (DisjointiveOperator)object;
			
			if(this.getLeftChild().equals(another.getLeftChild()) && this.getRightChild().equals(another.getRightChild()))
				return true;
			else 
				return false;
		} else
			return false;
	}
	
	public DisjointiveOperator clone() {
		DisjointiveOperator newDis = new DisjointiveOperator();
		
		newDis.setLeftChild(this.getLeftChild().clone());
		newDis.setRightChild(this.getRightChild().clone());
		newDis.setParent(this.getParent());
		
		return newDis;
	}
	
	public void visite(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visite(this);
	}
}
