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
public class Upload {
    public static int no_of_programs = 0;
    public static int no_of_persons = 0;
    public static int no_of_training_programs = 0;
    public static int no_of_test_programs = 0;
    public static int no_of_training_persons = 0;
    public static int no_of_test_persons = 0;
    public static int no_of_new_test_persons = 0;
    
    static ArrayList<Program> program = new ArrayList<Program>();
    static ArrayList<Program> test_program = new ArrayList<Program>();
    
    public static int start_date = 130101;
    public static int test_set_start_date = 130629;
    public static int no_of_test_days = (130630 - test_set_start_date) + 1; // 
    
    public static int no_of_programs_to_predict_per_day = 25;
    
// this array is to store the two meta-path weights (theta) for every user
    //public static double[][][] user_meta_weights = new double[1000][10][4];
    
        
    public static void upload () throws IOException
    {
        System.out.println("Done");
                
        FileInputStream program_file = new FileInputStream("data/program data example.txt");
        FileInputStream history_file = new FileInputStream("data/viewing log example.txt");
        
        Person[][] person = new Person[1000][10];
        Person[][] test_person = new Person[1000][10];
                
        String line = new String();
        String token[];
  
	try{
        BufferedReader br = new BufferedReader(new InputStreamReader(program_file));
       
        Program p = new Program();

        while((line = br.readLine()) != null){
			token = line.split("\t");
		
                        
			int date = Integer.parseInt(token[0]);
			int channel = Integer.parseInt(token[1]);
			double p_start_time = Double.parseDouble(token[2]); 
                        double p_end_time = Double.parseDouble(token[3]);
			int genre = Integer.parseInt(token[4]) - 1 ;
			int p_id = Integer.parseInt(token[5]);
			String title = token[6];
                        
            p = new Program(date, channel, p_start_time, p_end_time, genre, p_id, title);
            
            if(p.date >= test_set_start_date )
            {
                test_program.add(p);
                no_of_test_programs++;
            }
            else
            {
                program.add(p);
                no_of_training_programs++;
            }
                       
        }
        
        br = new BufferedReader(new InputStreamReader(history_file));
		while((line = br.readLine()) != null)
		{
			token = line.split(" ");			
			int date = Integer.parseInt(token[0]);
            int f_id = Integer.parseInt(token[1]);
			int personal_id = Integer.parseInt(token[2]);
			int channel = Integer.parseInt(token[3]);
			int genre = Integer.parseInt(token[4]) - 1;
			double view_start_time = Double.parseDouble(token[5]); double view_end_time = Double.parseDouble(token[6]);
			double p_start_time = Double.parseDouble(token[7]);	double p_end_time = Double.parseDouble(token[8]);
			double rating = Double.parseDouble(token[9]);
			int p_id = Integer.parseInt(token[10]);
            String title = token[11];
            
            if(date < test_set_start_date)
            {
                if(person[f_id][personal_id] == null)
                {
                    person[f_id][personal_id] = new Person();
                    
                    no_of_training_persons++;
                }
            
                if(view_end_time - view_start_time >= 0.007)
                {
                	Program pp = new Program(date, channel, p_start_time, p_end_time, genre, p_id, title);
                    pp.rating = rating;
                    person[f_id][personal_id].history.add(pp);
                }
            }
            else
            {
               if(test_person[f_id][personal_id] == null)
                {
                    test_person[f_id][personal_id] = new Person();
                    if(person[f_id][personal_id] == null)
                        no_of_new_test_persons++;
                        
                    no_of_test_persons++;
                }
            
                if(view_end_time - view_start_time >= 0.007)
                {
                	Program pp = new Program(date, channel, p_start_time, p_end_time, genre, p_id, title);
                    pp.rating = rating;
                    test_person[f_id][personal_id].history.add(pp);
                } 
            }
        }
                
        no_of_programs = no_of_training_programs + no_of_test_programs;
        no_of_persons = no_of_training_persons + no_of_new_test_persons;
        
// function call to remove duplicate programs from test data person history
        test_person = duplicate_removal(test_person);

        
        HIN_Recommender.HIN_based_recommender(person, test_person, program, test_program, start_date, test_set_start_date, no_of_test_days, no_of_programs_to_predict_per_day);
        
        
        program_file.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }      
        
    }
    
    // Function to remove duplicate programs from test data person history
    public static Person[][] duplicate_removal (Person[][] _test_person)
    {
        Methods object = new Methods();
        Program p1 = new Program();
        Program p2 = new Program();
        
        for(int i = 0; i < 1000; i++)
        {
            for(int j = 0; j<10; j++)
            {
                if(_test_person[i][j] != null && _test_person[i][j].history.size()>1)
                {
                    ArrayList<Integer> duplicate_indexes = new ArrayList<Integer>();
                
                    for(int k=0; k<_test_person[i][j].history.size(); k++)
                    {
                        p1 = _test_person[i][j].history.get(k);
                    
                        for(int l = k+1; l<_test_person[i][j].history.size(); l++)
                        {
                            p2 = _test_person[i][j].history.get(l);
                            if(object.is_equal(p1, p2))
                            {
                                duplicate_indexes.add(k);
                                //p2.rating = p2.rating + p1.rating;
                                //_test_person[i][j].history.get(l).rating = _test_person[i][j].history.get(l).rating + _test_person[i][j].history.get(k).rating;
                                break;
                            }
                        }
                    }
                    for(int m = 0; m<duplicate_indexes.size(); m++)
                    {
                        int index = duplicate_indexes.get(m) - m;
                        _test_person[i][j].history.remove(index);
                    }
                }
            }
        }
        return _test_person;
    }
    
}
    
    
    