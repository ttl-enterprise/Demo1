/*   emxPart.
 **
 **   Copyright (c) 2002-2011 Dassault Systemes.
 **   All Rights Reserved.
 **   This program contains proprietary and trade secret information of MatrixOne,
 **   Inc.  Copyright notice is precautionary only
 **   and does not evidence any actual or intended publication of such program.
 **
 **   This JPO contains the code for the "Part" business type.
 **
 **   static const char RCSID[] = $Id: emxPart.java.rca 1.10 Wed Oct 22 16:25:43 2008 przemek Experimental przemek przemek $
 * Modification Details:
 * 
 * 01-Feb-18 | skovvuru  | 16331 | Modified the method setRPNValue. Added new method setRARevisedRPNValue
 * 27-Apr-18 | skovvuru  | CR106 | Modified the method runCsvImport
 */

import matrix.db.*;
import matrix.util.*;

import java.net.URLEncoder;

import java.io.*;
import java.util.*;
import java.text.*;
import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import com.matrixone.apps.engineering.EngineeringConstants;
import com.matrixone.apps.framework.ui.*;
import com.matrixone.apps.common.Company;
import com.matrixone.apps.common.Search;
import com.matrixone.apps.engineering.*;
import com.matrixone.apps.framework.ui.*;
import com.exalead.search.query.SearchParameters.Type;
import com.javadocx.*;
import com.matrixone.apps.framework.taglib.*;
import com.matrixone.apps.program.ProgramCentralConstants;
import com.matrixone.apps.program.ProgramCentralUtil;

//XML Report

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.axis.client.HappyClient;
import org.jaxen.function.StringLengthFunction;
import org.owasp.esapi.waf.actions.DoNothingAction;

//import org.hamcrest.core.IsInstanceOf;

import com.ds.fmea.xsd.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


import au.com.bytecode.opencsv.CSVReader;



//XML Report

/**
 * The <code>emxPart</code> class contains code for the "Part" business type.
 * 
 * @version EC 9.5.JCI.0 - Copyright (c) 2002, MatrixOne, Inc.
 */
public class emxFMEABase_mxJPO extends emxPart_mxJPO {


	//CSV import :START
	// private static String csvFilePath = "D:\\FMEA\\WS_openCSV\\WS_openCSV\\testTest1.csv";
	//"D:\\test.csv";
	public static final int COLUMN_Operation	=1;
	public static final int COLUMN_Station	=2;
	public static final int COLUMN_Process_Flow_Symbol	=3;
	public static final int COLUMN_Verb_Noun	=4;
	public static final int COLUMN_Measurable	=5;
	public static final int COLUMN_FM_No	=6;
	public static final int COLUMN_Mode_Type	=7;
	public static final int COLUMN_Failure_Mode	=8;
	public static final int COLUMN_Effect	=9;
	public static final int COLUMN_Severity	=10;
	public static final int COLUMN_Manufacturing_or_Assembly_Effect_Only=11;
	public static final int COLUMN_CLASS	=12;
	public static final int COLUMN_FC_No	=13;
	public static final int COLUMN_Cause_of_Failure	=14;		
	public static final int COLUMN_Quality_History_Data_Source=15;
	public static final int COLUMN_Prevention_Controls	=16;
	public static final int COLUMN_Occurrence	=17;
	public static final int COLUMN_PreDJ_Detection_Control	=18;
	public static final int COLUMN_PostDJ_Detection_Control	=19;
	public static final int COLUMN_Detection	=20;
	public static final int COLUMN_RPN	=21;
	public static final int COLUMN_Recommended_Action	=22;
	public static final int COLUMN_Responsibility	=23;
	public static final int COLUMN_Target_End_Date	=24;
	public static final int COLUMN_Action_Taken	=25;
	public static final int COLUMN_Revised_Severity	=26;
	public static final int COLUMN_Revised_Occurrence	=27;
	public static final int COLUMN_Revised_Detection	=28;
	public static final int COLUMN_Revised_RPN	=29;
	public static final int COLUMN_Completion_Date	=30;
	public static final int COLUMN_Action_Complete=31;



	//CSV import:END
	//XML Report
	private static FMEASheet fmeas = new FMEASheet();
	//private static String xmlFilePath = "E:\\FMEA\\fmeaExp1.xml";
	//XML Report
	//constants....
	//TYPES
	public static final String TYPE_OPERATION=PropertyUtil.getSchemaProperty("type_Operation");
	public static final String TYPE_FAILURE_MODE=PropertyUtil.getSchemaProperty("type_FailureMode");
	public static final String TYPE_FAILURE_MODE_EFFECT=PropertyUtil.getSchemaProperty("type_FailureModeEffect");
	public static final String TYPE_FAILURE_MODE_CAUSE=PropertyUtil.getSchemaProperty("type_FailureModeCause");
	public static final String TYPE_FMEA_RPN=PropertyUtil.getSchemaProperty("type_FMEARPN");
	public static final String TYPE_FMEA=PropertyUtil.getSchemaProperty("type_FMEA");
	public static final String TYPE_CONTROL_PLAN=PropertyUtil.getSchemaProperty("type_ControlPlan");
	public static final String TYPE_FUNCTION=PropertyUtil.getSchemaProperty("type_Function");
	public static final String TYPE_RECOMMENDED_ACTION=PropertyUtil.getSchemaProperty("type_RecommendedAction");


	//ATTRIBUTES	  
	public  static final String ATTRIBUTE_OPERATION_NUMBER=PropertyUtil.getSchemaProperty("attribute_OperationNumber");	  
	public static final String  ATTRIBUTE_FM_MEASURABLE=PropertyUtil.getSchemaProperty("attribute_FMMeasurable");	  
	public static final String  ATTRIBUTE_OPERATION_STATUS=PropertyUtil.getSchemaProperty("attribute_OperationStatus"); 
	public  static final String  ATTRIBUTE_FMEA_TYPE=PropertyUtil.getSchemaProperty("attribute_FMEAType");
	public static final String  ATTRIBUTE_KEY_DATE=PropertyUtil.getSchemaProperty("attribute_KeyDate"); 
	public  static final String  ATTRIBUTE_KEY_GATEWAY=PropertyUtil.getSchemaProperty("attribute_KeyGateway");
	public static final String  ATTRIBUTE_RESPONSIBLE_PERSON=PropertyUtil.getSchemaProperty("attribute_ResponsiblePerson");
	public static final String  ATTRIBUTE_RESPONSIBLE_TEAM=PropertyUtil.getSchemaProperty("attribute_ResponsibleTeam");
	public static final String  ATTRIBUTE_SUBSYSTEM=PropertyUtil.getSchemaProperty("attribute_Subsystem");
	public static final String  ATTRIBUTE_SYSTEM=PropertyUtil.getSchemaProperty("attribute_System");
	public  static final String  ATTRIBUTE_VEHICHLE_PROGRAM=PropertyUtil.getSchemaProperty("attribute_VehicleProgram");
	public  static final String  ATTRIBUTE_CLASS=PropertyUtil.getSchemaProperty("attribute_Class");
	public static final String  ATTRIBUTE_COMPLETION_DATE=PropertyUtil.getSchemaProperty("attribute_CompletionDate");
	public static final String  ATTRIBUTE_CRITICAL_CHARACTERISTIC_VALUE=PropertyUtil.getSchemaProperty("attribute_CriticalCharacteristicValue");
	public static final String  ATTRIBUTE_CRITICAL_CHARACTERISTIC=PropertyUtil.getSchemaProperty("attribute_CriticalCharacteristic");
	public static final String  ATTRIBUTE_OPERATION_NAME=PropertyUtil.getSchemaProperty("attribute_OperationName");
	public static final String  ATTRIBUTE_DETECTION=PropertyUtil.getSchemaProperty("attribute_Detection");	  
	public static final String  ATTRIBUTE_FAILURE_MODE=PropertyUtil.getSchemaProperty("attribute_FailureMode");	 
	public static final String  ATTRIBUTE_FM_SEVERITY=PropertyUtil.getSchemaProperty("attribute_FMSeverity");
	public static final String  ATTRIBUTE_MODE_TYPE=PropertyUtil.getSchemaProperty("attribute_ModeType");
	public static final String  ATTRIBUTE_OCCURENCE=PropertyUtil.getSchemaProperty("attribute_Occurence");
	public  static final String  ATTRIBUTE_POTENTIAL_FAILURE_CAUSES=PropertyUtil.getSchemaProperty("attribute_PotentialFailureCauses");	  
	public static final String  ATTRIBUTE_POTENTIAL_FAILURE_DETAILS=PropertyUtil.getSchemaProperty("attribute_PotentialFailureDetails");
	public static final String  ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS=PropertyUtil.getSchemaProperty("attribute_PotentialFailureEffects");
	public static final String  ATTRIBUTE_RECOMMENDED_ACTION=PropertyUtil.getSchemaProperty("attribute_RecommendedAction");
	public static final String  ATTRIBUTE_RPN=PropertyUtil.getSchemaProperty("attribute_RPN");
	public static final String  ATTRIBUTE_STATUS=PropertyUtil.getSchemaProperty("attribute_Status");
	public static final String  ATTRIBUTE_CONTROL_PLAN_NUMBER=PropertyUtil.getSchemaProperty("attribute_ControlPlanNumber");
	public static final String  ATTRIBUTE_CONTROL_METHOD=PropertyUtil.getSchemaProperty("attribute_ControlMethod");
	public static final String  ATTRIBUTE_CORRECTIVE_ACTION=PropertyUtil.getSchemaProperty("attribute_CorrectiveAction");	 
	public static final String  ATTRIBUTE_NO=PropertyUtil.getSchemaProperty("attribute_No");
	public static final String  ATTRIBUTE_PROCESS=PropertyUtil.getSchemaProperty("attribute_Process");
	public  static final String  ATTRIBUTE_PROCESS_NAME=PropertyUtil.getSchemaProperty("attribute_ProcessName");
	public  static final String  ATTRIBUTE_PRODUCT=PropertyUtil.getSchemaProperty("attribute_Product");
	public static final String  ATTRIBUTE_REACTION_PLAN=PropertyUtil.getSchemaProperty("attribute_ReactionPlan");
	public static final String  ATTRIBUTE_SAMPLE_SIZE=PropertyUtil.getSchemaProperty("attribute_SampleSize");  
	public static final String  ATTRIBUTE_SAMPLING_FREQUENCY=PropertyUtil.getSchemaProperty("attribute_SamplingFrequency");
	public static final String  ATTRIBUTE_SPECIAL_CHARACTER_CLASS=PropertyUtil.getSchemaProperty("attribute_SpecialCharacterClass");	  
	public  static final String  ATTRIBUTE_TECHNIQUE=PropertyUtil.getSchemaProperty("attribute_Technique");
	public static final String  ATTRIBUTE_TOLERANCE=PropertyUtil.getSchemaProperty("attribute_Tolerance");
	public  static final String  ATTRIBUTE_TOOLS=PropertyUtil.getSchemaProperty("attribute_Tools");
	public static final String  ATTRIBUTE_ACTION_TAKEN=PropertyUtil.getSchemaProperty("attribute_ActionTaken");	 
	public static final String  ATTRIBUTE_RISK_RPN_VALUE=PropertyUtil.getSchemaProperty("attribute_RiskRPNValue");
	public  static final String  ATTRIBUTE_TARGET_END_DATE=PropertyUtil.getSchemaProperty("attribute_TargetEndDate");
	public static final String  ATTRIBUTE_FAILURE_MODE_EFFECT=PropertyUtil.getSchemaProperty("attribute_FailureModeEffect");
	public static final String  ATTRIBUTE_DETECTION_CONTROL=PropertyUtil.getSchemaProperty("attribute_DetectionControls");
	public static final String  ATTRIBUTE_POSTDJ_DETECTION_CONTROL=PropertyUtil.getSchemaProperty("attribute_PostDJDetectionControl");	 
	public static final String  ATTRIBUTE_PREDJ_DETECTION_CONTROL=PropertyUtil.getSchemaProperty("attribute_PreDJDetectionControl");
	public static final String  ATTRIBUTE_PREVENTION_CONTROLS=PropertyUtil.getSchemaProperty("attribute_PreventionControls");
	public static final String  ATTRIBUTE_COMPONENT=PropertyUtil.getSchemaProperty("attribute_Component");
	public static final String  ATTRIBUTE_FMEA_NUMBER=PropertyUtil.getSchemaProperty("attribute_FMEANumber");  
	public static final String  ATTRIBUTE_PROCESS_FLOW_SYMBOL=PropertyUtil.getSchemaProperty("attribute_ProcessFlowSymbol");
	public static final String  ATTRIBUTE_ASSEMBLY_EFFECT=PropertyUtil.getSchemaProperty("attribute_AssemblyEffect");
	public static final String  ATTRIBUTE_STATION=PropertyUtil.getSchemaProperty("attribute_Station");
	public static final String  ATTRIBUTE_CTQ=PropertyUtil.getSchemaProperty("attribute_CTQ");
	public static final String  ATTRIBUTE_ACTION_COMPLETE=PropertyUtil.getSchemaProperty("attribute_ActionComplete");
	public static final String  ATTRIBUTE_FMEA_TEAM=PropertyUtil.getSchemaProperty("attribute_FMEATeam");
	public static final String  ATTRIBUTE_QUALITY_HISTORY=PropertyUtil.getSchemaProperty("attribute_QualityHistory");
	public static final String  ATTRIBUTE_KEY_DATE_OLD=PropertyUtil.getSchemaProperty("attribute_KeyDateOld");
	public static final String  ATTRIBUTE_FM_SEQUENCE_ORDER=PropertyUtil.getSchemaProperty("attribute_FMSequenceOrder");
	public static final String  ATTRIBUTE_FC_SEQUENCE_ORDER=PropertyUtil.getSchemaProperty("attribute_FCSequenceOrder");
	
	//Added for INC-16331 Start
	public static final String  ATTRIBUTE_REVISED_OCCURRENCE=PropertyUtil.getSchemaProperty("attribute_iPLMRARevisedOccurrence");
	public static final String  ATTRIBUTE_REVISED_SEVERITY=PropertyUtil.getSchemaProperty("attribute_iPLMRARevisedSeverity");
	public static final String  ATTRIBUTE_REVISED_DETECTION=PropertyUtil.getSchemaProperty("attribute_iPLMRARevisedDetection");
	public static final String  ATTRIBUTE_REVISED_RPN=PropertyUtil.getSchemaProperty("attribute_iPLMRARevisedRPN");
	//Added for INC-16331 End





	//Attribute SELECT Statements

	public static final String SELECT_ATTRIBUTE_OPERATION_NUMBER="attribute[" + ATTRIBUTE_OPERATION_NUMBER+ "]";	  
	public static final String SELECT_ATTRIBUTE_FM_MEASURABLE="attribute[" + ATTRIBUTE_FM_MEASURABLE+ "]";
	public static final String SELECT_ATTRIBUTE_OPERATION_STATUS="attribute[" + ATTRIBUTE_OPERATION_STATUS+ "]";
	public static final String SELECT_ATTRIBUTE_FMEA_TYPE="attribute[" + ATTRIBUTE_FMEA_TYPE+ "]";
	public static final String SELECT_ATTRIBUTE_KEY_DATE="attribute[" + ATTRIBUTE_KEY_DATE+ "]";
	public static final String SELECT_ATTRIBUTE_KEY_GATEWAY="attribute[" + ATTRIBUTE_KEY_GATEWAY+ "]";
	public static final String SELECT_ATTRIBUTE_RESPONSIBLE_PERSON="attribute[" + ATTRIBUTE_RESPONSIBLE_PERSON+ "]";
	public static final String SELECT_ATTRIBUTE_RESPONSIBLE_TEAM="attribute[" + ATTRIBUTE_RESPONSIBLE_TEAM+ "]";
	public static final String SELECT_ATTRIBUTE_SUBSYSTEM="attribute[" + ATTRIBUTE_SUBSYSTEM+ "]";	  
	public static final String SELECT_ATTRIBUTE_SYSTEM="attribute[" + ATTRIBUTE_SYSTEM+ "]";
	public static final String SELECT_ATTRIBUTE_VEHICHLE_PROGRAM="attribute[" + ATTRIBUTE_VEHICHLE_PROGRAM+ "]";
	public static final String SELECT_ATTRIBUTE_CLASS="attribute[" + ATTRIBUTE_CLASS+ "]";
	public static final String SELECT_ATTRIBUTE_COMPLETION_DATE="attribute[" + ATTRIBUTE_COMPLETION_DATE+ "]";
	public static final String SELECT_ATTRIBUTE_CRITICAL_CHARACTERISTIC_VALUE="attribute[" + ATTRIBUTE_CRITICAL_CHARACTERISTIC_VALUE+ "]";
	public static final String SELECT_ATTRIBUTE_CRITICAL_CHARACTERISTIC="attribute[" + ATTRIBUTE_CRITICAL_CHARACTERISTIC+ "]";
	public static final String SELECT_ATTRIBUTE_OPERATION_NAME="attribute[" + ATTRIBUTE_OPERATION_NAME+ "]";
	public static final String SELECT_ATTRIBUTE_DETECTION="attribute[" + ATTRIBUTE_DETECTION+ "]";	  
	public static final String SELECT_ATTRIBUTE_FAILURE_MODE="attribute[" + ATTRIBUTE_FAILURE_MODE+ "]";
	public static final String SELECT_ATTRIBUTE_FM_SEVERITY="attribute[" + ATTRIBUTE_FM_SEVERITY+ "]";
	public static final String SELECT_ATTRIBUTE_MODE_TYPE="attribute[" + ATTRIBUTE_MODE_TYPE+ "]";	  
	public static final String SELECT_ATTRIBUTE_OCCURENCE="attribute[" + ATTRIBUTE_OCCURENCE+ "]";
	public static final String SELECT_ATTRIBUTE_POTENTIAL_FAILURE_CAUSES="attribute[" + ATTRIBUTE_POTENTIAL_FAILURE_CAUSES+ "]";
	public static final String SELECT_ATTRIBUTE_POTENTIAL_FAILURE_DETAILS="attribute[" + ATTRIBUTE_POTENTIAL_FAILURE_DETAILS+ "]";
	public static final String SELECT_ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS="attribute[" + ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS+ "]";
	public static final String SELECT_ATTRIBUTE_RECOMMENDED_ACTION="attribute[" + ATTRIBUTE_RECOMMENDED_ACTION+ "]";
	public static final String SELECT_ATTRIBUTE_RPN="attribute[" + ATTRIBUTE_RPN+ "]";	  
	public static final String SELECT_ATTRIBUTE_STATUS="attribute[" + ATTRIBUTE_STATUS+ "]";
	public static final String SELECT_ATTRIBUTE_CONTROL_PLAN_NUMBER="attribute[" + ATTRIBUTE_CONTROL_PLAN_NUMBER+ "]";
	public static final String SELECT_ATTRIBUTE_CONTROL_METHOD="attribute[" + ATTRIBUTE_CONTROL_METHOD+ "]";
	public static final String SELECT_ATTRIBUTE_CORRECTIVE_ACTION="attribute[" + ATTRIBUTE_CORRECTIVE_ACTION+ "]";
	public static final String SELECT_ATTRIBUTE_NO="attribute[" + ATTRIBUTE_NO+ "]";
	public static final String SELECT_ATTRIBUTE_PROCESS="attribute[" + ATTRIBUTE_PROCESS+ "]";
	public static final String SELECT_ATTRIBUTE_PROCESS_NAME="attribute[" + ATTRIBUTE_PROCESS_NAME+ "]";
	public static final String SELECT_ATTRIBUTE_PRODUCT="attribute[" + ATTRIBUTE_PRODUCT+ "]";
	public static final String SELECT_ATTRIBUTE_REACTION_PLAN="attribute[" + ATTRIBUTE_REACTION_PLAN+ "]";
	public static final String SELECT_ATTRIBUTE_SAMPLE_SIZE="attribute[" + ATTRIBUTE_SAMPLE_SIZE+ "]";
	public static final String SELECT_ATTRIBUTE_SAMPLING_FREQUENCY="attribute[" + ATTRIBUTE_SAMPLING_FREQUENCY+ "]";
	public static final String SELECT_ATTRIBUTE_SPECIAL_CHARACTER_CLASS="attribute[" + ATTRIBUTE_SPECIAL_CHARACTER_CLASS+ "]";
	public static final String SELECT_ATTRIBUTE_TECHNIQUE="attribute[" + ATTRIBUTE_TECHNIQUE+ "]";
	public static final String SELECT_ATTRIBUTE_TOLERANCE="attribute[" + ATTRIBUTE_TOLERANCE+ "]";
	public static final String SELECT_ATTRIBUTE_TOOLS="attribute[" + ATTRIBUTE_TOOLS+ "]";
	public static final String SELECT_ATTRIBUTE_ACTION_TAKEN="attribute[" + ATTRIBUTE_ACTION_TAKEN+ "]";
	public static final String SELECT_ATTRIBUTE_RISK_RPN_VALUE="attribute[" + ATTRIBUTE_RISK_RPN_VALUE+ "]";
	public static final String SELECT_ATTRIBUTE_TARGET_END_DATE="attribute[" + ATTRIBUTE_TARGET_END_DATE+ "]";	  
	public static final String SELECT_ATTRIBUTE_FAILURE_MODE_EFFECT="attribute[" + ATTRIBUTE_FAILURE_MODE_EFFECT+ "]";	  
	public static final String SELECT_ATTRIBUTE_DETECTION_CONTROL="attribute[" + ATTRIBUTE_DETECTION_CONTROL+ "]";	  
	public static final String SELECT_ATTRIBUTE_POSTDJ_DETECTION_CONTROL="attribute[" + ATTRIBUTE_POSTDJ_DETECTION_CONTROL+ "]";	  
	public static final String SELECT_ATTRIBUTE_PREDJ_DETECTION_CONTROL="attribute[" + ATTRIBUTE_PREDJ_DETECTION_CONTROL+ "]";	  
	public static final String SELECT_ATTRIBUTE_PREVENTION_CONTROLS="attribute[" + ATTRIBUTE_PREVENTION_CONTROLS+ "]";	  
	public static final String SELECT_ATTRIBUTE_FMEA_NUMBER="attribute[" + ATTRIBUTE_FMEA_NUMBER+ "]";	  
	public static final String SELECT_ATTRIBUTE_PROCESS_FLOW_SYMBOL="attribute[" + ATTRIBUTE_PROCESS_FLOW_SYMBOL+ "]";	  
	public static final String SELECT_ATTRIBUTE_ASSEMBLY_EFFECT="attribute[" + ATTRIBUTE_ASSEMBLY_EFFECT+ "]";	  
	public static final String SELECT_ATTRIBUTE_STATION="attribute[" + ATTRIBUTE_STATION+ "]";
	public static final String SELECT_ATTRIBUTE_CTQ="attribute[" + ATTRIBUTE_CTQ + "]";
	public static final String SELECT_ATTRIBUTE_ACTION_COMPLETE="attribute[" + ATTRIBUTE_ACTION_COMPLETE + "]";
	public static final String SELECT_ATTRIBUTE_FMEA_TEAM="attribute[" + ATTRIBUTE_FMEA_TEAM + "]";
	public static final String SELECT_ATTRIBUTE_QUALITY_HISTORY="attribute[" + ATTRIBUTE_QUALITY_HISTORY + "]";
	public static final String SELECT_ATTRIBUTE_KEY_DATE_OLD="attribute[" + ATTRIBUTE_KEY_DATE_OLD + "]";
	public static final String SELECT_ATTRIBUTE_FM_SEQUENCE_ORDER="attribute[" + ATTRIBUTE_FM_SEQUENCE_ORDER + "]";
	public static final String SELECT_ATTRIBUTE_FC_SEQUENCE_ORDER="attribute[" + ATTRIBUTE_FC_SEQUENCE_ORDER + "]";

	//Added for INC-16331 Start
	public static final String SELECT_ATTRIBUTE_REVISED_OCCURRENCE="attribute[" + ATTRIBUTE_REVISED_OCCURRENCE+ "]";	  
	public static final String SELECT_ATTRIBUTE_REVISED_SEVERITY="attribute[" + ATTRIBUTE_REVISED_SEVERITY+ "]";
	public static final String SELECT_ATTRIBUTE_REVISED_DETECTION="attribute[" + ATTRIBUTE_REVISED_DETECTION+ "]";
	public static final String SELECT_ATTRIBUTE_REVISED_RPN="attribute[" + ATTRIBUTE_REVISED_RPN+ "]";
	//Added for INC-16331 End


	//FMEA RELATIONSHIP
	public static final String  RELATIONSHIP_FMEA_POST_WORK_CHECKLIST_ITEM=PropertyUtil.getSchemaProperty("relationship_FMEAPostWorkCheckListItem");
	public static final String  RELATIONSHIP_POST_WORK_CHECKLIST=PropertyUtil.getSchemaProperty("relationship_FMEAPostWorkCheckList");	  
	public  static final String  RELATIONSHIP_FMEA_PROJECT_SPACE=PropertyUtil.getSchemaProperty("relationship_FMEAProjectSpace");
	public static final String  RELATIONSHIP_FMEA_DERIVED_FROM=PropertyUtil.getSchemaProperty("relationship_FMEADerivedFrom");
	public static final String  RELATIONSHIP_FMEA_REQUIREMENT=PropertyUtil.getSchemaProperty("relationship_FMEARequirement");
	public static final String  RELATIONSHIP_CONTROL_PLAN_FAILURE_MODE=PropertyUtil.getSchemaProperty("relationship_ControlPlanFailureMode");
	public static final String  RELATIONSHIP_FAILURE_MODE_EFFECT=PropertyUtil.getSchemaProperty("relationship_FailureModeEffect");
	public  static final String  RELATIONSHIP_OPERATION_FMEA=PropertyUtil.getSchemaProperty("relationship_OperationFMEA");
	public static final String  RELATIONSHIP_OPERATION_FAILURE_MODE=PropertyUtil.getSchemaProperty("relationship_OperationFailureMode");
	public static final String  RELATIONSHIP_FMEA_RESPONSIBLE_PERSON=PropertyUtil.getSchemaProperty("relationship_FMEAResponsiblePerson");
	public static final String  RELATIONSHIP_FAILURE_MODE=PropertyUtil.getSchemaProperty("relationship_FailureMode");
	public static final String  RELATIONSHIP_FMEA_TEAM_PERSONS=PropertyUtil.getSchemaProperty("relationship_FMEATeamPersons");
	public  static final String  RELATIONSHIP_FMEA_AFFECTED_ITEM=PropertyUtil.getSchemaProperty("relationship_FMEAAffectedItem");
	public static final String  RELATIONSHIP_FMEA_CONTROL_PLAN=PropertyUtil.getSchemaProperty("relationship_FMEAControlPlan");
	public static final String  RELATIONSHIP_FAILURE_MODE_CAUSE=PropertyUtil.getSchemaProperty("relationship_FailureModeCause");
	public static final String  RELATIONSHIP_FAILURE_MODE_CAUSE_RPN=PropertyUtil.getSchemaProperty("relationship_FailureModeCauseRPN");	
	public static final String  RELATIONSHIP_FMEA_FUNCTION_REFERENCE=PropertyUtil.getSchemaProperty("relationship_FMEAFunctionReference");
	public static final String  RELATIONSHIP_FMEA_CAUSE_REFERENCE=PropertyUtil.getSchemaProperty("relationship_FMEACauseReference");
	public static final String  RELATIONSHIP_FMEA_OWNER=PropertyUtil.getSchemaProperty("relationship_FMEAOwner");
	public static final String  RELATIONSHIP_REFERENCE_DOCUMENT_CHECKLIST_ITEM=PropertyUtil.getSchemaProperty("relationship_ReferenceDocumentChecklistItem");
	public static final String  RELATIONSHIP_FMEA_PART_STRUCTURE=PropertyUtil.getSchemaProperty("relationship_FMEAPartStructure");
	public static final String  RELATIONSHIP_OPERATION_PROCESS_HEADER=PropertyUtil.getSchemaProperty("relationship_OperationProcessHeader");



	//POLICY	  
	public static final String  POLICY_FMEA			= PropertyUtil.getSchemaProperty("policy_FMEA");
	public static final String  POLICY_FUNCTION			= PropertyUtil.getSchemaProperty("policy_Function");
	public static final String  POLICY_FAILURE_MODE			= PropertyUtil.getSchemaProperty("policy_FailureMode");
	public static final String  POLICY_FAILURE_MODE_EFFECT			= PropertyUtil.getSchemaProperty("policy_FailureModeEffect");
	public static final String  POLICY_FAILURE_MODE_CAUSE			= PropertyUtil.getSchemaProperty("policy_FailureModeCause");
	public static final String  POLICY_RECOMMENDED_ACTION			= PropertyUtil.getSchemaProperty("policy_RecommendedAction");

	//SYMBOLIC TYPE
	public static final String  TYPE_FMEA_SYMBOLIC_NAME="type_FMEA";
	public static final String  TYPE_FUNCTION_SYMBOLIC_NAME="type_Function";
	public static final String  TYPE_FAILURE_MODE_SYMBOLIC_NAME="type_FailureMode";
	public static final String  TYPE_FAILURE_MODE_EFFECT_SYMBOLIC_NAME="type_FailureModeEffect";
	public static final String  TYPE_FAILURE_MODE_CAUSE_SYMBOLIC_NAME="type_FailureModeCause";
	public static final String  TYPE_RECOMMENDED_ACTION_MODE_SYMBOLIC_NAME="type_RecommendedAction";

	//SYMBOLIC POLICY
	public static final String  POLICY_FMEA_SYMBOLIC_NAME			= "policy_FMEA";
	public static final String  POLICY_FUNCTION_SYMBOLIC_NAME			= "policy_Function";
	public static final String  POLICY_FAILURE_MODE_SYMBOLIC_NAME			= "policy_FailureMode";
	public static final String  POLICY_FAILURE_MODE_EFFECT_SYMBOLIC_NAME			= "policy_FailureModeEffect";
	public static final String  POLICY_FAILURE_MODE_CAUSE_SYMBOLIC_NAME			= "policy_FailureModeCause";
	public static final String  POLICY_RECOMMENDED_ACTION_SYMBOLIC_NAME			= "policy_RecommendedAction";

	//FROM.TO.ID SELECT STATEMENTS
	public static final String  FROM_RELATIONSHIP_OPERATION_FMEA_TO_ID			= "from["+RELATIONSHIP_OPERATION_FMEA+"].to.id";
	public static final String  FROM_RELATIONSHIP_FAILURE_MODE_TO_ID		= "from["+RELATIONSHIP_FAILURE_MODE+"].to.id";
	public static final String  FROM_RELATIONSHIP_FAILURE_MODE_EFFECT_TO_ID		= "from["+RELATIONSHIP_FAILURE_MODE_EFFECT+"].to.id";
	public static final String  FROM_RELATIONSHIP_FAILURE_MODE_CAUSE_TO_ID		= "from["+RELATIONSHIP_FAILURE_MODE_CAUSE+"].to.id";
	public static final String  FROM_RELATIONSHIP_FAILURE_MODE_CAUSE_RPN_TO_ID		= "from["+RELATIONSHIP_FAILURE_MODE_CAUSE_RPN+"].to.id";






	public final String ATTRIBUTE_COMPLETED_BY = PropertyUtil
	.getSchemaProperty("attribute_CompletedBy");

	/* Attribute indicates response for the Checklist Item */
	public final String ATTRIBUTE_RESPONSE = PropertyUtil
	.getSchemaProperty("attribute_Response");

	/* Attribute indicates response type for the Checklist Item */
	public final String ATTRIBUTE_RESPONSE_TYPE = PropertyUtil
	.getSchemaProperty("attribute_ResponseType");

