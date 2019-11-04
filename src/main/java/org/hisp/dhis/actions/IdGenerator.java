package org.hisp.dhis.actions;

import org.hisp.dhis.request.QueryParamsBuilder;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 * Convenience class to generate unique ID's using /system/id endpoint
 */
public class IdGenerator
    extends RestApiActions
{
    public IdGenerator()
    {
        super( "/system" );
    }

    public String generateUniqueId()
    {

        return get( "id.json", new QueryParamsBuilder().add( "limit=1" ) )
            .validate()
            .statusCode( 200 )
            .extract().path( "codes[0]" );
    }
}
