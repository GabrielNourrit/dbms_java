package SGBD.Gestion;

import java.util.ArrayList;

import SGBD.BDD.Table;
import SGBD.BDD.Table_tmp;
import jus.util.assertion.Require;

public class Select {
	
	/*SELECT nom, prenom, denomination FROM Producteur JOIN Produit ON (Producteur.id_producteur=Produit.id_producteur) WHERE id_producteur > 17 and id_producteur < 26
	 	Table t = new Table("Producteur");
		Table t2 = new Table("Produit");
	   Table[] table = {t,t2};
		String[] attribut = {"id_producteur","nom","prenom","denomination"};
		String[] clause1 = {"id_producteur", "=", "152"};
		String[] clause2 = {"id_producteur", "=", "200"};
		String [][] contrainte = {clause1,clause2};
		//String[] jointure = {"Producteur.id_producteur","Produit.id_producteur"};
		String[] jointure = {"NaturalJoin"};
		//String[] jointure = {};
		Select s = new Select(table, attribut,contrainte,"AND",jointure);
		System.out.println(s.select());*/
	
	private String[] attribut;
	private String[][] contraintes;
	private String typeContrainte;
	private Table[] table;
	private String[] jointure;
	private String orderBy;
	
	/**
	 * Construit un SELECT
	 * @param table la table ou faire le SELECT
	 * @param attribut les colonnes à selectionner
	 * @param contraintes les clauses du WHERE de la forme [["a","=","b"],["e","<","2"],["f","=","'Salut'"]]
	 * @param typeContrainte "OR" ou "AND"
	 * @param jointure si join on ["Producteur.id_producteur","Produit.id_producteur"]
	 * @param l'attribut à order by ex : id_producteur
	 */
	public Select(Table[] table, String[] attribut, String[][] contraintes, String typeContrainte, String[] jointure, String orderBy) {
		this.table = table;
		this.attribut = attribut;
		this.contraintes = contraintes;
		this.jointure = jointure;
		this.typeContrainte = typeContrainte;
		this.orderBy = orderBy;
	}
	
	/**
	 * Methode qui effectue un SELECT dans une table
	 * @return un chaine contenant la réponse à la requete
	 */
	public String select() {
		Table_tmp tmp=null; ArrayList<String> lContraintes = new ArrayList<String>();
		boolean join = false; //savoir si on a fait une jointure
		Table t = table[0];
		Table_tmp tmp_sauv;
		//Si on fait select * from t;
		if (attribut.length == 1 && attribut[0].equals("*") && contraintes.length==0 && jointure.length==0) {
			if (orderBy.equals("")) return t.toString();
			else return t.sort(orderBy).toString();
		}
		//Si on doit faire une jointure
		if(jointure.length>0) {
			join=true; //On passe join à vrai
			if (table.length!=2) throw new Require("Erreur il faut 2 tables pour un join");
			if (jointure.length==2)	tmp=Jointure.join_on(table[0],table[1],jointure[0],jointure[1]); //Join_On
			else if (jointure.length==1 && jointure[0].toUpperCase().equals("NATURALJOIN")) tmp=Jointure.natural_join(table[0],table[1]); //NJ
			tmp_sauv = tmp;
		}
		else tmp_sauv = t.toTableTmp();
		//Transformation du String[] en ArrayList<String>
		for (String s : attribut) lContraintes.add(s);	
		int taille;
		//Si on a des choses dans le WHERE
		if (contraintes.length > 0) {
			taille = contraintes[0][2].length();
			//Si on a eu une jointure on travaille sur une table temporaire en mémoire
			if (join) {
				//Vérification de si on a colonne1=colonne2 ou colonne1=2 ou colonne1='Salut'
				if (contraintes[0][2].substring(0,1).equals("'") && contraintes[0][2].substring(taille-1,taille).equals("'"))
					tmp = Selection.operationTmpVal(tmp, contraintes[0][0], contraintes[0][1], contraintes[0][2].substring(1,taille-1));
				else {
					boolean in = true;
					try {
						Integer.parseInt(contraintes[0][2]);
					} catch (NumberFormatException e) {
						in = false;
					}
					//Si in -> colonne1=2 sinon colonne1=colonne2
					if (in) tmp = Selection.operationTmpVal(tmp, contraintes[0][0], contraintes[0][1], contraintes[0][2]);
					else tmp = Selection.operationTmpCol(tmp, contraintes[0][0], contraintes[0][1], contraintes[0][2]);
				}
			}
			else {
				if (contraintes[0][2].substring(0,1).equals("'") && contraintes[0][2].substring(taille-1,taille).equals("'"))
					tmp = Selection.operationval(t, contraintes[0][0], contraintes[0][1], contraintes[0][2].substring(1,taille-1));
				else {
					boolean in = true;
					try {
						Integer.parseInt(contraintes[0][2]);
					} catch (NumberFormatException e) {
						in = false;
					}
					if (in) tmp = Selection.operationval(t, contraintes[0][0], contraintes[0][1], contraintes[0][2]);
					else tmp = Selection.operationCol(t, contraintes[0][0], contraintes[0][1], contraintes[0][2]);
				}
			}
			if (typeContrainte.toUpperCase().equals("OR")) {
				Table_tmp tmp1;
				for (int i = 1; i < contraintes.length; i++) {
					taille = contraintes[i][2].length();
					if (contraintes[i][2].substring(0,1).equals("'") && contraintes[i][2].substring(taille-1,taille).equals("'"))
						tmp1 = Selection.operationTmpVal(tmp_sauv, contraintes[i][0], contraintes[i][1], contraintes[i][2].substring(1,taille-1));
					else {
						boolean in = true;
						try {
							Integer.parseInt(contraintes[0][2]);
						} catch (NumberFormatException e) {
							in = false;
						}
						
						if (in) tmp1 = Selection.operationTmpVal(tmp_sauv, contraintes[i][0], contraintes[i][1], contraintes[i][2]);
						else tmp1 = Selection.operationTmpCol(tmp_sauv, contraintes[i][0], contraintes[i][1], contraintes[i][2]);
					}
					tmp = Union.operation(tmp, tmp1);
				}	
			}
			else {
				for (int i = 1; i < contraintes.length; i++) {
					taille = contraintes[i][2].length();
					if (contraintes[i][2].substring(0,1).equals("'") && contraintes[i][2].substring(taille-1,taille).equals("'"))
						tmp = Selection.operationTmpVal(tmp, contraintes[i][0], contraintes[i][1], contraintes[i][2].substring(1,taille-1));
					else {
						boolean in = true;
						try {
							Integer.parseInt(contraintes[0][2]);
						} catch (NumberFormatException e) {
							in = false;
						}
						if (in) tmp = Selection.operationTmpVal(tmp, contraintes[i][0], contraintes[i][1], contraintes[i][2]);
						else tmp = Selection.operationTmpCol(tmp, contraintes[i][0], contraintes[i][1], contraintes[i][2]);
					}
				}
			}		
			tmp = Projection.operationTable(lContraintes, tmp);
		}
		else {
			//Si on fait juste SELECT a FROM t JOIN t2 ON ...
			if (join) tmp = Projection.operationTable(lContraintes, tmp);
			else tmp = Projection.operationTable(lContraintes, t); //Si on a fait juste SELECT a FROM t
		}
		if (!orderBy.equals("")) {
			return tmp.sort(orderBy).toString();
		}
		else return tmp.toString();		
	}
}