	/* Type Checklist Item */
	public final String TYPE_CHECKLIST_ITEM = PropertyUtil
	.getSchemaProperty("type_ChecklistItem");

	/* Type Checklist */
	public final String TYPE_CHECKLIST = PropertyUtil
	.getSchemaProperty("type_Checklist");

	/* Type Checklist Item */
	public final String TYPE_FMEA_CHECKLIST_ITEM = PropertyUtil
	.getSchemaProperty("type_FMEAChecklistItem");

	/* Type Checklist */
	public final String TYPE_FMEA_CHECKLIST = PropertyUtil
	.getSchemaProperty("type_FMEAChecklist");
	/* Relationship between all type and type Checklist */
	public final String RELATIONSHIP_CHECKLIST = PropertyUtil
	.getSchemaProperty("relationship_Checklist");

	/* Relationship between type Checklist and type Checklist */
	public final String RELATIONSHIP_CHECKLIST_ITEM = PropertyUtil
	.getSchemaProperty("relationship_ChecklistItem");
	public final String SELECT_AFFECTED_ITEM = "from[FMEA Affected Item].to.name";
	public final String Unassigned = "Unassigned";

	// FMEA
	/* Type FMEA */

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            the eMatrix <code>Context</code> object.
	 * @param args
	 *            holds no arguments.
	 * @throws Exception
	 *             if the operation fails.
	 * @since EC 9.5.JCI.0.
	 */

		public emxFMEABase_mxJPO(Context context, String[] args) throws Exception {
		super(context, args);
	}

	
/**
 * Q53 Added to get All FMEA
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public static MapList getAllFMEAWorksheet(Context context, String args[])
	throws Exception {
		try {
			// Add the selectables
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement("attribute[FMEA Type]");
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append("&&");
			sbWhere.append("revision==last");

			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, "FMEA", "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

/**
 * Q53 Added to get FMEA Owned by Context User
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public static MapList getFMEAOwnedByUser(Context context, String args[])
	throws Exception {
		try {
			//Added by rneema1 for CR103 : Start
			MapList ownedIdList = new MapList();
	        Map programMap =  JPO.unpackArgs(args);
	        String projectId = (String) programMap.get("objectId");
	        if(UIUtil.isNotNullAndNotEmpty(projectId))
	        {
	        	 StringList busSelects = new StringList();
				 busSelects.add(DomainConstants.SELECT_ID);
		         busSelects.add(DomainConstants.SELECT_OWNER);
		         busSelects.add(DomainConstants.SELECT_TYPE);
				 StringList relSelects = new StringList();
	        	
	        	DomainObject dObjProject = new DomainObject(projectId);
	        	ownedIdList = dObjProject.getRelatedObjects(context,"FMEA Project Space","*", busSelects, relSelects, true, false, (short) 1, DomainObject.EMPTY_STRING, // object where clause
		                 DomainObject.EMPTY_STRING, // relationship where clause
						 0);
	        }
	        else
	        {
	        //Added by rneema1 for CR103 : End
			// Add the selectables
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement("attribute[FMEA Type]");
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			
			//Adding conditions in Where clause to get Specific FMEA with following condition
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("((context.user==owner");
			sbWhere.append(" || ");
			sbWhere.append("context.user==from[FMEA Owner].businessobject.name");
			sbWhere.append(" || ");
			sbWhere.append("context.user==from[FMEA Team Persons].businessobject.name)");
			sbWhere.append("&&");
			sbWhere.append("(revision==last))");
			
			//context.user==owner || context.user==from[FMEA Owner].businessobject.name || context.user==from[FMEA Team Persons].businessobject.name

			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			//Added by rneema1 for CR103 : Start
			ownedIdList = dObj.findObjects(context, TYPE_FMEA, QUERY_WILDCARD, QUERY_WILDCARD,
					QUERY_WILDCARD, QUERY_WILDCARD, sbWhere.toString(), true, objSelects);
			}
			//Added by rneema1 for CR103 : End
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Added by Q53 to get Product Design FMEA Type Data
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getProductDesignFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables
			String fmeaTypeAttribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement(fmeaTypeAttribute);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append(""+fmeaTypeAttribute+"=='Product Design'");
			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Added by Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getProcessFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables

			String fmeaTypeAttribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement(fmeaTypeAttribute);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append(""+fmeaTypeAttribute+"=='Process'");
			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Added by Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */

	public static MapList getConceptDesignFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables

