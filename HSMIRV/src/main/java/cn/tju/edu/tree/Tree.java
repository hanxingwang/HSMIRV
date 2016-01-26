package cn.tju.edu.tree;

public interface Tree {	
	public void setParent(Tree parent);
	public Tree getParent();
	
	public boolean equals(Object object);
	public Tree clone();
	public void visite(Visitor visitor);
}
