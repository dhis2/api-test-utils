package org.hisp.dhis.request;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class QueryParamsBuilderTest
{
    @Test
    public void shouldAddKeyAndValue() {
        String queryParams = new QueryParamsBuilder().add( "key", "value" )
            .add( "anotherKey", "anotherValue" )
            .build();

        assertThat(queryParams, equalTo("?key=value&anotherKey=anotherValue"));


        queryParams = new QueryParamsBuilder().add( "key=value" )
            .add( "anotherKey=anotherValue" )
            .build();

        assertThat(queryParams, equalTo("?key=value&anotherKey=anotherValue"));
    }

    @Test
    public void shouldAddAll() {
        String queryParams = new QueryParamsBuilder().addAll( "key=value", "anotherKey=anotherValue" )
            .build();

        assertThat(queryParams, equalTo("?key=value&anotherKey=anotherValue"));
    }

    @Test
    public void shouldReplaceParam() {
        String queryParams = new QueryParamsBuilder().add( "key", "value" )
            .add( "key", "anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=anotherValue" ) );
    }
}
