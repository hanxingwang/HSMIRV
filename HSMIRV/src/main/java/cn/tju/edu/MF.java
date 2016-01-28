package cn.tju.edu;

import java.util.ArrayList;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class MF implements fFunction {

	public int getAggregation(ArrayList<Integer> integers) {
		// TODO Auto-generated method stub
		int max = 0;
		
		for(Integer integer : integers) {
			if(integer > max)
				max = integer;
		}
		
		return max;
	}

}
