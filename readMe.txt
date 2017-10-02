Read me for challenge app.

Instructions for starting the application:
In the challenge directory, run the command ./gradlew bootrun. This will start the application web server.

Authentication:
This project has basic authentication. A username and a password is required to reach any endpoint. Usernames can be looked up in the file src/main/resources/data.sql. Passwords for all users are set to “password”. NOTE- I didn’t get the chance to encrypt the passwords in the database. Obviously, in a prod application this would be a must.
Sample username:ella
Default password:password



Endpoints: 
NOTE- the endpoints retrieve data for the authenticated user.
Feed 
HTTP GET
Endpoint: http://localhost:8080/feed
Optional query parameter: search
Sample Url:http://localhost:8080/feed?search=Duis
Sample Response: 
[
    {
        "tweet": "gravida sagittis. Duis gravida. Praesent eu nulla at sem molestie",
        "userName": "noble"
    }
]

Following
HTTP GET
Endpoint: http://localhost:8080/following
Sample Response:
[{"userName":"xandra"},{"userName":"eagan"},{"userName":"noble"}]

Followers
HTTP GET
Endpoint: http://localhost:8080/followers
Sample Response:
[{"userName":"guinevere"},{"userName":"emma"},{"userName":"rigel"},{"userName":"noble"}]


Follow
HTTP POST
Endpoint: http://localhost:8080/follow
Required query parameter: username
Sample Url http://localhost:8080/follow?username=noble
Sample Response: No Response. Status 204

Unfollow
HTTP POST
Endpoint: http://localhost:8080/unfollow
Required query parameter: username
Sample Url http://localhost:8080/unfollow?username=noble
Sample Response: No Response. Status 204

Top Followers(Additional)
HTTP GET
Endpoint: http://localhost:8080/topFollowers
Sample Response:
[
    {
        "Follower": "Ignatius Salinas",
        "Person": "Vanna Noel"
    },
    {
        "Follower": "Rigel Young",
        "Person": "Vanna Noel"
    },
    {
        "Follower": "Judith Woodard",
        "Person": "Vanna Noel"
    },
    {
        "Follower": "Ella Mullen",
        "Person": "Rigel Young"
    }
]
NOTE: This endpoint may return multiple Followers for a Person if there is a tie for most popular followers;

TESTING:
I have added some trivial testing of my DAO’s. This is not prod level testing. It requires certain data to be present in the database for the tests to succeed. Obviously, if this testing was more robust, dependency creation and deletion would take place within the test. In my efforts to limit the scope/effort for this project, I decided to leave those things out.

Database Schema Changes:
-Follower:changed the primary key in the followers table to be the composite key of person_id and follower_person_id.
-Person: added a username field and a password field to the person table.

IMPROVEMENTS(Wishlist if I had a bit more time to spare):
Caching- the objects can be cached for faster reads.
Scrollable interfaces in the DAO’s - limit the amount of data we are fetching from the database and loading in memory. This would be necessary for scale.
Password encrypting- as mentioned in the Authentication section, encrypt passwords. 

