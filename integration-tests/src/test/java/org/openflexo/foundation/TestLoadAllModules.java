package org.openflexo.foundation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.logging.Logger;

import org.junit.Test;
import org.openflexo.OpenflexoTestCaseWithGUI;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.module.FlexoModule;
import org.openflexo.module.ModuleLoader;
import org.openflexo.module.ModuleLoadingException;
import org.openflexo.module.ProjectLoader;
import org.openflexo.test.TestOrder;
import org.openflexo.ve.VEModule;
import org.openflexo.view.controller.TechnologyAdapterControllerService;
import org.openflexo.vpm.VPMModule;

public class TestLoadAllModules extends OpenflexoTestCaseWithGUI {

	protected static final Logger logger = Logger.getLogger(TestLoadAllModules.class.getPackage().getName());

	private static ModuleLoader moduleLoader;

	/**
	 * Instanciate test ApplicationContext
	 */
	@Test
	@TestOrder(1)
	public void test0UseTestApplicationContext() {
		log("test0UseTestApplicationContext()");
		instanciateTestServiceManager();

		logger.info("services: " + serviceManager.getRegisteredServices());

		assertNotNull(serviceManager.getService(ProjectLoader.class));
		assertNotNull(serviceManager.getService(ModuleLoader.class));
		assertNotNull(serviceManager.getService(FlexoResourceCenterService.class));
		assertNotNull(serviceManager.getService(TechnologyAdapterService.class));
		assertNotNull(serviceManager.getService(TechnologyAdapterControllerService.class));

		moduleLoader = serviceManager.getModuleLoader();
		assertEquals(moduleLoader, serviceManager.getService(ModuleLoader.class));

	}

	/**
	 * Try to load VPM module
	 */
	@Test
	@TestOrder(2)
	public void test1VPMModuleLoading() {
		log("test1VPMModuleLoading()");

		try {
			FlexoModule loadedModule = moduleLoader.getModuleInstance(VPMModule.VPM);
			if (loadedModule == null) {
				fail();
			}
			// This module is not in the classpath, normal
		} catch (ModuleLoadingException e) {
			fail();
		}

		assertNotNull(serviceManager.getService(TechnologyAdapterControllerService.class));

	}

	/**
	 * Try to load VPM module
	 */
	@Test
	@TestOrder(3)
	public void test2VEModuleLoading() {
		log("test2VEModuleLoading()");

		try {
			FlexoModule loadedModule = moduleLoader.getModuleInstance(VEModule.VE);
			if (loadedModule == null) {
				fail();
			}
			// This module is not in the classpath, normal
		} catch (ModuleLoadingException e) {
			fail();
		}

		assertNotNull(serviceManager.getService(TechnologyAdapterControllerService.class));

	}

}
