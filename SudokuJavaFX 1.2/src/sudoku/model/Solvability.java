package sudoku.model;

/**
 * Diese Enumeration bildet die einzelnen L�sbarkeitsstufen ab.
 * 
 * @author Tobias Berner, Yvette Labastille, William Riyadi, Florian St�ckl
 */
public enum Solvability {
  notEvaluated /**Nicht �berpr�ft */,
  notSolvable/**Nachgewiesen nicht l�sbar*/,
  solvable /**Nachgewiesen l�sbar*/,
  probablyNotSolvable/**mit sehr hoher Wahrscheinlichkeit nicht l�sbar, nicht bewiesen*/,
  uniquelySolvable /**nachgewiesen nur auf eine Art l�sbar*/,
  notUniquelySolvable/**nachgewiesen nicht nur auf eine Art l�sbar*/
}
