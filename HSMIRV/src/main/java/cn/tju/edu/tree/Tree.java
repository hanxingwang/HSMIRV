package cn.tju.edu.tree;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public interface Tree {	
	public void setParent(Tree parent);
	public Tree getParent();
	
	public boolean equals(Object object);
	public Tree clone();
	public void visite(Visitor visitor);
}
