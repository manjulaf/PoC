package com.nyl.poc.templating.controller;
/**
 * PoC for freeMaker templating engine with Postgres database
 * Based on the passed template id from the client, the corresponding template content is picked
 * from the Postgres database. Also based on the title and body passed by the client an out put
 * is generated with the template applied with values.
 */

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nyl.poc.templating.DBTemplateLoader;

import com.nyl.poc.templating.model.Mail;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller
public class TemplateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	DBTemplateLoader dbtemplateLoader;

	@Autowired
	Configuration configuration;

	@GetMapping(value = "/template")
	@RequestMapping(method = RequestMethod.POST)

	public void getMail(Model model, @RequestBody(required = false) Mail mail, HttpServletResponse response)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
			TemplateException {
		String templateId = mail.getTemplateId();
		LOGGER.info("template id " + templateId);
		if (templateId == null) {
			LOGGER.error("Error: template ID null");
		}
		model.addAttribute("title", mail.getMailContent().getTitle());
		model.addAttribute("content", mail.getMailContent().getContentBody());
		// Set the custom template loader to get the respective template from db
		LOGGER.info("Setting custom template loader ");
		configuration.setDefaultEncoding("UTF-8");
		configuration.setLocalizedLookup(false);
		configuration.setTemplateLoader(dbtemplateLoader);
		// Get the template content
		freemarker.template.Template tempObj = configuration.getTemplate(templateId);
		LOGGER.info("Writing to ouputstream ");
		tempObj.process(model, response.getWriter());

	}
}
