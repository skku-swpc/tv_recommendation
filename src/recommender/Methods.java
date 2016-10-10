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
import java.util.Arrays;


/**
 *
 * @author user
 */
public class Methods {
    
    public static int no_of_programs_to_predict;
    public ArrayList<Program> test_prog_per_day[];
    
    //public static int total_count = 0;
    //public static int user_count = 0;
    public static int total_user_count;
    
    public static double total_precision;
    public static double average_precision;
    
    public static double total_recall;
    public static double average_recall;
    
    public static double total_precision_per_user;
    public static double total_recall_per_user;
    public static double average_precision_per_user;
    public static double average_recall_per_user;
    
    public static int no_of_test_days;
    public static int no_of_test_days_calculated;
    
    public static int total_predicted_count_per_user;
    public static int total_watch_count_per_user;
    public static int total_no_of_programs_to_predict_per_user;
    
    public double sim_p_genre_p;
    public double sim_p_channel_p;
    public double sim_p_startTime_p;
    public double sim_p_startTime_channel_p;
   
    
    
    public Methods()
    {
        //this.no_of_programs_to_predict = 5;
    
    //public static int total_count = 0;
    //public static int user_count = 0;
    this.total_user_count = 0;
    
    this.total_precision = 0.0;
    this.average_precision = 0.0;
    
    this.total_recall = 0.0;
    this.average_recall = 0.0;
    
    this.total_precision_per_user = 0.0;
    this. total_recall_per_user = 0.0;
    this.average_precision_per_user = 0.0;
    this.average_recall_per_user = 0.0;
    
    //this.no_of_test_days;
    this.no_of_test_days_calculated = 0;
    
    this.total_predicted_count_per_user = 0;
    this.total_watch_count_per_user = 0;
    this.total_no_of_programs_to_predict_per_user = 0;
    
    this.sim_p_genre_p = 0;
    this.sim_p_channel_p = 0;
    this.sim_p_startTime_p = 0;
    this.sim_p_startTime_channel_p = 0;
   
    
    //public ArrayList<Program> test_prog_per_day[];// = new ArrayList[n];
    }
    
    public double get_average_precision()
    {
        return this.average_precision;
    }
    
    public double get_average_recall()
    {
        return this.average_recall;
    }
    
    public void user_rating_calculation(Person per)
        {
            
            this.program_genre_program(per);
            this.program_channel_program(per);
            this.program_startTime_program(per);
            //this.program_startTime_channel_program(per);
        }
    
    public boolean is_equal(Program p1, Program p2)
    {
        boolean flag = false;
        
        if(p1.channel == p2.channel)
            if(p1.date == p2.date)
                if(p1.genre == p2.genre)
                    if(p1.p_end_time == p2.p_end_time)
                        if(p1.p_id == p2.p_id)
                            if(p1.p_start_time == p2.p_start_time)
                                flag =  true;
        
        return flag;
    }
    
