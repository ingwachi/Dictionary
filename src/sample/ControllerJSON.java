package sample;

import javafx.scene.control.Label;

public class ControllerJSON {

    public Label JSONText;

    public void init(Dictionary dictionary) {
        String json = dictionary.format(new MyFormatter() {
            @Override
            public String format(Object object) {
                Dictionary dict = (Dictionary) object;
                StringBuilder str = new StringBuilder();
                for (Vocabulary d : dict.getDictionary()) {
                    str.append("\t(\n");
                    str.append("\t\tvocab: \"").append(d.getWord()).append("\",\n");
                    str.append("\t\tpathOfSpeech: \"").append(d.getPartOfSpeech()).append("\",\n");
                    str.append("\t\tmeaning: \"").append(d.getMeaning()).append("\",\n");
                    str.append("\t\texample: \"").append(d.getExample()).append("\",\n");
                    str.append("},\n");
                }
                str.append("]\n");
                return str.toString();
            }
        });
        JSONText.setText(json);
    }

}
