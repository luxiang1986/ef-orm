package jef.database.query;

import jef.database.QueryAlias;
import jef.database.annotation.JoinType;
import jef.database.meta.JoinKey;
import jef.database.meta.JoinPath;
import jef.database.query.condition.CondParams;
import jef.database.query.condition.ConditionUtils;
import jef.database.wrapper.clause.SqlBuilder;

/**
 * 一个Join的补充信息，描述这个Join实例与其左侧的各个表之前的关系
 * 
 * @author Administrator
 * 
 */
public class JoinCondition {
	private Query<?> left;
	private JoinPath value;
	private JoinType type;

	public JoinCondition(Query<?> query, JoinPath joinPaths) {
		this.left = query;
		this.value = joinPaths;
		type = joinPaths.getType();
	}

	public JoinType getType() {
		return type;
	}

	public void setType(JoinType type) {
		if (type != null) {
			this.type = type;
		}
	}

	public void toOnExpression(SqlBuilder sb, SqlContext rightContext, QueryAlias right, CondParams params) {
		rightContext = rightContext.getContextOf(right.getQuery());
		int onCount = 0;
		for (JoinKey key : value.getJoinKeys()) {
			if (onCount > 0) {
				sb.startSection(" and ");
				parseJoinkey(sb, key, rightContext, params);
				sb.endSection();
			} else {
				parseJoinkey(sb, key, rightContext, params);
			}
			onCount++;
		}
		for (JoinKey key : value.getJoinExpression()) {
			if (onCount > 0) {
				sb.startSection(" and ");
			}
			if (key.getField() == null) {
				if (key.getValue() instanceof JpqlExpression) {
					JpqlExpression jpql = (JpqlExpression) key.getValue();
					jpql.toSqlAndBindAttribs2(sb, rightContext, params.getDialect());
				}
			} else {
				key.toPrepareSqlClause(sb, rightContext, rightContext.getCurrent().getQuery().getInstance(), params);
			}
			if (onCount > 0) {
				sb.endSection();
			}
			onCount++;
		}
	}

	private void parseJoinkey(SqlBuilder builder, JoinKey key, SqlContext rightContext, CondParams params) {
		SqlContext leftContext;
		if (key.getLeftLock() != null) {// 重新校验和锁定
			leftContext = rightContext.getContextOf(key.getLeftLock());
		} else {
			leftContext = rightContext.getContextOf(left);
		}
		if (key.getRightLock() != null) {// 重新校验和锁定
			rightContext = rightContext.getContextOf(key.getRightLock());
		}
		ConditionUtils.parseField(builder, key.getLeft(), leftContext, null, params);
		builder.append('=');
		ConditionUtils.parseField(builder, key.getRightAsField(), rightContext, null, params);
	}
}
