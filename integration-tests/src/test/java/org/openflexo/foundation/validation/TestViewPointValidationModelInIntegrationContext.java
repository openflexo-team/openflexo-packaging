package org.openflexo.foundation.validation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.fge.ShapeGraphicalRepresentation.ShapeBorder;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.viewpoint.ViewPointValidationModel;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.validation.ValidationRule;
import org.openflexo.model.validation.ValidationRuleSet;
import org.openflexo.technologyadapter.diagram.fml.OverridingGraphicalRepresentation.ConnectorOverridingGraphicalRepresentation;
import org.openflexo.technologyadapter.diagram.fml.OverridingGraphicalRepresentation.ShapeOverridingGraphicalRepresentation;
import org.openflexo.technologyadapter.diagram.fml.editionaction.AddConnector;
import org.openflexo.technologyadapter.diagram.fml.editionaction.AddShape;
import org.openflexo.technologyadapter.diagram.fml.editionaction.GraphicalAction;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * This unit test is intented to test ViewPoint validation model in integration context (all technology adapters are loaded)
 * 
 * @author sylvain
 * 
 */
@RunWith(OrderedRunner.class)
public class TestViewPointValidationModelInIntegrationContext extends OpenflexoTestCase {

	static ViewPointValidationModel validationModel;

	@Test
	@TestOrder(1)
	public void testCreateViewPointValidationModel() throws ModelDefinitionException {

		instanciateTestServiceManager();
		validationModel = new ViewPointValidationModel(serviceManager.getTechnologyAdapterService());
		System.out.println("class number= " + validationModel.getValidationModelFactory().getModelContext().getEntityCount());

		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ViewPoint.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.ForegroundStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.GraphicalElementSpecification.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.emf.viewpoint.editionaction.SelectEMFObjectIndividual.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AddFlexoConceptInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.SelectFlexoConceptInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.SelectIndividual.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.CellStyleAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.metamodel.DiagramPaletteObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.DeleteFlexoConceptInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Star.class) != null);
		assertTrue(validationModel
				.getValidationModelFactory()
				.getModelContext()
				.getModelEntity(
						org.openflexo.technologyadapter.emf.viewpoint.editionaction.AddEMFObjectIndividualAttributeObjectPropertyValue.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.IntegerParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.LocalizedEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.ContainerGraphicalRepresentation.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.ColorGradientBackgroundStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.OverridingGraphicalRepresentation.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.AbstractCreationScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.LocalizedDictionary.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.TextAreaParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.FGEStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.SemanticsExcelModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AddToListAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.ClassInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AddFlexoConceptInstanceParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.VirtualModel.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.PrimitiveActorReference.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.editionaction.AddDiagramElementAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.connectors.CurveConnectorSpecification.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.URIParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.CheckboxParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.IndividualParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Square.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.editionaction.XMLDataPropertyAssertion.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.NoneBackgroundStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.DataPropertyInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.TechnologySpecificFlexoBehaviour.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.IntegerInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.DiagramRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Polygon.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.AddExcelSheet.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Arc.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.SelectExcelRow.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoConceptInstanceParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoBehaviourParameters.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.FlexoProperty.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.FreeDiagramModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.AddExcelCell.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.editionaction.AddStatement.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.ShapeRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.SelectExcelCell.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.shapes.ShapeSpecification.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Triangle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.OWLClassRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.FMLDiagramPaletteElementBinding.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.MatchingCriteria.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoBehaviourParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.GraphicalRepresentation.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.DeleteFlexoConceptInstanceParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.RemoveFromListAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.AddExcelRow.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoBehaviourObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.shapes.RectangularOctogon.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.editionaction.AddShape.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoConceptObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoConceptStructuralFacet.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.metamodel.XMLDataProperty.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.ConnectorRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.ObjectPropertyStatementRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.ShapeGraphicalRepresentation.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.editionaction.AddDiagram.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.FlexoConceptInspector.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.emf.viewpoint.EMFClassClassRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.OWLDataPropertyRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.BusinessConceptInstanceRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.editionaction.GetXMLDocumentRoot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.EditionAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.ObjectPropertyInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.TextAreaInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.technologyadapter.FreeModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.FreeXMLURIProcessor.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.editionaction.AddConnector.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.OWLObjectPropertyRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.editionaction.AddOWLIndividual.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.editionaction.SetXMLDocumentRoot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.virtualmodel.XMLTypeRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.metamodel.XMLMetaModel.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.FMLDiagramPaletteElementBindingParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.OWLModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.connectors.ConnectorSpecification.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.SelectExcelSheet.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ObjectPropertyParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FloatParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.OntologicObjectRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.emf.viewpoint.EMFEnumClassRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.TextureBackgroundStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.SynchronizationScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.FetchRequestCondition.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Chevron.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.FlexoConceptInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.connectors.RectPolylinConnectorSpecification.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.InnerModelSlotParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.VirtualModelModelSlotInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.XMLMetaModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.TextFieldInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.IndividualRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.ExecutionAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.shapes.ComplexCurve.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(ShapeBorder.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.shapes.RegularPolygon.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.VirtualModelModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.GraphicalElementRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.TextStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.ConditionalAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.NamedViewPointObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.XMLModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.CreateFlexoConceptInstanceParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.ControlStructureAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.AbstractActionScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.editionaction.GraphicalAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.ProcedureAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.editionaction.AddRestrictionStatement.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.ViewObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.emf.viewpoint.editionaction.AddEMFObjectIndividual.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.DataPropertyParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.emf.viewpoint.EMFObjectIndividualRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ViewPointObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.metamodel.XMLObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ListParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.PropertyRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.MatchFlexoConceptInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Losange.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.OWLIndividualRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.PrimitiveRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.BackgroundStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.virtualmodel.XMLActorReference.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.DiagramNavigationScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.viewpoint.editionaction.AddPowerpointSlide.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.DiagramFlexoBehaviour.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.TechnologyObjectParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.LinkScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.editionaction.DiagramAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.VirtualModelInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AddConcept.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.viewpoint.PowerpointSlideRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.BasicPowerpointModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.ExcelColumnRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.editionaction.AddDataPropertyStatement.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoConceptInstanceRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.DeclareFlexoRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ActionContainer.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.foundation.view.View.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.metamodel.XMLProperty.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.FreeXMLModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.FetchRequest.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(ConnectorOverridingGraphicalRepresentation.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.ExcelCellRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.emf.viewpoint.editionaction.SetEMFPropertyValue.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.emf.EMFModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AddClass.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.PropertyInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.FloatInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoConceptConstraint.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoConceptBehaviouralFacet.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.viewpoint.PowerpointShapeRole.class) != null);
		assertTrue(validationModel
				.getValidationModelFactory()
				.getModelContext()
				.getModelEntity(
						org.openflexo.technologyadapter.emf.viewpoint.editionaction.AddEMFObjectIndividualAttributeDataPropertyValue.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.CheckboxInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.editionaction.AddXMLIndividual.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.TextFieldParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.ActorReference.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.GraphicalElementAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoBehaviour.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.FreeModelSlotInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(ShapeOverridingGraphicalRepresentation.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.editionaction.AddXMLType.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoConcept.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ObjectPropertyRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.viewpoint.editionaction.AddPowerpointShape.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.editionaction.AddObjectPropertyStatement.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.viewpoint.editionaction.SelectPowerpointShape.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.BasicExcelModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.PowerpointModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.StatementRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ActionScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.ExcelRowRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.BusinessConceptTypeRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.fml.DropScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Circle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Plus.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.ConceptActorReference.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Rectangle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.virtualmodel.XMLIndividualRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.metamodel.XMLType.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.ColorBackgroundStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.editionaction.AddBusinessConceptInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.TypeAwareModelSlotInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AssignableAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.ModelSlotInstance.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.SubClassStatementRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.metamodel.DiagramPaletteElement.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.editionaction.AddSubClassStatement.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.IndividualInspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.DataPropertyAssertion.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AbstractAssertion.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.shapes.Oval.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.VirtualModelInstanceObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.connectors.CurvedPolylinConnectorSpecification.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.resource.ResourceData.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.DataPropertyRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.DeleteAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.foundation.FlexoObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ClassRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.AbstractXMLModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.AbstractXMLURIProcessor.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AddIndividual.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.BackgroundImageBackgroundStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext().getModelEntity(org.openflexo.fge.ShadowStyle.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.ClassParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.PropertyParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.view.ModelObjectActorReference.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.xml.XMLURIProcessor.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.CreationScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.technologyadapter.ModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.DeletionScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.TypedDiagramModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.AssignationAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.CloningScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.editionaction.AddOWLClass.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.technologyadapter.TypeAwareModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.DropDownParameter.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.IterationAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.NavigationScheme.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.ConnectorGraphicalRepresentation.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.fge.connectors.LineConnectorSpecification.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.diagram.DiagramModelSlot.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.FlexoRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.FetchRequestIterationAction.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.inspector.InspectorEntry.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.DataPropertyStatementRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.owl.viewpoint.OWLPropertyRole.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.viewpoint.editionaction.ObjectPropertyAssertion.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.powerpoint.viewpoint.editionaction.SelectPowerpointSlide.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.foundation.FlexoProjectObject.class) != null);
		assertTrue(validationModel.getValidationModelFactory().getModelContext()
				.getModelEntity(org.openflexo.technologyadapter.excel.viewpoint.ExcelSheetRole.class) != null);

		for (int i = 0; i < validationModel.getSortedClasses().size(); i++) {
			System.out.println("> " + validationModel.getSortedClasses().get(i));
		}

	}

	@Test
	@TestOrder(2)
	public void testAddShapeValidationRules() throws ModelDefinitionException {
		ValidationRuleSet<?> ruleSet = validationModel.getRuleSet(AddShape.class);
		assertNotNull(ruleSet);
		System.out.println("ruleSet=" + ruleSet + " size=" + ruleSet.getRulesCount());
		for (ValidationRule r : ruleSet.getRules()) {
			System.out.println("> " + r);
		}
		assertTrue(ruleSet.containsRuleClass(AddShape.AddShapeActionMustAdressAValidShapeRole.class));
		assertTrue(ruleSet.containsRuleClass(AddShape.AddShapeActionMustHaveAValidContainer.class));
	}

	@Test
	@TestOrder(3)
	public void testAddConnectorValidationRules() throws ModelDefinitionException {
		ValidationRuleSet<?> ruleSet = validationModel.getRuleSet(AddConnector.class);
		assertNotNull(ruleSet);
		System.out.println("ruleSet=" + ruleSet + " size=" + ruleSet.getRulesCount());
		for (ValidationRule r : ruleSet.getRules()) {
			System.out.println("> " + r);
		}
		assertTrue(ruleSet.containsRuleClass(AddConnector.AddConnectorActionMustAdressAValidConnectorRole.class));
		assertTrue(ruleSet.containsRuleClass(AddConnector.AddConnectorActionMustHaveAValidStartingShape.class));
		assertTrue(ruleSet.containsRuleClass(AddConnector.AddConnectorActionMustHaveAValidEndingShape.class));
	}

	@Test
	@TestOrder(4)
	public void testGraphicalActionValidationRules() throws ModelDefinitionException {
		ValidationRuleSet<?> ruleSet = validationModel.getRuleSet(GraphicalAction.class);
		assertNotNull(ruleSet);
		System.out.println("ruleSet=" + ruleSet + " size=" + ruleSet.getRulesCount());
		for (ValidationRule r : ruleSet.getRules()) {
			System.out.println("> " + r);
		}
		assertTrue(ruleSet.containsRuleClass(GraphicalAction.GraphicalActionMustHaveASubject.class));
		assertTrue(ruleSet.containsRuleClass(GraphicalAction.GraphicalActionMustDefineAValue.class));
	}

}
