/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommender;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class Main {
   
    public static void main (String[] args) throws IOException
    {
    	
    	//Upload.upload(); // txt
        Recommend.recommend(300, 0, 130629);
    }
}
    
    
    