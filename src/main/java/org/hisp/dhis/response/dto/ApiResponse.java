package org.hisp.dhis.response.dto;

import com.google.gson.JsonObject;
import io.restassured.path.json.config.JsonParserType;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class ApiResponse
{
    public Response getRaw()
    {
        return raw;
    }

    protected Response raw;

    public ApiResponse( Response response )
    {
        raw = response;
    }

    /**
     * Extracts uid when only one object was created.
     *
     * @return
     */
    public String extractUid()
    {
        String uid;

        if ( extract( "response" ) == null )
        {
            return extractString( "id" );
        }

        uid = extractString( "response.uid" );

        if ( !StringUtils.isEmpty( uid ) )
        {
            return uid;
        }

        return extractString( "response.importSummaries.reference[0]" );
    }

    /**
     * Extracts uids from import summaries.
     * Use when more than one object was created.
     *
     * @return
     */
    public List<String> extractUids()
    {
        return extractList( "response.importSummaries.reference" );
    }

    public String extractString( String path )
    {
        return raw.jsonPath().getString( path );
    }

    public <T> T extractObject( String path, Class<T> type )
    {
        return raw.jsonPath()
            .getObject( path, type );
    }

    public Object extract( String path )
    {
        return raw.jsonPath().get( path );
    }

    public JsonObject extractJsonObject( String path )
    {
        return raw.jsonPath( JsonPathConfig.jsonPathConfig().defaultParserType( JsonParserType.GSON ) )
            .getObject( path, JsonObject.class );
    }

    public <T> List<T> extractList( String path )
    {
        return raw.jsonPath().getList( path );
    }

    public <T> List<T> extractList( String path, Class<T> type )
    {
        return raw.body().jsonPath().getList( path, type );
    }

    public int statusCode()
    {
        return raw.statusCode();
    }

    public long getTime() {
        return raw.time();
    }

    public ValidatableResponse validate()
    {
        return raw.then();
    }

    public JsonObject getBody()
    {
        return extractJsonObject( "" );
    }

    public boolean isEntityCreated()
    {
        return this.extractUid() != null;
    }

    public String prettyPrint()
    {
        return raw.prettyPrint();
    }

    public String getAsString()
    {
        return raw.asString();
    }

    public String getContentType()
    {
        return raw.getContentType();
    }

}
