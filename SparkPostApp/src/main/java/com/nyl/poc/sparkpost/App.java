package com.nyl.poc.sparkpost;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import com.sparkpost.model.responses.Response;

/**
 * PoC to send  email through SprkPost using java api. When the Spring boot app starts it sends the 
 * email, with configured values.
 * SprkPost Prerequisites:
 * 1. Create a trial account in SparkPost(www.sparkpost.com) and generate an API key 
 * 2. Configure a valid "Sending domain" in Sparkpost and make sure it is verified. Till this step is done
 *    the emails cannot be send with from address with that domain.
 * 3. Update the variable API_KEY
 * 4. Update the variable FROM_ADDR from any address with the verified domain
 * 5. Update the variable TO_ADDR to any receiving address 
 *
 */

@SpringBootApplication
public class App {

	static String API_KEY = "fba67fece0126ba7a64fcb8a5f090d5140d9fdd4";
	static String TRANSMISSION_URL = "https://api.sparkpost.com/api/v1/transmissions";
	// from address from verified domain
	static String FROM_ADDR = "test@gallery.battleofthemaroons.lk"; 
	static String TO_ADDR = "youremail@gmail.com";

	static Logger log = Logger.getLogger(App.class);

	/**
	 * Email sending when the app starts
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);

		BasicConfigurator.configure();
		log.info(" connecting to URL ...");
		connectToUrl();
		Client client = new Client(API_KEY);
        // Sending email
		log.info(" sending email ... ");
		Response response = client.sendMessage(FROM_ADDR, TO_ADDR, "The subject of the message",
				"The text part of the email", "<b>The HTML part of the email</b>");

		ObjectMapper mapper = new ObjectMapper();
		String json = response.toJson();
		JsonNode actualObj = mapper.readTree(json);
		String responseID = actualObj.get("results").get("id").asText();
		log.info("ResponseID " + responseID);
	}

	/**
	 *  To trust SSL and by pass for the sake of PoC
	 * @throws SparkPostException
	 */

	@SuppressWarnings("restriction")
	public static void doTrustToCertificates() throws SparkPostException {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
				return;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
				return;
			}

		} };
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		}
		try {
			sc.init(null, trustAllCerts, new SecureRandom());
		} catch (KeyManagementException e) {
			log.error(e.getMessage());
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
					System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '"
							+ session.getPeerHost() + "'.");
				}
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	/**
	 * The connection url
	 * 
	 * @throws Exception
	 */
	public static void connectToUrl() throws Exception {
		doTrustToCertificates();
		URL url = new URL(TRANSMISSION_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		log.info("Connection = " + conn);
	}

}
