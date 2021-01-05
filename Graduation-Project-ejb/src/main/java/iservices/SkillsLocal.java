package iservices;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.json.JsonObject;

import entites.Skills;
import entites.Student;
import entites.user_skills;
@Local
public interface SkillsLocal {

	public boolean affectStudentskills(Student s,user_skills x);
	public boolean updateskillsstudent(Student s,user_skills x);
	public List<Object> top3student(int id);
	public List<Skills> getall();
	public List<user_skills> userskills();
	
}
