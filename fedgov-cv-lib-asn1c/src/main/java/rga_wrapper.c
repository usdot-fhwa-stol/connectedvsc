/*
 * Copyright (C) 2025 LEIDOS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

#include <stdio.h>
#include <sys/types.h>
#include "gov_usdot_cv_rgaencoder_Encoder.h"
#include "MessageFrame.h"
#include <stdint.h>

JNIEXPORT jbyteArray JNICALL Java_gov_usdot_cv_rgaencoder_Encoder_encodeRGA(JNIEnv *env, jobject cls, jobject baseLayer, jobject geometryContainers)
{
	printf("\n ***Inside the rga_wrapper.c file Satish **** \n");
	uint8_t buffer[2302];
	size_t buffer_size = sizeof(buffer);
	asn_enc_rval_t ec;

	MessageFrame_t *message;

	message = calloc(1, sizeof(MessageFrame_t));
	if (!message)
	{
		return NULL;
	}

	// set default value of messageId of DSRCmsgID_roadGeometryAndAttributes
	message->messageId = 43;

	// set default Message Frame Value for RoadGeometryAndAttributes
	message->value.present = MessageFrame__value_PR_RoadGeometryAndAttributes;

	RGABaseLayer_t rgaBaseLayer;
	jclass baseLayerClass = (*env)->GetObjectClass(env, baseLayer);

	// ================== Data Set Format Version Info (Major and Minor Version) ==================
	RGADataSetFormatVersionInfo_t dataSetFmtVerInfo;

	jmethodID getMajorVersion = (*env)->GetMethodID(env, baseLayerClass, "getMajorVer", "()I");
	jint majorVersion = (*env)->CallIntMethod(env, baseLayer, getMajorVersion);

	jmethodID getMinorVersion = (*env)->GetMethodID(env, baseLayerClass, "getMinorVer", "()I");
	jint minorVersion = (*env)->CallIntMethod(env, baseLayer, getMinorVersion);

	dataSetFmtVerInfo.majorVersion = (long)majorVersion;
	dataSetFmtVerInfo.minorVersion = (long)minorVersion;

	rgaBaseLayer.dataSetFmtVerInfo = dataSetFmtVerInfo;

	// ================== Reference Point Info (Reference Point) ==================
	ReferencePointInfo_t refPointInfo;

	jmethodID getLocation = (*env)->GetMethodID(env, baseLayerClass, "getLocation", "()Lgov/usdot/cv/mapencoder/Position3D;");
	jobject locationObj = (*env)->CallObjectMethod(env, baseLayer, getLocation);
	Position3D_t location;
	jclass locationClass = (*env)->GetObjectClass(env, locationObj);

	jmethodID getLatitude = (*env)->GetMethodID(env, locationClass, "getLatitude", "()D");
	jmethodID getLongitude = (*env)->GetMethodID(env, locationClass, "getLongitude", "()D");

	jdouble latitude = (*env)->CallDoubleMethod(env, locationObj, getLatitude);
	jdouble longitude = (*env)->CallDoubleMethod(env, locationObj, getLongitude);

	location.lat = (Common_Latitude_t)((long)latitude);
	location.Long = (Common_Longitude_t)((long)longitude);

	// Check if elevation exists
	jmethodID isElevationExists = (*env)->GetMethodID(env, locationClass, "isElevationExists", "()Z");
	jboolean elevationExists = (*env)->CallBooleanMethod(env, locationObj, isElevationExists);

	if (elevationExists)
	{
		jmethodID getElevation = (*env)->GetMethodID(env, locationClass, "getElevation", "()F");
		jfloat elevation = (*env)->CallFloatMethod(env, locationObj, getElevation);

		Common_Elevation_t *dsrcElevation = calloc(1, sizeof(Common_Elevation_t));
		*dsrcElevation = (long)elevation;
		location.elevation = dsrcElevation;
	}
	else
	{
		location.elevation = NULL;
	}
	location.regional = NULL;
	
	refPointInfo.location = location;

	// ================== Reference Point Info (Time Of Calculation) ==================
	DDate_t timeOfCalculation;

	jmethodID getTimeOfCalc = (*env)->GetMethodID(env, baseLayerClass, "getTimeOfCalculation", "()Lgov/usdot/cv/rgaencoder/DDate;");
	jobject timeOfCalcObj = (*env)->CallObjectMethod(env, baseLayer, getTimeOfCalc);
	jclass timeOfCalculationClass = (*env)->GetObjectClass(env, timeOfCalcObj);

	jmethodID getYear = (*env)->GetMethodID(env, timeOfCalculationClass, "getYear", "()I");
	jmethodID getMonth = (*env)->GetMethodID(env, timeOfCalculationClass, "getMonth", "()I");
	jmethodID getDay = (*env)->GetMethodID(env, timeOfCalculationClass, "getDay", "()I");

	jint year = (*env)->CallIntMethod(env, timeOfCalcObj, getYear);
	jint month = (*env)->CallIntMethod(env, timeOfCalcObj, getMonth);
	jint day = (*env)->CallIntMethod(env, timeOfCalcObj, getDay);

	timeOfCalculation.year = (long)year;
	timeOfCalculation.month = (long)month;
	timeOfCalculation.day = (long)day;

	refPointInfo.timeOfCalculation = timeOfCalculation;

	rgaBaseLayer.refPointInfo = refPointInfo;

	// ================== Road Geometry Ref ID Info (relativeToRdAuthID) ==================
	RoadGeometryRefIDInfo_t rdGeoRefID;

	RoadAuthorityID_t *roadAuthorityID = calloc(1, sizeof(RoadAuthorityID_t));

	// Check if full RAID exists
	jmethodID isFullRdAuthIDExists = (*env)->GetMethodID(env, baseLayerClass, "isFullRdAuthIDExists", "()Z");
	jboolean fullRdAuthIDExists = (*env)->CallBooleanMethod(env, baseLayer, isFullRdAuthIDExists);
	
	// Check if relative RAID exists
	jmethodID isRelRdAuthIDExists = (*env)->GetMethodID(env, baseLayerClass, "isRelRdAuthIDExists", "()Z");
	jboolean relRdAuthIDExists = (*env)->CallBooleanMethod(env, baseLayer, isRelRdAuthIDExists);
	
	//TODO: RAID Type must be updated to OBJECT_IDENTIFIER_t.
	if (fullRdAuthIDExists)
	{
		// Get full RAID
		jmethodID getFullRdAuthID = (*env)->GetMethodID(env, baseLayerClass, "getFullRdAuthID", "()[I");
		jintArray fullRdAuthID = (*env)->CallObjectMethod(env, baseLayer, getFullRdAuthID);
		const uint32_t* fullRAID = (*env)->GetIntArrayElements(env, fullRdAuthID, NULL);

		size_t fullRAIDLen = (*env)->GetArrayLength(env, fullRdAuthID);

		OBJECT_IDENTIFIER_t *fullRAIDObjID = calloc(1, sizeof(OBJECT_IDENTIFIER_t));
		
		OBJECT_IDENTIFIER_set_arcs(fullRAIDObjID,
                               fullRAID, fullRAIDLen);

		roadAuthorityID->present = RoadAuthorityID_PR_fullRdAuthID;
		roadAuthorityID->choice.fullRdAuthID = *fullRAIDObjID;
		rdGeoRefID.rdAuthorityID = roadAuthorityID;
	} else if (relRdAuthIDExists) {
		// Get relative RAID
		jmethodID getRelRdAuthID = (*env)->GetMethodID(env, baseLayerClass, "getRelRdAuthID", "()[I");
		jintArray relRdAuthID = (*env)->CallObjectMethod(env, baseLayer, getRelRdAuthID);
		const uint32_t* relRAID = (*env)->GetIntArrayElements(env, relRdAuthID, NULL);
		
		size_t relRAIDLen  = (*env)->GetArrayLength(env, relRdAuthID);

		RELATIVE_OID_t *relRAIDObjID = calloc(1, sizeof(RELATIVE_OID_t));

		RELATIVE_OID_set_arcs(relRAIDObjID,
                               relRAID, relRAIDLen);

		// Set RAID pointer's PRESENT and CHOICE
		roadAuthorityID->present = RoadAuthorityID_PR_relRdAuthID;
		roadAuthorityID->choice.relRdAuthID = *relRAIDObjID;
		rdGeoRefID.rdAuthorityID = roadAuthorityID;
	} else {
		roadAuthorityID->present = RoadAuthorityID_PR_NOTHING;
		rdGeoRefID.rdAuthorityID = NULL;
	}
	
	rdGeoRefID.rdAuthorityID = roadAuthorityID;

	// ================== Road Geometry Ref ID Info (relativeToRdAuthID) ==================
	jmethodID getRelToRdAuthID = (*env)->GetMethodID(env, baseLayerClass, "getRelativeToRdAuthID", "()[I"); 
	jintArray relativeToRdAuthID = (*env)->CallObjectMethod(env, baseLayer, getRelToRdAuthID);
	const uint32_t *relToRdAuthID = (*env)->GetIntArrayElements(env, relativeToRdAuthID, NULL);

	size_t relToAuthIDLen = (*env)->GetArrayLength(env, relativeToRdAuthID);

	RELATIVE_OID_t *relativeOID = calloc(1, sizeof(RELATIVE_OID_t));

	RELATIVE_OID_set_arcs(relativeOID,
                               relToRdAuthID, relToAuthIDLen);

	MappedGeometryID_t mappedGeomID;
	mappedGeomID.present = MappedGeometryID_PR_relativeToRdAuthID;
	mappedGeomID.choice.relativeToRdAuthID = *relativeOID;
	
	rdGeoRefID.mappedGeomID = mappedGeomID;
	rgaBaseLayer.rdGeomRefID = rdGeoRefID;

	// ================== Data Set Content Identification (Content Version) ==================
	DataSetContentIdentification_t rgaContentIdentification;

	jmethodID getContentVer = (*env)->GetMethodID(env, baseLayerClass, "getContentVer", "()I");
	jint contentVer = (*env)->CallIntMethod(env, baseLayer, getContentVer);

	rgaContentIdentification.contentVer = (long)contentVer;

	// ================== Data Set Content Identification (Content Date Time) ==================
	DDateTime_t contentDateTime;

	jmethodID getContentDateTime = (*env)->GetMethodID(env, baseLayerClass, "getContentDateTime", "()Lgov/usdot/cv/rgaencoder/DDateTime;");
	jobject contentDateTimeObj = (*env)->CallObjectMethod(env, baseLayer, getContentDateTime);
	jclass contentDateTimeClass = (*env)->GetObjectClass(env, contentDateTimeObj);

	jmethodID getHour = (*env)->GetMethodID(env, contentDateTimeClass, "getHour", "()I");
	jmethodID getMinute = (*env)->GetMethodID(env, contentDateTimeClass, "getMinute", "()I");
	jmethodID getSecond = (*env)->GetMethodID(env, contentDateTimeClass, "getSecond", "()I");

	jint hour = (*env)->CallIntMethod(env, contentDateTimeObj, getHour);
	jint minute = (*env)->CallIntMethod(env, contentDateTimeObj, getMinute);
	jint second = (*env)->CallIntMethod(env, contentDateTimeObj, getSecond);

	DHour_t *ddtHour = calloc(1, sizeof(DHour_t));
	DMinute_t *ddtMinute = calloc(1, sizeof(DMinute_t));
	DSecond_t *ddtSecond = calloc(1, sizeof(DSecond_t));
	DYear_t *ddtYear = calloc(1,sizeof(DYear_t));
	DMonth_t *ddtMonth = calloc(1, sizeof(DMonth_t));
	DDay_t *ddtDay = calloc(1, sizeof(DDay_t));
	DOffset_t *ddtOffset = calloc(1, sizeof(DOffset_t));

	*ddtYear = (long)year;
	*ddtMonth = (long)month;
	*ddtDay = (long)day;
	*ddtHour = (long)hour;
	*ddtMinute = (long)minute;
	*ddtSecond = (long)second;
	*ddtOffset = (long)0;
	
	contentDateTime.year = ddtYear;
	contentDateTime.month = ddtMonth;
	contentDateTime.day = ddtDay;
	contentDateTime.hour = ddtHour;
	contentDateTime.minute = ddtMinute;
	contentDateTime.second = ddtSecond;
	contentDateTime.offset = ddtOffset;

	rgaContentIdentification.contentDateTime = contentDateTime;
	rgaBaseLayer.rgaContentIdentification = rgaContentIdentification;

	rgaBaseLayer.partitionInfo = NULL;

	// Set BaseLayer, RGADataSet, and MessageFrame
	RGADataSet_t rgaDataSet;
	memset(&rgaDataSet, 0, sizeof(RGADataSet_t));

	rgaDataSet.baseLayer = rgaBaseLayer;

	if (geometryContainers != NULL)
	{
		// Extracting and Setting Geometry Container
		jclass geometryContainersList = (*env)->GetObjectClass(env, geometryContainers);
		jmethodID geometryContainersListSizeMethod = (*env)->GetMethodID(env, geometryContainersList, "size", "()I");
		jmethodID geometryContainersListGetMethod = (*env)->GetMethodID(env, geometryContainersList, "get", "(I)Ljava/lang/Object;");

		jint geometryContainersListSize = (*env)->CallIntMethod(env, geometryContainers, geometryContainersListSizeMethod);

		if (geometryContainersListSize > 0)
		{
			rgaDataSet.geometryContainer = calloc(1, sizeof(*rgaDataSet.geometryContainer));
			printf("Encoding Geometry Containers \n");

			for (int gIndex = 0; gIndex < geometryContainersListSize; gIndex++)
			{
				RGAGeometryLayers_t *geometryLayer = calloc(1, sizeof(RGAGeometryLayers_t));
				jobject geometryContainerObject = (*env)->CallObjectMethod(env, geometryContainers, geometryContainersListGetMethod, gIndex);
				jclass geometryContainerClass = (*env)->GetObjectClass(env, geometryContainerObject);

				// Retrieve geometryContainer-ID
				jmethodID getGeometryContainerID = (*env)->GetMethodID(env, geometryContainerClass, "getGeometryContainerID", "()I");
				jint geometryContainerID = (*env)->CallIntMethod(env, geometryContainerObject, getGeometryContainerID);

				geometryLayer->geometryContainer_ID = geometryContainerID;

				// Populate the geometryContainer_Value based on the containerID
				switch (geometryContainerID)
				{
				case APPROACH_GEOMETRY_LAYER_ID: // ApproachGeometryLayer
					geometryLayer->geometryContainer_Value.present = RGAGeometryLayers__geometryContainer_Value_PR_ApproachGeometryLayer;

					// Retrieving the ApproachGeometryLayer object
					jmethodID getApproachGeometryLayerMethod = (*env)->GetMethodID(env, geometryContainerClass, "getApproachGeometryLayer", "()Lgov/usdot/cv/rgaencoder/ApproachGeometryLayer;");
					jobject approachGeometryLayerObj = (*env)->CallObjectMethod(env, geometryContainerObject, getApproachGeometryLayerMethod);

					// Populating ApproachGeometryLayer_t
					ApproachGeometryLayer_t *approachGeometryLayer = calloc(1, sizeof(ApproachGeometryLayer_t));

					// Populating the approachGeomApproachSet from the ApproachGeometryLayer object
					jclass approachGeometryLayerClass = (*env)->GetObjectClass(env, approachGeometryLayerObj);
					jmethodID getApproachGeomApproachSetMethod = (*env)->GetMethodID(env, approachGeometryLayerClass, "getApproachGeomApproachSet", "()Ljava/util/List;");
					jobject approachGeomApproachSetList = (*env)->CallObjectMethod(env, approachGeometryLayerObj, getApproachGeomApproachSetMethod);

					jclass approachGeomApproachSetClass = (*env)->GetObjectClass(env, approachGeomApproachSetList);
					jmethodID approachGeomApproachSetSizeMethod = (*env)->GetMethodID(env, approachGeomApproachSetClass, "size", "()I");
					jmethodID approachGeomApproachSetGetMethod = (*env)->GetMethodID(env, approachGeomApproachSetClass, "get", "(I)Ljava/lang/Object;");

					jint approachGeomApproachSetSize = (*env)->CallIntMethod(env, approachGeomApproachSetList, approachGeomApproachSetSizeMethod);

					for (jint aIndex = 0; aIndex < approachGeomApproachSetSize; aIndex++)
					{
						jobject individualApproachGeometryInfoObj = (*env)->CallObjectMethod(env, approachGeomApproachSetList, approachGeomApproachSetGetMethod, aIndex);
						jclass individualApproachGeometryInfoClass = (*env)->GetObjectClass(env, individualApproachGeometryInfoObj);

						jmethodID getApproachIDMethod = (*env)->GetMethodID(env, individualApproachGeometryInfoClass, "getApproachID", "()I");
						jint approachID = (*env)->CallIntMethod(env, individualApproachGeometryInfoObj, getApproachIDMethod);

						IndividualApproachGeometryInfo_t *approachInfo = calloc(1, sizeof(IndividualApproachGeometryInfo_t));

						approachInfo->approachID = approachID;

						// Adding to approachGeomApproachSet
						ASN_SEQUENCE_ADD(&approachGeometryLayer->approachGeomApproachSet.list, approachInfo);
					}

					geometryLayer->geometryContainer_Value.choice.ApproachGeometryLayer = *approachGeometryLayer;
					break;
				case MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID: // MotorVehicleLaneGeometryLayer
					geometryLayer->geometryContainer_Value.present = RGAGeometryLayers__geometryContainer_Value_PR_MotorVehicleLaneGeometryLayer;

					// Retrieving the MotorVehicleLaneGeometryLayer object
					jmethodID getMotorVehicleLaneGeometryLayerMethod = (*env)->GetMethodID(env, geometryContainerClass, "getMotorVehicleLaneGeometryLayer", "()Lgov/usdot/cv/rgaencoder/MotorVehicleLaneGeometryLayer;");
					jobject motorVehicleLaneGeometryLayerObj = (*env)->CallObjectMethod(env, geometryContainerObject, getMotorVehicleLaneGeometryLayerMethod);

					// Populating MotorVehicleLaneGeometryLayer_t
					MotorVehicleLaneGeometryLayer_t *motorVehicleLaneGeometryLayer = calloc(1, sizeof(MotorVehicleLaneGeometryLayer_t));

					// Populating the MotorVehicleLaneGeometryLayer laneGeomLaneSet from the MotorVehicleLaneGeometryLayer object
					jclass motorVehicleLaneGeometryLayerClass = (*env)->GetObjectClass(env, motorVehicleLaneGeometryLayerObj);
					jmethodID getLaneGeomLaneSetMethod = (*env)->GetMethodID(env, motorVehicleLaneGeometryLayerClass, "getLaneGeomLaneSet", "()Ljava/util/List;");
					jobject laneGeomLaneSetList = (*env)->CallObjectMethod(env, motorVehicleLaneGeometryLayerObj, getLaneGeomLaneSetMethod);

					jclass laneGeomLaneSetClass = (*env)->GetObjectClass(env, laneGeomLaneSetList);
					jmethodID laneGeomLaneSetSizeMethod = (*env)->GetMethodID(env, laneGeomLaneSetClass, "size", "()I");
					jmethodID laneGeomLaneSetGetMethod = (*env)->GetMethodID(env, laneGeomLaneSetClass, "get", "(I)Ljava/lang/Object;");

					jint laneGeomLaneSetSize = (*env)->CallIntMethod(env, laneGeomLaneSetList, laneGeomLaneSetSizeMethod);

					for (jint lIndex = 0; lIndex < laneGeomLaneSetSize; lIndex++)
					{
						jobject indvMtrVehLaneGeometryInfoObj = (*env)->CallObjectMethod(env, laneGeomLaneSetList, laneGeomLaneSetGetMethod, lIndex);
						jclass indvMtrVehLaneGeometryInfoClass = (*env)->GetObjectClass(env, indvMtrVehLaneGeometryInfoObj);

						jmethodID getLaneIDMethod = (*env)->GetMethodID(env, indvMtrVehLaneGeometryInfoClass, "getLaneID", "()I");
						jint laneID = (*env)->CallIntMethod(env, indvMtrVehLaneGeometryInfoObj, getLaneIDMethod);

						IndvMtrVehLaneGeometryInfo_t *indvMtrVehLaneGeometryInfo = calloc(1, sizeof(IndvMtrVehLaneGeometryInfo_t));
						indvMtrVehLaneGeometryInfo->laneID = laneID;

						jmethodID getLaneConstructorTypeMethod = (*env)->GetMethodID(env, indvMtrVehLaneGeometryInfoClass, "getLaneConstructorType", "()Lgov/usdot/cv/rgaencoder/LaneConstructorType;");
						jobject laneConstructorTypeObj = (*env)->CallObjectMethod(env, indvMtrVehLaneGeometryInfoObj, getLaneConstructorTypeMethod);

						populateLaneConstructorType(env, laneConstructorTypeObj, &(indvMtrVehLaneGeometryInfo->laneConstructorType));

						ASN_SEQUENCE_ADD(&motorVehicleLaneGeometryLayer->laneGeomLaneSet.list, indvMtrVehLaneGeometryInfo);
					}

					geometryLayer->geometryContainer_Value.choice.MotorVehicleLaneGeometryLayer = *motorVehicleLaneGeometryLayer;
					break;

				case BICYCLE_LANE_GEOMETRY_LAYER_ID:
					// Handle BicycleLaneGeometryLayer
					geometryLayer->geometryContainer_Value.present = RGAGeometryLayers__geometryContainer_Value_PR_BicycleLaneGeometryLayer;

					// Retrieving the BicycleLaneGeometryLayer object
					jmethodID getBicycleLaneGeometryLayerMethod = (*env)->GetMethodID(env, geometryContainerClass, "getBicycleLaneGeometryLayer", "()Lgov/usdot/cv/rgaencoder/BicycleLaneGeometryLayer;");
					jobject bicycleLaneGeometryLayerObj = (*env)->CallObjectMethod(env, geometryContainerObject, getBicycleLaneGeometryLayerMethod);

					// Populating BicycleLaneGeometryLayer_t
					BicycleLaneGeometryLayer_t *bicycleLaneGeometryLayer = calloc(1, sizeof(BicycleLaneGeometryLayer_t));

					// Retrieve and populate BicycleLaneGeometryLayer laneGeomLaneSet
					jclass bicycleLaneGeometryLayerClass = (*env)->GetObjectClass(env, bicycleLaneGeometryLayerObj);
					jmethodID getBicycleLaneGeomLaneSetMethod = (*env)->GetMethodID(env, bicycleLaneGeometryLayerClass, "getLaneGeomLaneSet", "()Ljava/util/List;");
					jobject bicycleLaneGeomLaneSetList = (*env)->CallObjectMethod(env, bicycleLaneGeometryLayerObj, getBicycleLaneGeomLaneSetMethod);

					jclass bicycleLaneGeomLaneSetClass = (*env)->GetObjectClass(env, bicycleLaneGeomLaneSetList);
					jmethodID bicycleLaneGeomLaneSetSizeMethod = (*env)->GetMethodID(env, bicycleLaneGeomLaneSetClass, "size", "()I");
					jmethodID bicycleLaneGeomLaneSetGetMethod = (*env)->GetMethodID(env, bicycleLaneGeomLaneSetClass, "get", "(I)Ljava/lang/Object;");

					jint bicycleLaneGeomLaneSetSize = (*env)->CallIntMethod(env, bicycleLaneGeomLaneSetList, bicycleLaneGeomLaneSetSizeMethod);

					for (jint bIndex = 0; bIndex < bicycleLaneGeomLaneSetSize; bIndex++)
					{
						jobject indvBikeLaneGeometryInfoObj = (*env)->CallObjectMethod(env, bicycleLaneGeomLaneSetList, bicycleLaneGeomLaneSetGetMethod, bIndex);
						jclass indvBikeLaneGeometryInfoClass = (*env)->GetObjectClass(env, indvBikeLaneGeometryInfoObj);

						jmethodID getBikeLaneIDMethod = (*env)->GetMethodID(env, indvBikeLaneGeometryInfoClass, "getLaneID", "()I");
						jint bikeLaneID = (*env)->CallIntMethod(env, indvBikeLaneGeometryInfoObj, getBikeLaneIDMethod);

						IndvBikeLaneGeometryInfo_t *indvBikeLaneGeometryInfo = calloc(1, sizeof(IndvBikeLaneGeometryInfo_t));
						indvBikeLaneGeometryInfo->laneID = bikeLaneID;

						// Retrieve and set laneConstructorType
						jmethodID getBikeLaneConstructorTypeMethod = (*env)->GetMethodID(env, indvBikeLaneGeometryInfoClass, "getLaneConstructorType", "()Lgov/usdot/cv/rgaencoder/LaneConstructorType;");
						jobject bikeLaneConstructorTypeObj = (*env)->CallObjectMethod(env, indvBikeLaneGeometryInfoObj, getBikeLaneConstructorTypeMethod);

						populateLaneConstructorType(env, bikeLaneConstructorTypeObj, &(indvBikeLaneGeometryInfo->laneConstructorType));

						// jmethodID getTimeRestrictionsMethod = (*env)->GetMethodID(env, indvBikeLaneGeometryInfoClass, "getTimeRestrictions", "()Lgov/usdot/cv/rgaencoder/RGATimeRestrictions;");
						// jobject timeRestrictionsObj = (*env)->CallObjectMethod(env, indvBikeLaneGeometryInfoObj, getTimeRestrictionsMethod);

						// populateTimeRestrictions(env, timeRestrictionsObj, &(indvBikeLaneGeometryInfo->timeRestrictions));

						ASN_SEQUENCE_ADD(&bicycleLaneGeometryLayer->laneGeomLaneSet.list, indvBikeLaneGeometryInfo);
					}
					geometryLayer->geometryContainer_Value.choice.BicycleLaneGeometryLayer = *bicycleLaneGeometryLayer;

					break;

				case CROSSWALK_LANE_GEOMETRY_LAYER_ID:
					// Handle CrosswalkLaneGeometryLayer
					geometryLayer->geometryContainer_Value.present = RGAGeometryLayers__geometryContainer_Value_PR_CrosswalkLaneGeometryLayer;

					// Retrieving the CrosswalkLaneGeometryLayer object
					jmethodID getCrosswalkLaneGeometryLayerMethod = (*env)->GetMethodID(env, geometryContainerClass, "getCrosswalkLaneGeometryLayer", "()Lgov/usdot/cv/rgaencoder/CrosswalkLaneGeometryLayer;");
					jobject crosswalkLaneGeometryLayerObj = (*env)->CallObjectMethod(env, geometryContainerObject, getCrosswalkLaneGeometryLayerMethod);

					// Populating CrosswalkLaneGeometryLayer_t
					CrosswalkLaneGeometryLayer_t *crosswalkLaneGeometryLayer = calloc(1, sizeof(CrosswalkLaneGeometryLayer_t));

					// Retrieve and populate CrosswalkLaneGeometryLayer laneGeomLaneSet
					jclass crosswalkLaneGeometryLayerClass = (*env)->GetObjectClass(env, crosswalkLaneGeometryLayerObj);
					jmethodID getCrosswalkLaneGeomLaneSetMethod = (*env)->GetMethodID(env, crosswalkLaneGeometryLayerClass, "getLaneGeomLaneSet", "()Ljava/util/List;");
					jobject crossWalkLaneGeomLaneSetList = (*env)->CallObjectMethod(env, crosswalkLaneGeometryLayerObj, getCrosswalkLaneGeomLaneSetMethod);

					jclass crosswalkLaneGeomLaneSetClass = (*env)->GetObjectClass(env, crossWalkLaneGeomLaneSetList);
					jmethodID crosswalkLaneGeomLaneSetSizeMethod = (*env)->GetMethodID(env, crosswalkLaneGeomLaneSetClass, "size", "()I");
					jmethodID crosswalkLaneGeomLaneSetGetMethod = (*env)->GetMethodID(env, crosswalkLaneGeomLaneSetClass, "get", "(I)Ljava/lang/Object;");

					jint crosswalkLaneGeomLaneSetSize = (*env)->CallIntMethod(env, crossWalkLaneGeomLaneSetList, crosswalkLaneGeomLaneSetSizeMethod);

					for (jint cIndex = 0; cIndex < crosswalkLaneGeomLaneSetSize; cIndex++)
					{
						jobject indvCrosswalkLaneGeometryInfoObj = (*env)->CallObjectMethod(env, crossWalkLaneGeomLaneSetList, crosswalkLaneGeomLaneSetGetMethod, cIndex);
						jclass indvCrosswalkLaneGeometryInfoClass = (*env)->GetObjectClass(env, indvCrosswalkLaneGeometryInfoObj);

						jmethodID getCrossWalkLaneIDMethod = (*env)->GetMethodID(env, indvCrosswalkLaneGeometryInfoClass, "getLaneID", "()I");
						jint crosswalkLaneID = (*env)->CallIntMethod(env, indvCrosswalkLaneGeometryInfoObj, getCrossWalkLaneIDMethod);

						IndvCrosswalkLaneGeometryInfo_t *indvCrosswalkLaneGeometryInfo = calloc(1, sizeof(IndvCrosswalkLaneGeometryInfo_t));
						indvCrosswalkLaneGeometryInfo->laneID = crosswalkLaneID;

						// Retrieve and set laneConstructorType
						jmethodID getCrosswalkLaneConstructorTypeMethod = (*env)->GetMethodID(env, indvCrosswalkLaneGeometryInfoClass, "getLaneConstructorType", "()Lgov/usdot/cv/rgaencoder/LaneConstructorType;");
						jobject crosswalkLaneConstructorTypeObj = (*env)->CallObjectMethod(env, indvCrosswalkLaneGeometryInfoObj, getCrosswalkLaneConstructorTypeMethod);

						populateLaneConstructorType(env, crosswalkLaneConstructorTypeObj, &indvCrosswalkLaneGeometryInfo->laneConstructorType);

						// jmethodID getTimeRestrictionsMethod = (*env)->GetMethodID(env, indvCrosswalkLaneGeometryInfoClass, "getTimeRestrictions", "()Lgov/usdot/cv/rgaencoder/RGATimeRestrictions;");
						// jobject timeRestrictionsObj = (*env)->CallObjectMethod(env, indvCrosswalkLaneGeometryInfoObj, getTimeRestrictionsMethod);

						// populateTimeRestrictions(env, timeRestrictionsObj, &(indvCrosswalkLaneGeometryInfo->timeRestrictions));

						ASN_SEQUENCE_ADD(&crosswalkLaneGeometryLayer->laneGeomLaneSet.list, indvCrosswalkLaneGeometryInfo);
					}
					geometryLayer->geometryContainer_Value.choice.CrosswalkLaneGeometryLayer = *crosswalkLaneGeometryLayer;
					break;

				default:
					// Handle unknown ID
					geometryLayer->geometryContainer_Value.present = RGAGeometryLayers__geometryContainer_Value_PR_NOTHING;
					break;
				}

				ASN_SEQUENCE_ADD(&rgaDataSet.geometryContainer->list, geometryLayer);
			}
		}
	}
	rgaDataSet.movementsContainer = NULL;
	rgaDataSet.wayUseContainer = NULL;
	rgaDataSet.signalControlSupportContainer = NULL;

	RGADataSet_t *rgaDataSetList = calloc(1, sizeof(RGADataSet_t));
	RoadGeometryAndAttributes_t *roadGeomAndAttr = calloc(1, sizeof(RoadGeometryAndAttributes_t));

	if (rgaDataSetList)
	{
		*rgaDataSetList = rgaDataSet;
		ASN_SEQUENCE_ADD(&roadGeomAndAttr->dataSetSet.list, rgaDataSetList);
	}

	message->value.choice.RoadGeometryAndAttributes = *roadGeomAndAttr;

	ec = uper_encode_to_buffer(&asn_DEF_MessageFrame, 0, message, buffer, buffer_size);
	if (ec.encoded == -1)
	{
		printf("Cause of failure %s \n", ec.failed_type->name);
		printf("Unsuccessful!\n");
		return NULL;
	}
	printf("Successful!\n");

	jsize length = ec.encoded / 8;
	jbyteArray outJNIArray = (*env)->NewByteArray(env, length);
	if (outJNIArray == NULL)
	{
		return NULL;
	}

	(*env)->SetByteArrayRegion(env, outJNIArray, 0, length, buffer);

	free(message);

	// Get a pointer to the array elements
	jbyte *elements = (*env)->GetByteArrayElements(env, outJNIArray, NULL);

	if (elements == NULL)
	{
		return; // Error handling, if necessary
	}

	// Loop through and print each element
	for (jsize i = 0; i < length; i++)
	{
		// printf("Element %d: %d\n", i, elements[i]);
		printf("%d, ", elements[i]);
	}
	(*env)->ReleaseByteArrayElements(env, outJNIArray, elements, JNI_ABORT);

	return outJNIArray;
}

void populateLaneConstructorType(JNIEnv *env, jobject laneConstructorTypeObj, LaneConstructorType_t *laneConstructorType)
{
	jclass laneConstructorTypeClass = (*env)->GetObjectClass(env, laneConstructorTypeObj);

	// Get the choice type for LaneConstructorType
	jmethodID getLaneConstructorTypeChoiceMethod = (*env)->GetMethodID(env, laneConstructorTypeClass, "getChoice", "()I");
	jint laneConstructorTypeChoice = (*env)->CallIntMethod(env, laneConstructorTypeObj, getLaneConstructorTypeChoiceMethod);

	// Get the LaneConstructor Type
	if (laneConstructorTypeChoice == PHYSICAL_NODE)
	{
		// Populate physicalXYZNodeInfo
		jmethodID getPhysicalXYZNodeInfoMethod = (*env)->GetMethodID(env, laneConstructorTypeClass, "getPhysicalXYZNodeInfo", "()Lgov/usdot/cv/rgaencoder/PhysicalXYZNodeInfo;");
		jobject physicalXYZNodeInfoObj = (*env)->CallObjectMethod(env, laneConstructorTypeObj, getPhysicalXYZNodeInfoMethod);
		jclass physicalXYZNodeInfoClass = (*env)->GetObjectClass(env, physicalXYZNodeInfoObj);

		jmethodID getNodeXYZGeometryNodeSetMethod = (*env)->GetMethodID(env, physicalXYZNodeInfoClass, "getNodeXYZGeometryNodeSet", "()Ljava/util/List;");
		jobject nodeXYZGeometryNodeSetList = (*env)->CallObjectMethod(env, physicalXYZNodeInfoObj, getNodeXYZGeometryNodeSetMethod);

		jclass nodeXYZGeometryNodeSetClass = (*env)->GetObjectClass(env, nodeXYZGeometryNodeSetList);
		jmethodID nodeXYZGeometryNodeSetSizeMethod = (*env)->GetMethodID(env, nodeXYZGeometryNodeSetClass, "size", "()I");
		jmethodID nodeXYZGeometryNodeSetGetMethod = (*env)->GetMethodID(env, nodeXYZGeometryNodeSetClass, "get", "(I)Ljava/lang/Object;");

		jint nodeXYZGeometryNodeSetSize = (*env)->CallIntMethod(env, nodeXYZGeometryNodeSetList, nodeXYZGeometryNodeSetSizeMethod);		

		PhysicalXYZNodeInfo_t *physicalXYZNodeInfo = calloc(1, sizeof(PhysicalXYZNodeInfo_t));

		for (jint nIndex = 0; nIndex < nodeXYZGeometryNodeSetSize; nIndex++)
		{
			jobject individualXYZNodeGeometryInfoObj = (*env)->CallObjectMethod(env, nodeXYZGeometryNodeSetList, nodeXYZGeometryNodeSetGetMethod, nIndex);
			jclass individualXYZNodeGeometryInfoClass = (*env)->GetObjectClass(env, individualXYZNodeGeometryInfoObj);

			// Retrieve NodeXYZOffsetInfo
			jmethodID getNodeXYZOffsetInfoMethod = (*env)->GetMethodID(env, individualXYZNodeGeometryInfoClass, "getNodeXYZOffsetInfo", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetInfo;");
			jobject nodeXYZOffsetInfoObj = (*env)->CallObjectMethod(env, individualXYZNodeGeometryInfoObj, getNodeXYZOffsetInfoMethod);
			jclass nodeXYZOffsetInfoClass = (*env)->GetObjectClass(env, nodeXYZOffsetInfoObj);

			// Create a new NodeXYZOffsetInfo_t for the current node
			NodeXYZOffsetInfo_t *nodeXYZOffset = calloc(1, sizeof(NodeXYZOffsetInfo_t));

			// Populate nodeXOffsetValue
			jmethodID getNodeXOffsetMethod = (*env)->GetMethodID(env, nodeXYZOffsetInfoClass, "getNodeXOffsetValue", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetValue;");
			jobject nodeXOffsetValueObj = (*env)->CallObjectMethod(env, nodeXYZOffsetInfoObj, getNodeXOffsetMethod);
			populateNodeXYZOffsetValue(env, nodeXOffsetValueObj, &nodeXYZOffset->nodeXOffsetValue);

			// Populate nodeYOffsetValue
			jmethodID getNodeYOffsetMethod = (*env)->GetMethodID(env, nodeXYZOffsetInfoClass, "getNodeYOffsetValue", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetValue;");
			jobject nodeYOffsetValueObj = (*env)->CallObjectMethod(env, nodeXYZOffsetInfoObj, getNodeYOffsetMethod);
			populateNodeXYZOffsetValue(env, nodeYOffsetValueObj, &nodeXYZOffset->nodeYOffsetValue);

			// Populate nodeZOffsetValue
			jmethodID getNodeZOffsetMethod = (*env)->GetMethodID(env, nodeXYZOffsetInfoClass, "getNodeZOffsetValue", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetValue;");
			jobject nodeZOffsetValueObj = (*env)->CallObjectMethod(env, nodeXYZOffsetInfoObj, getNodeZOffsetMethod);
			populateNodeXYZOffsetValue(env, nodeZOffsetValueObj, &nodeXYZOffset->nodeZOffsetValue);

			// Populate WayPlanarGeometryInfo
			jmethodID getNodeLocPlanarGeometryInfoMethod = (*env)->GetMethodID(env, individualXYZNodeGeometryInfoClass, "getNodeLocPlanarGeomInfo", "()Lgov/usdot/cv/rgaencoder/WayPlanarGeometryInfo;");
			jobject nodeLocPlanarGeometryInfoObj = (*env)->CallObjectMethod(env, individualXYZNodeGeometryInfoObj, getNodeLocPlanarGeometryInfoMethod);
			jclass nodeLocPlanarGeometryInfoClass = (*env)->GetObjectClass(env, nodeLocPlanarGeometryInfoObj);

			// Populate wayWidth 
			jmethodID getWayWidthMethod = (*env)->GetMethodID(env, nodeLocPlanarGeometryInfoClass, "getWayWidth", "()D");
			jobject wayWidthObj = (*env)->CallObjectMethod(env, nodeLocPlanarGeometryInfoObj, getWayWidthMethod);
			jclass wayWidthClass = (*env)->GetObjectClass(env, wayWidthObj);

			jmethodID getChoice = (*env)->GetMethodID(env, wayWidthClass, "getChoice", "()B");
			jmethodID getFullWidth = (*env)->GetMethodID(env, wayWidthClass, "getFullWidth", "()I");
			jmethodID getDeltaWidth = (*env)->GetMethodID(env, wayWidthClass, "getDeltaWidth", "()I");

			jbyte choice = (*env)->CallByteMethod(env, wayWidthObj, getChoice);
			jint fullWidth = (*env)->CallIntMethod(env, wayWidthObj, getFullWidth);
			jint deltaWidth = (*env)->CallIntMethod(env, wayWidthObj, getDeltaWidth);

			printf("Getting waywidth, physical");

			WayWidth_t *wayWidth = calloc(1, sizeof(WayWidth_t));

			switch (choice) {
				case 0: // FULL_WIDTH in Java
					wayWidth->present = WayWidth_PR_fullWidth;
					wayWidth->choice.fullWidth = (long)fullWidth; // Cast jint to long
					break;
				case 1: // DELTA_WIDTH in Java
					wayWidth->present = WayWidth_PR_deltaWidth;
					wayWidth->choice.deltaWidth = (long)deltaWidth; // Cast jint to long
					break;
				default: // Uninitialized or invalid choice (-1 or other)
					wayWidth->present = WayWidth_PR_NOTHING;
					break;
			}

			printf("Got waywidth, physical");

			ASN_SEQUENCE_ADD(&physicalXYZNodeInfo->nodeXYZGeometryNodeSet.list, nodeXYZOffset);
		}

		laneConstructorType->present = LaneConstructorType_PR_physicalXYZNodeInfo;
		laneConstructorType->choice.physicalXYZNodeInfo = *physicalXYZNodeInfo;

		// jmethodID getReferencePointInfoMethod = (*env)->GetMethodID(env, physicalXYZNodeInfoClass, "getReferencePointInfo", "()Lgov/usdot/cv/rgaencoder/ReferencePointInfo;");
		// jobject referencePointInfoObj = (*env)->CallObjectMethod(env, physicalXYZNodeInfoObj, getReferencePointInfoMethod);
		// jclass referencePointInfoClass = (*env)->GetObjectClass(env, referencePointInfoObj);
		// populateReferencePointInfo(env, referencePointInfoObj, referencePointInfoClass);
		
	}
	else if (laneConstructorTypeChoice == COMPUTED_NODE)
	{
		// Populate computedXYZNodeInfo
		jmethodID getComputedXYZNodeInfoMethod = (*env)->GetMethodID(env, laneConstructorTypeClass, "getComputedXYZNodeInfo", "()Lgov/usdot/cv/rgaencoder/ComputedXYZNodeInfo;");
		jobject computedXYZNodeInfoObj = (*env)->CallObjectMethod(env, laneConstructorTypeObj, getComputedXYZNodeInfoMethod);
		jclass computedXYZNodeInfoClass = (*env)->GetObjectClass(env, computedXYZNodeInfoObj);

		// Get refLaneID
		jmethodID getRefLaneIDMethod = (*env)->GetMethodID(env, computedXYZNodeInfoClass, "getRefLaneID", "()I");
		jint refLaneID = (*env)->CallIntMethod(env, computedXYZNodeInfoObj, getRefLaneIDMethod);

		// Allocate memory for ComputedXYZNodeInfo_t
		ComputedXYZNodeInfo_t *computedXYZNodeInfo = calloc(1, sizeof(ComputedXYZNodeInfo_t));
		computedXYZNodeInfo->refLaneID = refLaneID;

		// Get laneCenterLineXYZOffset
		jmethodID getLaneCenterLineXYZOffsetMethod = (*env)->GetMethodID(env, computedXYZNodeInfoClass, "getLaneCenterLineXYZOffset", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetInfo;");
		jobject laneCenterLineXYZOffsetObj = (*env)->CallObjectMethod(env, computedXYZNodeInfoObj, getLaneCenterLineXYZOffsetMethod);
		jclass laneCenterLineXYZOffsetClass = (*env)->GetObjectClass(env, laneCenterLineXYZOffsetObj);

		// Populate nodeXOffsetValue
		jmethodID getNodeXOffsetMethod = (*env)->GetMethodID(env, laneCenterLineXYZOffsetClass, "getNodeXOffsetValue", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetValue;");
		jobject nodeXOffsetValueObj = (*env)->CallObjectMethod(env, laneCenterLineXYZOffsetObj, getNodeXOffsetMethod);
		populateNodeXYZOffsetValue(env, nodeXOffsetValueObj, &computedXYZNodeInfo->laneCenterLineXYZOffset.nodeXOffsetValue);

		// Populate nodeYOffsetValue
		jmethodID getNodeYOffsetMethod = (*env)->GetMethodID(env, laneCenterLineXYZOffsetClass, "getNodeYOffsetValue", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetValue;");
		jobject nodeYOffsetValueObj = (*env)->CallObjectMethod(env, laneCenterLineXYZOffsetObj, getNodeYOffsetMethod);
		populateNodeXYZOffsetValue(env, nodeYOffsetValueObj, &computedXYZNodeInfo->laneCenterLineXYZOffset.nodeYOffsetValue);

		// Populate nodeZOffsetValue
		jmethodID getNodeZOffsetMethod = (*env)->GetMethodID(env, laneCenterLineXYZOffsetClass, "getNodeZOffsetValue", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetValue;");
		jobject nodeZOffsetValueObj = (*env)->CallObjectMethod(env, laneCenterLineXYZOffsetObj, getNodeZOffsetMethod);
		populateNodeXYZOffsetValue(env, nodeZOffsetValueObj, &computedXYZNodeInfo->laneCenterLineXYZOffset.nodeZOffsetValue);

			// Populate WayPlanarGeometryInfo
			jmethodID getNodeLocPlanarGeometryInfoMethod = (*env)->GetMethodID(env, computedXYZNodeInfoClass, "getNodeLocPlanarGeomInfo", "()Lgov/usdot/cv/rgaencoder/WayPlanarGeometryInfo;");
			jobject nodeLocPlanarGeometryInfoObj = (*env)->CallObjectMethod(env, computedXYZNodeInfoObj, getNodeLocPlanarGeometryInfoMethod);
			jclass nodeLocPlanarGeometryInfoClass = (*env)->GetObjectClass(env, nodeLocPlanarGeometryInfoObj);

			// Populate wayWidth 
			jmethodID getWayWidthMethod = (*env)->GetMethodID(env, nodeLocPlanarGeometryInfoClass, "getWayWidth", "()D");
			jobject wayWidthObj = (*env)->CallObjectMethod(env, nodeLocPlanarGeometryInfoObj, getWayWidthMethod);
			jclass wayWidthClass = (*env)->GetObjectClass(env, wayWidthObj);

			jmethodID getChoice = (*env)->GetMethodID(env, wayWidthClass, "getChoice", "()B");
			jmethodID getFullWidth = (*env)->GetMethodID(env, wayWidthClass, "getFullWidth", "()I");
			jmethodID getDeltaWidth = (*env)->GetMethodID(env, wayWidthClass, "getDeltaWidth", "()I");

			jbyte choice = (*env)->CallByteMethod(env, wayWidthObj, getChoice);
			jint fullWidth = (*env)->CallIntMethod(env, wayWidthObj, getFullWidth);
			jint deltaWidth = (*env)->CallIntMethod(env, wayWidthObj, getDeltaWidth);

			printf("Getting waywidth, physical");

			WayWidth_t *wayWidth = calloc(1, sizeof(WayWidth_t));

			switch (choice) {
				case 0: // FULL_WIDTH in Java
					wayWidth->present = WayWidth_PR_fullWidth;
					wayWidth->choice.fullWidth = (long)fullWidth; // Cast jint to long
					break;
				case 1: // DELTA_WIDTH in Java
					wayWidth->present = WayWidth_PR_deltaWidth;
					wayWidth->choice.deltaWidth = (long)deltaWidth; // Cast jint to long
					break;
				default: // Uninitialized or invalid choice (-1 or other)
					wayWidth->present = WayWidth_PR_NOTHING;
					break;
			}

			printf("Got waywidth, physical");


		laneConstructorType->present = LaneConstructorType_PR_computedXYZNodeInfo;
		laneConstructorType->choice.computedXYZNodeInfo = *computedXYZNodeInfo;
	}
	else if (laneConstructorTypeChoice == DUPLICATE_NODE)
	{
		// Populate duplicateXYZNodeInfo
		jmethodID getDuplicateXYZNodeInfoMethod = (*env)->GetMethodID(env, laneConstructorTypeClass, "getDuplicateXYZNodeInfo", "()Lgov/usdot/cv/rgaencoder/DuplicateXYZNodeInfo;");
		jobject duplicateXYZNodeInfoObj = (*env)->CallObjectMethod(env, laneConstructorTypeObj, getDuplicateXYZNodeInfoMethod);
		jclass duplicateXYZNodeInfoClass = (*env)->GetObjectClass(env, duplicateXYZNodeInfoObj);

		// Get refLaneID
		jmethodID getRefLaneIDMethod = (*env)->GetMethodID(env, duplicateXYZNodeInfoClass, "getRefLaneID", "()I");
		jint refLaneID = (*env)->CallIntMethod(env, duplicateXYZNodeInfoObj, getRefLaneIDMethod);

		DuplicateXYZNodeInfo_t *duplicateXYZNodeInfo = calloc(1, sizeof(DuplicateXYZNodeInfo_t));
		duplicateXYZNodeInfo->refLaneID = refLaneID;

		laneConstructorType->present = LaneConstructorType_PR_duplicateXYZNodeInfo;
		laneConstructorType->choice.duplicateXYZNodeInfo = *duplicateXYZNodeInfo;
	}
}

// Function to handle offset values
void populateNodeXYZOffsetValue(JNIEnv *env, jobject offsetValueObj, NodeXYZOffsetValue_t *offsetValue)
{
	jclass offsetValueClass = (*env)->GetObjectClass(env, offsetValueObj);

	jmethodID getChoiceMethod = (*env)->GetMethodID(env, offsetValueClass, "getChoice", "()I");
	jint choice = (*env)->CallIntMethod(env, offsetValueObj, getChoiceMethod);

	if (choice == OFFSET_B10)
	{
		jmethodID getOffsetB10Method = (*env)->GetMethodID(env, offsetValueClass, "getOffsetB10", "()J");
		jlong offsetB10 = (*env)->CallLongMethod(env, offsetValueObj, getOffsetB10Method);

		offsetValue->present = NodeXYZOffsetValue_PR_plusMinus5pt11m;
		offsetValue->choice.plusMinus5pt11m = (Offset_B10_t)offsetB10;
	}
	else if (choice == OFFSET_B11)
	{
		jmethodID getOffsetB11Method = (*env)->GetMethodID(env, offsetValueClass, "getOffsetB11", "()J");
		jlong offsetB11 = (*env)->CallLongMethod(env, offsetValueObj, getOffsetB11Method);

		offsetValue->present = NodeXYZOffsetValue_PR_plusMinus10pt23m;
		offsetValue->choice.plusMinus10pt23m = (Offset_B11_t)offsetB11;
	}
	else if (choice == OFFSET_B12)
	{
		jmethodID getOffsetB12Method = (*env)->GetMethodID(env, offsetValueClass, "getOffsetB12", "()J");
		jlong offsetB12 = (*env)->CallLongMethod(env, offsetValueObj, getOffsetB12Method);

		offsetValue->present = NodeXYZOffsetValue_PR_plusMinus20pt47m;
		offsetValue->choice.plusMinus20pt47m = (Offset_B12_t)offsetB12;
	}
	else if (choice == OFFSET_B13)
	{
		jmethodID getOffsetB13Method = (*env)->GetMethodID(env, offsetValueClass, "getOffsetB13", "()J");
		jlong offsetB13 = (*env)->CallLongMethod(env, offsetValueObj, getOffsetB13Method);

		offsetValue->present = NodeXYZOffsetValue_PR_plusMinus40pt95m;
		offsetValue->choice.plusMinus40pt95m = (Offset_B13_t)offsetB13;
	}
	else if (choice == OFFSET_B14)
	{
		jmethodID getOffsetB14Method = (*env)->GetMethodID(env, offsetValueClass, "getOffsetB14", "()J");
		jlong offsetB14 = (*env)->CallLongMethod(env, offsetValueObj, getOffsetB14Method);

		offsetValue->present = NodeXYZOffsetValue_PR_plusMinus81pt91m;
		offsetValue->choice.plusMinus81pt91m = (Offset_B14_t)offsetB14;
	}
	else if (choice == OFFSET_B16)
	{
		jmethodID getOffsetB16Method = (*env)->GetMethodID(env, offsetValueClass, "getOffsetB16", "()J");
		jlong offsetB16 = (*env)->CallLongMethod(env, offsetValueObj, getOffsetB16Method);

		offsetValue->present = NodeXYZOffsetValue_PR_plusMinus327pt67m;
		offsetValue->choice.plusMinus327pt67m = (Offset_B16_t)offsetB16;
	}
	else
	{
		offsetValue->present = NodeXYZOffsetValue_PR_NOTHING;
	}
}

void populateReferencePoint(JNIEnv *env, jobject referencePointObj, ReferencePointInfo_t *referencePoint) {
    jclass referencePointClass = (*env)->GetObjectClass(env, referencePointObj);
    jmethodID getReferencePointMethod = (*env)->GetMethodID(env, referencePointClass, "getReferencePoint", "()Lgov/usdot/cv/rgaencoder/NodeXYZOffsetInfo;");
    jobject referencePointInfoObj = (*env)->CallObjectMethod(env, referencePointObj, getReferencePointMethod);

    jmethodID getLocation = (*env)->GetMethodID(env, referencePointInfoObj, "getLocation", "()Lgov/usdot/cv/rgaencoder/ReferencePointInfo;");
    jobject locationObj = (*env)->CallObjectMethod(env, referencePointInfoObj, getLocation);
	jclass locationClass = (*env)->GetObjectClass(env, locationObj);

    jmethodID getTimeOfCalculation = (*env)->GetMethodID(env, referencePointInfoObj, "getTimeOfCalculation", "()Lgov/usdot/cv/rgaencoder/ReferencePointInfo;");
    jobject timeOfCalculationObj = (*env)->CallObjectMethod(env, referencePointInfoObj, getTimeOfCalculation);
	jclass timeOfCalculationClass = (*env)->GetObjectClass(env, timeOfCalculationObj);

	// ================== Reference Point Info (Reference Point) ==================

	Position3D_t location;

	jmethodID getLatitude = (*env)->GetMethodID(env, locationClass, "getLatitude", "()D");
	jmethodID getLongitude = (*env)->GetMethodID(env, locationClass, "getLongitude", "()D");

	jdouble latitude = (*env)->CallDoubleMethod(env, locationObj, getLatitude);
	jdouble longitude = (*env)->CallDoubleMethod(env, locationObj, getLongitude);

	location.lat = (Common_Latitude_t)((long)latitude);
	location.Long = (Common_Longitude_t)((long)longitude);

	// Check if elevation exists
	jmethodID isElevationExists = (*env)->GetMethodID(env, locationClass, "isElevationExists", "()Z");
	jboolean elevationExists = (*env)->CallBooleanMethod(env, locationObj, isElevationExists);

	if (elevationExists)
	{
		jmethodID getElevation = (*env)->GetMethodID(env, locationClass, "getElevation", "()F");
		jfloat elevation = (*env)->CallFloatMethod(env, locationObj, getElevation);

		Common_Elevation_t *dsrcElevation = calloc(1, sizeof(Common_Elevation_t));
		*dsrcElevation = (long)elevation;
		location.elevation = dsrcElevation;
	}
	else
	{
		location.elevation = NULL;
	}
	location.regional = NULL;
	
	referencePoint->location = location;

	// ================== Reference Point Info (Time Of Calculation) ==================
	DDate_t timeOfCalculation;

	jmethodID getYear = (*env)->GetMethodID(env, timeOfCalculationClass, "getYear", "()I");
	jmethodID getMonth = (*env)->GetMethodID(env, timeOfCalculationClass, "getMonth", "()I");
	jmethodID getDay = (*env)->GetMethodID(env, timeOfCalculationClass, "getDay", "()I");

	jint year = (*env)->CallIntMethod(env, timeOfCalculationObj, getYear);
	jint month = (*env)->CallIntMethod(env, timeOfCalculationObj, getMonth);
	jint day = (*env)->CallIntMethod(env, timeOfCalculationObj, getDay);

	timeOfCalculation.year = (long)year;
	timeOfCalculation.month = (long)month;
	timeOfCalculation.day = (long)day;

	referencePoint->timeOfCalculation = timeOfCalculation;    


}

void populateTimeRestrictions(JNIEnv *env, jobject timeRestrictionsObj, RGATimeRestrictions_t *timeRestrictions) {
    jclass timeRestrictionsClass = (*env)->GetObjectClass(env, timeRestrictionsObj);

    jmethodID getTimeWindowItemControlInfoMethod = (*env)->GetMethodID(env, timeRestrictionsClass, "getFixedTimeWindowCtrl", "()Lgov/usdot/cv/rgaencoder/TimeRestrictions;");
    jmethodID timeWindowItemControlSizeMethod = (*env)->GetMethodID(env, timeRestrictionsClass, "size", "()I");

    jobject timeWindowItemControlObj = (*env)->CallObjectMethod(env, timeRestrictionsObj, getTimeWindowItemControlInfoMethod);
    jclass timeWindowItemControlClass = (*env)->GetObjectClass(env, timeWindowItemControlObj);

    jmethodID getTimeWindowSetMethod = (*env)->GetMethodID(env, timeWindowItemControlClass, "getTimeWindowSet", "()Ljava/util/List;");
    jobject timeWindowSetList = (*env)->CallObjectMethod(env, timeWindowItemControlObj, getTimeWindowSetMethod);

    jclass timeWindowSetClass = (*env)->GetObjectClass(env, timeWindowSetList);
    jmethodID timeWindowSetSizeMethod = (*env)->GetMethodID(env, timeWindowSetClass, "size", "()I");
	jmethodID timeWindowSetGetMethod = (*env)->GetMethodID(env, timeWindowSetClass, "get", "(I)Ljava/lang/Object;");

    jint timeWindowSetSize = (*env)->CallIntMethod(env, timeWindowSetList, timeWindowSetSizeMethod);

	TimeWindowItemControlInfo_t *fixedTimeWindowItemCtrl = calloc(1, sizeof(TimeWindowItemControlInfo_t));

    for (jint nIndex = 0; nIndex < timeWindowItemControlSizeMethod; nIndex++) {

		jobject timeWindowInformationObj = (*env)->CallObjectMethod(env, timeWindowSetList, timeWindowSetGetMethod, nIndex);
		jclass timeWindowInformationClass = (*env)->GetObjectClass(env, timeWindowInformationObj);

		TimeWindowInformation_t *timeWindowInformation = calloc(1, sizeof(TimeWindowInformation_t));

        DaysOfTheWeek_t daysOfTheWeek;
        DDate_t startPeriod;
        DDate_t endPeriod;
        GeneralPeriod_t generalPeriod;

        //DaysOfTheWeek
        jmethodID getDaysOfTheWeekMethod = (*env)->GetMethodID(env, timeWindowInformationClass, "getDaysOfTheWeek", "()Lgov/usdot/cv/rgaencoder/DaysOfTheWeek;");
        jobject daysOfTheWeekObj = (*env)->CallObjectMethod(env, timeWindowInformationObj, getDaysOfTheWeekMethod);
        jclass daysOfTheWeekClass = (*env)->GetObjectClass(env, daysOfTheWeekObj);

		jshort daysOfTheWeekShort = (*env)->CallShortMethod(env, daysOfTheWeekObj, getDaysOfTheWeekMethod);

		timeWindowInformation->daysOfTheWeek = calloc(1, sizeof(DaysOfTheWeek_t));

        uint8_t daysOfTheWeekValue = (uint8_t)daysOfTheWeekShort;
		timeWindowInformation->daysOfTheWeek->buf = calloc(1, sizeof(uint8_t));
		timeWindowInformation->daysOfTheWeek->buf[0] = daysOfTheWeekValue;
		timeWindowInformation->daysOfTheWeek->size = 1;
		timeWindowInformation->daysOfTheWeek->bits_unused = 0;

        //StartPeriod
        jmethodID getStartPeriodMethod = (*env)->GetMethodID(env, timeWindowInformationClass, "getStartPeriod", "()Lgov/usdot/cv/rgaencoder/DDate;");
        jobject startPeriodObj = (*env)->CallObjectMethod(env, timeWindowInformationObj, getStartPeriodMethod);
        jclass startPeriodClass = (*env)->GetObjectClass(env, startPeriodObj);

        jmethodID getStartYear = (*env)->GetMethodID(env, startPeriodClass, "getYear", "()I");
        jmethodID getStartMonth = (*env)->GetMethodID(env, startPeriodClass, "getMonth", "()I");
        jmethodID getStartDay = (*env)->GetMethodID(env, startPeriodClass, "getDay", "()I");

        jint startYear = (*env)->CallIntMethod(env, startPeriodObj, getStartYear);
        jint startMonth = (*env)->CallIntMethod(env, startPeriodObj, getStartMonth);
        jint startDay = (*env)->CallIntMethod(env, startPeriodObj, getStartDay);

		timeWindowInformation->startPeriod = calloc(1, sizeof(DDate_t));

        timeWindowInformation->startPeriod->year = (long)startYear;
        timeWindowInformation->startPeriod->month = (long)startMonth;
        timeWindowInformation->startPeriod->day = (long)startDay;  

        //EndPeriod
        jmethodID getEndPeriodMethod = (*env)->GetMethodID(env, timeWindowInformationClass, "getEndPeriod", "()Lgov/usdot/cv/rgaencoder/DDate;");
        jobject endPeriodObj = (*env)->CallObjectMethod(env, timeWindowInformationObj, getEndPeriodMethod);
        jclass endPeriodClass = (*env)->GetObjectClass(env, endPeriodObj);

        jmethodID getEndYear = (*env)->GetMethodID(env, endPeriodClass, "getYear", "()I");
        jmethodID getEndMonth = (*env)->GetMethodID(env, endPeriodClass, "getMonth", "()I");
        jmethodID getEndDay = (*env)->GetMethodID(env, endPeriodClass, "getDay", "()I");

        jint endYear = (*env)->CallIntMethod(env, endPeriodObj, getEndYear);
        jint endMonth = (*env)->CallIntMethod(env, endPeriodObj, getEndMonth);
        jint endDay = (*env)->CallIntMethod(env, endPeriodObj, getEndDay);

		timeWindowInformation->endPeriod = calloc(1, sizeof(DDate_t));

        timeWindowInformation->endPeriod->year = (long)endYear;
        timeWindowInformation->endPeriod->month = (long)endMonth;
        timeWindowInformation->endPeriod->day = (long)endDay;  

        //GeneralPeriod
        jmethodID getGeneralPeriodMethod = (*env)->GetMethodID(env, timeWindowInformationClass, "getGeneralPeriod", "()Lgov/usdot/cv/rgaencoder/GeneralPeriod;");
        jobject generalPeriodObj = (*env)->CallObjectMethod(env, timeWindowInformationObj, getGeneralPeriodMethod);
        jclass generalPeriodClass = (*env)->GetObjectClass(env, generalPeriodObj);

		jmethodID getGeneralPeriodmethod = (*env)->GetMethodID(env, generalPeriodClass, "getGeneralPeriod", "()I");

		jshort generalPeriodShort = (*env)->CallShortMethod(env, generalPeriodObj, getGeneralPeriodMethod);

		timeWindowInformation->generalPeriod = calloc(1, sizeof(GeneralPeriod_t));
		timeWindowInformation->generalPeriod = (long)generalPeriodShort;

		// Adding to timeRestrictions
		ASN_SEQUENCE_ADD(&fixedTimeWindowItemCtrl->timeWindowSet.list, timeWindowInformation);
    }
    jmethodID getOtherDataSetItemCtrlMethod = (*env)->GetMethodID(env, timeRestrictionsClass, "getOtherDataSetItemCtrl", "()Lgov/usdot/cv/rgaencoder/TimeRestrictions;");
    jobject otherDataSetItemCtrlObj = (*env)->CallObjectMethod(env, timeRestrictionsObj, getOtherDataSetItemCtrlMethod);
    jclass otherDataSetItemCtrlClass = (*env)->GetObjectClass(env, otherDataSetItemCtrlObj);

	OtherDSItemControlInfo_t *otherDataSetItemCtrl = calloc(1, sizeof(OtherDSItemControlInfo_t));

    jmethodID getMessageID = (*env)->GetMethodID(env, otherDataSetItemCtrlClass, "getMessageID", "()J");
    jlong messageID = (*env)->CallLongMethod(env, otherDataSetItemCtrlObj, getMessageID);

    jmethodID getEnaAttributeID = (*env)->GetMethodID(env, otherDataSetItemCtrlClass, "getEnaAttributeID", "()J");
    jlong enaAttributeID = (*env)->CallIntMethod(env, otherDataSetItemCtrlObj, getEnaAttributeID);

    otherDataSetItemCtrl->messageID = (long)messageID;
    otherDataSetItemCtrl->enaAttributeID = (long)enaAttributeID;

}