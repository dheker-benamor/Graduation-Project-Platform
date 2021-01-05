package iservices;

import entites.ReportPost;

public interface IReportService {

	public void ajouterReport(ReportPost v, int id);

	public int getNbrReportPerPost(int id);

	public void deletePostIdfromReport(int id);

}
