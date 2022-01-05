import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String[][] map = new String[16][16]; //array initalization

        //loop for taking in user input to array
        for (int i = 0; i < 16; i++) {
            String line = in.nextLine(); //takes in input by line
            map[i] = line.split(""); //split line onto array row
        }
        floodFill(map); //method to flood edges of map with x's
        System.out.println(check(map)); //checks for o's that are safe
    }

    public static int check(String [][] floodMap){ //check for o's method
        int count = 0; //o count
        boolean valid = false; //flag to check if o is valid, starts false as first column should never be true or needs trigger
        for (int x = 0; x < 15; x++){ //row loop
            for(int y = 0; y < 15; y++){ //column loop
                if (floodMap[x][y].equals("|")){ //wall trigger swaps validation, walls cant be next to one another so this always works
                    valid = !valid; //swaps true/false
                } else if (floodMap[x][y].equals("+") || floodMap[x][y].equals("-")){ //checks corner or edge
                    if(floodMap[x][y+1].equals("+") || floodMap[x][y+1].equals("-") || floodMap[x][y+1].equals("o") || floodMap[x][y+1].equals(" ")){ //checks one to the right of previous
                        try { //can meet out of bounds if on edge of map
                            if (isPlusOrMinus(floodMap, x-1, y+1) || floodMap[x+1][y+1].matches("o") || floodMap[x+1][y+1].matches(" ") || floodMap[x+1][y+1].matches("-")){ //checks above and below previous
                              valid = true;
                            }else {
                            valid = false;
                        }
                    } catch (ArrayIndexOutOfBoundsException exception) {}
                    } 
                }
                try { //can meet out of bounds if on edge of map
                    if (floodMap[x][y].equals("o") && (floodMap[x+1][y+1].equals(" ") || floodMap[x-1][y+1].equals(" "))){ //edge case add to count
                        count++;
                    } else if (floodMap[x][y].equals("o") && valid == true){ //adds to count
                        count++;
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {}
            }
            valid = false; //resets validation, check initilization
        }
        return count; //returns o count
    }

    public static boolean isPlusOrMinus(String [][] floodMap, int x, int y){
        Set<String> values = new HashSet<String>(Arrays.asList("o"," ", "-"));
        if (values.contains(floodMap[x][y])){
            return true;
        }
        return false;
    }

    public static void floodFill(String [][] map){
        int x = map.length;
        int y = map[0].length;

        //creates queue of . and o to go over from edge of map(array)
        Queue<int[]> edge = new LinkedList<>();
        for (int i = 0; i < x; i++){
            if (map[i][0].equals(".") || map[i][0].equals("o")){ //left side
                edge.add(new int[]{i,0});
            }
            if (map[i][y-1].equals(".") || map[i][y-1].equals("o")){ //right side
                edge.add(new int[]{i,y-1});
            }
        }
        for (int n = 0; n < y; n++){
            if (map[0][n].equals(".") || map[0][n].equals("o")){ //top
                edge.add(new int[]{0, n});
            }
            if (map[x-1][n].equals(".") || map[x-1][n].equals("o")){ //bottom
                edge.add(new int[]{x-1, n});
            }
        }

        //flood fills edges until no more . or o reachable
        while (!edge.isEmpty()){
            int[] next = edge.poll(); //removes front element of queue
            int i = next[0];
            int n = next[1];
            map[i][n] = "x"; //changes value in array for consistency
            for (int mapi = -1; mapi<= 1; mapi++){ //starts from -1 to scan around later
                for (int mapn = -1; mapn <= 1; mapn++){ //starts from -1 to scan around later
                    try { //can go out of bounds
                        if (map[i+mapi][n+mapn].equals(".") || map[i+mapi][n+mapn].equals("o")){ //checks around
                            edge.add(new int[]{i+mapi,n+mapn}); //adds to queue
                        }     
                    } catch (ArrayIndexOutOfBoundsException exception) {}
                }
            }
        }
    }
}