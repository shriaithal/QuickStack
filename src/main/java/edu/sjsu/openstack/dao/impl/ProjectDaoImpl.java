package edu.sjsu.openstack.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.sjsu.openstack.dao.ProjectDao;
import edu.sjsu.openstack.model.Project;

@Repository
public class ProjectDaoImpl implements ProjectDao {

	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public void insertRecord(Project createProject) {
		mongoOperations.save(createProject);
	}

	@Override
	public Project getProjectByName(String projectName) {
		Criteria criteria = Criteria.where("projectName").is(projectName);
		Query query = new Query(criteria);
		Project project = mongoOperations.findOne(query, Project.class);
		return project;
	}

	@Override
	public List<Project> getProjects() {
		return mongoOperations.findAll(Project.class);
	}

	@Override
	public Project getProjectById(String projectId) {
		Criteria criteria = Criteria.where("_id").is(new ObjectId(projectId));
		Query query = new Query(criteria);
		Project project = mongoOperations.findOne(query, Project.class);
		return project;
	}
}
