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

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.foundation.action.AddRepositoryFolder;
import org.openflexo.foundation.resource.FileSystemBasedResourceCenter;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.technologyadapter.FlexoModelResource;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.technologyadapter.TypeAwareModelSlotInstanceConfiguration;
import org.openflexo.foundation.view.action.CreateBasicVirtualModelInstance;
import org.openflexo.foundation.view.action.CreateView;
import org.openflexo.foundation.view.action.ModelSlotInstanceConfiguration.DefaultModelSlotInstanceConfigurationOption;
import org.openflexo.foundation.view.rm.ViewResource;
import org.openflexo.foundation.view.rm.VirtualModelInstanceResource;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.foundation.viewpoint.ViewPoint;
import org.openflexo.foundation.viewpoint.VirtualModel;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

@RunWith(OrderedRunner.class)
public class TestCityMappingView extends OpenflexoProjectAtRunTimeTestCase {

	public static FlexoProject project;
	private static FlexoEditor editor;
	private static ViewPoint cityMappingVP;
	private static RepositoryFolder<ViewResource> viewFolder;
	private static View view;

	/*public static void main(String[] args) {

		FlexoLoggingManager.forceInitialize(-1, true, null, Level.INFO, null);

		serviceManager = new DefaultFlexoServiceManager();
		FlexoResourceCenterService rcService = serviceManager.getResourceCenterService();
		FlexoResourceCenter resourceCenter = new DirectoryResourceCenter(new File("/Users/sylvain/Library/OpenFlexo/FlexoResourceCenter"));
		rcService.addToResourceCenters(resourceCenter);

		// Access to CityMapping ViewPoint
		ViewPointResource cityMappingVPRes = resourceCenter.getViewPointRepository().getResource(
				"http://www.thalesgroup.com/openflexo/emf/CityMapping");
		ViewPoint cityMappingVP = cityMappingVPRes.getViewPoint();

		// First define an editor factory: here instantiate the default flexo editor
		FlexoEditorFactory editorFactory = new FlexoEditorFactory() {
			@Override
			public DefaultFlexoEditor makeFlexoEditor(FlexoProject project, FlexoServiceManager serviceManager) {
				return new DefaultFlexoEditor(project, serviceManager);
			}
		};

		// Then define where to create the project
		File projectDirectory = new File("/Users/sylvain/tmp/TestProject.prj");

		// Instantiate an editor using provided directory, factory and service manager
		FlexoEditor editor;
		try {
			editor = FlexoResourceManager.initializeNewProject(projectDirectory, editorFactory, serviceManager);
		} catch (ProjectInitializerException e) {
			e.printStackTrace();
			fail(e.getMessage());
			return;
		}

		// You might now access to your newly created project
		FlexoProject project = editor.getProject();
		System.out.println("Created project " + project);

		// Programmmatically add a repository folder using FlexoAction API
		AddRepositoryFolder addRepositoryFolder = AddRepositoryFolder.actionType.makeNewAction(project.getViewLibrary().getRootFolder(),
				null, editor);
		addRepositoryFolder.setNewFolderName("NewViewFolder");
		addRepositoryFolder.doAction();
		RepositoryFolder<ViewResource> viewFolder = addRepositoryFolder.getNewFolder();

		// Programmmatically create a new view conform to CityMapping viewpoint using FlexoAction API
		CreateView addView = CreateView.actionType.makeNewAction(viewFolder, null, editor);
		addView.newViewName = "TestNewView";
		addView.newViewTitle = "A nice title for a new view";
		addView.viewpointResource = (ViewPointResource) cityMappingVP.getResource();
		addView.doAction();
		View newView = addView.getNewView();
		System.out.println("New view " + newView + " created in " + newView.getResource().getFile());
	}*/

	/**
	 * Instantiate test resource center
	 */
	@Test
	@TestOrder(1)
	public void test0InstantiateResourceCenter() {

		log("test0InstantiateResourceCenter()");

		// TODO: create a project where all those tests don't need a manual import of projects
		// TODO: copy all test VP in tmp dir and work with those VP instead of polling GIT workspace
		instanciateTestServiceManager();
	}

