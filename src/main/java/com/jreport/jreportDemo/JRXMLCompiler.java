package com.jreport.jreportDemo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

public class JRXMLCompiler {
	public static JasperReport compileReport(String jrxmlName) throws JRException {
		return JasperCompileManager.compileReport(jrxmlName);
	}
}
