package br.edu.ifpb.scream.projects;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;

import br.ifpb.monteiro.scream.util.jsf.JsfUtil;

/**
 *
 * @author Mauricio Aguiar
 */
@Named(value = "dashboardController")
@Model
public class DashboardController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProductService productService;

	@Inject
	private ProjectService projectService;

//	@Inject
//	private SecurityService ss;

	//Atributtes

	private Product product;

	private Boolean productValitator;

	private Product productSelect;

	private List<Product> listProduct;

	private Project project;

	private Project projectSelect;

	private List<Project> listProject;

	FacesContext contexto = FacesContext.getCurrentInstance();

	/**
	 * Creates a new instance of DashboardController
	 */
	@PostConstruct
	public void init() {
		product = new Product();
		project = new Project();

		updateListProduct();
		updateListProject();

		getProductSelectedContext();
		getProjectSelectedContext();
	}


	public void getProjectSelectedContext() {
			projectSelect = (Project) contexto.getExternalContext().getSessionMap().get("projectSelected");
	}

	public void getProductSelectedContext() {
			productSelect = (Product) contexto.getExternalContext().getSessionMap().get("productSelected");
	}

	private Calendar getCurrentTime() {
		Calendar calendar = GregorianCalendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.format(calendar.getTime());
		calendar = dateFormat.getCalendar();

		return calendar;
	}

	//Begin Product operations

	public void updateListProduct() {
		listProduct = productService.findAll();
	}

	public void createProduct() {
		//if (ss.isAuthorized("SCRUM_MASTER")) {
		if(productValidator(product)){
			//			registrarData();
			productService.create(product);
			this.product = new Product();
			JsfUtil.addSuccessMessage("Product Criado com Sucesso");
			redirect(); 
		}
	}

	public void deleteProduct() {
		//        if (ss.isAuthorized("SCRUM_MASTER")) {
		//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		JsfUtil.addSuccessMessage("Product Apagado com Sucesso");
		productService.remove(productSelect);
		redirect();

	}

	//	public boolean checkProduct(){
	//		System.out.println("Está checando");
	//		if (!productValidator(product)){
	//			System.out.println("está verdadeiro");
	//			setProductValitator(true);
	//			return true;}
	//		System.out.println("está falso");
	//			
	//		return false;
	//	}

	public void keepProduct(){
		contexto.getExternalContext().getSessionMap().put("productSelected", productSelect);
		System.out.println("Product Selected: "+productSelect.getName());
		System.out.println("Product: "+product.getName());

	}

	public void editProduct() {

		if (productSelect.getId() == null) {
			System.out.println("Deu merge");
			JsfUtil.addErrorMessage("Erro ao selecionar seu produto, por favor tente mais tarde");

		} else {
			if (productValidator(productSelect)){
				System.out.println("Não deu merge");
				productService.update(productSelect);
				JsfUtil.addSuccessMessage("Produto atualizado com sucesso");
				redirect();
			}
		}

	}

	public Boolean productValidator(Product produto){
		if(produto.getName()==null || produto.getName().equals(""))
			return false;

		return true;
	}

	//End Product operations

	//Begin Project operations

	public void createProject(){
		if(projectValidator(project) ){
			System.out.println("Produto commitado: "+productSelect.getName());
			project.setStartDate(getCurrentTime().getTime());
			project.setIsCompleted(false);
			projectService.create(project);
			productSelect.getListProjects().add(project);
			project.setProduct(productSelect);
			projectService.update(project);
			this.project = new Project();
			JsfUtil.addSuccessMessage("Projeto Criado com Sucesso");
			updateListProject();
		}else{
			JsfUtil.addSuccessMessage("Não foi possível criar o projeto");
		}
	}

	public void deleteProject(){
		if(projectValidator(projectSelect)){
			JsfUtil.addSuccessMessage("Projeto Apagado com Sucesso");
			projectService.remove(projectSelect);
		}else{
			JsfUtil.addSuccessMessage("Não foi possível apagar este projeto");
		}
	}

	public void updateProject(){
		if(projectValidator(projectSelect)){
			JsfUtil.addSuccessMessage("Projeto Editado com Sucesso");
			projectService.update(projectSelect);
		}else{
			JsfUtil.addSuccessMessage("Não foi possível editar este projeto");
		}
	}

	public void keepProject(){
		contexto.getExternalContext().getSessionMap().put("projectSelected", projectSelect);
		System.out.println("Project Selected: "+projectSelect.getTitle());
		System.out.println("Project: "+project.getTitle());

	}

	public Boolean projectValidator(Project entity){
		if(project.getTitle()==null || project.getTitle().equals(""))
			return false;
		return true;		
	}

	public void updateListProject() {
		if(getProductSelect()!=null){
			if(getProductSelect().getId()!=null)
				listProject = projectService.findByIdProduct(getProductSelect());
				System.out.println(listProject.toString());
		}else{
			listProject = null;}
	}

	public void onChangeProduct(){
		updateListProject();
		keepProduct();
	}
	
	public void onChangeProject(){
		updateListProject();
		keepProject();
	}

	//End Project operations


	//	public void verItens(Product product) {
	//		try {
	//			System.out.println("Product: " + product);
	//			FacesContext.getCurrentInstance().getExternalContext().getRequest();
	//			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	//
	//			FacesContext.getCurrentInstance().getExternalContext()
	//			.redirect("/Scream/itensProduto/index.xhtml");
	//		} catch (IOException ex) {
	//			JsfUtil.addErrorMessage(ex, "Pagina não encontrada");
	//			Logger.getLogger(ContaController.class.getName()).log(Level.SEVERE,
	//					null, ex);
	//		}
	//	}

	//Begin Primefaces setup

	public void handleReorder(DashboardReorderEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Reordered: " + event.getWidgetId());
		message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());

		addMessage(message);
	}

	public void handleClose(CloseEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");

		addMessage(message);
	}

	public void handleToggle(ToggleEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());

		addMessage(message);
	}

	public void redirect(){
		try {//Redirect para atualização das informações
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("/Scream/app.xhtml");
		} catch (IOException e) {
			JsfUtil.addErrorMessage("Aconteceu algo inesperado ao apagar este product");
		}
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	//End Primefaces setup

	//Getters and Setters

	public ProductService getService() {
		return productService;
	}

	public void setService(ProductService service) {
		this.productService = service;
	}

	public Product getProduct() {
		return product;
	}

	public List<Product> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<Product> listProduct) {
		this.listProduct = listProduct;
	}

	public Product getProductSelect() {
		return productSelect;
	}

	public void setProductSelect(Product produtoSelect) {
		this.productSelect = produtoSelect;
	}

	public Boolean getProductValitator() {
		return productValitator;
	}

	public void setProductValitator(Boolean productValitator) {
		this.productValitator = productValitator;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProjectSelect() {
		return projectSelect;
	}

	public void setProjectSelect(Project projectSelect) {
		this.projectSelect = projectSelect;
	}

	public List<Project> getListProject() {
		return listProject;
	}

	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}

}