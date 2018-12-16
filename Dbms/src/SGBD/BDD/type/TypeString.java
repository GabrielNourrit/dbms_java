package SGBD.BDD.type;


import java.util.Comparator;

import SGBD.BDD.Type;

public class TypeString implements Type<String> {
	private String valeur;

	//@Override
	/*public Integer newInstance(Integer v) {
		this.valeur=v;
		return this.valeur;
	}*/
	public TypeString(String v) {
		this.valeur=v;
	}

	@Override
	public Comparator<String> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return this.valeur;
	}
}
