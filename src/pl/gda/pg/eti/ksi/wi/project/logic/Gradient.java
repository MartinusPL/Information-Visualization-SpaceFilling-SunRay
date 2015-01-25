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

import javafx.scene.paint.Color;

/**
 *
 * @author Marcin Grzesiak <kontakt@mgrzesiak.eu>
 */
public class Gradient {
    Color startColor;
    Color endColor;
    private double color_b_step;
    private double color_g_step;
    private double color_r_step;

    public Gradient() {
    }
    
    public Gradient(Color startColor, Color endColor, int numberOfSteps) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.calculateColorPartsSteps(numberOfSteps);
    }
    
    void calculateColorPartsSteps(int numberOfSteps) {
        double start_r;
        double start_g;
        double start_b;
        start_r = this.startColor.getRed();
        start_g = this.startColor.getGreen();
        start_b = this.startColor.getBlue();
        double end_r;
        double end_g;
        double end_b;
        end_r = this.endColor.getRed();
        end_g = this.endColor.getGreen();
        end_b = this.endColor.getBlue();
        double numberOfStepsFloat = (double) numberOfSteps;
        this.color_r_step = (end_r - start_r) / numberOfStepsFloat;
        this.color_g_step = (end_g - start_g) / numberOfStepsFloat;
        this.color_b_step = (end_b - start_b) / numberOfStepsFloat;
    }

    private double applyColorPartStep(double part, double step) {
        if (part + step < 0.0) {
            return part;
        } else if (part + step > 1.0) {
            return part;
        } else {
            return part + step;
        }
    }

    Color generateNextColor(Color start) {
        double r;
        double g;
        double b;
        double bright;
        double sat;
        double opacity;
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
    
}
