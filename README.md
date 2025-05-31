# Context

This is a RESTful API implemented using Java + Spring Boot.  This API has the below capabilities:
* It can take an image file sent to it in a POST request, save that image in S3, run the image through AWS Rekognition, and return the text detections.
* It can save a desired text detection to a DynamoDB table via a separate POST request.
* It can retrieve all of the saved text detections in the DynamoDB table via a GET request.

The intent of this project was originally to serve as the backend of a study tool I was building.  I was planning to use AWS Rekognition to streamline adding new kanji to my Japanese language study decks.  Upon completion of the build-out and testing, I realized that AWS Rekognition does not support Japanese text, therefore there will not be further development on this repo.

While AWS Rekognition does not suit my needs, the code here is functional and can be used as a good starter for an application that needs to leverage AWS Rekognition.  I would note that certain decisions were made with the low-volume of my use case in mind (i.e. using a non-paginated DynamoDB scan) that I would avoid in a higher-traffic application.

## Installation

This project requires an installation of Java 24 and Maven.  Given that those are set up on the machine executing the project, then simply executing this project as a Spring Boot project should perform any necessary installation of dependencies.

## Contributing

If you would like to fork this repo, please feel free to do so.  However, there will be no further contributions to this repo.