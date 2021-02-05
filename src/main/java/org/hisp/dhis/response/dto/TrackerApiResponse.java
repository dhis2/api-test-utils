package org.hisp.dhis.response.dto;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class TrackerApiResponse
    extends ApiResponse
{

    public TrackerApiResponse( ApiResponse response )
    {
        super( response.raw );
    }

    public List<String> extractImportedTeis()
    {
        return this.extractList( "bundleReport.typeReportMap.TRACKED_ENTITY.objectReports.uid" );
    }

    public List<String> extractImportedEnrollments()
    {
        return this.extractList( "bundleReport.typeReportMap.ENROLLMENT.objectReports.uid" );
    }

    public List<String> extractImportedEvents()
    {
        return this.extractList( "bundleReport.typeReportMap.EVENT.objectReports.uid" );
    }

    public List<String> extractImportedRelationships()
    {
        return this.extractList( "bundleReport.typeReportMap.RELATIONSHIP.objectReports.uid" );
    }

    public TrackerApiResponse validateSuccessfulImport()
    {
        validate()
            .statusCode( 200 )
            .body( "status", equalTo( "OK" ) )
            .body( "stats.created", greaterThanOrEqualTo( 1 ) )
            .body( "stats.ignored", equalTo( 0 ) )
            .body( "stats.total", greaterThanOrEqualTo( 1 ) )
            .body( "bundleReport.typeReportMap", notNullValue() );

        return this;
    }

    public ValidatableResponse validateErrorReport()
    {
        return validate().statusCode( 200 )
            .body( "stats.ignored", greaterThanOrEqualTo( 1 ) )
            .body( "validationReport.errorReports", Matchers.notNullValue() )
            .rootPath( "validationReport.errorReports" );
    }

    public ValidatableResponse validateWarningReport() {
        return validate().statusCode( 200 )
            .body( "validationReport.warningReports", Matchers.notNullValue())
            .rootPath( "validationReport.warningReports");
    }

    public ValidatableResponse validateTeis() {
        return validate()
            .body( "bundleReport.typeReportMap.TRACKED_ENTITY", notNullValue() )
            .rootPath( "bundleReport.typeReportMap.TRACKED_ENTITY" );
    }

    public ValidatableResponse validateEvents() {
        return validate()
            .body( "bundleReport.typeReportMap.EVENT", notNullValue() )
            .rootPath( "bundleReport.typeReportMap.EVENT" );
    }

    public ValidatableResponse validateEnrollments()
    {
        return validate()
            .body( "bundleReport.typeReportMap.ENROLLMENT", notNullValue() )
            .rootPath( "bundleReport.typeReportMap.ENROLLMENT" );
    }

}
