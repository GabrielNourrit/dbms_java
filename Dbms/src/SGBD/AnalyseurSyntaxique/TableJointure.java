package SGBD.AnalyseurSyntaxique;

import SGBD.BDD.Table;

public class TableJointure {
	public Table table=null;	//and or
	public String[] join= {};
	public TableJointure(Table t, String[] c) {
		table=t;
		join=c;
	}
	
	public TableJointure() {
	}
}
