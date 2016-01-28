package cn.tju.edu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import cn.tju.edu.tree.BinaryOperator;
import cn.tju.edu.tree.ConjunctiveOperator;
import cn.tju.edu.tree.DisjointiveOperator;
import cn.tju.edu.tree.Leave;
import cn.tju.edu.tree.Root;
import cn.tju.edu.tree.Tree;
import cn.tju.edu.tree.Visitor;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectInverseOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

/*
 * author Xingwanghan, School of Computer Science and Technology, Tianjin University
 * All right reserved
 * Created on 2016.01
 * 
 */
public class Main {
	private File T;
	private dFunction d;
	private fFunction f;

	public File getTBox() {
		return T;
	}

	public void setTBox(File t) {
		this.T = t;
	}

	public dFunction getBFunction() {
		return d;
	}

	public void setBFunction(dFunction d) {
		this.d = d;
	}

	public fFunction getFFunction() {
		return f;
	}

	public void setFFunction(fFunction f) {
		this.f = f;
	}

	public Main(File T, dFunction d, fFunction f) {
		this.setTBox(T);
		this.setBFunction(d);
		this.setFFunction(f);
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("The argument number is wrong.");
			return;
		}
		
		File TBox = new File(args[0]);
		
		System.out.flush();
		System.err.flush();
		
		for(int i=0; i<6; i++) {
			System.out.println("The ontology is : " + TBox.getName());
			
			dFunction d = null;
			fFunction f = null;
			
			switch (i) {
			case 0:
				d = new DD();
				System.out.println("The distance function is Hamming distance.");
				f = new KF();
				System.out.println("The arrgegation function is k-voting function.");
				break;
			
			case 1:
				d = new DD();
				System.out.println("The distance function is Hamming distance.");
				f = new MF();
				System.out.println("The arrgegation function is maximum function.");
				break;
				
			case 2:
				d = new DD();
				System.out.println("The distance function is Hamming distance.");
				f = new SF();
				System.out.println("The arrgegation function is summation function.");
				break;
				
			case 3:
				d = new DH();
				System.out.println("The distance function is Drastic distance.");
				f = new KF();
				System.out.println("The arrgegation function is k-voting function.");
				break;
			
			case 4:
				d = new DH();
				System.out.println("The distance function is Drastic distance.");
				f = new MF();
				System.out.println("The arrgegation function is maximum function.");
				break;
				
			case 5:
				d = new DH();
				System.out.println("The distance function is Drastic distance.");
				f = new SF();
				System.out.println("The arrgegation function is summation function.");
				break;

			default:
				System.err.println("There are something wrong in the d and f function.");
				break;
			}
			
			Main main = new Main(TBox, d, f);
			
			main.setTBox(TBox);
			
			try {
				System.err.println("The result is :" + main.HSMIRV());
			} catch (OWLOntologyCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.flush();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.flush();
		}
	}

	public CopyOnWriteArrayList<CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>> HSMIRV() throws OWLOntologyCreationException {
		Set<OWLEntity> signature = null;
		Set<OWLAxiom> TBox = null;

		OntologyManager manager = new OntologyManager();
		OWLOntology ontology = manager.loadOntology(this.getTBox().getAbsolutePath());

		long beginTime = System.currentTimeMillis();
		TBox = manager.getTBox(ontology);
		signature = manager.getTBoxSignature(ontology);

		CType unSet = universalSet(signature);

		SCType scType = new SCType();
		for (OWLAxiom axiom : TBox) {
			scType.add(dnf(axiom, signature));
		}

		CType model = modelType(unSet, scType);

		if (!model.isRCSatisfiable()) {
			while (!model.isRCSatisfiable()) {
				Type rcUnsatis = model.getRcType();

				if (rcUnsatis.getTypes().size() == 0)
					System.err.println("this is an error line 93 in main class.");

				CType leftModel = unSet.minus(model);
				OWLClassExpression property = rcUnsatis.getTypes().iterator().next();
				CType addTypesAll = leftModel.getContainProperty(property);
				CType addType = modelType(addTypesAll, scType);

				model.addTypes(addType.getcTypes());
			}
		}

		boolean first = true;
		CopyOnWriteArrayList<CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>> hsmirv = new CopyOnWriteArrayList<CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>>();
		int i = 0;
		for (OWLAxiom axiom : TBox) {
			CType cType = scType.get(i);
			i++;

			ArrayList<ArrayList<OWLEntity>> ht = new ArrayList<ArrayList<OWLEntity>>();

			if (!cType.containsAll(model)) {
				ArrayList<Tree> treeNodes = new ArrayList<Tree>();
				CopyOnWriteArrayList<ArrayList<OWLEntity>> omega = cnf(axiom, treeNodes);

				int count = 0;
				for (ArrayList<OWLEntity> entities : omega) {
					if (typeDNF(((Root) treeNodes.get(count)).getRoot(), signature).containsAll(model)) {
						omega.remove(entities);
					}

					count++;
				}

				HittingSet hittingSet = new HittingSet();
				ht = hittingSet.getHittingSet(omega);
			} else {
				OWLClassImpl thing = (OWLClassImpl) OWLManager.getOWLDataFactory().getOWLThing();
				ArrayList<OWLEntity> list = new ArrayList<OWLEntity>();
				list.add(thing);
				ht.add(list);
			}

			if (first) {
				CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>> firstHt = new CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>();
				for (ArrayList<OWLEntity> entities : ht) {
					CopyOnWriteArrayList<OWLEntity> copyEntities = new CopyOnWriteArrayList<OWLEntity>();
					copyEntities.addAll(entities);
					firstHt.add(copyEntities);
				}
				
				hsmirv.add(firstHt);
			} else {
				hsmirv = cartesianProduct(hsmirv, ht);
			}

			first = false;
		}
		
		System.out.println("The total time is : " + (System.currentTimeMillis()-beginTime));

		return hsmirv;
	}

