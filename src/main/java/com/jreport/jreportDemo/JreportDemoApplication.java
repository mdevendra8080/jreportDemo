package com.jreport.jreportDemo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jndi.JndiObjectFactoryBean;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@SpringBootApplication
public class JreportDemoApplication implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(JreportDemoApplication.class);

	public static void main(String[] args) {
		// System.out.println("START MAIN 1");
		logger.info("START MAIN");
		SpringApplication.run(JreportDemoApplication.class, args);

	}

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatFactory() {

		return new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				// TODO Auto-generated method stub
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {

				ContextResource resource = new ContextResource();
				resource.setName("jdbc/custread");
				resource.setProperty("factory", "org.apache.commons.dbcp.BasicDataSourceFactory");
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
				resource.setProperty("url", "jdbc:oracle:thin:@localhost:1521:orcl");
				resource.setProperty("password", "tiger");
				resource.setProperty("username", "scott");

				context.getNamingResources().addResource(resource);
			}
		};

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("HIIII");
		logger.info("INSIDE RUN");

		String jrxmlName = "jreport/oracle/ACCOUNT_REPORT.jrxml";

		Resource resource = new ClassPathResource(jrxmlName);
		// resource.getFile().getAbsolutePath();

		logger.info("PATH::" + resource.getFile().getAbsolutePath());
		Map<String, Object> parameters = new HashMap<String, Object>();
		System.out.println("IMAGE PATH:"+new ClassPathResource("jreport/images/mini.jpg").getFile().getAbsolutePath());
		parameters.put("jreportpath", new ClassPathResource("jreport/images/mini.jpg").getFile().getAbsolutePath().toString());
		JasperReport jasperReport = JRXMLCompiler.compileReport(resource.getFile().getAbsolutePath());

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jndiDataSource().getConnection());

		// URL outDir = new ClassPathResource("jasperoutput").getURL();

		// System.out.println(outDir);
		// File fdir = new File(outDir.getFile());
		// fdir.mkdirs();

		File outFile = createResourceSubFolder("jasperoutput");
		/// account.pdf
		// Export to PDF.
		JasperExportManager.exportReportToPdfFile(jasperPrint, new File(outFile, "account.pdf").getAbsolutePath());

	}

	private static File createResourceSubFolder(String folderName) throws URISyntaxException, IOException {
		java.net.URL url = JreportDemoApplication.class.getResource("/");
		System.out.println("::===>>>" + url);
		File fullPathToSubfolder = new File(url.toURI()).getAbsoluteFile();
		String projectFolder = fullPathToSubfolder.getAbsolutePath().split("target")[0];
		File testResultsFolder = new File(projectFolder + "src/main/resources/" + folderName);
		if (!testResultsFolder.exists()) {
			testResultsFolder.mkdir();
		}
		return testResultsFolder;
	}

	@Bean(destroyMethod = "")
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("jdbc/custread");
		bean.setResourceRef(true);
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
}
