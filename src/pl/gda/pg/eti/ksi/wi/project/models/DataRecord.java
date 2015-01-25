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

package pl.gda.pg.eti.ksi.wi.project.models;

import java.util.Objects;

/**
 *
 * @author Marcin
 */
public class DataRecord implements Comparable{
    private String Concern;
    private String Type;
    private String Mark;
    private String Model;
    private int Quantity;

    public DataRecord(String Concern, String Type, String Mark, String Model, int Quantity) {
        this.Concern = Concern;
        this.Type = Type;
        this.Mark = Mark;
        this.Model = Model;
        this.Quantity = Quantity;
    }
    
    public String getConcern() {
        return Concern;
    }

    public void setConcern(String Concern) {
        this.Concern = Concern;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getMark() {
        return Mark;
    }

    public void setMark(String Mark) {
        this.Mark = Mark;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.Concern);
        hash = 19 * hash + Objects.hashCode(this.Type);
        hash = 19 * hash + Objects.hashCode(this.Mark);
        hash = 19 * hash + Objects.hashCode(this.Model);
        //hash = 19 * hash + this.Quantity;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataRecord other = (DataRecord) obj;
        if (!Objects.equals(this.Concern, other.Concern)) {
            return false;
        }
        if (!Objects.equals(this.Type, other.Type)) {
            return false;
        }
        if (!Objects.equals(this.Mark, other.Mark)) {
            return false;
        }
        if (!Objects.equals(this.Model, other.Model)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object t) {
        DataRecord tmp = (DataRecord) t;        
        
        if (!tmp.getConcern().equals(Concern)){
            return Concern.compareTo(tmp.getConcern());
        }else if (!tmp.getType().equals(Type)){
            return Type.compareTo(tmp.getType());
        }else if (!tmp.getMark().equals(Mark)){
            return Mark.compareTo(tmp.getMark());
        }else if (!tmp.getModel().equals(Model)){
            return Model.compareTo(tmp.getModel());
        }else 
            return Quantity - (tmp.getQuantity());
        
    }
 
    
    
}
