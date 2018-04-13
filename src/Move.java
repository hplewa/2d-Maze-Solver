/**
 * Created by hubertplewa on 4/19/17.
 */
public class Move {
    private int x;
    private int y;
    //private Move Next;

    public Move()
    {
        x = 0;
        y = 0;
        //Next = null;
    }
    public Move(int row, int col)
    {
        x = row;
        y = col;
        //Next = null;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int n) { x = n; }
    public void setY(int n) { y = n; }


    //public Move GetNext() { return Next; }

    //public void SetNext(Move n) { Next = n; }
}