	public CopyOnWriteArrayList<CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>> cartesianProduct(CopyOnWriteArrayList<CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>> hsmirv,
			ArrayList<ArrayList<OWLEntity>> ht) {
		CopyOnWriteArrayList<CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>> newHsmirv = new CopyOnWriteArrayList<CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>>();
		for(CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>> sec : hsmirv) {
			for(CopyOnWriteArrayList<OWLEntity> third : sec) {
				for(ArrayList<OWLEntity> htFir : ht) {
					CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>> anotherSecond = new CopyOnWriteArrayList<CopyOnWriteArrayList<OWLEntity>>();
					for(OWLEntity entity : htFir) {
						CopyOnWriteArrayList<OWLEntity> anotherThird = new CopyOnWriteArrayList<OWLEntity>();
						anotherThird.addAll(third);
						anotherThird.add(entity);
						anotherSecond.add(anotherThird);
					}
					newHsmirv.add(anotherSecond);
				}
			}
		}
		
		return newHsmirv;
	}

	private CType modelType(CType cTypeSource, SCType scType) {

		ArrayList<Integer> fDistances = new ArrayList<Integer>();
		for (Type type : cTypeSource.getcTypes()) {
			int fDis = 0;
			ArrayList<Integer> distances = new ArrayList<Integer>();

			for (CType cType : scType.getScTypes()) {
				distances.add(typeDistanceCType(type, cType));
			}

			fDis = f.getAggregation(distances);
			fDistances.add(fDis);
		}

		int minimal = Integer.MAX_VALUE;
		for (Integer integer : fDistances) {
			if (integer < minimal)
				minimal = integer;
		}

		Set<Type> minimalType = new HashSet<Type>();
		Iterator<Integer> distance = fDistances.iterator();
		Iterator<Type> types = cTypeSource.getcTypes().iterator();

		while (distance.hasNext()) {
			int d = distance.next();
			Type type = types.next();

			if (d == minimal) {
				minimalType.add(type);
			}
		}

		CType modelTypes = new CType();
		modelTypes.addTypes(minimalType);

		return modelTypes;
	}

	private int typeDistanceCType(Type type, CType cType) {
		int minimal = Integer.MAX_VALUE;

		for (Type ty : cType.getcTypes()) {
			int temp = d.getDistance(type, ty);

			if (temp < minimal) {
				minimal = temp;
			}
		}

		return minimal;
	}

