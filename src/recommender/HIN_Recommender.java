/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommender;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author user
 */
public final class HIN_Recommender {
    
    private static ArrayList<Program> program = new ArrayList<Program>();
    private static ArrayList<Program> test_program = new ArrayList<Program>();
    
    private static int start_date;
    private static int test_set_start_date;
    private static int no_of_test_days;
    
    private static int no_of_programs_to_predict_per_day;
    
// this array is to store the two meta-path weights (theta) for every user
    private static double[][][] user_meta_weights = new double[1000][10][4];
    
    private static void HIN_Recommender(ArrayList<Program> _program, ArrayList<Program> _test_program, int _start_date, int _test_set_start_date, int _no_of_test_days, int _no_of_programs_to_predict_per_day )
    {
        program = _program;
        test_program = _test_program;
        start_date = _start_date;
        test_set_start_date = _test_set_start_date;
        no_of_test_days = _no_of_test_days;
        no_of_programs_to_predict_per_day = _no_of_programs_to_predict_per_day;
        
    }
    
    public static void HIN_based_recommender (Person[][] person, Person[][] test_person, ArrayList<Program> _program, ArrayList<Program> _test_program, int _start_date, int _test_set_start_date, int _no_of_test_days, int _no_of_programs_to_predict_per_day) throws IOException
    {
        HIN_Recommender(_program, _test_program, _start_date, _test_set_start_date, _no_of_test_days, _no_of_programs_to_predict_per_day);
        
        similarity_weight_calculation(person); //수정
   
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/recommend_data.txt"));
        //Scanner sc = new Scanner(System.in);
        
        ArrayList<Program>test_prog_per_day[] = new ArrayList[no_of_test_days];
        Methods obj1 = new Methods();
        test_prog_per_day = obj1.test_prog_per_day_format(test_program, no_of_test_days); // 날짜에 따라 프로그램을 나누는 함수
        //int group, member;
        //System.out.println("Input Group id : ");
        //group = sc.nextInt();
        //System.out.println("Input Member id : ");
        //member = sc.nextInt();
        for(int group = 0; group < 1000; group++){
        	for(int member = 0; member < 10; member++){
        		for(int i = 25; i<no_of_programs_to_predict_per_day + 1; i++)
                {
        			if(person[group][member] != null){
                        Methods obj = new Methods();
                        obj.each_user_recommend(person[group][member], user_meta_weights[group][member], _no_of_test_days, i, test_prog_per_day, group, member, bw);
        			}

                }
        	}
        }
        bw.close();
        System.out.println("Fininsh");
    }
    
    
// to calculate the weights for all users during training
    public static void similarity_weight_calculation(Person[][] per)
    {
// Meta-Weights calculation for all training users 1 by 1
        int no_of_active_users  = 0;
        int sim_count = 0;
        for(int i = 0; i<1000; i++)
        {
            for(int j=0; j<10; j++)
            {
                if(per[i][j] != null && per[i][j].history.size()>0)
                {
                    Methods user = new Methods();
                    user.user_rating_calculation(per[i][j]);
                    double sum = user.sim_p_genre_p + user.sim_p_channel_p + user.sim_p_startTime_p +user.sim_p_startTime_channel_p;
                    
                    if(sum == 0)
                    {
                        user_meta_weights[i][j][0] = 0;
                        user_meta_weights[i][j][1] = 0;
                        user_meta_weights[i][j][2] = 0;
                        user_meta_weights[i][j][3] = 0;
                    }
                    else
                    {
                        user_meta_weights[i][j][0] = user.sim_p_genre_p / sum;
                        user_meta_weights[i][j][1] = user.sim_p_channel_p / sum;
                        user_meta_weights[i][j][2] = user.sim_p_startTime_p / sum;
                        user_meta_weights[i][j][3] = user.sim_p_startTime_channel_p / sum;
                        sim_count++;
                    }
                    no_of_active_users++;
                }
            }
           
        }
        
        //Store_similarity(user_meta_weights);
    }

	private static void Store_similarity(double[][][] _user_meta_weights) {
		// TODO Auto-generated method stub
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("data/user_similarity.txt"));
			String s = "";
			int cnt = 0;
			for(int i = 0;i <_user_meta_weights.length;i++){
				s = "";
				cnt = 0;
				for(int j = 0;j < _user_meta_weights[i].length;j++){
					if(_user_meta_weights[i][j][0] != 0.0 && _user_meta_weights[i][j][1] != 0.0 &&_user_meta_weights[i][j][2] != 0.0) cnt++;
				}
				s += (Integer.toString(cnt)+"\n");
				for(int j = 0;j < _user_meta_weights[i].length;j++){
					if(_user_meta_weights[i][j][0] != 0.0 && _user_meta_weights[i][j][1] != 0.0 &&_user_meta_weights[i][j][0] != 0.0){
						for(int k = 0;k < 3;k++){
							s += Double.toString(_user_meta_weights[i][j][k]);
							s += "\t";
						}
						s += "\n";
					}
				}
				bw.write(s);
			}
			System.out.println("finish");
			bw.close();
		} catch (IOException e) {
			System.err.println(e);;
			System.exit(1);
			e.printStackTrace();
		}
	}   
}
