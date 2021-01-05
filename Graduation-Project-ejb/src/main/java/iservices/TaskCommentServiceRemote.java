package iservices;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;


import entites.TaskComment;

@Remote
public interface TaskCommentServiceRemote {

	void AjouterTaskComment(TaskComment taskComment);

	void UpdateTaskComment(TaskComment taskComment);

	void DeleteTaskComment(int id);

	TaskComment findTaskCommentById(int id);

	Set<TaskComment> findAllTaskComments(int id);

	List<TaskComment> findCommentsByTask(int id);

}
