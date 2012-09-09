package org.apache.stanbol.commons.usermanagement;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.clerezza.rdf.ontologies.PLATFORM;
import org.apache.clerezza.rdf.utils.GraphNode;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;

@Component
@Service(Servlet.class)
@Properties({
		@Property(name = "felix.webconsole.label", value = "usermanagement"),
		@Property(name = "felix.webconsole.title", value = "User Management") })
public class WebConsolePlugin extends
		org.apache.felix.webconsole.AbstractWebConsolePlugin {

	@Reference
	private UserManager userManager;
	
	public static final String NAME = "User Management";
	public static final String LABEL = "usermanagement";

	public String getLabel() {
		return LABEL;
	}

	public String getTitle() {
		return NAME;
	}

	protected void renderContent(HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.write("<h1>List of users:</h1>");
		for (GraphNode user: userManager.getUsers()) {
			renderUser(pw, user);
		}
	}

	private void renderUser(PrintWriter pw, GraphNode user) {
		pw.write("<p> User:"+user.getLiterals(PLATFORM.userName).next()+"</p>");
	}

	public void activateBundle(BundleContext bundleContext) {
		super.activate(bundleContext);
	}

	public void deactivate() {
		super.deactivate();

	}
}