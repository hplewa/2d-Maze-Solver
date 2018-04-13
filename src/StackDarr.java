/**
 * Created by hubertplewa on 4/19/17.
 */
public class StackDarr {
    private Move[] Array;
    private int Size;
    private int Capacity;

    public StackDarr()
    {
        Size = -1;
        Capacity = 10;
        Array = new Move[Capacity];
    }

    public StackDarr(int s)
    {
        Size = -1;

        if(s > 0)
        {
            Capacity = s;
        }
        else
        {
            Capacity = 10;
        }
        Array = new Move[Capacity];

    }

    public boolean isEmpty()
    {
        if(Size > -1){
            return false;
        }
        return true;
    }

    public void push(Move num)
    {
        if(Size == Capacity - 1)
        {
            grow();
        }
        Size++;
        Array[Size] = num;
    }

    public void pop()
    {
        if(isEmpty() == false)
        {
            Size--;
        }
    }

    public Move top()
    {
        if(isEmpty() == false)
        {
            return Array[Size];
        }
        return null;
    }

    private void grow()
    {
        Capacity = Capacity * 2;
        Move[] grownArray = new Move[Capacity];
        for(int i = 0; i <= Size; i++)
        {
            grownArray[i] = Array[i];
        }
        Array = grownArray;
    }

    public int numElements()
    {
        return Size + 1;
    }

    public int getCapacity() { return Capacity; }

    public void print()
    {
        System.out.println("Size: " + Size + "\nCapacity: " + Capacity);

        for(int i = 0; i < Size; i++){
            System.out.print(Array[i] + " ");
        }
        System.out.println();
    }
}
