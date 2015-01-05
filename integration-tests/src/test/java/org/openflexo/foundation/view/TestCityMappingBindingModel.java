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
package org.openflexo.foundation.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.antar.binding.BindingModel;
import org.openflexo.antar.binding.BindingVariable;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.SynchronizationScheme;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.fml.ViewType;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.VirtualModelInstanceType;
import org.openflexo.foundation.fml.binding.EditionActionBindingModel;
import org.openflexo.foundation.fml.binding.FlexoBehaviourBindingModel;
import org.openflexo.foundation.fml.binding.ViewPointBindingModel;
import org.openflexo.foundation.fml.binding.VirtualModelBindingModel;
import org.openflexo.foundation.fml.controlgraph.ConditionalAction;
import org.openflexo.foundation.fml.controlgraph.FetchRequestIterationAction;
import org.openflexo.foundation.fml.editionaction.FetchRequestCondition;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.foundation.fml.rt.editionaction.MatchFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.MatchingCriteria;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.technologyadapter.emf.fml.EMFObjectIndividualRole;
import org.openflexo.technologyadapter.emf.fml.editionaction.SelectEMFObjectIndividual;
import org.openflexo.technologyadapter.emf.model.EMFModel;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

@RunWith(OrderedRunner.class)
public class TestCityMappingBindingModel extends OpenflexoProjectAtRunTimeTestCase {

	private static FlexoEditor editor;
	private static ViewPoint cityMappingVP;
	private static SynchronizationScheme syncScheme;

	/**
	 * Instantiate test resource center
	 */
	@Test
	@TestOrder(1)
	public void instantiateResourceCenter() {

		log("test0InstantiateResourceCenter()");

		instanciateTestServiceManager();
	}

	@Test
	@TestOrder(2)
	public void loadViewPoint() {
		String viewPointURI = "http://www.thalesgroup.com/openflexo/emf/CityMapping";
		log("Testing ViewPoint loading: " + viewPointURI);

		System.out.println("resourceCenter=" + resourceCenter);
		System.out.println("resourceCenter.getViewPointRepository()=" + resourceCenter.getViewPointRepository());

		ViewPointResource vpRes = resourceCenter.getViewPointRepository().getResource(viewPointURI);

		assertNotNull(vpRes);
		assertFalse(vpRes.isLoaded());

		ViewPoint vp = vpRes.getViewPoint();
		assertTrue(vpRes.isLoaded());
		cityMappingVP = vp;

		VirtualModel cityMapping = vp.getVirtualModelNamed("CityMapping");
		assertNotNull(cityMapping);

	}

