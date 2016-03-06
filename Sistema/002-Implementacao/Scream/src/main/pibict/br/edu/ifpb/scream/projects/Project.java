package br.edu.ifpb.scream.projects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
*
* @author Mauricio Aguiar
*/
@Entity
@Table(name = "project")
public class Project implements Serializable{
	
	/**
	 * Manusuable atributes
	 * title
	 * start Date 
	 * end Date
	 * timeBoxInDays number
	 * isCompleted
	 */    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100, name = "title")
    private String title;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(nullable = false, length = 255, name = "isCompleted")
    private Boolean isCompleted;
    
    @Column(nullable = false, length = 10, name = "timeBoxInDays")
    private int timeBoxInDays;
    
//    @OneToMany(mappedBy = "projeto")
//    private List<UsuarioProjeto> listUsuarioProjeto;
    
//    @ManyToOne
//    @OneToMany(mappedBy = "projeto")
//    private List<DefinicaoDePronto> definicoesDePronto;
//    
//    @OneToMany(mappedBy = "projeto")
//    private List<ItemProductBacklog> itens;
//    
//    @OneToMany(mappedBy = "projeto")
//    private List<Sprint> listSprint;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Product product;
    
    //Local Equals    
    @Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && id != null)
            ? id.equals(((Project) other).id)
            : (other == this);
    }
    
	@Override
    public int hashCode() {
        return (id != null) 
            ? (getClass().hashCode() + id.hashCode())
            : super.hashCode();
    }
    
    @Override
    public String toString() {
        return "Project[" + "title=" + getTitle() + ", Time in Days=" + getTimeBoxInDays()+"]";
    }    
    
    
    //Getters and Setters 
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTimeBoxInDays() {
		return timeBoxInDays;
	}

	public void setTimeBoxInDays(int timeBoxInDays) {
		this.timeBoxInDays = timeBoxInDays;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getIsCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    

//    public List<UsuarioProjeto> getListUsuarioProjeto() {
//        return listUsuarioProjeto;
//    }
//
//    public void setListUsuarioProjeto(List<UsuarioProjeto> listUsuarioProjeto) {
//        this.listUsuarioProjeto = listUsuarioProjeto;
//    }
//
//    public List<CriterioAceitacao> getCriterios() {
//        return criterios;
//    }
//
//    public void setCriterios(List<CriterioAceitacao> criterios) {
//        this.criterios = criterios;
//    }
//     
}
