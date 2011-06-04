package grammar;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Sapan, Pulkit & Himanshu
 */
public class Grammar {

    List<Rule> rules;

    /**
     * Constructor for Grammar.
     */
    public Grammar()
    {
        rules = new ArrayList<Rule>();
    }

    /**
     * Function to add a rule to the grammar.
     * @param r Rule to be added
     */
    public void AddRule(Rule r)
    {
        rules.add(r);
    }

    /**
     * Function to find a Rule with given left hand side
     * @param lhs Left hand Side of the rule
     * @return the corresponding Rule or null if no such rule exists in the grammar
     */
    public List<Rule> FindRulesWithLHS(String lhs)
    {
        List <Rule> rulesWithGivenLHS = new ArrayList<Rule>();
        for (int i = 0; i < rules.size(); ++i) {   
            if (rules.get(i).getLHS().equals(lhs)) {
                rulesWithGivenLHS.add(rules.get(i));
            }
        }
        return rulesWithGivenLHS;
    }

    /**
     * Print all the rules in the grammar
     */
    public void printGrammarRules()
    {
        for (int i = 0; i < rules.size(); ++i) {
            rules.get(i).printRule();
        }
    }
}