    public boolean is_same(Program p1, Program p2)
    {
        boolean flag = false;
        
        if(p1.title.equals(p2.title))
            if(p1.channel == p2.channel)
                if(p1.p_start_hour == p2.p_start_hour)
                        flag =  true;
        
        return flag;
    }
    
        
    public void program_genre_program(Person per)
    {
        double count = 0.0;
        for(int i = 0; i<per.history.size(); i++)
        {
            double s = 0;
                        
                for(int j = 0; j<per.history.size(); j++)
                {
                    if(j!=i)
                        if(per.history.get(i).genre == per.history.get(j).genre)
                        {
                            //s = s + (per.history.get(i).rating * per.history.get(j).rating);
                            s = s + 1;
                            count++;
                        }
                }
           this.sim_p_genre_p = this.sim_p_genre_p + s; 
        }
        //this.sim_p_genre_p = this.sim_p_genre_p * count;
    }
    
        
    public void program_channel_program(Person per)
    {
        double count = 0.0;
        
        ArrayList<Program> program_channels[]= new ArrayList[7947];  // unique p_id = 7946  unique_channels = 24
        for(int i = 0; i<per.history.size(); i++)
        {
            Program pp = new Program();
            pp = per.history.get(i);
            
            if(program_channels[pp.p_id] == null)
            {
                program_channels[pp.p_id] = new ArrayList<Program>();
                pp.watch_count++;
                program_channels[pp.p_id].add(pp);
                
            }
            
            else
            {
                int check = 0;
                for(int j = 0; j<program_channels[pp.p_id].size(); j++)
                {
                    int channel = program_channels[pp.p_id].get(j).channel;
                    
                    if(channel == pp.channel )
                    {
                        program_channels[pp.p_id].get(j).watch_count++;
                        check++;
                        break;
                    }
                }
                
                if(check == 0)
                {
                    pp.watch_count++;
                    program_channels[pp.p_id].add(pp);
                }
                
            }
     
        }
        
        for(int i = 0; i<7947; i++)
            if(program_channels[i] != null)
                for(int j = 0; j<program_channels[i].size(); j++)
                {
                    double s = 0.0;
                    Program p1 = new Program();
                    p1 = program_channels[i].get(j);
                    
                    for(int k = 0; k<7947; k++)
                        if(program_channels[k] != null)
                            for(int l = 0; l<program_channels[k].size(); l++)
                            {
                                Program p2 = new Program();
                                p2 = program_channels[k].get(l);
                                
                                //if(i != k)
                                    if(p1.channel == p2.channel)
                                    {
                                        int path_count = 0;
                                        
                                        for(int m = 0; m<program_channels[i].size(); m++)
                                            for(int n = 0; n<program_channels[k].size(); n++)
                                                if(program_channels[i].get(m).channel == program_channels[k].get(n).channel)
                                                    path_count++;
                                        
                                        s = s + ((double) path_count * 2 * p1.watch_count * p2.watch_count) / (program_channels[i].size() + program_channels[k].size());
                                        //s = s + ((double) path_count * 2 ) / (program_channels[i].size() + program_channels[k].size());
                                    }
                            }
                    this.sim_p_channel_p = this.sim_p_channel_p + s;
                }
    
        //this.sim_p_channel_p = this.sim_p_channel_p * count;
    }
    
    public void program_startTime_program(Person per)
    {
        double count = 0.0;
        
        ArrayList<Program> program_hrs[]= new ArrayList[7947];  // unique p_id = 7946  unique_hrs = 24
        for(int i = 0; i<per.history.size(); i++)
        {
            Program pp = new Program();
            pp = per.history.get(i);
            
            if(program_hrs[pp.p_id] == null)
            {
                program_hrs[pp.p_id] = new ArrayList<Program>();
                pp.watch_count++;
                program_hrs[pp.p_id].add(pp);
            }
            
            else
            {
                int check = 0;
                for(int j = 0; j<program_hrs[pp.p_id].size(); j++)
                {
                    double hrs = program_hrs[pp.p_id].get(j).p_start_hour;
                    
                    if(hrs == pp.p_start_hour )
                    {
                        program_hrs[pp.p_id].get(j).watch_count++;
                        check++;
                        break;
                    }
                }
                
                if(check == 0)
                {
                    pp.watch_count++;
                    program_hrs[pp.p_id].add(pp);
                }
            }
     
        }
        
        for(int i = 0; i<7947; i++)
            if(program_hrs[i] != null)
                for(int j = 0; j<program_hrs[i].size(); j++)
                {
                    double s = 0.0;
                    Program p1 = new Program();
                    p1 = program_hrs[i].get(j);
                    
                    for(int k = 0; k<7947; k++)
                        if(program_hrs[k] != null)
                            for(int l = 0; l<program_hrs[k].size(); l++)
                            {
                                Program p2 = new Program();
                                p2 = program_hrs[k].get(l);
                                
                                //if(i != k)
                                    if(p1.p_start_hour == p2.p_start_hour)
                                    {
                                        int path_count = 0;
                                        
                                        for(int m = 0; m<program_hrs[i].size(); m++)
                                            for(int n = 0; n<program_hrs[k].size(); n++)
                                                if(program_hrs[i].get(m).p_start_hour == program_hrs[k].get(n).p_start_hour)
                                                    path_count++;
                                        
                                        s = s + ((double) path_count * 2 * p1.watch_count * p2.watch_count) / (program_hrs[i].size() + program_hrs[k].size());
                                        //s = s + ((double) path_count * 2 ) / (program_hrs[i].size() + program_hrs[k].size());
                                    }
                            }
                    this.sim_p_startTime_p = this.sim_p_startTime_p + s;
                }
        
        //this.sim_p_startTime_p = this.sim_p_startTime_p * count;
    }
    
    // test data functions
    
