package jef.database.dialect.handler;

import jef.database.wrapper.clause.BindSql;

public class OracleLimitHander implements LimitHandler {

	private final static String ORACLE_PAGE1 = "select * from (select tb__.*, rownum rid__ from (";
	private final static String ORACLE_PAGE2 = " ) tb__ where rownum <= %end%) where rid__ > %start%";

	public BindSql toPageSQL(BindSql bind, int[] range) {
		if(range[0]==0){
			return new BindSql("select tb__.* from (\n"+bind.getSql()+") tb__ where rownum <= "+range[1],bind.getBind());
		}else{
			String sql = ORACLE_PAGE1 + bind.getSql();
			String limit = ORACLE_PAGE2.replace("%start%", String.valueOf(range[0]));
			limit = limit.replace("%end%", String.valueOf(range[0]+range[1]));
			sql = sql.concat(limit);
			return new BindSql(sql,bind.getBind());	
		}
		
	}


	@Override
	public BindSql toPageSQL(BindSql sql, int[] offsetLimit, boolean isUnion) {
		return toPageSQL(sql, offsetLimit);
	}

}
