import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.math.BigInteger;

public class Sudoku extends JComponent{
    private String color; 
    private int[][] board;
    private int[][] og;
    private boolean check;
    private boolean red;
    private Random randgen;
    private long seed; 
    private int difficulty; //0 -  4 (super easy - super hard)


    //default difficulty is medium
    public Sudoku () {
	this (2, (long)(Math.random()* 999999999), false, false);
    }

    public Sudoku (int[][] gameboard) {
	this ();
        this.setBoard(gameboard);
	og = new int[9][9];
        for (int i = 0; i < 9; i ++){
	    for (int j = 0; j < 9; j ++){
		og[i][j] = board[i][j];
	    }
	}
	color = "Black";
	check = false;
	red = false; 
    }

    public Sudoku (int[][] gameboard, boolean temp) {
	this (2 ,(long)(Math.random()* 999999999), false, temp);
	this.setBoard(gameboard);
        og = new int[9][9];
	og = gameboard;
	color = "Black";
	check = false;
	red = false; 
    } 

    public Sudoku (int diff, boolean showKey) { 
	this (diff, (long)(Math.random()* 999999999), showKey, false); 
    }

    public Sudoku (int diff) {
	this (diff, false);
    }

    public Sudoku (boolean showKey) { 
	this (2, showKey); 
    }


    public Sudoku (int diff, long seed) {
	this (diff, seed, false, false);
    }

    public Sudoku (String empty) {
	this();
	if (empty.equals("empty")) {
	    clear();
	    clearOg();
	    
	}
    }


    public Sudoku (int diff, long seed, boolean showKey, boolean temp) {
	board = new int[9][9];
	difficulty = diff;
	this.seed = seed;
	randgen = new Random(seed);
	clear();
	if (!temp) { 
	    this.fillWithNumbers();
	    if (!showKey) {
		this.removeMultiple();
	    }
	}
	og = new int[9][9];
	for (int i = 0; i < 9; i ++){
	    for (int j = 0; j < 9; j ++){
		og[i][j] = board[i][j];
	    }
	}
	color = "Black";
	check = false;
	red = false;
	}

	
    //Set all values on the board to 10
    public void clear(){		
	for (int row = 0; row < board.length; row ++) {
	    for ( int column = 0; column < board[row].length; column ++) {
		board[row][column] = 10;
	    }
	}
    }

    public void clearOg() {
	for (int row = 0; row < board.length; row ++) {
	    for ( int column = 0; column < board[row].length; column ++) {
		og[row][column] = 10;
	    }
	}
    }

    

    public boolean full () {
	for (int r = 0; r < board.length; r ++) {
	    for (int c = 0; c < board[r].length; c ++) {
		if (board[r][c] == 10) {
		    return false;
		} 
	    }
	}
	return true;
    } 

    public void setRed (boolean red) {
	this.red = red;
    }

    public boolean getRed () {
	return red;
    }

    public int getNum(int r, int c){
	return board[r][c];
     }

    public void setNum(int r, int c, int x){
	board[r][c] = x;
    }

    public void setCheck(boolean x){
	check = x;
    }

    public boolean getCheck(){
	return check;
    }

    public int getSeed(){
	return (int) this.seed;
    }
    
    public int ogNum(int r, int c){
	return og[r][c];
    }

    public String getColor(){
	return color;
    }

    public void setColor(String c){
	color = c;
    }

    public int[][] getOG(){ return og; }


    public int[][] getBoard() { return board; }

    public void setBoard(int[][] board) {

	for (int r= 0; r < board.length; r ++) {
	    for (int c = 0; c < board[r].length; c ++) {
	       this.board[r][c] = board[r][c];
	    }
	}
    }

    public int getDifficulty () {return difficulty; }

    public void setDifficulty (int diff) { difficulty = diff; }

  
    public  boolean equals (Sudoku s) {
	int[][] otherboard = s.getBoard();
	for (int r= 0; r < board.length; r ++) {
	    for (int c = 0; c < board[r].length; c ++) {
		if (board[r][c] != otherboard[r][c]) {
		    return false;
		}
	    }
	}
	return true;
    }

	
    
    
    //max sum is 45
    public int sumRow (int r) {
	return sumRow (r, 0, board[r].length);
    }

    //non inclusive of endcol
    public  int sumRow (int r, int startcol, int endcol) {
	int sum = 0;
	int[] row = board[r];
	for (int index = startcol; index < endcol; index ++) {
	    if (row[index] != 10) {
		sum += row[index];
	    }
	}

	return sum;
    }

    public int sumCol (int col) {
	return sumCol (col, board.length );
    }

