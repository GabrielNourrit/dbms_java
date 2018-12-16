package SGBD.BDD;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.NodeList;

import jus.util.assertion.Require;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class Schema {
	private String fichier;
	private ArrayList<Attribut> listeAttributs;

	public Schema(String fichier,String nomRelation) {
		this.fichier = fichier;
		listeAttributs = new ArrayList<Attribut>();
		chargerSchema(nomRelation);
	}
	
	/**
	 * Retourne l'indice d'un attribut
	 * @param attribut l'attribut dont on veut connaitre l'index
	 * @return l'indice de l'attribut, -1 si il n'existe pas
	 * @require attribut!=null
	 */
	public int indiceAttributs(String attribut) {
		if (attribut==null) throw new Require("Erreur attribut");
		int index = -1, i = 0;
		String s;
		for (Attribut a : listeAttributs) {
			if (a.nom().toUpperCase().equals(attribut.toUpperCase())) {
				index = i;
			}
			i++;
		}
		return index;
	}
	
	/**
	 * @return une liste contant tout les types du schema
	 */
	public ArrayList<String> types() {
		ArrayList<String> l = new ArrayList<String>();
		for (Attribut a : listeAttributs) {
			l.add(a.type());
		}
		return l;
	}
	
	/**
     * transforme un DOM en XML
     * @param doc le DOM a transformer
     * @param fichier le fichier ou ecrire
     * @throws java.lang.Exception
     * @require doc!=null && fichier!=null
     */
    public void sauvegarderSchema(Document doc, String fichier) throws Exception {
    	if (doc==null || fichier==null) throw new Require("Erreur sauvegarde");
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DocumentType dt = doc.getDoctype();
        if (dt != null) {
            String pub = dt.getPublicId();
            if (pub != null) {
                t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, pub);
            }
            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dt.getSystemId());
        }
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        Source source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(fichier));
        t.transform(source, result);
    }
	
	/**
	 * Methode qui charge un schema en memoire
	 * @param nomRelation la relation que l'on veut charger
	 * @require nomRelation!=null
	 */
	public void chargerSchema(String nomRelation) {
		if (nomRelation==null) throw new Require("Erreur nomRelation");
		Document doc;
		try {
			doc = fromXML(this.fichier);
			NodeList noeud = doc.getElementsByTagName("Relation");
			NodeList noeudAttribut;
			int taille = noeud.getLength();
			int taille2=0;
			String nom="", type = "",cle="";
			for (int i = 0; i < taille; i++) {
				if(noeud.item(i).getAttributes().item(0).getTextContent().equals(nomRelation)){
					Element e = (Element) noeud.item(i);
					noeudAttribut = e.getElementsByTagName("Attribut");
					taille2=noeudAttribut.getLength();
					for (int j = 0; j < taille2; j++) {
						nom = ((Element)noeudAttribut.item(j)).getAttribute("nom");
						type = ((Element)noeudAttribut.item(j)).getAttribute("type");
						cle = ((Element)noeudAttribut.item(j)).getAttribute("cle");
						listeAttributs.add(new Attribut(nom,type,cle));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Methode qui ajoute un attribut
     * @param a l'attribut a ajouter
     * @param nomRelation nom de la relation
     * @require a!=null && nomRelation!=null
     */
    public void ajouterAttribut(Attribut a, String nomRelation) {
    	if (a==null || nomRelation==null) throw new Require("Erreur ajout attribut");
    	Document doc;
		try {
			doc = fromXML(fichier);
			NodeList noeud = doc.getElementsByTagName("Relation");
	    	int taille = 0;
	    	//On cree un element Attribut
	        Element nouvelAttribut = doc.createElement("Attribut");
	        nouvelAttribut.setAttribute("nom", a.nom()); //On initialise l'attribut nom
	        nouvelAttribut.setAttribute("type", a.type()); //On initialise l'attrubut type
	        nouvelAttribut.setAttribute("cle", a.cleOuNon()); //On initialise l'attribut cle
	        //On ajoute notre element a notre relation
	        taille = noeud.getLength();
	        for (int i = 0; i < taille; i++) {
	        	if(noeud.item(i).getAttributes().item(0).getTextContent().equals(nomRelation)) {
	        		noeud.item(0).appendChild(nouvelAttribut);
	        	}   		
	        }
	        //sauvegarderSchema(doc,this.fichier);
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }


	/**
	 * Cree un DOM grace a un xml
	 * @param fileName le fichier XML a transformer
	 * @require fileName!=null
	 */
	public static Document fromXML(String fileName) throws Exception {
		if (fileName==null) throw new Require("Erreur fileName");
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document theDocument = (Document) builder.parse(new File(fileName));
		return theDocument;
	}

	/**
	 * @return la liste d'attribut
	 */
	public ArrayList<Attribut> attributs() { ArrayList<Attribut> l = this.listeAttributs; return l; }
}
