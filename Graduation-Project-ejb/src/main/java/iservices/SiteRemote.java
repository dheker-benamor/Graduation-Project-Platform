package iservices;

import java.util.List;

import javax.ejb.Remote;

import entites.School;
import entites.Site;
@Remote
public interface SiteRemote {

	public int addsite(Site s);
	public boolean updateSite(Site s);
	public boolean deleteSite(int id);
	public List<Site> ListSite();
	public boolean affectSite(School s,int idE);
	public List<Site> getSitesbySchool(int s);
}
