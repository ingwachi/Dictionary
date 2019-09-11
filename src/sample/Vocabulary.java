package sample;

import javafx.beans.property.SimpleStringProperty;

public class Vocabulary {
    private SimpleStringProperty word;
    private SimpleStringProperty partOfSpeech;
    private SimpleStringProperty meaning;
    private SimpleStringProperty example;

    Vocabulary(String word,String partOfSpeech,String meaning,String example){
        this.word = new SimpleStringProperty(word);
        this.partOfSpeech = new SimpleStringProperty(partOfSpeech);
        this.meaning = new SimpleStringProperty(meaning);
        this.example = new SimpleStringProperty(example);
    }

    public String getWord(){
        return word.get();
    }

    public void setWord(String word) {
        this.word.set(word);
    }

    public String getPartOfSpeech() {
        return partOfSpeech.get();
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech.set(partOfSpeech);
    }

    public String getMeaning() {
        return meaning.get();
    }

    public void setMeaning(String meaning) {
        this.meaning.set(meaning);
    }

    public String getExample() {
        return example.get();
    }

    public void setExample(String example) {
        this.example.set(example);
    }
}
