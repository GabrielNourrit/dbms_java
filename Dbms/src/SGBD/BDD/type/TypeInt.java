package SGBD.BDD.type;

import java.util.Comparator;

import SGBD.BDD.Type;

public class TypeInt implements Type<Integer> {
	private int valeur;

	//@Override
	/*public Integer newInstance(Integer v) {
		this.valeur=v;
		return this.valeur;
	}*/
	public TypeInt(int v) {
		this.valeur=v;
	}

	@Override
	public Comparator<Integer> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return ""+this.valeur;
	}
}
