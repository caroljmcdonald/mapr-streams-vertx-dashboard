/*
 * Copyright 2017 cmcdonald.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapr.demo.producer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
/**
 *
 * @author cmcdonald
 */
public class Data {
    @JsonProperty public String xValue;
	@JsonProperty public Double[] columnA;
	@JsonProperty public Double[] columnB;
	
	
	@Override
	public String toString() {
		
	       return MoreObjects.toStringHelper(this)
	    		   	.add("xValue", xValue)
	                .add("columnA", columnA)
	                .add("columnB", columnB)
	                .toString();
	}

	public Data(){
		
	}
	
	public Data(String xValue,Double[] columnA,Double[] columnB){
		this.xValue = xValue;
		this.columnA = columnA;
		this.columnB = columnB;
	}

	public String getxValue() {
		return xValue;
	}

	public void setxValue(String xValue) {
		this.xValue = xValue;
	}

	public Double[] getColumnA() {
		return columnA;
	}

	public void setColumnA(Double[] columnA) {
		this.columnA = columnA;
	}

	public Double[] getColumnB() {
		return columnB;
	}

	public void setColumnB(Double[] columnB) {
		this.columnB = columnB;
	}
	
}
