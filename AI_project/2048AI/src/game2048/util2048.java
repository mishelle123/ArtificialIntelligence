package game2048;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;





public class util2048 {
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	//maps the action to a string
	public static final Map<Integer, String> INT_TO_ACTION = createMap();
	private static Map<Integer, String> createMap() {
		Map<Integer, String> aMap = new HashMap<>();
		aMap.put(0, UP);
		aMap.put(1, DOWN);
		aMap.put(2, LEFT);
		aMap.put(3, RIGHT);
		return Collections.unmodifiableMap(aMap);
	}

	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @param score
	 * @return board representation
	 */
	public static void printMatrix(int[] board, int boardSize, int[]score){
		System.out.println("score: " + score[0]);
		for(int i=0; i < boardSize;i++){
			for (int j=0; j < boardSize; j++){
				//calculate the row and column position
				System.out.print(board[j + i*boardSize]+" ");
			}
			System.out.println();
		}
	}

	/**
	 * 
	 * @param board
	 * @return copy of the board
	 */
	public static int[] getBoardCopy(int[] board){
		int[] copy = new int[board.length];
		System.arraycopy(board, 0, copy, 0, board.length);
		return copy;
	}

	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @return true if the game is over
	 */
	public static boolean isGameOver(int[] board, int boardSize){

		if (getEmptyCells(board).length != 0) {
			return false;
		}
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				int t = valueAt(board, boardSize, x, y);
				//checks if there are neighbors with the same value
				if ((x < (boardSize - 1) && t == valueAt(board, boardSize, x + 1, y))
						|| ((y < (boardSize - 1)) && t == valueAt(board, boardSize, x, y+1))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @param score
	 * @return player successors a successor is an int[][] like this - {board,score,action}
	 */
	public static List<int[][]> getPlayerSuccessors(int[] board, int boardSize, int[]score){
		ArrayList<int [][]> successors = new ArrayList<>(4);

		for (int i=0; i < 4; i++){
			int[] copy = getBoardCopy(board);
			int[] copyScore = {score[0]};
			if(doMove(copy, boardSize, copyScore, INT_TO_ACTION.get(new Integer(i)))){
				// generate successor(boardCopy, score, action)
				int[][] succ = {copy, copyScore, {i}};
				successors.add(succ);
			}

		}
		return successors;	
	}
	/**
	 * 
	 * @param board
	 * @param score
	 * @return computer successors a successor is an int[][] like this - {board,score,action}
	 */
	public static List<int[][]> getComputerSuccessors(int[] board, int[] score){
		ArrayList<int [][]> successors = new ArrayList<>(4);
		int[] values = {2,4};
		for (int v: values){
			for (int p : util2048.getEmptyCells(board) ){
				int[] copy = getBoardCopy(board);
				copy[p] = v;
				// generate successor(boardCopy, score, action)
				int[][] succ = {copy, {score[0]}, {v}};
				successors.add(succ);
			}
		}

		return successors;	

	}

	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @param x
	 * @param y
	 * @return the value at the given position
	 */
	public static int valueAt(int[] board, int boardSize, int x, int y){
		return board[x + y * boardSize];
	}

	/**
	 * 
	 * @param board
	 * @return array of indices of available space 
	 */
	public static int[] getEmptyCells(int[] board) {
		ArrayList<Integer> list = new ArrayList<>(16);
		for (int i=0; i < board.length; i++) {
			if (board[i] == 0) {
				list.add(i);
			}
		}
		int[] a = new int[list.size()];
		for (int i=0; i< list.size(); i++){
			a[i] = list.get(i).intValue();
		}
		return a;
	}

	/**
	 * 
	 * @param b1
	 * @param b2
	 * @return true if the boards are equal
	 */
	public static boolean compareBoards(int[] b1, int[] b2){ 
		return Arrays.equals(b1, b2);
	}

	/**
	 * 
	 * @param board - update the board
	 * @param boardSize
	 * @param score - update the score
	 * @param move
	 * @return true if an action has performed
	 */
	public static boolean doMove(int[] board, int boardSize, int[] score, String move){
		if (!isGameOver(board, boardSize)) {
			//perform the action
			switch (move) {
			case LEFT:
				return left(board, boardSize, score);
			case RIGHT:
				return right(board, boardSize, score);
			case DOWN:
				return down(board, boardSize, score);
			case UP:
				return up(board, boardSize, score);
			}

		}
		return false;

	}

	/**
	 * 
	 * @param board - update the board
	 * @param boardSize
	 * @param score
	 * @return true if the move "left" performed
	 */
	public static boolean left(int[] board, int boardSize, int[] score) {
		boolean moved = false;
		for (int i = 0; i < boardSize; i++) {
			int[] line = getRow(board, boardSize, i);
			int[] merged = mergeLine(boardSize, moveLine(boardSize, line), score);
			setLine(board, boardSize, i, merged);
			if (!moved && !compareBoards(line, merged)) {
				moved = true;
			}
		}
		return moved;
	}

	/**
	 * 
	 * @param board - update the board
	 * @param boardSize
	 * @param score
	 * @return true if the move "right" performed
	 */
	public static boolean right(int[] board, int boardSize, int[] score) {
		rotate(board, boardSize, 180);
		boolean moved = left(board, boardSize, score);
		rotate(board, boardSize, 180);
		return moved;
	}

	/**
	 * 
	 * @param board - update the board
	 * @param boardSize
	 * @param score
	 * @return true if the move "up" performed
	 */
	public static boolean up(int[] board, int boardSize, int[] score) {
		rotate(board, boardSize, 270);
		boolean moved = left(board, boardSize, score);
		rotate(board, boardSize, 90);
		return moved;
	}

	/**
	 * 
	 * @param board - update the board
	 * @param boardSize
	 * @param score
	 * @return true if the move "down" performed
	 */
	public static boolean down(int[] board, int boardSize, int[] score) {
		rotate(board, boardSize, 90);
		boolean moved = left(board, boardSize, score);
		rotate(board, boardSize, 270);
		return moved;
	}
	/*
	 * rotate the board
	 */
	private static void rotate(int[]board, int boardSize, int angle) {
		int[] newBoard = new int[boardSize * boardSize];
		int offsetX = boardSize - 1, offsetY = boardSize - 1;
		if (angle == 90) {
			offsetY = 0;
		} else if (angle == 270) {
			offsetX = 0;
		}else if (angle == 0){
			offsetX = offsetY = 0;
		}

		double rad = Math.toRadians(angle);
		int cos = (int) Math.cos(rad);
		int sin = (int) Math.sin(rad);
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				int newX = (x * cos) - (y * sin) + offsetX;
				int newY = (x * sin) + (y * cos) + offsetY;
				newBoard[(newX) + (newY) * boardSize] = valueAt(board, boardSize, x, y);
			}
		}
		System.arraycopy(newBoard, 0, board, 0, boardSize*boardSize);
	}
	/*
	 * generate the moved line 
	 */
	private static int[] moveLine(int boardSize, int[] oldLine) {
		LinkedList<Integer> l = new LinkedList<>();
		// search for values != 0
		for (int i = 0; i < boardSize; i++) {
			if (oldLine[i]!=0){	
				l.addLast(oldLine[i]);
			}
		}
		if (l.size() == 0) {
			return oldLine;
		} else {
			int[] newLine = new int[boardSize];
			// fill in with 0
			ensureSize(boardSize, l);
			for (int i = 0; i < boardSize; i++) {
				// pop
				newLine[i] = l.removeFirst().intValue();
			}
			return newLine;
		}
	}

