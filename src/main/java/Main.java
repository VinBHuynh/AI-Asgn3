package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //take in file name (of the board) and number of which heuristic to use
        String temp = "";
        ArrayList<String[]> t = new ArrayList<>();
        int inputHeuristic = 1;
        try{
            File file = new File(args[0]);
            inputHeuristic = Integer.parseInt((args[1]));
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                temp = scanner.nextLine();
                String[] line = temp.split("\t");
                t.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int r_len = t.size();
        int c_len = t.get(0).length;
        int[][] board = new int[r_len][c_len];
        Node start = new Node(-1, -1, -1);
        Node end = new Node(-1, -1, -1);;

        for(int i = 0; i < t.size(); i++)
        {
            for (int j = 0; j < t.get(0).length; j++) {
                if (t.get(i)[j].equals("S")) {
                    start = new Node(j, i, 1);
                    start.setDir(0);
                    board[i][j] = 1;
                } else if (t.get(i)[j].equals("G")) {
                    end = new Node(j, i, 1);
                    board[i][j] = 1;
                }
                else {
                    board[i][j] = Integer.parseInt(t.get(i)[j]);
                }
            }
        }

        AStar astar;
        if (start.getX() == -1 || end.getX() == -1) {
            System.out.println("Missing start or end node");
        }
        else {
            astar = new AStar(start, end, board, inputHeuristic);
            Node fullpath = astar.getFullPath();
            astar.printPath(fullpath);
        }
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++)
                System.out.print(board[i][j] + "\t");
            System.out.println();
        }
    }

    public String[][] generateBoard(int i, int j) //pass in desired row and column
    {
        String[][] b = new String[i][j];
        ArrayList<Integer> i_pos = new ArrayList<Integer>();
        ArrayList<Integer> j_pos = new ArrayList<Integer>();
        int temp;

        for(int u = 0; u < i; u++) //generate list of all possible row positions
        {
            i_pos.add(u);
        }
        for(int v = 0; v < j; v++) //generate list of all possible col positions
        {
            j_pos.add(v);
        }


        int g_r = i_pos.get((int) (Math.random()*(i_pos.size()))); //give a row spot to the goal state
        int g_c = j_pos.get((int) (Math.random()*(j_pos.size()))); //give a col spot to the goal state
        i_pos.remove(g_r);
        j_pos.remove(g_c);
        b[g_r][g_c] = "G";
        int s_r = i_pos.get((int) (Math.random()*(i_pos.size()))); //repeating above with start spot
        int s_c = j_pos.get((int) (Math.random()*(j_pos.size())));
        b[s_r][s_c] = "S";


        for(int x = 0; x < b.length; x++)
        {
            for(int y = 0; y < b[0].length; y++)
            {
                if(b[x][y] == null)
                {
                    temp = (int)(Math.random()*(9 - 1) + 1);
                    b[x][y] = String.valueOf(temp);
                }
            }
        }
        return b;
    }
}
