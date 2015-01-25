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

package pl.gda.pg.eti.ksi.wi.project.logic;

import pl.gda.pg.eti.ksi.wi.project.models.ElementCounter;
import pl.gda.pg.eti.ksi.wi.project.models.DataRecord;
import java.util.ArrayList;
import java.util.TreeSet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 *
 * @author Marcin
 */

public class SunRayDrawer {
    private GraphicsContext graphicContext;
    private TreeSet<DataRecord> data;
    private Label statusLbl;
    private javafx.scene.canvas.Canvas drawingSpace;
    
    private int lvlCounter[];
    
    private ArrayList<ElementCounter> elementsSecond;
    private ArrayList<ElementCounter> elementsThird;
    
    
    public SunRayDrawer(GraphicsContext g, Label l, Canvas c){
        this.graphicContext = g;
        this.statusLbl = l;
        this.drawingSpace = c;
        this.data = new TreeSet<DataRecord>() {};
        this.lvlCounter = new int[4];
    }
    
    public void AddData(DataRecord r){
        this.data.add(r);
    }
    
    private void AnalyzeData(){
        
        fillElementsSecond();
        fillElementsThird();
    }
    
    public void Draw(){
        if (this.data.isEmpty()){
            statusLbl.setText("Ta metoda nie jest jeszcze zaimplementowana.");
            return;
        }
        
        AnalyzeData();
        
        int r,g,b;
        r = 0xFF;
        g = 0x00;
        b = 0x00;
        
        Color base;
        
        base = Color.YELLOW;
        DrawFourthLevel(base);
        
        base = Color.ORANGE;
        DrawThirdLevel(base);
        
        base = Color.RED;
        DrawSecondLevel(base);
        
        base = Color.BLACK;
        DrawFirstLevel(base);
    }
    
    // Concern
    private void DrawFirstLevel(Color startColor){
        double radius = countRadius(1);
        graphicContext.setFill(startColor);
        graphicContext.fillArc(-radius, drawingSpace.getHeight() - radius, 2*radius, 2*radius, 0, 90, ArcType.ROUND);
    }
    
    // Type
    private void DrawSecondLevel(Color startColor){
        int lvl = 2;
        double radius = countRadius(lvl);
        
        int sum = fillElementsSecond();
        
        // drawing elements
        double last_degrees = 0;
        for (ElementCounter ec : elementsSecond){
            double degrees = (float)ec.quantity / (float)sum * 90.00;
            
            startColor = startColor.darker();
            graphicContext.setFill(startColor);
            //-prev_radius, drawingSpace.getHeight() - radius + prev_radius
            graphicContext.fillArc(-radius, drawingSpace.getHeight() - radius, 2*radius, 2*radius, last_degrees, degrees, ArcType.ROUND);
            last_degrees += degrees;
        }
    }

    // Mark
    private void DrawThirdLevel(Color startColor){
        int lvl = 3;
        double radius = countRadius(lvl);
        
        int sum = fillElementsThird();
        
        // drawing elements
        double last_degrees = 0;
        for (ElementCounter ec : elementsThird){            
            double degrees = (float)ec.quantity / (float) sum * 90.00;
            
            startColor = startColor.darker();
            graphicContext.setFill(startColor);
            graphicContext.fillArc(-radius, drawingSpace.getHeight() - radius, 2*radius, 2*radius, last_degrees, degrees, ArcType.ROUND);
            last_degrees += degrees;
        }
    }
    
    // Model
    private void DrawFourthLevel(Color startColor){
        int lvl = 4;
        double radius = countRadius(lvl);
       
        int sum = fillElementsThird(); // only get sum, list of elements is not important here
        
        // drawing elements
        double last_degrees = 0;
        for (DataRecord ec : this.data){            
            double degrees = (float)ec.getQuantity() / (float) sum * 90.00;
            
            startColor = startColor.darker();
            graphicContext.setFill(startColor);
            graphicContext.fillArc(-radius, drawingSpace.getHeight() - radius, 2*radius, 2*radius, last_degrees, degrees, ArcType.ROUND);
            last_degrees += degrees;
        }
    }   
    
    private double countRadius(int level){
        double multiplier;
        switch(level){
            case 1:
                multiplier = 0.25;
                break;
            case 2:
                multiplier = 0.50;
                break;
            case 3:
                multiplier = 0.75;
                break;
            case 4:
                multiplier = 1.00;
                break;
            default:
                multiplier = 1.00;
        }
        double radius_float =  multiplier * (drawingSpace.getHeight() < drawingSpace.getWidth() ? drawingSpace.getHeight() : drawingSpace.getWidth());
        System.out.println("Poziom " + level + " - promieñ wyliczony: " + radius_float);
        
        return radius_float;
    }
    
    private int fillElementsSecond(){
        ArrayList<ElementCounter> elements = new ArrayList<>();
        
        // counting aggregated values of elements at this level
        String last = this.data.first().getType();
        int counter = 0;
        int sum = 0;
        for (DataRecord dr : this.data){
            String current = dr.getType();
            if (!current.equals(last)){
                elements.add(new ElementCounter(last, counter));
                counter = dr.getQuantity();
                sum += dr.getQuantity();
                last = current;
            } else {
                counter += dr.getQuantity();
                sum += dr.getQuantity();
            }
        }
        elements.add(new ElementCounter(last, counter));
        
        elementsSecond = elements;
        
        return sum;
    }
    
    private int fillElementsThird(){
        ArrayList<ElementCounter> elements = new ArrayList<>();
        
        // counting aggregated values of elements at this level
        String lastType = this.data.first().getType();
        String lastMark = this.data.first().getMark();
        int counter = 0;
        int sum = 0;
        for (DataRecord dr : this.data){
            String currentType = dr.getType();
            String currentMark = dr.getMark();
            if (!currentMark.equals(lastMark) || !currentType.equals(lastType)){
                elements.add(new ElementCounter(lastMark, lastType, counter));
                counter = dr.getQuantity();
                sum += dr.getQuantity();
                lastType = currentType;
                lastMark = currentMark;
            } else {
                counter += dr.getQuantity();
                sum += dr.getQuantity();
            }
        }
        elements.add(new ElementCounter(lastMark, lastType, counter));
        
        elementsThird = elements;
        
        return sum;
    }
    
}
