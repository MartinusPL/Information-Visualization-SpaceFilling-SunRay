/*
 * The MIT License
 *
 * Copyright 2015 Marcin Grzesiak <kontakt@mgrzesiak.eu>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.gda.pg.eti.ksi.wi.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pl.gda.pg.eti.ksi.wi.project.models.DataRecord;

/**
 * FXML Controller class
 *
 * @author Marcin Grzesiak <kontakt@mgrzesiak.eu>
 */
public class EditWindowController implements Initializable {

    private TreeSet<DataRecord> data;
    private ObservableList<DataRecord> list;
    private Stage stage;
    @FXML
    private Button generateBtn;

    @FXML
    private TableView<DataRecord> dataTable = new TableView<>();
    @FXML
    private TableColumn<DataRecord, String> firstLevelColumn = new TableColumn<>("Concern");
    @FXML
    private TableColumn<DataRecord, String> secondLevelColumn = new TableColumn<>("Type");
    ;
    @FXML
    private TableColumn<DataRecord, String> thirsLevelColumn = new TableColumn<>("Mark");
    ;
    @FXML
    private TableColumn<DataRecord, String> fourthLevelColumn = new TableColumn<>("Model");
    ;
    @FXML
    private TableColumn<DataRecord, Number> quantityColumn = new TableColumn<>("Quantity");
    ;
    
    @FXML
    private Label statusLbl;

    public void setData(TreeSet<DataRecord> data) {
        this.data = data;
        list = FXCollections.observableList(new ArrayList<DataRecord>(data));
        dataTable.setItems(list);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGenerateBtn(Button btn) {
        this.generateBtn = btn;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        firstLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getConcernProperty());
        secondLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        thirsLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getMarkProperty());
        fourthLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getModelProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getQuantityProperty());

        firstLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstLevelColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<DataRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<DataRecord, String> t) {
                        ((DataRecord) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setConcern(t.getNewValue());
                    }
                }
        );

        secondLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        secondLevelColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<DataRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<DataRecord, String> t) {
                        ((DataRecord) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setType(t.getNewValue());
                    }
                }
        );

        thirsLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        thirsLevelColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<DataRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<DataRecord, String> t) {
                        ((DataRecord) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setMark(t.getNewValue());
                    }
                }
        );

        fourthLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fourthLevelColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<DataRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<DataRecord, String> t) {
                        ((DataRecord) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setModel(t.getNewValue());
                    }
                }
        );

        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>() {
            @Override
            public Number fromString(String string) {
                try {
                    string = string.replace(',', '.');
                    Double d = Double.parseDouble(string);
                    Integer ret = d.intValue();
                    return ret;
                } catch (NumberFormatException ex) {
                    System.out.println("Number format Exception!");
                    statusLbl.setText("B³±d! Niedozwolony format danych w polu Ilo¶æ. Dopuszczalne tylko liczby ca³kowite.");
                    return 0;
                }
            }

            @Override
            public String toString(Number object) {
                Integer i = (Integer) object;
                return i.toString();
            }
        }));
        quantityColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<DataRecord, Number>>() {
                    @Override
                    public void handle(CellEditEvent<DataRecord, Number> t) {
                        ((DataRecord) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setQuantity(t.getNewValue().intValue());
                    }
                }
        );

        dataTable.setEditable(true);
        firstLevelColumn.setEditable(true);
        secondLevelColumn.setEditable(true);
        thirsLevelColumn.setEditable(true);
        fourthLevelColumn.setEditable(true);
        quantityColumn.setEditable(true);
    }

    @FXML
    private void closeBtnClick(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void saveBtnBlick(ActionEvent event) {
        TreeSet<DataRecord> newData = new TreeSet(list);
        data.clear();
        data.addAll(newData);
        generateBtn.setDisable(false);
        stage.close();
    }

    @FXML
    private void exportBtnClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("c:\\"));
        fc.setTitle("Wybór piku do zapisania danych");
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add("*.csv");
        extensions.add("*.CSV");
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV files", extensions));
        File src = fc.showSaveDialog(null);

        if (src != null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(src));
                StringBuilder sb = new StringBuilder();
                String line = "Koncern;Typ;Marka;Model;Ilosc";
                bw.write(line);
                bw.newLine();

                for (DataRecord dr : list) {
                    sb.delete(0, sb.length());

                    sb.append(dr.getConcern());
                    sb.append(';');
                    sb.append(dr.getType());
                    sb.append(';');
                    sb.append(dr.getMark());
                    sb.append(';');
                    sb.append(dr.getModel());
                    sb.append(';');
                    sb.append(dr.getQuantity());

                    line = sb.toString();
                    bw.write(line);
                    bw.newLine();
                }

                statusLbl.setText("Plik zosta³ pomy¶lnie zapisany pod adresem " + src.getAbsolutePath());
                bw.close();
            } catch (Exception e) {
                statusLbl.setText("Nie uda³o siê zapisaæ pliku " + src.getAbsolutePath());
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void AddBtnClick(ActionEvent event) {
        list.add(new DataRecord("", "", "", "", 0));
    }

    @FXML
    private void RemoveBtnClick(ActionEvent event) {
        list.remove(dataTable.getSelectionModel().getSelectedItem());
    }

}
