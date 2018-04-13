/**
 * Created by hubertplewa on 4/19/17.
 */
public class QueueDarr {
    private Move[] Array;
    private int Size;
    private int Front;
    private int Capacity;
    private int Count;

    public QueueDarr()
    {
        Size = -1;
        Front = 0;
        Capacity = 10;
        Count = 0;
        Array = new Move[Capacity];

    }

    public QueueDarr(int s)
    {
        Size = -1;
        Front = 0;
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
//        if(Size > -1)
        if(Count > 0)
        {
            return false;
        }
        return true;
    }

    public void enqueue(Move s)
    {
        if(Size == Capacity-1)
        {
            grow();
        }
        Count++;
        Size++;
        Array[Size] = s;

//        if(Front == -1){
//            Front = 0;
//        }
    }

    public void dequeue()
    {
        if(isEmpty() == false)
        {
            Front++;
            Count--;
        }
    }

    public Move front()
    {
        if(isEmpty() == false)
        {
            return Array[Front];
        }
        return null;
    }

    public Move back()
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
        int start = Front;
        for(int i = Front; i <= Size; i++)
        {
            grownArray[i-start] = Array[i];
        }
        Size = Count-1;
        Front = 0;
        Array = null;
        Array = grownArray;
    }

    public int size()
    {
        return Count;
    }

    public void print()
    {
        System.out.println("Size: " + Size + "\nCapacity: " + Capacity);

        for(int i = 0; i < Size; i++){
            System.out.print(Array[i] + " ");
        }
        System.out.println();
    }
}
