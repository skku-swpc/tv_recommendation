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
    public class Pair implements Comparable<Pair> {
    public final int index;
    public final double value;

    public Pair(int index, double value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        //multiplied to -1 as the author need descending sort order
        return -1 * Double.valueOf(this.value).compareTo(other.value);
    }
}
  