	private CType universalSet(Set<OWLEntity> signature) {
		CType cType = new CType();

		Set<OWLClassExpression> type = new HashSet<OWLClassExpression>();

		OWLClassImpl thing = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory().getOWLThing();
		OWLClassImpl nothing = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory().getOWLNothing();

		for (OWLEntity entity : signature) {
			if (thing.equals(entity))
				continue;

			if (nothing.equals(entity))
				continue;

			if (entity instanceof OWLClassImpl) {
				type.add((OWLClassImpl) entity);
			}

			if (entity instanceof OWLObjectPropertyImpl) {
				OWLClassImpl thing1 = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory()
						.getOWLThing();
				OWLClassImpl thing2 = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory()
						.getOWLThing();

				OWLObjectSomeValuesFromImpl someValuesFrom = new OWLObjectSomeValuesFromImpl(
						(OWLObjectPropertyImpl) entity, thing1);
				OWLObjectSomeValuesFromImpl inverseSomeValuesFrom = new OWLObjectSomeValuesFromImpl(
						((OWLObjectPropertyImpl) entity).getInverseProperty(), thing2);

				type.add(someValuesFrom);
				type.add(inverseSomeValuesFrom);
			}
		}

		int length = 1;
		for (int i = type.size(); i > 0; i--) {
			length *= 2;
		}

		Iterator<OWLClassExpression> iterator = null;
		for (int i = 0; i < length; i++) {
			Type temp = new Type();
			iterator = type.iterator();

			int tempInt = i;
			while (iterator.hasNext()) {
				OWLClassExpression expression = iterator.next();
				if ((tempInt & 1) == 1) {
					temp.add(expression);
				}
				tempInt = (tempInt >> 1);
			}
			cType.addTypes(temp);
		}

		return cType;
	}

	private CopyOnWriteArrayList<ArrayList<OWLEntity>> cnf(OWLAxiom axiom, ArrayList<Tree> cnfTrees) {
		OWLClassExpression expression = null;

		if (axiom instanceof OWLSubClassOfAxiomImpl) {
			OWLSubClassOfAxiom subClassOfAxiom = (OWLSubClassOfAxiom) axiom;
			expression = subClass(subClassOfAxiom);
		}

		expression = expression.getNNF();

		Root tree = new Root();

		if (expression instanceof OWLObjectUnionOf) {
			tree.setRoot(Travel((OWLObjectUnionOfImpl) expression));
		} else if (expression instanceof OWLObjectIntersectionOf) {
			tree.setRoot(Travel((OWLObjectIntersectionOf) expression));
		} else {
			Leave leave = new Leave();
			leave.setArgs(expression);
			tree.setRoot(leave);
		}

		visitCNF(tree.getRoot());

		return typeCNF(tree.getRoot(), cnfTrees);
	}

	private CopyOnWriteArrayList<ArrayList<OWLEntity>> typeCNF(Tree root, ArrayList<Tree> cnfTrees) {
		CopyOnWriteArrayList<ArrayList<OWLEntity>> result = new CopyOnWriteArrayList<ArrayList<OWLEntity>>();

		if (!(root instanceof ConjunctiveOperator)) {
			Root treeRoot = new Root();
			treeRoot.setRoot(root);
			cnfTrees.add(treeRoot);
		} else {
			ConjunctiveOperator conjunctiveOperator = (ConjunctiveOperator) root;
			while (conjunctiveOperator.getRightChild() instanceof ConjunctiveOperator) {
				Root treeRoot = new Root();
				treeRoot.setRoot(conjunctiveOperator.getLeftChild());
				cnfTrees.add(treeRoot);

				conjunctiveOperator = (ConjunctiveOperator) conjunctiveOperator.getRightChild();
			}

			Root treeRoot1 = new Root();
			Root treeRoot2 = new Root();

			treeRoot1.setRoot(conjunctiveOperator.getLeftChild());
			treeRoot2.setRoot(conjunctiveOperator.getRightChild());

			cnfTrees.add(treeRoot1);
			cnfTrees.add(treeRoot2);
		}

		for (Tree r : cnfTrees) {
			result.add(getTypeCNF(r));
		}

		return result;
	}

