package SGBD.BDD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jus.util.assertion.Ensure;
import jus.util.assertion.Require;

public class Index {
	private int numero;
	private String cle; 

	public <T> Index(int numero, String cle){
		this.numero=numero;
		this.cle=cle;
	}
	
	/**
	 * @param schema
	 * @param fichier
	 * @return le tuple correspondant à notre index sous forme de string
	 * @require fichier!=null
	 * @ensure ligne!=null
	 */
	public String desindex(String fichier) {
		if(fichier==null) throw new Require("Erreur fichier");
		File f = new File(fichier);
		int n = 1, c = 0; String ligne="";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			while(n!=this.numero){
				br.readLine(); n++;
			}
			ligne = br.readLine();
			if ((ligne != null)) {
				
			}			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		if(ligne==null) throw new Ensure("Erreur ligne");
		return ligne;
	}

	/**
	 * @param schema
	 * @param fichier
	 * @return le tuple correspondant a notre index sous forme de liste de donnee
	 * @require fichier!=null
	 * @ensure l!=null
	 */
	public ArrayList<Donnee> desindexDonnee(String fichier) {
		if(fichier==null) throw new Require("Erreur fichier");
		ArrayList<Donnee> l = new ArrayList<Donnee>();
		String ligne = desindex(fichier);
		String[] lignePars = ligne.split(";");
		for (String s : lignePars) l.add(new Donnee<String>(s));
		return l;
	}	

	/**
	 * @return l'index sous forme de String
	 */
	public String toString() { return this.numero+";"+this.cle.toString(); }
	
	/**
	 * @return le numero de l'index
	 */
	public int numero() { int n=this.numero; return n; }
	
	/**
	 * @return la cle sous forme de string
	 */
	public String cle() { String c=this.cle;return c; }

}
