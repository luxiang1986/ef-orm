package jef.database.dialect.handler;

import jef.database.jdbc.statement.UnionJudgement;
import jef.database.jdbc.statement.UnionJudgementDruidMySQLImpl;
import jef.database.wrapper.clause.BindSql;
import jef.tools.StringUtils;

public class MySqlLimitHandler implements LimitHandler {
	private final static String MYSQL_PAGE = " limit %start%,%next%";
	private UnionJudgement unionJudge;

	public MySqlLimitHandler() {
		if(UnionJudgement.isDruid()){
			unionJudge=new UnionJudgementDruidMySQLImpl();
		}else{
			unionJudge=UnionJudgement.DEFAULT;
		}
	}

	public BindSql toPageSQL(BindSql sql, int[] range) {
		return toPageSQL(sql, range,unionJudge.isUnion(sql.getSql()));
	}

	@Override
	public BindSql toPageSQL(BindSql sql, int[] range, boolean isUnion) {
		String[] s = new String[] { Integer.toString(range[0]), Integer.toString(range[1]) };
		String limit = StringUtils.replaceEach(MYSQL_PAGE, new String[] { "%start%", "%next%" }, s);
		String oldSql=sql.getSql();
		return new BindSql(isUnion ? StringUtils.concat("select * from (", oldSql, ") tb__", limit) : oldSql.concat(limit), sql.getBind());
	}
}
