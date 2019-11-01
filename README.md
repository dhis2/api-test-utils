# api-test-utils
Shared utility library for DHIS2 API, performance tests. 

## Packages
### actions 
Actions package contains utils needed for interacting with DHIS2 API. 

RestApiActions.java - class  simplifying all the rest-assured request methods necessary for interacting with DHIS2. This 
class should be extended by every other actions class or initialized with the name of the endpoint.

### request

Request package contains utils that helps with request creation. 

For example, QueryParamsBuilder.java`, that simplifies building URL params.

### response

Response package contains utils and models that helps with response validation.

### rest assured

Response package contains Rest-Assured framework extensions. 