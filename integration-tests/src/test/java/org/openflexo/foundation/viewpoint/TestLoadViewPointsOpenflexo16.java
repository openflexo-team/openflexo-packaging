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
public class TestLoadViewPointsOpenflexo16 extends OpenflexoTestCase {

	protected static final Logger logger = Logger.getLogger(TestLoadViewPointsOpenflexo16.class.getPackage().getName());

	public static FlexoServiceManager serviceManager;
	
	/**
	 * Instantiate test resource center
	 */
	@Test
	@TestOrder(1)
	public void test0InstantiateResourceCenter() {
		log("test0InstantiateResourceCenter()");
		
		instanciateTestServiceManager();
	}

	private ViewPoint testLoadViewPoint(String viewPointURI) {

		log("Testing ViewPoint loading: " + viewPointURI);

		System.out.println("resourceCenter=" + resourceCenter);
		System.out.println("resourceCenter.getViewPointRepository()=" + resourceCenter.getViewPointRepository());

		ViewPointResource vpRes = resourceCenter.getViewPointRepository().getResource(viewPointURI);

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
			for (FlexoResource<?> r2 : vmRes.getContents()) {
				assertTrue(r2 instanceof DiagramResource || r2 instanceof DiagramPaletteResource);
				if (r2 instanceof DiagramResource) {
					DiagramResource edRes = (DiagramResource) r2;
					assertFalse(edRes.isLoaded());
					Diagram ed = edRes.getDiagram();
					assertNotNull(ed);
					assertTrue(edRes.isLoaded());
				}
				if (r2 instanceof DiagramPaletteResource) {
					DiagramPaletteResource dpRes = (DiagramPaletteResource) r2;
					assertFalse(dpRes.isLoaded());
					DiagramPalette dp = dpRes.getDiagramPalette();
					assertNotNull(dp);
					assertTrue(dpRes.isLoaded());
				}
			}
		}
		
		return vp;

	}
	
	@Test
	@TestOrder(2)
	public void test1LoadBasicOntology() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/Basic/BasicOntology.owl");
	}

	@Test
	@TestOrder(3)
	public void test2LoadBDN() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/ScopeDefinition/BenefitDependancyNetwork.owl");
	}

	@Test
	@TestOrder(4)
	public void test3LoadOC() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/ScopeDefinition/OrganizationalChart.owl");
	}

	@Test
	@TestOrder(5)
	public void test4LoadOM() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/ScopeDefinition/OrganizationalMap.owl");
	}

	@Test
	@TestOrder(6)
	public void test5LoadOUD() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/ScopeDefinition/OrganizationalUnitDefinition.owl");
	}

	@Test
	@TestOrder(7)
	public void test6LoadSKOS() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/SKOS/SKOSThesaurusEditor.owl");
	}

	@Test
	@TestOrder(8)
	public void test7LoadUMLPackage() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/UML/PackageDiagram.owl");
	}

	@Test
	@TestOrder(9)
	public void test8LoadUMLUseCases() {
		testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints16/UML/UseCaseDiagram.owl");
	}
	
	/*@Test
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
		
	}*/

	/*@Test
	@TestOrder(3)
	public void test2LoadExcelViewpoint() {
		testLoadViewPoint("/TestResourceCenter/OldViewPoints/Excel/ViewPoints16/ExcelViewpoint.viewpoint");
	}*/
}
