/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommender;
import java.util.*;

/**
 *
 * @author user
 */
public class Person {
    
    int id;
    ArrayList<Program> history = new ArrayList<Program>(); 
    
    //int date;
    //int family_id;
    //int person_id;
    //int channel;
    //int genre;
    //double w_begin_time;
    //double w_end_time;
    //double p_start_time;
    //double p_end_time;
    //double rating;
    //int p_id;
    //String title;
    
    Person()
    {
        
    }
    
    /*Person(int _date, int _family_id, int _person_id, int _channel, int _genre, double _w_begin_time, double _w_end_time, double _p_start_time, double _p_end_time, double _rating, int _p_id, String _title )
    {
        this.date = _date;
        this.family_id = _family_id;
        this.person_id = _person_id;
        this.channel = _channel;
        this.genre = _genre;
        this.w_begin_time = _w_begin_time;
        this.w_end_time = _w_end_time;
        this.p_start_time = _p_start_time;
        this.p_end_time = _p_end_time;
        this.rating = _rating;
        this.p_id = _p_id;
        this.title = _title;
    }*/
    
}
