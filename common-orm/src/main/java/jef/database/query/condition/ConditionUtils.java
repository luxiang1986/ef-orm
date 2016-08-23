package jef.database.query.condition;

import jef.database.Condition;
import jef.database.Condition.Operator;
import jef.database.Field;
import jef.database.IQueryableEntity;
import jef.database.query.SqlContext;
import jef.database.wrapper.clause.SqlBuilder;
import jef.database.wrapper.variable.ConstantVariable;
import jef.database.wrapper.variable.QueryLookupVariable;

public class ConditionUtils {
	public static void parseValue(SqlBuilder builder, Field rawField, Operator operator, Object value, CondParams params) {
		if (params.isBatch()) {
			builder.addBind(new QueryLookupVariable(rawField, operator));
		} else {
			builder.addBind(new ConstantVariable(rawField.name() + operator, value));
		}
	}
	

	public static void parseField(SqlBuilder builder, Field field, SqlContext context, IQueryableEntity instance, CondParams params) {
		// TODO simple
		Condition c = Condition.get(field, null, null);
		c.toPrepareSqlClause(builder, context, instance, params);
	}

}
