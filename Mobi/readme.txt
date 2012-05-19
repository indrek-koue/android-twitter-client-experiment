Description:
A Twitter client application for a mobile device. 

For Twitter integration there are several libraries available: https://dev.twitter.com/docs/twitter-libraries. You can choose the one suitable for 
platform and needs. The more mature libraries are preferred. The usage of such library is not 
mandatory, you can create the application directly on top of the twitter REST APIs: https://
dev.twitter.com/docs/api.

Non-functional requirements:
1) Platform: Android/iOS/Windows Phone
2) Source code will be hosted in revision control system and accessible to us (we prefer github, 
but accept other places as well)

Functional requirements:
1) User authentication - https://dev.twitter.com/docs/api#oauth
2) Update a status (also known as tweeting) - https://dev.twitter.com/docs/api/1/post/statuses/
update
3) Display user’s home timeline - https://dev.twitter.com/docs/api/1/get/statuses/home_timeline
4) Search tweets - https://dev.twitter.com/docs/api/1/get/search

We will evaluate following aspects:
1) Object oriented approach
2) Code style and cleanness
3) Understanding the basics of platform and application's life cycle
4) Memory usage
6) User experience (please remember to handle blocking and time-consuming operations 
properly)
5) Creativity :)