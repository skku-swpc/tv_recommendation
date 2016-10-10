/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommender;

/**
 *
 * @author user
 */
public class Program {
    
    int date;
    int channel;
    double p_start_time;
    double p_end_time;
    int genre;
    int p_id;
    String title;
    double rating;
    double p_start_hour;
    double p_end_hour;
    int watch_count = 0;
    int index = 0;
    
    Program()
    {
        
    }
    
    Program(int _date, int _channel, double _p_start_time, double _p_end_time, int _genre, int _p_id, String _title)
	{
                this.date = _date;	
                this.channel = _channel;
                this.p_start_time = _p_start_time; p_end_time = _p_end_time;
                this.genre = _genre;
                this.p_id = _p_id;
                this.title = _title;
                //this.rating = _rating;
                this.p_start_hour = Cal_Time.Get_Hour(this.p_start_time);
				
		
	}
    
    void display_programs()
    {
        System.out.println(this.date + "  " + this.channel + "  " + this.p_start_time + "  " + this.p_end_time + "  " + this.genre + "  " + this.p_id + "  " +this.title);
    }
    
}
