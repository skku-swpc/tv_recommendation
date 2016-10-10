/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommender;

/**
 *
 * @author umar
 */
public class Cal_Time {

	static int Get_Week(int date)
	{
		int week = 0;
		if(130107<=date && date<=130113)
			week = 0;
		else if(130114<=date && date<=130120)
			week = 1;
		else if(130121<=date && date<=130127)
			week = 2;
		else if(130128<=date && date<=130203)
			week = 3;
		else if(130204<=date && date<=130210)
			week = 4;
		else if(130211<=date && date<=130217)
			week = 5;
		else if(130218<=date && date<=130224)
			week = 6;
		else if(130225<=date && date<=130303)
			week = 7;
		else if(130304<=date && date<=130310)
			week = 8;
		else if(130311<=date && date<=130317)
			week = 9;
		else if(130318<=date && date<=130324)
			week = 10;
		else if(130325<=date && date<=130331)
			week = 11;
		else if(130401<=date && date<=130407)
			week = 12;
		else if(130408<=date && date<=130414)
			week = 13;
		else if(130415<=date && date<=130421)
			week = 14;
		else if(130422<=date && date<=130428)
			week = 15;
		else if(130429<=date && date<=130505)
			week = 16;
		else if(130506<=date && date<=130512)
			week = 17;
		else if(130513<=date && date<=130519)
			week = 18;
		else if(130520<=date && date<=130526)
			week = 19;
		else if(130527<=date && date<=130602)
			week = 20;
		else if(130603<=date && date<=130609)
			week = 21;
		else if(130610<=date && date<=130616)
			week = 22;
		else if(130617<=date && date<=130623)
			week = 23;
		else if(130624<=date && date<=130630)
			week = 24;
		
		return week;
	}
	
	static int Get_Day(int date)
	{
		int day = 0;
		if(130101<=date && date<=130131)
			day = Math.abs((date-130107)%7);
		if(130201<=date && date<=130228)
			day = Math.abs((date-130204)%7);
		if(130301<=date && date<=130331)
			day = Math.abs((date-130304)%7);
		if(130401<=date && date<=130430)
			day = Math.abs((date-130408)%7);
		if(130501<=date && date<=130531)
			day = Math.abs((date-130506)%7);
		if(130601<=date && date<=130630)
			day = Math.abs((date-130603)%7);
		return day;
	}
	
	static int Get_Hour(double time)
	{
		double time24[] = {0.083333333,	0.125,	0.166666667,0.208333333,0.25,0.291666667,0.333333333,
				0.375,	0.416666667,0.458333333,0.5,0.541666667,0.583333333,0.625,0.666666667,0.708333333,0.75,	0.791666667,
				0.833333333,0.875,0.916666667,0.958333333,1,1.041666667,1.083333333};
		int t = 0;
		for(int i=0; i<24; i++)
		{
			if(time >=time24[i] && time <time24[i+1] )
				t = i;
		}
		return t;
	}
	
	static int Get_Weekend(int date)
	{
		int weekend = 0;
		if(Get_Day(date) >= 0 && Get_Day(date) <= 4)
			weekend = 0;
		else
			weekend = 1;
		return weekend;
	}
}
