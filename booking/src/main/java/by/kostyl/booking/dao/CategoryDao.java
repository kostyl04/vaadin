package by.kostyl.booking.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository(value="categorydao")
@Transactional
public class CategoryDao extends CrudDaoBean{
	
}
