package jef.database.wrapper.clause;

import jef.database.wrapper.variable.Variable;

public interface SqlClauseBuilder {

	void addBind(Variable constantVariable);

	SqlClauseBuilder append(String string);

}
