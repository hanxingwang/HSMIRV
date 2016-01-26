package cn.tju.edu;

import java.util.ArrayList;

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
