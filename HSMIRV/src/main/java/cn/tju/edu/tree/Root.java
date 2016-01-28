package cn.tju.edu.tree;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class Root implements Tree {
	private Tree parent;
	private Tree root;
	
	public Root() {
		this.parent = null;
		this.root = null;
	}

	public Tree getRoot() {
		return root;
	}

	public void setRoot(Tree root) {
		this.root = root;
		this.root.setParent(this);
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public Tree getParent() {
		return this.parent;
	}
	
	public boolean equals(Object object) {
		if(object instanceof Root) {
			Root anotherRoot = (Root)object;
			if(this.getRoot().equals(anotherRoot.getRoot()))
				return true;
			else
				return false;
		} else 
			return false;
	}
	
	public Root clone() {
		Root newRoot = new Root();
		
		newRoot.setParent(null);
//		newRoot.setRoot(this.root.clone());
		
		return newRoot;
	}

	public void visite(Visitor visitor) {
		// TODO Auto-generated method stub
		this.getRoot().visite(visitor);
	}
}