	@Test
	@TestOrder(2)
	public void test1CreateProject() {
		editor = createProject("TestCreateView");
		project = editor.getProject();

		assertNotNull(project.getViewLibrary());
	}

	@Test
	@TestOrder(3)
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
	}

	@Test
	@TestOrder(4)
	public void test2LoadCityMappingViewPoint() {
		assertNotNull(cityMappingVP);
		System.out.println("Found view point in " + ((ViewPointResource) cityMappingVP.getResource()).getFile());
	}

	@Test
	@TestOrder(5)
	public void test3CreateViewFolder() {
		AddRepositoryFolder addRepositoryFolder = AddRepositoryFolder.actionType.makeNewAction(project.getViewLibrary().getRootFolder(),
				null, editor);
		addRepositoryFolder.setNewFolderName("NewViewFolder");
		addRepositoryFolder.doAction();
		assertTrue(addRepositoryFolder.hasActionExecutionSucceeded());
		viewFolder = addRepositoryFolder.getNewFolder();
		assertTrue(viewFolder.getFile().exists());
	}

	@Test
	@TestOrder(6)
	public void test4CreateView() {
		CreateView addView = CreateView.actionType.makeNewAction(viewFolder, null, editor);
		addView.setNewViewName("TestNewView");
		addView.setNewViewTitle("A nice title for a new view");
		addView.setViewpointResource((ViewPointResource) cityMappingVP.getResource());
		addView.doAction();
		assertTrue(addView.hasActionExecutionSucceeded());
		View newView = addView.getNewView();
		System.out.println("New view " + newView + " created in " + ((ViewResource) newView.getResource()).getFile());
		assertNotNull(newView);
		assertEquals(addView.getNewViewName(), newView.getName());
		assertEquals(addView.getNewViewTitle(), newView.getTitle());
		assertEquals(addView.getViewpointResource().getViewPoint(), cityMappingVP);
		assertTrue(((ViewResource) newView.getResource()).getFile().exists());
	}

	@Test
	@TestOrder(7)
	public void test5ReloadProject() {
		editor = reloadProject(project.getProjectDirectory());
		project = editor.getProject();
		assertNotNull(project.getViewLibrary());
		assertEquals(1, project.getViewLibrary().getRootFolder().getChildren().size());
		viewFolder = project.getViewLibrary().getRootFolder().getChildren().get(0);
		assertEquals(1, viewFolder.getResources().size());
		ViewResource viewRes = viewFolder.getResources().get(0);
		assertEquals(viewRes, project.getViewLibrary().getResource(viewRes.getURI()));
		assertNotNull(viewRes);
		assertFalse(viewRes.isLoaded());
		view = viewRes.getView();
		assertTrue(viewRes.isLoaded());
		assertNotNull(view);
		assertEquals(project, ((ViewResource) view.getResource()).getProject());
		assertEquals(project, view.getProject());
	}

	@Test
	@TestOrder(8)
	public void test6CreateVirtualModelInstance() {
		System.out.println("Create virtual model instance, view=" + view + " editor=" + editor);

		CreateBasicVirtualModelInstance createVirtualModelInstance = CreateBasicVirtualModelInstance.actionType.makeNewAction(view, null,
				editor);
		createVirtualModelInstance.setNewVirtualModelInstanceName("TestNewVirtualModel");
		createVirtualModelInstance.setNewVirtualModelInstanceTitle("A nice title for a new virtual model instance");

		VirtualModel cityMappingVM = cityMappingVP.getVirtualModelNamed("CityMapping");
		assertNotNull(cityMappingVM);

		createVirtualModelInstance.setVirtualModel(cityMappingVM);

		ModelSlot emfModelSlot1 = cityMappingVM.getModelSlots().get(0);
		TypeAwareModelSlotInstanceConfiguration emfModelSlotConfiguration1 = (TypeAwareModelSlotInstanceConfiguration) createVirtualModelInstance
				.getModelSlotInstanceConfiguration(emfModelSlot1);
		emfModelSlotConfiguration1.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingModel);
		File modelFile1 = new File(((FileSystemBasedResourceCenter) resourceCenter).getRootDirectory(), "TestResourceCenter/ViewPointsOpenflexo17/EMF/Model/city1/my.city1");
		System.out.println("Searching " + modelFile1.getAbsolutePath());
		assertTrue(modelFile1.exists());
		System.out.println("Searching " + modelFile1.toURI().toString());
		FlexoModelResource<?, ?, ?> modelResource1 = project.getServiceManager().getInformationSpace()
				.getModelWithURI(modelFile1.toURI().toString());
		assertNotNull(modelResource1);
		emfModelSlotConfiguration1.setModelResource(modelResource1);
		assertTrue(emfModelSlotConfiguration1.isValidConfiguration());

		ModelSlot emfModelSlot2 = cityMappingVM.getModelSlots().get(1);
		TypeAwareModelSlotInstanceConfiguration emfModelSlotConfiguration2 = (TypeAwareModelSlotInstanceConfiguration) createVirtualModelInstance
				.getModelSlotInstanceConfiguration(emfModelSlot2);
		emfModelSlotConfiguration2.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingModel);
		File modelFile2 = new File(((FileSystemBasedResourceCenter) resourceCenter).getRootDirectory(), "TestResourceCenter/ViewPointsOpenflexo17/EMF/Model/city2/first.city2");
		System.out.println("Searching " + modelFile2.getAbsolutePath());
		assertTrue(modelFile2.exists());
		System.out.println("Searching " + modelFile2.toURI().toString());
		FlexoModelResource<?, ?, ?> modelResource2 = project.getServiceManager().getInformationSpace()
				.getModelWithURI(modelFile2.toURI().toString());
		assertNotNull(modelResource2);
		emfModelSlotConfiguration2.setModelResource(modelResource2);
		assertTrue(emfModelSlotConfiguration2.isValidConfiguration());

		createVirtualModelInstance.doAction();
		System.out.println("exception thrown=" + createVirtualModelInstance.getThrownException());
		assertTrue(createVirtualModelInstance.hasActionExecutionSucceeded());
		VirtualModelInstance newVirtualModelInstance = createVirtualModelInstance.getNewVirtualModelInstance();
		System.out.println("New VirtualModelInstance " + newVirtualModelInstance + " created in "
				+ ((VirtualModelInstanceResource) newVirtualModelInstance.getResource()).getFile());
		assertNotNull(newVirtualModelInstance);
		assertEquals(createVirtualModelInstance.getNewVirtualModelInstanceName(), newVirtualModelInstance.getName());
		assertEquals(createVirtualModelInstance.getNewVirtualModelInstanceTitle(), newVirtualModelInstance.getTitle());
		assertEquals(createVirtualModelInstance.getVirtualModel(), cityMappingVM);
		assertTrue(((VirtualModelInstanceResource) newVirtualModelInstance.getResource()).getFile().exists());
		assertEquals(project, ((VirtualModelInstanceResource) newVirtualModelInstance.getResource()).getProject());
		assertEquals(project, newVirtualModelInstance.getProject());

		FlexoConcept cityEP = cityMappingVM.getFlexoConcept("City");
		FlexoConcept houseEP = cityMappingVM.getFlexoConcept("House");
		FlexoConcept appartmentEP = cityMappingVM.getFlexoConcept("Appartment");
		FlexoConcept mansionEP = cityMappingVM.getFlexoConcept("Mansion");
		FlexoConcept residentEP = cityMappingVM.getFlexoConcept("Resident");

		assertNotNull(cityEP);
		assertNotNull(houseEP);
		assertNotNull(appartmentEP);
		assertNotNull(mansionEP);
		assertNotNull(residentEP);

		
		// temporary removed
	/*	assertEquals(5, newVirtualModelInstance.getFlexoConceptInstances(cityEP).size());
		assertEquals(3, newVirtualModelInstance.getFlexoConceptInstances(houseEP).size());
		assertEquals(2, newVirtualModelInstance.getFlexoConceptInstances(appartmentEP).size());
		assertEquals(1, newVirtualModelInstance.getFlexoConceptInstances(mansionEP).size());
		assertEquals(3, newVirtualModelInstance.getFlexoConceptInstances(residentEP).size());*/
		
	}
}
