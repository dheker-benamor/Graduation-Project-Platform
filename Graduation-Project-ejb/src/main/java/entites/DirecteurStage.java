package entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Directeur")
public class DirecteurStage extends User implements Serializable{
	private static final long serialVersionUID = 1L;


}
