package br.ifpb.monteiro.scream.converters;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.scream.projects.Project;
import br.edu.ifpb.scream.projects.ProjectService;

/**
 * 
 * @author Mauricio  Aguiar
 * This class is responsible to converter a project to a SelectItems type
 * to work with selectOneMenu function
 *
 */
@Named
@ApplicationScoped
public class ProjectConverter implements Converter {

	
	@Inject
	private ProjectService service;
	
	/**
	 * method recover a object from a object string key on context 
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("Passou aqui getAsObject");
		System.out.println(value);
		
	       if (value == null) {
	            return null;
	            
	       }try{
	    	   Project p = service.find(Long.valueOf(value));
	    	   return p;
	    	   
	    	   
	       }catch(NumberFormatException e){
	    	   throw new ConverterException(new FacesMessage(String.format("projeto não válido", value)), null);
	       }
	       
	    }
	
	/**
	 * method convert a object in a unique String
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		System.out.println("Passou aqui getAsString");
		return (value instanceof Project) ? ((Project) value).getId().toString() : null;
	}

}
