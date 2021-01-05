package iservices;



import java.util.List;

import javax.ejb.Remote;

import entites.CommentPost;



@Remote
public interface ICommentPostServices {


	public int ajouterComment(CommentPost cp, int id);

	public List<CommentPost> getAllCommentsByPost(int postId);

	public List<String> getAll(int postId);

	public void signalerPost(int postId);

	public List<CommentPost> getAllComments();

	public List<String> Notifs(int id);

	public List<entites.VoteComment> getAllVotePerComment(int postId);

}
