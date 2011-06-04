package parser;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pulkit, Sapan and Himanshu
 */
public class Tagger {
    final String model = "lib/left3words-wsj-0-18.tagger";
    MaxentTagger mt;
    List<ArrayList<? extends HasWord>> sentences;

    int currIndex;

    /**
     * Constructor
     */
    public Tagger()
    {
        this.sentences = null;
        try {
            this.mt = new MaxentTagger(model);
        } catch (IOException ex) {
            Logger.getLogger(Tagger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Tagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Function to tag a given file.
     * @param FileToTag
     * @return
     */
    @SuppressWarnings("static-access")
    public List <String> Tag(String FileToTag)
    {
        List<String> TaggedText = new ArrayList<String>();
        
        List<ArrayList<? extends HasWord>> SentencesInFile;
        try {
            SentencesInFile = mt.tokenizeText(new BufferedReader(new FileReader(FileToTag)));
            for (ArrayList<? extends HasWord> sentence : SentencesInFile) {
              ArrayList<TaggedWord> tSentence = mt.tagSentence(sentence);
              
              TaggedText.add(Sentence.listToString(tSentence, false));
            }
            return TaggedText;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tagger.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Function to tag the next sentence in the file.
     * @param Sentence
     * @return
     */
    public String TagSentence(String Sentence)
    {
        return (mt.tagString(Sentence));
    }

    /**
     * Set the file to be tagged.
     * @param FileToTag
     */
    public void setFile(String FileToTag)
    {

        try {
            sentences = mt.tokenizeText(new BufferedReader(new FileReader(FileToTag)));
            currIndex = 0;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the next tagged sentence.
     * @return
     */
    public List<TaggedWord> GetNextTagged()
    {
        if (currIndex < sentences.size()) {
            ArrayList<TaggedWord> tSentence = mt.tagSentence(sentences.get(currIndex));
            currIndex++;
            return tSentence;
        }
        return null;
    }
}