	ArrayList<OWLEntity> getTypeCNF(Tree root) {
		ArrayList<OWLEntity> entities = new ArrayList<OWLEntity>();

		Set<OWLClassExpression> treeNodes = new HashSet<OWLClassExpression>();
		Visitor visitor = new Visitor(treeNodes);

		root.visite(visitor);

		for (OWLClassExpression node : treeNodes) {
			if (node instanceof OWLClassImpl) {
				OWLClassImpl classImpl = (OWLClassImpl) node;
				entities.add(classImpl);
			}

			if (node instanceof OWLObjectComplementOf) {
				OWLClassImpl classImpl = (OWLClassImpl) (((OWLObjectComplementOf) node).getOperand());
				entities.add(classImpl);
			}

			if (node instanceof OWLObjectSomeValuesFromImpl) {
				OWLObjectSomeValuesFromImpl objectSomeValuesFromImpl = (OWLObjectSomeValuesFromImpl) node;

				OWLObjectPropertyExpression objectPropertyExpression = objectSomeValuesFromImpl.getProperty();

				if (objectPropertyExpression instanceof OWLObjectInverseOfImpl) {
					OWLObjectInverseOfImpl objectInverseOfImpl = (OWLObjectInverseOfImpl) objectPropertyExpression;
					OWLObjectPropertyImpl objectPropertyImpl = (OWLObjectPropertyImpl) objectInverseOfImpl
							.getInverseProperty();

					entities.add(objectPropertyImpl);
				} else {
					OWLObjectPropertyImpl objectProperty = (OWLObjectPropertyImpl) objectPropertyExpression;
					entities.add(objectProperty);
				}
			}

			if (node instanceof OWLObjectAllValuesFromImpl) {
				OWLObjectAllValuesFromImpl objectAllValuesFromImpl = (OWLObjectAllValuesFromImpl) node;

				OWLObjectPropertyExpression objectPropertyExpression = objectAllValuesFromImpl.getProperty();

				if (objectPropertyExpression instanceof OWLObjectInverseOfImpl) {
					OWLObjectInverseOfImpl objectInverseOfImpl = (OWLObjectInverseOfImpl) objectPropertyExpression;
					OWLObjectPropertyImpl objectPropertyImpl = (OWLObjectPropertyImpl) objectInverseOfImpl
							.getInverseProperty();

					entities.add(objectPropertyImpl);
				} else {
					OWLObjectPropertyImpl objectProperty = (OWLObjectPropertyImpl) objectPropertyExpression;
					entities.add(objectProperty);
				}
			}
		}

		return entities;
	}

	private CType dnf(OWLAxiom axiom, Set<OWLEntity> signature) {
		OWLClassExpression expression = null;

		if (axiom instanceof OWLSubClassOfAxiomImpl) {
			OWLSubClassOfAxiom subClassOfAxiom = (OWLSubClassOfAxiom) axiom;
			expression = subClass(subClassOfAxiom);
		}

		expression = expression.getNNF();

		Root tree = new Root();

		if (expression instanceof OWLObjectUnionOf) {
			tree.setRoot(Travel((OWLObjectUnionOfImpl) expression));
		} else if (expression instanceof OWLObjectIntersectionOf) {
			tree.setRoot(Travel((OWLObjectIntersectionOf) expression));
		} else {
			Leave leave = new Leave();
			leave.setArgs(expression);
			tree.setRoot(leave);
		}

		visitDNF(tree.getRoot());

		CType cType = typeDNF(tree.getRoot(), signature);

		return cType;
	}

	private CType typeDNF(Tree root, Set<OWLEntity> signature) {
		Set<OWLClassExpression> type = new HashSet<OWLClassExpression>();

		OWLClassImpl thing = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory().getOWLThing();

		for (OWLEntity entity : signature) {
			if (thing.equals(entity))
				continue;

			if (entity instanceof OWLClassImpl) {
				type.add((OWLClassImpl) entity);
			}

			if (entity instanceof OWLObjectPropertyImpl) {
				OWLClassImpl thing1 = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory()
						.getOWLThing();
				OWLClassImpl thing2 = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory()
						.getOWLThing();

				OWLObjectSomeValuesFromImpl someValuesFrom = new OWLObjectSomeValuesFromImpl(
						(OWLObjectPropertyImpl) entity, thing1);
				OWLObjectSomeValuesFromImpl inverseSomeValuesFrom = new OWLObjectSomeValuesFromImpl(
						((OWLObjectPropertyImpl) entity).getInverseProperty(), thing2);

				type.add(someValuesFrom);
				type.add(inverseSomeValuesFrom);
			}
		}

		CType cType = new CType();

		if (!(root instanceof DisjointiveOperator)) {
			cType.addTypes(getType(root, type));
		} else {
			DisjointiveOperator disjointiveOperator = (DisjointiveOperator) root;
			while (disjointiveOperator.getRightChild() instanceof DisjointiveOperator) {
				cType.addTypes(getType(disjointiveOperator.getLeftChild(), type));
				disjointiveOperator = (DisjointiveOperator) disjointiveOperator.getRightChild();
			}

			cType.addTypes(getType(disjointiveOperator.getLeftChild(), type));
			cType.addTypes(getType(disjointiveOperator.getRightChild(), type));
		}

		return cType;
	}

