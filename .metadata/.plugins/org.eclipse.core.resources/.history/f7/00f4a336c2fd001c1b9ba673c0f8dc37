package com.technoelevate.order_service.ulitily;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class OrderIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		Random rand = new Random();
		char c1 = (char) (rand.nextInt(26) + 'A');
		char c2 = (char) (rand.nextInt(26) + 'A');
		char c3 = (char) (rand.nextInt(26) + 'A');
		char c4 = (char) (rand.nextInt(26) + 'A');
		char c5 = (char) (rand.nextInt(26) + 'A');
		char c6 = (char) (rand.nextInt(26) + 'A');

		return c1 + "" + c2 + "" + c3+""+ c4 + "" + c5 + "" + c6;

	}
}
