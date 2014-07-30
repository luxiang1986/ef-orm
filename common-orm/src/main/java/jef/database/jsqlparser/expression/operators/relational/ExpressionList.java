/*
 * JEF - Copyright 2009-2010 Jiyi (mr.jiyi@gmail.com)
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
package jef.database.jsqlparser.expression.operators.relational;

import java.util.Arrays;
import java.util.List;

import jef.database.jsqlparser.expression.Child;
import jef.database.jsqlparser.expression.Expression;
import jef.database.jsqlparser.expression.Function;
import jef.database.jsqlparser.statement.select.PlainSelect;

/**
 * A list of expressions, as in SELECT A FROM TAB WHERE B IN (expr1,expr2,expr3)
 */
public class ExpressionList implements ItemsList {

	private Function parent;
    private List<Expression> expressions;
    private String between=",";
    
    public Function getParent() {
		return parent;
	}

	public ExpressionList setParent(Function parent) {
		this.parent = parent;
		return this;
	}

	public ExpressionList() {
    }

    public String getBetween() {
		return between;
	}

	public ExpressionList setBetween(String between) {
		this.between = between;
		return this;
	}

	public int size(){
    	return expressions.size();
    }
    
    public boolean isEmpty(){
    	return expressions==null?true:expressions.isEmpty();
    }
    
    public ExpressionList(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public ExpressionList(Expression... expression) {
        this.expressions = Arrays.asList(expression);
	}

	public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expression> list) {
        expressions = list;
        if(list!=null){
        	for(Expression e:list){
            	if(e instanceof Child){
            		((Child) e).setParent(this);
            	}
            }	
        }
    }

    public void accept(ItemsListVisitor itemsListVisitor) {
        itemsListVisitor.visit(this);
    }

    public String toString() {
    	StringBuilder sb=new StringBuilder();
        PlainSelect.getStringList(sb,expressions, between, true);
        return sb.toString();
    }

	public void appendTo(StringBuilder sb) {
		PlainSelect.getStringList(sb,expressions, between, true);
	}
}