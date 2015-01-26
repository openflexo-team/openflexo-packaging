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

package org.openflexo.foundation.viewpoint;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

@RunWith(OrderedRunner.class)
public class TestViewpointsOpenflexo17 extends OpenflexoTestCase {

	protected static final Logger logger = Logger.getLogger(TestViewpointsOpenflexo17.class.getPackage().getName());

	/**
	 * Instantiate test resource center
	 */
	@Test
	@TestOrder(1)
	public void test0InstantiateResourceCenter() {

		log("test0InstantiateResourceCenter()");

		// TODO: create a project where all those tests don't need a manual import of projects
		// TODO: copy all test VP in tmp dir and work with those VP instead of polling GIT workspace
		instanciateTestServiceManager(true);

	}

	private ViewPoint testLoadViewPoint(String viewPointURI) {

		log("Testing ViewPoint loading: " + viewPointURI);

		System.out.println("resourceCenter=" + resourceCenter);
		System.out.println("resourceCenter.getViewPointRepository()=" + resourceCenter.getViewPointRepository());

		ViewPointResource vpRes = resourceCenter.getViewPointRepository().getResource(viewPointURI);

		assertNotNull(vpRes);
		assertFalse(vpRes.isLoaded());

		ViewPoint vp = vpRes.getViewPoint();
		assertTrue(vpRes.isLoaded());

		return vp;

	}

	@Test
	@TestOrder(2)
	public void test1BasicOntologyEditor() {
		log("test1BasicOntologyEditor()");

		ViewPoint basicOntologyEditor = testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/Basic/BasicOntology.owl");
		assertNotNull(basicOntologyEditor);
		System.out.println("Read resource " + ((ViewPointResource) basicOntologyEditor.getResource()).getFlexoIODelegate().toString());

		// TODO: rewrite this

		/*FlexoConcept conceptEP = basicOntologyEditor.getDefaultDiagramSpecification().getFlexoConcept("Concept");

		for (FlexoConcept ep : basicOntologyEditor.getDefaultDiagramSpecification().getFlexoConcepts()) {
			System.out.println("ep=" + ep);
		}

		assertNotNull(conceptEP);

		DropScheme ds = (DropScheme) conceptEP.getEditionScheme("DropConceptAtTopLevel");
		assertNotNull(ds);*/

		assertViewPointIsValid(basicOntologyEditor);

	}

	@Test
	@TestOrder(3)
	public void test2BDN() {
		log("test2BDN()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/ScopeDefinition/BenefitDependancyNetwork.owl"));
	}

	@Test
	@TestOrder(4)
	public void test3OrganizationalChart() {
		log("test3OrganizationalChart()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/ScopeDefinition/OrganizationalChart.owl"));
	}

	@Test
	@TestOrder(5)
	public void test4OrganizationalMap() {
		log("test4OrganizationalMap()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/ScopeDefinition/OrganizationalMap.owl"));
	}

	@Test
	@TestOrder(6)
	public void test5OrganizationalUnitDefinition() {
		log("test5OrganizationalUnitDefinition()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/ScopeDefinition/OrganizationalUnitDefinition.owl"));
	}

	@Test
	@TestOrder(7)
	public void test6SKOS() {
		log("test6SKOS()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/SKOS/SKOSThesaurusEditor.owl"));
	}

	@Test
	@TestOrder(8)
	public void test7BasicOrganizationTreeEditor() {
		log("test7BasicOrganizationTreeEditor()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/Tests/BasicOrganizationTreeEditor"));
	}

	@Test
	@TestOrder(9)
	public void test8UMLPackageDiagram() {
		log("test8UMLPackageDiagram()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/UML/PackageDiagram.owl"));
	}

	@Test
	@TestOrder(10)
	public void test9UMLUCDiagram() {
		log("test9UMLUCDiagram()");
		assertViewPointIsValid(testLoadViewPoint("http://www.agilebirds.com/openflexo/ViewPoints/UML/UseCaseDiagram.owl"));
	}
}
