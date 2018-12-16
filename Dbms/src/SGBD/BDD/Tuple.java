package SGBD.BDD;

import java.util.ArrayList;

import SGBD.BDD.type.TypeInt;
import SGBD.BDD.type.TypeString;
import jus.util.assertion.Require;

public class Tuple {
	private Index i;
	private Schema schema;

	public Tuple(Index i, Schema schema) {
		this.schema = schema;
		this.i = i;
	}

	
	/**
	 * Renvoie le tuple sous forme de liste de donnee
	 * @param fichier
	 * @return le tuple sous forme de liste de donne
	 * @require fichier!=null
	 * @ensure listeDonnees!=null
	 */
	public ArrayList<Donnee> recupererTuple(String fichier) {
		if (fichier==null) throw new Require("Erreur fichier");
		ArrayList<Donnee> listeDonnees = new ArrayList<Donnee>();
		String ligne = i.desindex(fichier);
		String str[] =ligne.split(";");
		ArrayList<String> types = schema.types();
		for (int i = 0; i < str.length; i++) listeDonnees.add(donnee(types.get(i),str[i]));
		return listeDonnees;
	}
	
	/**
	 * Methode qui cree une donnee
	 * @param type le type de la donnee
	 * @param valeur la valeur de la donnee
	 * @return la donne cree
	 * @require type!=null && valeur!=null
	 * @ensure d!=null
	 */
	private Donnee donnee(String type, String valeur) {
		if (type==null) throw new Require("Erreur type");
		if (!type.equals("TypeInt") && !type.equals("TypeString") && !type.equals("TypeDouble")) throw new Require("Erreur type");
		if (valeur==null) throw new Require("Erreur Valeur");
		Donnee d = null;
		if (type.equals("TypeInt")) {
			TypeInt v = new TypeInt(Integer.parseInt(valeur));
			d=new Donnee<TypeInt>(v);
		}
		if (type.equals("TypeString")) {
			TypeString v = new TypeString(valeur);
			d=new Donnee<TypeString>(v);
		}
		return d;
	}

} 
