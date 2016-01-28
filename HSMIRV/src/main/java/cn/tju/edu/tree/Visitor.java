package cn.tju.edu.tree;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class Visitor implements Tree {
	private Set<OWLClassExpression> classes;

	public Visitor(Set<OWLClassExpression> classes) {
		this.classes = classes;
	}
	
	public void setParent(Tree parent) {
		// TODO Auto-generated method stub
		
	}

	public Tree getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public Tree clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public void visite(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	public void visite(Leave leave) {
		if(!classes.contains(leave.getArgs())) {
			classes.add(leave.getArgs());
		}
	}
	
	public void visite(BinaryOperator binaryOperator) {
		binaryOperator.getLeftChild().visite(this);
		binaryOperator.getRightChild().visite(this);
	}
	
	public void visite(DisjointiveOperator disjointiveOperator) {
		disjointiveOperator.getLeftChild().visite(this);
		disjointiveOperator.getRightChild().visite(this);
	}

	public void visite(ConjunctiveOperator conjunctiveOperator) {
		conjunctiveOperator.getLeftChild().visite(this);
		conjunctiveOperator.getRightChild().visite(this);
	}
}
