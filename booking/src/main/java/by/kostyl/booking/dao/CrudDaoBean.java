package by.kostyl.booking.dao;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Hibernate crud dao interface implementation
 * 
 * @author harchevnikov_m Date: 18.09.11 Time: 21:20
 */
@Repository
@Transactional
public class CrudDaoBean implements CrudDao {
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Default constructor
	 */
	public CrudDaoBean() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T merge(T t) {
		
			return (T) currentSession().merge(t);
		

	}

	public <T, PK extends Serializable> T find(Class<T> type, PK id)
			{
			return (T) currentSession().get(type, id);
		
	}

	public <T, PK extends Serializable> void delete(Class<T> type, PK id)
			{
		
			Session currentSession = currentSession();
			Object object = currentSession.get(type, id);
			currentSession.delete(object);
		

	}

	
	public <T> List<T> list(Class<T> type) {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		Root<T> root = criteria.from(type);
        criteria.select(root);
			return currentSession().createQuery(criteria).getResultList();	
		
	}

	
	

	protected Session currentSession() {
		Session currentSession = sessionFactory.getCurrentSession();		
		//Session currentSession = sessionFactory.openSession();
		return currentSession;
	}
}