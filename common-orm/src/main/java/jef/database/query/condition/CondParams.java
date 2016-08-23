package jef.database.query.condition;

import jef.database.SqlProcessor;
import jef.database.dialect.DatabaseDialect;

public final class CondParams {
	private boolean batch;
	private DatabaseDialect dialect;
	private SqlProcessor processor;
	
	public CondParams(SqlProcessor processor,DatabaseDialect dialect,boolean batch){
		this.processor=processor;
		this.dialect=dialect;
		this.batch=batch;
	}
	
	public boolean isBatch() {
		return batch;
	}
	public DatabaseDialect getDialect() {
		return dialect;
	}
	public SqlProcessor getProcessor() {
		return processor;
	}
}
