package br.ufes.progweb.acerolatrack;

import com.vaadin.flow.server.VaadinServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@SpringBootApplication()
@EnableJpaAuditing
public class AcerolatrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcerolatrackApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean frontendServletBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean<>(new VaadinServlet() {
			@Override
			protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				if (!serveStaticOrWebJarRequest(req, resp)) {
					resp.sendError(404);
				}
			}
		}, "/front/*");
		bean.setLoadOnStartup(1);
		return bean;
	}

}
