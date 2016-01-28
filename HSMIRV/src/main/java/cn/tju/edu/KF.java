package cn.tju.edu;

import java.util.ArrayList;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class KF implements fFunction {
	private float k;
	
	public KF(float k) {
		this.setK(k);
	}

	public int getAggregation(ArrayList<Integer> integers) {
		// TODO Auto-generated method stub
		int zeroLength = 0;
		int length = integers.size();
		
		for(Integer integer : integers) {
			if(integer == 0)
				zeroLength ++;
		}
		
		if(zeroLength == length) 
			return 0;
		
		if((zeroLength > k*length && zeroLength < length) || zeroLength == k*length)
			return 1;
		else 
			return 2;
	}

	public float getK() {
		return k;
	}

	public void setK(float k) {
		this.k = k;
	}
	
	public KF() {
		k = 0.5f;
	}

}