	private Set<Type> getType(Tree root, Set<OWLClassExpression> type) {
		Set<Type> types = new HashSet<Type>();

		Set<OWLClassExpression> treeNodes = new HashSet<OWLClassExpression>();
		Visitor visitor = new Visitor(treeNodes);

		root.visite(visitor);

		Set<OWLClassExpression> leftType = new HashSet<OWLClassExpression>();

		for (OWLClassExpression entity : type) {
			if (entity instanceof OWLClassImpl) {
				OWLClassImpl entityClass = (OWLClassImpl) entity;
				if (!treeNodes.contains(entityClass) && !treeNodes.contains(entityClass.getObjectComplementOf()))
					leftType.add(entityClass);
			}

			if (entity instanceof OWLObjectSomeValuesFromImpl) {
				OWLClassImpl nothing = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory()
						.getOWLNothing();
				OWLObjectSomeValuesFromImpl entityProperty = (OWLObjectSomeValuesFromImpl) entity;
				OWLObjectAllValuesFromImpl entityAllValuesFrom = new OWLObjectAllValuesFromImpl(
						entityProperty.getProperty(), nothing);
				if (!treeNodes.contains(entityProperty) && !treeNodes.contains(entityAllValuesFrom))
					leftType.add(entityProperty);
			}
		}

		Tree currentNode = root.clone();
		for (OWLClassExpression entity : leftType) {
			if (entity instanceof OWLClassImpl) {
				OWLClassImpl entityClass = (OWLClassImpl) entity;

				DisjointiveOperator disjointiveOperator = new DisjointiveOperator();

				Leave leftLeave = new Leave();
				Leave rightLeave = new Leave();
				leftLeave.setArgs(entityClass);
				rightLeave.setArgs(entityClass.getObjectComplementOf());

				disjointiveOperator.setLeftChild(leftLeave);
				disjointiveOperator.setRightChild(rightLeave);

				ConjunctiveOperator conjunctiveOperator = new ConjunctiveOperator();

				conjunctiveOperator.setLeftChild(currentNode);
				conjunctiveOperator.setRightChild(disjointiveOperator);
				currentNode = conjunctiveOperator;
			}

			if (entity instanceof OWLObjectSomeValuesFromImpl) {
				OWLObjectSomeValuesFromImpl entityProperty = (OWLObjectSomeValuesFromImpl) entity;
				OWLClassImpl nothing = (OWLClassImpl) OWLManager.createOWLOntologyManager().getOWLDataFactory()
						.getOWLNothing();
				OWLObjectAllValuesFromImpl entityPropertyNeg = new OWLObjectAllValuesFromImpl(
						entityProperty.getProperty(), nothing);

				DisjointiveOperator disjointiveOperator = new DisjointiveOperator();

				Leave leftLeave = new Leave();
				Leave rightLeave = new Leave();
				leftLeave.setArgs(entityProperty);
				rightLeave.setArgs(entityPropertyNeg);

				disjointiveOperator.setLeftChild(leftLeave);
				disjointiveOperator.setRightChild(rightLeave);

				ConjunctiveOperator conjunctiveOperator = new ConjunctiveOperator();

				conjunctiveOperator.setLeftChild(currentNode);
				conjunctiveOperator.setRightChild(disjointiveOperator);
				currentNode = conjunctiveOperator;
			}
		}

		Root newTree = new Root();
		newTree.setRoot(currentNode);

		visitDNF(newTree.getRoot());

		if (!(newTree.getRoot() instanceof DisjointiveOperator)) {
			types.add(getTypeFromTree(newTree.getRoot()));
			return types;
		} else {
			DisjointiveOperator disjointiveOperator = (DisjointiveOperator) newTree.getRoot();
			while (disjointiveOperator.getRightChild() instanceof DisjointiveOperator) {
				types.add(getTypeFromTree(disjointiveOperator.getLeftChild()));
				disjointiveOperator = (DisjointiveOperator) disjointiveOperator.getRightChild();
			}

			types.add(getTypeFromTree(disjointiveOperator.getLeftChild()));
			types.add(getTypeFromTree(disjointiveOperator.getRightChild()));

			return types;
		}
	}

	public Type getTypeFromTree(Tree root) {
		Type type = new Type();

		Set<OWLClassExpression> treeNodes = new HashSet<OWLClassExpression>();
		Visitor visitor = new Visitor(treeNodes);

		root.visite(visitor);

		for (OWLClassExpression entity : treeNodes) {
			if (entity instanceof OWLClassImpl || entity instanceof OWLObjectSomeValuesFromImpl) {
				type.add(entity);
			}
		}

		return type;
	}

