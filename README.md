# Smart Scholarship Tracker
A JavaFX application for discovering scholarships, evaluating eligibility, tracking applications, and receiving application status notifications.

## Requirements
- Java 21 or later

## Configuration
Create a `config.properties` file in the same folder as the executable JAR.

Example:
```properties
api.tokens=YOUR_API_TOKEN_1,YOUR_API_TOKEN_2
```

## Running the application

Run:
```bash
java -jar SmartScholarshipTracker.jar
```

## Notes
- The application retrieves scholarship information from the Apify Scholarship API.
- If `config.properties` is not available or the API cannot be reached, the application automatically loads the bundled scholarship dataset.
- Retrieved scholarship data is cached locally for offline use.
