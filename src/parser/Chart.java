package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Pulkit, Sapan & Himanshu
 */
public class Chart {


    List <List <State> > chart = new ArrayList <List <State> > ();
    List <HashSet <State> > chartForSearching = new ArrayList <HashSet <State> > ();

    /**
     * Constructor for chart
     * @param N
     */
    public Chart(int N)
    {
        for (int i = 0; i < N; ++i) {
            chart.add(new ArrayList<State>());
            chartForSearching.add(new HashSet<State>());
        }
    }

    /**
     *
     * @return size of the chart
     */
    public int size()
    {
        return chart.size();
    }

    /**
     * Add a state to the chart
     * @param i     Add state to chart[i]
     * @param s     State to be added
     * @param addToBothCharts
     */
    public void addState(int i, State s, Boolean addToBothCharts)
    {
        if (i > chart.size()) {
            //throw ArrayIndexOutOfBoundsException;
            return;
        }
        chart.get(i).add(s);
        if (addToBothCharts) {
            chartForSearching.get(i).add(s);
        }
    }

    /**
     * Get a state at particular index
     * @param i     Chart Number
     * @param j     State Number
     * @return      The state at requested index
     */
    public State getState(int i, int j)
    {
        if (j < chart.get(i).size()) {
            return chart.get(i).get(j);
        } else {
            return null;
        }
    }

    /**
     * Get the number of states
     * @param i     Chart Number
     * @return  Number of states in the chart
     */
    public int getNStates(int i)
    {
        if (i < chart.size()) {
            return chart.get(i).size();
        } else {
            return 0;
        }
    }

    /**
     * Get all the states
     * @param i Chart number
     * @return  List of all the states in chart[i]
     */
    public List<State> getStatesList(int i)
    {
        if (i < chart.size()) {
            return chart.get(i);
        } else {
            return null;
        }
    }

    /**
     * Check whether State s exists in Chart[i]
     * @param i
     * @param s
     * @return  TRUE if state exists, false otherwise.
     */
    public Boolean contains(int i, State s)
    {
        return chartForSearching.get(i).contains(s);
    }
}