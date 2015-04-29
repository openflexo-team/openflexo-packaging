/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Integration-tests, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

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
import org.openflexo.ism.ISMModule;
import org.openflexo.ism.InformationSpaceModule;
import org.openflexo.module.FlexoModule;
import org.openflexo.module.ModuleLoader;
import org.openflexo.module.ModuleLoadingException;
import org.openflexo.prefs.PreferencesService;
import org.openflexo.project.ProjectLoader;
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
	public void testUseTestApplicationContext() {
		log("testUseTestApplicationContext()");
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
	public void testVPMModuleLoading() {
		log("testVPMModuleLoading()");

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
	public void testVEModuleLoading() {
		log("testVEModuleLoading()");

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
	public void testISMModuleLoading() {
		log("testISMModuleLoading()");

		try {
			FlexoModule<ISMModule> loadedModule = moduleLoader.getModuleInstance(InformationSpaceModule.INSTANCE);
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
	 * Try to load FME module
	 */
	@Test
	@TestOrder(5)
	public void testFMEModuleLoading() {
		log("testFMEModuleLoading()");

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
