package SGBD.AnalyseurSyntaxique;
/**
 * Class permettant de recuperer les contraintes et le type de contraintes
 * @author traor
 *
 */
public class Condition {
	public String typeCondition;	//and or
	public String[][] contraintes;
	public Condition(String t, String[][] c) {
		typeCondition=t;
		contraintes=c;
	}

}
