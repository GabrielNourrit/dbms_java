package SGBD.AnalyseurSyntaxique;

import SGBD.BDD.Table;

public class TablesRecherche {
	public Table table1;	
	public Table table2;	
	public String[] join;
	public TablesRecherche(Table t,Table t2, String[] c) {
		table1=t;
		table2=t2;
		join=c;
	}

}
