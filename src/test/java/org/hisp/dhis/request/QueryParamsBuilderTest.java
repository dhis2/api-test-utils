package org.hisp.dhis.request;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
* @author Gintare Vilkelyte
 */
public class QueryParamsBuilderTest
{
    @Test
    public void addShouldAddKeyAndValue()
    {
        String queryParams = new QueryParamsBuilder().add( "key", "value" )
            .add( "anotherKey", "anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=anotherValue" ) );

        queryParams = new QueryParamsBuilder().add( "key=value" )
            .add( "anotherKey=anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=anotherValue" ) );
    }

    @Test
    public void addOrUpdateShouldAddKeyAndValue() {
        String queryParams = new QueryParamsBuilder().addOrUpdate( "key", "value" )
            .addOrUpdate( "anotherKey", "anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=anotherValue" ) );

        queryParams = new QueryParamsBuilder().addOrUpdate( "key=value" )
            .addOrUpdate( "anotherKey=anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=anotherValue" ) );
    }

    @Test
    public void addShouldAddAll()
    {
        String queryParams = new QueryParamsBuilder().addAll( "key=value", "anotherKey=anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=anotherValue" ) );
    }

    @Test
    public void addOrUpdateShouldAddAll() {
        String queryParams = new QueryParamsBuilder().addOrUpdateAll( "key=value", "anotherKey=anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=anotherValue" ) );
    }

    @Test
    public void addAllShouldNotUpdateParams() {
        String queryParams = new QueryParamsBuilder().addAll( "key=value", "anotherKey=anotherValue", "anotherKey=yetAnotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=anotherValue&anotherKey=yetAnotherValue" ) );
    }

    @Test
    public void addOrUpdateAllShouldUpdateParams() {
        String queryParams = new QueryParamsBuilder().addOrUpdateAll( "key=value", "anotherKey=anotherValue", "anotherKey=yetAnotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&anotherKey=yetAnotherValue" ) );
    }

    @Test
    public void addOrUpdateShouldUpdateParam()
    {
        String queryParams = new QueryParamsBuilder().add( "key", "value" )
            .addOrUpdate( "key", "anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=anotherValue" ) );
    }

    @Test
    public void addShouldNotUpdateParam() {
        String queryParams = new QueryParamsBuilder().add( "key", "value" )
            .add( "key", "anotherValue" )
            .build();

        assertThat( queryParams, equalTo( "?key=value&key=anotherValue" ) );
    }
}
