package org.hisp.dhis.actions;

import org.hisp.dhis.request.QueryParamsBuilder;
import org.hisp.dhis.response.dto.ApiResponse;
import org.hisp.dhis.response.dto.schemas.Schema;
import org.hisp.dhis.response.dto.schemas.SchemaProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Gintare Vilkelyte
 * Actions class meant to simplify interactions with /schemas endpoint
 */
public class SchemasActions
    extends RestApiActions
{
    public SchemasActions()
    {
        super( "/schemas" );
    }

    public List<SchemaProperty> getRequiredProperties( String resource )
    {
        List<SchemaProperty> list = get( resource ).extractList( "properties", SchemaProperty.class );

        return list.stream()
            .filter( ( SchemaProperty::isRequired ) )
            .collect( Collectors.toList() );
    }

    public Schema getSchema( String resource )
    {
        return get( resource ).extractObject( "", Schema.class );
    }

    public ApiResponse validateObjectAgainstSchema( String resource, Object obj )
    {
        return post( resource, obj );
    }

    public String findSchemaPropertyByKlassName( String klass, String property )
    {
        return findSchemaPropertyByKnownProperty( property, "klass", klass );
    }

    public String findSchemaPropertyByKnownProperty( String propertyToFind, String knownPropertyName, String knownPropertyValue )
    {
        return get( "", new QueryParamsBuilder().add( String.format( "fields=%s,%s", propertyToFind, knownPropertyName ) ) )
            .extractString(
                String.format( "schemas.find{it.%s == '%s'}.%s", knownPropertyName, knownPropertyValue, propertyToFind ) );
    }
}
