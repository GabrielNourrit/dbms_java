package SGBD.BDD;

import java.util.Comparator;

public interface Type<T> {
	
	/** g�n�rateur al�atoire d'un �l�ment du type
	 * @return une valeur d'un type
	 */
	//public T newInstance(T v);
	/** restitue l'op�rateur de comparaison du Triable
	 * @return un comparator
	 */
	public Comparator<T> comparator();
	/** restitue la repr�sentation textuelle de la
	 * valeur servant dans la relation d'ordre.
	 * @return une chaine
	 */
	public String toString();
}
