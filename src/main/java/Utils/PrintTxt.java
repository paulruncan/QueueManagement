package Utils;

import java.io.FileWriter;
import java.io.IOException;

public class PrintTxt {
    public static void printTxt(String text){
        try {
            FileWriter myWriter = new FileWriter("outputx.txt",true);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException exception){
            System.out.println("fail");
        }
    }
    public static void clearTxt(){
        try{
            FileWriter myWriter =new FileWriter("outputx.txt");
            myWriter.write("");
            myWriter.close();
        } catch (IOException exception){
            System.out.println("fail");
        }
    }
}
