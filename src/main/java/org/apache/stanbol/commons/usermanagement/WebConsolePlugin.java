package org.apache.stanbol.commons.usermanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.clerezza.rdf.core.BNode;
import org.apache.clerezza.rdf.core.Literal;
import org.apache.clerezza.rdf.core.impl.SimpleMGraph;
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
		renderUserForm(pw, new GraphNode(new BNode(), new SimpleMGraph()));
	}

	private void renderUser(PrintWriter pw, GraphNode user) {
		pw.write("<p> User:"+user.getLiterals(PLATFORM.userName).next().getLexicalForm()+"</p>");
	}
	
	private void renderUserForm(PrintWriter pw, GraphNode user) {
		Iterator<Literal> userNames = user.getLiterals(PLATFORM.userName);
		String currentUserName = userNames.hasNext()? userNames.next().getLexicalForm() : "new-user";
		pw.write("<form action=\"edit?user="+currentUserName+"\">");
		pw.write("<input type=\"text\" name=\""+currentUserName+"\">");
		pw.write("</form>");
	}

	public void activateBundle(BundleContext bundleContext) {
		super.activate(bundleContext);
	}

	public void deactivate() {
		super.deactivate();

	}
}