	private void visitDNF(Tree root) {
		Tree empty = new BinaryOperator();

		if (root instanceof DisjointiveOperator) {
			visitDNF(((DisjointiveOperator) root).getLeftChild());
			visitDNF(((DisjointiveOperator) root).getRightChild());

			if (root.getParent() instanceof DisjointiveOperator) {
				DisjointiveOperator parent = (DisjointiveOperator) root.getParent();
				DisjointiveOperator disjointiveOperator = (DisjointiveOperator) root;

				if (parent.getRightChild().equals(disjointiveOperator)) {

				} else {
					Tree ancestor = parent.getParent();

					parent.setLeftChild(disjointiveOperator.getRightChild());
					disjointiveOperator.setRightChild(parent);

					if (ancestor instanceof Root) {
						Root tree = (Root) ancestor;

						tree.setRoot(disjointiveOperator);
					} else if (ancestor instanceof ConjunctiveOperator) {
						ConjunctiveOperator conjunctiveOperator = (ConjunctiveOperator) ancestor;

						if (conjunctiveOperator.getLeftChild().equals(parent))
							conjunctiveOperator.setLeftChild(disjointiveOperator);
						else
							conjunctiveOperator.setRightChild(disjointiveOperator);
					} else if (ancestor instanceof DisjointiveOperator) {
						DisjointiveOperator disjointiveOperator2 = (DisjointiveOperator) ancestor;

						if (disjointiveOperator2.getLeftChild().equals(parent))
							disjointiveOperator2.setLeftChild(disjointiveOperator);
						else
							disjointiveOperator2.setRightChild(disjointiveOperator);
					}

					visitDNF(disjointiveOperator);
				}
			} else if (root.getParent() instanceof ConjunctiveOperator) {
				ConjunctiveOperator parent = (ConjunctiveOperator) root.getParent();
				DisjointiveOperator disjointiveOperator = (DisjointiveOperator) root;
				Tree ancestor = parent.getParent();

				ConjunctiveOperator leftConjunctive = parent.clone();
				ConjunctiveOperator rightConjunctive = parent.clone();

				leftConjunctive.setLeftChild(parent.getLeftChild().clone());
				leftConjunctive.setRightChild(parent.getRightChild().clone());

				rightConjunctive.setLeftChild(parent.getLeftChild().clone());
				rightConjunctive.setRightChild(parent.getRightChild().clone());

				if (parent.getLeftChild().equals(disjointiveOperator)) {
					leftConjunctive.setLeftChild(disjointiveOperator.getLeftChild());
					rightConjunctive.setLeftChild(disjointiveOperator.getRightChild());
				} else {
					leftConjunctive.setRightChild(disjointiveOperator.getLeftChild());
					rightConjunctive.setRightChild(disjointiveOperator.getRightChild());
				}

				disjointiveOperator.setLeftChild(leftConjunctive);
				disjointiveOperator.setRightChild(rightConjunctive);

				if (ancestor instanceof Root) {
					Root tree = (Root) ancestor;

					tree.setRoot(disjointiveOperator);
				} else if (ancestor instanceof ConjunctiveOperator) {
					ConjunctiveOperator conjunctiveOperator = (ConjunctiveOperator) ancestor;

					if (conjunctiveOperator.getLeftChild().equals(parent))
						conjunctiveOperator.setLeftChild(disjointiveOperator);
					else
						conjunctiveOperator.setRightChild(disjointiveOperator);
				} else if (ancestor instanceof DisjointiveOperator) {
					DisjointiveOperator disjointiveOperator2 = (DisjointiveOperator) ancestor;

					if (disjointiveOperator2.getLeftChild().equals(parent))
						disjointiveOperator2.setLeftChild(disjointiveOperator);
					else
						disjointiveOperator2.setRightChild(disjointiveOperator);
				}

				parent.setLeftChild(empty);
				parent.setRightChild(empty);
				visitDNF(disjointiveOperator);
			}
		}

		if (root instanceof ConjunctiveOperator) {
			visitDNF(((ConjunctiveOperator) root).getLeftChild());
			visitDNF(((ConjunctiveOperator) root).getRightChild());
		}
	}

