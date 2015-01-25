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

import pl.gda.pg.eti.ksi.wi.project.logic.SunRayDrawer;
import pl.gda.pg.eti.ksi.wi.project.models.DataRecord;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
/**
 *
 * @author Marcin
 */
public class MainWindowController implements Initializable {
    
    // FXML Controls 
    @FXML
    private Canvas DrawingSpace;
    @FXML
    private Button generateBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Label statusLbl;
    @FXML
    private CheckBox graphTypeChooser;
    
    // Class Properties
    private SunRayDrawer srd;
    private WritableImage wi;
    
    @FXML
    private void CloseBtnClick(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void GenerateBtnClick(ActionEvent event) {
        statusLbl.setText("Trwa generowanie wizualizacji...");
        boolean isCentered = (graphTypeChooser.isSelected());
        srd.Draw(isCentered);
        statusLbl.setText("Wizualizacja wygenerowana!");
        
        saveBtn.setDisable(false);
        generateBtn.setDisable(true);
    }
    
    @FXML
    private void EditBtnClick(ActionEvent event) {
        statusLbl.setText("Ta metoda nie jest jeszcze zaimplementowana.");
    }
    
    @FXML
    private void SaveBtnClick(ActionEvent event) {        
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\"));
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add("*.png");
        extensions.add("*.PNG");
        fc.setTitle("Wybieranie katalogu do zapisu obrazu");
        File dst = fc.showSaveDialog(null);
        if (dst != null){
            DrawingSpace.snapshot(null, wi);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", dst);
                statusLbl.setText("Obraz zosta³ zapisany do pliku " + dst.getAbsolutePath());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                statusLbl.setText("Nie uda³o siê zapisaæ obrazu do pliku " + dst.getAbsolutePath());
            }
        }
    }
    
    @FXML
    private void LoadBtnClick(ActionEvent event) {
        
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("c:\\"));
        fc.setTitle("Wybór piku do wczytania danych");
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add("*.csv");
        extensions.add("*.CSV");
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV files", extensions));
        File src = fc.showOpenDialog(null);
        
        if (src != null){
            try {
                BufferedReader br = new BufferedReader(new FileReader(src));
                String line;
                int counter = 0;
                int ommited = 0;
                while ((line = br.readLine()) != null){
                    // ommit first row
                    if (counter == 0){
                        counter ++;
                        continue;
                    }
                    
                    String tmp[] = line.split(";");
                    try{
                        String concern = tmp[0];
                        String type = tmp[1];
                        String mark = tmp[2];
                        String model = tmp[3];
                        int quantity = Integer.parseInt(tmp[4]);
                        
                        DataRecord dr = new DataRecord(concern, type, mark, model, quantity);
                        this.srd.AddData(dr);
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                        ommited ++;
                    }
                    
                    counter ++;
                }
                
                if ((ommited == 0) && (counter > 0)){
                    statusLbl.setText("Wczytano pomy¶lnie " + counter + " rekordów");
                    editBtn.setDisable(false);
                    generateBtn.setDisable(false);
                    graphTypeChooser.setDisable(false);
                } else if ((ommited > 0) && (counter - ommited > 0)){
                    statusLbl.setText("Wczytano pomy¶lnie " + (counter - ommited) + " rekordów. Pominiêto " + ommited + " z powodu b³êdów");
                    editBtn.setDisable(false);
                    generateBtn.setDisable(false);
                    graphTypeChooser.setDisable(false);
                } else {
                    statusLbl.setText("Wczytanie pliku nie powiod³o siê - wczytano 0 rekordów");
                }
                
                br.close();
            } catch (Exception e){
                statusLbl.setText("Nie uda³o siê wczytaæ pliku " + src.getAbsolutePath());
                System.out.println(e.getMessage());
            }
        }
        
        /*statusLbl.setText("Ta metoda nie jest jeszcze zaimplementowana - ma na sta³e zapisane warto¶ci!");
        srd.AddData(new DataRecord("FORD", "osobowy", "Ford", "Fiesta", 50000));
        srd.AddData(new DataRecord("FORD", "ciezarowy", "Ford", "Transit", 30000));
        srd.AddData(new DataRecord("FORD", "ciezarowy", "Ford", "Superasny", 30000));
        srd.AddData(new DataRecord("FORD", "ciezarowy", "Kia", "Sportage", 30000));
        srd.AddData(new DataRecord("FORD", "osobowy", "Ford", "Ka", 20000));
        srd.AddData(new DataRecord("FORD", "osobowy", "Ford", "Focus", 30000));*/
        
        
    }
    
    @FXML
    private void GraphTypeCBStateChange(){
        generateBtn.setDisable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        wi = new WritableImage((int)DrawingSpace.getWidth(), (int)DrawingSpace.getHeight());
        
        GraphicsContext gc = DrawingSpace.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        //gc.fillArc(0, 0, 100, 100, 0, 90, ArcType.ROUND);
        srd = new SunRayDrawer(gc,statusLbl,DrawingSpace);
    }
}




