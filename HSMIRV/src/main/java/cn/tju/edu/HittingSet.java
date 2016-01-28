package cn.tju.edu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.semanticweb.owlapi.model.OWLEntity;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */

public class HittingSet {
	public ArrayList<ArrayList<OWLEntity>> getHittingSet(CopyOnWriteArrayList<ArrayList<OWLEntity>> sourceSet) {
		ArrayList<ArrayList<OWLEntity>> hittingSet = new ArrayList<ArrayList<OWLEntity>>();

		ArrayList<OWLEntity> entities = new ArrayList<OWLEntity>();

		for (ArrayList<OWLEntity> tempEntities : sourceSet) {
			for (OWLEntity tempEntity : tempEntities) {
				if (!entities.contains(tempEntity))
					entities.add(tempEntity);
			}
		}

		CopyOnWriteArrayList<ArrayList<OWLEntity>> univeralSet = new CopyOnWriteArrayList<ArrayList<OWLEntity>>();

		int length = 1;
		for (int i = entities.size(); i > 0; i--) {
			length *= 2;
		}

		Iterator<OWLEntity> iterator = null;
		for (int i = 1; i < length; i++) {
			ArrayList<OWLEntity> temp = new ArrayList<OWLEntity>();
			iterator = entities.iterator();

			int tempInt = i;
			while (iterator.hasNext()) {
				OWLEntity entity = iterator.next();
				if ((tempInt & 1) == 1) {
					temp.add(entity);
				}
				tempInt = (tempInt >> 1);
			}
			univeralSet.add(temp);
		}

		for (ArrayList<OWLEntity> tempEntities : univeralSet) {
			boolean flag = true;
			for (ArrayList<OWLEntity> list : sourceSet) {
				if (!hasCommon(tempEntities, list))
					flag = false;
			}

			if (flag) {
				removeAllParentSet(univeralSet, tempEntities);
			} else {
				univeralSet.remove(tempEntities);
			}
		}

		for(ArrayList<OWLEntity> tempEntities : univeralSet) {
			hittingSet.add(tempEntities);
		}
		
		return hittingSet;
	}

	public void removeAllParentSet(CopyOnWriteArrayList<ArrayList<OWLEntity>> univeralSet, ArrayList<OWLEntity> sonSet) {
		for(ArrayList<OWLEntity> tempEntities : univeralSet) {
			if(isParent(sonSet, tempEntities))
				univeralSet.remove(tempEntities);
		}
	}
	
	public boolean isParent(ArrayList<OWLEntity> sonSet, ArrayList<OWLEntity> parentSet) {
		if(sonSet.size() == parentSet.size())
			return false;
		else if(parentSet.containsAll(sonSet))
			return true;
		else {
			return false;
		}		
	}

	public boolean hasCommon(ArrayList<OWLEntity> list1, ArrayList<OWLEntity> list2) {
		for (OWLEntity entity : list1) {
			if (list2.contains(entity)) {
				return true;
			}
		}

		return false;
	}
}
