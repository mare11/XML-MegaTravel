package org.xmlws.existdb;

import java.io.File;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ExistWarConfiguration {
	
	@Bean
	public ServletWebServerFactory servletContainerFactory() {
	    return new TomcatServletWebServerFactory() {
	    	
	    	@Override
	    	protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
	    		new File(tomcat.getServer().getCatalinaBase(), "webapps").mkdir();
    			try {
    				tomcat.addWebapp("/exist", new ClassPathResource("exist.war").getFile().toString());
    			} catch (Exception ex) {
    				throw new IllegalStateException("Failed to add webapp", ex);
    			}	    			
	            return super.getTomcatWebServer(tomcat);
	    	}
	    };
	}
}
