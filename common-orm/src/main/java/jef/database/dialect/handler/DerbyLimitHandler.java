package jef.database.dialect.handler;

import jef.database.wrapper.clause.BindSql;

public class DerbyLimitHandler implements LimitHandler {
	public BindSql toPageSQL(BindSql sql, int[] range) {
		String limit; 
		if(range[0]==0){
			limit=" fetch next "+range[1]+" rows only";
		}else{
			limit=" offset "+range[0]+" row fetch next "+range[1]+" rows only";
		}
		String newsql = sql.getSql().concat(limit);
		return new BindSql(newsql,sql.getBind());
	}

	public BindSql toPageSQL(BindSql sql, int[] range, boolean isUnion) {
		return toPageSQL(sql, range);
	}
}
