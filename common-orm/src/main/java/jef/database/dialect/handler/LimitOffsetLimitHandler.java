package jef.database.dialect.handler;

import jef.database.jdbc.statement.UnionJudgement;
import jef.database.jdbc.statement.UnionJudgementDruidPGImpl;
import jef.database.wrapper.clause.BindSql;
import jef.tools.StringUtils;

public class LimitOffsetLimitHandler implements LimitHandler {
	private UnionJudgement unionJudge;

	public LimitOffsetLimitHandler() {
		if (UnionJudgement.isDruid()) {
			unionJudge = new UnionJudgementDruidPGImpl();
		} else {
			unionJudge = UnionJudgement.DEFAULT;
		}
	}

	public BindSql toPageSQL(BindSql sql, int[] range) {
		return toPageSQL(sql, range, unionJudge.isUnion(sql.getSql()));

	}

	public BindSql toPageSQL(BindSql sql, int[] range, boolean isUnion) {
		String limit;
		if (range[0] == 0) {
			limit = " limit " + range[1];
		} else {
			limit = " limit " + range[1] + " offset " + range[0];
		}
		String s = sql.getSql();
		return new BindSql(isUnion ? StringUtils.concat("select * from (", s, ") tb__", limit) : s.concat(limit), sql.getBind());
	}
}
