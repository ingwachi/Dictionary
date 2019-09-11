package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class Controller implements Initializable {

    @FXML public javafx.scene.control.Button addButton;
    @FXML public TextField textFieldWord;
    @FXML public TextField textFieldMeaning;
    @FXML public TextField textFieldPartOfSpeech;
    @FXML public TextField textFieldExample;
    public TextField searchTextField;
    @FXML
    private TableView<Vocabulary> tableView;
    @FXML
    private TableColumn<Vocabulary,String> colWord;
    @FXML
    private TableColumn<Vocabulary,String> colPartOfSpeech;
    @FXML
    private TableColumn<Vocabulary,String> colMeaning;
    @FXML
    private TableColumn<Vocabulary,String> colExample;
    Dictionary dictionary = new Dictionary();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        tableView.setItems(getVocabulary());
        tableView.setEditable(true);
        colWord.setCellValueFactory(new PropertyValueFactory<>("Word"));
        colPartOfSpeech.setCellValueFactory(new PropertyValueFactory<>("partOfSpeech"));
        colMeaning.setCellValueFactory(new PropertyValueFactory<>("Meaning"));
        colExample.setCellValueFactory(new PropertyValueFactory<>("Example"));
        colWord.setCellFactory(TextFieldTableCell.forTableColumn());
        colPartOfSpeech.setCellFactory(TextFieldTableCell.forTableColumn());
        colMeaning.setCellFactory(TextFieldTableCell.forTableColumn());
        colExample.setCellFactory(TextFieldTableCell.forTableColumn());
    }
    ObservableList<Vocabulary> result = FXCollections.observableArrayList();

    public ObservableList<Vocabulary> getVocabulary() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Vocabulary.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if(arr[0]!=null && arr[1]!=null&& arr[2]!=null &&arr[3]!=null) {
                    Vocabulary vocabulary = new Vocabulary(arr[0], arr[1], arr[2], arr[3]);
                    dictionary.addWord(vocabulary);
                    result.add(vocabulary);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void handelAddButtonOnClick(javafx.event.ActionEvent actionEvent) {
        Vocabulary vocabulary = new Vocabulary(textFieldWord.getText(),textFieldPartOfSpeech.getText(),textFieldMeaning.getText(),textFieldExample.getText());
        dictionary.addWord(vocabulary);
        result.add(vocabulary);
        textFieldWord.setText(null);
        textFieldPartOfSpeech.setText(null);
        textFieldMeaning.setText(null);
        textFieldExample.setText(null);
        try {

            FileWriter fileWriter = new FileWriter("Vocabulary.txt", false);
            fileWriter.append(vocabulary.getWord());
            fileWriter.append(",");
            fileWriter.append(vocabulary.getPartOfSpeech());
            fileWriter.append(",");
            fileWriter.append(vocabulary.getMeaning());
            fileWriter.append(",");
            fileWriter.append(vocabulary.getExample());
            fileWriter.append("\n");
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEditWordChange(TableColumn.CellEditEvent<Vocabulary, String> vocabularyStringCellEditEvent) {
        Vocabulary vocabulary = tableView.getSelectionModel().getSelectedItem();
        vocabulary.setWord(vocabularyStringCellEditEvent.getNewValue());
    }

    public void onEditPartOfSpeechChange(TableColumn.CellEditEvent<Vocabulary, String> vocabularyStringCellEditEvent) {
        Vocabulary vocabulary = tableView.getSelectionModel().getSelectedItem();
        vocabulary.setPartOfSpeech(vocabularyStringCellEditEvent.getNewValue());
    }

    public void onEditMeaningChange(TableColumn.CellEditEvent<Vocabulary, String> vocabularyStringCellEditEvent) {
        Vocabulary vocabulary = tableView.getSelectionModel().getSelectedItem();
        vocabulary.setMeaning(vocabularyStringCellEditEvent.getNewValue());
    }

    public void onEditExampleChange(TableColumn.CellEditEvent<Vocabulary, String> vocabularyStringCellEditEvent) {
        Vocabulary vocabulary = tableView.getSelectionModel().getSelectedItem();
        vocabulary.setExample(vocabularyStringCellEditEvent.getNewValue());
    }

    public void handelDeleteButtonOnClick(ActionEvent actionEvent) {
        ObservableList<Vocabulary> vocabularies,vocabularie;
        vocabularies = tableView.getItems();
        vocabularie = tableView.getSelectionModel().getSelectedItems();
        vocabularie.forEach(vocabularies::remove);
    }

    public void handelJSONButtonOnClick(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UI/JSON.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            ControllerJSON controllerJSON = fxmlLoader.getController();
            controllerJSON.init(dictionary);
            stage.show();
        }catch (Exception e){
            System.out.println("Can't load new window");
        }
    }

    public void handelXMLButtonOnClick(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UI/XML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            ControllerXML controllerXML = fxmlLoader.getController();
            controllerXML.init(dictionary);
            stage.show();
        }catch (Exception e){
            System.out.println("Can't load new window");
        }
    }

    public void search(){
        FilteredList<Vocabulary> filteredData = new FilteredList<>(result, e -> true);
        searchTextField.setOnKeyReleased(e -> {
            searchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Vocabulary>) user -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    if(user.getWord().contains(newValue.toLowerCase())){
                        return true;
                    }
                    return false;
                });
            }));
            SortedList<Vocabulary> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }

    public void haddleUpdateButtonOnclick(ActionEvent actionEvent) {
        try {
            int i;
            FileWriter fileWriter = new FileWriter("Vocabulary.txt");
            for (i=0;i<result.size();i++) {

                fileWriter.append(result.get(i).getWord());
                fileWriter.append(",");
                fileWriter.append(result.get(i).getPartOfSpeech());
                fileWriter.append(",");
                fileWriter.append(result.get(i).getMeaning());
                fileWriter.append(",");
                fileWriter.append(result.get(i).getExample());
                fileWriter.append("\n");
                fileWriter.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
