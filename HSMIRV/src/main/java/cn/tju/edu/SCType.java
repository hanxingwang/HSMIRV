package cn.tju.edu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SCType {
	private List<CType> scTypes;
	
	public SCType() {
		scTypes = new ArrayList<CType>();
	}

	public List<CType> getScTypes() {
		return scTypes;
	}

	public void setScTypes(ArrayList<CType> scTypes) {
		this.scTypes = scTypes;
	}
	
	public void add(ArrayList<CType> cTypes) {
		for (CType ctype : cTypes) {
			// if(!this.cTypes.contains(type))
			// this.cTypes.add(type);
			boolean flag = false;
			for (CType cty : scTypes) {
				if (cty.equals(ctype)) {
					flag = true;
					break;
				}
			}

			if (!flag) {
				scTypes.add(ctype);
			}
		}
	}

	public void add(CType ctye) {
		boolean flag = false;
		for (CType cty : scTypes) {
			if (cty.equals(ctye)) {
				flag = true;
				break;
			}
		}

		if (!flag) {
			scTypes.add(ctye);
		}

	}
	
	public CType get(int i) {
		return this.getScTypes().get(i);
	}
}
