package com.karaoke.management.report;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class BuildBillReport {

	private BillReportMetaData metadata = new BillReportMetaData();

	private final String template = "bill-report-template.txt";

	public Map<String, BillReportMetaData> getEmailParameters() {
		Map<String, BillReportMetaData> parameters = new HashMap<String, BillReportMetaData>();
		parameters.put("metadata", metadata);
		return parameters;
	}

	public String render(String template, Map<String, BillReportMetaData> parameters) {
		Mustache templates = compile(template);
		StringWriter w = new StringWriter();
		templates.execute(w, parameters);
		return w.toString();
	}

	private Mustache compile(String template) {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(template);
		return mustache;
	}

	public String builder() {
		String html = render(this.template, getEmailParameters());
		return html;
	}

	public BillReportMetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(BillReportMetaData metadata) {
		this.metadata = metadata;
	}
	
	

}
