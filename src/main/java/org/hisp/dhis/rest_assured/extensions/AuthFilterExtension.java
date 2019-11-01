package org.hisp.dhis.rest_assured.extensions;

import io.restassured.authentication.NoAuthScheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

/**
 * @author Gintare Vilkelyte <vilkelyte.gintare@gmail.com>
 */
public class AuthFilterExtension
    implements io.restassured.spi.AuthFilter
{
    private String lastLoggedInUser = "";

    @Override
    public Response filter( FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
        FilterContext ctx )
    {
        if ( requestSpec.getAuthenticationScheme() instanceof NoAuthScheme )
        {
            if ( hasSessionCookie( requestSpec ) )
            {
                requestSpec.removeCookies();
            }

            lastLoggedInUser = "";
        }

        if ( requestSpec.getAuthenticationScheme() instanceof PreemptiveBasicAuthScheme &&
            ((PreemptiveBasicAuthScheme) requestSpec.getAuthenticationScheme()).getUserName() != lastLoggedInUser )
        {
            if ( hasSessionCookie( requestSpec ) )
            {
                requestSpec.removeCookies();
            }

            lastLoggedInUser = ((PreemptiveBasicAuthScheme) requestSpec.getAuthenticationScheme()).getUserName();
        }

        final Response response = ctx.next( requestSpec, responseSpec );
        return response;
    }

    private boolean hasSessionCookie( FilterableRequestSpecification requestSpec )
    {
        return requestSpec.getCookies().hasCookieWithName( "JSESSIONID" ) ||
            requestSpec.getCookies().hasCookieWithName( "SESSION" );
    }
}