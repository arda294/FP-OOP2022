package Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserDataUtil {
    private File data;
    private int bestTime;
    private int bestPuts;
    public UserDataUtil(String level) {
        data = new File("levels/userdata/"+level+".dat");
        try {
            if(data.createNewFile()) saveNew();
            else getData(data);
        } catch(IOException e) {
            System.out.println("Error in saving data");
            e.printStackTrace();
        }
    }
    public void update(int time, int puts) throws IOException {
        Scanner sc = new Scanner(data);
        int bestTime = sc.nextInt();
        int bestPuts = sc.nextInt();
        sc.close();
        if(time < bestTime) bestTime = time;
        if(puts < bestPuts) bestPuts = puts;
        FileWriter fw = new FileWriter(data);
        fw.write(bestTime + "\n");
        fw.write(bestPuts + "\n");
        fw.close();
    }

    private void saveNew() throws IOException {
        FileWriter fw = new FileWriter(data);
        fw.write(Integer.MAX_VALUE + "\n");
        fw.write(Integer.MAX_VALUE + "\n");
        fw.close();
    }

    private void getData(File data) throws IOException {
        Scanner sc = new Scanner(data);
        bestTime = sc.nextInt();
        bestPuts = sc.nextInt();
        sc.close();
    }

    public int getBestPuts() {
        return bestPuts;
    }

    public int getBestTime() {
        return bestTime;
    }
}
