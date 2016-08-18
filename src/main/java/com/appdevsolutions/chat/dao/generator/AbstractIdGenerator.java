package com.appdevsolutions.chat.dao.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIdGenerator {
	private static final String SELECT_QUERY="select * from primary_keys_info where key_name=?";
	private static final String INSERT_QUERY="insert into primary_keys_info values(?,?,?)";
	private static final String UPDATE_QUERY="update primary_keys_info set key_count=? where key_name=?";
	private static final Logger LOGGER=LoggerFactory.getLogger(AbstractIdGenerator.class);
	
	public String generateId(SessionImplementor sessionImplementor,String primaryKeyConstant, String primaryKeyIdPrefix)throws SQLException{
		final Connection connection=sessionImplementor.connection();
		String code;
			final PreparedStatement selectStatement=connection.prepareStatement(SELECT_QUERY) ;
			selectStatement.setString(1, primaryKeyConstant);
			final ResultSet rs = selectStatement.executeQuery();
			int count=0;
			if (!rs.next()) {
				final PreparedStatement insertStatement=connection.prepareStatement(INSERT_QUERY) ;
				count=1;
				code = primaryKeyIdPrefix + StringUtils.leftPad("" + count,4, '0');
				LOGGER.info("newly generating email_template id : "+code);
				insertStatement.setInt(1, 5);
				insertStatement.setString(3, primaryKeyConstant);
				insertStatement.setInt(2, count+1);
				final int status=insertStatement.executeUpdate();
				LOGGER.info("insert into roleId status : "+status);
			}else{
				count = rs.getInt("key_count");
				rs.close();
				code = primaryKeyIdPrefix + StringUtils.leftPad("" + count,4, '0');
				LOGGER.info("newly generating email_template id : "+code);
				final PreparedStatement updateStatement=connection.prepareStatement(UPDATE_QUERY);
				updateStatement.setInt(1, count+1);
				updateStatement.setString(2, primaryKeyConstant);
				final int status=updateStatement.executeUpdate();
				LOGGER.info("update into roleId status : "+status);
			}
		return code;
	}
}
