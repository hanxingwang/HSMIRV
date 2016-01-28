package cn.tju.edu;

import java.util.ArrayList;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class SF implements fFunction {

	public int getAggregation(ArrayList<Integer> integers) {
		// TODO Auto-generated method stub
		int sum = 0;
		
		for(Integer integer : integers) {
			sum += integer;
		}
		
		return sum;
	}

}
