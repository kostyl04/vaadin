package by.kostyl.booking.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import by.kostyl.booking.entity.Hotel;

@Repository(value="hoteldao")
@Transactional
public class HotelDao extends CrudDaoBean{
	public List<Hotel> filter(String name,String address){
		Query query = currentSession()
				.createQuery("select h from Hotel h where lower(h.name) like lower('%"+name+"%') and lower(h.address) like lower('%"+address+"%')");
		@SuppressWarnings("unchecked")
		List <Hotel> result=query.getResultList();
		return result;
		
	}
}
