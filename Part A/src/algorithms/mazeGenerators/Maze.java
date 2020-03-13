package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * the Maze class represents a maze.
 * any maze has a start position and an end position.
 * the value 1 will represent a wall in the maze and the value 0 will represents a free cell.
 */

public class Maze implements Serializable {

    private int [][] maze;
    private int rows, columns;
    private Position startPosition, goalPosition;

    public Maze(int rows, int columns) {
        if (rows <= 0 || columns <=0){ // in case of illegal input, the size of default maze will be 1X1
            this.rows = 1;
            this.columns =1;
        }
        else{
            this.rows = rows;
            this.columns = columns;
        }
        maze = new int [this.rows][this.columns];
        startPosition = new Position (0,0);
        goalPosition = new Position(rows-1, columns -1);
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                maze[i][j]= 1;
            }
        }
    }

    public Maze(byte[] b){
        this.rows = unite(b[0],b[1]);
        this.columns = unite(b[2],b[3]);
        startPosition = new Position (unite(b[4],b[5]),unite(b[6],b[7]));
        goalPosition = new Position(unite(b[8],b[9]), unite(b[10],b[11]));

        maze = new int[rows][columns];
        int bCounter = 12, index = 0 ;
        int[] binaryNum = convertToBinary(b[bCounter], 8);

        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                maze[i][j] = binaryNum[index];
                if(index==7){
                    index=0;
                    bCounter++;
                    binaryNum = convertToBinary(b[bCounter], 8);
                }
                else{
                    index++;
                }
            }
        }

    }

    public void setCell(int row, int column, int value){
        if ( row<0 || row>this.rows || column<0 || column>this.columns){
            return;
        }
        maze[row][column] = value;
    }

    public int getCellValue (int row, int column){
        return maze[row][column];
    }

    public int getCellValue(Position p){
        return maze[p.getRowIndex()][p.getColumnIndex()];
    }

    public Position getStartPosition(){
        return startPosition;
    }

    public Position getGoalPosition(){
        return goalPosition;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setStartPosition(int row, int col){
        startPosition = new Position(row, col);
    }

/*
    public void print (){
        for (int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex()){
                    System.out.print("S");
                }
                else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex()){
                    System.out.print("E");
                }
                else{
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }
*/

    public void print () {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex()) {//startPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex()) {//goalPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (maze[i][j] == 1) System.out.print(" " + "\u001B[45m" + " ");
                else System.out.print(" " + "\u001B[107m" + " ");
            }
            System.out.println(" " + "\u001B[107m");
        }

    }
    public byte[] toByteArray(){
        byte [] res = new byte[13+ (rows*columns)/8];
        int[] row = splitToTwoBytes (rows);
        res[0] = (byte) row[0];
        res[1] = (byte) row[1];
        int[] col = splitToTwoBytes (columns);
        res[2] = (byte) col[0];
        res[3] = (byte) col[1];
        int[] startRow = splitToTwoBytes (startPosition.getRowIndex());
        res[4] = (byte) startRow[0];
        res[5] = (byte) startRow[1];
        int[] startCol = splitToTwoBytes (startPosition.getColumnIndex());
        res[6] = (byte) startCol[0];
        res[7] = (byte) startCol[1];
        int[] goalRow = splitToTwoBytes (goalPosition.getRowIndex());
        res[8] = (byte) goalRow[0];
        res[9] = (byte) goalRow[1];
        int[] goalCol = splitToTwoBytes (goalPosition.getColumnIndex());
        res[10] = (byte) goalCol[0];
        res[11] = (byte) goalCol[1];

        int resIndex = 12;
        int counter =0;
        int[] num = new int[8];
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                num[counter]= maze[i][j];
                if(counter ==7) {
                    counter = 0;
                    int intNum = convertToInteger(num, 0, 7);
                    res[resIndex] = (byte)intNum;
                    resIndex++;
                }
                else
                    counter++;
            }
        }
        if(counter<7){
            for(int i=counter; i<8; i++){
                num[i] =0;
            }
            int intNum = convertToInteger(num, 0, 7);
            res[resIndex] = (byte)intNum;
        }

        return res;
    }

    private int[] splitToTwoBytes (int num){
        int []res = new int[2];
        int [] binaryNum = convertToBinary(num, 16);
        res[0] = convertToInteger(binaryNum,0,7);
        res[1] = convertToInteger(binaryNum,8,15);
        return res;
    }

    private int[] convertToBinary(int num, int size) {
        boolean negative = false;
        boolean done = false;
        int[] res = new int[size];
        if (num<0) {
            negative = true;
            num = num*(-1);
        }
        for (int i=size-1; i>=0; i--){
            if(Math.pow(2,i)>num){
                res[i] =0;
            }
            else {
                res[i]=1;
                num = num-(int)Math.pow(2,i);
            }
        }
        if(negative){
            for(int i=0; i<res.length; i++){
                if(res[i] ==0 )
                    res[i]=1;
                else
                    res[i]=0;
            }
            int index =  0;
            while (!done){
                if(res[index]==0){
                    done= true;
                    res[index] = 1;
                }
                else{
                    res[index] = 0;
                }
                index++;
            }
        }
        return res;
    }

    private int convertToInteger(int[] binaryNum, int start, int end) {
        int res=0;
        int index =0;
        for(int i=start;i<end; i++){
            res = res + (int)Math.pow(2,index) * binaryNum[i];
            index++;
        }
        res= res -(int)Math.pow(2,index) * binaryNum[end];
        return res;
    }

    private int unite(int num1, int num2) {
        int[] binary1 = convertToBinary(num1,8);
        int[] binary2 = convertToBinary(num2, 8);

        int[] binaryNum = new int[16];

        for (int i = 0; i < 8; i++) {
            binaryNum[i] = binary1[i];
            binaryNum[i + 8] = binary2[i];
        }

        return convertToInteger(binaryNum,0,15);
    }
}
