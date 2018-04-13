import java.awt.*;

/**
 * Created by hubertplewa on 4/28/17.
 */
class Maze
{
    private GridDisplay disp;
    private char[][] board;
    private boolean[][] visited;
    private int rows;
    private int cols;

    private int curX;
    private int curY;

    private int startRow;
    private int startCol;

    private int endRow;
    private int endCol;

    private Move getStart(){ return new Move(startRow, startCol); }
    private Move getEnd(){ return new Move(endRow, endCol); }

    private int getRows(){ return  rows; }
    private int getCols(){ return  cols; }

    private boolean getVisited(int x, int y){ return visited[x][y]; }
    private void setVisited(int x, int y) { visited[x][y] = true; }

    private void setCurX(int x) { curX = x; }
    private int getCurX(){ return curX; }

    private void setCurY(int y){  curY = y; }
    private int getCurY(){ return curY; }

    public Maze(int row, int col)
    {
        rows = row;
        cols = col;
        disp = new GridDisplay(rows, cols);
        board = new char[rows][cols];
        visited = new boolean[rows][cols];

        // initialize visited
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                visited[i][j] = false;
            }
        }

        //Top & bottom borders
        for(int j = 0; j < cols; j++) {
            disp.setColor(0, j, Color.black);
            board[0][j] = '*';

            disp.setColor(rows-1, j, Color.black);
            board[rows-1][j] = '*';
        }

        //Side borders
        for(int i = 1; i < rows - 1; i++){
            disp.setColor(i, 0, Color.black);
            board[i][0] = '*';


            disp.setColor(i, cols-1, Color.black);
            board[i][cols-1] = '*';
        }

        // Fill with blocks randomly, 33% of board will be blocks
        int blocks = rows * cols / 3;
        for(int i = 0; i < blocks; i++){
            int x = (int)(Math.random() * rows);
            int y = (int)(Math.random() * cols);
            board[x][y] = '*';

            disp.setColor(x,y, Color.black);
        }

        // Set starting pos
        int xStart = (int)(Math.random() * (rows-3)) + 1;
        int yStart = (int)(Math.random() * (cols-3)) + 1;
        board[xStart][yStart] = 's';
        disp.setColor(xStart,yStart, Color.green);
        startRow = xStart;
        startCol = yStart;

        // Set finish pos, cannot be in border
        int xEnd = (int)(Math.random() * (rows-3)) + 1;
        int yEnd = (int)(Math.random() * (cols-3)) + 1;
        board[xEnd][yEnd] = 'e';
        disp.setColor(xEnd,yEnd, Color.red);
        endRow = xEnd;
        endCol = yEnd;


        curX = xStart;
        curY = yStart;
        setVisited(curX, curY);
        //disp.setColor(curX, curY, Color.blue);

    }
    public Maze()
    {
        rows = 50;
        cols = 100;
        disp = new GridDisplay(rows, cols);
        board = new char[rows][cols];
        visited = new boolean[rows][cols];

        // initialize visited
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                visited[i][j] = false;
            }
        }

        //Top & bottom borders
        for(int j = 0; j < cols; j++) {
            disp.setColor(0, j, Color.black);
            board[0][j] = '*';

            disp.setColor(rows-1, j, Color.black);
            board[rows-1][j] = '*';
        }

        //Side borders
        for(int i = 1; i < rows - 1; i++){
            disp.setColor(i, 0, Color.black);
            board[i][0] = '*';


            disp.setColor(i, cols-1, Color.black);
            board[i][cols-1] = '*';
        }

        // Fill with blocks randomly, 33% of board will be blocks
        int blocks = rows * cols / 3;
        for(int i = 0; i < blocks; i++){
            int x = (int)(Math.random() * rows);
            int y = (int)(Math.random() * cols);
            board[x][y] = '*';

            disp.setColor(x,y, Color.black);
        }

        // Set starting pos
        int xStart = (int)(Math.random() * rows);
        int yStart = (int)(Math.random() * cols);
        board[xStart][yStart] = 's';
        disp.setColor(xStart,yStart, Color.green);

        // Set finish pos, cannot be in border
        int xEnd = (int)(Math.random() * (rows-3)) + 1;
        int yEnd = (int)(Math.random() * (cols-3)) + 1;
        board[xEnd][yEnd] = 'e';
        disp.setColor(xEnd,yEnd, Color.red);
        endRow = xEnd;
        endCol = yEnd;


        curX = xStart;
        curY = yStart;
        setVisited(curX, curY);
        //disp.setColor(curX, curY, Color.blue);

    }
    public void finish()
    {
        if (atEnd() == true) {
            colorBorders(Color.green);
        }
        else{
            colorBorders(Color.red);
        }
        GridDisplay.mySleep(1000);

    }

    public void dfs()
    {
        StackDarr moves = new StackDarr();
        Move s = new Move(curX, curY);
        moves.push(s);
        //Depth first search
        while (atEnd() == false) {
            GridDisplay.mySleep(10);
            // If we can't move
            if (move(moves) == false) {

                // Are we stuck at the start?
                if (atStart() == true) {
                    // Game over
                    break;
                }
                // Can't go back either?
                if (backtrack(moves) == false) {
                    // Game over
                    break;
                }
            }
        }
        moves = null;
    }

    private boolean move(StackDarr moves)
    {           //  n  s   e  w
        int[] x = { 0, 0, -1, 1 };
        int[] y = { 1, -1, 0, 0 };

        for(int i = 0; i < 4; i++){
            int nextX = curX + x[i];
            int nextY = curY + y[i];

            // Out of bound?
            if(validXY(nextX, nextY)){
                if(isEmpty(nextX, nextY) == true && getVisited(nextX, nextY) == false){

                    //Store position to return to later if needed
                    Move cur = new Move(curX, curY);
                    moves.push(cur);

                    //Set this position to be visited
                    setVisited(nextX, nextY);

                    //Set the current pos to new
                    setCurX(nextX);
                    setCurY(nextY);

                    // Don't color over the finish
                    if(atEnd() == false && board[nextX][nextY] != 's') {
                        disp.setColor(nextX, nextY, Color.pink);
                    }

                    return true;
                }
            }
        }
        return false;
    }

    public boolean backtrack(StackDarr moves)
    {
        // Can we backtrack?
        if(moves.isEmpty() == false){
            disp.setColor(curX,curY,Color.gray);
            Move m = moves.top();
            moves.pop();

            try {
                setCurX(m.getX());
                setCurY(m.getY());
            }
            catch(NullPointerException e){
                System.out.print("hah lern 2 grow n array");
            }
            return true;
        }

        // No more moves to backtrack
        return false;
    }

    public void bfs()
    {
        int[] x = {0, 0, -1, 1};
        int[] y = {1, -1, 0, 0};
        int nextX;
        int nextY;
        int count = 0;

        QueueDarr unvisitedQueue = new QueueDarr();

        Move s = new Move(curX, curY);
        unvisitedQueue.enqueue(s);

        while(unvisitedQueue.isEmpty() == false)
        {
            GridDisplay.mySleep(10);
            s = unvisitedQueue.front();
            unvisitedQueue.dequeue();

            setCurX(s.getX());
            setCurY(s.getY());

            if(atEnd() == false) {
                if(atStart() == false) {
                    disp.setColor(curX, curY, Color.gray);
                }
                for (int i = 0; i < 4; i++) {
                    nextX = curX + x[i];
                    nextY = curY + y[i];

                    // Out of bounds?
                    if (validXY(nextX, nextY)) {
                        if (isEmpty(nextX, nextY) == true && getVisited(nextX, nextY) == false) {
                            setVisited(nextX, nextY);
                            if(nextX != endRow && nextY != endCol) {
                                disp.setColor(nextX, nextY, Color.pink);
                            }

                            s = new Move(nextX, nextY);
                            unvisitedQueue.enqueue(s);
                        }
                    }
                }
            }
            else{
                unvisitedQueue = null;
                break;
            }
        }

    }

    public void Dijkstra()
    {
        Move end = new Move(endRow, endCol);
        Move start = new Move(startRow, startCol);

        int[][] distances = new int[getRows()][getCols()];
        Move[][] predecessors = new Move[getRows()][getCols()];
        QueueDarr unvisitedQueue = new QueueDarr();

        int INF = Integer.MAX_VALUE;

        for(int i = 0; i < rows; i++){
            for(int  j = 0; j < cols; j++){
                distances[i][j] = INF;
                predecessors[i][j] = null;
                visited[i][j] = false;
            }
        }
        distances[startRow][startCol] = 0;
        visited[startRow][startCol] = true;

        Move cur = new Move(startRow, startCol);
        unvisitedQueue.enqueue(cur);

        int[] xshift = {0, 0, -1, 1};
        int[] yshift = {-1, 1, 0, 0};

        while(unvisitedQueue.isEmpty() == false)
        {
            cur = unvisitedQueue.front();
            unvisitedQueue.dequeue();

            curX = cur.getX();
            curY = cur.getY();

            if(atEnd()){
                break;
            }

            if( !atStart() && !atEnd()) {
                disp.setColor(curX, curY, Color.gray);
                GridDisplay.mySleep(10);
            }

            for(int i = 0; i < 4; i++)
            {
                int nextX = curX + xshift[i];
                int nextY = curY + yshift[i];

                if(validXY(nextX, nextY) && !visited[nextX][nextY] && isEmpty(nextX, nextY))
                {
                    Move dest = new Move(nextX, nextY);

                    unvisitedQueue.enqueue(dest);
                    visited[nextX][nextY] = true;
                    if(nextX != startRow && nextX !=endRow &&
                            nextY != startCol && nextY != endCol)
                    {
                        disp.setColor(nextX, nextY, Color.pink);
                        GridDisplay.mySleep(10);

                    }

                    int alternatePathDistance = distances[curX][curY] + 1;

                    if(alternatePathDistance < distances[nextX][nextY])
                    {
                        distances[nextX][nextY] = alternatePathDistance;
                        predecessors[nextX][nextY] = cur;
                    }
                }
            }//end for i < 4

        }//end while

        // If there is a path to the end
        if(predecessors[endRow][endCol] != null) {
            curX = endRow;
            curY = endCol;

            while (!atStart())
            {
                if(!atEnd()) {
                    disp.setColor(curX, curY, Color.orange);
                    GridDisplay.mySleep(100);
                }

                Move prev = predecessors[curX][curY];
                curX = prev.getX();
                curY = prev.getY();
            }
            curX = endRow;
            curY = endCol;
        }

        GridDisplay.mySleep(1000);
    }

    public void reset()
    {
        for(int i = 0; i < rows; i++)
        {
            for( int j = 0 ; j < cols; j++)
            {
                visited[i][j] = false;
                if(board[i][j] != 's' && board[i][j] != 'e' && board[i][j] != '*') {
                    disp.setColor(i, j, Color.white);
                }
            }
        }
        colorBorders(Color.black);
        curX = startRow;
        curY = startCol;
        setVisited(curX,curY);
    }

    private boolean validXY(int x, int y)
    {
        if(x < 1 || x > rows-2 || y < 1 || y > cols-2){
            return false;
        }
        return true;
    }

    private boolean isEmpty(int x, int y)
    {
        if(board[x][y] == '*'){
            return false;
        }
        return true;
    }

    private boolean atEnd()
    {
        if(board[curX][curY] == 'e'){
            return true;
        }
        return false;
    }

    private boolean atStart()
    {
        if(board[curX][curY] == 's'){
            return true;
        }
        return false;
    }

    private void fillPath(StackDarr moves)
    {
        while(moves.isEmpty() == false)
        {
            Move m = moves.top();
            moves.pop();
            //Don't color over the start and finish
            if(board[m.getX()][m.getY()] != 'e' && board[m.getX()][m.getY()] != 's')
            {
                disp.setColor(m.getX(), m.getY(), Color.orange);
            }

        }
    }

    private void colorBorders(Color s)
    {
        for(int j = 0; j < cols; j++){
            if(board[0][j] != 's') {
                disp.setColor(0, j, s);
            }
            if( board[rows-1][j] != 's') {
                disp.setColor(rows - 1, j, s);
            }
        }

        for(int i = 0; i < rows; i++){
            if(board[i][0] != 's') {
                disp.setColor(i, 0, s);
            }
            if(board[i][cols-1] != 's') {
                disp.setColor(i, cols - 1, s);
            }
        }
    }
    private void noPathChris() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 9; j += 9) {
                disp.setChar(i, j, 'C');
                disp.setChar(i, j + 1, 'a');
                disp.setChar(i, j + 2, 't');
                disp.setChar(i, j + 3, 's');
                disp.setChar(i, j + 4, ' ');
                disp.setChar(i, j + 5, 'S');
                disp.setChar(i, j + 6, 'u');
                disp.setChar(i, j + 7, 'c');
                disp.setChar(i, j + 8, 'k');
                disp.setChar(i, j + 9, '!');
            }
        }
    }

} //End Maze class
