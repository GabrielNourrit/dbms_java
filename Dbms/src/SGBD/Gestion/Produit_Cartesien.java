package SGBD.Gestion;

import java.util.ArrayList;

import SGBD.BDD.Index;
import SGBD.BDD.Schema_tmp;
import SGBD.BDD.Table;
import SGBD.BDD.Table_tmp;
import SGBD.BDD.Tuple;
import jus.util.assertion.Require;
import SGBD.BDD.Attribut;
import SGBD.BDD.Donnee;

public class Produit_Cartesien{

	/**
	 * Methode qui realise le produit cartesien de deux tables
	 * @param t1 la premiere table
	 * @param t2 la deuxieme table
	 * @return la table avec le produit cartesien realise
	 * @require t1!=null && t2!=null && t1.schema()!=null && t2.schema()!=null && t1.listeIndexTuple()!=null && t2.listeIndexTuple()!=null
	 * @ensure tmp!=null && tmp.schema()!=null && tmp le produit cartesien de t1 t2
	 */
	public static Table_tmp operation(Table t1, Table t2){

		/* pour chaque ligne de t1 on la colle a coté de t2 (for)
		 * -> on doit recuperer la premiere ligne de t1(t1cur) puis la premiere de t2(t2cur)
		 * -> modifier t2cur
		 * à chaque itération on doit passer t1.donnee()+t2.donnee() à notre res().ajout_tuple()
		 * 
		 */
		
		if(t1==null || t2==null || t1.schema()==null || t2.schema()==null || t1.listeIndexTuple()==null || t2.listeIndexTuple()==null) throw new Require("Erreur table");
		
		ArrayList<ArrayList<Donnee>> nouvelleListe = new ArrayList<ArrayList<Donnee>>();
		ArrayList<Donnee> a=new ArrayList<Donnee>();
		ArrayList<Donnee> b=new ArrayList<Donnee>();
		ArrayList<Donnee> c=new ArrayList<Donnee>();

		for(Index index : t1.listeIndexTuple()){
			a=new Tuple(index,t1.schema()).recupererTuple("src/"+t1.nom()+".txt");

			for(Index index2 : t2.listeIndexTuple()){
				b=new Tuple(index2,t2.schema()).recupererTuple("src/"+t2.nom()+".txt");
				c.addAll(a); //Concatenation a t1.donnee() et t2.donnee() !
				c.addAll(b);
				nouvelleListe.add(c);
				//res.ajouterTuple(c);
				c=new ArrayList<Donnee>();
			}
		}
		Attribut atmp;
		ArrayList<Attribut> sc = new ArrayList<Attribut>();
		for (Attribut at : t1.schema().attributs()) {
			atmp = at; atmp.setNom(t1.nom()+"."+at.nom());
			sc.add(atmp);
		}
		for (Attribut at : t2.schema().attributs()) {
			atmp = at; atmp.setNom(t2.nom()+"."+at.nom());
			sc.add(atmp);
		}
		Schema_tmp schema = new Schema_tmp(sc);
		Table_tmp res = new Table_tmp(nouvelleListe,schema);
		return res;
	}

}
