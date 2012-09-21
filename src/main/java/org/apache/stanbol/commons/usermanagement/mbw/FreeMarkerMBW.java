package org.apache.stanbol.commons.usermanagement.mbw;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.clerezza.rdf.ontologies.RDF;
import org.apache.clerezza.rdf.utils.GraphNode;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.commons.ldpathtemplate.LdRenderer;
import org.apache.stanbol.commons.usermanagement.Ontology;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@Component
@Service(FreeMarkerMBW.class)
public class FreeMarkerMBW implements MessageBodyWriter<GraphNode> {

	@Reference
	private LdRenderer ldRenderer;
	
	public class Prefix implements TemplateHashModel {

		private String path;

		public Prefix(String path) {
			this.path = path;
		}

		@Override
		public TemplateModel get(String section) throws TemplateModelException {
			System.out.println("getting: "+section+" on "+path);
			return new Prefix(path+section);
		}

		@Override
		public boolean isEmpty() throws TemplateModelException {
			// TODO Auto-generated method stub
			return false;
		}

	}

	public FreeMarkerMBW() {
		System.out.println("initializing mbw");
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return GraphNode.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(GraphNode t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(GraphNode t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		Writer out = new OutputStreamWriter(entityStream);
		if (t.hasProperty(RDF.type, Ontology.EditableUser)) {
			ldRenderer.render(t, "rdftypes/EditableUser.ftl", out);
		} else {
			ldRenderer.render(t, "rdftypes/Resource.ftl", out);
		}
		out.flush();  
		
	}
	

}
