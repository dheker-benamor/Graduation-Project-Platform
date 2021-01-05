package iservices;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import entites.TaskStatus;
import entites.Task;

@Remote
public interface TaskServiceRemote {

	void AjouterTask(Task task);

	boolean UpdateTask(Task task);

	void DeleteTask(int id);

	List<Task> findAllTask();

	Task findTaskById(int id);

	void UpdateStatus(Task task);

	Set<Task> findTaskByProject(int id);

	long getapprovedstats(int ap,int project);

	long getstats(TaskStatus st, int project);

	String UpdateApproved(Task task);

	long dateStats(String op,int project);

}
