package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.dao.interfaces.TypeDAO;
import org.training.issuetracker.services.TypeService;

@Service("typeService") 
@Repository 
@Transactional 
public class SpringTypeService implements TypeService{

	@Autowired
	private TypeDAO typeDAO;
	
	public void setTypeDAO(TypeDAO typeDAO) {
		this.typeDAO = typeDAO;
	}
	
	@Transactional
	@Override
	public List<Type> getTypes() {
		return typeDAO.getTypes();
	}

	@Transactional
	@Override
	public Type getTypeById(int typeId) {
		return typeDAO.getTypeById(typeId);
	}

	@Transactional
	@Override
	public boolean createType(Type type) {
		return typeDAO.createType(type);
	}

	@Transactional
	@Override
	public boolean updateType(Type type) {
		return typeDAO.updateType(type);
	}

	@Transactional
	@Override
	public boolean deleteType(int typeId) {
		return typeDAO.deleteType(typeId);
	}

}
