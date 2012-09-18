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

import org.apache.clerezza.rdf.utils.GraphNode;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.commons.ldpathtemplate.LdRenderer;

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
		

		Configuration cfg = new Configuration();
		// Specify the data source where the template files come from.
		// Here I set a file directory for it:
		cfg.setTemplateLoader(new ClassTemplateLoader(getClass(),""));
		/*cfg.setTemplateLoader(new TemplateLoader() {
			
			@Override
			public Reader getReader(Object arg0, String arg1) throws IOException {
				System.out.println("getReader "+arg0+" "+arg1);
				return null;
			}
			
			@Override
			public long getLastModified(Object arg0) {
				System.out.println("getLastModified "+arg0);
				return 0;
			}
			
			@Override
			public Object findTemplateSource(String arg0) throws IOException {
				System.out.println("findTemplateSource "+arg0);
				return null;
			}
			
			@Override
			public void closeTemplateSource(Object arg0) throws IOException {
				System.out.println("closeTemplateSource "+arg0);
				
			}
		});*/
		//fake data 
		
		// Create the root hash
		final Map<String, Object> root = new HashMap<String, Object>();
		// Put string ``user'' into the root
		root.put("user", "Big Joe");
		// Create the hash for ``latestProduct''
		Map<String, Object> latest = new HashMap<String, Object>();
		// and put it into the root
		root.put("latestProduct", latest);
		// put ``url'' and ``name'' into latest
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
		// Specify how templates will see the data-model. This is an advanced topic...
		// but just use this:
		
		/*cfg.setObjectWrapper(new DefaultObjectWrapper() {
			
			@Override
			public TemplateModel wrap(final Object wrapped) throws TemplateModelException {
				final TemplateModel result = super.wrap(root);
				System.out.println("fake result: "+result+" a "+result.getClass());
				//return result;
				
				return new TemplateHashModel() {
					
					@Override
					public boolean isEmpty() throws TemplateModelException {
						// TODO Auto-generated method stub
						return false;
					}
					
					

					public TemplateModel get(String section)
							throws TemplateModelException {
						System.out.println("someone is getting: "+section+" for "+wrapped);
						if (wrapped instanceof Prefix) {							
							return ((SimpleHash)result).get(section);
						} 
						return new Prefix(section);
					}

					@Override
					public String toString() {
						return "Graphnode template model";
					}
				};
			}
			@Override
			public String toString() {
				return "Graphnode Object Wrapper";
			}
		});*/  
		
		

		  
		Template temp = cfg.getTemplate("test.ftl");
		Writer out = new OutputStreamWriter(entityStream);
		ldRenderer.render(t, temp, out);
		//entityStream.write("hello world!".getBytes());
		
		/*try {
			temp.process(t, out);
		} catch (TemplateException e) {
			throw new RuntimeException();
		}*/
		out.flush();  
		
	}
	

}
