/*
 * Copyright (c) 2004-2018, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.actions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.hisp.dhis.request.QueryParamsBuilder;
import org.hisp.dhis.response.dto.ApiResponse;

import java.io.File;

/**
* @author Gintare Vilkelyte
 * Convenience class for Rest-Assured requests
 */
public class RestApiActions
{
    private String endpoint;

    private String baseUri;

    public RestApiActions( final String endpoint )
    {
        this.baseUri = RestAssured.baseURI;
        this.endpoint = endpoint;
    }

    /**
     * Overrides baseUri provided in RestAssured configuration.
     *
     * @param baseUri uri to DHIS2 instance
     * @return RestApiActions
     */
    public RestApiActions setBaseUri( String baseUri )
    {
        this.baseUri = baseUri;

        return this;
    }

    protected RequestSpecification given()
    {
        return RestAssured.given()
            .baseUri( this.baseUri )
            .basePath( endpoint );
    }

    /**
     * Sends post request to specified endpoint.
     * If post request successful, saves created entity in TestRunStorage
     *
     * @param object Body of request
     * @return ApiResponse
     */
    public ApiResponse post( Object object )
    {
        return post( "", object, null );
    }

    public ApiResponse post( String resource, Object object )
    {
        return post( resource, ContentType.JSON.toString(), object, null );
    }

    public ApiResponse post( String resource, Object object, QueryParamsBuilder queryParams )
    {
        return post( resource, ContentType.JSON.toString(), object, queryParams );
    }

    public ApiResponse post( Object object, QueryParamsBuilder queryParamsBuilder )
    {
        return post( "", ContentType.JSON.toString(), object, queryParamsBuilder );
    }

    public ApiResponse post( String resource, String contentType, Object object, QueryParamsBuilder queryParams )
    {
        String path = queryParams == null ? "" : queryParams.build();

        return new ApiResponse( this.given()
            .body( object )
            .contentType( contentType )
            .when()
            .post( resource + path ) );
    }

    /**
     * Sends post request to specified endpoint and verifies that request was successful
     *
     * @param object Body of request
     * @return ID of generated entity.
     */
    public String create( Object object )
    {
        ApiResponse response = post( object );

        response.validate()
            .statusCode( Matchers.isOneOf( 200, 201 ) );

        return response.extractUid();
    }

    /**
     * Sends GET request with provided path appended to URL.
     *
     * @param path ID of resource
     * @return Response
     */
    public ApiResponse get( String path )
    {
        return get( path, null );
    }

    /**
     * Sends GET request to specified endpoint
     *
     * @return Response
     */
    public ApiResponse get()
    {
        return get( "" );
    }

    /**
     * Sends GET request with provided path and queryParams appended to URL.
     *
     * @param resourceId         Id of resource
     * @param queryParamsBuilder Query params to append to url
     * @return ApiResponse
     */
    public ApiResponse get( String resourceId, QueryParamsBuilder queryParamsBuilder )
    {
        String path = queryParamsBuilder == null ? "" : queryParamsBuilder.build();

        return new ApiResponse( this.given()
            .contentType( ContentType.TEXT )
            .when()
            .get( resourceId + path ) );
    }

    /**
     * Sends DELETE request to specified resource.
     *
     * @param path Id of resource
     * @return ApiResponse
     */
    public ApiResponse delete( String path )
    {
        return new ApiResponse( this.given()
            .when()
            .delete( path ) );
    }

    /**
     * Sends PUT request to specified resource.
     *
     * @param resourceId Id of resource
     * @param object     Body of request
     * @return ApiResponse
     */
    public ApiResponse update( String resourceId, Object object )
    {
        return new ApiResponse( this.given().body( object, ObjectMapperType.GSON )
            .when()
            .put( resourceId ) );
    }

    public ApiResponse update( String resourceId, Object object, String contentType )
    {
        return new ApiResponse( this.given()
            .contentType( contentType )
            .body( object, ObjectMapperType.GSON )
            .when()
            .put( resourceId ) );
    }

    public ApiResponse postFile( File file )
    {
        return this.postFile( file, null );
    }

    public ApiResponse postFile( File file, QueryParamsBuilder queryParamsBuilder )
    {
        String url = queryParamsBuilder == null ? "" : queryParamsBuilder.build();

        return new ApiResponse( this.given()
            .body( file )
            .when()
            .post( url ) );
    }
}

