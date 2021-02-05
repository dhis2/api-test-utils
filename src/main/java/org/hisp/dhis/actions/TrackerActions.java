package org.hisp.dhis.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.hisp.dhis.request.QueryParamsBuilder;
import org.hisp.dhis.response.dto.ApiResponse;
import org.hisp.dhis.response.dto.TrackerApiResponse;
import org.hisp.dhis.utils.JsonObjectBuilder;

import java.io.File;
import java.time.Instant;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class TrackerActions
    extends RestApiActions
{
    private Logger logger = Logger.getLogger( TrackerActions.class.getName() );

    public TrackerActions()
    {
        super( "/tracker" );
    }

    public ApiResponse getJob( String jobId )
    {
        return this.get( "/jobs/" + jobId );
    }

    public ApiResponse waitUntilJobIsCompleted( String jobId )
    {
        logger.info( String.format( "Waiting until tracker job with id %s is completed", jobId ) );
        ApiResponse response = null;
        boolean completed = false;
        int maxAttempts = 100;

        while ( !completed && maxAttempts > 0 )
        {
            response = getJob( jobId );
            response.validate().statusCode( 200 );
            completed = response.extractList( "completed" ).contains( true );
            maxAttempts--;
        }

        if ( maxAttempts == 0 )
        {
            logger.warning(
                String.format( "Tracker job didn't complete in %d. Message: %s", maxAttempts, response.extract( "message" ) ) );
        }

        logger.info( "Tracker job is completed. Message: " + response.extract( "message" ) );
        return response;
    }

    public TrackerApiResponse postAndGetJobReport( File file )
    {
        ApiResponse response = this.postFile( file );

        return new TrackerApiResponse( response );
        //return getJobReportByImportResponse( response );
    }

    public TrackerApiResponse postAndGetJobReport( File file, QueryParamsBuilder queryParamsBuilder )
    {
        queryParamsBuilder.add( "async=false" );
        ApiResponse response = this.postFile( file, queryParamsBuilder );

        return new TrackerApiResponse( response );
        //return getJobReportByImportResponse( response );
    }

    public TrackerApiResponse postAndGetJobReport( JsonObject jsonObject )
    {
        ApiResponse response = this.post( jsonObject );

        return new TrackerApiResponse( response );
        //return getJobReportByImportResponse( response );
    }

    public TrackerApiResponse postAndGetJobReport( JsonObject jsonObject, QueryParamsBuilder queryParamsBuilder )
    {
        queryParamsBuilder.add( "async=false" );

        ApiResponse response = this.post( jsonObject, queryParamsBuilder );

        return new TrackerApiResponse( response );
        //return getJobReportByImportResponse( response );
    }

    public TrackerApiResponse getJobReport( String jobId, String reportMode )
    {
        ApiResponse response = this.get( String.format( "/jobs/%s/report?reportMode=%s", jobId, reportMode ) );

        // add created entities

        return new TrackerApiResponse( response );
    }

    private TrackerApiResponse getJobReportByImportResponse( ApiResponse response )
    {
        response.validate()
            .statusCode( 200 )
            .body( "response.id", notNullValue() );

        String jobId = response.extractString( "response.id" );

        this.waitUntilJobIsCompleted( jobId );

        return this.getJobReport( jobId, "FULL" );

    }
}
