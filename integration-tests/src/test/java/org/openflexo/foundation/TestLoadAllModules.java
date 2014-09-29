package org.openflexo.foundation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.OpenflexoTestCaseWithGUI;
import org.openflexo.fme.FMEModule;
import org.openflexo.fme.FreeModellingEditor;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.module.FlexoModule;
import org.openflexo.module.ModuleLoader;
import org.openflexo.module.ModuleLoadingException;
import org.openflexo.module.ProjectLoader;
import org.openflexo.prefs.PreferencesService;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;
import org.openflexo.ve.VEModule;
import org.openflexo.ve.ViewEditor;
import org.openflexo.view.controller.TechnologyAdapterControllerService;
import org.openflexo.vpm.VPMModule;
import org.openflexo.vpm.ViewPointModeller;

@RunWith(OrderedRunner.class)
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
		assertNotNull(serviceManager.getService(PreferencesService.class));

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
			FlexoModule<VPMModule> loadedModule = moduleLoader.getModuleInstance(ViewPointModeller.INSTANCE);
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
	 * Try to load VE module
	 */
	@Test
	@TestOrder(3)
	public void test2VEModuleLoading() {
		log("test2VEModuleLoading()");

		try {
			FlexoModule<VEModule> loadedModule = moduleLoader.getModuleInstance(ViewEditor.INSTANCE);
			if (loadedModule == null) {
				fail();
			}
			// This module is not in the classpath, normal
		} catch (ModuleLoadingException e) {
			e.printStackTrace();
			fail();
		}

		assertNotNull(serviceManager.getService(TechnologyAdapterControllerService.class));

	}

	/**
	 * Try to load FME module
	 */
	@Test
	@TestOrder(4)
	public void test3FMEModuleLoading() {
		log("test3FMEModuleLoading()");

		try {
			FlexoModule<FMEModule> loadedModule = moduleLoader.getModuleInstance(FreeModellingEditor.INSTANCE);
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
