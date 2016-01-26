package cn.tju.edu;

import java.util.ArrayList;

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
