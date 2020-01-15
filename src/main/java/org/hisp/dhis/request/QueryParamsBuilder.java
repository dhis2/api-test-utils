package org.hisp.dhis.request;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class QueryParamsBuilder
{
    private List<MutablePair<String, String>> queryParams;

    public QueryParamsBuilder()
    {
        queryParams = new ArrayList<>();
    }

    /**
     * Adds or updates the query param.
     * Format: key=value
     *
     * @param param
     * @return
     */
    public QueryParamsBuilder addOrUpdate( String param )
    {
        String[] split= param.split( "=" );

        return this.addOrUpdate( split[0], split[1] );
    }

    /**
     * Adds or updates the query param.
     *
     * @param key
     * @param value
     * @return
     */

    public QueryParamsBuilder addOrUpdate( String key, String value )
    {
        MutablePair<String, String> pair = getByKey( key );

        if ( pair != null )
        {
            pair.setRight( value );
            return this;
        }

        queryParams.add( MutablePair.of( key, value ) );

        return this;
    }

    /**
     * Adds query param even when param with the same key exists.
     * Format: key=value
     *
     * @param param
     * @return
     */
    public QueryParamsBuilder add( String param )
    {
        String[] split= param.split( "=" );

        return this.add( split[0], split[1] );
    }

    /**
     * Adds the query param even when param with the same key exists.
     *
     * @param key
     * @param value
     * @return
     */

    public QueryParamsBuilder add( String key, String value )
    {
        queryParams.add( MutablePair.of( key, value ) );

        return this;
    }

    /**
     * Adds or updates the query params.
     * @param params
     * @return
     */
    public QueryParamsBuilder addOrUpdateAll( String... params )
    {
        for ( String param : params )
        {
            this.addOrUpdate( param );
        }

        return this;
    }

    /**
     * Adds the query params.
     * @param params
     * @return
     */
    public QueryParamsBuilder addAll( String... params )
    {
        for ( String param : params )
        {
            this.add( param );
        }

        return this;
    }

    private MutablePair<String, String> getByKey( String key )
    {
        return queryParams.stream()
            .filter( p -> p.getLeft().equals( key ) )
            .findFirst()
            .orElse( null );
    }

    /**
     * Returns the query params built as part of URL query.
     * Example: ?key=value&anotherKey=anotherValue
     * @return
     */
    public String build()
    {
        if ( queryParams.size() == 0 )
        {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append( "?" );

        for ( int i = 0; i < queryParams.size(); i++ )
        {
            builder.append( String.format( "%s=%s", queryParams.get( i ).getLeft(), queryParams.get( i ).getRight() ) );

            if ( i != queryParams.size() - 1 )
            {
                builder.append( "&" );
            }
        }

        return builder.toString();
    }
}
