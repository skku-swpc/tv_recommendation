/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommender;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author user
 */
public class Recommend {
    
	public static int start_date = 130629;
    public static void recommend (int group, int member, int date) throws IOException
    {
    	FileInputStream recommend_data = new FileInputStream("data/recommend_data.txt");
    	
    	PersonInfo[][][] person = new PersonInfo[1000][10][10];
    	for(int i = 0; i < 1000 ;i++){
    		for(int j = 0; j < 10; j++){
    			for(int k = 0; k < 10;k++){
    				person[i][j][k] = new PersonInfo();
    			}
    		}
    	}
    	String line = new String();
        String token[];
        
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(recommend_data));
           
            int cnt = 0;
            while((line = br.readLine()) != null){
            	if(cnt == 3) cnt = 0;
    			token = line.split("\t");
    			int in_group = Integer.parseInt(token[0]);
    			int in_member = Integer.parseInt(token[1]);
    			int in_date = Integer.parseInt(token[2]);
    			String title = token[3];
    			double start_hour = Double.parseDouble(token[4]);
    			
                person[in_group][in_member][in_date-start_date+cnt].setTitle(title);
                person[in_group][in_member][in_date-start_date+cnt].setHour(start_hour);
                cnt++;
            }
            
            
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        for(int i = 0; i < 3; i++){
        	System.out.println("Group Id :	"+group+"	Member Id : 	"+member+"	Date : 	"+(start_date)+"	Title : 	"+person[group][member][i].title+"	Start Hour : "+person[group][member][i].start_hour);
        }
        
    }
}
    
    
    