package com.appdevsolutions.chat.dao.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RoleIdGenerator implements IdentifierGenerator{
	
	private static final String PREFIX="ROL";
	private static final String SELECT_QUERY="select * from primary_keys_info where key_name=?";
	private static final String INSERT_QUERY="insert into primary_keys_info values(?,?,?)";
	private static final String UPDATE_QUERY="update primary_keys_info set key_count=? where key_name=?";
	//private static final String SELECT_QUERY1="select count(*) from primary_keys_info";
	private static final Logger LOGGER=LoggerFactory.getLogger(RoleIdGenerator.class);
	
	@Override
	public Serializable generate(SessionImplementor sessionImplementor, Object object) throws HibernateException {
		final Connection connection=sessionImplementor.connection();
		String code;
		try {
		final PreparedStatement selectStatement=connection.prepareStatement(SELECT_QUERY) ;
		selectStatement.setString(1, "roleId");
		final ResultSet rs = selectStatement.executeQuery();
		int count=0;
        if (!rs.next()) {
        	final PreparedStatement insertStatement=connection.prepareStatement(INSERT_QUERY) ;
        	count=1;
        	code = PREFIX + StringUtils.leftPad("" + count,4, '0');
            LOGGER.info("newly generating privilege id : "+code);
			insertStatement.setInt(1, 0);
			insertStatement.setString(3, "roleId");
			insertStatement.setInt(2, count+1);
			final int status=insertStatement.executeUpdate();
			LOGGER.info("insert into roleId status : "+status);
        }else{
        	count = rs.getInt("key_count");
        	rs.close();
        	code = PREFIX + StringUtils.leftPad("" + count,4, '0');
            LOGGER.info("newly generating privilege id : "+code);
        	final PreparedStatement updateStatement=connection.prepareStatement(UPDATE_QUERY);
        	updateStatement.setInt(1, count+1);
        	updateStatement.setString(2, "roleId");
        	final int status=updateStatement.executeUpdate();
        	LOGGER.info("update into roleId status : "+status);
        }
        
		} catch (SQLException sqlException) {
			LOGGER.error("could not be generated role id ",sqlException);
			throw new HibernateException("could not generate role id");
		}
		return code;
	}
}