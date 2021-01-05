package iservices;

import java.util.List;

import javax.ejb.Remote;


import entites.TaskAttachment;


@Remote
public interface TaskAttachmentServiceRemote {

	void AddAttachment(String name, int id);



	TaskAttachment findAttachmentById(int id);

	List<TaskAttachment> findAllAttachments(int id);



	void DeleteAttachment(int id);

}
