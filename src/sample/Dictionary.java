package sample;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Vocabulary> dictionaryList;
    public Dictionary(){
        dictionaryList = new ArrayList<>();
    }
    public void addWord(Vocabulary vocab){
        dictionaryList.add(vocab);
    }
    public String format(MyFormatter formatter) {
        return formatter.format(this);
    }

    public ArrayList<Vocabulary> getDictionary() {
        return dictionaryList;
    }
}
