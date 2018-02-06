package com.nyl.poc.templating;
/** 
 * Custom template loader to get the template from the database. 
 */

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nyl.poc.templating.model.Template;
import org.springframework.stereotype.Service;

import freemarker.cache.TemplateLoader;

@Service
public class DBTemplateLoader implements TemplateLoader {

	@Autowired
	TemplateRepository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(DBTemplateLoader.class);

	@Override
	public Object findTemplateSource(String templateId) throws IOException {
		LOGGER.info("templateId in DBTemplateLoader " + templateId);
		// read the database and get the template contents as a string for the given
		// template Id
		Template templateObj = repository.findOne(templateId);
		LOGGER.info("template content from DB " + templateObj.getTemplate_content());
		return templateObj;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return new StringReader(((Template) templateSource).getTemplate_content());
	}

	@Override
	public void closeTemplateSource(Object arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public long getLastModified(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
