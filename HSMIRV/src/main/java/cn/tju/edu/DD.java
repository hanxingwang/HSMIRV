package cn.tju.edu;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */
public class DD implements dFunction {

	public int getDistance(Type t1, Type t2) {
		// TODO Auto-generated method stub
		if(t1.equals(t2))
			return 0;
		else 
			return 1;
	}
	
}
