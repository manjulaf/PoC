PoC for freeMaker templating engine with Postgres database.
Based on the passed template id from the client, the corresponding template content is picked 
from the Postgres database. Also based on the title and body passed by the client an out put is
generated with the template applied with values.

Prerequisite: 
- Create the "emailtemplates" table in Postgres database by executing dbscripts/emailtemplates-table.sql
- Populate the table with template data by executing dbscripts/insert-data.sql
- Update the src/main/resources/application.properties with database credentials (url,user,password)


- Start the Springboot App. 
- Execute the below end point
The end point:
	http://localhost:8080/template
	POST
	
	sample request
	   {
		"to":"smith@gmail.com",
		"from":"jhone@gmail.com",
		"templateId":"template001",
			"mailContent":{
				"title":"Test title for template",
				"contentBody":"Test content of the body"
			}
		}

