package iservices;




import java.util.List;

import javax.ejb.Remote;

import entites.Student;
import entites.VoteComment;

@Remote
public interface IVoteService {



	public int ajouterVote(VoteComment v, int id);

	public int getNbrVotePerComment(int id);

	public int verif(Student s, int id);

	public List<VoteComment> getNbrVotePerComment2(int id);

}
