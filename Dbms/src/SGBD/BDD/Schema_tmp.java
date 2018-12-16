package SGBD.BDD;

import java.util.ArrayList;

import jus.util.assertion.Require;

public class Schema_tmp {
	private ArrayList<Attribut> listeAttributs;
	
	public Schema_tmp(ArrayList<Attribut> l) {
		this.listeAttributs = l;
	}
	
	/**
	 * @return une ArrayList d'attributs
	 */
	public ArrayList<Attribut> attributs() { ArrayList<Attribut> l = this.listeAttributs; return l; }
	
	/**
	 * Methode qui retourne l'indice de l'attribut passe en parametre
	 * @param attribut
	 * @return l'indice de l'attribut, -1 si il n'existe pas
	 * @require attribut!=null
	 */
	public int indiceAttributs(String attribut) {
		int index = -1, i = 0;
		if (attribut==null) throw new Require("Erreur attribut");
		for (Attribut a : listeAttributs) {
			if (a.nom().toUpperCase().equals(attribut.toUpperCase())) {
				index = i;
			}
			i++;
		}
		return index;
	}
	
	/**
	 * Compare deux schemas
	 * @param s le schema a comparer
	 * @return true si les schemas sont egaux false sinon
	 */
	public boolean equals(Schema_tmp s) {
		return listeAttributs.equals(s.attributs());
	}
}
