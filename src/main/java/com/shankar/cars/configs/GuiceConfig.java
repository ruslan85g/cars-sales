/*
 */

package com.shankar.cars.configs;

import java.util.Map;

import lombok.extern.java.Log;

import com.shankar.cars.action.*;
import com.shankar.cars.data.persist.OfyService;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Creates our Guice module
 * 
 */
@Log
public class GuiceConfig extends GuiceServletContextListener {
	/** */
	public static class CarsServletModule extends ServletModule {
		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.inject.servlet.ServletModule#configureServlets()
		 */
		@Override
		protected void configureServlets() {
			log.info("ObjectifyFilter through");
			filter("/*").through(ObjectifyFilter.class);
			
			
			Map<String, String> params = Maps.newHashMap();
			params.put("com.sun.jersey.config.property.packages", "com.shankar.cars.action");
			serve("/api/*").with(GuiceContainer.class, params);
		}
	}

	/** Public so it can be used by unit tests */
	public static class CarsModule extends AbstractModule {
		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.inject.AbstractModule#configure()
		 */
		// @Override
		protected void configure() {
			
			requestStaticInjection(OfyService.class);
			
			bind(ObjectifyFilter.class).in(Singleton.class);
			
			bind(CarsServlet.class);
			bind(CarTypeServlet.class);
		}
	}
	/** Public so it can be used by unit tests */
	public static class CarsTestModule extends AbstractModule {
		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.inject.AbstractModule#configure()
		 */
		// @Override
		protected void configure() {
			log.info("OfyService");
			requestStaticInjection(OfyService.class);

			log.info("ObjectifyFilter");
			bind(ObjectifyFilter.class).in(Singleton.class);
			/*
			bind(DeployServletTest.class);
			*/
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.servlet.GuiceServletContextListener#getInjector()
	 */
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new CarsServletModule() , new CarsModule());
	}

}
