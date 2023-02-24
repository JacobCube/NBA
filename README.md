# Demo full Jetpack Compose app

This project serves as a demonstration of my skills in the newest technologies in native Android development (Jetpack Compose, Room database, Paging 3, Hilt, Jetpack Navigation,...).
It was developed using the open source balldontlie API (https://www.balldontlie.io/home.html#introduction).

How the app works:
Upon opening, list of NBA players provided by the API (initial page) is being either downloaded from RestApi via Retrofit or retrieved from local Room database.
Whenever app decides to download the data from RestApi (data older than 1 hour or it doesn't exist in the local database) it is being stored locally into the mentioned database as well.
That is especially useful for the opening of player detail (upon clicking on any item in the players list) or team detail (upon clicking on the team information on right side of the items). Passing Serializable, Parcelable or JSON through intent or arguments is against best practise, instead Google urges developers to store data into a local storage and use a mediator such as repository to retrieve previously stored data by unique identifier.

Unfortunately the API doesn't retrieve any URLs for images of players/teams etc., so I've used the alpha Glide Compose library for at least loading icons as a showcase. If there were images in every item, there's a pretty neat way of preloading the images (via GlideLazyListPreloader) we could use, which supports LazyList (doesn't support LazyVerticalGrid though).
