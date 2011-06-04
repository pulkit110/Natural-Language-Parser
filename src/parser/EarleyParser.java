package parser;

import edu.stanford.nlp.ling.TaggedWord;
import grammar.Grammar;
import grammar.Rule;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sapan, Pulkit & Himanshu
 */
public class EarleyParser implements Runnable {

    Grammar grammar;
    UI parent;
    List <TaggedWord> Sentence;
    List <String> parseTrees;
    public Boolean halt = true;

    /**
     * Constructor
     * @param parent    Reference to the parent of this object
     */
    public EarleyParser(UI parent)
    {
        this.parent = parent;
    }

    /**
     * Set the sentence to be parsed
     * @param Sentence
     */
    public void setSentence(List <TaggedWord> Sentence)
    {
        System.out.println("Sentence set");
        this.Sentence = Sentence;
    }

    /**
     * Parse the sentence
     * @return
     */
    public List <String> parse()
    {
        System.out.println(Sentence);
        System.out.println();
        if (grammar == null) {
            //TODO: Better Error Handling
            return null;
        }

        State.Sentence = Sentence;
        for (int i = 0; i < Sentence.size(); ++i) {
            System.out.print(Sentence.get(i).word() + "/" + Sentence.get(i).tag() + " ");
        }

        Chart c = new Chart(Sentence.size()+1);
        c.addState(0, new State(new Rule("ROOT", "S"), 0, 0, 0), false);

        for (int i = 0; i < Sentence.size()+1; ++i) {
            parent.updateStats((i*100)/(Sentence.size()+1));
            System.out.println("Started processing Chart [" + i + "]" + ". No. of states is " + c.getNStates(i));
            for (int j = 0; j < c.getNStates(i); ++j) {
                if (!c.getState(i, j).isComplete()) {
                    predict(c.getState(i, j), c);
                    scan(c.getState(i, j), c, Sentence);
                } else {
                    complete(c.getState(i, j), c);
                }
            }
            System.out.println("Finished processing Chart [" + i + "]" + ". No. of states is " + c.getNStates(i));
        }

        /////////////////////FOR TESTING///////////////////////////////
/*
        for (int j = 0; j < c.size(); ++j) {
            System.out.println("Chart " + j);
            for (int i = 0; i < c.getStatesList(j).size(); ++i) {
                c.getStatesList(j).get(i).PrintState();
            }
        }
*/
        int nParseTrees = 0;
        List <String> ParseTrees = new ArrayList<String>();

        for (int j = 0; j < c.size(); ++j) {
            //System.out.println("Chart " + j);
            for (int i = 0; i < c.getStatesList(j).size(); ++i) {
                State s = c.getStatesList(j).get(i);
                if (s.r.getLHS().equals("ROOT") && s.RulePosition == 0 && s.isComplete() && s.DotPositionInSentence == Sentence.size()-1) {
                    System.out.println("Parsing successful");
                    nParseTrees++;
                    StringBuilder tree = new StringBuilder();
                    s.PrintState();
                    System.out.println("Tree");
                    s.printTree(tree);
                    ParseTrees.add(tree.toString());
                }
            }
        }
        System.out.println(nParseTrees + " parse trees found.");
        ////////////////////////////////////////////////////////////////

        return ParseTrees;
    }

    /**
     * Set the grammar
     * @param grammar
     */
    public void setGrammar(Grammar grammar)
    {
        this.grammar = grammar;
    }

    /**
     * Predictor operation.
     * @param s
     * @param chart
     */
    private void predict(State s, Chart chart)
    {

        List<Rule> rules = grammar.FindRulesWithLHS(s.getNextCat());
        for (int k = 0; k < rules.size(); ++k) {
            State temp = new State(rules.get(k), s.DotPositionInSentence, s.DotPositionInSentence, s.DotPositionInSentence);
            //if (!(chart.getStatesList(s.DotPositionInSentence).contains(temp))) {
            if (!(chart.contains(s.DotPositionInSentence, temp))) {
                chart.addState(s.DotPositionInSentence, temp, true);
                //System.out.println("Predictor added "); temp.PrintState();
            }
            
        }
    }

    /**
     * Scanner operation
     * @param s
     * @param chart
     * @param sentence
     */
    private void scan(State s, Chart chart, List <TaggedWord> sentence)
    {
        
        if (sentence.get(s.DotPositionInSentence).tag().equalsIgnoreCase(s.r.getRHS().get(s.DotPosition/*InSentence?TODO CHECK*/-s.RulePosition).toString())) {
            State temp = new State(new Rule(sentence.get(s.DotPositionInSentence).tag(), sentence.get(s.DotPositionInSentence).tag()), s.DotPositionInSentence, s.DotPositionInSentence+1, s.DotPositionInSentence+1);
            
            if (!(chart.contains(s.DotPositionInSentence+1, temp))) {
                chart.addState(s.DotPositionInSentence+1, temp, true);
               
            }
        }
    }

    /**
     * Completer Operation
     * @param s
     * @param chart
     */
    private void complete(State s, Chart chart)
    {
        
        List <State> statesList = chart.getStatesList(s.RulePosition);
        for (int i = 0; i < statesList.size(); ++i) {
            if (!statesList.get(i).isComplete() && statesList.get(i).getNextCat().equals(s.r.getLHS())) {
                State temp = new State(new Rule(statesList.get(i).r), statesList.get(i).RulePosition, s.DotPositionInSentence, statesList.get(i).DotPosition+1);
                if (statesList.get(i).PrevStates != null) {
                    temp.PrevStates = new ArrayList<State>(statesList.get(i).PrevStates);
                }
                temp.addPrevState(s);
                
                    chart.addState(s.DotPositionInSentence, temp, false);
            }
        }
    }

    /**
     * Load the grammar from file.
     * @param grammar
     * @return
     */
    public Boolean loadGrammar(String grammar)
    {
        Grammar g = new Grammar();

        try
        {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new
                    FileInputStream(grammar);

            // Convert our input stream to a
            // DataInputStream
            DataInputStream in =
                    new DataInputStream(fstream);

            // Continue to read lines while
            // there are still some left to read
            while (in.available() !=0)
            {
                String rule = in.readLine();
                String[] lhsrhs = rule.split("->");
                String lhs = lhsrhs[0].trim();
                String[] rhs = lhsrhs[1].trim().split(" ");
                g.AddRule(new Rule(lhs, rhs));

            }

            in.close();
        }
        catch (Exception e)
        {
            System.err.println("File input error");
            return false;
        }

        setGrammar(g);
        return true;
    }

    /**
     * Run the thread
     */
    public void run() {
        System.out.println("run called.");
        parseTrees = null;
        halt = false;
        parent.updateStats(0);
        parseTrees = parse();
        halt = true;
        parent.updateStats(0);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}