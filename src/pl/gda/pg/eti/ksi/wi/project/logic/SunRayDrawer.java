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
    
    private boolean isCentered;
    private int lvlCounter[];
    
    private ArrayList<ElementCounter> elementsSecond;
    private ArrayList<ElementCounter> elementsThird;
    
    private Color startColor;
    private Color endColor;
    private double color_r_step;
    private double color_g_step;
    private double color_b_step;
    
    
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
    
    public void Draw(boolean isCentered){
        if (this.data.isEmpty()){
            statusLbl.setText("Ta metoda nie jest jeszcze zaimplementowana.");
            return;
        }
        
        //clear canvas
        graphicContext.clearRect(0, 0, drawingSpace.getWidth(), drawingSpace.getHeight());
        
        this.isCentered = isCentered;
        
        AnalyzeData();
        
        this.startColor = Color.rgb(170,255,0); // light green
        this.endColor = Color.rgb(0,80,0); // darg green
        DrawFourthLevel();
        
        this.startColor = Color.rgb(255,255,0); //yellow
        this.endColor = Color.rgb(215,100,0); // darkorange
        DrawThirdLevel();
        
        this.startColor = Color.rgb(255, 0, 0); // red
        this.endColor = Color.rgb(92, 0, 0); // dark brown
        DrawSecondLevel();
        
        this.startColor = Color.BLACK;
        this.endColor = Color.BLACK;
        DrawFirstLevel();
    }
    
    // Concern
    private void DrawFirstLevel(){
        double radius = countRadius(1);
        Color startColor = this.startColor;
        graphicContext.setFill(startColor);
        
        calculateColorPartsSteps(1);
        
        double posx, posy, degrees;
        if (isCentered){
            posx = drawingSpace.getWidth() / 2 - radius;
            posy = drawingSpace.getHeight()/ 2 - radius;
            degrees = 360;
        } else {
            posx = -radius;
            posy = drawingSpace.getHeight() - radius;
            degrees = 90;
        }
        
        graphicContext.fillArc(posx, posy, 2*radius, 2*radius, 0, degrees, ArcType.ROUND);
    }
    
    // Type
    private void DrawSecondLevel(){
        int lvl = 2;
        double radius = countRadius(lvl);
        
        int sum = fillElementsSecond();
        calculateColorPartsSteps(elementsSecond.size());
        
        
        
        // drawing elements
        double last_degrees = 0;
        for (ElementCounter ec : elementsSecond){
            double degrees = (float)ec.quantity / (float)sum * 90.00;
            
            double posx, posy;
            if (isCentered){
                posx = drawingSpace.getWidth() / 2 - radius;
                posy = drawingSpace.getHeight()/ 2 - radius;
                degrees = degrees * 4;
            } else {
                posx = -radius;
                posy = drawingSpace.getHeight() - radius;
            }
            
            startColor = generateNextColor(startColor);
            graphicContext.setFill(startColor);
            //-prev_radius, drawingSpace.getHeight() - radius + prev_radius
            graphicContext.fillArc(posx, posy, 2*radius, 2*radius, last_degrees, degrees, ArcType.ROUND);
            last_degrees += degrees;
        }
    }

    // Mark
    private void DrawThirdLevel(){
        int lvl = 3;
        double radius = countRadius(lvl);
        
        int sum = fillElementsThird();
        
        calculateColorPartsSteps(elementsThird.size());
        
        // drawing elements
        double last_degrees = 0;
        for (ElementCounter ec : elementsThird){            
            double degrees = (float)ec.quantity / (float) sum * 90.00;
            
            double posx, posy;
            if (isCentered){
                posx = drawingSpace.getWidth() / 2 - radius;
                posy = drawingSpace.getHeight()/ 2 - radius;
                degrees = degrees * 4;
            } else {
                posx = -radius;
                posy = drawingSpace.getHeight() - radius;
            }
            
            startColor = generateNextColor(startColor);
            graphicContext.setFill(startColor);
            graphicContext.fillArc(posx, posy, 2*radius, 2*radius, last_degrees, degrees, ArcType.ROUND);
            last_degrees += degrees;
        }
    }
    
    // Model
    private void DrawFourthLevel(){
        int lvl = 4;
        double radius = countRadius(lvl);
       
        int sum = fillElementsThird(); // only get sum, list of elements is not important here

        calculateColorPartsSteps(this.data.size());
        
        
        
        // drawing elements
        double last_degrees = 0;
        for (DataRecord ec : this.data){            
            double degrees = (float)ec.getQuantity() / (float) sum * 90.00;
            
            double posx, posy;
            if (isCentered){
                posx = drawingSpace.getWidth() / 2 - radius;
                posy = drawingSpace.getHeight()/ 2 - radius;
                degrees = degrees * 4;
            } else {
                posx = -radius;
                posy = drawingSpace.getHeight() - radius;
            }
            
            startColor = generateNextColor(startColor);
            graphicContext.setFill(startColor);
            graphicContext.fillArc(posx, posy, 2*radius, 2*radius, last_degrees, degrees, ArcType.ROUND);
            last_degrees += degrees;
        }
    }   
    
    private double countRadius(int level){
        double multiplier;
        if (!isCentered){
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
        } else {
            switch(level){
                case 1:
                    multiplier = 0.12;
                    break;
                case 2:
                    multiplier = 0.24;
                    break;
                case 3:
                    multiplier = 0.36;
                    break;
                case 4:
                    multiplier = 0.48;
                    break;
                default:
                    multiplier = 0.48;
            }
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
    
    private Color generateNextColor(Color start){
        
        double r, g, b, bright, sat, opacity;
        r = start.getRed();
        g = start.getGreen();
        b = start.getBlue();
        bright = start.getBrightness();
        sat = start.getSaturation();
        opacity = start.getOpacity();
        
       
        r = applyColorPartStep(r, this.color_r_step);
        g = applyColorPartStep(g, this.color_g_step);
        b = applyColorPartStep(b, this.color_b_step);
        
        Color ret = new Color(r, g, b, start.getOpacity());
        
        return ret;
        
    }
    
    private double applyColorPartStep(double part, double step){
        
        if (part + step < 0.00){
            return part;
        }else if (part + step > 1.00){
            return part;
        } else {
            return part + step;
        }
        
    }
    
    private void calculateColorPartsSteps(int numberOfSteps){
        
        double start_r, start_g, start_b;
        start_r = this.startColor.getRed();
        start_g = this.startColor.getGreen();
        start_b = this.startColor.getBlue();
        
        double end_r, end_g, end_b;
        end_r = this.endColor.getRed();
        end_g = this.endColor.getGreen();
        end_b = this.endColor.getBlue();
        
        double numberOfStepsFloat = (double) numberOfSteps;
        this.color_r_step = (end_r - start_r)/numberOfStepsFloat;
        this.color_g_step = (end_g - start_g)/numberOfStepsFloat;
        this.color_b_step = (end_b - start_b)/numberOfStepsFloat;
    }
}
