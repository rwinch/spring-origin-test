///*
// * Copyright 2002-2015 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
package server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Sebastien Deleuze
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	//@Override
	//public void addCorsMappings(CorsRegistry registry) {
	//	registry.addMapping("/api/**").allowedOrigins("http://domain2.com")
	//			.allowedMethods("PUT", "DELETE").allowedHeaders("header1", "header2", "header3")
	//			.exposedHeaders("header1", "header2").allowCredentials(false).maxAge(3600);
	//}

    /**
     * Yes this is not on by default, but many applications will have
     * dispatchOptionsRequest enabled if they are doing CORS now. Furthermore,
     * this is a very easy copy paste error with huge implications if the recomendation is
     * to disable security for all OPTIONS requests.
     *
     * @param context
     * @return
     */
    @Bean
    public DispatcherServlet dispatcherServlet(WebApplicationContext context) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setDispatchOptionsRequest(true);
        return dispatcherServlet;
    }
}