	/*
	 * merge the equal tiles
	 */
	private static int[] mergeLine(int boardSize, int[] oldLine, int[] score) {
		LinkedList<Integer> list = new LinkedList<>();
		for (int i = 0; (i < boardSize) && !(oldLine[i]==0); i++) {
			int num = oldLine[i];
			if (i < (boardSize - 1) && oldLine[i] == oldLine[i + 1]) {
				num *= 2;
				score[0] += num;

				i++;
			}
			list.add(new Integer(num));
		}
		if (list.size() == 0) {
			return oldLine;
		} else {
			ensureSize(boardSize, list);
			int[] result = new int[boardSize];
			for (int i=0; i< list.size(); i++){
				result[i] = list.get(i).intValue();
			}
			return result;
		}
	}

	/*
	 * fill in with 0
	 */
	private static void ensureSize(int boardSize, java.util.List<Integer> l) {
		while (l.size() != boardSize) {
			l.add(0);
		}
	}

	/*
	 * return the line from the board
	 */
	private static int[] getRow(int[]board, int boardSize, int index) { 
		int[] result = new int[boardSize];
		for (int i = 0; i < boardSize; i++) {
			result[i] = valueAt(board, boardSize, i, index);
		}
		return result;
	}

	private static int[] getColumn(int[]board, int boardSize, int index) { 
		int[] result = new int[boardSize];
		for (int i = 0; i < boardSize; i++) {
			result[i] = valueAt(board, boardSize, index, i);
		}
		return result;
	}
	/*
	 * copy the line to the board
	 */
	private static void setLine(int[]board, int boardSize, int index, int[] line) {
		System.arraycopy(line, 0, board, index * boardSize, boardSize);
	}

