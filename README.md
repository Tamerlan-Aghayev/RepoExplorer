# GitHub Repository Explorer

This project is a Spring Boot application that provides an API to explore GitHub repositories and their branches for a given username using the GitHub API.

## Tools and Technologies

- Java 21
- Spring Boot 3
- Gradle
- Spring Cloud OpenFeign (for GitHub API integration)
- JUnit and Spring Boot Test (for integration testing)

## Installation

1. Clone the repository: git clone https://github.com/TamerlanDev/RepoExplorer.git
2. Navigate to the project directory
3. Build the project: ./gradlew build
## Running the Application

To run the application, use the following command: ./gradlew bootRun
The application will start on `http://localhost:8080` by default.

## API Documentation

### Explore Repositories

Retrieves a list of repositories and their branches for a given GitHub username.

- **URL**: `/api/repos/{username}`
- **Method**: GET
- **URL Params**:
    - Required: `username=[string]`
- **Success Response**:
    - Code: 200
    - Content: JSON object containing a list of repositories and their branches

Example: GET /api/repos/torvalds

## GitHub API Integration

This project uses Spring Cloud OpenFeign to integrate with the GitHub API. The `CodeRepositoryClient` interface defines the following API calls:

1. Validate User:
    - Endpoint: `/users/{username}`
    - Method: GET

2. Get Repositories:
    - Endpoint: `/users/{username}/repos`
    - Method: GET
    - Query Params: `per_page`, `page`

3. Get Branches:
    - Endpoint: `/repos/{owner}/{repo}/branches`
    - Method: GET

## Testing

To run the tests, use the following command: ./gradlew test
This will run both unit tests and integration tests.
