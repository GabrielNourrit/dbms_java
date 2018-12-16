package SGBD.BDD;

import java.util.Comparator;

import SGBD.BDD.type.TypeInt;

/**
 * @param <T> le type de la donnée
 */
public class Donnee<T> implements Comparator {
	private T valeur;
	
	public Donnee(T val) {
		this.valeur = val;
	}
	
	public Donnee<T> getValeur() {
		return new Donnee<T>(valeur);
	}
	
	public String toString() {
		return ""+this.valeur;
	}
	
	public T valeur() { return this.valeur; }

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
}
