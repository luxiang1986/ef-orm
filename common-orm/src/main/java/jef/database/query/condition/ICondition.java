package jef.database.query.condition;

import jef.database.IQueryableEntity;
import jef.database.query.SqlContext;
import jef.database.wrapper.clause.SqlBuilder;

/**
 * 重构后，所有Condition对象接口
 * 
 * @author publicxtgxrj10
 * 
 */
public interface ICondition {
	void toSqlClause(SqlBuilder builder, SqlContext context, IQueryableEntity instance, CondParams params);

}