    public ArrayList<Program>[] test_prog_per_day_format(ArrayList<Program> _test_prog, int _no_of_test_days)
    {
        no_of_test_days = _no_of_test_days;
        test_prog_per_day = new ArrayList[no_of_test_days];
        int date_index = 0;
        test_prog_per_day[0] = new ArrayList<Program>();
        
    // To store all the test programs in test programs per day ArrayList format    
        int p_count = 0;
        for(p_count = 0; p_count<_test_prog.size()-1; p_count++)
        {
            test_prog_per_day[date_index].add(_test_prog.get(p_count));
            if(_test_prog.get(p_count).date != _test_prog.get(p_count+1).date )
            {
                no_of_test_days_calculated++;
                date_index++;
                test_prog_per_day[date_index] = new ArrayList<Program>();
            }
        }
        test_prog_per_day[date_index].add(_test_prog.get(p_count));
        
        return test_prog_per_day;
    }
    

    public void each_user_recommend(Person _per, double[] _user_meta_weights, int _no_of_test_days, int _no_of_programs_to_predict, ArrayList<Program>[] _test_prog_per_day, int group, int member, BufferedWriter bw){
        no_of_test_days = _no_of_test_days;
        no_of_programs_to_predict = _no_of_programs_to_predict;
        test_prog_per_day = _test_prog_per_day;
        
        ArrayList<Program> test_per_per_day[] = new ArrayList[no_of_test_days];
        
        for(int i = 0; i<_per.history.size(); i++)
        {
            for(int p_count = 0; p_count < no_of_test_days; p_count++)
            {
                if(test_prog_per_day[p_count].get(0).genre == _per.history.get(i).genre)
                {
                    if(test_per_per_day[p_count] == null)
                    {
                        test_per_per_day[p_count] = new ArrayList<Program>();
                        //day_count_per_user++;
                    }
                
                    test_per_per_day[p_count].add(_per.history.get(i));
                    break;
                }
                
            }
        }
        for(int index=0; index < no_of_test_days; index++)
        {
            if(test_per_per_day[index] != null)
            {
                double[] sim_MP1 = new double[test_prog_per_day[index].size()];
                double[] sim_MP2 = new double[test_prog_per_day[index].size()];
                double[] sim_MP3 = new double[test_prog_per_day[index].size()];
                double[] sim_MP4 = new double[test_prog_per_day[index].size()];
        
                double[] sim_final = new double[test_prog_per_day[index].size()];
        
                sim_MP1 = program_genre_program_test(_per, test_prog_per_day[index]);
                sim_MP2 = program_channel_program_test(_per, test_prog_per_day[index] );
                sim_MP3 = program_startTime_program_test(_per, test_prog_per_day[index]);
                //sim_MP4 = program_startTime_channel_program_test(_per, test_prog_per_day[index]);
        
                //어떤날에 프로그램중에서 simlairty 가장 높은 프로그램 구하기
                for(int i = 0; i<test_prog_per_day[index].size(); i++)
                {
                    sim_final[i] = (sim_MP1[i] * _user_meta_weights[0]) + (sim_MP2[i] * _user_meta_weights[1]) + (sim_MP3[i] * _user_meta_weights[2]) + (sim_MP4[i] * _user_meta_weights[3]);
            
                }
        
                Pair[] sorted_sim_final = new Pair[test_prog_per_day[index].size()];
                for(int i = 0; i<test_prog_per_day[index].size(); i++) sorted_sim_final[i] = new Pair(i, sim_final[i]);

                Arrays.sort(sorted_sim_final); // similarity가 높은 순서대로 정렬
                
                recommend(sorted_sim_final, index, group, member, bw);
            }  
        }
    }
    private void recommend(Pair[] sorted_sim_final, int index, int group, int member, BufferedWriter bw) {
		// TODO Auto-generated method stub
    	
    	try {
			
			Program p = new Program();
			String s = "";
			int cnt = 0;
			
			for(int i = 0; i < 3; i++){
				s = "";
				p = test_prog_per_day[index].get(sorted_sim_final[i].index);
				s += (group+"\t"+member+"\t"+p.date+"\t"+p.title+"\t"+p.p_start_hour+"\n");
				bw.write(s);
			}
			//System.out.println("finish");
			
		} catch (IOException e) {
			System.err.println(e);;
			System.exit(1);
			e.printStackTrace();
		}
	}
    
    
    public double[] program_genre_program_test(Person _per, ArrayList<Program> _test_prog)
    {
        double[] sim = new double[_test_prog.size()];
        
        for(int i = 0; i<_test_prog.size(); i++)
        {
            double s = 0;
            
            for(int j = 0; j<_per.history.size(); j++)
            {
                if(_test_prog.get(i).genre == _per.history.get(j).genre)
                    //s = s + (_per.history.get(j).rating * 1);
                    s = s + 1;
                
            }
            sim[i] = s;
            
        }
        return sim;
    }
    
