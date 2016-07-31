package sudoku.model;


public enum Solvability {
  notEvaluated /**Nicht �berpr�ft */,notSolvable/**Nachgewisen nicht losbar*/,Solvable /**Nachgewiesen l�sbar*/,probablyNotSolvable/**mit sehr hoher Wahrscheinlichkeit nicht l�sbar, nicht bewiesen*/,uniquelySolvable /**nachgewiesen nur auf eine Art l�sbar*/,notUniquelySolvable/**nachgewiesen nicht nur auf eine Art l�sbar*/
}
