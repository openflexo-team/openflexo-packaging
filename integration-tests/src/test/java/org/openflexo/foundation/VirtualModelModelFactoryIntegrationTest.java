/*
 * (c) Copyright 2010-2011 AgileBirds
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openflexo.foundation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.DefaultTechnologyAdapterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.foundation.viewpoint.VirtualModelModelFactory;
import org.openflexo.foundation.viewpoint.VirtualModelTechnologyAdapter;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.model.ModelContext;
import org.openflexo.model.ModelEntity;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.diagram.DiagramTechnologyAdapter;
import org.openflexo.technologyadapter.emf.EMFTechnologyAdapter;
import org.openflexo.technologyadapter.excel.ExcelTechnologyAdapter;
import org.openflexo.technologyadapter.owl.OWLTechnologyAdapter;
import org.openflexo.technologyadapter.powerpoint.PowerpointTechnologyAdapter;
import org.openflexo.technologyadapter.xml.XMLTechnologyAdapter;
import org.openflexo.technologyadapter.xsd.XSDTechnologyAdapter;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * Test instanciation of VirtualModelModelFactory<br>
 * Here the model factory is instanciated with all known technology adapters
 * 
 */
@RunWith(OrderedRunner.class)
public class VirtualModelModelFactoryIntegrationTest extends OpenflexoTestCase {

	private static final Logger logger = FlexoLogger.getLogger(VirtualModelModelFactoryIntegrationTest.class.getPackage().getName());

	/**
	 * Instanciate test ServiceManager
	 */
	@Test
	@TestOrder(1)
	public void initializeServiceManager() {
		log("initializeServiceManager()");
		instanciateTestServiceManager();

		logger.info("services: " + serviceManager.getRegisteredServices());

		assertNotNull(serviceManager.getService(FlexoResourceCenterService.class));
		assertNotNull(serviceManager.getService(TechnologyAdapterService.class));

		TechnologyAdapterService taService = serviceManager.getTechnologyAdapterService();
		assertEquals(taService, serviceManager.getService(TechnologyAdapterService.class));

		assertNotNull(taService.getTechnologyAdapter(VirtualModelTechnologyAdapter.class));
		assertNotNull(taService.getTechnologyAdapter(DiagramTechnologyAdapter.class));
		assertNotNull(taService.getTechnologyAdapter(EMFTechnologyAdapter.class));
		assertNotNull(taService.getTechnologyAdapter(ExcelTechnologyAdapter.class));
		assertNotNull(taService.getTechnologyAdapter(OWLTechnologyAdapter.class));
		assertNotNull(taService.getTechnologyAdapter(PowerpointTechnologyAdapter.class));
		assertNotNull(taService.getTechnologyAdapter(XMLTechnologyAdapter.class));
		assertNotNull(taService.getTechnologyAdapter(XSDTechnologyAdapter.class));
	}

	private void testVirtualModelModelFactoryWithTechnologyAdapter(TechnologyAdapter ta) {
		assertNotNull(ta);
		try {
			System.out.println("Instanciating ViewPointModelFactory");
			TechnologyAdapterService taService = DefaultTechnologyAdapterService.getNewInstance(null);
			taService.addToTechnologyAdapters(ta);
			VirtualModelModelFactory factory = new VirtualModelModelFactory(taService);
			ModelContext modelContext = factory.getModelContext();
			for (Iterator<ModelEntity> it = modelContext.getEntities(); it.hasNext();) {
				ModelEntity e = it.next();
				System.out.println("> Found " + e.getImplementedInterface());
			}
			for (Class<?> modelSlotClass : ta.getAvailableModelSlotTypes()) {
				assertNotNull(factory.getModelContext().getModelEntity(modelSlotClass));
			}
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	/**
	 * Check the presence of VirtualModelTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(2)
	public void checkVirtualModelTechnologyAdapter() {
		log("checkVirtualModelTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				VirtualModelTechnologyAdapter.class));
	}

	/**
	 * Check the presence of DiagramTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(3)
	public void checkDiagramTechnologyAdapter() {
		log("checkDiagramTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				DiagramTechnologyAdapter.class));
	}

	/**
	 * Check the presence of EMFTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(4)
	public void checkEMFTechnologyAdapter() {
		log("checkEMFTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				EMFTechnologyAdapter.class));
	}

	/**
	 * Check the presence of ExcelTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(5)
	public void checkExcelTechnologyAdapter() {
		log("checkExcelTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				ExcelTechnologyAdapter.class));
	}

	/**
	 * Check the presence of OWLTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(6)
	public void checkOWLTechnologyAdapter() {
		log("checkOWLTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				OWLTechnologyAdapter.class));
	}

	/**
	 * Check the presence of PowerpointTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(7)
	public void checkPowerpointTechnologyAdapter() {
		log("checkPowerpointTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				PowerpointTechnologyAdapter.class));
	}

	/**
	 * Check the presence of XMLTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(8)
	public void checkXMLTechnologyAdapter() {
		log("checkXMLTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				XMLTechnologyAdapter.class));
	}

	/**
	 * Check the presence of XSDTechnologyAdapter, instanciate VirtualModelModelFactory with this TA
	 */
	@Test
	@TestOrder(9)
	public void checkXSDTechnologyAdapter() {
		log("XSDTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				XSDTechnologyAdapter.class));
	}

	/**
	 * 
	 */
	@Test
	@TestOrder(10)
	public void testInstantiateVirtualModelModelFactoryForAllTechnologyAdapters() {
		try {
			System.out.println("Instanciating ViewPointModelFactory");
			VirtualModelModelFactory factory = new VirtualModelModelFactory(serviceManager.getTechnologyAdapterService());
			ModelContext modelContext = factory.getModelContext();
			for (Iterator<ModelEntity> it = modelContext.getEntities(); it.hasNext();) {
				ModelEntity e = it.next();
				System.out.println("> Found " + e.getImplementedInterface());
			}
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
}
