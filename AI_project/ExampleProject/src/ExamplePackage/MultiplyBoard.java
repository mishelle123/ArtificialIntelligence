package ExamplePackage;

public class MultiplyBoard {

	private final int ROWS = 10;
	private final int COLUMS = 10;
	
	private int rows;
	private int colums;
	private int[][] board;
	
	// default constructor
	public MultiplyBoard() {
		this.rows = ROWS;
		this.colums = COLUMS;
		this.board = new int[ROWS][COLUMS];
	}
	
	// overload constructor
	public MultiplyBoard(int userRows, int userColums) {
		this.rows = userRows;
		this.colums = userColums;
		this.board = new int[this.rows][this.colums];
	}
	
	public void createBoard()
	{
		for(int i = 0; i < this.rows; i++)
		{
			for(int j = 0; j < this.rows; j++)
			{
				board[i][j] = (i +1)*(j +1);
			}
		}
		
	}
	
	public void printBoard()
	{
		for(int i = 0; i < this.rows ; i++)
		{
			for(int j = 0; j < this.rows; j++)
			{
				System.out.print(this.board[i][j] + "  ");
			}
			System.out.println();
		}
	}
	
	public int getMultiply(int a, int b)
	{
		System.out.println(this.board[a][b]);
		return this.board[a][b];
	}
}
