# CSTV_Challenge

App that displays CS:GO matches. Made for the [Moises coding challenge](https://fuzecc.notion.site/Android-Dev-d48d6ac18c104fee908d47208a58b4d3).

The code was made using Clean Architecture's principles, and the Presentation/UI layers use MVVM implemented with Jetpack components, such as ViewModel and LiveData.

The UI Layer was written using XML. Jetpack Compose was considered, but I opted for XML instead because of my familiarity with it, and the time constraints.

For threading and concurrency Kotlin Coroutines was used.

## Instructions
Create a local.properties file in the project's root folder, and add this line at the end:

```
API_KEY = "{YOUR_PANDASCORE_TOKEN}"
```

After that you should be able to run the project in Android Studio.

## Screenshots
### Portuguese
#### Splash
<img src="/screenshots/portuguese/splash.png" width="260"/> 

#### Matches screen
<img src="/screenshots/portuguese/match_list_loading.png" width="260"/> <img src="/screenshots/portuguese/match_list.png" width="260"/> <img src="/screenshots/portuguese/match_list_error.png" width="260"/> 

#### Match details screen
<img src="/screenshots/portuguese/match_details_loading.png" width="260"/> <img src="/screenshots/portuguese/match_details.png" width="260"/> <img src="/screenshots/portuguese/match_details_error.png" width="260"/>

### English
#### Matches screen
<img src="/screenshots/english/match_list.png" width="260"/> <img src="/screenshots/english/match_list_error.png" width="260"/> 

#### Match details screen
<img src="/screenshots/english/match_details.png" width="260"/> <img src="/screenshots/english/match_details_error.png" width="260"/>

## Known issues
When running in Android 12 the Splash screen will not show the logo, if you launch the app from inside Android Studio. This is an issue with Android's [SplashScreen library](https://developer.android.com/guide/topics/ui/splash-screen) and the bug is tracked [here](https://issuetracker.google.com/issues/205021357?pli=1). This issue does not happen if you run the app from the device launcher.

## Next steps
Since this was done in the short span of a week, it's needless to say there is still much to be improved in the project. Some of the ideas for future improvement are:
- Writing more tests.
- Break the `app`, `common`, `match_list` and `match_details` packages into their own separate modules.
- Rewriting the UI layer with [Jetpack Compose](https://developer.android.com/jetpack/compose).
