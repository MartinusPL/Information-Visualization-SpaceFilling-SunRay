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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marcin
 */
public class DataRecord implements Comparable{
    private StringProperty Concern;
    private StringProperty Type;
    private StringProperty Mark;
    private StringProperty Model;
    private IntegerProperty Quantity;

    public DataRecord(String Concern, String Type, String Mark, String Model, int Quantity) {
        this.Concern = new SimpleStringProperty(Concern);
        this.Type = new SimpleStringProperty(Type);
        this.Mark = new SimpleStringProperty(Mark);
        this.Model = new SimpleStringProperty(Model);
        this.Quantity = new SimpleIntegerProperty(Quantity);
    }
    
    public String getConcern() {
        return Concern.get();
    }
    
    public void setConcern(String Concern) {
        this.Concern.set(Concern);
    }
    
    public String getType() {
        return Type.get();
    }
    
    public void setType(String Type) {
        this.Type.set(Type);
    }

    public String getMark() {
        return Mark.get();
    }

    public void setMark(String Mark) {
        this.Mark.set(Mark);
    }

    public String getModel() {
        return Model.get();
    }

    public void setModel(String Model) {
        this.Model.set(Model);
    }

    public int getQuantity() {
        return Quantity.get();
    }

    public void setQuantity(int Quantity) {
        this.Quantity.set(Quantity);
    }
    
    public StringProperty getConcernProperty(){
        return Concern;
    }
    
    public StringProperty getMarkProperty(){
        return Mark;
    }
    
    public StringProperty getModelProperty(){
        return Model;
    }
    
    public StringProperty getTypeProperty(){
        return Type;
    }
    
    public IntegerProperty getQuantityProperty(){
        return Quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.Concern.get());
        hash = 19 * hash + Objects.hashCode(this.Type.get());
        hash = 19 * hash + Objects.hashCode(this.Mark.get());
        hash = 19 * hash + Objects.hashCode(this.Model.get());
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
        if (!Objects.equals(this.Concern.get(), other.Concern.get())) {
            return false;
        }
        if (!Objects.equals(this.Type.get(), other.Type.get())) {
            return false;
        }
        if (!Objects.equals(this.Mark.get(), other.Mark.get())) {
            return false;
        }
        if (!Objects.equals(this.Model.get(), other.Model.get())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object t) {
        DataRecord tmp = (DataRecord) t;        
        
        if (!tmp.getConcern().equals(Concern.get())){
            return Concern.get().compareTo(tmp.getConcern());
        }else if (!tmp.getType().equals(Type.get())){
            return Type.get().compareTo(tmp.getType());
        }else if (!tmp.getMark().equals(Mark.get())){
            return Mark.get().compareTo(tmp.getMark());
        }else if (!tmp.getModel().equals(Model.get())){
            return Model.get().compareTo(tmp.getModel());
        }else 
            return Quantity.get() - (tmp.getQuantity());
        
    } 
    
    
    
}