	private void visitCNF(Tree root) {
		Tree empty = new BinaryOperator();

		if (root instanceof ConjunctiveOperator) {
			visitCNF(((ConjunctiveOperator) root).getLeftChild());
			visitCNF(((ConjunctiveOperator) root).getRightChild());

			if (root.getParent() instanceof ConjunctiveOperator) {
				ConjunctiveOperator parent = (ConjunctiveOperator) root.getParent();
				ConjunctiveOperator disjointiveOperator = (ConjunctiveOperator) root;

				if (parent.getRightChild().equals(disjointiveOperator)) {

				} else {
					Tree ancestor = parent.getParent();

					parent.setLeftChild(disjointiveOperator.getRightChild());
					disjointiveOperator.setRightChild(parent);

					if (ancestor instanceof Root) {
						Root tree = (Root) ancestor;

						tree.setRoot(disjointiveOperator);
					} else if (ancestor instanceof ConjunctiveOperator) {
						ConjunctiveOperator conjunctiveOperator = (ConjunctiveOperator) ancestor;

						if (conjunctiveOperator.getLeftChild().equals(parent))
							conjunctiveOperator.setLeftChild(disjointiveOperator);
						else
							conjunctiveOperator.setRightChild(disjointiveOperator);
					} else if (ancestor instanceof DisjointiveOperator) {
						DisjointiveOperator disjointiveOperator2 = (DisjointiveOperator) ancestor;

						if (disjointiveOperator2.getLeftChild().equals(parent))
							disjointiveOperator2.setLeftChild(disjointiveOperator);
						else
							disjointiveOperator2.setRightChild(disjointiveOperator);
					}

					visitCNF(disjointiveOperator);
				}
			} else if (root.getParent() instanceof DisjointiveOperator) {
				DisjointiveOperator parent = (DisjointiveOperator) root.getParent();
				ConjunctiveOperator disjointiveOperator = (ConjunctiveOperator) root;
				Tree ancestor = parent.getParent();

				DisjointiveOperator leftConjunctive = parent.clone();
				DisjointiveOperator rightConjunctive = parent.clone();

				leftConjunctive.setLeftChild(parent.getLeftChild().clone());
				leftConjunctive.setRightChild(parent.getRightChild().clone());

				rightConjunctive.setLeftChild(parent.getLeftChild().clone());
				rightConjunctive.setRightChild(parent.getRightChild().clone());

				if (parent.getLeftChild().equals(disjointiveOperator)) {
					leftConjunctive.setLeftChild(disjointiveOperator.getLeftChild());
					rightConjunctive.setLeftChild(disjointiveOperator.getRightChild());
				} else {
					leftConjunctive.setRightChild(disjointiveOperator.getLeftChild());
					rightConjunctive.setRightChild(disjointiveOperator.getRightChild());
				}

				disjointiveOperator.setLeftChild(leftConjunctive);
				disjointiveOperator.setRightChild(rightConjunctive);

				if (ancestor instanceof Root) {
					Root tree = (Root) ancestor;

					tree.setRoot(disjointiveOperator);
				} else if (ancestor instanceof ConjunctiveOperator) {
					ConjunctiveOperator conjunctiveOperator = (ConjunctiveOperator) ancestor;

					if (conjunctiveOperator.getLeftChild().equals(parent))
						conjunctiveOperator.setLeftChild(disjointiveOperator);
					else
						conjunctiveOperator.setRightChild(disjointiveOperator);
				} else if (ancestor instanceof DisjointiveOperator) {
					DisjointiveOperator disjointiveOperator2 = (DisjointiveOperator) ancestor;

					if (disjointiveOperator2.getLeftChild().equals(parent))
						disjointiveOperator2.setLeftChild(disjointiveOperator);
					else
						disjointiveOperator2.setRightChild(disjointiveOperator);
				}

				parent.setLeftChild(empty);
				parent.setRightChild(empty);
				visitCNF(disjointiveOperator);
			}
		}

		if (root instanceof DisjointiveOperator) {
			visitCNF(((DisjointiveOperator) root).getLeftChild());
			visitCNF(((DisjointiveOperator) root).getRightChild());
		}
	}

