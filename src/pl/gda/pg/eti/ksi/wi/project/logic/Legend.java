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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.gda.pg.eti.ksi.wi.project.models.LegendObject;

/**
 *
 * @author Marcin Grzesiak <kontakt@mgrzesiak.eu>
 */
public class Legend {
    
    // properties
    private final ArrayList<LegendObject> data;
    private Canvas canvas;
    
    // calculated values
    private GraphicsContext graphicsContext;
    private double legendWidth;
    private double legendHeight;
    
    // legend config
    private static final double legendIconWidth = 12;
    private static final double legendIconHeight = 12;
    private static final double legendIconBorder = 1;
    private static final Color legendIconBorderColor = Color.BLACK;
    private static final Color legendIconTextColor = Color.BLACK;
    private static final double legendRowHeight = 20;
    
    public Legend(){
        data = new ArrayList<>();
    }
    
    public void Add(String label, Color color, int level){
        data.add(new LegendObject(label, color, level));
    }
    
    public void Reset(){
        data.clear();
    }
    
    public void Draw(Canvas canvas, int level){
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        
        legendWidth = canvas.getWidth() / 2 / SunRayDrawer.numberOfLevels;
        legendHeight = canvas.getHeight();
        
        System.out.println("Legend size is " + legendWidth + " width and"
                + legendHeight + " height");
        
        
        double startX = canvas.getWidth() - (legendWidth + 10) * (SunRayDrawer.numberOfLevels - level + 1);
        double startY = 5;
        
        for(LegendObject lo : data){
            
            graphicsContext.setFill(legendIconBorderColor);
            graphicsContext.fillRect(startX - legendIconBorder, startY - legendIconBorder, 
                    legendIconWidth + 2*legendIconBorder, legendIconHeight + 2*legendIconBorder);
            
            graphicsContext.setFill(lo.getColor());
            graphicsContext.fillRect(startX, startY, legendIconWidth, legendIconHeight);
            
            graphicsContext.setFill(legendIconTextColor);
            double textX = startX + 2*legendIconBorder + legendIconWidth + 10;
            double textMaxWidth = legendWidth - textX + startX;
            graphicsContext.fillText(lo.getLabel(), textX , startY + legendIconHeight, textMaxWidth);
            
            startY += legendRowHeight;
        }
    }
    
}