			String fmeaTypeAttribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement(fmeaTypeAttribute);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append(""+fmeaTypeAttribute+"=='Concept Design'");
			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Added by Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getConceptProcessFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables
			String fmeaTypeAttribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement(fmeaTypeAttribute);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append(""+fmeaTypeAttribute+"=='Concept Process'");
			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Added by Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getFoundationDesignFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables
			String fmeaTypeAttribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement(fmeaTypeAttribute);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append(""+fmeaTypeAttribute+"=='Foundation Design'");
			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Added by Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getFoundationProcessFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables
			String fmeaTypeAttribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement(fmeaTypeAttribute);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append(""+fmeaTypeAttribute+"=='Foundation Process'");
			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Added By Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getMachineryFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables
			String fmeaTypeAttribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement(fmeaTypeAttribute);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append(""+fmeaTypeAttribute+"==Machinery");
			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53 Added
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getInWorkFMEAWorksheet(Context context, String args[])
	throws Exception {
		try {
			// Add the selectables

			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement("attribute[FMEA Type]");
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			sbWhere.append(" && ");
			sbWhere.append("(");
			sbWhere.append("current==Draft || current==Review");
			sbWhere.append(")");
			DomainObject dObj = DomainObject.newInstance(context);
			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Added by Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getCompletedFMEAWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables			
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_NAME);
			objSelects.addElement("attribute[FMEA Type]");
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			objSelects.addElement(DomainConstants.SELECT_REVISION);
			objSelects.addElement("last.revision");
			StringBuffer sbWhere = new StringBuffer(64);
			//sbWhere.append("policy!=Version");
			//sbWhere.append(" && ");
			//sbWhere.append("evaluate[revision == revisions.last]");
			//sbWhere.append(" && ");
			//sbWhere.append("revision==last");

			DomainObject dObj = DomainObject.newInstance(context);

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList finalList=new MapList();

			MapList ownedIdList = dObj.findObjects(context, TYPE_FMEA, "*", "*",
					"*", "*", sbWhere.toString(), true, objSelects);
			MapList seperateMaplist=new MapList();
			MapList commonMaplist=new MapList();
			for(int i=0;i<ownedIdList.size();i++)
			{
				HashMap finalMap=new HashMap();  
				Map infoMap=(Map)ownedIdList.get(i);
				String id=(String)infoMap.get(SELECT_ID);
				String name=(String)infoMap.get(SELECT_NAME);
				String revision=(String)infoMap.get(SELECT_REVISION);
				String revisionLast=(String)infoMap.get("last.revision");
				if(revision.equals(revisionLast))
				{
					String currentState=(String)infoMap.get(SELECT_CURRENT);
					if(currentState.equals("Approved"))
					{
						finalList.add(infoMap);

					}else{


						DomainObject domObj=DomainObject.newInstance(context);
						domObj.setId(id);
						BusinessObject lastRevObj = domObj.getPreviousRevision(context);						
						String objectId=lastRevObj.getObjectId();
						if(UIUtil.isNotNullAndNotEmpty(objectId))
						{
							DomainObject domObjLast=DomainObject.newInstance(context);
							domObjLast.setId(objectId);
							String selectState=domObjLast.getInfo(context, SELECT_CURRENT);
							if(selectState.equals("Approved")){
								finalMap.put("id",objectId);
								finalList.add(finalMap); 
							}
						}

					}		
				}

			}
			return finalList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * Get the impact attribute value for Create Form from Risk-RPN relation
	 * 
	 * @param context
	 *            the eMatrix <code>Context</code> object
	 * @return String
	 * @throws Exception
	 *             if the operation fails
	 * @since PMC V6R2008-1
	 */
	public String getOccurance(Context context, String args[]) throws Exception {
		String prob = "";
		try {

			Vector probability = new Vector();
			StringBuffer sb = new StringBuffer();
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			HashMap paramMap = (HashMap) programMap.get("requestMap");
			String objectId = (String) paramMap.get("objectId");
			DomainObject dobj = new DomainObject(objectId);
			// String
			// fmeaType=PropertyUtil.getSchemaProperty("type_FailureMode");
			MapList tempMapList = getTypeAttributes(context, "Failure Mode");
			Iterator itr = tempMapList.iterator();
			StringList impactList = null;
			while (itr.hasNext()) {
				Map map = (Map) itr.next();
				String ocuurance = (String) map.get("name");
				if (ocuurance != null
						&& ocuurance.equalsIgnoreCase("Occurence")) {
					impactList = (StringList) map.get("choices");
				}

			}

			int size = impactList.size();
			sb
			.append("<select name=\"Occurence\" onChange=\"modifyFMEARPN();\">");

			for (int i = 0; i < size; i++) {
				String value = (String) impactList.get(i);
				if (value.equals("5")) {
					sb
					.append("<option value=\"" + value
							+ "\" selected=\"selected\">" + value
							+ "</option>");
				} else {
					sb.append("<option value=\"" + value + "\">" + value
							+ "</option>");
				}
			}
			sb.append("</select>");
			prob = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prob;
	}
/**
 * Q53
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public String getSeverity(Context context, String args[]) throws Exception {
		String prob = "";
		try {

			Vector probability = new Vector();
			StringBuffer sb = new StringBuffer();
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			HashMap paramMap = (HashMap) programMap.get("requestMap");
			String objectId = (String) paramMap.get("objectId");
			DomainObject dobj = new DomainObject(objectId);
			// String
			// fmeaType=PropertyUtil.getSchemaProperty("type_FailureMode");
			MapList tempMapList = getTypeAttributes(context, "Failure Mode");
			Iterator itr = tempMapList.iterator();
			StringList impactList = null;
			while (itr.hasNext()) {
				Map map = (Map) itr.next();
				String ocuurance = (String) map.get("name");
				if (ocuurance != null
						&& ocuurance.equalsIgnoreCase("FM Severity")) {
					impactList = (StringList) map.get("choices");
				}

			}

			int size = impactList.size();
			sb
			.append("<select name=\"Severity\" onChange=\"modifyFMEARPN();\">");

			for (int i = 0; i < size; i++) {
				String value = (String) impactList.get(i);
				if (value.equals("5")) {
					sb
					.append("<option value=\"" + value
							+ "\" selected=\"selected\">" + value
							+ "</option>");
				} else {
					sb.append("<option value=\"" + value + "\">" + value
							+ "</option>");
				}
			}
			sb.append("</select>");
			prob = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prob;
	}
/**
 * Q53
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public String getDetection(Context context, String args[]) throws Exception {
		String prob = "";
		try {

			Vector probability = new Vector();
			StringBuffer sb = new StringBuffer();
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			HashMap paramMap = (HashMap) programMap.get("requestMap");
			String objectId = (String) paramMap.get("objectId");
			DomainObject dobj = new DomainObject(objectId);
			// String
			// fmeaType=PropertyUtil.getSchemaProperty("type_FailureMode");
			MapList tempMapList = getTypeAttributes(context, "Failure Mode");
			Iterator itr = tempMapList.iterator();
			StringList impactList = null;
			while (itr.hasNext()) {
				Map map = (Map) itr.next();
				String ocuurance = (String) map.get("name");
				if (ocuurance != null
						&& ocuurance.equalsIgnoreCase("Detection")) {
					impactList = (StringList) map.get("choices");
				}

			}

			int size = impactList.size();
			sb
			.append("<select name=\"Detection\" onChange=\"modifyFMEARPN();\">");

			for (int i = 0; i < size; i++) {
				String value = (String) impactList.get(i);
				if (value.equals("5")) {
					sb
					.append("<option value=\"" + value
							+ "\" selected=\"selected\">" + value
							+ "</option>");
				} else {
					sb.append("<option value=\"" + value + "\">" + value
							+ "</option>");
				}
			}
			sb.append("</select>");
			prob = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prob;
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public String getRPN(Context context, String args[]) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb
		.append("<input name=\"RPN\" size=\"2\" value=\"25\" maxlength=\"2\" readonly=\"readonly\">");
		sb.append("</input>");
		String textbox = sb.toString();
		return textbox;
	}
/**
 * Q53
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public static MapList getAllFailureModeWorksheet(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);

			// StringBuffer sbWhere = new StringBuffer(64);
			// sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("(policy != 'Version')");
			sbWhere.append(" && (attribute[Originator] == '");
			sbWhere.append(context.getUser());
			sbWhere.append("')");
			sbWhere.append(" && (type == '"+TYPE_FAILURE_MODE+"')");

			// Call Find objects method to get the id's with select list and
			// where clause
			MapList ownedIdList = dObj.findObjects(context, TYPE_FAILURE_MODE,
					"*", "*", "*", "*", sbWhere.toString(), true, objSelects);
			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}
/**
 * Q53
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public MapList getFMEAFailureMode(Context context, String[] args)
	throws Exception {
		MapList mlObjects = new MapList();

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");

		if (objectId != null && objectId.trim().length() > 0) {
			StringList slObjSel = new StringList(1);
			slObjSel.add(DomainConstants.SELECT_ID);
			slObjSel.add(DomainConstants.SELECT_CURRENT);
			slObjSel.add(DomainConstants.SELECT_LAST_ID);
			slObjSel.add("attribute[Critical Characteristic]");
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			mlObjects = doObj.getRelatedObjects(context, RELATIONSHIP_FAILURE_MODE,
					TYPE_FAILURE_MODE, slObjSel, slRelSel, true, true, (short) 1,
					"revision==last", null);
		}
		
		return mlObjects;
	}
	/**Q53
	 * Complete FMEA
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public MapList getCompleteFMEA(Context context, String[] args)
	throws Exception {



		MapList mlCompleteFMEAList =null;
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");		 

		if (objectId != null && objectId.trim().length() > 0) {
			//Object Select
			StringList slObjSel = new StringList(1);
			slObjSel.add(DomainConstants.SELECT_ID);
			slObjSel.add(DomainConstants.SELECT_CURRENT);
			slObjSel.add(DomainConstants.SELECT_LAST_ID);			
			slObjSel.add(DomainConstants.SELECT_LEVEL);
			slObjSel.add(DomainConstants.SELECT_TYPE);
			slObjSel.add(DomainConstants.SELECT_NAME);	
			slObjSel.add(DomainConstants.SELECT_RELATIONSHIP_NAME);				
			slObjSel.add(DomainConstants.SELECT_DESCRIPTION);	
			slObjSel.add("from["+RELATIONSHIP_OPERATION_FMEA+"].to.id");
			slObjSel.add("from["+RELATIONSHIP_FAILURE_MODE+"].to.id");
			slObjSel.add("from["+RELATIONSHIP_FAILURE_MODE_EFFECT+"].to.id");
			slObjSel.add("from["+RELATIONSHIP_FAILURE_MODE_CAUSE+"].to.id");
			slObjSel.add("from["+RELATIONSHIP_FAILURE_MODE_CAUSE_RPN+"].to.id");



			//Rel Select
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);

			//Domain Object
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);

			//Rel Pattern			
			Pattern relPattern = new Pattern(RELATIONSHIP_OPERATION_FMEA);
			relPattern.addPattern(RELATIONSHIP_FAILURE_MODE);
			relPattern.addPattern(RELATIONSHIP_FAILURE_MODE_EFFECT);
			relPattern.addPattern(RELATIONSHIP_FAILURE_MODE_CAUSE);
			relPattern.addPattern(RELATIONSHIP_FAILURE_MODE_CAUSE_RPN);
			relPattern.addPattern(RELATIONSHIP_FMEA_AFFECTED_ITEM);


			//Type Pattern
			Pattern typePattern = new Pattern(TYPE_FUNCTION);
			typePattern.addPattern(TYPE_FAILURE_MODE);
			typePattern.addPattern(TYPE_FAILURE_MODE_EFFECT);
			typePattern.addPattern(TYPE_FAILURE_MODE_CAUSE);
			typePattern.addPattern(TYPE_RECOMMENDED_ACTION);
			typePattern.addPattern(TYPE_PART);


			//Get all the object Connected to FMEA            
			mlCompleteFMEAList = doObj.getRelatedObjects(context, relPattern.getPattern(),//"*",//relPatter.getPattern(), // relationship pattern

					typePattern.getPattern(), // object pattern

					slObjSel, // object selects

					slRelSel, // relationship selects

					false, // to direction

					true, // from direction

					(short) 0, // recursion level

					null, // object where clause

					null);

			


		}	

		return mlCompleteFMEAList;
	}
/**
 * Q53
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public MapList getFMEAFailureModeRnD(Context context, String[] args)
	throws Exception {
		MapList mlObjects = new MapList();

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");
		String expandLevel = (String) programMap.get("expandLevel");

		DomainObject dmoProject = DomainObject.newInstance(context, objectId);
		String type = dmoProject.getInfo(context, SELECT_TYPE);

		String strTypePattern = "";
		String strRelPattern = "";
		if (objectId != null && objectId.trim().length() > 0) {
			StringList slObjSel = new StringList(1);
			slObjSel.add(DomainConstants.SELECT_ID);
			slObjSel.add(DomainConstants.SELECT_CURRENT);
			slObjSel.add(DomainConstants.SELECT_LAST_ID);
			slObjSel.add("attribute[Critical Characteristic]");
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			if (type.equals("FMEA")) {
				strTypePattern = "Operation";
				strRelPattern = "Operation FMEA";
			} else if (type.equals("Operation")) {
				strTypePattern = "Failure Mode";
				strRelPattern = "Failure Mode";
			} else if (type.equals("Failure Mode")) {
				strTypePattern = "Failure Mode Effect";
				strRelPattern = "Failure Mode Effect";
			} else if (type.equals("Failure Mode Effect")) {
				strTypePattern = "Failure Mode Cause";
				strRelPattern = "Failure Mode Cause";
			} else if (type.equals("Failure Mode Cause")) {
				strTypePattern = "FMEA RPN";
				strRelPattern = "Failure Mode Cause RPN";

			}

			mlObjects = doObj.getRelatedObjects(context, strRelPattern,
					strTypePattern, slObjSel, slRelSel, true, true, (short) 1,
					"revision==last", null);
		}
		
		return mlObjects;

	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectAffectedItem(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strPartId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		String newPartFamilyIds = (String) paramMap.get("New OID");
		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String partobject = DomainConstants.EMPTY_STRING;
		String partId = DomainConstants.EMPTY_STRING;
		String strType = "*";
		String strRev = "*";
		String newPartFamilyName = "";
		StringList newFailureModeList = new StringList();
		if (newPartFamilyIds.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
			newPartFamilyName = (String) paramMap.get("New Value");
			if (!newPartFamilyName.equalsIgnoreCase("")) {
				String result = MqlUtil.mqlCommand(context, "temp query bus \""
						+ strType + "\" \"" + newPartFamilyName + "\" \""
						+ strRev + "\" select id dump |", true);
				if (result != null && result.length() > 0) {
					newFailureModeList = FrameworkUtil.split(result, "\n");
					partobject = (String) newFailureModeList.get(0);
					StringList newList = FrameworkUtil.split(partobject, "|");
					partId = (String) newList.get(3);
					newPartFamilyIds = partId;
				}
			}
		}
		StringList newPartFamilyList = FrameworkUtil.split(newPartFamilyIds,
		",");
		
		DomainObject partObj = DomainObject.newInstance(context, strPartId);

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_AFFECTED_ITEM, // relationship pattern
				TYPE_PART, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					if (!newPartFamilyList.contains(strId)) {
						String strRelId = (String) ((Map) partFamilyList.get(i))
						.get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newPartFamilyList.remove(strId);
					}
				}
			}
			if (newPartFamilyList.size() > 0) {
				Iterator partFamilyItr = newPartFamilyList.iterator();
				DomainObject partFam = DomainObject.newInstance(context);
				while (partFamilyItr.hasNext()) {
					String newPartFamily = (String) partFamilyItr.next();
					partFam = DomainObject.newInstance(context, newPartFamily);
					// setId(newPartFamily);
					DomainObject domainObjectToType = newInstance(context,
							strPartId);
					partFam.addRelatedObject(context, new RelationshipType(
							RELATIONSHIP_FMEA_AFFECTED_ITEM), true, strPartId);
					// DomainRelationship.connect(context,domainObjectToType,"MHI_Classification",this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}

	public Object connectAffectedItemProjectSpace(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		HashMap requestMap = (HashMap) programMap.get("requestMap");
		//String isFromEdit = (String) requestMap.get("isFromEdit");

		String strPartId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		StringList newFailureModeList = new StringList();

		//(String) paramMap.get("New OID");		
		//StringList newFailureModeList = FrameworkUtil.split(
		//	newFailureModeFamilyIds, "|");

		DomainObject FMEAObj = DomainObject.newInstance(context, strPartId);
		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String partobject =DomainConstants.EMPTY_STRING;
		String partId = DomainConstants.EMPTY_STRING;		
		String strRev = "*";
		String newPartFamilyName =DomainConstants.EMPTY_STRING;
		String newFailureModeFamilyName =DomainConstants.EMPTY_STRING;
		StringList failureModeList = new StringList();
		//if (newFailureModeFamilyIds.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
		// Added by vkannan for Incident 14149 - Start
		//Modified by rneema1 for CR57(Added 3 new types of projects in PPPM)
		String strTypes = "iPLMVehicleProject,iPLMUNITPTProjectSpace,iPLMUNITElectricalProject,iPLMUNITTransmissionProject,iPLMUNITArchitectureProject,iPLMUNITEpropulsionProject";
		// Added by vkannan for Incident 14149 - End
		newPartFamilyName = (String) paramMap.get("New Value");
		if (!newPartFamilyName.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
			StringList failureModeList1 = FrameworkUtil.split(
					newPartFamilyName, "|");
			int size = failureModeList1.size();
			for (int i = 0; i < size; i++)

			{
				newFailureModeFamilyName = (String) failureModeList1.get(i);
				String result = MqlUtil.mqlCommand(context,
						"temp query bus \"" + strTypes + "\" \""
						+ newFailureModeFamilyName + "\" \""
						+ strRev + "\" select id dump |", true);
				if (result != null && result.length() > 0) {
					failureModeList = FrameworkUtil.split(result, "\n");
					partobject = (String) failureModeList.get(0);
					StringList newList = FrameworkUtil.split(partobject,
							"|");
					partId = (String) newList.get(3);
					newFailureModeList.add(partId);
				}
			}
		}
		
		// commented by vkannan for Incident 14149 - Start
		/*String newFailureModeFamilyId =DomainConstants.EMPTY_STRING;
		String newPartFamilyOID = (String) paramMap.get("New OID");
		if (!newPartFamilyOID.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) 
		{
			StringList failureModeList1 = FrameworkUtil.split(newPartFamilyOID, "|");
			int size = failureModeList1.size();
			for (int i = 0; i < size; i++)
			{
				newFailureModeFamilyId = (String) failureModeList1.get(i);
				newFailureModeList.add(newFailureModeFamilyId);
			}
		}*/
		// commented by vkannan for Incident 14149 - End
		//}

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList failureModeFamilyList = FMEAObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_PROJECT_SPACE, // relationship pattern
				TYPE_PROJECT_SPACE, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (failureModeFamilyList != null
					&& failureModeFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = failureModeFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) failureModeFamilyList.get(i))
					.get(SELECT_ID);
					if (!newFailureModeList.contains(strId)) {
						String strRelId = (String) ((Map) failureModeFamilyList
								.get(i)).get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newFailureModeList.remove(strId);
					}
				}
			}
			if (newFailureModeList.size() > 0) {
				Iterator partFamilyItr = newFailureModeList.iterator();
				DomainObject FailureModeFam = DomainObject.newInstance(context);
				while (partFamilyItr.hasNext()) {
					String newFailureModeFamily = (String) partFamilyItr.next();
					FailureModeFam = DomainObject.newInstance(context,
							newFailureModeFamily);
					// setId(newPartFamily);
					DomainObject domainObjectToType = newInstance(context,
							strPartId);
					FailureModeFam.addRelatedObject(context,
							new RelationshipType(RELATIONSHIP_FMEA_PROJECT_SPACE),
							true, strPartId);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}

	/**
	 * Q53
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */

	public Object connectResponsiblePerson(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strPartId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		StringList newPartFamilyList = null;
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		String newPartFamilyIds = (String) paramMap.get("New OID");
		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String partobject =DomainConstants.EMPTY_STRING;
		String partId =DomainConstants.EMPTY_STRING;
		String strType = "*";
		String strRev = "*";
		String newPartFamilyName =DomainConstants.EMPTY_STRING;
		StringList newFailureModeList = new StringList();
		if (newPartFamilyIds == null || newPartFamilyIds.equals(DomainConstants.EMPTY_STRING)) {
			newPartFamilyName = (String) paramMap.get("New Value");
			if (!newPartFamilyName.equalsIgnoreCase("")) {
				String result = MqlUtil.mqlCommand(context, "temp query bus \""
						+ strType + "\" \"" + newPartFamilyName + "\" \""
						+ strRev + "\" select id dump |", true);
				if (result != null && result.length() > 0) {
					newFailureModeList = FrameworkUtil.split(result, "\n");
					partobject = (String) newFailureModeList.get(0);
					StringList newList = FrameworkUtil.split(partobject, "|");
					partId = (String) newList.get(3);
					newPartFamilyIds = partId;
				}
			}
		}

		if(newPartFamilyIds !=null){
			if (newPartFamilyIds.contains("|")) {
				newPartFamilyList = FrameworkUtil.split(newPartFamilyIds, "|");			
			} else {
				newPartFamilyList = FrameworkUtil.split(newPartFamilyIds, ",");			
			}
		}

		DomainObject partObj = DomainObject.newInstance(context, strPartId);

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_RESPONSIBLE_PERSON, // relationship pattern
				TYPE_PERSON, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					if(newPartFamilyList ==null)
					{
						String strRelId = (String) ((Map) partFamilyList.get(i))
						.get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);

					}else
					{
						if (!newPartFamilyList.contains(strId)) {
							String strRelId = (String) ((Map) partFamilyList.get(i))
							.get(SELECT_RELATIONSHIP_ID);
							// Disconnecting the existing relationship
							DomainRelationship.disconnect(context, strRelId);
						} else {
							newPartFamilyList.remove(strId);
						}
					}
				}
			}
			if(newPartFamilyList!=null)
				if (newPartFamilyList.size() > 0) {
					Iterator partFamilyItr = newPartFamilyList.iterator();
					DomainObject partFam = DomainObject.newInstance(context);
					while (partFamilyItr.hasNext()) {
						String newPartFamily = (String) partFamilyItr.next();
						partFam = DomainObject.newInstance(context, newPartFamily);
						// setId(newPartFamily);
						DomainObject domainObjectToType = newInstance(context,
								strPartId);
						partFam.addRelatedObject(context, new RelationshipType(
								RELATIONSHIP_FMEA_RESPONSIBLE_PERSON), true, strPartId);
						// DomainRelationship.connect(context,domainObjectToType,"MHI_Classification",this);
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}
	/**
	 * Q53
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectProcessHeader(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strPartId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		StringList newPartFamilyList = null;
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		String newPartFamilyIds = (String) paramMap.get("New OID");
		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String partobject =DomainConstants.EMPTY_STRING;
		String partId =DomainConstants.EMPTY_STRING;
		String type="VPLMtyp/DELLmiGeneralOperationReference";
		String strType = "*";
		String strRev = "*";
		String newPartFamilyName =DomainConstants.EMPTY_STRING;
		StringList newFailureModeList = new StringList();
		if (newPartFamilyIds == null || newPartFamilyIds.equals(DomainConstants.EMPTY_STRING)) {
			newPartFamilyName = (String) paramMap.get("New Value");
			if (!newPartFamilyName.equalsIgnoreCase("")) {
				String result = MqlUtil.mqlCommand(context, "temp query bus \""
						+ strType + "\" \"" + newPartFamilyName + "\" \""
						+ strRev + "\" select id dump |", true);
				if (result != null && result.length() > 0) {
					newFailureModeList = FrameworkUtil.split(result, "\n");
					partobject = (String) newFailureModeList.get(0);
					StringList newList = FrameworkUtil.split(partobject, "|");
					partId = (String) newList.get(3);
					newPartFamilyIds = partId;
				}
			}
		}

		if(newPartFamilyIds !=null){
			if (newPartFamilyIds.contains("|")) {
				newPartFamilyList = FrameworkUtil.split(newPartFamilyIds, "|");			
			} else {
				newPartFamilyList = FrameworkUtil.split(newPartFamilyIds, ",");			
			}
		}

		DomainObject partObj = DomainObject.newInstance(context, strPartId);

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_OPERATION_PROCESS_HEADER, // relationship pattern
				type, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					if(newPartFamilyList ==null)
					{
						String strRelId = (String) ((Map) partFamilyList.get(i))
						.get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);

					}else
					{
						if (!newPartFamilyList.contains(strId)) {
							String strRelId = (String) ((Map) partFamilyList.get(i))
							.get(SELECT_RELATIONSHIP_ID);
							// Disconnecting the existing relationship
							DomainRelationship.disconnect(context, strRelId);
						} else {
							newPartFamilyList.remove(strId);
						}
					}
				}
			}
			if(newPartFamilyList!=null)
				if (newPartFamilyList.size() > 0) {
					Iterator partFamilyItr = newPartFamilyList.iterator();
					DomainObject partFam = DomainObject.newInstance(context);
					while (partFamilyItr.hasNext()) {
						String newPartFamily = (String) partFamilyItr.next();
						partFam = DomainObject.newInstance(context, newPartFamily);
						// setId(newPartFamily);
						DomainObject domainObjectToType = newInstance(context,
								strPartId);
						partFam.addRelatedObject(context, new RelationshipType(
								RELATIONSHIP_OPERATION_PROCESS_HEADER), true, strPartId);
						// DomainRelationship.connect(context,domainObjectToType,"MHI_Classification",this);
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}
	/**
	 * Q53
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectFunctinalAndLogicalReference(Context context, String[] args)
	throws Exception {

		//Q53 Chnage Done
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strFMEAId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strFMEAId, "|");
		StringList newPersonList = null;
		if (slRowIds.size() > 1) {
			strFMEAId = (String) slRowIds.get(1);
		}

		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String personObject = DomainConstants.EMPTY_STRING;
		String personId =DomainConstants.EMPTY_STRING;
		String strType = "*";
		String strRev = "*";
		String newPersonFamilyName =DomainConstants.EMPTY_STRING;
		String newPersonsFamilyName =DomainConstants.EMPTY_STRING;
		StringList personList = new StringList();
		StringList newPersonsList = new StringList();

		newPersonFamilyName = (String) paramMap.get("New Value");
		if (!newPersonFamilyName.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
			StringList selectedPersonList = FrameworkUtil.split(
					newPersonFamilyName, "|");
			int size = selectedPersonList.size();
			for (int i = 0; i < size; i++)

			{
				newPersonsFamilyName = (String) selectedPersonList.get(i);
				String result = MqlUtil.mqlCommand(context,
						"temp query bus \"" + strType + "\" \""
						+ newPersonsFamilyName + "\" \"" + strRev
						+ "\" select id dump |", true);
				if (result != null && result.length() > 0) {
					personList = FrameworkUtil.split(result, "\n");
					personObject = (String) personList.get(0);
					StringList newList = FrameworkUtil.split(personObject,
							"|");
					personId = (String) newList.get(3);
					newPersonsList.add(personId);
				}
			}
		}

		DomainObject partObj = DomainObject.newInstance(context, strFMEAId);

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		Pattern typePattern = new Pattern("VPLMtyp/RFLPLMFunctionalReference");
		typePattern.addPattern("VPLMtyp/RFLVPMLogicalReference");
		// Get the part Family connected to part.
		MapList personsFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_CAUSE_REFERENCE, // relationship pattern
				typePattern.getPattern(), // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (personsFamilyList != null && personsFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = personsFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) personsFamilyList.get(i))
					.get(SELECT_ID);
					if (!newPersonsList.contains(strId)) {
						String strRelId = (String) ((Map) personsFamilyList
								.get(i)).get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newPersonsList.remove(strId);
					}
				}
			}
			if (newPersonsList.size() > 0) {
				Iterator personFamilyItr = newPersonsList.iterator();
				DomainObject personFam = DomainObject.newInstance(context);
				while (personFamilyItr.hasNext()) {
					String newPersonFamily = (String) personFamilyItr.next();
					personFam = DomainObject.newInstance(context,
							newPersonFamily);
					// setId(newPartFamily);
					DomainObject domainObjectToType = newInstance(context,
							strFMEAId);					
					personFam.addRelatedObject(context, new RelationshipType(
							RELATIONSHIP_FMEA_CAUSE_REFERENCE), true, strFMEAId);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}


	/**
	 * Q53
	 * Added to connect Person Objects with FMEA as a team member..
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectTeam(Context context, String[] args) throws Exception {

		//Q53 Change Done
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strFMEAId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strFMEAId, "|");
		StringList newPersonList = null;
		if (slRowIds.size() > 1) {
			strFMEAId = (String) slRowIds.get(1);
		}
		//String newPersonIds = (String) paramMap.get("New OID");
		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String personObject = DomainConstants.EMPTY_STRING;
		String personId =DomainConstants.EMPTY_STRING;
		String strType = "*";
		String strRev = "*";
		String newPersonFamilyName =DomainConstants.EMPTY_STRING;
		String newPersonsFamilyName =DomainConstants.EMPTY_STRING;
		StringList personList = new StringList();
		StringList newPersonsList = new StringList();
		//if (newPersonIds == null || newPersonIds.equals(DomainConstants.EMPTY_STRING)) {
		newPersonFamilyName = (String) paramMap.get("New Value");
		if (!newPersonFamilyName.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
			StringList selectedPersonList = FrameworkUtil.split(
					newPersonFamilyName, "|");
			int size = selectedPersonList.size();
			for (int i = 0; i < size; i++)

			{
				newPersonsFamilyName = (String) selectedPersonList.get(i);
				String result = MqlUtil.mqlCommand(context,
						"temp query bus \"" + strType + "\" \""
						+ newPersonsFamilyName + "\" \"" + strRev
						+ "\" select id dump |", true);
				if (result != null && result.length() > 0) {
					personList = FrameworkUtil.split(result, "\n");
					personObject = (String) personList.get(0);
					StringList newList = FrameworkUtil.split(personObject,
							"|");
					personId = (String) newList.get(3);
					newPersonsList.add(personId);
				}
			}
		}
		
		DomainObject partObj = DomainObject.newInstance(context, strFMEAId);

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList personsFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_TEAM_PERSONS, // relationship pattern
				TYPE_PERSON, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (personsFamilyList != null && personsFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = personsFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) personsFamilyList.get(i))
					.get(SELECT_ID);
					if (!newPersonsList.contains(strId)) {
						String strRelId = (String) ((Map) personsFamilyList
								.get(i)).get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newPersonsList.remove(strId);
					}
				}
			}
			if (newPersonsList.size() > 0) {
				Iterator personFamilyItr = newPersonsList.iterator();
				DomainObject personFam = DomainObject.newInstance(context);
				while (personFamilyItr.hasNext()) {
					String newPersonFamily = (String) personFamilyItr.next();
					personFam = DomainObject.newInstance(context,
							newPersonFamily);					
					DomainObject domainObjectToType = newInstance(context,
							strFMEAId);					
					personFam.addRelatedObject(context, new RelationshipType(
							RELATIONSHIP_FMEA_TEAM_PERSONS), true, strFMEAId);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} 

		return new Boolean(true);
	}

	/**
	 * Q53
	 * Added to update FMEA Owner
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectFMEAOwner(Context context, String[] args) throws Exception {

		//Q53 Change Done
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strFMEAId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strFMEAId, "|");
		StringList newPersonList = null;
		if (slRowIds.size() > 1) {
			strFMEAId = (String) slRowIds.get(1);
		}
		//String newPersonIds = (String) paramMap.get("New OID");
		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String personObject = DomainConstants.EMPTY_STRING;
		String personId =DomainConstants.EMPTY_STRING;		
		String newPersonFamilyName =DomainConstants.EMPTY_STRING;
		String newPersonsFamilyName =DomainConstants.EMPTY_STRING;
		StringList personList = new StringList();
		StringList newPersonsList = new StringList();
		
		newPersonFamilyName = (String) paramMap.get("New Value");
		if (!newPersonFamilyName.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
			StringList selectedPersonList = FrameworkUtil.split(
					newPersonFamilyName, "|");
			int size = selectedPersonList.size();
			for (int i = 0; i < size; i++)

			{
				newPersonsFamilyName = (String) selectedPersonList.get(i);
				String result = MqlUtil.mqlCommand(context,
						"temp query bus \"" + QUERY_WILDCARD + "\" \""
						+ newPersonsFamilyName + "\" \"" + QUERY_WILDCARD
						+ "\" select id dump |", true);
				if (result != null && result.length() > 0) {
					personList = FrameworkUtil.split(result, "\n");
					personObject = (String) personList.get(0);
					StringList newList = FrameworkUtil.split(personObject,
							"|");
					personId = (String) newList.get(3);
					newPersonsList.add(personId);
				}
			}
		}
		

		DomainObject partObj = DomainObject.newInstance(context, strFMEAId);

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList personsFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_OWNER, // relationship pattern
				TYPE_PERSON, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (personsFamilyList != null && personsFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = personsFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) personsFamilyList.get(i))
					.get(SELECT_ID);
					if (!newPersonsList.contains(strId)) {
						String strRelId = (String) ((Map) personsFamilyList
								.get(i)).get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newPersonsList.remove(strId);
					}
				}
			}
			if (newPersonsList.size() > 0) {
				Iterator personFamilyItr = newPersonsList.iterator();
				DomainObject personFam = DomainObject.newInstance(context);
				while (personFamilyItr.hasNext()) {
					String newPersonFamily = (String) personFamilyItr.next();
					personFam = DomainObject.newInstance(context,
							newPersonFamily);
					// setId(newPartFamily);
					DomainObject domainObjectToType = newInstance(context,
							strFMEAId);					
					personFam.addRelatedObject(context, new RelationshipType(
							RELATIONSHIP_FMEA_OWNER), true, strFMEAId);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object getAffectedVehichleProgram(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap requestMap = (HashMap) programMap.get("requestMap");
		String strPartId = (String) requestMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		StringBuffer sbVehichleProgramName = new StringBuffer();
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}

		DomainObject partObj = DomainObject.newInstance(context, strPartId);
		String strName =DomainConstants.EMPTY_STRING;
		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		selectStmts.addElement(SELECT_NAME);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);		
		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_PROJECT_SPACE, // relationship pattern
				TYPE_PROJECT_SPACE, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					strName = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_NAME);

					if (partFamilyListSize != 1) {
						sbVehichleProgramName.append(strName);
						if (i != (partFamilyListSize - 1)) {
							sbVehichleProgramName.append("|");
						}
					} else {
						sbVehichleProgramName.append(strName);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return sbVehichleProgramName.toString();
	}
/**
 * Q53
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public Object getAffectedItem(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap requestMap = (HashMap) programMap.get("requestMap");
		String strPartId = (String) requestMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		DomainObject partObj = DomainObject.newInstance(context, strPartId);
		String strName =DomainConstants.EMPTY_STRING;
		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		selectStmts.addElement(SELECT_NAME);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		String strTypePattern = "Part" + "," + "Products" + ","
		+ "Logical Feature" + "," + "Model" + "," + "Product Line";
		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_AFFECTED_ITEM, // relationship pattern
				strTypePattern, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					strName = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_NAME);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return strName;
	}

/**
 * Q53
 * @param context
 * @param args
 * @return
 * @throws Exception
 */
	public Object getFMEAOwner(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap requestMap = (HashMap) programMap.get("requestMap");
		String strPartId = (String) requestMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		DomainObject partObj = DomainObject.newInstance(context, strPartId);
		String strName =DomainConstants.EMPTY_STRING;
		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		selectStmts.addElement(SELECT_NAME);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);		
		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_OWNER, // relationship pattern
				TYPE_PERSON, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					strName = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_NAME);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return strName;
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object getFailureMode(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap requestMap = (HashMap) programMap.get("requestMap");
		String strPartId = (String) requestMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		DomainObject partObj = DomainObject.newInstance(context, strPartId);
		String strName =DomainConstants.EMPTY_STRING;
		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		selectStmts.addElement(SELECT_NAME);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		StringBuffer sbFailureModeName = new StringBuffer();

		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FAILURE_MODE, // relationship pattern
				TYPE_FAILURE_MODE, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					strName = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_NAME);
					if (partFamilyListSize != 1) {
						sbFailureModeName.append(strName);
						if (i != (partFamilyListSize - 1)) {
							sbFailureModeName.append("|");
						}
					} else {
						sbFailureModeName.append(strName);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return sbFailureModeName.toString();
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object getFunctions(Context context, String[] args) throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap requestMap = (HashMap) programMap.get("requestMap");
		String strPartId = (String) requestMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		DomainObject partObj = DomainObject.newInstance(context, strPartId);
		String strName =DomainConstants.EMPTY_STRING;
		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		selectStmts.addElement(SELECT_NAME);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		StringBuffer sbFailureModeName = new StringBuffer();

		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_OPERATION_FMEA, // relationship pattern
				TYPE_FUNCTION, // object pattern
				selectStmts, // object selectsR
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					strName = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_NAME);
					if (partFamilyListSize != 1) {
						sbFailureModeName.append(strName);
						if (i != (partFamilyListSize - 1)) {
							sbFailureModeName.append("|");
						}
					} else {
						sbFailureModeName.append(strName);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return sbFailureModeName.toString();
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object getTeam(Context context, String[] args) throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap requestMap = (HashMap) programMap.get("requestMap");
		String strPartId = (String) requestMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		DomainObject partObj = DomainObject.newInstance(context, strPartId);
		String strName =DomainConstants.EMPTY_STRING;
		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		selectStmts.addElement(SELECT_NAME);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		StringBuffer sbFailureModeName = new StringBuffer();

		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				RELATIONSHIP_FMEA_TEAM_PERSONS, // relationship pattern
				TYPE_PERSON, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion levelOc
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					strName = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_NAME);
					if (partFamilyListSize != 1) {
						sbFailureModeName.append(strName);
						if (i != (partFamilyListSize - 1)) {
							sbFailureModeName.append("|");
						}
					} else {
						sbFailureModeName.append(strName);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return sbFailureModeName.toString();
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectFailureMode(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strPartId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		String newFailureModeFamilyIds = (String) paramMap.get("New OID");
		String strFailureModeRelationship = "Failure Mode";
		StringList newFailureModeList = FrameworkUtil.split(
				newFailureModeFamilyIds, "|");
		DomainObject FMEAObj = DomainObject.newInstance(context, strPartId);
		String resultRecords = "";
		String resultFields = "";
		String partobject = "";
		String partId = "";
		String strType = "Failure Mode";
		String strRev = "*";
		String newPartFamilyName = "";
		String newFailureModeFamilyName = "";
		StringList failureModeList = new StringList();
		if (newFailureModeFamilyIds.equalsIgnoreCase("")) {
			newPartFamilyName = (String) paramMap.get("New Value");
			if (!newPartFamilyName.equalsIgnoreCase("")) {
				StringList failureModeList1 = FrameworkUtil.split(
						newPartFamilyName, "|");
				int size = failureModeList1.size();
				for (int i = 0; i < size; i++)

				{
					newFailureModeFamilyName = (String) failureModeList1.get(i);
					String result = MqlUtil.mqlCommand(context,
							"temp query bus \"" + strType + "\" \""
							+ newFailureModeFamilyName + "\" \""
							+ strRev + "\" select id dump |", true);
					if (result != null && result.length() > 0) {
						failureModeList = FrameworkUtil.split(result, "\n");
						partobject = (String) failureModeList.get(0);
						StringList newList = FrameworkUtil.split(partobject,
						"|");
						partId = (String) newList.get(3);
						newFailureModeList.add(partId);
					}
				}
			}
		}

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList failureModeFamilyList = FMEAObj.getRelatedObjects(context,
				strFailureModeRelationship, // relationship pattern
				"Failure Mode", // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (failureModeFamilyList != null
					&& failureModeFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = failureModeFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) failureModeFamilyList.get(i))
					.get(SELECT_ID);
					if (!newFailureModeList.contains(strId)) {
						String strRelId = (String) ((Map) failureModeFamilyList
								.get(i)).get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newFailureModeList.remove(strId);
					}
				}
			}
			if (newFailureModeList.size() > 0) {
				Iterator partFamilyItr = newFailureModeList.iterator();
				DomainObject FailureModeFam = DomainObject.newInstance(context);
				while (partFamilyItr.hasNext()) {
					String newFailureModeFamily = (String) partFamilyItr.next();
					FailureModeFam = DomainObject.newInstance(context,
							newFailureModeFamily);
					// setId(newPartFamily);
					DomainObject domainObjectToType = newInstance(context,
							strPartId);
					FailureModeFam.addRelatedObject(context,
							new RelationshipType(strFailureModeRelationship),
							true, strPartId);
					// DomainRelationship.connect(context,domainObjectToType,"MHI_Classification",this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectFunctions(Context context, String[] args)
	throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strPartId = (String) paramMap.get("objectId");
		StringList slRowIds = FrameworkUtil.splitString(strPartId, "|");
		if (slRowIds.size() > 1) {
			strPartId = (String) slRowIds.get(1);
		}
		String newFailureModeFamilyIds = (String) paramMap.get("New OID");		
		StringList newFailureModeList = FrameworkUtil.split(
				newFailureModeFamilyIds, "|");

		DomainObject FMEAObj = DomainObject.newInstance(context, strPartId);
		String resultRecords =DomainConstants.EMPTY_STRING;
		String resultFields =DomainConstants.EMPTY_STRING;
		String partobject =DomainConstants.EMPTY_STRING;
		String partId = DomainConstants.EMPTY_STRING;		
		String strRev = "*";
		String newPartFamilyName =DomainConstants.EMPTY_STRING;
		String newFailureModeFamilyName =DomainConstants.EMPTY_STRING;
		StringList failureModeList = new StringList();
		if (newFailureModeFamilyIds.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
			newPartFamilyName = (String) paramMap.get("New Value");
			if (!newPartFamilyName.equalsIgnoreCase(DomainConstants.EMPTY_STRING)) {
				StringList failureModeList1 = FrameworkUtil.split(
						newPartFamilyName, "|");
				int size = failureModeList1.size();
				for (int i = 0; i < size; i++)

				{
					newFailureModeFamilyName = (String) failureModeList1.get(i);
					String result = MqlUtil.mqlCommand(context,
							"temp query bus \"" + TYPE_FUNCTION + "\" \""
							+ newFailureModeFamilyName + "\" \""
							+ strRev + "\" select id dump |", true);
					if (result != null && result.length() > 0) {
						failureModeList = FrameworkUtil.split(result, "\n");
						partobject = (String) failureModeList.get(0);
						StringList newList = FrameworkUtil.split(partobject,
						"|");
						partId = (String) newList.get(3);
						newFailureModeList.add(partId);
					}
				}
			}
		}

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList failureModeFamilyList = FMEAObj.getRelatedObjects(context,
				RELATIONSHIP_OPERATION_FMEA, // relationship pattern
				TYPE_FUNCTION, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577
			if (failureModeFamilyList != null
					&& failureModeFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = failureModeFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) failureModeFamilyList.get(i))
					.get(SELECT_ID);
					if (!newFailureModeList.contains(strId)) {
						String strRelId = (String) ((Map) failureModeFamilyList
								.get(i)).get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newFailureModeList.remove(strId);
					}
				}
			}
			if (newFailureModeList.size() > 0) {
				Iterator partFamilyItr = newFailureModeList.iterator();
				DomainObject FailureModeFam = DomainObject.newInstance(context);
				while (partFamilyItr.hasNext()) {
					String newFailureModeFamily = (String) partFamilyItr.next();
					FailureModeFam = DomainObject.newInstance(context,
							newFailureModeFamily);
					// setId(newPartFamily);
					DomainObject domainObjectToType = newInstance(context,
							strPartId);
					FailureModeFam.addRelatedObject(context,
							new RelationshipType(RELATIONSHIP_OPERATION_FMEA),
							true, strPartId);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}

	public Vector getResponsibleAssignee(Context context, String args[])
	throws Exception {
		return new Vector();
	}

	/**
	 * Q53 Include OID
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList includeSelectedFMEAFailureModeOIDs(
			Context context, String args[]) throws Exception {
		StringList finalOIDs = new StringList();
		HashMap programMap = (HashMap) JPO.unpackArgs(args);		
		String strFMEAId = (String) programMap.get("objectId");		
		DomainObject FMEAObj = DomainObject.newInstance(context, strFMEAId);
		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList failureModeFamilyList = FMEAObj.getRelatedObjects(context,
				RELATIONSHIP_OPERATION_FMEA, // relationship pattern
				TYPE_FUNCTION, // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				true, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause
		Iterator objectListIterator = failureModeFamilyList.iterator();
		try {
			while (objectListIterator.hasNext()) {
				Map objectMap = (Map) objectListIterator.next();
				String strFailureModeId = (String) objectMap
				.get(DomainConstants.SELECT_ID);
				finalOIDs.add(strFailureModeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalOIDs;
	}

	

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getAllFMEARelatedToControlPlans(Context context,
			String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList ownedIdList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_CONTROL_PLAN, TYPE_FMEA, objSelects, slRelSel, true,
					true, (short) 1, "revision==last", null);
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getAllRequirementAndReuirementSpecificationRelatedToFMEA(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);

			String typePatternReq = "Requirement" + ","
			+ "Requirement Specification";
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList ownedIdList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_REQUIREMENT, typePatternReq, objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getAllSysSubsysComponent(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);

			String typePatternReq = DomainConstants.QUERY_WILDCARD;
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList ownedIdList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_PART_STRUCTURE, typePatternReq, objSelects, slRelSel,
					false, true, (short) 0, null, null);
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getAllTeamMembers(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);

			String typePatternReq =DomainConstants.TYPE_PERSON;

			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList ownedIdList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_TEAM_PERSONS, typePatternReq, objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getAllRequirementRelatedToFailureMode(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);

			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList ownedIdList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_REQUIREMENT, "Requirement", objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getAllFunctionalAndLogicalReference(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);

			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);

			//Rel Pattern			
			Pattern relPattern = new Pattern(RELATIONSHIP_FMEA_FUNCTION_REFERENCE);
			//relPattern.addPattern(RELATIONSHIP_FAILURE_MODE);
			//relPattern.addPattern(RELATIONSHIP_FAILURE_MODE_EFFECT);
			//relPattern.addPattern(RELATIONSHIP_FAILURE_MODE_CAUSE);
			//relPattern.addPattern(RELATIONSHIP_FAILURE_MODE_CAUSE_RPN);



			//Type Pattern
			Pattern typePattern = new Pattern("VPLMtyp/RFLPLMFunctionalReference");
			typePattern.addPattern("VPLMtyp/RFLVPMLogicalReference");
			//typePattern.addPattern(TYPE_FAILURE_MODE_EFFECT);
			//typePattern.addPattern(TYPE_FAILURE_MODE_CAUSE);
			//typePattern.addPattern(TYPE_RECOMMENDED_ACTION);


			//Get all the object Connected to FMEA            
			MapList ownedIdList = doObj.getRelatedObjects(context, relPattern.getPattern(),//"*",//relPatter.getPattern(), // relationship pattern

					typePattern.getPattern(), // object pattern

					objSelects, // object selects

					slRelSel, // relationship selects

					true, // to direction

					true, // from direction

					(short) 0, // recursion level

					"revision==last", // object where clause

					null);	
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static MapList getAllDerivedFromFMEA(Context context, String args[])
	throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);

			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList ownedIdList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_DERIVED_FROM, TYPE_FMEA, objSelects, slRelSel, true,
					true, (short) 1, "revision==last", null);			
			return ownedIdList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeFMEAAlreadyConnectedToControlPlans(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList mlList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_CONTROL_PLAN, TYPE_FMEA, objSelects, slRelSel, true,
					true, (short) 1, "revision==last", null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeRequirementAndRequirementSpecificationAlreadyFMEA(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			String typePatternReq = "Requirement" + ","
			+ "Requirement Specification";
			MapList mlList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_REQUIREMENT, typePatternReq, objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeRelatedSystemPart(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);

			MapList mlList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_PART_STRUCTURE, DomainConstants.QUERY_WILDCARD, objSelects, slRelSel,
					false, true, (short) 0, null, null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeSupplimentK(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);

			MapList mlList = doObj.getRelatedObjects(context,
					"Reference Document", DomainConstants.QUERY_WILDCARD, objSelects, slRelSel,
					false, true, (short) 0, null, null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Q53 
	 * Added for team Member
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeTeamMembersAlreadyConnectedToFMEA(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			String typePatternReq = DomainConstants.TYPE_PERSON;
			MapList mlList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_TEAM_PERSONS, typePatternReq, objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53 
	 * Added for team Member
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeLogicalRefernceAlreadyConnectedToFMEA(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			Pattern typePattern = new Pattern("VPLMtyp/RFLPLMFunctionalReference");
			typePattern.addPattern("VPLMtyp/RFLVPMLogicalReference");
			MapList mlList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_FUNCTION_REFERENCE, typePattern.getPattern(), objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeRequirementAlreadyFailureMode(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList mlList = doObj.getRelatedObjects(context,
					RELATIONSHIP_FMEA_REQUIREMENT, "Requirement", objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static StringList excludeProcessHeaderAlreadyConnected(
			Context context, String args[]) throws Exception {
		try {
			// Add the selectables
			HashMap programMap = (HashMap) JPO.unpackArgs(args);
			String objectId = (String) programMap.get("objectId");
			StringList objSelects = new StringList();
			objSelects.addElement(DomainConstants.SELECT_ID);
			objSelects.addElement(DomainConstants.SELECT_CURRENT);
			StringBuffer sbWhere = new StringBuffer(64);
			sbWhere.append("policy!=Version");
			DomainObject dObj = DomainObject.newInstance(context);
			dObj.setId(objectId);
			StringList slexclude = new StringList();
			StringList slRelSel = new StringList(1);
			slRelSel.add(DomainRelationship.SELECT_ID);
			DomainObject doObj = DomainObject.newInstance(context);
			doObj.setId(objectId);
			MapList mlList = doObj.getRelatedObjects(context,
					RELATIONSHIP_OPERATION_PROCESS_HEADER, TYPE_PERSON, objSelects, slRelSel,
					true, true, (short) 1, "revision==last", null);

			if (mlList != null && mlList.size() > 0) {

				int mapsize = mlList.size();
				for (int j = 0; j < mlList.size(); j++) {
					Map m = (Map) mlList.get(j);
					String sId = (String) m.get("id");
					slexclude.add(sId);
				}
			}
			return slexclude;
		} catch (Exception e) {
			throw e;
		}
	}


	/**
	 * Q53 include Failure Mode ID
	 * @param context
	 * @param args
	 * @return
	 * @throws MatrixException
	 */
	public StringList getFailureModeIds(Context context, String[] args) throws MatrixException {
		try{
			//HashMap programMap = (HashMap) JPO.unpackArgs(args);
			StringList failureModeEffect = new StringList();
			String parentType =DomainConstants.EMPTY_STRING; 
			StringList strList = new StringList();
			strList.add(SELECT_NAME);
			strList.add(SELECT_TYPE);
			strList.add(SELECT_ID);			
			MapList mlFailureMode = DomainObject.findObjects(context, TYPE_FAILURE_MODE, "*", null, strList);
			for(int itr = 0; itr < mlFailureMode.size(); itr++){
				Map map = (Map)mlFailureMode.get(itr);
				parentType = (String)map.get(SELECT_TYPE);
				if(null != parentType && !parentType.equalsIgnoreCase(TYPE_FAILURE_MODE_EFFECT)){
					failureModeEffect.add(map.get(DomainConstants.SELECT_ID));
				}
			}
			return failureModeEffect;
		}
		catch(Exception ex){
			throw new MatrixException(ex);
		}
	}
	
	/**
	 * Q53 include Recommended Action ID
	 * @param context
	 * @param args
	 * @return
	 * @throws MatrixException
	 * @author rneema1
	 * CR - 15128
	 */
	public StringList getRecommendedActionIds(Context context, String[] args) throws MatrixException {
		try{
			//HashMap programMap = (HashMap) JPO.unpackArgs(args);
			StringList slRecommendedActions = new StringList();
			String parentType =DomainConstants.EMPTY_STRING; 
			StringList strList = new StringList();
			strList.add(SELECT_NAME);
			strList.add(SELECT_TYPE);
			strList.add(SELECT_ID);			
			MapList mlRecommendedAction = DomainObject.findObjects(context, TYPE_RECOMMENDED_ACTION, "*", null, strList);
			for(int itr = 0; itr < mlRecommendedAction.size(); itr++)
			{
				Map map = (Map)mlRecommendedAction.get(itr);
				parentType = (String)map.get(SELECT_TYPE);
				if(null != parentType && parentType.equalsIgnoreCase(TYPE_RECOMMENDED_ACTION)){
					slRecommendedActions.add(map.get(DomainConstants.SELECT_ID));
				}
			}
			return slRecommendedActions;
		}
		catch(Exception ex){
			throw new MatrixException(ex);
		}
	}
	
	/**
	 * Q53 include Failure Mode Cause IDs
	 * @param context
	 * @param args
	 * @return
	 * @throws MatrixException
	 * @author rneema1
	 * CR - 15128
	 */
	public StringList getFailureModeCauseIds(Context context, String[] args) throws MatrixException {
		try{
			//HashMap programMap = (HashMap) JPO.unpackArgs(args);
			StringList failureModeEffect = new StringList();
			String parentType =DomainConstants.EMPTY_STRING; 
			StringList strList = new StringList();
			strList.add(SELECT_NAME);
			strList.add(SELECT_TYPE);
			strList.add(SELECT_ID);			
			MapList mlFailureModeCause = DomainObject.findObjects(context, TYPE_FAILURE_MODE_CAUSE, "*", null, strList);
			for(int itr = 0; itr < mlFailureModeCause.size(); itr++)
			{
				Map map = (Map)mlFailureModeCause.get(itr);
				parentType = (String)map.get(SELECT_TYPE);
				if(null != parentType && parentType.equalsIgnoreCase(TYPE_FAILURE_MODE_CAUSE)){
					failureModeEffect.add(map.get(DomainConstants.SELECT_ID));
				}
			}
			return failureModeEffect;
		}
		catch(Exception ex){
			throw new MatrixException(ex);
		}
	}
		

	/**
	 * Changed by Q53 To set the CLASS value
	 * 
	 * @param context
	 * @param args
	 * @throws Exception
	 */
	public void setRPNValue(Context context, String args[]) throws Exception {

		try {
			String sObjectId = args[0];
			String sAttrName = args[2];
			String sAttrValue = args[3];
			DomainObject dObjectCause = new DomainObject();
			String sAttrValueSeverity =DomainConstants.EMPTY_STRING;
			String sAttrValueDetection =DomainConstants.EMPTY_STRING;
			String sAttrValueOccurence =DomainConstants.EMPTY_STRING;
			String strId =DomainConstants.EMPTY_STRING;
			String severity =DomainConstants.EMPTY_STRING;
			String strIdNew =DomainConstants.EMPTY_STRING;
			String detection =DomainConstants.EMPTY_STRING;
			String occurence =DomainConstants.EMPTY_STRING;
			String strAttributeAssemblyEffect=DomainConstants.EMPTY_STRING;
			boolean isProcessFMEA=false;
			if (isNotBlank(sObjectId) && isNotBlank(sAttrName)) {
				//Added to check the FMEA type
				String fmeaId=getFMEAId(context, sObjectId);				
				if(UIUtil.isNotNullAndNotEmpty(fmeaId))
				{
					DomainObject dObjectFMEA = DomainObject.newInstance(context,
							fmeaId);
					String fmeaTypeAttribute=dObjectFMEA.getInfo(context, SELECT_ATTRIBUTE_FMEA_TYPE);
					if(fmeaTypeAttribute.equals("Process") || fmeaTypeAttribute.equals("Foundation Process")|| fmeaTypeAttribute.equals("Concept Process")){

						isProcessFMEA=true;				

					}
				}
				DomainObject dObject = DomainObject.newInstance(context,
						sObjectId);
				String strType = dObject.getInfo(context, SELECT_TYPE);
				//Commented for INC-16331 Start
				/*if (strType.equals(TYPE_RECOMMENDED_ACTION)
						&& ((sAttrName.equals(ATTRIBUTE_DETECTION) && sAttrValue
								.equals(DomainConstants.EMPTY_STRING))
								|| (sAttrName.equals(ATTRIBUTE_OCCURENCE) && sAttrValue
										.equals(DomainConstants.EMPTY_STRING)) || (sAttrName
												.equals(ATTRIBUTE_FM_SEVERITY) && sAttrValue.equals(DomainConstants.EMPTY_STRING)))) {
					dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
				}
				Commented for INC-16331 End*/
				
				//Added for INC-16331 Start
				if(strType.equals(TYPE_RECOMMENDED_ACTION)){
					if((sAttrName.equals(ATTRIBUTE_REVISED_DETECTION) || sAttrName.equals(ATTRIBUTE_REVISED_OCCURRENCE) || sAttrName.equals(ATTRIBUTE_REVISED_SEVERITY))){
						if(sAttrValue.equals(DomainConstants.EMPTY_STRING)){
							dObject.setAttributeValue(context, ATTRIBUTE_REVISED_RPN, DomainConstants.EMPTY_STRING);
						}
						else {
							setRARevisedRPNValue(context,dObject,sAttrName,sAttrValue);
						}
					}
				}
				//Added for INC-16331 End
				
				if (strType.equals(TYPE_FAILURE_MODE_CAUSE)) {
					if ((sAttrName.equals(ATTRIBUTE_DETECTION) && sAttrValue.equals(DomainConstants.EMPTY_STRING))
							|| (sAttrName.equals(ATTRIBUTE_OCCURENCE) && sAttrValue
									.equals(DomainConstants.EMPTY_STRING))) {
								
						// Added on 8/7/2014 for issue when Detection and Occurenec is blank and Severity 9 or 10 then Class should be calculated :START
						dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
						
						StringList selectStmts = new StringList(2);
						selectStmts.addElement(SELECT_ID);
						StringList RelSelectsList = new StringList(
								SELECT_RELATIONSHIP_ID);
						MapList partFamilyList = dObject.getRelatedObjects(
								context, RELATIONSHIP_FAILURE_MODE_CAUSE, // relationship
								// pattern
								QUERY_WILDCARD, // object pattern
								selectStmts, // object selects
								RelSelectsList, // relationship selects
								true, // to direction
								false, // from direction
								(short) 1, // recursion level
								null, // object where clause
								null);

						if (partFamilyList != null && partFamilyList.size() > 0) {
							// construct array of ids
							int partFamilyListSize = partFamilyList.size();
							for (int i = 0; i < partFamilyListSize; i++) {
								strId = (String) ((Map) partFamilyList.get(i))
								.get(SELECT_ID);

							}
						}

						if (strId != null && !strId.equals(DomainConstants.EMPTY_STRING)) {
							DomainObject dObjectEffect = DomainObject
							.newInstance(context, strId);
							severity = dObjectEffect.getAttributeValue(context,
									ATTRIBUTE_FM_SEVERITY);
							if(isProcessFMEA){
								strAttributeAssemblyEffect = dObjectEffect.getAttributeValue(context,
										ATTRIBUTE_ASSEMBLY_EFFECT);
							}
							sAttrValueSeverity = severity;

						}
						int sev=0;
						if(!sAttrValueSeverity.equals(DomainConstants.EMPTY_STRING))
								{
						         sev = Integer.parseInt(sAttrValueSeverity);
						         
								}

						 if(isProcessFMEA)
							{	
							 int occ=0;
								String strOccValue=dObject.getAttributeValue(context, ATTRIBUTE_OCCURENCE);
								if(UIUtil.isNotNullAndNotEmpty(strOccValue))
								{
									occ=Integer.parseInt(strOccValue);
								}
								
								if (((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("Yes")) {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "OS");
								}else if(((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("No"))
								{
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "CC");
								}else if ((sev == 5 || sev == 6 || sev == 7 || sev == 8)&& (occ == 4 || occ == 5 || occ == 6 || occ == 7 || occ == 8 || occ == 9 || occ == 10) && strAttributeAssemblyEffect.equals("Yes")) {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "HI");
								}else if((sev == 5 || sev == 6 || sev == 7 || sev == 8)&& (occ == 4 || occ == 5 || occ == 6 || occ == 7 || occ == 8 || occ == 9 || occ == 10) && strAttributeAssemblyEffect.equals("No"))
								{
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "SC");
								}else {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
								}

							}else{
								   ///get occurrence value here and set the class added on 24/11/2014
								int occ=0;
								String strOccValue=dObject.getAttributeValue(context, ATTRIBUTE_OCCURENCE);
								if(UIUtil.isNotNullAndNotEmpty(strOccValue))
								{
									occ=Integer.parseInt(strOccValue);
								}
								if ((sev == 9) || (sev == 10)) {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "YC");
								}else if (((sev == 5 || sev == 6 || sev == 7 || sev == 8)&& (occ == 4 || occ == 5 || occ == 6 || occ == 7 || occ == 8 || occ == 9 || occ == 10)))
								{
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "YS");
									
								}
								else {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
								}
							}
						//dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
						// Added on 8/7/2014 for issue when Detection and Occurenec is blank and Severity 9 or 10 then Class should be calculated :END
						
					} else {

						StringList selectStmts = new StringList(2);
						selectStmts.addElement(SELECT_ID);
						StringList RelSelectsList = new StringList(
								SELECT_RELATIONSHIP_ID);
						MapList partFamilyList = dObject.getRelatedObjects(
								context, RELATIONSHIP_FAILURE_MODE_CAUSE, // relationship
								// pattern
								QUERY_WILDCARD, // object pattern
								selectStmts, // object selects
								RelSelectsList, // relationship selects
								true, // to direction
								false, // from direction
								(short) 1, // recursion level
								null, // object where clause
								null);

						if (partFamilyList != null && partFamilyList.size() > 0) {
							// construct array of ids
							int partFamilyListSize = partFamilyList.size();
							for (int i = 0; i < partFamilyListSize; i++) {
								strId = (String) ((Map) partFamilyList.get(i))
								.get(SELECT_ID);

							}
						}

						if (strId != null && !strId.equals(DomainConstants.EMPTY_STRING)) {
							DomainObject dObjectEffect = DomainObject
							.newInstance(context, strId);
							severity = dObjectEffect.getAttributeValue(context,
									ATTRIBUTE_FM_SEVERITY);
							if(isProcessFMEA){
								strAttributeAssemblyEffect = dObjectEffect.getAttributeValue(context,
										ATTRIBUTE_ASSEMBLY_EFFECT);
							}
							sAttrValueSeverity = severity;

						}
					}
				} else if (strType.equals(TYPE_FAILURE_MODE)) {

					strAttributeAssemblyEffect = dObject.getInfo(context, SELECT_ATTRIBUTE_ASSEMBLY_EFFECT);

					sAttrValue = dObject.getInfo(context, SELECT_ATTRIBUTE_FM_SEVERITY);
					StringList selectStmtsNew = new StringList(2);
					selectStmtsNew.addElement(SELECT_ID);
					StringList RelSelectsListNew = new StringList(
							SELECT_RELATIONSHIP_ID);
					MapList partFamilyListNew = dObject.getRelatedObjects(
							context, RELATIONSHIP_FAILURE_MODE_CAUSE, // relationship pattern
							QUERY_WILDCARD, // object pattern
							selectStmtsNew, // object selects
							RelSelectsListNew, // relationship selects
							false, // to direction
							true, // from direction
							(short) 1, // recursion level
							null, // object where clause
							null);

					if (partFamilyListNew != null
							&& partFamilyListNew.size() > 0) {
						// construct array of ids
						int partFamilyListSize = partFamilyListNew.size();
						for (int i = 0; i < partFamilyListSize; i++) {
							strIdNew = (String) ((Map) partFamilyListNew.get(i))
							.get(SELECT_ID);

							if (strIdNew != null && !strIdNew.equals(DomainConstants.EMPTY_STRING)) {
								dObjectCause = DomainObject.newInstance(
										context, strIdNew);

								detection = dObjectCause.getAttributeValue(
										context,  ATTRIBUTE_DETECTION);
								occurence = dObjectCause.getAttributeValue(
										context, ATTRIBUTE_OCCURENCE);
								sAttrValueOccurence = occurence;
								sAttrValueDetection = detection;

							}
							if (!(sAttrName.equals(ATTRIBUTE_FM_SEVERITY) && sAttrValue
									.equals(DomainConstants.EMPTY_STRING))
									&& !occurence.equals(DomainConstants.EMPTY_STRING)
									&& !detection.equals(DomainConstants.EMPTY_STRING)) {
								//Changed this for Number Format Exception..
								int sev=0;
								int det=0;
								int occ=0 ;
																
								if(!sAttrValue.equals(DomainConstants.EMPTY_STRING) && !sAttrValueDetection.equals(DomainConstants.EMPTY_STRING) && !sAttrValueOccurence.equals(DomainConstants.EMPTY_STRING))
								{
									sev = Integer.parseInt(sAttrValue);
									det = Integer.parseInt(sAttrValueDetection);
									occ = Integer.parseInt(sAttrValueOccurence);
									int rpn = sev * det * occ;
									dObjectCause.setAttributeValue(context, ATTRIBUTE_RPN,
											rpn + DomainConstants.EMPTY_STRING);
									
									
								}else{
									
									dObjectCause.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
									
								}
								//Previous
								//int sev = Integer.parseInt(sAttrValue);
								//int det = Integer.parseInt(sAttrValueDetection);
								//int occ = Integer.parseInt(sAttrValueOccurence);
								//int rpn = sev * det * occ;
								//dObjectCause.setAttributeValue(context, ATTRIBUTE_RPN,
									//	rpn + DomainConstants.EMPTY_STRING);
								
								
							 if(isProcessFMEA)
								{									
									if (((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("Yes")) {
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "OS");
									}else if(((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("No"))
									{
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "CC");
									}else if (((sev == 5 || sev == 6 || sev == 7 || sev == 8)
											&& (occ == 4 || occ == 5 || occ == 6
													|| occ == 7 || occ == 8
													|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("Yes")) {
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "HI");
									}else if(((sev == 5 || sev == 6 || sev == 7 || sev == 8)
											&& (occ == 4 || occ == 5 || occ == 6
													|| occ == 7 || occ == 8
													|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("No"))
									{

										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "SC");

									}else {
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
									}

								}else{
									if ((sev == 9) || (sev == 10)) {
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "YC");
									} else if ((sev == 5 || sev == 6 || sev == 7 || sev == 8)
											&& (occ == 4 || occ == 5 || occ == 6
													|| occ == 7 || occ == 8
													|| occ == 9 || occ == 10)) {
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "YS");
									} else {
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
									}
								}
							} else {

								dObjectCause.setAttributeValue(context, ATTRIBUTE_RPN,
										DomainConstants.EMPTY_STRING);
								
								//Here Added  to check Severity Value if is it 9 or 10 Changed on 05/06/2014
								if(!isProcessFMEA){
								if(!sAttrValue.equals(DomainConstants.EMPTY_STRING))
								{
									int intSevValue=Integer.parseInt(sAttrValue);
									//Added on 24/11/2014
									int occ=0;
									if(UIUtil.isNotNullAndNotEmpty(sAttrValueOccurence))
									{
										occ=Integer.parseInt(sAttrValueOccurence);
									}
									
									
									if ((intSevValue == 9) || (intSevValue == 10)) {
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "YC");
									} else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)&&(occ == 4 || occ == 5 || occ == 6	|| occ == 7 || occ == 8	|| occ == 9 || occ == 10))
									{
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, "YS");
										
									}else{
										
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
										
									}

								}else{
									dObjectCause.setAttributeValue(context,
											ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);

								}
								}else if(isProcessFMEA){

									if(!sAttrValue.equals(DomainConstants.EMPTY_STRING))
									{
										
										//Added on 24/11/2014
										int occ=0;
										if(UIUtil.isNotNullAndNotEmpty(sAttrValueOccurence))
										{
											occ=Integer.parseInt(sAttrValueOccurence);
										}
										
										int intSevValue=Integer.parseInt(sAttrValue);
										if (((intSevValue == 9) || (intSevValue == 10))&&  strAttributeAssemblyEffect.equals("Yes")) {
											dObjectCause.setAttributeValue(context,
													ATTRIBUTE_CLASS, "OS");
										}else if(((intSevValue == 9) || (intSevValue == 10))&&  strAttributeAssemblyEffect.equals("No")) 
										{
											dObjectCause.setAttributeValue(context,
													ATTRIBUTE_CLASS, "CC");

										}else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)&&(occ==4 || occ==5 || occ==6 || occ==7 || occ==8 || occ==9 || occ==10) && strAttributeAssemblyEffect.equals("Yes"))
										{
											dObjectCause.setAttributeValue(context,
													ATTRIBUTE_CLASS, "HI");

										}else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)&&(occ==4 || occ==5 || occ==6 || occ==7 || occ==8 || occ==9 || occ==10) && strAttributeAssemblyEffect.equals("No"))
										{
											dObjectCause.setAttributeValue(context,
													ATTRIBUTE_CLASS, "SC");

										}else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)&&(sAttrValueOccurence.equals(DomainConstants.EMPTY_STRING)))
										{
											dObjectCause.setAttributeValue(context,
													ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);

										}else{

											dObjectCause.setAttributeValue(context,
													ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);

										}
									}else{
										dObjectCause.setAttributeValue(context,
												ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
									}

									
								}else{
									
									dObjectCause.setAttributeValue(context,
											ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);

								}
								
								
							}
						}
					}

				}

				if (sAttrName.equalsIgnoreCase(ATTRIBUTE_OCCURENCE) && isNotBlank(sAttrValue)) {

					if (!strType.equals(TYPE_FAILURE_MODE_CAUSE)) {

						sAttrValueSeverity = dObject.getAttributeValue(context,
								ATTRIBUTE_FM_SEVERITY);
					}

					sAttrValueDetection = dObject.getAttributeValue(context,
							ATTRIBUTE_DETECTION);

					if (!((sAttrName.equals( ATTRIBUTE_DETECTION) && sAttrValue
							.equals(DomainConstants.EMPTY_STRING))
							|| (sAttrName.equals(ATTRIBUTE_OCCURENCE) && sAttrValue
									.equals(DomainConstants.EMPTY_STRING)) || (sAttrName
											.equals(ATTRIBUTE_FM_SEVERITY) && sAttrValue.equals(DomainConstants.EMPTY_STRING)))) {
						//Changed this for Number Format Exception
						int sev=0;
						int det=0;
						int occ=0 ;
						
						//Changed on 9/7/2014:START : if user changed Detection Value to blank and any vlaue of occurence then class is not getting updating
						//Added by rneema1 for Incident 15428 - Start
						if(UIUtil.isNotNullAndNotEmpty(sAttrValueSeverity))
						{
						sev = Integer.parseInt(sAttrValueSeverity);
						}
						//Added by rneema1 for Incident 15428 - END
						if(!sAttrValue.equals(DomainConstants.EMPTY_STRING) && !sAttrValueSeverity.equals(DomainConstants.EMPTY_STRING) && !sAttrValueDetection.equals(DomainConstants.EMPTY_STRING))
						{
							
							det = Integer.parseInt(sAttrValueDetection);
							occ = Integer.parseInt(sAttrValue);
							int rpn = sev * det * occ;
							dObject.setAttributeValue(context, ATTRIBUTE_RPN,
									rpn + DomainConstants.EMPTY_STRING);
							
							
						}else{
							
							dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
							
						}
						
						//Previous
						//int sev = Integer.parseInt(sAttrValueSeverity);
						//int det = Integer.parseInt(sAttrValueDetection);
						//int occ = Integer.parseInt(sAttrValue);
						//int rpn = sev * det * occ;						
						//dObject.setAttributeValue(context, ATTRIBUTE_RPN, rpn + DomainConstants.EMPTY_STRING);
						if(isProcessFMEA){
							//added on 24/11/2014
							if(UIUtil.isNotNullAndNotEmpty(sAttrValue))
							{
								occ=Integer.parseInt(sAttrValue);
							}

							if (((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("Yes")) {
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "OS");
							}else if(((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("No"))
							{
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "CC");
							}else if (((sev == 5 || sev == 6 || sev == 7 || sev == 8)
									&& (occ == 4 || occ == 5 || occ == 6
											|| occ == 7 || occ == 8
											|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("Yes")) {
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "HI");
							}else if(((sev == 5 || sev == 6 || sev == 7 || sev == 8)
									&& (occ == 4 || occ == 5 || occ == 6
											|| occ == 7 || occ == 8
											|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("No"))
							{

								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "SC");

							}else {
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
							}

						}else{
							//added on 24/11/2014
							if(UIUtil.isNotNullAndNotEmpty(sAttrValue))
							{
								occ=Integer.parseInt(sAttrValue);
							}

							if ((sev == 9) || (sev == 10)) {
								dObject.setAttributeValue(context, ATTRIBUTE_CLASS, "YC");
							} else if ((sev == 5 || sev == 6 || sev == 7 || sev == 8)&& (occ == 4 || occ == 5 || occ == 6 || occ == 7 || occ == 8 || occ == 9 || occ == 10)) {
								dObject.setAttributeValue(context, ATTRIBUTE_CLASS, "YS");
							} else {
								dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
							}
						}
					} else {
						dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
						dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
					}

				} else if (sAttrName.equalsIgnoreCase(ATTRIBUTE_FM_SEVERITY)
						&& isNotBlank(sAttrValue)) {
					sAttrValueOccurence = dObject.getAttributeValue(context,
							ATTRIBUTE_OCCURENCE);
					sAttrValueDetection = dObject.getAttributeValue(context,
							ATTRIBUTE_DETECTION);

					if (!strType.equals(TYPE_FAILURE_MODE)) {

						if (!((sAttrName.equals( ATTRIBUTE_DETECTION) && sAttrValue
								.equals(DomainConstants.EMPTY_STRING))
								|| (sAttrName.equals(ATTRIBUTE_OCCURENCE) && sAttrValue
										.equals(DomainConstants.EMPTY_STRING)) || (sAttrName
												.equals(ATTRIBUTE_FM_SEVERITY) && sAttrValue.equals(DomainConstants.EMPTY_STRING)))) {
							//Changed this for Number Format Exception
							int sev=0;
							int det=0;
							int occ=0 ;
															
							if(!sAttrValue.equals(DomainConstants.EMPTY_STRING) && !sAttrValueDetection.equals(DomainConstants.EMPTY_STRING) && !sAttrValueOccurence.equals(DomainConstants.EMPTY_STRING))
							{
								sev = Integer.parseInt(sAttrValue);
								det = Integer.parseInt(sAttrValueDetection);
								occ = Integer.parseInt(sAttrValueOccurence);
								int rpn = sev * det * occ;
								dObject.setAttributeValue(context, ATTRIBUTE_RPN,
										rpn + DomainConstants.EMPTY_STRING);
								
								
							}else{
								
								dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
								
							}
							//Previous
							//int sev = Integer.parseInt(sAttrValue);
							//int det = Integer.parseInt(sAttrValueDetection);
							//int occ = Integer.parseInt(sAttrValueOccurence);
							//int rpn = sev * det * occ;
							//dObject.setAttributeValue(context, ATTRIBUTE_RPN, rpn + DomainConstants.EMPTY_STRING);
							if(isProcessFMEA)
							{
								if (((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("Yes")) {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "OS");
								}else if(((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("No"))
								{
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "CC");
								}else if (((sev == 5 || sev == 6 || sev == 7 || sev == 8)
										&& (occ == 4 || occ == 5 || occ == 6
												|| occ == 7 || occ == 8
												|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("Yes")) {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "HI");
								}else if(((sev == 5 || sev == 6 || sev == 7 || sev == 8)
										&& (occ == 4 || occ == 5 || occ == 6
												|| occ == 7 || occ == 8
												|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("No"))
								{

									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, "SC");

								}else {
									dObject.setAttributeValue(context,
											ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
								}


							}else{
								if ((sev == 9) || (sev == 10)) {
									dObject.setAttributeValue(context, ATTRIBUTE_CLASS,
									"YC");
								} else if ((sev == 5 || sev == 6 || sev == 7 || sev == 8)
										&& (occ == 4 || occ == 5 || occ == 6
												|| occ == 7 || occ == 8 || occ == 9 || occ == 10)) {
									dObject.setAttributeValue(context, ATTRIBUTE_CLASS,
									"YS");
								} else {
									dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
								}
							}
						} else {
							dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
							dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);

						}
					}

				} else if (sAttrName.equalsIgnoreCase( ATTRIBUTE_DETECTION)
						&& isNotBlank(sAttrValue)) {
					sAttrValueOccurence = dObject.getAttributeValue(context,
							ATTRIBUTE_OCCURENCE);
					if (!strType.equals(TYPE_FAILURE_MODE_CAUSE)) {

						sAttrValueSeverity = dObject.getAttributeValue(context,
								ATTRIBUTE_FM_SEVERITY);
					}

					if (!((sAttrName.equals( ATTRIBUTE_DETECTION) && sAttrValue
							.equals(DomainConstants.EMPTY_STRING))
							|| (sAttrName.equals(ATTRIBUTE_OCCURENCE) && sAttrValue
									.equals(DomainConstants.EMPTY_STRING)) || (sAttrName
											.equals(ATTRIBUTE_FM_SEVERITY) && sAttrValue.equals(DomainConstants.EMPTY_STRING)))) {
                        //Changed this for Number Format Exception
						int sev=0;
						int det=0;
						int occ=0 ;		
						//Changed on 9/7/2014:START : if user changed Detection Value to blank and any vlaue of occurence then class is not getting updating
						//Added by rneema1 for Incident 15428 - Start
						if(UIUtil.isNotNullAndNotEmpty(sAttrValueSeverity))
						{
						sev = Integer.parseInt(sAttrValueSeverity);
						}
						//Added by rneema1 for Incident 15428 - End
						if(!sAttrValueSeverity.equals(DomainConstants.EMPTY_STRING) && !sAttrValue.equals(DomainConstants.EMPTY_STRING) && !sAttrValueOccurence.equals(DomainConstants.EMPTY_STRING))
						{
							
							det = Integer.parseInt(sAttrValue);
							occ = Integer.parseInt(sAttrValueOccurence);
							int rpn = sev * det * occ;
							dObject.setAttributeValue(context, ATTRIBUTE_RPN,
									rpn + DomainConstants.EMPTY_STRING);
							
							
						}else{
							
							dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
							
						}
						//Previous
						//int sev = Integer.parseInt(sAttrValueSeverity);
						//int det = Integer.parseInt(sAttrValue);
						//int occ = Integer.parseInt(sAttrValueOccurence);
						//int rpn = sev * det * occ;
						//dObject.setAttributeValue(context, ATTRIBUTE_RPN, rpn + DomainConstants.EMPTY_STRING);
						if(isProcessFMEA)
						{
							if (((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("Yes")) {
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "OS");
							}else if(((sev == 9) || (sev == 10))&& strAttributeAssemblyEffect.equals("No"))
							{
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "CC");
							}else if (((sev == 5 || sev == 6 || sev == 7 || sev == 8)
									&& (occ == 4 || occ == 5 || occ == 6
											|| occ == 7 || occ == 8
											|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("Yes")) {
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "HI");
							}else if(((sev == 5 || sev == 6 || sev == 7 || sev == 8)
									&& (occ == 4 || occ == 5 || occ == 6
											|| occ == 7 || occ == 8
											|| occ == 9 || occ == 10)) && strAttributeAssemblyEffect.equals("No"))
							{

								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, "SC");

							}else {
								dObject.setAttributeValue(context,
										ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
							}

						}else{
							if ((sev == 9) || (sev == 10)) {
								dObject.setAttributeValue(context, ATTRIBUTE_CLASS, "YC");
							} else if ((sev == 5 || sev == 6 || sev == 7 || sev == 8)
									&& (occ == 4 || occ == 5 || occ == 6
											|| occ == 7 || occ == 8 || occ == 9 || occ == 10)) {
								dObject.setAttributeValue(context, ATTRIBUTE_CLASS, "YS");
							} else {
								dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
							}
						}
					} else {
						dObject.setAttributeValue(context, ATTRIBUTE_RPN, DomainConstants.EMPTY_STRING);
						dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
					}
				}
			}

		} catch (Exception e) {
			//Commented due to NumberFormat exception in console
			e.printStackTrace();
			//Added for INC 16331 Start
			throw new Exception(e.getMessage());
			//Added for INC 16331 End
		}
	}
	/**
	 * Added Q53 on 20022014
	 * @param context
	 * @param objectId
	 * @return
	 * @throws Exception
	 */
	public String getFMEAId(Context context,String objectId)throws Exception
	{
		String fmeaId=DomainConstants.EMPTY_STRING;
		String selectFmeaId=DomainConstants.EMPTY_STRING;


		DomainObject dObj=null;
		String  type=DomainConstants.EMPTY_STRING;
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING)){
			dObj = DomainObject.newInstance(context,
					objectId);
			type=dObj.getInfo(context,SELECT_TYPE);
		}

		if(type.equals(TYPE_FUNCTION) )		{


			selectFmeaId="to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";	
			fmeaId=dObj.getInfo(context, selectFmeaId);			

		}else if(type.equals(TYPE_FAILURE_MODE)){		


			selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			fmeaId=dObj.getInfo(context, selectFmeaId);			


		}else if(type.equals(TYPE_FAILURE_MODE_CAUSE))	
		{
			selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE_CAUSE+"].from.to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			fmeaId=dObj.getInfo(context, selectFmeaId);			

		}else if(type.equals(TYPE_RECOMMENDED_ACTION))
		{

			selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE_CAUSE_RPN+"].from.to["+RELATIONSHIP_FAILURE_MODE_CAUSE+"].from.to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			fmeaId=dObj.getInfo(context, selectFmeaId);			


		}

		return fmeaId;

	}
	/**
	 * Changed by Q53 To set the CLASS value
	 * 
	 * @param context
	 * @param args
	 * @throws Exception
	 */
	
	/**
	 * Q53 Update RPN value Currently Not in use
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public void updateOccurence(Context context, String args[])
	throws Exception {
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String objectId = (String) paramMap.get("objectId");
		String newValue = (String) paramMap.get("New Value");
		DomainObject dObject = DomainObject.newInstance(context, objectId);
		String sAttrValueSeverity = dObject.getAttributeValue(context,
				ATTRIBUTE_FM_SEVERITY);
		String sAttrValueDetection = dObject.getAttributeValue(context,
				ATTRIBUTE_DETECTION);
		;
		String sAttrValueOccurence = dObject.getAttributeValue(context,
				ATTRIBUTE_OCCURENCE);
		int sev = Integer.parseInt(sAttrValueSeverity);
		int det = Integer.parseInt(sAttrValueDetection);
		int occ = Integer.parseInt(newValue);
		int rpn = sev * det * occ;
		dObject.setAttributeValue(context, ATTRIBUTE_RPN, rpn + "");

	}

	/**
	 * Added by Q53
	 * 
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public HashMap postProcessRefresh(Context context, String[] args)
	throws Exception {
		// unpack the incoming arguments
		HashMap inputMap = (HashMap) JPO.unpackArgs(args);

		HashMap returnMap = new HashMap(1);
		returnMap.put("Action", "refresh");
		return returnMap;

	}

	/**
	 * Q53 Added to not show the policy field
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean hidePolicyField(Context context, String args[])
	throws Exception {
		return false;
	}


	public boolean isFromFMEAEdit(Context context, String args[])throws Exception
	{
		boolean isFromFMEAEdit=false;
		HashMap programMap = (HashMap) JPO.unpackArgs(args);

		String isFromEdit = (String) programMap.get("isFromEdit");
		if(UIUtil.isNotNullAndNotEmpty(isFromEdit))
		{
			if(isFromEdit.equals("true"))
			{
				isFromFMEAEdit=true;

			}

		}
		return isFromFMEAEdit;


	}

	/**
	 * Q53 Added to not show commands
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isFromFMEA(Context context, String args[])
	throws Exception {
		boolean isFromFMEA=false;

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING))
		{
			DomainObject dObj = DomainObject.newInstance(context,
					objectId);
			String type=dObj.getInfo(context, DomainConstants.SELECT_TYPE);
			if(type.equals(TYPE_FMEA)|| type.equals(TYPE_FAILURE_MODE)|| type.equals(TYPE_FUNCTION)){

				isFromFMEA=true;


			}


		}	

		return isFromFMEA;

	}
	/**
	 * Where : In the Structure Browser, If the user doesn't have permission to
	 *          modify field then message throws when click on "Edit".
	 * How : Get the objectId from argument map update map with Action message.
	 *
	 * @param context the eMatrix <code>Context</code> object
	 * @param args holds the following input arguments:
	 *        0 - String containing the "columnMap"
	 *
	 * @returns HashMap
	 * @throws Exception if operation fails
	 * @since PMC V6R2008-1
	 */
	// Modified by rneema1 for incident - 15510
	public HashMap preProcessCheckForFMEAEdit (Context context, String[] args) throws Exception
	{
		// unpack the incoming arguments
		HashMap inputMap = (HashMap)JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) inputMap.get("paramMap");
		HashMap tableData = (HashMap) inputMap.get("tableData");
		MapList objectList = (MapList) tableData.get("ObjectList");
		String strObjectId = (String) paramMap.get("objectId");
		
		//Added by rneema1 for incident - 15510 : Start
		StringList sTeamMemberListFromChecklist = new StringList();
		StringList sTeamMemberListFromPostWorkList = new StringList();
		//Added by rneema1 for incident - 15510 : End
		com.matrixone.apps.common.Person person = com.matrixone.apps.common.Person.getPerson(context);
		//Get FMEA Administrator role from property
		final String ROLE_FMEA_ADMINISTRATOR=PropertyUtil.getSchemaProperty("role_FMEAAdministrator");
	 
	    
		HashMap returnMap = null;
		DomainObject dom = DomainObject.newInstance(context, strObjectId);
		// Checking Access
		boolean editFlag = false;
		          
		String sFMEAState =DomainConstants.EMPTY_STRING;
		StringList busSelect = new StringList();		
		busSelect.add(SELECT_CURRENT);
		//added by rneema1 for incident - 13649 : start
		busSelect.add("to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		busSelect.add("to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		busSelect.add("to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		busSelect.add("to[Failure Mode Cause RPN].from.to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		//Added by rneema1 for incident - 15510 : Start
		busSelect.add("to[Checklist].from.from[FMEA Team Persons].businessobject.name");
		busSelect.add("to[FMEAPostWorkCheckList].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : End
		//added by rneema1 for incident - 13649 : End
		busSelect.add("from[FMEA Owner].businessobject.name");
		//Adding selectable in multivalue
		
		DomainConstants.MULTI_VALUE_LIST.add("from[FMEA Team Persons].businessobject.name");
		//added by rneema1 for incident - 13649 : start
		DomainConstants.MULTI_VALUE_LIST.add("to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.add("to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.add("to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.add("to[Failure Mode Cause RPN].from.to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : Start
		DomainConstants.MULTI_VALUE_LIST.add("to[Checklist].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.add("to[FMEAPostWorkCheckList].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : End
		//added by rneema1 for incident - 13649 : End
		busSelect.add("from[FMEA Team Persons].businessobject.name");
		//added by rneema1 for incident - 13649 : start
		busSelect.add("to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		busSelect.add("to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		busSelect.add("to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		busSelect.add("to[Failure Mode Cause RPN].from.to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : Start
		busSelect.add("to[Checklist].from.from[FMEA Team Persons].businessobject.name");
		busSelect.add("to[FMEAPostWorkCheckList].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : End
		//added by rneema1 for incident - 13649 : End
		busSelect.add(SELECT_OWNER);
	      //Get Context User
		 String contextUser=context.getUser();
		 
		//Get Information of Object
		Map mpParentInfo = dom.getInfo(context, busSelect);	
		//Removed multi value list after getInfo
		DomainConstants.MULTI_VALUE_LIST.remove("from[FMEA Team Persons].businessobject.name");
		//added by rneema1 for incident - 13649 : start
		DomainConstants.MULTI_VALUE_LIST.remove("to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.remove("to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.remove("to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.remove("to[Failure Mode Cause RPN].from.to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : Start
		DomainConstants.MULTI_VALUE_LIST.remove("to[Checklist].from.from[FMEA Team Persons].businessobject.name");
		DomainConstants.MULTI_VALUE_LIST.remove("to[FMEAPostWorkCheckList].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : End
		//added by rneema1 for incident - 13649 : End
		//get FMEA Owner
		String fmeaOwner=(String)mpParentInfo.get("from[FMEA Owner].businessobject.name");
		//added by rneema1 for incident - 13649 : start
		String FNfmeaOwner=(String)mpParentInfo.get("to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		String FMfmeaOwner=(String)mpParentInfo.get("to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		String FCfmeaOwner=(String)mpParentInfo.get("to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		String RAfmeaOwner=(String)mpParentInfo.get("to[Failure Mode Cause RPN].from.to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Owner].businessobject.name");
		//added by rneema1 for incident - 13649 : End
		//Added by rneema1 for incident - 15510 : Start
		sTeamMemberListFromChecklist = (StringList)mpParentInfo.get("to[Checklist].from.from[FMEA Team Persons].businessobject.name");
		sTeamMemberListFromPostWorkList = (StringList)mpParentInfo.get("to[FMEAPostWorkCheckList].from.from[FMEA Team Persons].businessobject.name");
		//Added by rneema1 for incident - 15510 : End
		boolean isFMEAOwnerExist=false;
		if(UIUtil.isNotNullAndNotEmpty(fmeaOwner))
		{
			if(fmeaOwner.equals(contextUser))
			{
				isFMEAOwnerExist=true;

			}

		}
		//Added by rneema1 for incident - 15510 : Start
		boolean isCheckListOwnerExist = false;
		if(null != sTeamMemberListFromChecklist && !sTeamMemberListFromChecklist.isEmpty())
		{
			if(sTeamMemberListFromChecklist.contains(contextUser))
			{
				isCheckListOwnerExist=true;

			}

		}
		
		boolean isFMEATeamMemberExist = false;
		if(null != sTeamMemberListFromPostWorkList && !sTeamMemberListFromPostWorkList.isEmpty())
		{
			if(sTeamMemberListFromPostWorkList.contains(contextUser))
			{
				isFMEATeamMemberExist=true;

			}

		}
		//Added by rneema1 for incident - 15510 : End
				
		//added by rneema1 for incident - 13649 : start
		boolean isFNFMEAOwnerExist=false;
		if(UIUtil.isNotNullAndNotEmpty(FNfmeaOwner))
		{
			if(FNfmeaOwner.equals(contextUser))
			{
				isFNFMEAOwnerExist=true;

			}

		}
		
		boolean isFMFMEAOwnerExist=false;
		if(UIUtil.isNotNullAndNotEmpty(FMfmeaOwner))
		{
			if(FMfmeaOwner.equals(contextUser))
			{
			    isFMFMEAOwnerExist=true;

			}

		}
		boolean isFCFMEAOwnerExist=false;
		if(UIUtil.isNotNullAndNotEmpty(FCfmeaOwner))
		{
			if(FCfmeaOwner.equals(contextUser))
			{
			    isFCFMEAOwnerExist=true;

			}

		}
		
		boolean isRAFMEAOwnerExist=false;
		if(UIUtil.isNotNullAndNotEmpty(RAfmeaOwner))
		{
			if(RAfmeaOwner.equals(contextUser))
			{
			    isRAFMEAOwnerExist=true;

			}

		}
		//added by rneema1 for incident - 13649 : End
		//Get FMEA Team Persons
		StringList slfmeaTeamPersons=(StringList)mpParentInfo.get("from[FMEA Team Persons].businessobject.name");
		//added by rneema1 for incident - 13649 : start
		StringList slFNfmeaTeamPersons=(StringList)mpParentInfo.get("to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		StringList slFMfmeaTeamPersons=(StringList)mpParentInfo.get("to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		StringList slFCfmeaTeamPersons=(StringList)mpParentInfo.get("to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		StringList slRAfmeaTeamPersons=(StringList)mpParentInfo.get("to[Failure Mode Cause RPN].from.to[Failure Mode Cause].from.to[Failure Mode].from.to[Operation FMEA].from.from[FMEA Team Persons].businessobject.name");
		//added by rneema1 for incident - 13649 : End
		boolean isTeamPersonExists=false;
		//Check if the context user is a team member...
		if(slfmeaTeamPersons!=null)
		{
			for(int i=0;i<slfmeaTeamPersons.size();i++)
			{
				String strTeamPerson=(String)slfmeaTeamPersons.get(i);
				if(strTeamPerson.equals(contextUser))
				{
					isTeamPersonExists=true;
					break;					
					
				}
				
				
			}
			
		}
		//added by rneema1 for incident - 13649 : start
		boolean isFNTeamPersonExists=false;
		if(slFNfmeaTeamPersons!=null)
		{
			for(int i=0;i<slFNfmeaTeamPersons.size();i++)
			{
				String strTeamPerson=(String)slFNfmeaTeamPersons.get(i);
				if(strTeamPerson.equals(contextUser))
				{
					isFNTeamPersonExists=true;
					break;					
					
				}
				
				
			}
			
		}
		
		boolean isFMTeamPersonExists=false;
		if(slFMfmeaTeamPersons!=null)
		{
			for(int i=0;i<slFMfmeaTeamPersons.size();i++)
			{
				String strTeamPerson=(String)slFMfmeaTeamPersons.get(i);
				if(strTeamPerson.equals(contextUser))
				{
					isFMTeamPersonExists=true;
					break;					
					
				}
				
				
			}
			
		}
		
		boolean isFCTeamPersonExists=false;
		if(slFCfmeaTeamPersons!=null)
		{
			for(int i=0;i<slFCfmeaTeamPersons.size();i++)
			{
				String strTeamPerson=(String)slFCfmeaTeamPersons.get(i);
				if(strTeamPerson.equals(contextUser))
				{
					isFCTeamPersonExists=true;
					break;					
					
				}
				
				
			}
			
		}
		
		boolean isRATeamPersonExists=false;
		if(slRAfmeaTeamPersons!=null)
		{
			for(int i=0;i<slRAfmeaTeamPersons.size();i++)
			{
				String strTeamPerson=(String)slRAfmeaTeamPersons.get(i);
				if(strTeamPerson.equals(contextUser))
				{
					isRATeamPersonExists=true;
					break;					
					
				}
				
				
			}
			
		}
		//added by rneema1 for incident - 13649 : End       	
		//Get FMEA Creator
		String fmeaContextOwner=(String)mpParentInfo.get(SELECT_OWNER);   
		//Select state of FMEA
		sFMEAState=(String)mpParentInfo.get(SELECT_CURRENT);
		//Modified by rneema1 for incident - 13649 and 15510
		//Modified by skovvuru for incident - 15095 Start
		String languageStr = context.getSession().getLanguage();
		if((isFMEAOwnerExist || isFNFMEAOwnerExist || isFMFMEAOwnerExist || isFCFMEAOwnerExist || isRAFMEAOwnerExist || isTeamPersonExists || isFNTeamPersonExists || isFMTeamPersonExists || isFCTeamPersonExists || isRATeamPersonExists || fmeaContextOwner.equals(contextUser) || person.hasRole(context,ROLE_FMEA_ADMINISTRATOR) || isCheckListOwnerExist || isFMEATeamMemberExist) && (!sFMEAState.equals("Approved") && !sFMEAState.equals("Obsolete")))
		{//Modified by skovvuru for incident - 15095 End
			editFlag=true;

		}

		//check condition 
		if(editFlag){
			returnMap = new HashMap(2);
			returnMap.put("Action","Continue");
			returnMap.put("ObjectList",objectList);
		} else {
			returnMap = new HashMap(3);
			returnMap.put("Action","Stop");
			//Modified by skovvuru for incident - 15095 Start
			returnMap.put("Message",EnoviaResourceBundle.getProperty(context, "ProgramCentral","emxFMEA.Approved.Structure.NoEdit", languageStr));
			//Modified by skovvuru for incident - 15095 End
			returnMap.put("ObjectList",objectList);
		}
		return returnMap;

	}


	/**
	 * Q53 Test
	 * @param context
	 * @param args
	 * @throws Exception
	 */
	public boolean preProcessCheckForFMEAStructureEdit (Context context, String[] args) throws Exception
	{
		boolean editFlag=false;
		boolean isAccess=false;
		HashMap returnMap = null;
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		//HashMap paramMap = (HashMap) programMap.get("paramMap");
		String objectId = (String) programMap.get("objectId");

		DomainObject dObj=null;
		DomainObject dObjFMEA=null;
		String selectFmeaId=DomainConstants.EMPTY_STRING;
		String fmeaTypeId=DomainConstants.EMPTY_STRING;
		String fmeaState=DomainConstants.EMPTY_STRING;
		String  type=DomainConstants.EMPTY_STRING;
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING)){
			dObj = DomainObject.newInstance(context,
					objectId);
			type=dObj.getInfo(context,SELECT_TYPE);
		}
		if(type.equals(TYPE_FMEA))
		{
			dObjFMEA = DomainObject.newInstance(context,
					objectId);
			fmeaState=dObjFMEA.getInfo(context, SELECT_CURRENT);
			if(fmeaState.equals("Approved")){

				editFlag=true;


			}

		}else if(type.equals(TYPE_FUNCTION) )
		{

			//selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";	
			selectFmeaId="to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";	
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			dObjFMEA = DomainObject.newInstance(context,
					fmeaTypeId);
			fmeaState=dObjFMEA.getInfo(context, SELECT_CURRENT);
			if(fmeaState.equals("Approved")){

				editFlag=true;


			}
		}else if(type.equals(TYPE_FAILURE_MODE)){		

			//	selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE_EFFECT+"].from.to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			dObjFMEA = DomainObject.newInstance(context,
					fmeaTypeId);
			fmeaState=dObjFMEA.getInfo(context, SELECT_CURRENT);
			if(fmeaState.equals("Approved")){

				editFlag=true;

			}

		}else if(type.equals(TYPE_FAILURE_MODE_CAUSE))	
		{
			selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE_CAUSE+"].from.to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			dObjFMEA = DomainObject.newInstance(context,
					fmeaTypeId);
			fmeaState=dObjFMEA.getInfo(context, SELECT_CURRENT);
			if(fmeaState.equals("Approved")){

				editFlag=true;

			}
		}else if(type.equals(TYPE_RECOMMENDED_ACTION))
		{

			selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE_CAUSE_RPN+"].from.to["+RELATIONSHIP_FAILURE_MODE_CAUSE+"].from.to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			dObjFMEA = DomainObject.newInstance(context,
					fmeaTypeId);
			fmeaState=dObjFMEA.getInfo(context, SELECT_CURRENT);
			if(fmeaState.equals("Approved")){

				editFlag=true;

			}

		}

		if(editFlag){
			//returnMap = new HashMap(2);
			//returnMap.put("Action","Continue");
			isAccess=false;


		} else {
			//returnMap = new HashMap(3);
			//returnMap.put("Action","Stop");
			//returnMap.put("Message","emxComponents.FMEA.Structure.NoEdit");

			isAccess=true;

		}
		return isAccess;


	}
	/**
	 * Q53 Added to not show commands
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isFMEAStateApproved(Context context, String args[])
	throws Exception {
		boolean isFMEAApproved=false;

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING))
		{
			DomainObject dObj = DomainObject.newInstance(context,
					objectId);
			String currentState=dObj.getInfo(context, DomainConstants.SELECT_CURRENT);
			if(!currentState.equals("Approved")){

				isFMEAApproved=true;


			}


		}	

		return isFMEAApproved;

	}
	/**
	 * Q53 Added to not show commands
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isProcessFMEA(Context context, String args[])
	throws Exception {
		boolean isProcessFMEA=false;

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING))
		{
			DomainObject dObj = DomainObject.newInstance(context,
					objectId);
			String fmeatypeattribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			String fmeaTypeAttribute=dObj.getInfo(context, fmeatypeattribute);
			if(fmeaTypeAttribute.equals("Process") || fmeaTypeAttribute.equals("Foundation Process")|| fmeaTypeAttribute.equals("Concept Process")){

				isProcessFMEA=true;				

			}


		}	

		return isProcessFMEA;

	}
	/**
	 * Q53 Added to not show commands
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isProcessFMEAFunctionCategories(Context context, String args[])
	throws Exception {
		boolean isProcessFMEA=false;

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");
		String selectFmeaId=DomainConstants.EMPTY_STRING;
		String fmeaTypeId=DomainConstants.EMPTY_STRING;
		String fmeaTypeAttribute=DomainConstants.EMPTY_STRING;
		String  type=DomainConstants.EMPTY_STRING;
		DomainObject dObj=null;
		DomainObject  dObjFMEA=null;

		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING)){
			dObj = DomainObject.newInstance(context,
					objectId);
			type=dObj.getInfo(context,SELECT_TYPE);
		}

		if(type.equals(TYPE_FUNCTION) )
		{
			selectFmeaId="to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";	
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			//Added by rneema1 for incindet - 15512 : Start
			if(null !=fmeaTypeId && !fmeaTypeId.equals(DomainConstants.EMPTY_STRING))
			{
				dObjFMEA = DomainObject.newInstance(context,fmeaTypeId);
			fmeaTypeAttribute=dObjFMEA.getInfo(context, "attribute["+ATTRIBUTE_FMEA_TYPE+"]");
				if(fmeaTypeAttribute.equals("Process") || fmeaTypeAttribute.equals("Foundation Process")|| fmeaTypeAttribute.equals("Concept Process"))
				{

				isProcessFMEA=true;				

			}
		}			
			//Added by rneema1 for incindet - 15512 : END
		}			
		return isProcessFMEA;

	}
	/**
	 * Q53 Added to not show commands
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isFromFailureModeFMEA(Context context, String args[])
	throws Exception {
		boolean isProcessFMEA=true;

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		/*String objectId = (String) programMap.get("objectId");
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING))
		{
			DomainObject dObj = DomainObject.newInstance(context,
					objectId);
			String fmeatypeattribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			String fmeaTypeAttribute=dObj.getInfo(context, fmeatypeattribute);
			if(fmeaTypeAttribute.equals("Process") || fmeaTypeAttribute.equals("Foundation Process")|| fmeaTypeAttribute.equals("Concept Process")){

				isProcessFMEA=true;				

			}


		}*/	

		return isProcessFMEA;

	}
	/**
	 * Q53 Added to not show commands
	 * Changed on 24/12/2013
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	// Modified method by rneema1 for Incident - 15512
	public boolean isProcessFMEAEffect(Context context, String args[])
	throws Exception {
		boolean isProcessFMEA=false;
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");
		DomainObject dObj=null;
		DomainObject dObjFMEA=null;
		String selectFmeaId=DomainConstants.EMPTY_STRING;
		String fmeaTypeId=DomainConstants.EMPTY_STRING;
		String fmeaTypeAttribute=DomainConstants.EMPTY_STRING;
		String  type=DomainConstants.EMPTY_STRING;
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING)){
			dObj = DomainObject.newInstance(context,objectId);
			type=dObj.getInfo(context,SELECT_TYPE);
		}

		if(!type.equals(TYPE_FAILURE_MODE) )
		{
			//selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";	
			selectFmeaId="to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";	
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			dObjFMEA = DomainObject.newInstance(context,fmeaTypeId);
			fmeaTypeAttribute=dObjFMEA.getInfo(context, "attribute["+ATTRIBUTE_FMEA_TYPE+"]");
			if(fmeaTypeAttribute.equals("Process") || fmeaTypeAttribute.equals("Foundation Process")|| fmeaTypeAttribute.equals("Concept Process"))
			{

				isProcessFMEA=true;				

			}
		}else if(type.equals(TYPE_FAILURE_MODE)){		

			//	selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE_EFFECT+"].from.to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			selectFmeaId="to["+RELATIONSHIP_FAILURE_MODE+"].from.to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			//Added by rneema1 for Incident - 15512 : Start
			if(null !=fmeaTypeId && !fmeaTypeId.equals(DomainConstants.EMPTY_STRING))
			{
				dObjFMEA = DomainObject.newInstance(context,fmeaTypeId);
			fmeaTypeAttribute=dObjFMEA.getInfo(context, "attribute["+ATTRIBUTE_FMEA_TYPE+"]");
				if(fmeaTypeAttribute.equals("Process") || fmeaTypeAttribute.equals("Foundation Process")|| fmeaTypeAttribute.equals("Concept Process"))
				{
				isProcessFMEA=true;				
			}
			}
			//Added by rneema1 for Incident - 15512 : End

		}	
		return isProcessFMEA;

	}
	/**
	 * Q53 Added to not show commands
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isConceptFMEA(Context context, String args[])
	throws Exception {
		boolean isConceptFMEA=false;

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");
		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING))
		{
			DomainObject dObj = DomainObject.newInstance(context,
					objectId);
			String fmeatypeattribute="attribute["+ATTRIBUTE_FMEA_TYPE+"]";
			String fmeaTypeAttribute=dObj.getInfo(context, fmeatypeattribute);
			if(fmeaTypeAttribute.equals("Foundation Design") || fmeaTypeAttribute.equals("Product Design")|| fmeaTypeAttribute.equals("Concept Design")){

				isConceptFMEA=true;				

			}


		}	

		return isConceptFMEA;

	}

	/**
	 * Q53 Added to not show commands
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isConceptFMEAFunctionCategories(Context context, String args[])
	throws Exception {
		boolean isConceptFMEA=false;

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String objectId = (String) programMap.get("objectId");

		String selectFmeaId=DomainConstants.EMPTY_STRING;
		String fmeaTypeId=DomainConstants.EMPTY_STRING;
		String fmeaTypeAttribute=DomainConstants.EMPTY_STRING;
		String  type=DomainConstants.EMPTY_STRING;
		DomainObject dObj=null;
		DomainObject  dObjFMEA=null;

		if(null !=objectId && !objectId.equals(DomainConstants.EMPTY_STRING))
		{
			dObj = DomainObject.newInstance(context,
					objectId);
			type=dObj.getInfo(context,SELECT_TYPE);
		}

		if(type.equals(TYPE_FUNCTION) )
		{


			selectFmeaId="to["+RELATIONSHIP_OPERATION_FMEA+"].from.id";	
			fmeaTypeId=dObj.getInfo(context, selectFmeaId);			
			//Added by rneema1 for incident - 15512 : Start
			if(null !=fmeaTypeId && !fmeaTypeId.equals(DomainConstants.EMPTY_STRING))
			{
				dObjFMEA = DomainObject.newInstance(context,fmeaTypeId);
			fmeaTypeAttribute=dObjFMEA.getInfo(context, "attribute["+ATTRIBUTE_FMEA_TYPE+"]");
				if(fmeaTypeAttribute.equals("Foundation Design") || fmeaTypeAttribute.equals("Product Design")|| fmeaTypeAttribute.equals("Concept Design"))
				{

				isConceptFMEA=true;				

			}
		}
			//Added by rneema1 for incident - 15512 : End
		}

		return isConceptFMEA;

	}

	/***
	 * Added by Q53
	 * 
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public boolean isFromFailureModeJLR(Context context, String args[])
	throws Exception {
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		String isPOC_FailureModesJLRCommand = (String) programMap
		.get("portalCmdName");
		boolean isPocJlrCommand = true;
		if (null != isPOC_FailureModesJLRCommand) {
			if (isPOC_FailureModesJLRCommand
					.equalsIgnoreCase("POC_FailureModesJLRNew")) {
				isPocJlrCommand = false;
			}
		}
		return isPocJlrCommand;

	}
/**
 * Q53
 * @param sInput
 * @return
 * @throws Exception
 */
	public boolean isNotBlank(String sInput) throws Exception {

		if (sInput != null && !"".equalsIgnoreCase(sInput)
				&& !"null".equalsIgnoreCase(sInput)) {
			return true;
		} else {
			return false;
		}
	}
/**
 * Q53
 * @param sInput
 * @return
 * @throws Exception
 */
	public boolean isBlank(String sInput) throws Exception {

		if (sInput == null || "".equalsIgnoreCase(sInput)
				|| "null".equalsIgnoreCase(sInput)) {
			return true;
		} else {
			return false;
		}
	}


	

	/**
	 * Q53
	 * @param context
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object connectFMEA(Context context, String[] args) throws Exception {

		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("paramMap");
		String strPartId = (String) paramMap.get("objectId");
		String newPartFamilyIds = (String) paramMap.get("New OID");
		String strPartFamilyRelationship = "FMEA Control Plan";
		StringList newPartFamilyList = FrameworkUtil.split(newPartFamilyIds,
		",");

		DomainObject partObj = DomainObject.newInstance(context, strPartId);

		StringList selectStmts = new StringList(2);
		selectStmts.addElement(SELECT_ID);
		StringList RelSelectsList = new StringList(SELECT_RELATIONSHIP_ID);
		// Get the part Family connected to part.
		MapList partFamilyList = partObj.getRelatedObjects(context,
				strPartFamilyRelationship, // relationship pattern
				"FMEA", // object pattern
				selectStmts, // object selects
				RelSelectsList, // relationship selects
				true, // to direction
				false, // from direction
				(short) 1, // recursion level
				null, // object where clause
				null); // relationship where clause

		try {
			ContextUtil.pushContext(context, PropertyUtil.getSchemaProperty(
					context, "person_UserAgent"), DomainConstants.EMPTY_STRING,
					DomainConstants.EMPTY_STRING); // 366577

			if (partFamilyList != null && partFamilyList.size() > 0) {
				// construct array of ids
				int partFamilyListSize = partFamilyList.size();
				for (int i = 0; i < partFamilyListSize; i++) {
					String strId = (String) ((Map) partFamilyList.get(i))
					.get(SELECT_ID);
					if (!newPartFamilyList.contains(strId)) {
						String strRelId = (String) ((Map) partFamilyList.get(i))
						.get(SELECT_RELATIONSHIP_ID);
						// Disconnecting the existing relationship
						DomainRelationship.disconnect(context, strRelId);
					} else {
						newPartFamilyList.remove(strId);
					}
				}
			}
			if (newPartFamilyList.size() > 0) {
				Iterator partFamilyItr = newPartFamilyList.iterator();
				DomainObject partFam = DomainObject.newInstance(context);
				while (partFamilyItr.hasNext()) {
					String newPartFamily = (String) partFamilyItr.next();
					partFam = DomainObject.newInstance(context, newPartFamily);
					// setId(newPartFamily);
					DomainObject domainObjectToType = newInstance(context,
							strPartId);
					
					partFam.addRelatedObject(context, new RelationshipType(
							strPartFamilyRelationship), true, strPartId);
					// DomainRelationship.connect(context,domainObjectToType,"MHI_Classification",this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ContextUtil.popContext(context);
		} // 366577

		return new Boolean(true);
	}

	/**
	 * Q53
	 * @param context
	 * @param args
	 * @throws Exception
	 */

	public void createAssociatedObjects1(Context context, String args[])
	throws Exception {
		try {
			HashMap progMap = (HashMap) JPO.unpackArgs(args);
			HashMap paramMap = (HashMap) progMap.get("paramMap");
			String sObjectId = (String) paramMap.get("objectId");
			if (isNotBlank(sObjectId)) {
				// DomainObject dObj = DomainObject.newIsntace(context
				// ,sObjectId);
				// get the part Id to connect to the other objects
				String sPartId = (String) (getFMEAPartId(context, sObjectId,
						null)).get(DomainConstants.SELECT_ID);
				// create DFMEA
				String sDFMEAId = createFMEAObject(context, "FMEA",
						"Product Design", "", sPartId);
				// createFMEAObject
				// (context,"Control Plan","",sDFMEAId,sPartId);
				String sPFMEAId = createFMEAObject(context, "FMEA", "Process",
						"", sPartId);
				createFMEAObject(context, "Control Plan", "", sPFMEAId, sPartId);
				createFMEAObject(context, "PPAP", "", "", sPartId);
				createFMEAObject(context, "Manufacturing Process", "", "",
						sPartId);
				// create PFMEA
				// create Control Plan
				// create PPAP

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Q53
 * @param context
 * @param sObjectId
 * @param dObj
 * @return
 * @throws Exception
 */
	public Map getFMEAPartId(Context context, String sObjectId,
			DomainObject dObj) throws Exception {
		Map partMap = null;
		try {
			if (dObj != null) {
				//
			} else if (isNotBlank(sObjectId)) {
				dObj = DomainObject.newInstance(context, sObjectId);
			}
			StringList slObjSel = new StringList(15);
			slObjSel.add(DomainConstants.SELECT_ID);
			slObjSel.add(DomainConstants.SELECT_NAME);
			slObjSel.add(DomainConstants.SELECT_CURRENT);

			slObjSel.add("attribute[Output Voltage]");
			slObjSel.add("attribute[Mounting Bracket]");
			slObjSel.add("attribute[Housing Tolerance]");
			slObjSel.add("attribute[Critical Diameter]");
			slObjSel.add("attribute[Cylindricity]");
			slObjSel.add("attribute[Thickness]");
			slObjSel.add("attribute[Weight]");
			slObjSel.add("attribute[Correct Solder]");
			slObjSel.add("attribute[Critical Char1]");
			slObjSel.add("attribute[Critical Char2]");
			slObjSel.add("attribute[Critical Char3]");
			slObjSel.add("attribute[Critical Char4]");
			slObjSel.add("attribute[Critical Char5]");
			StringList slRelSel = new StringList(1);
			MapList mlObjects = dObj.getRelatedObjects(context,
					"FMEA Affected Item", "Part", slObjSel, slRelSel, false,
					true, (short) 1, "", null);
			if (mlObjects != null && !mlObjects.isEmpty()) {
				partMap = (Map) mlObjects.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return partMap;
	}

	public String createFMEAObject(Context context, String sType,
			String sFMType, String sFMEAId, String sPartId) throws Exception {
		String strITObjId = "";
		try {
			String typeSymbolicName = FrameworkUtil.getAliasForAdmin(context,
					"type", sType, true);
			String policySymbolicName = FrameworkUtil.getAliasForAdmin(context,
					"policy", sType, true);
			strITObjId = FrameworkUtil.autoName(context, typeSymbolicName,
					policySymbolicName);
			DomainObject dObj = DomainObject.newInstance(context, strITObjId);

			Map attributes = new HashMap();
			attributes.put("Originator", context.getUser());
			if (isNotBlank(sFMType)) {
				attributes.put("FMEA Type", sFMType);
				if (sFMType.equalsIgnoreCase("Process")) {
					sFMType = "PFMEA : " + sFMType;
				} else {
					sFMType = "DFMEA : " + sFMType;
				}
				dObj.setDescription(context, sFMType);
			} else {
				dObj.setDescription(context, sType);
			}

			dObj.setAttributeValues(context, attributes);
			if (isNotBlank(sPartId)) {
				DomainRelationship infoRel = dObj.addRelatedObject(context,
						new RelationshipType("FMEA Affected Item"), false,
						sPartId);
			}
			if (isNotBlank(sFMEAId)) {
				DomainRelationship infoRel_1 = dObj.addRelatedObject(context,
						new RelationshipType("FMEA Control Plan"), false,
						sFMEAId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strITObjId;
	}


	

	
	
	
	

	





		

	
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFunctions(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFunctions=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFunction=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFunction=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFunction.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==1)
				{
					mlFunctions.add(mpFunction);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFunctions;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFailureModes(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModes=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModes=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModes=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModes.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==2)
				{
					mlFailureModes.add(mpFailureModes);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModes;
	}

	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFunctionsFailureModesAddExisting(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModes=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModes=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModes=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModes.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==1)
				{
					mlFailureModes.add(mpFailureModes);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModes;
	}


	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFailureModeEffects(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModeEffects=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModeEffects=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModeEffects=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModeEffects.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==3)
				{
					mlFailureModeEffects.add(mpFailureModeEffects);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModeEffects;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFailureModeEffectsForAddExisting(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModeEffects=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModeEffects=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModeEffects=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModeEffects.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==1)
				{
					mlFailureModeEffects.add(mpFailureModeEffects);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModeEffects;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFunctionsFailureModeEffectsForAddExisting(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModeEffects=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModeEffects=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModeEffects=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModeEffects.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==2)
				{
					mlFailureModeEffects.add(mpFailureModeEffects);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModeEffects;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFailureModeCauses(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModeCauses=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModeCauses=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModeCauses=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModeCauses.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==3)
				{
					mlFailureModeCauses.add(mpFailureModeCauses);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModeCauses;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFailureModeCausesForAddExisting(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModeCauses=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModeCauses=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModeCauses=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModeCauses.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==1)
				{
					mlFailureModeCauses.add(mpFailureModeCauses);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModeCauses;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFunctionsFailureModeCausesForAddExisting(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlFailureModeCauses=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpFailureModeCauses=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpFailureModeCauses=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpFailureModeCauses.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==2)
				{
					mlFailureModeCauses.add(mpFailureModeCauses);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlFailureModeCauses;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getRecommendedActions(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlRecommendedActions=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpRecommededActions=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpRecommededActions=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpRecommededActions.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==4)
				{
					mlRecommendedActions.add(mpRecommededActions);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlRecommendedActions;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getRecommendedActionsForAddExisting(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlRecommendedActions=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpRecommededActions=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpRecommededActions=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpRecommededActions.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==2)
				{
					mlRecommendedActions.add(mpRecommededActions);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlRecommendedActions;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getFunctionsRecommendedActionsForAddExisting(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlRecommendedActions=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpRecommededActions=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpRecommededActions=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpRecommededActions.get(DomainConstants.SELECT_LEVEL));			
				if(intLevel==3)
				{
					mlRecommendedActions.add(mpRecommededActions);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlRecommendedActions;
	}
	/**
	 * Q53
	 * @param context
	 * @param MapListtotalResultMapList
	 * @return
	 * @throws Exception
	 */
	public MapList getAffectedItem(Context context,MapList MapListtotalResultMapList)throws Exception
	{
		MapList  mlAffectedItem=new MapList();
		try{
			MapList totalMapList=MapListtotalResultMapList;	
			Map mpAffectedItem=null;
			for(int i=0;i<totalMapList.size();i++)
			{
				mpAffectedItem=(Map)totalMapList.get(i);			
				int intLevel = Integer.parseInt((String)mpAffectedItem.get(DomainConstants.SELECT_LEVEL));
				String type=(String)mpAffectedItem.get(SELECT_TYPE);
				if(intLevel==1 && type.equals(TYPE_PART))
				{
					mlAffectedItem.add(mpAffectedItem);

				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return mlAffectedItem;
	}

	public StringList getTypeSelectList(Context context, String sType)
	throws Exception {
		StringList slSelect = new StringList(10);
		slSelect.addElement(SELECT_AFFECTED_ITEM);
		slSelect.addElement(SELECT_NAME);
		slSelect.addElement(SELECT_TYPE);
		slSelect.addElement(SELECT_CURRENT);
		slSelect.addElement(SELECT_OWNER);
		slSelect.addElement(SELECT_POLICY);
		slSelect.add(SELECT_ORIGINATED);
		slSelect.add(SELECT_ORIGINATOR);
		slSelect.add(SELECT_DESCRIPTION);
		slSelect.add(SELECT_MODIFIED);
		slSelect.add(SELECT_VAULT);
		slSelect.add(SELECT_REVISION);
		/* slSelect.addElement("attribute[FMEA Type]"); */
		BusinessType eBusType = new BusinessType(sType, new Vault(PersonUtil
				.getDefaultVault(context)));
		AttributeTypeList eAttrList = eBusType.getAttributeTypes(context);
		Iterator eAttrListItr = eAttrList.iterator();
		String attrName = "";
		while (eAttrListItr.hasNext()) {
			attrName = ((AttributeType) eAttrListItr.next()).getName();
			slSelect.add("attribute[" + attrName + "]");
		}
		return slSelect;
	}

	/**
	 * Q53 This method is used to check if the FMEA number is exist in Data or Not
	 * @param context the eMatrix <code>Context</code> object
	 * @param args holds the following input arguments:
	 *        0 - objectList MapList
	 * @throws MatrixException if the operation fails
	 * @since PRG 2011x
	 * @author VM3
	 * @returns StringList
	 */
	public boolean isFMEAExistInDatabase(Context context, String fmeaNumber) throws MatrixException {
		try{

			boolean isExist=false;	
			StringList strList = new StringList();
			strList.add(SELECT_NAME);
			strList.add(SELECT_TYPE);
			strList.add(SELECT_ID);
			String strFmeaNumberDatabase=DomainConstants.EMPTY_STRING;
			MapList mlAllFMEAList = DomainObject.findObjects(context, TYPE_FMEA, ProgramCentralConstants.QUERY_WILDCARD, null, strList);
			for(int itr = 0; itr < mlAllFMEAList.size(); itr++){
				Map map = (Map)mlAllFMEAList.get(itr);
				strFmeaNumberDatabase=(String)map.get(SELECT_NAME);

				if(strFmeaNumberDatabase.contains(fmeaNumber)){
					isExist=true;
					break;
				}
			}
			return isExist;
		}
		catch(Exception ex){
			throw new MatrixException(ex);
		}
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++CSV Import :START++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	public HashMap runCsvImport(Context context,String args[])throws Exception
	{
		String errorMessage="";
		HashMap resultMap = new HashMap();
		HashMap programMap = (HashMap) JPO.unpackArgs(args);
		HashMap paramMap = (HashMap) programMap.get("programMap");
		String csvFilePath=(String)paramMap.get("csvFilePath");        
		resultMap.put("errorMsg", DomainObject.EMPTY_STRING);
		try{
			ContextUtil.startTransaction(context, true);
			MapList   functionMaplist=new MapList();
			MapList   failureModeMaplist=new MapList();		 
			MapList   failureModeCauselist=new MapList();
			MapList   recommendedActionMaplist=new MapList();          
			HashMap fmeaMap = new HashMap();       


			String fmeaTitle=DomainConstants.EMPTY_STRING;
			String fmeaScope=DomainConstants.EMPTY_STRING;
			String fmeaNumber=DomainConstants.EMPTY_STRING;
			String fmeaName=DomainConstants.EMPTY_STRING;
			String fmeaTeam=DomainConstants.EMPTY_STRING;
			String fmeaType=DomainConstants.EMPTY_STRING;		
			String vehichleProgram=DomainConstants.EMPTY_STRING;
			String keyGateway=DomainConstants.EMPTY_STRING;
			String keyDate=DomainConstants.EMPTY_STRING;
			String system=DomainConstants.EMPTY_STRING;
			String subSystem=DomainConstants.EMPTY_STRING;
			String component=DomainConstants.EMPTY_STRING;
			String relatedSystemOrPart=DomainConstants.EMPTY_STRING;
			String creator=DomainConstants.EMPTY_STRING;

			String  functionOperationNumber=DomainConstants.EMPTY_STRING;
			String  functionStation=DomainConstants.EMPTY_STRING;
			String  functionProcessFlowSymbol=DomainConstants.EMPTY_STRING;
			String  functionVerbNoun=DomainConstants.EMPTY_STRING;
			String  functionMeasurable=DomainConstants.EMPTY_STRING;
			
			String  fmNo=DomainConstants.EMPTY_STRING;
			String  failureModeDescription=DomainConstants.EMPTY_STRING;
			String  failureModeType=DomainConstants.EMPTY_STRING;

			String failureModeEffectPotentialEffect=DomainConstants.EMPTY_STRING;
			String failureModeEffectSeverity=DomainConstants.EMPTY_STRING;
			String failureModeManufacturingAssemblyEffect=DomainConstants.EMPTY_STRING;

			String failureModeCauseClass=DomainConstants.EMPTY_STRING;
			String  fcNo=DomainConstants.EMPTY_STRING;
			String failureModeCauseCauses=DomainConstants.EMPTY_STRING;
			String failureModeCauseQualityHistoryData=DomainConstants.EMPTY_STRING;
			String failureModeCausePreventionControls=DomainConstants.EMPTY_STRING;
			String failureModeCauseOccurence=DomainConstants.EMPTY_STRING;	
			String failureModeCausePreDJDection=DomainConstants.EMPTY_STRING;
			String failureModeCausePostDJDection=DomainConstants.EMPTY_STRING;
			String failureModeCauseDetection=DomainConstants.EMPTY_STRING;
			String failureModeCauseRPN=DomainConstants.EMPTY_STRING;

			String recommndedActionOccurence=DomainConstants.EMPTY_STRING;												
			String recommendedActionDetection=DomainConstants.EMPTY_STRING;
			String recommendedActionSeverity=DomainConstants.EMPTY_STRING;
			String recommendedActionTaken=DomainConstants.EMPTY_STRING;
			String recommendedAction=DomainConstants.EMPTY_STRING;
			String recommendedActionTargetEndDate=DomainConstants.EMPTY_STRING;
			String recommendedActionCompletionEndDate=DomainConstants.EMPTY_STRING;
			String recommendedActionRPN=DomainConstants.EMPTY_STRING;
			String recommendedActionResponsibility=DomainConstants.EMPTY_STRING;
			String recommendedActionActionComplete=DomainConstants.EMPTY_STRING;
			StringBuffer exprectedString= new StringBuffer();



			//Parse the csv file for header section Name to check

			CSVReader readerToRead = new CSVReader(new FileReader(csvFilePath));		

			String [] nextLineToRead;
			int rowToRead=0;

			while ((nextLineToRead = readerToRead.readNext()) != null) {
				rowToRead=rowToRead+1;

				switch (rowToRead){
				case 1:fmeaType=nextLineToRead[0];
				fmeaType=fmeaType.replaceAll(" ", "");
				fmeaType=fmeaType.replaceAll("\n", "");
				if(!fmeaType.equalsIgnoreCase("FMEAType"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"FMEA Type\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;

				case 2:fmeaNumber=nextLineToRead[0];
				fmeaNumber=fmeaNumber.replaceAll(" ", "");
				fmeaNumber=fmeaNumber.replaceAll("\n", "");
				if(!fmeaNumber.equalsIgnoreCase("FMEANumber"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"FMEA Number\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				//Q53 Here need to add logic if the FMEA Number already exist in data base
				break;
				case 3:fmeaScope=nextLineToRead[0];
				fmeaScope=fmeaScope.replaceAll(" ", "");
				fmeaScope=fmeaScope.replaceAll("\n", "");
				if(!fmeaScope.equalsIgnoreCase("FMEASCOPE"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"FMEA SCOPE\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;
				case 4:fmeaTitle=nextLineToRead[0];
				fmeaTitle=fmeaTitle.replaceAll(" ", "");
				fmeaTitle=fmeaTitle.replaceAll("\n", "");
				if(!fmeaTitle.equalsIgnoreCase("Title"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Title\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;	
				case 5:vehichleProgram=nextLineToRead[0];
				vehichleProgram=vehichleProgram.replaceAll(" ", "");
				vehichleProgram=vehichleProgram.replaceAll("\n", "");
				if(!vehichleProgram.equalsIgnoreCase("VehicleProgramme"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Vehicle Programme\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;
				case 6:relatedSystemOrPart=nextLineToRead[0];
				relatedSystemOrPart=relatedSystemOrPart.replaceAll(" ", "");
				relatedSystemOrPart=relatedSystemOrPart.replaceAll("\n", "");
				if(!relatedSystemOrPart.equalsIgnoreCase("RelatedSystemorPart"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Related System or Part\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;
				case 7:creator=nextLineToRead[0];
				creator=creator.replaceAll(" ", "");
				creator=creator.replaceAll("\n", "");
				if(!creator.equalsIgnoreCase("Creator"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Creator\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;
				case 8:system=nextLineToRead[0];
				system=system.replaceAll(" ", "");
				system=system.replaceAll("\n", "");
				if(!system.equalsIgnoreCase("System"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"System\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;
				case 9:subSystem=nextLineToRead[0];
				subSystem=subSystem.replaceAll(" ", "");
				subSystem=subSystem.replaceAll("\n", "");
				if(!subSystem.equalsIgnoreCase("Subsystem"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Subsystem\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;
				case 10:component=nextLineToRead[0];
				component=component.replaceAll(" ", "");
				component=component.replaceAll("\n", "");
				if(!component.equalsIgnoreCase("Component"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Component\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");	
					exprectedString.append("\n");


				}
				break;
				case 11:keyGateway=nextLineToRead[0];
				keyGateway=keyGateway.replaceAll(" ", "");
				keyGateway=keyGateway.replaceAll("\n", "");
				if(!keyGateway.equalsIgnoreCase("KeyGateway"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Key Gateway\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");
					exprectedString.append("\n");


				}
				break;
				case 12:keyDate=nextLineToRead[0];
				keyDate=keyDate.replaceAll(" ", "");
				keyDate=keyDate.replaceAll("\n", "");
				if(!keyDate.equalsIgnoreCase("KeyDate"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"key Date\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");		
					exprectedString.append("\n");


				}
				break;
				case 13:fmeaTeam=nextLineToRead[0];
				fmeaTeam=fmeaTeam.replaceAll(" ", "");
				fmeaTeam=fmeaTeam.replaceAll("\n", "");
				if(!fmeaTeam.equalsIgnoreCase("Team"))
				{
					exprectedString.append("-Expected string");
					exprectedString.append(" ");
					exprectedString.append("\"Team\"");
					exprectedString.append(" ");
					exprectedString.append("at row :");
					exprectedString.append(rowToRead);
					exprectedString.append(" ");
					exprectedString.append("and column :");
					exprectedString.append("1");
					exprectedString.append("\n");


				}
				break;
				}

				//skip row 13 and 14
				if(rowToRead==14){
					continue;				
				}

				if(rowToRead==15)

				{       

					//Function Information
					functionOperationNumber=nextLineToRead[0];
					functionOperationNumber=functionOperationNumber.replaceAll(" ", "");
					functionOperationNumber=functionOperationNumber.replaceAll("\n", "");
					if(!functionOperationNumber.equalsIgnoreCase("Operation")&& !functionOperationNumber.equalsIgnoreCase("Functionno") )
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");						
						exprectedString.append("\"Operation or Function no\"");					
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("1");
						exprectedString.append("\n");


					}

					functionStation=nextLineToRead[1];
					functionStation=functionStation.replaceAll(" ", "");
					functionStation=functionStation.replaceAll("\n", "");
					if(!functionStation.equalsIgnoreCase("Station"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Station\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("2");
						exprectedString.append("\n");


					}
					functionProcessFlowSymbol=nextLineToRead[2];
					functionProcessFlowSymbol=functionProcessFlowSymbol.replaceAll(" ", "");
					functionProcessFlowSymbol=functionProcessFlowSymbol.replaceAll("\n", "");
					if(!functionProcessFlowSymbol.equalsIgnoreCase("ProcessFlowSymbol"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Process Flow Symbol\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("3");
						exprectedString.append("\n");


					}

					functionVerbNoun=nextLineToRead[3];
					functionVerbNoun=functionVerbNoun.replaceAll(" ", "");
					functionVerbNoun=functionVerbNoun.replaceAll("\n", "");
					if(!functionVerbNoun.equalsIgnoreCase("Verb/Noun"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Verb/Noun\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("4");
						exprectedString.append("\n");


					}

					functionMeasurable=nextLineToRead[4];	
					functionMeasurable=functionMeasurable.replaceAll(" ", "");
					functionMeasurable=functionMeasurable.replaceAll("\n", "");
					if(!functionMeasurable.equalsIgnoreCase("Measurable"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Measurable\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("5");
						exprectedString.append("\n");


					}


					//Failure Mode And Effect Information					
					fmNo=nextLineToRead[5];
					fmNo=fmNo.replaceAll(" ", "");
					fmNo=fmNo.replaceAll("\n", "");
					if(!fmNo.equalsIgnoreCase("FMNo"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"FM No\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("6");
						exprectedString.append("\n");


					}
					
					failureModeType=nextLineToRead[6];
					failureModeType=failureModeType.replaceAll(" ", "");
					failureModeType=failureModeType.replaceAll("\n", "");
					if(!failureModeType.equalsIgnoreCase("ModeType"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Mode Type\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("6");
						exprectedString.append("\n");


					}
					failureModeDescription=nextLineToRead[7];	
					failureModeDescription=failureModeDescription.replaceAll(" ", "");
					failureModeDescription=failureModeDescription.replaceAll("\n", "");
					if(!failureModeDescription.equalsIgnoreCase("FailureMode"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Failure Mode\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("7");
						exprectedString.append("\n");


					}
					failureModeEffectPotentialEffect=nextLineToRead[8];
					failureModeEffectPotentialEffect=failureModeEffectPotentialEffect.replaceAll(" ", "");
					failureModeEffectPotentialEffect=failureModeEffectPotentialEffect.replaceAll("\n", "");
					if(!failureModeEffectPotentialEffect.equalsIgnoreCase("Effect"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Effect\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("8");
						exprectedString.append("\n");


					}

					failureModeEffectSeverity=nextLineToRead[9];
					failureModeEffectSeverity=failureModeEffectSeverity.replaceAll(" ", "");
					failureModeEffectSeverity=failureModeEffectSeverity.replaceAll("\n", "");
					if(!failureModeEffectSeverity.equalsIgnoreCase("Severity"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Severity\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("9");
						exprectedString.append("\n");


					}
					failureModeManufacturingAssemblyEffect=nextLineToRead[10];
					failureModeManufacturingAssemblyEffect=failureModeManufacturingAssemblyEffect.replaceAll(" ", "");
					failureModeManufacturingAssemblyEffect=failureModeManufacturingAssemblyEffect.replaceAll("\n", "");
					if(!failureModeManufacturingAssemblyEffect.equalsIgnoreCase("ManufacturingorAssembly(Internal)EffectOnly?"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Manufacturing or Assembly(Internal) Effect Only?\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("10");
						exprectedString.append("\n");


					}


					//Failure Mode Cause Information
					failureModeCauseClass=nextLineToRead[11];
					failureModeCauseClass=failureModeCauseClass.replaceAll(" ", "");
					failureModeCauseClass=failureModeCauseClass.replaceAll("\n", "");
					if(!failureModeCauseClass.equalsIgnoreCase("CLASS"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"CLASS\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("11");
						exprectedString.append("\n");


					}
					
					fcNo=nextLineToRead[12];
					fcNo=fcNo.replaceAll(" ", "");
					fcNo=fcNo.replaceAll("\n", "");
					if(!fcNo.equalsIgnoreCase("FCNo"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"FC No\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("11");
						exprectedString.append("\n");


					}

					failureModeCauseCauses=nextLineToRead[13];
					failureModeCauseCauses=failureModeCauseCauses.replaceAll(" ", "");
					failureModeCauseCauses=failureModeCauseCauses.replaceAll("\n", "");
					if(!failureModeCauseCauses.equalsIgnoreCase("CauseofFailure"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Cause of Failure\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("12");
						exprectedString.append("\n");


					}						

					failureModeCauseQualityHistoryData=nextLineToRead[14];
					failureModeCauseQualityHistoryData=failureModeCauseQualityHistoryData.replaceAll(" ", "");
					failureModeCauseQualityHistoryData=failureModeCauseQualityHistoryData.replaceAll("\n", "");
					if(!failureModeCauseQualityHistoryData.equalsIgnoreCase("QualityHistoryDataSource"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Quality History Data Source\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("12");
						exprectedString.append("\n");


					}
					failureModeCausePreventionControls=nextLineToRead[15];
					failureModeCausePreventionControls=failureModeCausePreventionControls.replaceAll(" ", "");
					failureModeCausePreventionControls=failureModeCausePreventionControls.replaceAll("\n", "");
					if(!failureModeCausePreventionControls.equalsIgnoreCase("PreventionControls"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Prevention Controls\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("13");
						exprectedString.append("\n");


					}
					failureModeCauseOccurence=nextLineToRead[16];
					failureModeCauseOccurence=failureModeCauseOccurence.replaceAll(" ", "");
					failureModeCauseOccurence=failureModeCauseOccurence.replaceAll("\n", "");
					if(!failureModeCauseOccurence.equalsIgnoreCase("Occurrence"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Occurrence\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("14");
						exprectedString.append("\n");


					}
					failureModeCausePreDJDection=nextLineToRead[17];
					failureModeCausePreDJDection=failureModeCausePreDJDection.replaceAll(" ", "");
					failureModeCausePreDJDection=failureModeCausePreDJDection.replaceAll("\n", "");
					if(!failureModeCausePreDJDection.equalsIgnoreCase("Pre-DJDetectionControl"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Pre-DJ Detection Control\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("15");
						exprectedString.append("\n");


					}
					failureModeCausePostDJDection=nextLineToRead[18];
					failureModeCausePostDJDection=failureModeCausePostDJDection.replaceAll(" ", "");
					failureModeCausePostDJDection=failureModeCausePostDJDection.replaceAll("\n", "");
					if(!failureModeCausePostDJDection.equalsIgnoreCase("Post-DJDetectionControl"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Post-DJ Detection Control\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("16");
						exprectedString.append("\n");


					}
					failureModeCauseDetection=nextLineToRead[19];
					failureModeCauseDetection=failureModeCauseDetection.replaceAll(" ", "");
					failureModeCauseDetection=failureModeCauseDetection.replaceAll("\n", "");
					if(!failureModeCauseDetection.equalsIgnoreCase("Detection"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Detection\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("17");
						exprectedString.append("\n");


					}
					failureModeCauseRPN=nextLineToRead[20];	
					failureModeCauseRPN=failureModeCauseRPN.replaceAll(" ", "");
					failureModeCauseRPN=failureModeCauseRPN.replaceAll("\n", "");
					if(!failureModeCauseRPN.equalsIgnoreCase("RPN"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"RPN\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("18"); 
						exprectedString.append("\n");


					}



					//Recommeded Action Information

					recommendedAction=nextLineToRead[21];
					recommendedAction=recommendedAction.replaceAll(" ", "");
					recommendedAction=recommendedAction.replaceAll("\n", "");
					if(!recommendedAction.equalsIgnoreCase("RecommendedAction"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Recommended Action\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("19");
						exprectedString.append("\n");


					}
					recommendedActionResponsibility=nextLineToRead[22];
					recommendedActionResponsibility=recommendedActionResponsibility.replaceAll(" ", "");
					recommendedActionResponsibility=recommendedActionResponsibility.replaceAll("\n", "");
					if(!recommendedActionResponsibility.equalsIgnoreCase("Responsibility"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Responsibility\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("20");
						exprectedString.append("\n");


					}
					recommendedActionTargetEndDate=nextLineToRead[23];	
					recommendedActionTargetEndDate=recommendedActionTargetEndDate.replaceAll(" ", "");
					recommendedActionTargetEndDate=recommendedActionTargetEndDate.replaceAll("\n", "");
					if(!recommendedActionTargetEndDate.equalsIgnoreCase("TargetEndDate"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Target End Date\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("21");
						exprectedString.append("\n");


					}
					recommendedActionTaken=nextLineToRead[24];
					recommendedActionTaken=recommendedActionTaken.replaceAll(" ", "");
					recommendedActionTaken=recommendedActionTaken.replaceAll("\n", "");
					if(!recommendedActionTaken.equalsIgnoreCase("ActionTaken"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Action Taken\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("22");
						exprectedString.append("\n");


					}
					recommendedActionSeverity=nextLineToRead[25];
					recommendedActionSeverity=recommendedActionSeverity.replaceAll(" ", "");
					recommendedActionSeverity=recommendedActionSeverity.replaceAll("\n", "");
					if(!recommendedActionSeverity.equalsIgnoreCase("RevisedSeverity"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Revised Severity\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("23");
						exprectedString.append("\n");


					}

					recommndedActionOccurence=nextLineToRead[26];
					recommndedActionOccurence=recommndedActionOccurence.replaceAll(" ", "");
					recommndedActionOccurence=recommndedActionOccurence.replaceAll("\n", "");
					if(!recommndedActionOccurence.equalsIgnoreCase("RevisedOccurrence"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Revised Occurrence\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("24");
						exprectedString.append("\n");


					}
					recommendedActionDetection=nextLineToRead[27];
					recommendedActionDetection=recommendedActionDetection.replaceAll(" ", "");
					recommendedActionDetection=recommendedActionDetection.replaceAll("\n", "");
					if(!recommendedActionDetection.equalsIgnoreCase("RevisedDetection"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Revised Detection\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("25");
						exprectedString.append("\n");


					}
					recommendedActionRPN=nextLineToRead[28];
					recommendedActionRPN=recommendedActionRPN.replaceAll(" ", "");
					recommendedActionRPN=recommendedActionRPN.replaceAll("\n", "");
					if(!recommendedActionRPN.equalsIgnoreCase("RevisedRPN"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Revised RPN\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("26");
						exprectedString.append("\n");


					}
					recommendedActionCompletionEndDate=nextLineToRead[29];	
					recommendedActionCompletionEndDate=recommendedActionCompletionEndDate.replaceAll(" ", "");
					recommendedActionCompletionEndDate=recommendedActionCompletionEndDate.replaceAll("\n", "");
					if(!recommendedActionCompletionEndDate.equalsIgnoreCase("CompletionDate"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Completion Date\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("27");
						exprectedString.append("\n");


					}
					recommendedActionActionComplete=nextLineToRead[30];
					recommendedActionActionComplete=recommendedActionActionComplete.replaceAll(" ", "");
					recommendedActionActionComplete=recommendedActionActionComplete.replaceAll("\n", "");
					if(!recommendedActionActionComplete.equalsIgnoreCase("ActionComplete"))
					{
						exprectedString.append("-Expected string");
						exprectedString.append(" ");
						exprectedString.append("\"Action Complete\"");
						exprectedString.append(" ");
						exprectedString.append("at row :");
						exprectedString.append(rowToRead);
						exprectedString.append(" ");
						exprectedString.append("and column :");
						exprectedString.append("28");
						exprectedString.append("\n");


					}

					break;
				}


			}
			StringBuffer expectedFormat=new StringBuffer();

			if(!exprectedString.toString().equals(""))
			{
				expectedFormat.append("Invalid csv format :");
				expectedFormat.append("\n");
				expectedFormat.append(exprectedString.toString());
				errorMessage= expectedFormat.toString();				 
				throw new Exception();	


			}
			//Parse the csv file for header section

			//Added by DS : 14339 : FMEA - Import Fails due to Backslash Character - Starts -->
			//CSVReader reader = new CSVReader(new FileReader(csvFilePath));
			CSVReader reader = new CSVReader(new FileReader(csvFilePath), ',' , '\"', '\u263A');
			//Added by DS : 14339 : FMEA - Import Fails due to Backslash Character - Ends -->
						
			String [] nextLine;		
			int row=0;
			while ((nextLine = reader.readNext()) != null) {
				row=row+1;

				switch (row){
				case 1:fmeaType=nextLine[1];
				break;
				case 2:fmeaNumber=nextLine[1];
				break;
				case 3:fmeaScope=nextLine[1];
				break;
				case 4:fmeaTitle=nextLine[1];
				break;	
				case 5:vehichleProgram=nextLine[1];
				break;
				case 6:relatedSystemOrPart=nextLine[1];
				break;
				case 7:creator=nextLine[1];
				break;
				case 8:system=nextLine[1];
				break;
				case 9:subSystem=nextLine[1];
				break;
				case 10:component=nextLine[1];
				break;
				case 11:keyGateway=nextLine[1];
				break;
				case 12:keyDate=nextLine[1];
				break;
				case 13:fmeaTeam=nextLine[1];
				break;
				}

				//skip row 13 and 14
				if(row==14 || row ==15){
					continue;				
				}




				if(row>=16)
				{	

					HashMap functionMap = new HashMap();
					HashMap failureModeMap = new HashMap();
					// HashMap failureModeEffectMap = new HashMap();
					HashMap failureModeCauseMap = new HashMap();
					HashMap recommendedActionMap = new HashMap();


					//Function information
					functionOperationNumber=nextLine[COLUMN_Operation-1];
					functionStation=nextLine[COLUMN_Station-1];
					functionProcessFlowSymbol=nextLine[COLUMN_Process_Flow_Symbol-1];
					functionVerbNoun=nextLine[COLUMN_Verb_Noun-1];
					functionMeasurable=nextLine[COLUMN_Measurable-1];				
					//Functuion data map---->>
					functionMap.put(ATTRIBUTE_OPERATION_NUMBER, functionOperationNumber);
					functionMap.put(ATTRIBUTE_STATION, functionStation);
					functionMap.put(ATTRIBUTE_PROCESS_FLOW_SYMBOL, functionProcessFlowSymbol);
					functionMap.put(ATTRIBUTE_OPERATION_NAME, functionVerbNoun);
					functionMap.put(ATTRIBUTE_FM_MEASURABLE, functionMeasurable);
					functionMap.put(DomainConstants.SELECT_LEVEL, "1");



					//Failure mode And Effect  information
					fmNo=nextLine[COLUMN_FM_No-1];
					failureModeType=nextLine[COLUMN_Mode_Type-1];
					failureModeDescription=nextLine[COLUMN_Failure_Mode-1];	
					failureModeEffectPotentialEffect=nextLine[COLUMN_Effect-1];
					failureModeEffectSeverity=nextLine[COLUMN_Severity-1];
					failureModeManufacturingAssemblyEffect=nextLine[COLUMN_Manufacturing_or_Assembly_Effect_Only-1];		

					//Failure Mode And Effect Map data
					failureModeMap.put(ATTRIBUTE_FM_SEQUENCE_ORDER, fmNo);
					failureModeMap.put(ATTRIBUTE_MODE_TYPE, failureModeType);
					failureModeMap.put(ATTRIBUTE_FAILURE_MODE, failureModeDescription);				
					failureModeMap.put(ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS, failureModeEffectPotentialEffect);
					failureModeMap.put(ATTRIBUTE_FM_SEVERITY, failureModeEffectSeverity);
					failureModeMap.put(ATTRIBUTE_ASSEMBLY_EFFECT, failureModeManufacturingAssemblyEffect);
					failureModeMap.put(DomainConstants.SELECT_LEVEL, "2");

					//Failure Mode Cause information				
					failureModeCauseClass=nextLine[COLUMN_CLASS-1];
					fcNo=nextLine[COLUMN_FC_No-1];
					failureModeCauseCauses=nextLine[COLUMN_Cause_of_Failure-1];
					failureModeCauseQualityHistoryData=nextLine[COLUMN_Quality_History_Data_Source-1];
					failureModeCausePreventionControls=nextLine[COLUMN_Prevention_Controls-1];
					failureModeCauseOccurence=nextLine[COLUMN_Occurrence-1];	
					failureModeCausePreDJDection=nextLine[COLUMN_PreDJ_Detection_Control-1];
					failureModeCausePostDJDection=nextLine[COLUMN_PostDJ_Detection_Control-1];
					failureModeCauseDetection=nextLine[COLUMN_Detection-1];
					failureModeCauseRPN=nextLine[COLUMN_RPN-1];				

					//Failure Mode Cause Map data
					failureModeCauseMap.put(ATTRIBUTE_CLASS, failureModeCauseClass);  //No need to set this attribute value as this is automaticallly calculate
					failureModeCauseMap.put(ATTRIBUTE_FC_SEQUENCE_ORDER, fcNo); 
					failureModeCauseMap.put(ATTRIBUTE_POTENTIAL_FAILURE_CAUSES, failureModeCauseCauses);
					failureModeCauseMap.put(ATTRIBUTE_QUALITY_HISTORY, failureModeCauseQualityHistoryData);				
					failureModeCauseMap.put(ATTRIBUTE_PREVENTION_CONTROLS, failureModeCausePreventionControls);
					failureModeCauseMap.put(ATTRIBUTE_OCCURENCE, failureModeCauseOccurence);
					failureModeCauseMap.put(ATTRIBUTE_PREDJ_DETECTION_CONTROL, failureModeCausePreDJDection);
					failureModeCauseMap.put(ATTRIBUTE_POSTDJ_DETECTION_CONTROL, failureModeCausePostDJDection);
					failureModeCauseMap.put(ATTRIBUTE_DETECTION, failureModeCauseDetection);
					failureModeCauseMap.put(ATTRIBUTE_RPN, failureModeCauseRPN); //This is also automatically calculate i think no need to set this values
					failureModeCauseMap.put(DomainConstants.SELECT_LEVEL, "3");


					//Recommended Action information
					recommendedAction=nextLine[COLUMN_Recommended_Action-1];
					recommendedActionResponsibility=nextLine[COLUMN_Responsibility-1];
					recommendedActionTargetEndDate=nextLine[COLUMN_Target_End_Date-1];

					
					//Creating the Date in to Matrix format date
					if(!recommendedActionTargetEndDate.equals(DomainConstants.EMPTY_STRING))
					{
						Date statesDate = eMatrixDateFormat.getJavaDate(recommendedActionTargetEndDate);
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM d, yyyy", Locale.US);
						recommendedActionTargetEndDate = sdf.format(statesDate);  
					}


					recommendedActionTaken=nextLine[COLUMN_Action_Taken-1];
					recommendedActionSeverity=nextLine[COLUMN_Revised_Severity-1];
					recommndedActionOccurence=nextLine[COLUMN_Revised_Occurrence-1];
					recommendedActionDetection=nextLine[COLUMN_Revised_Detection-1];
					recommendedActionRPN=nextLine[COLUMN_Revised_RPN-1];
					recommendedActionCompletionEndDate=nextLine[COLUMN_Completion_Date-1];
					//Creating the Date in to Matrix format date
					if(!recommendedActionCompletionEndDate.equals(DomainConstants.EMPTY_STRING))
					{
						Date statesDate = eMatrixDateFormat.getJavaDate(recommendedActionCompletionEndDate);
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM d, yyyy", Locale.US);
						recommendedActionCompletionEndDate = sdf.format(statesDate);  
					}				

					recommendedActionActionComplete=nextLine[COLUMN_Action_Complete-1];

					//Recommended Action map data
					recommendedActionMap.put(ATTRIBUTE_RECOMMENDED_ACTION, recommendedAction);
					//recommendedActionMap.put(ATTRIBUTE_RESPONSIBLE_PERSON, value); // this is the relationship between RA and person so there is no attribute for this
					//date needs to be convert in matrix format
					recommendedActionMap.put(ATTRIBUTE_TARGET_END_DATE, recommendedActionTargetEndDate);
					recommendedActionMap.put(ATTRIBUTE_ACTION_TAKEN, recommendedActionTaken);
					//Modified for CR106 Start
					recommendedActionMap.put(ATTRIBUTE_REVISED_SEVERITY, recommendedActionSeverity);
					recommendedActionMap.put(ATTRIBUTE_REVISED_OCCURRENCE, recommndedActionOccurence);
					recommendedActionMap.put(ATTRIBUTE_REVISED_DETECTION, recommendedActionDetection);
					recommendedActionMap.put(ATTRIBUTE_REVISED_RPN, recommendedActionRPN);
					//Modified for CR106 End
					recommendedActionMap.put(ATTRIBUTE_COMPLETION_DATE, recommendedActionCompletionEndDate);
					//recommendedActionMap.put(ATTRIBUTE_ACTION_COMPLETE, value);//here ths values should be Yes or No not as COMPLETED otherwise it will voilate the range of attribute
					recommendedActionMap.put(DomainConstants.SELECT_LEVEL, "4");				

					//Filter All Functions and add it to in Maplist
					Map mFunctionMap=isFunctionAlreadyExist(functionMaplist,functionMap,"Operation Name");


					if(mFunctionMap!=null)
					{
						failureModeMaplist = (MapList)mFunctionMap.get("FUNCTION_CHILD");

					}
					else
					{

						failureModeMaplist= new MapList();
						mFunctionMap=functionMap;
						mFunctionMap.put("FUNCTION_CHILD", failureModeMaplist);
						functionMaplist.add(mFunctionMap);
					}

					//Filter All Failure Mode and add it to in Maplist
					Map  mFailureModeMap=isFailureModeAlreadyExist(failureModeMaplist,failureModeMap);
					if(mFailureModeMap!=null)
					{					
						failureModeCauselist = (MapList)mFailureModeMap.get("FAILUREMODE_CHILD");
					}
					else
					{
						failureModeCauselist= new MapList();
						mFailureModeMap=failureModeMap;
						mFailureModeMap.put("FAILUREMODE_CHILD", failureModeCauselist);
						failureModeMaplist.add(mFailureModeMap);

					}
					
					/*//Filter All Same Failure Mode with different Failure Mode Effect and add it to in Maplist
					Map mFailureModeEffectMap=isFailureModeEffectAlreadyExist(failureModeMaplist,failureModeMap);
					if(mFailureModeEffectMap!=null)
					{					
						failureModeCauselist = (MapList)mFailureModeEffectMap.get("FAILUREMODE_CHILD");
					}
					else
					{
						failureModeCauselist= new MapList();
						mFailureModeEffectMap=failureModeMap;
						mFailureModeEffectMap.put("FAILUREMODE_CHILD", failureModeCauselist);
						failureModeMaplist.add(mFailureModeEffectMap);

					}*/

					//Filter All Failure Mode Cause and add it to in Maplist
					Map  mFailureModeCauseMap=isFailureModeCauseAlreadyExist(failureModeCauselist,failureModeCauseMap);

					if(mFailureModeCauseMap!=null)
					{					
						recommendedActionMaplist = (MapList)mFailureModeCauseMap.get("FAILUREMODECAUSE_CHILD");
					}
					else
					{
						recommendedActionMaplist= new MapList();
						mFailureModeCauseMap=failureModeCauseMap;
						mFailureModeCauseMap.put("FAILUREMODECAUSE_CHILD", recommendedActionMaplist);
						failureModeCauselist.add(mFailureModeCauseMap);

					}

					//Add all Recommeded Action and add it to in Maplist
					recommendedActionMaplist.add(recommendedActionMap);
				}
			}	
			/*Foundation Process
		Foundation Design
		Process
		Product Desisgn
		Concept Process
		Concept Design
		Machinery*/
						
			
			//Check if the number is less than 8000
			StringBuffer sbFmeaNumberLessthan=new StringBuffer();
			if(UIUtil.isNotNullAndNotEmpty(fmeaNumber))
			{
			int fmeaNumberInteger=Integer.parseInt(fmeaNumber);
			if(fmeaNumberInteger<8000)
			{
				//do Nothing

			}else{
				sbFmeaNumberLessthan.append("-The FMEA number is invalid");						
				sbFmeaNumberLessthan.append("\n");
			}

			if(!sbFmeaNumberLessthan.toString().equals(DomainConstants.EMPTY_STRING))
			{

				errorMessage= sbFmeaNumberLessthan.toString();				 
				throw new Exception();				

			}
			}
			//Check if FMEA Number Already Exist in Database
			if(UIUtil.isNotNullAndNotEmpty(fmeaNumber))
			{
			boolean isFmeaExist=isFMEAExistInDatabase(context,fmeaNumber);
			StringBuffer sbFmeaNumberAlreadyExist=new StringBuffer();
			if(isFmeaExist==true)
			{
				sbFmeaNumberAlreadyExist.append("-An FMEA with this number already exists");						
				sbFmeaNumberAlreadyExist.append("\n");

			}

			if(!sbFmeaNumberAlreadyExist.toString().equals(DomainConstants.EMPTY_STRING))
			{

				errorMessage= sbFmeaNumberAlreadyExist.toString();				 
				throw new Exception();	


			}

			}




			//Fmea Map data				
			if(fmeaType.equalsIgnoreCase("Process"))
			{
				if(vehichleProgram.equalsIgnoreCase("Foundation"))
				{
					fmeaType="Foundation Process";

				}else{

					fmeaType="Process";
				}
			}else if(fmeaType.equalsIgnoreCase("Design")){

				if(vehichleProgram.equalsIgnoreCase("Foundation"))
				{
					fmeaType="Foundation Design";

				}else{

					fmeaType="Product Design";
				}

			}else if(fmeaType.equalsIgnoreCase("Foundation Process"))
			{			
				fmeaType="Foundation Process";
				//|| fmeaType.equalsIgnoreCase("Foundation Design") || fmeaType.equalsIgnoreCase("Foundation Process") ||fmeaType.equalsIgnoreCase("Foundation Process") || ){

			}else if(fmeaType.equalsIgnoreCase("Foundation Design"))
			{
				fmeaType="Foundation Design";

			}else if(fmeaType.equalsIgnoreCase("Product Desisgn"))
			{

				fmeaType="Product Desisgn";
			}else if(fmeaType.equalsIgnoreCase("Concept Process"))
			{

				fmeaType="Concept Process";
			}else if(fmeaType.equalsIgnoreCase("Concept Design"))
			{

				fmeaType="Concept Design";
			}else if(fmeaType.equalsIgnoreCase("Machinery"))
			{

				fmeaType="Machinery";
			}else{
				errorMessage="Invaild FMEA TYPE In CSV Format.Please check FMEA Type" ;
				//resultMap.put("errorMsg", errorMessage);
				throw new Exception();
			}

			//Here check if FMEA no is empty or not
			//If FMEA no empty then get the autoname and create the object.
			//else create object with autoname
			// Chnage on 1/11/2014
			if(fmeaNumber.equals(DomainConstants.EMPTY_STRING))
			{
				fmeaNumber=getObjectAutoname(context,TYPE_FMEA_SYMBOLIC_NAME,POLICY_FMEA_SYMBOLIC_NAME);
			}else{
				
				fmeaNumber = "FMEA"+"-"+fmeaNumber;
			}

			

			
			fmeaMap.put(ATTRIBUTE_FMEA_TYPE, fmeaType); 
			//fmeaMap.put(ATTRIBUTE_DESCRIPTION, fmeaScope); here we dont required to add discrition in Map
			fmeaMap.put(ATTRIBUTE_TITLE, fmeaTitle);
			//fmeaMap.put(key, vehichleProgram)++++++VehichleProgram is not a attribute this is the connection between FMEA and Vehicle Program
			//fmeaMap.put(key, relatedSystemOrPart)+++Related SystemOrPart is not a attribute this is the connection between FMEA and Vehicle Program
			//fmeaMap.put(key, creator)///Create need to assign as a context logged in person
			fmeaMap.put(ATTRIBUTE_SYSTEM, system);
			fmeaMap.put(ATTRIBUTE_SUBSYSTEM, subSystem);
			fmeaMap.put(ATTRIBUTE_COMPONENT, component);		
			//fmeaMap.put(key, keyGateway)//this is the milestone objects need to attached in FMEA edit page
			//fmeaMap.put(key, keyDate)//this is the milestone objects start date need to attached in FMEA edit page
			fmeaMap.put(ATTRIBUTE_FMEA_TEAM, fmeaTeam);//Need to create a new attribute for this and need to set this field values in this attribute.

			// Creating the object structuire in data base 
			StringList slobjectId=createObjectStructureFromCsv(context,fmeaMap,functionMaplist,fmeaScope,fmeaNumber);
			String fmeaId=DomainConstants.EMPTY_STRING;
			if(slobjectId !=null)
			{
              fmeaId=(String)slobjectId.get(0);
			}
			//Then need to iterate Function maplist and inside function maplist need to iterate all the Maplist
			ContextUtil.commitTransaction(context);
			emxFMEA_mxJPO fmea = new emxFMEA_mxJPO(context, nextLine);
			fmea.createCheckListObject(context,fmeaId);
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Here need to add code for object creation++++++++++++++
			resultMap.put("SUCCESS",true);
			resultMap.put("objectId",fmeaId);

		}catch(Exception e)
		{
			ContextUtil.abortTransaction(context);
			resultMap.put("SUCCESS",false);
			errorMessage="FMEA Import Not Done Successfully.\n"+errorMessage+"\n";
			resultMap.put("errorMsg", errorMessage);
			
		}
		return resultMap;	
	}
	/**
	 * Q53 added to Create a object structure form CSV file
	 * @param context
	 * @param fmeaMap
	 * @param functionMaplist
	 */

	public StringList createObjectStructureFromCsv(Context context,Map fmeaMap,MapList functionMaplist,String fmeaScope,String fmeaNumber)throws Exception
	{
            StringList slFMEAId=new StringList();
		try{

			String  strFmeaName=DomainConstants.EMPTY_STRING;
			String  strFmeaObjectId=DomainConstants.EMPTY_STRING;
			DomainObject dObjFmea = new DomainObject();			
             String fmeaType=(String)fmeaMap.get(ATTRIBUTE_FMEA_TYPE);
             boolean isProcessFMEA=false;
             if (fmeaType.equalsIgnoreCase("Process")|| fmeaType.equalsIgnoreCase("Concept Process") || fmeaType.equalsIgnoreCase("Foundation Process"))
             {
            	 isProcessFMEA=true;
             }
			//get FMEA Autoname
			//strFmeaName=getObjectAutoname(context,TYPE_FMEA_SYMBOLIC_NAME,POLICY_FMEA_SYMBOLIC_NAME);
			//Creating a FMEA object
			dObjFmea.createObject(context, TYPE_FMEA, fmeaNumber,"0",POLICY_FMEA,context.getVault().getName());
			//Getting objectId of Newly Created FMEA.
			strFmeaObjectId = dObjFmea.getObjectId();	
			slFMEAId.add(strFmeaObjectId);
			dObjFmea.setId(strFmeaObjectId);
			//Setting the Attributes values to Newly Created FMEA.
			dObjFmea.setDescription(context,fmeaScope);
			dObjFmea.setAttributeValues(context, fmeaMap);
			//int functionCount=0;


			for (int i = 0; i < functionMaplist.size(); i++) {
				Map newFunctionMap=(Map)functionMaplist.get(i);		
				MapList  functionChildMapList=(MapList)newFunctionMap.get("FUNCTION_CHILD");				
				//functionCount++;
				//get all the attributes values of Function  type
				String functionAutoname=getObjectAutoname(context,TYPE_FUNCTION_SYMBOLIC_NAME,POLICY_FUNCTION_SYMBOLIC_NAME);
				String  functionOperationNumber=(String)newFunctionMap.get(ATTRIBUTE_OPERATION_NUMBER);		
				String  functionDescription=(String)newFunctionMap.get(ATTRIBUTE_OPERATION_NAME);				
				String  functionProcessFlowSymbol=(String)newFunctionMap.get(ATTRIBUTE_PROCESS_FLOW_SYMBOL);
				String  functionStation=(String)newFunctionMap.get(ATTRIBUTE_STATION);				    			  
				String  functionMeasurable=(String)newFunctionMap.get(ATTRIBUTE_FM_MEASURABLE);					
				DomainRelationship domRelFunction = createAndConnect(context, TYPE_FUNCTION, functionAutoname, "0", 
						POLICY_FUNCTION, context.getVault().getName(),RELATIONSHIP_OPERATION_FMEA,dObjFmea,true);    
				StringList slFunction = new StringList(1);
				slFunction.add(DomainRelationship.SELECT_TO_ID);
				DomainRelationship domRelObjFunction = new DomainRelationship(domRelFunction);
				Map relationshipDataFunction = domRelObjFunction.getRelationshipData(context,slFunction);
				StringList selectedObjListFunction = (StringList) relationshipDataFunction.get(DomainRelationship.SELECT_TO_ID);
				String clonedSelectedFunctionObj = (String) selectedObjListFunction.get(0);
				DomainObject objFunction = DomainObject.newInstance(context,clonedSelectedFunctionObj);
				Map attributesFunction = new HashMap();					
				//OperationNumber
				//Added this for the  Number count of function..
				//String strCnt=Integer.toString(functionCount);
				attributesFunction.put(ATTRIBUTE_OPERATION_NUMBER,functionOperationNumber);	
				attributesFunction.put(ATTRIBUTE_OPERATION_NAME,functionDescription);				
				attributesFunction.put(ATTRIBUTE_PROCESS_FLOW_SYMBOL,functionProcessFlowSymbol);
				attributesFunction.put(ATTRIBUTE_STATION,functionStation);				
				attributesFunction.put(ATTRIBUTE_FM_MEASURABLE,functionMeasurable);

				objFunction.setAttributeValues(context, attributesFunction);

				for(int j=0;j<functionChildMapList.size();j++)
				{
					Map newFailureModeMap=(Map)functionChildMapList.get(j);					
					MapList  failureAndEffectModeChildMapList=(MapList)newFailureModeMap.get("FAILUREMODE_CHILD");

					String failureModeAutoname=getObjectAutoname(context,TYPE_FAILURE_MODE_SYMBOLIC_NAME,POLICY_FAILURE_MODE_SYMBOLIC_NAME);
					String  failureModeDescription=(String)newFailureModeMap.get(ATTRIBUTE_FAILURE_MODE);
					String  fmNo=(String)newFailureModeMap.get(ATTRIBUTE_FM_SEQUENCE_ORDER);
					String  failureModeType=(String)newFailureModeMap.get(ATTRIBUTE_MODE_TYPE);
					String  failureModeAndEffects=(String)newFailureModeMap.get(ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS);
					String  failureModeAndEffectSeveirty=(String)newFailureModeMap.get(ATTRIBUTE_FM_SEVERITY);
					String  failureModeAndEffectAssemblyEffect=(String)newFailureModeMap.get(ATTRIBUTE_ASSEMBLY_EFFECT);


					//creating new object and connecting to NewFunction
					DomainRelationship domRelFailureMode = createAndConnect(context, TYPE_FAILURE_MODE, failureModeAutoname, "0", 
							POLICY_FAILURE_MODE, context.getVault().getName(),RELATIONSHIP_FAILURE_MODE,objFunction,true);    			  
					StringList slFailureMode = new StringList(1);
					slFailureMode.add(DomainRelationship.SELECT_TO_ID);
					DomainRelationship domRelObjFailureMode = new DomainRelationship(domRelFailureMode);
					Map relationshipDataFailureMode = domRelObjFailureMode.getRelationshipData(context,slFailureMode);
					StringList selectedObjListFailureMode = (StringList) relationshipDataFailureMode.get(DomainRelationship.SELECT_TO_ID);
					String selectedFailureModeObj = (String) selectedObjListFailureMode.get(0);
					DomainObject objFailureMode = DomainObject.newInstance(context,selectedFailureModeObj);
					Map attributesFailureMode = new HashMap();					
					attributesFailureMode.put(ATTRIBUTE_FAILURE_MODE,failureModeDescription);
					attributesFailureMode.put(ATTRIBUTE_FM_SEQUENCE_ORDER,fmNo);
					attributesFailureMode.put(ATTRIBUTE_MODE_TYPE,failureModeType);    						
					attributesFailureMode.put(ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS,failureModeAndEffects);
					attributesFailureMode.put(ATTRIBUTE_FM_SEVERITY,failureModeAndEffectSeveirty);    
					//Q53 Added on 15/05/2014:START:Here setting hardcoded value for Assembly effect as a "No" if it is blank in Excel sheet..
					if(failureModeAndEffectAssemblyEffect.equals(DomainConstants.EMPTY_STRING))
					{
					  failureModeAndEffectAssemblyEffect="No";
					  attributesFailureMode.put(ATTRIBUTE_ASSEMBLY_EFFECT,failureModeAndEffectAssemblyEffect);
					}else{
						
						attributesFailureMode.put(ATTRIBUTE_ASSEMBLY_EFFECT,failureModeAndEffectAssemblyEffect);
					}
					
					//Q53 Added on 15/05/2014:END:Here setting hardcoded value for Assembly effect as a "No" if it is blank in Excel sheet..
					objFailureMode.setAttributeValues(context, attributesFailureMode);			



					for(int l=0;l<failureAndEffectModeChildMapList.size(); l++)
					{
						Map newFailureModeCauseMap=(Map)failureAndEffectModeChildMapList.get(l);						
						MapList  failureCauseChildMapList=(MapList)newFailureModeCauseMap.get("FAILUREMODECAUSE_CHILD");
						String failureModeCauseAutoname=getObjectAutoname(context,TYPE_FAILURE_MODE_CAUSE_SYMBOLIC_NAME,POLICY_FAILURE_MODE_CAUSE_SYMBOLIC_NAME);    	    				

						//get all the attributes values of Failure Mode Cause type
						String failureModeCauseOccurence=(String)newFailureModeCauseMap.get(ATTRIBUTE_OCCURENCE);
						///String  failureModeCauseClass=(String)newFailureModeCauseMap.get(ATTRIBUTE_CLASS); It will calculate Automatically need to check					
						String  failureModeCauseDetection=(String)newFailureModeCauseMap.get(ATTRIBUTE_DETECTION);
						String  fcNo=(String)newFailureModeCauseMap.get(ATTRIBUTE_FC_SEQUENCE_ORDER);
						String  failureModeCauseCauses=(String)newFailureModeCauseMap.get(ATTRIBUTE_POTENTIAL_FAILURE_CAUSES);
						String  failureModeCauseQualityHistoryData=(String)newFailureModeCauseMap.get(ATTRIBUTE_QUALITY_HISTORY);
						String  failureModeCausePreDJDection=(String)newFailureModeCauseMap.get(ATTRIBUTE_PREDJ_DETECTION_CONTROL);
						String  failureModeCausePostDJDection=(String)newFailureModeCauseMap.get(ATTRIBUTE_POSTDJ_DETECTION_CONTROL);
						String  failureModeCausePreventionControls=(String)newFailureModeCauseMap.get(ATTRIBUTE_PREVENTION_CONTROLS);
						String  failureModeCauseRPN=(String)newFailureModeCauseMap.get(ATTRIBUTE_RPN);

						//creating new object and connecting NewFailureModeEffect

						DomainRelationship domRelFailureModeCause= createAndConnect(context, TYPE_FAILURE_MODE_CAUSE, failureModeCauseAutoname, "0", 
								POLICY_FAILURE_MODE_CAUSE, context.getVault().getName(),RELATIONSHIP_FAILURE_MODE_CAUSE,objFailureMode,true);    			  
						StringList slFailureModeCause = new StringList(1);
						slFailureModeCause.add(DomainRelationship.SELECT_TO_ID);
						DomainRelationship domRelObjFailureModeCause = new DomainRelationship(domRelFailureModeCause);
						Map relationshipDataFailureModeCause  = domRelObjFailureModeCause.getRelationshipData(context,slFailureModeCause);
						StringList selectedObjListFailureModeCause = (StringList) relationshipDataFailureModeCause.get(DomainRelationship.SELECT_TO_ID);
						String selectedFailureModeCauseObj = (String) selectedObjListFailureModeCause.get(0);
						DomainObject objFailureModeCause = DomainObject.newInstance(context,selectedFailureModeCauseObj);
						Map attributesFailureModeCause = new HashMap();	
						//Added to set Class Values according to Severity and Occ on 07/5/2014:START
						int intSevValue=0;
						int intOccValue=0;
						if(!isProcessFMEA){
						if(!failureModeAndEffectSeveirty.equals(DomainConstants.EMPTY_STRING))
						{
							
						    intSevValue=Integer.parseInt(failureModeAndEffectSeveirty);
						    
						    if(!failureModeCauseOccurence.equals(DomainConstants.EMPTY_STRING))
						    {
						    	intOccValue=Integer.parseInt(failureModeCauseOccurence);
						    }

							if ((intSevValue == 9) || (intSevValue == 10)) {
								attributesFailureModeCause.put(ATTRIBUTE_CLASS,"YC");							

							}else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)
									&& (intOccValue == 4 || intOccValue == 5 || intOccValue == 6
											|| intOccValue == 7 || intOccValue == 8
											|| intOccValue == 9 || intOccValue == 10)){
								attributesFailureModeCause.put(ATTRIBUTE_CLASS,"YS");
								
							}
							else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)&&(failureModeCauseOccurence.equals(DomainConstants.EMPTY_STRING)))
							{
								attributesFailureModeCause.put(ATTRIBUTE_CLASS,DomainConstants.EMPTY_STRING);


							}else{

								attributesFailureModeCause.put(ATTRIBUTE_CLASS,DomainConstants.EMPTY_STRING);

							}

						  }
						}else if(isProcessFMEA)
						{
							if(!failureModeAndEffectSeveirty.equals(DomainConstants.EMPTY_STRING))
							{
								
							    intSevValue=Integer.parseInt(failureModeAndEffectSeveirty);
							    
							    if(!failureModeCauseOccurence.equals(DomainConstants.EMPTY_STRING))
							    {
							    	intOccValue=Integer.parseInt(failureModeCauseOccurence);
							    }

								if (((intSevValue == 9) || (intSevValue == 10))&& failureModeAndEffectAssemblyEffect.equals("Yes")) {
									attributesFailureModeCause.put(ATTRIBUTE_CLASS,"OS");							

								}else if(((intSevValue == 9) || (intSevValue == 10))&& failureModeAndEffectAssemblyEffect.equals("No"))									
								{
									attributesFailureModeCause.put(ATTRIBUTE_CLASS,"CC");	
									
								}else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)
										&& (intOccValue == 4 || intOccValue == 5 || intOccValue == 6
												|| intOccValue == 7 || intOccValue == 8
												|| intOccValue == 9 || intOccValue == 10) && failureModeAndEffectAssemblyEffect.equals("Yes")){
									attributesFailureModeCause.put(ATTRIBUTE_CLASS,"HI");
									
								}else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)
										&& (intOccValue == 4 || intOccValue == 5 || intOccValue == 6
												|| intOccValue == 7 || intOccValue == 8
												|| intOccValue == 9 || intOccValue == 10) &&  failureModeAndEffectAssemblyEffect.equals("No")){
									attributesFailureModeCause.put(ATTRIBUTE_CLASS,"SC");
									
								}
								else if((intSevValue == 5 || intSevValue == 6 || intSevValue == 7 || intSevValue == 8)&&(failureModeCauseOccurence.equals(DomainConstants.EMPTY_STRING) ||failureModeAndEffectAssemblyEffect.equals(DomainConstants.EMPTY_STRING)))
								{
									attributesFailureModeCause.put(ATTRIBUTE_CLASS,DomainConstants.EMPTY_STRING);


								}else{

									attributesFailureModeCause.put(ATTRIBUTE_CLASS,DomainConstants.EMPTY_STRING);

								}

							  }
							
						}
						//Added to set Class Values according to Severity and Occ on 07/5/2014:END
										
						attributesFailureModeCause.put(ATTRIBUTE_OCCURENCE,failureModeCauseOccurence);
						//attributesFailureModeCause.put(ATTRIBUTE_CLASS,failureModeCauseClass); It will calculate Automatically need to check	
						attributesFailureModeCause.put(ATTRIBUTE_DETECTION,failureModeCauseDetection);
						attributesFailureModeCause.put(ATTRIBUTE_FC_SEQUENCE_ORDER,fcNo);
						attributesFailureModeCause.put(ATTRIBUTE_POTENTIAL_FAILURE_CAUSES,failureModeCauseCauses);  
						attributesFailureModeCause.put(ATTRIBUTE_QUALITY_HISTORY,failureModeCauseQualityHistoryData); 							
						attributesFailureModeCause.put(ATTRIBUTE_PREDJ_DETECTION_CONTROL,failureModeCausePreDJDection);
						attributesFailureModeCause.put(ATTRIBUTE_POSTDJ_DETECTION_CONTROL,failureModeCausePostDJDection);  
						attributesFailureModeCause.put(ATTRIBUTE_PREVENTION_CONTROLS,failureModeCausePreventionControls);
						attributesFailureModeCause.put(ATTRIBUTE_RPN,failureModeCauseRPN);  
						objFailureModeCause.setAttributeValues(context, attributesFailureModeCause);

						for(int m=0; m<failureCauseChildMapList.size();m++)
						{
							Map newRecommendedActionMap=(Map)failureCauseChildMapList.get(m);	

							String recommendedActionAutoname=getObjectAutoname(context,TYPE_RECOMMENDED_ACTION_MODE_SYMBOLIC_NAME,POLICY_RECOMMENDED_ACTION_SYMBOLIC_NAME);    	    				
							//get all the attributes values of Recommended Action type

							//Modified for CR106 Start
							String recommndedActionOccurence=(String)newRecommendedActionMap.get(ATTRIBUTE_REVISED_OCCURRENCE);												
							String  recommendedActionDetection=(String)newRecommendedActionMap.get(ATTRIBUTE_REVISED_DETECTION);
							String  recommendedActionSeverity=(String)newRecommendedActionMap.get(ATTRIBUTE_REVISED_SEVERITY);	
							//Modified for CR106 End
							String  recommendedActionTaken=(String)newRecommendedActionMap.get(ATTRIBUTE_ACTION_TAKEN);
							String  recommendedAction=(String)newRecommendedActionMap.get(ATTRIBUTE_RECOMMENDED_ACTION);
							String  recommendedActionTargetEndDate=(String)newRecommendedActionMap.get(ATTRIBUTE_TARGET_END_DATE);
							String  recommendedActionCompletionEndDate=(String)newRecommendedActionMap.get(ATTRIBUTE_COMPLETION_DATE);
							//Modified for CR106 Start
							String  recommendedActionRPN=(String)newRecommendedActionMap.get(ATTRIBUTE_REVISED_RPN);		
							//Modified for CR106 End

							DomainRelationship domRelRecommendedAction= createAndConnect(context, TYPE_RECOMMENDED_ACTION, recommendedActionAutoname, "0", 
									POLICY_RECOMMENDED_ACTION, context.getVault().getName(),RELATIONSHIP_FAILURE_MODE_CAUSE_RPN,objFailureModeCause,true);    			  
							StringList slRecommendedAction = new StringList(1);
							slRecommendedAction.add(DomainRelationship.SELECT_TO_ID);
							DomainRelationship domRelObjRecommendedAction = new DomainRelationship(domRelRecommendedAction);
							Map relationshipDataRecommendedAction  = domRelObjRecommendedAction.getRelationshipData(context,slRecommendedAction);
							StringList selectedObjListRecommendedAction= (StringList) relationshipDataRecommendedAction.get(DomainRelationship.SELECT_TO_ID);
							String selectedRecommendedActionObj = (String) selectedObjListRecommendedAction.get(0);
							DomainObject objRecommendedAction= DomainObject.newInstance(context,selectedRecommendedActionObj);
							Map attributesRecommendedAction = new HashMap();	
							//Modified for CR106 Start
							attributesRecommendedAction.put(ATTRIBUTE_REVISED_OCCURRENCE,recommndedActionOccurence);
							attributesRecommendedAction.put(ATTRIBUTE_REVISED_SEVERITY,recommendedActionSeverity); 
							attributesRecommendedAction.put(ATTRIBUTE_REVISED_DETECTION,recommendedActionDetection);
							//Modified for CR106 End
							attributesRecommendedAction.put(ATTRIBUTE_ACTION_TAKEN,recommendedActionTaken);  
							attributesRecommendedAction.put(ATTRIBUTE_RECOMMENDED_ACTION,recommendedAction);
							attributesRecommendedAction.put(ATTRIBUTE_TARGET_END_DATE,recommendedActionTargetEndDate);  
							attributesRecommendedAction.put(ATTRIBUTE_COMPLETION_DATE,recommendedActionCompletionEndDate);
							//Modified for CR106 Start
							attributesRecommendedAction.put(ATTRIBUTE_REVISED_RPN,recommendedActionRPN); 
							//Modified for CR106 End
							objRecommendedAction.setAttributeValues(context, attributesRecommendedAction);



						}


					}						

				}

			}			


			//}


		}catch(Exception e)
		{
			throw new Exception(e);
			//e.printStackTrace();
		}
		return slFMEAId;
	}


	/**
	 * Q53
	 * Added to get Autoname of Object
	 * @param context
	 * @param type
	 * @param policy
	 * @return
	 * @throws Exception
	 */

	public String getObjectAutoname(Context context,String type,String policy)throws Exception

	{ 
		try{

			String strAutoname =  com.matrixone.apps.domain.util.FrameworkUtil.autoName(context,

					type,//Symbolic type name
					null,
					policy,//Symbolic policy name
					null,
					null,
					true,
					true);	
			return strAutoname;

		}catch(Exception e)
		{
			throw new MatrixException(e);
		}	  
	}
	/**
	 * Q53
	 * Get the sorted MapList
	 * @param OrignalMaplist
	 * @return
	 * @throws Exception
	 */


	public Map isFunctionAlreadyExist(MapList OrignalMaplist,Map functionMap,String strKey)throws Exception
	{
		int sizeMap = OrignalMaplist.size();
		Map iscurentMapAdded=null;
		for(int iCount=0; iCount<sizeMap;iCount++)		
		{
			Map map=(Map)OrignalMaplist.get(iCount);
			String nameForCheck=(String)map.get(strKey);
			nameForCheck=nameForCheck.replaceAll(" ", "");
			nameForCheck=nameForCheck.replaceAll("\n", "");
			String fromNewMap=(String)functionMap.get(strKey);		
			fromNewMap=fromNewMap.replaceAll(" ", "");
			fromNewMap=fromNewMap.replaceAll("\n", "");
			//need to remove space trim

			if(nameForCheck.equals(fromNewMap))
			{
				return map;					

			}		

		}
		return iscurentMapAdded;
	}

	/**Q53
	 * Get the sorted MapList
	 * @param OrignalMaplist
	 * @return
	 * @throws Exception
	 */



	public Map isFailureModeAlreadyExist(MapList OrignalMaplist ,Map failureModeMap)throws Exception
	{		
          String strKey1="Failure Mode";
          String strKey2= "Potential Failure Effects";      
          String strKey3= "FM Sequence Order";
		//Map iscurentMapAdded=isFunctionAlreadyExist(OrignalMaplist, failureModeMap, "Failure Mode");
		
		int sizeMap = OrignalMaplist.size();
		Map iscurentMapAdded=null;
		for(int iCount=0; iCount<sizeMap;iCount++)		
		{
			Map map=(Map)OrignalMaplist.get(iCount);
			String nameForCheck1=(String)map.get(strKey1);			
			nameForCheck1=nameForCheck1.replaceAll(" ", "");
			nameForCheck1=nameForCheck1.replaceAll("\n", "");
			String fromNewMap1=(String)failureModeMap.get(strKey1);		
			fromNewMap1=fromNewMap1.replaceAll(" ", "");
			fromNewMap1=fromNewMap1.replaceAll("\n", "");
			
			
			String nameForCheck2=(String)map.get(strKey2);			
			nameForCheck2=nameForCheck2.replaceAll(" ", "");
			nameForCheck2=nameForCheck2.replaceAll("\n", "");
			String fromNewMap2=(String)failureModeMap.get(strKey2);		
			fromNewMap2=fromNewMap2.replaceAll(" ", "");
			fromNewMap2=fromNewMap2.replaceAll("\n", "");
			
			
			String nameForCheck3=(String)map.get(strKey3);			
			nameForCheck3=nameForCheck3.replaceAll(" ", "");
			nameForCheck3=nameForCheck3.replaceAll("\n", "");
			String fromNewMap3=(String)failureModeMap.get(strKey3);		
			fromNewMap3=fromNewMap3.replaceAll(" ", "");
			fromNewMap3=fromNewMap3.replaceAll("\n", "");

			
			if(nameForCheck1.equals(fromNewMap1) && nameForCheck2.equals(fromNewMap2) && nameForCheck3.equals(fromNewMap3) )
			{
				return map;					

			}		

		}
		return iscurentMapAdded;

	}
	
/**
 * Check if the effect the different for Same Failure Mode
 * @param OrignalMaplist
 * @param failureModeMap
 * @return
 * @throws Exception
 */
	public Map isFailureModeEffectAlreadyExist(MapList OrignalMaplist ,Map failureModeMap)throws Exception
	{		

		Map iscurentMapAdded=isFunctionAlreadyExist(OrignalMaplist, failureModeMap, "Potential Failure Effects");
		return iscurentMapAdded;

	}

	
	/**
	 * Q53
	 * Get the sorted MapList
	 * @param OrignalMaplist
	 * @return
	 * @throws Exception
	 */


	public Map isFailureModeCauseAlreadyExist(MapList OrignalMaplist,Map failureModeCauseMap)throws Exception
	{

		//Map iscurentMapAdded=isFunctionAlreadyExist(OrignalMaplist, failureModeCauseMap, "Potential Failure Causes");
		//return iscurentMapAdded;		
		String strKey1="Potential Failure Causes";
        String strKey2= "FC Sequence Order";        
		//Map iscurentMapAdded=isFunctionAlreadyExist(OrignalMaplist, failureModeMap, "Failure Mode");
		
		int sizeMap = OrignalMaplist.size();
		Map iscurentMapAdded=null;
		for(int iCount=0; iCount<sizeMap;iCount++)		
		{
			Map map=(Map)OrignalMaplist.get(iCount);
			String nameForCheck1=(String)map.get(strKey1);			
			nameForCheck1=nameForCheck1.replaceAll(" ", "");
			nameForCheck1=nameForCheck1.replaceAll("\n", "");
			String fromNewMap1=(String)failureModeCauseMap.get(strKey1);		
			fromNewMap1=fromNewMap1.replaceAll(" ", "");
			fromNewMap1=fromNewMap1.replaceAll("\n", "");
			
			
			String nameForCheck2=(String)map.get(strKey2);			
			nameForCheck2=nameForCheck2.replaceAll(" ", "");
			nameForCheck2=nameForCheck2.replaceAll("\n", "");
			String fromNewMap2=(String)failureModeCauseMap.get(strKey2);		
			fromNewMap2=fromNewMap2.replaceAll(" ", "");
			fromNewMap2=fromNewMap2.replaceAll("\n", "");
			if(nameForCheck1.equals(fromNewMap1) && nameForCheck2.equals(fromNewMap2) )
			{
				return map;					

			}		

		}
		return iscurentMapAdded;



	}


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++CSV import :END++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++XML REPORT:START++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public FMEASheet generateXMLReport(Context context,String args[])throws Exception
	{
		if (args == null || args.length < 1) {
			throw (new IllegalArgumentException());

		}


		String objectId = args[0];
		// XML Report generation: Start
		String[] methodargs = new String[1];
		methodargs[0] = objectId;
		populateXMLFile(context,methodargs);
		return fmeas;

		// XML Report generation: End

	}

	// XML Report generation: Start
	public void populateXMLFile(Context context,String args[])throws Exception
	{
		String objectId = args[0];
		DomainObject ecrObj = null;
		ecrObj = DomainObject.newInstance(context, objectId);
		String sType = ecrObj.getInfo(context, "type");
		StringList slSelect = getTypeSelectList(context, sType);
		Map attributeMap = ecrObj.getInfo(context, slSelect);
		String fmeaTitle=DomainConstants.EMPTY_STRING;
		String fmeaScope=DomainConstants.EMPTY_STRING;
		String fmeaName=DomainConstants.EMPTY_STRING;
		String fmeaTeam=DomainConstants.EMPTY_STRING;
		String fmeaType=DomainConstants.EMPTY_STRING;
		String fmeaNumber=DomainConstants.EMPTY_STRING;
		String  vehichleProgram=DomainConstants.EMPTY_STRING;
		String keyGateway=DomainConstants.EMPTY_STRING;
		String keyDate=DomainConstants.EMPTY_STRING;
		String system=DomainConstants.EMPTY_STRING;
		String subSystem=DomainConstants.EMPTY_STRING;
		String component=DomainConstants.EMPTY_STRING;
		if(attributeMap !=null && attributeMap.size()>0)
		{		
			fmeaTitle=(String)attributeMap.get("attribute[Title]");
			fmeaScope =(String)attributeMap.get("description");
			fmeaName =(String)attributeMap.get("name");
			fmeaType=(String)attributeMap.get("attribute[FMEA Type]");
			fmeaTeam =(String)attributeMap.get("attribute[Responsible Team]");
			fmeaNumber =(String)attributeMap.get("attribute[FMEA Number]");
			vehichleProgram =(String)attributeMap.get("attribute[Vehicle Program]");
			keyGateway =(String)attributeMap.get("attribute[Key Gateway]");
			keyDate =(String)attributeMap.get("attribute[Key Date]");
			system=(String)attributeMap.get("attribute[System]");
			subSystem=(String)attributeMap.get("attribute[Subsystem]");
			component=(String)attributeMap.get("attribute[Component]");
			// FMEA elements
			fmeas.setFMEATitle(fmeaTitle);
			fmeas.setFMEAScope(fmeaScope);
			fmeas.setFMEAName(fmeaName);
			fmeas.setFMEAType(fmeaType);
			fmeas.setFMEATeam(fmeaTeam);
			fmeas.setFMEANumber(fmeaNumber);
			fmeas.setFMEAAssociatedProgram(vehichleProgram);
			fmeas.setFMEAKeyGateway(keyGateway);
			fmeas.setFMEAKeyDate(keyDate);
			fmeas.setFMEASystem(system);
			fmeas.setFMEASubSystem(subSystem);
			fmeas.setFMEAComponent(component);		


		}

		HashMap hm = new HashMap();
		hm.put("objectId", args[0]);		
		MapList totalResultMapList = (MapList) getCompleteFMEA(context, JPO
				.packArgs(hm));		

		MapList  functionsMapList=getFunctions(context,totalResultMapList);
		MapList  failureModesMapList=getFailureModes(context,totalResultMapList);
		MapList  failureModeEffectsMapList=getFailureModeEffects(context,totalResultMapList);
		MapList  failureModeCausesMapList=getFailureModeCauses(context,totalResultMapList);
		MapList  recommendedActionsMapList=getRecommendedActions(context,totalResultMapList);

		// Failure Modes element
		FailureModes fms;
		fms = new FailureModes();
		fmeas.setFailureModes(fms);

		// get all function
		for (int i = 0; i < functionsMapList.size(); i++) {
			Map newFunctionMap=(Map)functionsMapList.get(i);
			String functionid=(String)newFunctionMap.get(SELECT_ID);			
			String  functiontype=(String)newFunctionMap.get(SELECT_TYPE);	
			if(!functiontype.equals(TYPE_PART)){
				StringList connectedFailureMode= new StringList();
				Object objFunction = newFunctionMap.get("from[Failure Mode].to.id");			
				if(objFunction!=null){
					if(objFunction instanceof StringList){
						connectedFailureMode = (StringList)objFunction;
					}else if(objFunction instanceof String){
						connectedFailureMode.add((String)objFunction);
					}
				}			
				StringList slSelectFunctions = getTypeSelectList(context, functiontype);
				DomainObject functionObj = DomainObject.newInstance(context, functionid);		
				Map attributeMapFunctions = functionObj.getInfo(context, slSelectFunctions);	

				String  functionDescription=(String)attributeMapFunctions.get(SELECT_ATTRIBUTE_OPERATION_NAME);
				String  functionNumber=(String)attributeMapFunctions.get(SELECT_ATTRIBUTE_OPERATION_NUMBER);
				String  functionMeasurable=(String)attributeMapFunctions.get(SELECT_ATTRIBUTE_FM_MEASURABLE);
				String  functionRevision=(String)attributeMapFunctions.get("revision");
				String  functionStatus=(String)attributeMapFunctions.get("current");

				Function func = new Function();
				func.setFunctionDescription(functionDescription);
				func.setFunctionNumber(functionNumber);
				func.setFunctionMeasurable(functionMeasurable);
				func.setRevision(functionRevision);
				func.setStatus(functionStatus);
				fms.getFunction().add(func);		

				// Failure mode
				for (int j=0; j<failureModesMapList.size(); j++ ){
					FailureMode fm = new FailureMode();
					Map newFailureModeMap=(Map)failureModesMapList.get(j);
					String failureModeId=(String)newFailureModeMap.get(SELECT_ID);			
					String  failureModetype=(String)newFailureModeMap.get(SELECT_TYPE);
					StringList connectedFailureModeEffect=new StringList();
					Object objfailureMode=newFailureModeMap.get("from[Failure Mode Effect].to.id");
					if(objfailureMode !=null){
						if(objfailureMode instanceof StringList) {
							connectedFailureModeEffect=(StringList)objfailureMode;
						}else if(objfailureMode instanceof String) {
							connectedFailureModeEffect.add((String)objfailureMode);
						}
					}

					StringList slSelectFailureMode = getTypeSelectList(context, failureModetype);
					DomainObject failureModeObj = DomainObject.newInstance(context, failureModeId);		
					Map attributeMapFailureMode = failureModeObj.getInfo(context, slSelectFailureMode);					
					String  failureModeDescription=(String)attributeMapFailureMode.get("attribute[Failure Mode]");
					String  failureModeType=(String)attributeMapFailureMode.get("attribute[Mode Type]");
					if(connectedFailureMode.contains(failureModeId)) {					

						fm.setFailureModeDescription(failureModeDescription);
						fm.setFailureModeType(failureModeType);
						func.getFailureMode().add(fm);



						// Effect
						for(int m=0;m<failureModeEffectsMapList.size();m++){				
							Map newFailureModeEffectMap=(Map)failureModeEffectsMapList.get(m);
							String failureModeEffectId=(String)newFailureModeEffectMap.get(SELECT_ID);			
							String  failureModeEffecttype=(String)newFailureModeEffectMap.get(SELECT_TYPE);
							StringList connectedFailureModeCause=new StringList();
							Object objfailureModeEffect=newFailureModeEffectMap.get("from[Failure Mode Cause].to.id");
							if(objfailureModeEffect !=null){
								if(objfailureModeEffect instanceof StringList) {
									connectedFailureModeCause=(StringList)objfailureModeEffect;
								}else if(objfailureModeEffect instanceof String) {
									connectedFailureModeCause.add((String)objfailureModeEffect);
								}
							}

							StringList slSelectfailureModeEffect = getTypeSelectList(context, failureModeEffecttype);
							DomainObject failureModeEffectObj = DomainObject.newInstance(context, failureModeEffectId);		
							Map attributeMapFailureModeEffect = failureModeEffectObj.getInfo(context, slSelectfailureModeEffect);	
							String failureModeEffectPotentialEffect=(String)attributeMapFailureModeEffect.get(SELECT_ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS);
							String  failureModeEffectSeverity=(String)attributeMapFailureModeEffect.get(SELECT_ATTRIBUTE_FM_SEVERITY);
							if(connectedFailureModeEffect.contains(failureModeEffectId))
							{	

								fm.setEffect(new Effect());
								fm.getEffect().setPotentialEffect(failureModeEffectPotentialEffect);
								fm.getEffect().setSeverity(failureModeEffectSeverity);


								// Cause
								for (int k=0; k<failureModeCausesMapList.size(); k++){
									Cause cause = new Cause();
									Map newFailureModeCauseMap=(Map)failureModeCausesMapList.get(k);	
									String failureModeCauseId=(String)newFailureModeCauseMap.get(SELECT_ID);			
									String  failureModeCauseType=(String)newFailureModeCauseMap.get(SELECT_TYPE);
									StringList connectedRecommendedAction=new StringList();
									Object objRecommendedAction=newFailureModeCauseMap.get("from[Failure Mode Cause RPN].to.id");
									if(objRecommendedAction !=null){
										if(objRecommendedAction instanceof StringList) {
											connectedRecommendedAction=(StringList)objRecommendedAction;
										}else if(objfailureModeEffect instanceof String) {
											connectedRecommendedAction.add((String)objRecommendedAction);
										}
									}
									StringList slSelectfailureModeCause = getTypeSelectList(context, failureModeCauseType);
									DomainObject failureModeCauseObj = DomainObject.newInstance(context, failureModeCauseId);

									Map attributeMapFailureModeCause = failureModeCauseObj.getInfo(context, slSelectfailureModeCause);	
									String failureModeCauseOccurence=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_OCCURENCE);
									String  failureModeCauseClass=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_CLASS);					
									String  failureModeCauseDetection=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_DETECTION);
									String  failureModeCauseCauses=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_POTENTIAL_FAILURE_CAUSES);
									String  failureModeCausePreDJDection=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_PREDJ_DETECTION_CONTROL);
									String  failureModeCausePostDJDection=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_POSTDJ_DETECTION_CONTROL);
									String  failureModeCausePreventionControls=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_PREVENTION_CONTROLS);
									String  failureModeCauseRPN=(String)attributeMapFailureModeCause.get(SELECT_ATTRIBUTE_RPN);
									if(connectedFailureModeCause.contains(failureModeCauseId)){
										cause.setCauseOccurence(failureModeCauseOccurence);
										cause.setCauseClass(failureModeCauseClass);
										cause.setDetection(failureModeCauseDetection);
										cause.setFailureCause(failureModeCauseCauses);
										cause.setPostDJDetectionControl(failureModeCausePostDJDection);
										cause.setPreDJDetectionControl(failureModeCausePreDJDection);
										cause.setPreventionControls(failureModeCausePreventionControls);
										cause.setRPN(failureModeCauseRPN);
										fm.getEffect().getCause().add(cause);


										// Action
										for (int l=0; l<recommendedActionsMapList.size(); l++) {
											Action action = new Action();
											Map newRecommendedActionMap=(Map)recommendedActionsMapList.get(l);	
											String recommendedActionId=(String)newRecommendedActionMap.get(SELECT_ID);			
											String  recommendedActionType=(String)newRecommendedActionMap.get(SELECT_TYPE);						  
											StringList slSelectRecommendedActions = getTypeSelectList(context, recommendedActionType);
											DomainObject recommendedActionObj = DomainObject.newInstance(context, recommendedActionId);							
											Map attributeMaprecommendedAction = recommendedActionObj.getInfo(context, slSelectRecommendedActions);	
											
											//Modified for CR106 Start
											String recommndedActionOccurence=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_REVISED_OCCURRENCE);												
											String  recommendedActionDetection=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_REVISED_DETECTION);
											String  recommendedActionSeverity=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_REVISED_SEVERITY);	
											//Modified for CR106 End
											
											String  recommendedActionTaken=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_ACTION_TAKEN);
											String  recommendedAction=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_RECOMMENDED_ACTION);
											String  recommendedActionTargetEndDate=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_TARGET_END_DATE);
											String  recommendedActionCompletionEndDate=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_COMPLETION_DATE);
											//Modified for CR106 Start
											String  recommendedActionRPN=(String)attributeMaprecommendedAction.get(SELECT_ATTRIBUTE_RPN);
											//Modified for CR106 End
											if(connectedRecommendedAction.contains(recommendedActionId))
											{
												action.setActionTaken(recommendedActionTaken);
												action.setCompletionDate(recommendedActionCompletionEndDate);
												action.setRecommendedAction(recommendedAction);
												action.setResponsibility("");
												action.setRevisedDetection(recommendedActionDetection);
												action.setRevisedOccurence(recommndedActionOccurence);
												action.setRevisedRPN(recommendedActionRPN);
												action.setRevisedSeverity(recommendedActionSeverity);
												action.setTargetEndDate(recommendedActionTargetEndDate);
												cause.getAction().add(action);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// AffectedItems element
		MapList  affectedItemMapList=getAffectedItem(context,totalResultMapList);

		for(int n=0;n<affectedItemMapList.size();n++)
		{
			Map  mpAffectedItem=(Map)affectedItemMapList.get(n);
			String sTypeAffectedItem = (String)mpAffectedItem.get(SELECT_TYPE);
			String sTypeAffectedItemId = (String)mpAffectedItem.get(SELECT_ID);
			String sNameAffectedItem = (String)mpAffectedItem.get(SELECT_NAME);
			String sDescriptionAffectedItem = (String)mpAffectedItem.get(SELECT_DESCRIPTION);   

			fmeas.setAffectedItem(new AffectedItem());
			fmeas.getAffectedItem().setType(sTypeAffectedItem);
			fmeas.getAffectedItem().setName(sNameAffectedItem);
			fmeas.getAffectedItem().setDescription(sDescriptionAffectedItem);



		}
		
	}

	/**
	 * Save the XML document on the filesystem
	 * @return 
	 * 
	 */	
	private static void save(String filename)

	{
		try {

			JAXBContext jc = JAXBContext.newInstance( "com.ds.fmea.xsd" );
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal(fmeas, new FileOutputStream(filename));
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (JAXBException je) {
			je.printStackTrace();
		}
	}
	// XML Report generation: Start	



	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++XML REPORT:END++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	/**
	 * Q53
	 * Added
	 */
	public void createProcessFlowAndControlPlanForFailureMode(Context context,
			String args[]) throws Exception {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Q53
	 * @param context
	 * @param args
	 * @throws Exception
	 */
	public void reviseRelatedItems(Context context, String args[])
	throws Exception {

		try {
			if (context == null || args == null) {
				throw new IllegalArgumentException();
			}

			String strObjectId = args[0]; // object id of previous revision
			String strNewRev = args[1];

			if (strObjectId == null || "".equalsIgnoreCase(strObjectId)) {
				throw new IllegalArgumentException();
			}

			if (strNewRev == null || "".equalsIgnoreCase(strNewRev)) {
				throw new IllegalArgumentException();
			}

			String strLanguage = context.getSession().getLanguage();
			String strLatestRevObjId = ""; // new object id of revised object
			int count = 0;
			String SELECT_NEXT_ID = FrameworkProperties.getProperty(context,
			"emxProgramCentral.Subscriptions.Select.Next.Id");
			StringList slBusSel = new StringList();
			slBusSel.add(SELECT_NEXT_ID);
			DomainObject dmoOldObject = DomainObject.newInstance(context,
					strObjectId);

			Map dObjInfoMap = dmoOldObject.getInfo(context, slBusSel);
			// get the new revision's object id
			String nextId = (String) dObjInfoMap.get(SELECT_NEXT_ID);

			StringList slSelects = new StringList(4);
			slSelects.addElement(DomainConstants.SELECT_ID);
			slSelects.addElement(DomainConstants.SELECT_TYPE);
			slSelects.addElement(DomainConstants.SELECT_NAME);
			slSelects.addElement(DomainConstants.SELECT_OWNER);
			StringList relSelects = new StringList();
			relSelects.addElement(DomainRelationship.SELECT_NAME);
			relSelects.addElement(DomainRelationship.SELECT_ID);
			DomainObject oldRev = DomainObject
			.newInstance(context, strObjectId);
			MapList mlControlPlanOperations = oldRev.getRelatedObjects(context,
					"Control Plan Failure Mode", "*", slSelects, relSelects,
					true, false, (short) 1, "revision==last", null);
			
			if (mlControlPlanOperations != null
					&& mlControlPlanOperations.size() > 0) {
				Map map = null;
				String sCPOId = "";
				String sCPOOwner = "";
				DomainObject dCPO = DomainObject.newInstance(context);
				String argsNotify[] = new String[4];

				argsNotify[2] = "Failure Mode is changed/updated. Control Plan needs update.";
				argsNotify[3] = "Failure Mode for the corresponding Control Plan has been modified/changed.\nPlease update the Control Plan.";
				for (int i = 0; i < mlControlPlanOperations.size(); i++) {
					map = (Map) mlControlPlanOperations.get(i);
					sCPOId = (String) map.get(DomainConstants.SELECT_ID);
					sCPOOwner = (String) map.get(DomainConstants.SELECT_OWNER);
					if (isNotBlank(sCPOId)) {
						dCPO = DomainObject.newInstance(context, sCPOId);
						dCPO.reviseObject(context, true);
						try {
							argsNotify[0] = sCPOId;
							argsNotify[1] = sCPOOwner;
							notifyOwner(context, argsNotify);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			MapList mlWIOperations = oldRev.getRelatedObjects(context,
					"Work Instruction Failure Mode", "*", slSelects,
					relSelects, true, false, (short) 1, "revision==last", null);
			
			if (mlWIOperations != null && mlWIOperations.size() > 0) {
				Map map1 = null;
				String sCPOId1 = "";
				String sCPOOwner1 = "";
				DomainObject dCPO1 = DomainObject.newInstance(context);
				// String argsNotify1[] = new String[4];

				// argsNotify1[2]="Failure Mode is changed/updated. WI needs update.";
				// argsNotify1[3]="Failure Mode for the corresponding Control Plan has been modified/changed.\nPlease update the Control Plan.";
				for (int ii = 0; ii < mlWIOperations.size(); ii++) {
					map1 = (Map) mlWIOperations.get(ii);
					sCPOId1 = (String) map1.get(DomainConstants.SELECT_ID);
					sCPOOwner1 = (String) map1
					.get(DomainConstants.SELECT_OWNER);
					if (isNotBlank(sCPOId1)) {
						dCPO1 = DomainObject.newInstance(context, sCPOId1);
						dCPO1.reviseObject(context, true);
						try {
							// argsNotify[0]=sCPOId;
							// argsNotify[1]=sCPOOwner;
							// notifyOwner(context,argsNotify);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Q53
	 * @param context
	 * @param args
	 * @throws Exception
	 * @throws FrameworkException
	 */
	public void notifyOwner(Context context, String[] args) throws Exception,
	FrameworkException {

		// Get the Object Id of the context Engineering Change object.
		String strObjectId = (String) args[0];
		String strSubjectKey = "Quality Attribute Modified:TS Document Update Needed";
		String strMessageKey = "Quality Attribute has been modified.\nA Failure Mode is associate with it.\nPlease do the needful";
		String strAssignee = "Test Everything";
		if (args.length > 1) {
			strAssignee = args[1];
		}
		if (args.length > 2) {
			strSubjectKey = args[2];
		}
		if (args.length > 3) {
			strMessageKey = args[3];
		}

		// Form the stringlist of all the assignee names to send the message
		StringList slAssigneeList = new StringList(strAssignee);

		// Form the subject and the message body
		String strLanguage = context.getSession().getLanguage();
		String[] subjectKeys = {};
		String[] subjectValues = {};
		String[] messageKeys = {};
		String[] messageValues = {};

		// Form the message attachment
		StringList slAttachments = new StringList();
		slAttachments.add(strObjectId);

		// Send the notification to all the Assignees

		${CLASS:emxMailUtilBase}.sendNotification(context, slAssigneeList, null,
				null, strSubjectKey, subjectKeys, subjectValues, strMessageKey,
				messageKeys, messageValues, slAttachments, null);

	}
	
	
	/**
	 * getProjectName - gets attribute iPLMProjectDisplayName
	 * @param context
	 * @param args
	 * @throws Exception
	 * @author RNEEMA1
	 * @Incident -14149
	 */
	public String getProjectName(Context context,String args[]) throws Exception{
	
		StringBuffer striPLMProjName= new StringBuffer("");
		HashMap inputMap 			= (HashMap)JPO.unpackArgs(args);
		HashMap paramMap 			= (HashMap) inputMap.get("paramMap");
		String objectId 			= (String) paramMap.get("objectId");
		DomainObject dob 			= new DomainObject(objectId);
		StringList busSelects 		= new StringList(2);
        busSelects.addElement( SELECT_ID );
        busSelects.addElement( SELECT_TYPE );
        DomainObject doPerson 		= new DomainObject(objectId);
		//Modified by vkannan for Incident - 14149
      //Modified by rneema1 for CR57(Added 3 new types of projects in PPPM)
        MapList mlVehicleProject = dob.getRelatedObjects(context, "FMEA Project Space",//"*",//relPatter.getPattern(), // relationship pattern

        		 "iPLMVehicleProject,iPLMUNITPTProjectSpace,iPLMUNITElectricalProject,iPLMUNITTransmissionProject,iPLMUNITArchitectureProject,iPLMUNITEpropulsionProject", // object pattern

        		 	busSelects, // object selects

        		 	null, // relationship selects

					false, // to direction

					true, // from direction

					(short) 0, // recursion level

					null, // object where clause

					null);
         
         DomainObject domProjObj 	= new DomainObject();
         String strProjDisplayName 	= DomainConstants.EMPTY_STRING;
         String strProjName 		=  DomainConstants.EMPTY_STRING;
         Iterator mlItr 			= mlVehicleProject.iterator();
        
         while (mlItr.hasNext()) 
         {
        	 Map mProject 			= (Map) mlItr.next();
             String strObjId 		= (String) mProject.get( SELECT_ID );
             domProjObj  			= new DomainObject(strObjId);
             strProjDisplayName 	= domProjObj.getAttributeValue(context, "iPLMProjectDisplayName");
             strProjName 			= domProjObj.getInfo(context, "name");
             if(UIUtil.isNullOrEmpty(strProjDisplayName))
     		 {
     			strProjDisplayName 		= strProjName;
     		 }
             striPLMProjName.append("<table class=\"\"><tbody><tr><td> ");
             striPLMProjName.append("<a href=\"javascript:showNonModalDialog('../common/emxTree.jsp?objectId=" + strObjId + "', '700', '600')\">" + strProjDisplayName + "</a>");
             striPLMProjName.append("</td> </tr></tbody></table> ");	
         }
          
		return striPLMProjName.toString();
	}
	
	
	/**
		 * This method is used to dispaly Failure Mode and Potential Failure Effects attribute value .
		 *
		 * @param context
		 * @param args
		 * @return Vector
		 * @throws Exception
		 * @author rgandhi
		 * @since 13235
		 */
	
	/*	 public Vector displayFailureModeAndEffects(Context context,String args[]) throws Exception
	{
		HashMap programMap = (HashMap) JPO.unpackArgs( args );
		MapList objList = (MapList) programMap.get("objectList");
		
		Map colList = (Map) programMap.get("columnMap");
		String strColumnName = (String) colList.get("name");
		
		int size = objList.size();
	
		StringBuffer sbFinalString = new StringBuffer();
		StringList slFMList = new StringList();
		Vector exprVector = new Vector(size);	
		
		String strFMValue=DomainConstants.EMPTY_STRING;
		String strFMId=DomainConstants.EMPTY_STRING;
		String strType=DomainConstants.EMPTY_STRING;
		
		Map mItem;
		
		try 
		
		{
			for ( int i = 0; i < size; i++ )
			{ 
				mItem = (Map) objList.get(i);
				strFMId = (String) mItem.get(DomainConstants.SELECT_ID);
						
				setId(strFMId);
				
				strType = getInfo(context ,DomainConstants.SELECT_TYPE);
					
				if(strType.equalsIgnoreCase(TYPE_FAILURE_MODE))
				{

			        if(strColumnName.equalsIgnoreCase("Failure Mode"))
					{
					strFMValue = getAttributeValue(context, ATTRIBUTE_FAILURE_MODE);
					}
					else
					{
						strFMValue = getAttributeValue(context, ATTRIBUTE_POTENTIAL_FAILURE_EEFFECTS);
						
					}						
		
				   slFMList = FrameworkUtil.split(strFMValue,"\n");		
					
				   sbFinalString.append("<html>");
				   sbFinalString.append("<table>");
					
					int iSize = slFMList.size();
					
					for(int iSplit=0 ;iSplit<iSize ; iSplit++)
					{
					   
					     sbFinalString.append("<tr><td>");
					   
						   if(strColumnName.equalsIgnoreCase("Failure Mode"))
						{
						   sbFinalString.append("<a href=\"javascript:showNonModalDialog('../common/emxTree.jsp?objectId=" + strFMId + "', '700', '600')\">" + slFMList.get(iSplit) + "</a>");
						}
						else
						{
							 sbFinalString.append(slFMList.get(iSplit));
							
						}
						   sbFinalString.append("</td></tr>");
					}
					
					sbFinalString.append("</table>");
					sbFinalString.append("</html>");
		
					exprVector.addElement(sbFinalString.toString());
				}
					
				else{

					exprVector.addElement("");
				}
				sbFinalString.delete(0, sbFinalString.length());

			}
		
		}
		catch(Exception ex)
		{
			ex.toString();
			
		}
		
        return exprVector;
		
	}*/
		
		/**
		 * This method is used to update Failure Mode attribute value .
		 *
		 * @param context
		 * @param args
		 * @throws Exception
		 * @author rgandhi
		 * @since 13235
		 */
		
	/*	public void updateFailureMode(Context context,String[] args)throws Exception
	{
		try 
		{
			HashMap programMap = (HashMap)JPO.unpackArgs(args);
			HashMap paramMap = (HashMap)programMap.get("paramMap");   
			String sFMEffectId              = (String)paramMap.get("objectId");
			
			String sFMEffectAttribute              = (String)paramMap.get("New Value");	  	
			
			if(UIUtil.isNotNullAndNotEmpty(sFMEffectId))
			{ 	    
				DomainObject  doObjectFmeaType = DomainObject.newInstance(context, sFMEffectId);	    
				doObjectFmeaType.setAttributeValue(context, ATTRIBUTE_FAILURE_MODE, sFMEffectAttribute);
			}   

	}
	catch(Exception ex)
	{
		ex.toString();
		
	}

		}*/

	/** Added for INC 16331 Start
	 * This method is used to update the RA RPN attribute value .
	 * @param context
	 * @param dObject
	 * @param strAttrName
	 * @param strAttrValue
	 * @throws Exception
	 * @author skovvuru
	 */
	public void setRARevisedRPNValue(Context context,DomainObject dObject,String strAttrName,String strAttrValue) throws Exception
	{
		String strRevisedSeverity = "",strRevisedDetection = "",strRevisedOccurrence="";
		int iSev=0,iDet=0,iOcc=0,iRPN = 0;
		StringList slRASelects = new StringList();
		slRASelects.add(SELECT_ATTRIBUTE_REVISED_OCCURRENCE);
		slRASelects.add(SELECT_ATTRIBUTE_REVISED_SEVERITY);
		slRASelects.add(SELECT_ATTRIBUTE_REVISED_DETECTION);
		try{
			
			
			Map mpRADetails = dObject.getInfo(context,slRASelects);
			if(mpRADetails!=null && !mpRADetails.isEmpty()){
				strRevisedSeverity  = (String)mpRADetails.get(SELECT_ATTRIBUTE_REVISED_SEVERITY);
				strRevisedDetection = (String)mpRADetails.get(SELECT_ATTRIBUTE_REVISED_DETECTION);
				strRevisedOccurrence = (String)mpRADetails.get(SELECT_ATTRIBUTE_REVISED_OCCURRENCE);
			}
			if(strAttrName.equals(ATTRIBUTE_REVISED_SEVERITY) && UIUtil.isNotNullAndNotEmpty(strRevisedDetection) &&  UIUtil.isNotNullAndNotEmpty(strRevisedOccurrence)){
				iSev = Integer.parseInt(strAttrValue);
				iDet = Integer.parseInt(strRevisedDetection);
				iOcc = Integer.parseInt(strRevisedOccurrence);
				iRPN = iSev * iDet * iOcc;
				dObject.setAttributeValue(context, ATTRIBUTE_REVISED_RPN,iRPN + DomainConstants.EMPTY_STRING);
						
			}else if(strAttrName.equals(ATTRIBUTE_REVISED_DETECTION) && UIUtil.isNotNullAndNotEmpty(strRevisedSeverity) &&  UIUtil.isNotNullAndNotEmpty(strRevisedOccurrence)){
				iSev = Integer.parseInt(strRevisedSeverity);
				iDet = Integer.parseInt(strAttrValue);
				iOcc = Integer.parseInt(strRevisedOccurrence);
				iRPN = iSev * iDet * iOcc;
				dObject.setAttributeValue(context, ATTRIBUTE_REVISED_RPN,iRPN + DomainConstants.EMPTY_STRING);
				
			}else if(strAttrName.equals(ATTRIBUTE_REVISED_OCCURRENCE) && UIUtil.isNotNullAndNotEmpty(strRevisedSeverity) &&  UIUtil.isNotNullAndNotEmpty(strRevisedDetection)){
				iSev = Integer.parseInt(strRevisedSeverity);
				iDet = Integer.parseInt(strRevisedDetection);
				iOcc = Integer.parseInt(strAttrValue);
				iRPN = iSev * iDet * iOcc;
				dObject.setAttributeValue(context, ATTRIBUTE_REVISED_RPN,iRPN + DomainConstants.EMPTY_STRING);
						
			} else {
				dObject.setAttributeValue(context, ATTRIBUTE_REVISED_RPN,DomainConstants.EMPTY_STRING);
				dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
			}
			
			//Setiing Class value
			if ((iSev == 9) || (iSev == 10)) {
				dObject.setAttributeValue(context, ATTRIBUTE_CLASS, "YC");
			} else if ((iSev == 5 || iSev == 6 || iSev == 7 || iSev == 8)&& (iOcc == 4 || iOcc == 5 || iOcc == 6 || iOcc == 7 || iOcc == 8 || iOcc == 9 || iOcc == 10)) {
				dObject.setAttributeValue(context, ATTRIBUTE_CLASS, "YS");
			} else {
				dObject.setAttributeValue(context, ATTRIBUTE_CLASS, DomainConstants.EMPTY_STRING);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	//Added for INC 16331 End
}

