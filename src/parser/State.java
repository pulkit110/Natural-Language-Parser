package parser;

import edu.stanford.nlp.ling.TaggedWord;
import grammar.Rule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sapan, Pulkit & Himanshu
 */
public class State {

    Rule r;
    int RulePosition;
    int DotPosition;
    int DotPositionInSentence;
    public List<State> PrevStates;
    public static List<TaggedWord> Sentence;

    /**
     * Constructor
     * @param r
     * @param RulePosition
     * @param DotPositionInSentence
     * @param DotPosition
     */
    public State(Rule r, int RulePosition, int DotPositionInSentence, int DotPosition)
    {
        this.r = r;
        this.RulePosition = RulePosition;
        this.DotPosition = DotPosition;
        this.DotPositionInSentence = DotPositionInSentence;
        PrevStates = null;
    }

    /**
     * Constructor
     * @param rule
     */
    State(Rule rule) {
        this.r = rule;
        this.RulePosition = 0;
        this.DotPosition = 0;
        this.DotPositionInSentence = 0;
        PrevStates = null;
    }

    /**
     * Print the state
     */
    public void PrintState()
    {
        System.out.print(r.getLHS() + " -> ");
        for (int i = 0; i < DotPosition-RulePosition; ++i) {
            System.out.print(r.getRHS().get(i) + " ");
        }
        System.out.print(". ");
        for (int i = DotPosition-RulePosition; i < r.getRHS().size(); ++i) {
            System.out.print(r.getRHS().get(i) + " ");
        }
        System.out.println("[" + RulePosition + ", " + DotPositionInSentence + "]");
    }

    /**
     * Equal operation for State
     * @param state
     * @return
     */
    @Override
    public boolean equals (Object state)
    {
        if (state.getClass() == this.getClass()) {
            boolean isEqual = this.r.equals(((State)state).r)
                                && (this.DotPosition == ((State)state).DotPosition)
                                && (this.RulePosition == ((State)state).RulePosition)
                                && (this.DotPositionInSentence == ((State)state).DotPositionInSentence);
            return (isEqual);
        }
        return false;
    }

    /**
     * Automatically Generated Function.
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.r != null ? this.r.hashCode() : 0);
        hash = 37 * hash + this.RulePosition;
        hash = 37 * hash + this.DotPosition;
        hash = 37 * hash + this.DotPositionInSentence;
        return hash;
    }

    /**
     * Get the category after the dot.
     * @return
     */
    public String getNextCat()
    {

        return (r.getRHS().get(DotPosition-RulePosition));
    }

    /**
     * Check if the state is complete.
     * @return
     */
    public boolean isComplete()
    {
        if ((DotPosition-RulePosition) >= r.getRHS().size()) {
            return true;
        }
        return false;
    }

    /**
     * Add a pointer to previous state
     * @param s
     */
    public void addPrevState(State s)
    {
        if (this.PrevStates == null) {
            this.PrevStates = new ArrayList<State>();
        }
        PrevStates.add(s);
    }

    /**
     * Print the tree recursively by calling printTree function on Previous States.
     * @param tree The output tree
     */
    public void printTree(StringBuilder tree)
    {
  
        tree.append("(").append(r.getLHS().toString());

        if (PrevStates == null) {
            
            tree.append(" ").append(Sentence.get(RulePosition).word());
        } else {
            for (int i = 0; i < PrevStates.size(); ++i) {
                PrevStates.get(i).printTree(tree);//tree);
            }
        }
        tree.append(")");
    }
}