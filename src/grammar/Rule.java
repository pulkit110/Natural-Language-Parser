/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grammar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pulkit, Sapan & Himanshu
 */
public class Rule {

    String lhs;
    List <String> rhs;

    /**
     * Constructor for Rule
     * @param lhs   LHS of Grammar
     * @param rhs   RHS of Rule
     */
    public Rule(String lhs, String[] rhs)
    {
        initialize(lhs, rhs);
    }

    /**
     * Constructor for Rule
     * @param lhs
     * @param rhs
     */
    public Rule(String lhs, String rhs)
    {
        String[] a_rhs = new String[1];
        a_rhs[0] = rhs;
        initialize(lhs, a_rhs);
    }

    /**
     * Constructor for Rule
     * @param r
     */
    public Rule(Rule r)
    {
        this.lhs = r.getLHS();
        this.rhs = new ArrayList<String>();
        //System.out.println(r.getRHS().size());
        for (int i = 0; i < r.getRHS().size(); ++i) {
            //System.out.println(i);
            this.rhs.add(r.getRHS().get(i));
        }
    }

    /**
     * Print the rule
     */
    public void printRule()
    {
        System.out.print(this.lhs.toString());
        System.out.print(" -> ");
        System.out.print(rhs.size());
        for (int i = 0; i < rhs.size(); ++i) {
            System.out.print(rhs.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Equals operation for Rule
     * @param rule
     * @return
     */
    @Override
    public boolean equals (Object rule)
    {
        if (rule.getClass() == this.getClass()) {
            boolean isEqual = (this.lhs == null ? ((Rule) rule).lhs == null : this.lhs.equals(((Rule) rule).lhs))
                                && (this.rhs.equals(((Rule)rule).rhs));
            return isEqual;
        }
        return false;
    }

    /**
     * Automatically Generated Function.
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.lhs != null ? this.lhs.hashCode() : 0);
        hash = 59 * hash + (this.rhs != null ? this.rhs.hashCode() : 0);
        return hash;
    }

    /**
     * Initialize the rule
     * @param lhs
     * @param rhs
     */
    private void initialize(String lhs, String[] rhs)
    {
        this.rhs = new ArrayList<String>();

        try {
            this.lhs = lhs.trim();
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding rhs to rule: " + e);
        }

        for (int i = 0; i < rhs.length; ++i) {
            try {
                //System.out.println(rhs[i]);
                this.rhs.add(rhs[i].trim());
            } catch (IllegalArgumentException e) {
                System.err.println("Error adding rhs to rule: " + e);
            }
        }
    }

    /**
     * Return LHS
     * @return Returns RHS
     */
    public String getLHS()
    {
        return lhs;
    }

    /**
     * Return RHS
     * @return Returns RHS
     */
    public List <String> getRHS()
    {
        return rhs;
    }
    
}