	/**
	 * 
	 * @param board
	 * @return max value at the board
	 */
	public static int[] getMax(int[] board) {
		int [] max = {0,0};

		for(int i=0; i<board.length; i++) {
			if(board[i] >= max[1]) {
				// index and value
				max[0] = i;
				max[1] = board[i];
			}
		}
		return max;
	}

	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @return monotonicity evaluation of the board
	 */
	public static double getMonotonicityValue(int[] board, int boardSize) {
		double leftToRight = 0;
		double rightToLeft = 0;
		double upToDown = 0;
		double downToUp = 0;
		int[] row ;
		int[] column ;
		for(int i=0; i<boardSize; i++) {
			row = getRow(board, boardSize, i);
			for(int j=0;j<boardSize-1;j++){


				double current =  row[j];

				double neighbor =  row[j+1];

				if(current < neighbor){
					leftToRight += neighbor - current;
				} else{
					rightToLeft += current - neighbor;
				}

			}
		}
		for(int i=0; i<boardSize; i++) {
			column = getColumn(board, boardSize, i);
			for(int j=0;j<boardSize-1;j++){


				double current =  column[j];

				double neighbor =  column[j+1];

				if(current < neighbor){
					leftToRight += neighbor - current;
				} else{
					rightToLeft += current - neighbor;
				}
			}
		}

		return Math.max(rightToLeft, leftToRight) + Math.max(downToUp, upToDown);

	}

	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @return evaluation of the tiles in corners 
	 */
	public static double getCornerValue(int[] board, int boardSize){ 
		int[] weightBoard = new int[boardSize * boardSize];
		int maxCorner = 0; 
		// assign numbers to the board in which corners get the max number
		for (int i=0; i < boardSize; i++){
			int w = (boardSize - 1)  - i;
			for (int j=0; j < boardSize; j++){
				int pow = ((w-j)==0) ? 0 : (int) Math.pow(2, (Math.abs(w-j)-1));
				weightBoard[i + j*boardSize] = (w-j > 0) ? pow : (-pow);
			}
		}
		//		int[] score ={0};
		//		printMatrix(weightBoard, boardSize,score);
		int angel = 90;
		// sum the value*tileNumber and rotate the board
		for (int i=0; i < 4; i++){
			int v = 0;
			rotate(weightBoard, boardSize, angel * i); 
			for(int j=0; j < board.length; j++){
				double tVal = board[j];

				v += tVal * weightBoard[j]; 
			}
			if (v > maxCorner){
				maxCorner = v;
			}
		}
		return maxCorner;


	}
	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @return minus the sum of the abstract value f the difference between neighbor tiles
	 */
	public static double getDiffernceValue(int[] board, int boardSize){
		double diffVal = 0;
		for (int x=0; x < boardSize; x++){
			for (int y=0; y < boardSize; y++){
				int t = valueAt(board, boardSize, x, y);
				double current =  t;

				if (y + 1 < boardSize){
					double neighbor =  valueAt(board, boardSize, x, y + 1);

					diffVal += Math.abs(current - neighbor);
				}
				if (x + 1 < boardSize){
					double neighbor =  valueAt(board, boardSize, x + 1, y);

					diffVal += Math.abs(current - neighbor);
				}
			}
		}

		return -diffVal;


	}
	/**
	 * 
	 * @param board
	 * @param boardSize
	 * @return a value that represent how snake like the board is.
	 */
	public static double getSnakeValue(int[] board, int boardSize) {



		double upLeftToRight = 0;
		double upRightToLeft = 0;
		double downLeftToRight = 0;
		double downRightToLeft = 0;
		double leftUpToDown = 0;
		double leftDownToUp = 0;
		double rightUpToDown = 0;
		double rightDownToUp = 0;
		int[] row ;
		int[] column ;
		//		double tileValueMinus = 2^(boardSize^2);
		double tileValueMinus = Math.pow(2, Math.pow(boardSize, 2));

		double tileValuePlus = 2;
		for(int i=0; i<boardSize; i++) {
			row = getRow(board, boardSize, i);
			if(i%2 == 0){
				for (int x=0; x<boardSize; x++)
				{
					upLeftToRight += tileValueMinus*row[x];
					upRightToLeft += tileValueMinus*row[boardSize-x-1];
					downLeftToRight += tileValuePlus*row[x];
					downRightToLeft += tileValuePlus*row[boardSize-x-1];
					tileValueMinus = tileValueMinus/2;
					tileValuePlus = tileValuePlus*2;
				}

			}else{
				for (int x=0; x<boardSize; x++)
				{					
					upLeftToRight += tileValueMinus*row[boardSize-x-1];
					upRightToLeft += tileValueMinus*row[x];
					downLeftToRight += tileValuePlus*row[boardSize-x-1];
					downRightToLeft += tileValuePlus*row[x];	
					tileValueMinus = tileValueMinus/2;
					tileValuePlus = tileValuePlus*2;
				}
			}
		}

		//		tileValueMinus = 2^(boardSize^2);
		tileValueMinus = Math.pow(2, Math.pow(boardSize, 2));

		tileValuePlus = 2;
		for(int i=0; i<boardSize; i++) {
			column = getColumn(board, boardSize, i);

			if(i%2 == 0){
				for (int x=0; x<boardSize; x++)
				{
					leftUpToDown += tileValueMinus*column[x];
					leftDownToUp += tileValueMinus*column[boardSize-x-1];
					rightUpToDown += tileValuePlus*column[x];
					rightDownToUp += tileValuePlus*column[boardSize-x-1];

					tileValueMinus = tileValueMinus/2;
					tileValuePlus = tileValuePlus*2;
				}
			}else{
				for (int x=0; x<boardSize; x++)
				{
					leftUpToDown += tileValueMinus*column[boardSize-x-1];
					leftDownToUp += tileValueMinus*column[x];
					rightUpToDown += tileValuePlus*column[boardSize-x-1];
					rightDownToUp += tileValuePlus*column[x];	

					tileValueMinus = tileValueMinus/2;
					tileValuePlus = tileValuePlus*2;
				}
			}
		}
		double max = Math.max(Math.max(Math.max(upLeftToRight, upRightToLeft), 
				Math.max(downLeftToRight, downRightToLeft)), 
				Math.max(Math.max(leftUpToDown, leftDownToUp), 
						Math.max(rightUpToDown, rightDownToUp)));

		

		return max/(Math.pow(2, Math.pow(boardSize, 2)-2));

		//
		//		double max = 0; 
		//		int[] row1 = new int[boardSize];
		//		int[] weightBoard1 = new int[board.length];
		//		int[] row2 = new int[boardSize];
		//		int[] weightBoard2 = new int[board.length];
		//		for(int i=0; i < boardSize; i++){
		//			if(i%2 == 0){
		//				for (int j=0; j < boardSize; j++){
		//					row1[j] = (int) Math.pow(2, (i*boardSize)+j+1);
		//					row2[boardSize - j - 1] = (int) Math.pow(2, (i*boardSize)+j+1);
		//				}
		//			}else{
		//				for (int j=0; j < boardSize; j++){
		//					row1[boardSize - j - 1] = (int) Math.pow(2, (i*boardSize)+j+1);
		//					row2[j] = (int) Math.pow(2, (i*boardSize)+j+1);
		//				}
		//			}
		//			setLine(weightBoard1, boardSize, i, row1);
		//			setLine(weightBoard2, boardSize, i, row2);
		//		}
		//		//		int[] score = {0};
		//		//		printMatrix(weightBoard1, boardSize, score);
		//		//		printMatrix(weightBoard2, boardSize, score);
		//
		//		int angel = 90;
		//		// sum the value*tileNumber and rotate the board
		//		for (int i=0; i < 4; i++){
		//			double v = 0;
		//			double u = 0;
		//			rotate(weightBoard1, boardSize, angel * i); 
		//			rotate(weightBoard2, boardSize, angel * i); 
		//			for(int j=0; j < board.length; j++){
		//				double tVal = board[j];	
		//				v += tVal * weightBoard1[j]; 
		//				u += tVal * weightBoard2[j]; 
		//			}
		//			max = Math.max(max, Math.max(u, v));
		//		}
		//		//				return max;
		//
		//		return max/(Math.pow(2, Math.pow(boardSize, 2)-2));

	}

}
