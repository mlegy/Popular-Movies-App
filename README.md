# Popular Movies App

This is my version of `Popular Movies App` The final project of the Udacity course [Developing Android Apps: Android Fundamentals](https://www.udacity.com/course/ud853).

This App is graded as `Exceeds Specifications` by the `Udacity Team`.

For more info about the project please check the project [wiki](https://docs.google.com/document/d/1ZlN1fUsCSKuInLECcJkslIqvpKlP7jWL2TP9m6UiA6I/pub?embedded=true) by udacity.

Signed APK can be found [here](../master/app/app-release.apk).

## Features:
1. Find popular movies.
2. Find most rated movies.
3. Show trailers and reviews of the movies.
4. Mark Movies as favourites.
5. Add favourite movies.
6. Save favourite movies for offline use alongside thier trailers and reviews.

## Technical Notes:
1. Cache images for not loading them again.
2. Access the database through a `content provider`.
3. Provide Content Descriptions for `TalkBack` feature.
4. Mark movie as a favourite from the main activity and the details one.
5. Use `RecyclerView` instead of `GridView`.
6. Use `Material Design`.
7. Follow most of `Google Design Guidelines`.
8. Support Phone and Tablet devices.

## Screenshots:
- ### Phone:
    ![alt text](../master/art/device-2015-10-01-202200.png "Phone Main Activity - Portrait")
    ![alt text](../master/art/device-2015-10-01-202245.png "Phone Detial Activity - Portrait")
    ![alt text](../master/art/device-2015-10-01-202328.png "Phone Detial Activity Cont. - Portrait")
    ![alt text](../master/art/device-2015-10-01-202402.png "Phone Main Activity Cont. - Landscape")
    ![alt text](../master/art/device-2015-10-01-202436.png "Phone - No favourite movies")
    ![alt text](../master/art/device-2015-10-01-202459.png "Phone - No Internet Connection")

- ## Tablet:
    ![alt text](../master/art/device-2015-10-01-201941.png "Tablet Main Activity - Landscape")
    ![alt text](../master/art/device-2015-10-01-202119.png "Tablet Main Activity - Portrait")

## How to build the App:
You have to replace the variable `sensitiveData.API_KEY` with your API Key from [The Movie Database](https://www.themoviedb.org/documentation/api)

## Libraries:
- [Picasso](http://square.github.io/picasso/)
- [OkHttp](http://square.github.io/okhttp/)
- [Picasso Palette](http://florent37.github.io/PicassoPalette/)
- [EndlessRecyclerViewAdapter](https://github.com/rockerhieu/rv-adapter-endless)

## Credits:
- ![alt text](../master/art/ic_launcher.png) Icon made by [Catalin Fertu](http://www.flaticon.com/authors/catalin-fertu) from www.flaticon.com.
- ![alt text](../master/art/disconnected.png "No Connection") Icon made by [freepik](http://www.flaticon.com/authors/freepik) from www.flaticon.com.
- ![alt text](../master/art/ic_play_arrow.png "Youtube logo") Icon made by [Google](http://www.flaticon.com/authors/google) from www.flaticon.com.
- ![alt text](../master/art/broken_heart.png "No favourites image") Heart vector designed by [Freepik] (http://www.freepik.com/free-photos-vectors/heart).


## License:
    Popular Movies - Android Developer Nanodegree Program - Final Project #1.
    Copyright (C) 2015  Ahmad El-Melegy

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
