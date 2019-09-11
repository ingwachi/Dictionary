package sample;

import javafx.scene.control.Label;

public class ControllerXML {

    public Label XMLText;

    public void init(Dictionary dictionary) {
        String xml = dictionary.format(new MyFormatter() {
            @Override
            public String format(Object object) {
                Dictionary dict = (Dictionary) object;
                StringBuilder str = new StringBuilder();
                for (Vocabulary d : dict.getDictionary()) {
                    str.append("\t<Vocab word=\"").append(d.getWord()).append("\">\n");
                    str.append("\t\t<PartOfSpeech>").append(d.getPartOfSpeech()).append("</PartOfSpeech>\n");
                    str.append("\t\t<Meaning>").append(d.getMeaning()).append("</Meaning>\n");
                    str.append("\t\t<Example>").append(d.getExample()).append("</Example>\n");
                    str.append("</Vocab>\n");
                }
                str.append("</Dictionary>\n");
                return str.toString();
            }
        });
        XMLText.setText(xml);
    }
}
