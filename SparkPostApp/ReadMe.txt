 PoC to send  email through SprkPost using java api. When the Spring boot app starts it sends the 
 email, with configured values.
 SprkPost Prerequisites:
 1. Create a trial account in SparkPost(www.sparkpost.com) and generate an API key 
 2. Configure a valid "Sending domain" in Sparkpost and make sure it is verified. Until verification is done
    the emails cannot be send with from address with that domain. The default from address can send only 05
    emails (sandbox@sparkpostbox.com)
    
    Update following variables in the App.java code
 3. The API_KEY 
 4. The FROM_ADDR from any address with the verified domain 
 5. The TO_ADDR to any receiving address 
 6. Run the Springboot app