    //noninclusive of endrow
    public  int sumCol (int col, int endrow) {
	int sum = 0;
	for (int row = 0; row < endrow; row ++) {
	    if (board[row][col] != 10) {
		sum += board[row][col];
	    }
	} 
	return sum;
    }

    public  int sumSquare (int row, int col) {
	int sum = 0;
	int square = findSquare ( row,  col);
	int firstrow = (row < 3) ? 0 : ( (row > 5) ? 6: 3); 
	int firstcol = (col < 3) ? 0 : ( (col > 5) ? 6: 3);
	for (int r = firstrow; r < firstrow + 3; r ++) {
	    sum += sumRow(r, firstcol ,firstcol + 3);
	}		   
	return sum;
    }


    public static int findSquare (int row, int col) {
	int square;
	if (row  < 3) {
	    square = ( col < 3) ? 1 : ( (col > 5) ?  3: 2);	    
	}else if (row > 5) {
	    square = ( col < 3) ? 7 : ( (col > 5) ?  9: 8);
	}else {
	    square = ( col < 3) ? 4 : ( (col > 5) ?  6: 5);
	}
	return square;
    }


    public static boolean isSolvable (Sudoku a) {
	Sudoku s = new Sudoku (a.getBoard(), true);
	SudokuSolver.solveSudoku(s);
	//System.nout.println ("Solveda " + a);
	//System.out.println ("Solveds " + s);
	if (SudokuSolver.validSums(s)) {
	    // System.out.println ("issolvable " + a) ;
	    return true; 
	}
	return false; 
    }
      
    public boolean addNumber () {
	    int num =  Math.abs(randgen.nextInt() % 9) + 1;
	    for (int r = 0; r < board.length; r ++) {
		for (int c = 0; c < board[r].length; c ++) {
		    if (board[r][c] != 10) {
			continue;
		    } 
		    while (!SudokuSolver.isLegit(this,r,c,num)) {
			num =  Math.abs(randgen.nextInt() % 9) + 1;
		    }
		    board[r][c] = num;
		    if (isSolvable(this)) {
			return true;
		    } else {
			board[r][c] = 10;
			addNumber(); 
		    }
		}   
    
	    }
	    return false;
    }
    


    public void  fillWithNumbers () {
	for (int i = 0; i < 81; i ++) {
	    addNumber();
	}
	//SudokuSolver.solveSudoku(this);	      	
    }

    public static  boolean isUnique (Sudoku s, int r, int c) {
	int count = 0;
	int temp = s.getBoard()[r][c];
	for (int num = 1; num < 10; num ++) {
	    s.getBoard()[r][c] = num;
	    if (isSolvable(s)) {
		count ++;
	    }
	}
	s.getBoard()[r][c] = temp;
	if (count > 1) {
	    return false;
	}

	return true;
    }

  


    public boolean removeNumber () {
	int row = Math.abs(randgen.nextInt()) % 9;
	int col = Math.abs(randgen.nextInt()) % 9;
	int temp = board[row][col];
	board[row][col] = 10;
	if (isSolvable(this) && isUnique (this,row,col)) { 
	    return true;
	} else  {
	    board[row][col] = temp;
	    removeNumber();
	}
	return false;
    }

    public void removeMultiple () {
	int numstoremove = 0;
	//Super Easy: < 31 removed
	//Easy: 32 - 45 removed
	//Medium: 46 - 49 removed
	//Hard: 50 - 53 removed
	//Super Hard: 55 - 59 removed
	if (difficulty == 0) {
	    numstoremove = Math.abs(randgen.nextInt()) % 30 + 1;
	}else if (difficulty == 1) { 
	    numstoremove = Math.abs(randgen.nextInt()) % 8 + 32;
	} else if (difficulty == 2) {
	     numstoremove = Math.abs(randgen.nextInt()) % 4 + 46;
	} else if (difficulty == 3) {
	    numstoremove = Math.abs(randgen.nextInt()) % 4 + 50;
	}else {
	    numstoremove = Math.abs(randgen.nextInt()) % 5  + 55;
	}

	for (int i = 0; i < numstoremove; i ++) {
	    removeNumber ();
	}
    }

    public String toString () {
	String str = "Seed: " + seed + "\n";
	for (int r = 0 ; r < board.length; r ++) {
	    for (int c = 0; c < board[r].length; c ++) {
		if (board[r][c] == 10) { 
		    str = str + "  ";  
		} else 
		    str = str +  board[r][c] + " ";
		}			       

	    str += "\n";
    }
	str += "\n\n";
	return str; 
    }

    public static void main (String[] args) {
 
	//	SudokuSolver.solveSudoku(a);
	//System.out.println (a);
	//System.out.println (SudokuSolver.validSums(a));
	
	
    } 

}
