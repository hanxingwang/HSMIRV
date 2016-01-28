package cn.tju.edu.tree;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class BinaryOperator implements Tree{
	private Tree parent;
	private Tree leftChild;
	private Tree rightChild;
	
	public void setLeftChild(Tree leftNode) {
		this.leftChild = leftNode;
		this.leftChild.setParent(this);
	}
	
	public Tree getLeftChild() {
		return this.leftChild;
	}
	
	public void setRightChild(Tree rightNode) {
		this.rightChild = rightNode;
		this.rightChild.setParent(this);
	}
	
	public Tree getRightChild() {
		return this.rightChild;
	}
	
	public Tree getParent() {
		return parent;
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}
	
	public boolean equals(Object object) {
		return false;
	}
	
	public BinaryOperator clone() {
		return null;
	}

	public void visite(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visite(this);
	}
}
