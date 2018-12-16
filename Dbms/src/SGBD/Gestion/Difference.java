package SGBD.Gestion;

import java.util.ArrayList;

import SGBD.BDD.Index;
import SGBD.BDD.Table;
import SGBD.BDD.Table_tmp;
import SGBD.BDD.Tuple;
import jus.util.assertion.Require;
import SGBD.BDD.Donnee;

public class Difference {

	/**
	 * Realise la difference entre 2 tables
	 * @param t1 la premiere table
	 * @param t2 la deuxieme table
	 * @return la table avec la difference realisee
	 * @require t1!=null && t2!=null && t1.schema()!=null && t2.schema()!=null && t1.listeIndexTuple()!=null && t2.listeIndexTuple()!=null
	 * @ensure tmp!=null && tmp.schema()!=null && tmp le produit cartesien de t1 t2
	 */
	public Table_tmp operation(Table t1, Table t2){
		Table_tmp res = new Table_tmp();
		ArrayList<Donnee> a=new ArrayList<Donnee>();
		ArrayList<Donnee> b=new ArrayList<Donnee>();
		
		if(t1==null || t2==null || t1.schema()==null || t2.schema()==null || t1.listeIndexTuple()==null || t2.listeIndexTuple()==null) throw new Require("Erreur table");
		
		for(Index index : t1.listeIndexTuple()){
			a=new Tuple(index,t1.schema()).recupererTuple("src/"+t1.nom()+".txt");
			for(Index index2 : t2.listeIndexTuple()){
				b=new Tuple(index2,t2.schema()).recupererTuple("src/"+t2.nom()+".txt");
				if(!a.toString().equals(b.toString()) && !res.isin(a)) res.ajouterTuple(a);
			}
		}			
		return res;
	}

}