	private DisjointiveOperator Travel(OWLObjectUnionOf classExpression) {
		if (classExpression.getOperandsAsList().size() != 2) {
			try {
				new Exception("There are more than two argument in the union operator!");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		DisjointiveOperator disjointiveOperator = new DisjointiveOperator();

		List<OWLClassExpression> owlList = classExpression.getOperandsAsList();

		OWLClassExpression leftChild = owlList.get(0);
		OWLClassExpression rightChild = owlList.get(1);

		Tree left = null;
		Tree right = null;

		if (leftChild instanceof OWLObjectUnionOf) {
			left = Travel((OWLObjectUnionOfImpl) leftChild);
		} else if (leftChild instanceof OWLObjectIntersectionOf) {
			left = Travel((OWLObjectIntersectionOf) leftChild);
		} else {
			Leave leave = new Leave();
			leave.setArgs(leftChild);
			left = leave;
		}

		if (rightChild instanceof OWLObjectUnionOf) {
			right = Travel((OWLObjectUnionOfImpl) rightChild);
		} else if (rightChild instanceof OWLObjectIntersectionOf) {
			right = Travel((OWLObjectIntersectionOf) rightChild);
		} else {
			Leave leave = new Leave();
			leave.setArgs(rightChild);
			right = leave;
		}

		disjointiveOperator.setLeftChild(left);
		disjointiveOperator.setRightChild(right);

		return disjointiveOperator;
	}

	private ConjunctiveOperator Travel(OWLObjectIntersectionOf classExpression) {
		if (classExpression.getOperandsAsList().size() != 2) {
			try {
				new Exception("There are more than two argument in the intersection operator!");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		ConjunctiveOperator conjunctiveOperator = new ConjunctiveOperator();

		List<OWLClassExpression> owlList = classExpression.getOperandsAsList();

		OWLClassExpression leftChild = owlList.get(0);
		OWLClassExpression rightChild = owlList.get(1);

		Tree left = null;
		Tree right = null;

		if (leftChild instanceof OWLObjectUnionOf) {
			left = Travel((OWLObjectUnionOfImpl) leftChild);
		} else if (leftChild instanceof OWLObjectIntersectionOf) {
			left = Travel((OWLObjectIntersectionOf) leftChild);
		} else {
			Leave leave = new Leave();
			leave.setArgs(leftChild);
			left = leave;
		}

		if (rightChild instanceof OWLObjectUnionOf) {
			right = Travel((OWLObjectUnionOfImpl) rightChild);
		} else if (rightChild instanceof OWLObjectIntersectionOf) {
			right = Travel((OWLObjectIntersectionOf) rightChild);
		} else {
			Leave leave = new Leave();
			leave.setArgs(rightChild);
			right = leave;
		}

		conjunctiveOperator.setLeftChild(left);
		conjunctiveOperator.setRightChild(right);

		return conjunctiveOperator;
	}

	private OWLClassExpression subClass(OWLSubClassOfAxiom subAxiom) {
		OWLClassExpression subClassExpression = subAxiom.getSubClass();
		OWLClassExpression supClassExpression = subAxiom.getSuperClass();

		if (subClassExpression instanceof OWLObjectUnionOfImpl) {
			OWLObjectUnionOfImpl union = (OWLObjectUnionOfImpl) subClassExpression;

			Set<OWLClassExpression> list = union.getOperands();

			if (list.size() == 2) {
				Iterator<OWLClassExpression> iterator = list.iterator();
				OWLClassExpression classExpression1 = iterator.next();
				OWLClassExpression classExpression2 = iterator.next();

				if (classExpression1.getObjectComplementOf().equals(classExpression2))
					return supClassExpression;
			}
		}

		if (subClassExpression.equals(OWLManager.getOWLDataFactory().getOWLThing()))
			return supClassExpression;

		if (supClassExpression instanceof OWLObjectUnionOfImpl) {
			OWLObjectUnionOfImpl union = (OWLObjectUnionOfImpl) supClassExpression;

			Set<OWLClassExpression> list = union.getOperands();

			if (list.size() == 2) {
				Iterator<OWLClassExpression> iterator = list.iterator();
				OWLClassExpression classExpression1 = iterator.next();
				OWLClassExpression classExpression2 = iterator.next();

				if (classExpression1.getObjectComplementOf().equals(classExpression2))
					return subClassExpression.getObjectComplementOf();
			}
		}

		if (supClassExpression.equals(OWLManager.getOWLDataFactory().getOWLNothing()))
			return subClassExpression.getObjectComplementOf();

		Set<OWLClassExpression> unionSet = new HashSet<OWLClassExpression>();

		subClassExpression = subClassExpression.getObjectComplementOf();

		unionSet.add(subClassExpression);
		unionSet.add(supClassExpression);

		OWLObjectUnionOf unionOf = new OWLObjectUnionOfImpl(unionSet);

		return unionOf;
	}
}
