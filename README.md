# Branch Take Home Test

## Architecture
- When I begin to break down a problem - I want to clearly understand the goal of the application. The outline indicates there should be one endpoint that takes a string path variable, and the application will make external calls to retrieve data. With that information, I implemented a design pattern that uses a controller to expose REST endpoints, a service to hold the logic, and a client to make outbound calls. 
- This pattern sections up the responsibilities of the application, but the code isn’t segmented to the extent that makes it difficult to read. This is a strategy I used at Ford Motor Company; it is simple and repeatable, so my team implemented it frequently. Because of this, our repositories were structured similarly. Due to the similarities, it allowed us to traverse and edit our repositories quickly.

## Decisions
- One of the first decisions I made while working on this test was deciding between RestTemplates and WebClients. While WebClients are quick and are non-blocking; this application did not require quick response times. Due to this, RestTemplates would be a more appropriate solution for their simplicity and readability.
- I chose to have the basepath of this application include ```/api/v1``` because of the usefulness of versioning APIs. Not including it from the start can cause growing pains as an API evolves.
- I decided against try/catch blocks in the client, and instead used a global exception handler. This feature keeps all the error handling in a central location. Compared to try/catch blocks which can be spread throughout the application. A global exception handler is extendable. When new endpoints have errors; they can be added to one place and tested.
- I have separate models for external calls and for what the application will return. If one changes, they aren't tied together. If they were coupled it could cause complications when adjusting the APIs.
- My last decision was to implement a caching system for the GitHub API calls. They return an ETag header and a Last-Modified header which I used to reduce unnecessary API calls. To do so, I kept a Bean of a HashMap. This kept track of usernames and their caching data. Since there are two API calls, I couldn’t use the same HashMap for both. Instead of having two HashMaps (one for the user data and another for the repo data), I decided to keep them in the same map. I did this by appending a '-user' or a '-repo' on the GitHub username to indicate which data was being cached.

## Install/Run and Utilization
To run the application, use: ```./gradlew bootRun``` \
The application should be accessible from http://localhost:8080 \
There is one endpoint exposed: ```/api/v1/user/{GitHubUsername}``` \
This endpoint will return a variety of user information as well as all of their repositories (name and url)
