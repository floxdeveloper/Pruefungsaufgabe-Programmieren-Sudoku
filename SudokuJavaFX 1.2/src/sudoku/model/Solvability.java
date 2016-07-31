package sudoku.model;


public enum Solvability {
  notEvaluated /**Nicht überprüft */,notSolvable/**Nachgewisen nicht losbar*/,Solvable /**Nachgewiesen lösbar*/,probablyNotSolvable/**mit sehr hoher Wahrscheinlichkeit nicht lösbar, nicht bewiesen*/,uniquelySolvable /**nachgewiesen nur auf eine Art lösbar*/,notUniquelySolvable/**nachgewiesen nicht nur auf eine Art lösbar*/
}
