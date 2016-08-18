package com.appdevsolutions.chat.dao.generator;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioIdGenerator implements IdentifierGenerator{
	
	private static final String PREFIX="INADO";
	private static final String IN_AUDIO_FIND_BY_COUNT="InAudio.findByCount";
	private static final Logger LOGGER=LoggerFactory.getLogger(AudioIdGenerator.class);
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		final Query query=session.getNamedQuery(IN_AUDIO_FIND_BY_COUNT);
		final Long count=((Long)query.uniqueResult())+1;
		LOGGER.info("InAudio id count is : "+count);
		final String code = PREFIX + StringUtils.leftPad("" + count,4, '0');
		LOGGER.info("Generated Primary key for InAudio is : "+code);
        return code;
	}
}
