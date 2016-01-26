package cn.tju.edu;

public class DD implements dFunction {

	public int getDistance(Type t1, Type t2) {
		// TODO Auto-generated method stub
		if(t1.equals(t2))
			return 0;
		else 
			return 1;
	}
	
}
