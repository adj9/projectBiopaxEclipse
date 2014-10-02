package ui;


/**
 * Classe starter per eseguire il programma.
 * 
 * @author Alessandro
 *
 */
public class PatternProcessStarter {
	// entityName - array contenente i nome delle entità
	// patternName - array contenente i nomi dei pattern
	private static String[] entityName;
	private static String[]  patternName;
	
	/**
	 * Metodo per eseguire il parsing dei patametri da linea di comando.
	 * 
	 * @param arguments - parametri in input
	 */
	private static void setArguments(String[] arguments) {		
//		Dichiarazione delle variaabili per effettuare il parsing:
//			1.	entityNameNumber - numero di entità passate;
//			2.	patternNameNumber - numero di pattern passati;
//			3.	i - indice per i cicli for;
//			4.	searche - label per cercare il separanote dei parametri passati 		
		int entityNameNumber = 0;
		int patternNameNumber = 0;
		int i = 0;
		boolean searcher = false;
	
//		Ciclo for per sapere il numero di entità e di patternn passati al comandi.
//		Sono separati dal carttere "-"		
		for (i = 0; i < arguments.length; ++i) {
//			Test per verificare dove c'è il separatore			
			if (arguments[i].equals("-")) {
				searcher = true;
			}
			if (searcher) {
//				Incremento del numero di pattern dopo aver incontrato il carattere separatore 
				++patternNameNumber;
			} else {
//				Inclemento del numero di entità prima di aver incontrato il carattere separatore				
				++entityNameNumber;
			}
		}

		--patternNameNumber;
		
//		Creazione dell'arrray entityName
		entityName = new String[entityNameNumber];
		
//		Creazione dell'array patternName
		patternName = new String[patternNameNumber];

//		Ciclo for per caricare i nomi delle entità 		
		for (i = 0; i < entityNameNumber; ++i) {
			entityName[i] = arguments[i].toUpperCase();
		}
		
//		Ciclo for per caricare i nomi dei pattern, i nomi dei patter sono convetiti in maiuscolo
//		Nel caso i cui nel comando non ci sono pattern l'array è nullo.
		for (int j = i + 1; j < arguments.length; ++j) {
			patternName[j - entityNameNumber - 1] = arguments[j].toUpperCase();
		}
	}	
	
	/**
	 * Metodo main per lo starter del programma.
	 * 
	 * @param args - parametri in input
	 */
	public static void main(String[] args) {
		setArguments(args);
		PatternProcess starter = new PatternProcess(entityName, patternName);
		
		starter.starter();
	}
}
