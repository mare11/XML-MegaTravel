package org.xmlws.reservationservice.config;

import net.xqj.exist.ExistXQDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.dataservice.config.exist.ExistTemplate;

import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

@Configuration
public class ExistTemplateConfiguration {

    //    @Value("${exist.datasource.server-firstName:localhost}")
    @Value("${exist.datasource.server-firstName:exist}")
    private String serverName;

    @Value("${exist.datasource.port:8080}")
    private String port;

    @Value("${exist.datasource.user:admin}")
    private String user;

    @Value("${exist.datasource.password:}")
    private String password;

    @Bean
    XQDataSource existXqDataSource() throws XQException {
        XQDataSource xqDataSource = new ExistXQDataSource();
        xqDataSource.setProperty("serverName", serverName);
        xqDataSource.setProperty("port", port);
        ((ExistXQDataSource) xqDataSource).setUser(user);
        ((ExistXQDataSource) xqDataSource).setPassword(password);
        return xqDataSource;
    }

    @Bean
    ExistTemplate existTemplate() {
        return new ExistTemplate();
    }

    @Bean
    Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("org.xmlws");
        return marshaller;
    }

    @Bean
    CatalogRepository catalogRepository() {
        return new CatalogRepository();
    }
}