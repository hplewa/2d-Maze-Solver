import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubertplewa on 4/28/17.
 */
public class Main {

    public static void main(String[] args)
    {
        int lines = 10;
        int line = 0;
        while(line < lines/2){
            int space = 0;
            while(space < lines-line-1){
                System.out.print("  ");
                space++;
            }
            int stars = 0;
            while(stars < 1 + 2*line){
                System.out.print("* ");
                stars++;
            }
            line++;
            System.out.println();

        }
        while(line >= 0){
            int space = 0;
            while(space < lines-(lines-(lines-line)) - 1){
                System.out.print("  ");
                space++;
            }
            int stars = 0;
            while(stars < 1 + 2*(lines-(lines-line))){
                System.out.print("* ");
                stars++;
            }
            line--;
            System.out.println();

        }
        int x = 40;
        int y = 40;
        for( int i = 0; i < args.length; i++)
        {
            if(args[i].equals("-d")){
                try{
                    x = Integer.parseInt(args[i+1]);
                    y = Integer.parseInt(args[i+2]);
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid format");
                }
                catch (NullPointerException e){
                    System.out.println("Invalid format");
                }
            }
        }
        while(true) {

            Maze maze = new Maze(x, y);
            GridDisplay.mySleep(2000);

            maze.dfs();
            GridDisplay.mySleep(500);
            maze.finish();

            GridDisplay.mySleep(500);
            maze.reset();
            GridDisplay.mySleep(1000);

            maze.Dijkstra();
            GridDisplay.mySleep(2000);
            maze.finish();



            GridDisplay.mySleep(1000);
            maze = null;

        }

    }

}
