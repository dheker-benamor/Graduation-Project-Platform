package iservices;

import java.util.List;

import javax.ejb.Remote;

import entites.Activity;
import entites.TaskComment;

@Remote
public interface ActivityServiceRemote {

	void AjouterActivity(Activity activity);



	Activity findActivityById(int id);

	List<Activity> findAllActivities(int id);

}
