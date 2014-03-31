package org.openflexo.foundation.viewpoint;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.foundation.viewpoint.rm.ViewPointResourceImpl;
import org.openflexo.foundation.viewpoint.rm.VirtualModelResource;
import org.openflexo.technologyadapter.diagram.TypedDiagramModelSlot;
import org.openflexo.technologyadapter.diagram.metamodel.DiagramPalette;
import org.openflexo.technologyadapter.diagram.model.Diagram;
import org.openflexo.technologyadapter.diagram.rm.DiagramPaletteResource;
import org.openflexo.technologyadapter.diagram.rm.DiagramResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

@RunWith(OrderedRunner.class)
public class TestLoadOldViewPoints extends OpenflexoTestCase {

	protected static final Logger logger = Logger.getLogger(TestLoadOldViewPoints.class.getPackage().getName());

	public static FlexoServiceManager serviceManager;
	
	/**
	 * Instantiate test resource center
	 */
	@Test
	@TestOrder(1)
	public void test0InstantiateResourceCenter() {
		log("test0InstantiateResourceCenter()");
		serviceManager = instanciateTestServiceManager(false);
		
	}

	private ViewPoint testLoadViewPoint(String viewPointDirectoryPath) {

		log("Testing ViewPoint loading: " + viewPointDirectoryPath);

		System.out.println("resourceCenter=" + resourceCenter);
		System.out.println("resourceCenter.getViewPointRepository()=" + resourceCenter.getViewPointRepository());
		System.out.println("Converting viewpoint =" + resourceCenter.getViewPointRepository() + viewPointDirectoryPath);
		File newViewPointDirectory = new File(resourceCenter.getViewPointRepository().getDirectory()+viewPointDirectoryPath);

		ViewPointResource vpRes = ViewPointResourceImpl.retrieveViewPointResource(newViewPointDirectory, serviceManager);
		
		assertNotNull(vpRes);
		assertFalse(vpRes.isLoaded());

		ViewPoint vp = vpRes.getViewPoint();
		assertNotNull(vp);
		assertTrue(vpRes.isLoaded());

		for (FlexoResource<?> r : vpRes.getContents()) {
			assertTrue(r instanceof VirtualModelResource);
			VirtualModelResource vmRes = (VirtualModelResource) r;
			VirtualModel vm = vmRes.getVirtualModel();
			assertNotNull(vm);
			assertTrue(vmRes.isLoaded());
		}
		
		return vp;

	}

	@Test
	@TestOrder(2)
	public void test1LoadCityMappingViewpoint() {
		ViewPoint vp = testLoadViewPoint("/TestResourceCenter/OldViewPoints/EMF/ViewPoints/CityMapping.viewpoint");
		VirtualModel cityDiagramVm = vp.getVirtualModelNamed("CityDiagram");
		VirtualModel citiesDiagramVm = vp.getVirtualModelNamed("CitiesDiagram");
		VirtualModel cityMappingVm = vp.getVirtualModelNamed("CityMapping");
		
		assertNotNull(cityDiagramVm);
		assertNotNull(citiesDiagramVm);
		assertNotNull(cityMappingVm);
		
		assertTrue(cityDiagramVm.getModelSlots(TypedDiagramModelSlot.class).size()==1);
		assertTrue(citiesDiagramVm.getModelSlots(TypedDiagramModelSlot.class).size()==1);
		
	}

	@Test
	@TestOrder(3)
	public void test2LoadExcelViewpoint() {
		testLoadViewPoint("/TestResourceCenter/OldViewPoints/Excel/ViewPoints/ExcelViewpoint.viewpoint");
	}
	
}
