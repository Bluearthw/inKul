import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Cell[][] cells;
    private int mineNumber;
    private int column;
    private int row;
    private ArrayList<Integer> locations;

    private int[][] testCells;

    public Board(String level){//int w, int l, int m) {
        if(level.equals("e"))
        {
        column = 8;
        row = 8;
        mineNumber = 10;
        }
        else if(level.equals("n"))
        {
            column = 16;
            row = 16;
            mineNumber = 40;
        }
        else if(level.equals("h"))
        {
            column = 30;
            row = 16;
            mineNumber = 99;
        }
        cells = new Cell[row][column];
        locations = new ArrayList<>(row * column + 1);
//        testCells = new int[row][column];
    }

    public void boardInit(int r, int c) {

        minePlant(r, c);
        for (int l = 0; l < row; l++) {
            for (int w = 0; w < column; w++) {
                if (cells[l][w] == null) {
                    cells[l][w] = new NormalCell();

//                    testCells[l][w] = 0;
                }
//                else
//                    testCells[l][w] = 1;

            }
        }
        calculateMine();
        cells[r][c].setIsFlipped();

    }

    //翻开所有雷
    public void allMine() {
        for (int a : locations) {
            int currentRow = a / column;
            int currentColumn = a % row;
            cells[currentRow][currentColumn].setIsFlipped();
        }
    }

    //自动展开
    public void autoExpand(int l, int w) {
        if (cells[l][w].getMineAround() == 0) {
            for (int i = l - 1; i <= l + 1; i++) {
                if (i < 0 || i >= row)
                    continue;
                for (int j = w - 1; j <= w + 1; j++) { //for cells around
                    if (j < 0 || j >= column || cells[i][j].getIsFlipped())
                        continue;


                        cells[i][j].setIsFlipped();
                    if(cells[i][j].getMineAround() == 0)
                    autoExpand(i, j);
                }
            }
        }
    }

    //reset
    public void boardReset(){
        locations.clear();
        for (int l = 0; l < row; l++) {
            for (int w = 0; w < column; w++) { // g xiaosi
                cells[l][w].reset();
//                locations.clear();
            }
        }

    }

    //检测是否赢，用剩下的砖块和雷比较
    public boolean remain() {
        int count = 0;
        for (int l = 0; l < row; l++) {
            for (int w = 0; w < column; w++) {
                if (!cells[l][w].getIsFlipped()) {
                    count++;
                }
            }
        }
        if (count == mineNumber)
            return true;
        else
            return false;
    }

    public void calculateMine() {


        for (int l = 0; l < row; l++) {
            for (int w = 0; w < column; w++) {// for each cell
                int count = 0;
                for (int i = l - 1; i <= l + 1; i++) { // for surround cells
                    if (i < 0 || i >= row) // boundary judgment
                        continue;

                    for (int j = w - 1; j <= w + 1; j++) { //from left to right from top to down
                        if (j < 0 || j >= column) // boundary judgment
                            continue;

                        else if (cells[i][j].getIsMine())
                            count++;


                    }
                }
                cells[l][w].setMineAround(count);
            }
        }

    }
    //不要了
    public void flagOrNot(int row, int column) {
        if (cells[row][column].getIsFlag())
            System.out.println("IS FLAG");
        else
            System.out.println("IS NOT FLAG");
    }

    public void setCell() {
        // TODO - implement Board.setCell
        throw new UnsupportedOperationException();
    }

    public void minePlant(int r, int c) {
        SecureRandom ran = new SecureRandom();
        //get mine location

        for (int i = 0; i < mineNumber; i++) {
            int x = ran.nextInt(column * row - 1); // column * row - 1 is the max, nextInt range from 0 to bound
            if (!locations.contains(x) && x != r * column + c) {
                locations.add(x);
            } else
                i--;
        }
        //set mine
        for (int a : locations) {
            int currentRow = a / column;
            int currentColumn = a % row;
            cells[currentRow][currentColumn] = new MineCell();
            cells[currentRow][currentColumn].setIsMine();
        }
        //locations.clear();
    }

    public void explode() {
        System.out.println(" BOOOOMMM!!!!! ");
    }

    public void testRandom() {
        SecureRandom ran = new SecureRandom();
        for (int i = 0; i < 10; i++)
            System.out.println(ran.nextInt(123));
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getMineNumber() {
        return mineNumber;
    }


}