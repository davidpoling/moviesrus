package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.List;
import java.util.logging.Logger;

public class InfoTraversalPolicy extends FocusTraversalPolicy{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	Container panel;
	List<Component> components;
	
	public InfoTraversalPolicy(Container panel, List<Component> components){
		this.panel = panel;
		this.components = components;
	}
	
	@Override
	public Component getComponentAfter(Container aContainer, Component aComponent) {
		logger.info("After");
		int index = components.indexOf(aComponent);
		
		if(aComponent.equals(components.get(components.size() - 1))){
			return components.get(0);
		}
		return components.get(index + 1);
	}

	@Override
	public Component getComponentBefore(Container aContainer, Component aComponent) {
		logger.info("Before");
		int index = components.indexOf(aComponent);
		
		if(aComponent.equals(components.get(0))){
			return components.get(components.size() - 1);
		}
		return components.get(index - 1);
	}

	@Override
	public Component getFirstComponent(Container aContainer) {
		logger.info("First");
		return components.get(0);
	}

	@Override
	public Component getLastComponent(Container aContainer) {
		logger.info("Last");
		return components.get(components.size() - 1);
	}

	@Override
	public Component getDefaultComponent(Container aContainer) {
		logger.info("Default");
		return components.get(0);
	}

}