    public double[] program_channel_program_test(Person _per, ArrayList<Program> _test_prog)
    {        
        
        ArrayList<Program> program_channels[]= new ArrayList[7947];  // unique p_id = 7946  unique_channels = 24
        for(int i = 0; i<_per.history.size(); i++)
        {
            Program pp = new Program();
            pp = _per.history.get(i);
            
            if(program_channels[pp.p_id] == null)
            {
                program_channels[pp.p_id] = new ArrayList<Program>();
                pp.watch_count++;
                program_channels[pp.p_id].add(pp);
                
            }
            
            else
            {
                int check = 0;
                for(int j = 0; j<program_channels[pp.p_id].size(); j++)
                {
                    int channel = program_channels[pp.p_id].get(j).channel;
                    
                    if(channel == pp.channel )
                    {
                        program_channels[pp.p_id].get(j).watch_count++;
                        check++;
                        break;
                    }
                }
                
                if(check == 0)
                {
                    pp.watch_count++;
                    program_channels[pp.p_id].add(pp);
                }
            }
     
        }
        
        ArrayList<Program> test_program_channels[]= new ArrayList[7947];  // unique p_id = 7946  unique_hrs = 24
        for(int i = 0; i<_test_prog.size(); i++)
        {
            Program pp = new Program();
            pp = _test_prog.get(i);
            
            if(test_program_channels[pp.p_id] == null)
            {
                test_program_channels[pp.p_id] = new ArrayList<Program>();
                pp.index = i;
                test_program_channels[pp.p_id].add(pp);
            }
            
            else
            {
                int check = 0;
                for(int j = 0; j<test_program_channels[pp.p_id].size(); j++)
                {
                    int ch = test_program_channels[pp.p_id].get(j).channel;
                    
                    if(ch == pp.channel )
                    {
                        check++;
                        //pp.index = i;
                        break;
                    }
                }
                
                if(check == 0)
                {
                    pp.index = i;
                    test_program_channels[pp.p_id].add(pp);
                }
            }
     
        }
        
        double[] sim = new double[_test_prog.size()];  //7947 program_ids and 24 channels
        
        for(int i = 0; i<7947; i++)
            if(test_program_channels[i] != null)
                for(int j = 0; j<test_program_channels[i].size(); j++)
                {
                    double s = 0;
                    Program p1 = new Program();
                    p1 = test_program_channels[i].get(j);
                    
                    for(int k = 0; k <7947; k++)
                        if(program_channels[k] != null)
                            for(int l = 0; l<program_channels[k].size(); l++)
                            {
                                Program p2 = new Program();
                                p2 = program_channels[k].get(l);
                                
                                //if(i != k )
                                    if(p1.channel == p2.channel)
                                    {
                                        int path_count = 0;
                                        
                                        for(int m= 0; m<test_program_channels[i].size(); m++)
                                            for(int n = 0; n<program_channels[k].size(); n++)
                                                if(test_program_channels[i].get(m).channel == program_channels[k].get(n).channel)
                                                    path_count++;
                                        
                                        //s = s + ( (double) path_count * 2 ) / (test_program_channels[i].size() + program_channels[k].size());
                                         s = s + ( (double) path_count * 2 * p2.watch_count) / (test_program_channels[i].size() + program_channels[k].size());
                                    }
                            }
                    
                    sim[p1.index] = s;
                }
            
        return sim;
        
    }
    