	@Test
	@TestOrder(3)
	public void checkSynchronizationScheme() {
		VirtualModel cityMapping = cityMappingVP.getVirtualModelNamed("CityMapping");
		assertNotNull(cityMapping);

		syncScheme = cityMapping.getSynchronizationScheme();
		assertNotNull(syncScheme);

		System.out.println("FML=" + syncScheme.getFMLRepresentation());

		assertEquals(8, syncScheme.getBindingModel().getBindingVariablesCount());
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed(ViewPointBindingModel.REFLEXIVE_ACCESS_PROPERTY));
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed(VirtualModelBindingModel.REFLEXIVE_ACCESS_PROPERTY));
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed(VirtualModelBindingModel.VIEW_PROPERTY));
		assertEquals(ViewType.getViewType(cityMappingVP),
				syncScheme.getBindingModel().bindingVariableNamed(VirtualModelBindingModel.VIEW_PROPERTY).getType());
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed(VirtualModelBindingModel.VIRTUAL_MODEL_INSTANCE_PROPERTY));
		assertEquals(VirtualModelInstanceType.getFlexoConceptInstanceType(cityMapping),
				syncScheme.getBindingModel().bindingVariableNamed(VirtualModelBindingModel.VIRTUAL_MODEL_INSTANCE_PROPERTY).getType());
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed("model1"));
		assertEquals(EMFModel.class, syncScheme.getBindingModel().bindingVariableNamed("model1").getType());
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed("model2"));
		assertEquals(EMFModel.class, syncScheme.getBindingModel().bindingVariableNamed("model2").getType());
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed(FlexoBehaviourBindingModel.PARAMETERS_PROPERTY));
		assertNotNull(syncScheme.getBindingModel().bindingVariableNamed(FlexoBehaviourBindingModel.PARAMETERS_DEFINITION_PROPERTY));

	}

	@Test
	@TestOrder(4)
	public void checkFetchRequestIteration() {

		FetchRequestIterationAction frIterationAction = (FetchRequestIterationAction) syncScheme.getActions().get(0);
		assertNotNull(frIterationAction);

		assertEquals(8, frIterationAction.getBindingModel().getBindingVariablesCount());

		assertEquals(12, frIterationAction.getInferedBindingModel().getBindingVariablesCount());
		assertNotNull(frIterationAction.getInferedBindingModel().bindingVariableNamed("city1"));
		assertNotNull(frIterationAction.getInferedBindingModel().bindingVariableNamed("matchingCitiesInModel2"));
		assertNotNull(frIterationAction.getInferedBindingModel().bindingVariableNamed("tempList"));
		assertNotNull(frIterationAction.getInferedBindingModel().bindingVariableNamed("currentCity"));

		SelectEMFObjectIndividual fetchRequest1 = (SelectEMFObjectIndividual) frIterationAction.getFetchRequest();
		assertNotNull(fetchRequest1);

		SelectEMFObjectIndividual fetchRequest2 = (SelectEMFObjectIndividual) frIterationAction.getActions().get(0);
		assertNotNull(fetchRequest2);

		assertEquals(12, fetchRequest2.getBindingModel().getBindingVariablesCount());

		for (FetchRequestCondition c : fetchRequest2.getConditions()) {
			System.out.println("condition: " + c.getCondition() + " bm=" + c.getBindingModel());
			assertEquals(13, c.getBindingModel().getBindingVariablesCount());
			assertNotNull(c.getBindingModel().bindingVariableNamed(FetchRequestCondition.SELECTED));
			assertTrue(c.getCondition().isValid());
		}

	}

	@Test
	@TestOrder(4)
	public void checkMatchFlexoConceptInstance() {

		FetchRequestIterationAction frIterationAction = (FetchRequestIterationAction) syncScheme.getActions().get(0);
		assertNotNull(frIterationAction);

		ConditionalAction conditional = (ConditionalAction) frIterationAction.getActions().get(2);
		assertNotNull(conditional);

		MatchFlexoConceptInstance matchAction = (MatchFlexoConceptInstance) conditional.getActions().get(0);
		assertNotNull(matchAction);
		assertTrue(matchAction.getBindingModel() instanceof EditionActionBindingModel);

		for (MatchingCriteria criteria : matchAction.getMatchingCriterias()) {
			// System.out.println("> Criteria: " + criteria.getValue());
			assertEquals(12, criteria.getBindingModel().getBindingVariablesCount());
			assertTrue(criteria.getValue().isValid());
			assertNotNull(criteria.getBindingModel().bindingVariableNamed("matchingCitiesInModel2"));
		}

	}

	@Test
	@TestOrder(5)
	public void checkConceptAndRolesBindingModel() {

		VirtualModel cityMapping = cityMappingVP.getVirtualModelNamed("CityMapping");

		FlexoConcept fc = cityMapping.getFlexoConcept("City");

		FlexoRole<?> fcRole = fc.getFlexoRole("cityInModel1");

		BindingModel fcRoleBindingModel = fcRole.getBindingModel();
		int nbBindVar = fcRoleBindingModel.getBindingVariablesCount();

		System.out.println(" \n\n***********************************");

		System.out.println(" \n****Binding Model");

		System.out.println(fcRoleBindingModel);

		System.out.println(" \n****Binding variables");

		for (int i = 0; i < nbBindVar; i++) {
			BindingVariable v = fcRoleBindingModel.getBindingVariableAt(i);
			System.out.println("BVar : " + v.getVariableName() + "[ " + v.getType().toString() + "]");
		}

		if (fcRole instanceof EMFObjectIndividualRole) {
			EMFObjectIndividualRole emfRole = (EMFObjectIndividualRole) fcRole;

			IFlexoOntologyClass aType = emfRole.getOntologicType();
		}

	}

	@Test
	@TestOrder(10)
	public void checkViewPointValidity() {
		assertViewPointIsValid(cityMappingVP);
	}

}
