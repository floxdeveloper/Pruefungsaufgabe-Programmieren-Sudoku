package sudoku.model;

/**
 * Diese Enumeration bildet die einzelnen Lösbarkeitsstufen auf Strings ab.
 */
public enum Solvability {
  notEvaluated /**Nicht überprüft */,
  notSolvable/**Nachgewiesen nicht lösbar*/,
  solvable /**Nachgewiesen lösbar*/,
  probablyNotSolvable/**mit sehr hoher Wahrscheinlichkeit nicht lösbar, nicht bewiesen*/,
  uniquelySolvable /**nachgewiesen nur auf eine Art lösbar*/,
  notUniquelySolvable/**nachgewiesen nicht nur auf eine Art lösbar*/
}