    public double[] program_startTime_program_test(Person _per, ArrayList<Program> _test_prog)
    {
        ArrayList<Program> program_hrs[]= new ArrayList[7947];  // unique p_id = 7946  unique_hrs = 24
        for(int i = 0; i<_per.history.size(); i++)
        {
            Program pp = new Program();
            pp = _per.history.get(i);
            
            if(program_hrs[pp.p_id] == null)
            {
                program_hrs[pp.p_id] = new ArrayList<Program>();
                pp.watch_count++;
                program_hrs[pp.p_id].add(pp);
            }
            
            else
            {
                int check = 0;
                for(int j = 0; j<program_hrs[pp.p_id].size(); j++)
                {
                    double hrs = program_hrs[pp.p_id].get(j).p_start_hour;
                    
                    if(hrs == pp.p_start_hour )
                    {
                        program_hrs[pp.p_id].get(j).watch_count++;
                        check++;
                        break;
                    }
                }
                
                if(check == 0)
                {
                    pp.watch_count++;
                    program_hrs[pp.p_id].add(pp);
                }
            }
     
        }
        
        ArrayList<Program> test_program_hrs[]= new ArrayList[7947];  // unique p_id = 7946  unique_hrs = 24
        for(int i = 0; i<_test_prog.size(); i++)
        {
            Program pp = new Program();
            pp = _test_prog.get(i);
            
            if(test_program_hrs[pp.p_id] == null)
            {
                test_program_hrs[pp.p_id] = new ArrayList<Program>();
                pp.index = i;
                test_program_hrs[pp.p_id].add(pp);
            }
            
            else
            {
                int check = 0;
                for(int j = 0; j<test_program_hrs[pp.p_id].size(); j++)
                {
                    double hrs = test_program_hrs[pp.p_id].get(j).p_start_hour;
                    
                    if(hrs == pp.p_start_hour )
                    {
                        check++;
                        break;
                    }
                }
                
                if(check == 0)
                {
                    pp.index = i;
                    test_program_hrs[pp.p_id].add(pp);
                }
                
            }
     
        }
        
        double[] sim = new double[_test_prog.size()];  //7947 program_ids and 24 channels
        
        for(int i = 0; i<7947; i++)
            if(test_program_hrs[i] != null)
                for(int j = 0; j<test_program_hrs[i].size(); j++)
                {
                    double s = 0;
                    Program p1 = new Program();
                    p1 = test_program_hrs[i].get(j);
                    
                    for(int k = 0; k <7947; k++)
                        if(program_hrs[k] != null)
                            for(int l = 0; l<program_hrs[k].size(); l++)
                            {
                                Program p2 = new Program();
                                p2 = program_hrs[k].get(l);
                                
                                //if(i != k )
                                    if(p1.p_start_hour == p2.p_start_hour)
                                    {
                                        int path_count = 0;
                                        
                                        for(int m= 0; m<test_program_hrs[i].size(); m++)
                                            for(int n = 0; n<program_hrs[k].size(); n++)
                                                if(test_program_hrs[i].get(m).p_start_hour == program_hrs[k].get(n).p_start_hour)
                                                    path_count++;
                                        
                                        //s = s + ( (double) path_count * 2) / (test_program_hrs[i].size() + program_hrs[k].size());
                                         s = s + ( (double) path_count * 2 * p2.watch_count) / (test_program_hrs[i].size() + program_hrs[k].size());
                                    }
                            }
                    
                    sim[p1.index] = s;
                }
            
        return sim;
       
    }
    
    public void program_startTime_channel_program(Person per)
    {
        double count = 0.0;
        for(int i = 0; i<per.history.size(); i++)
        {
            double s = 0;
           
                for(int j = 0; j<per.history.size(); j++)
                {
                    if(j!=i)
                        if(per.history.get(i).p_start_hour == per.history.get(j).p_start_hour)
                        {
                            if(per.history.get(i).channel == per.history.get(j).channel)
                            {
                                s = s + (per.history.get(i).rating * per.history.get(j).rating);
                                count++;
                            }
                        }
                }
           this.sim_p_startTime_channel_p = this.sim_p_startTime_channel_p + s; 
        }
        //this.sim_p_startTime_channel_p = this.sim_p_startTime_channel_p * count;
    }
    
    public double[] program_startTime_channel_program_test(Person _per, ArrayList<Program> _test_prog)
    {
        double[] sim = new double[_test_prog.size()];
        
        for(int i = 0; i<_test_prog.size(); i++)
        {
            double s = 0;
            
            for(int j = 0; j<_per.history.size(); j++)
            {
                if(_test_prog.get(i).p_start_hour == _per.history.get(j).p_start_hour)
                    if(_test_prog.get(i).channel == _per.history.get(j).channel)
                        s = s + (_per.history.get(j).rating * 1);
                
            }
            sim[i] = s;
            
        }
        return sim;
    }
    
}
