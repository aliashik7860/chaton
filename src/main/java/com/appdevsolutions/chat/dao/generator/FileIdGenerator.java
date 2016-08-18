package com.appdevsolutions.chat.dao.generator;

import java.io.Serializable;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class FileIdGenerator extends AbstractIdGenerator implements IdentifierGenerator{

	private static final String PRIMARY_KEY_CONST="fileId";
	private static final String PREFIX="FILE";
	
	@Override
	public Serializable generate(SessionImplementor sessionImplementor, Object arg1) throws HibernateException {
		try{
			return generateId(sessionImplementor, PRIMARY_KEY_CONST, PREFIX);
		}catch(SQLException sqlException){
			throw new HibernateException("Could not generate videoId");
		}
	}
}