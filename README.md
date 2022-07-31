# CSTV_Challenge

App that displays CS:GO matches. Made for the [Moises coding challenge](https://fuzecc.notion.site/Android-Dev-d48d6ac18c104fee908d47208a58b4d3).

## Instructions
Create a local.properties file in the project's root folder, and add this line at the end:

```
API_KEY = "{YOUR_PANDASCORE_TOKEN}"
```

## Screenshots
### Portuguese
#### Splash
<img src="/screenshots/portuguese/splash.png" width="330"/> 

#### Matches screen
<img src="/screenshots/portuguese/match_list_loading.png" width="330"/> <img src="/screenshots/portuguese/match_list.png" width="330"/> <img src="/screenshots/portuguese/match_list_error.png" width="330"/> 

#### Match details screen
<img src="/screenshots/portuguese/match_details_loading.png" width="330"/> <img src="/screenshots/portuguese/match_details.png" width="330"/> <img src="/screenshots/portuguese/match_details_error.png" width="330"/>

### English
#### Matches screen
<img src="/screenshots/english/match_list.png" width="330"/> <img src="/screenshots/english/match_list_error.png" width="330"/> 

#### Match details screen
<img src="/screenshots/english/match_details.png" width="330"/> <img src="/screenshots/english/match_details_error.png" width="330"/>

## Next steps
Since this was done in the short span of a week, it's needless to say there is still much to be improved in the project. Some of the ideas for future improvement are:
- Writing tests.
- Break the `app`, `common`, `match_list` and `match_details` packages into their own separate modules.
- Rewriting the UI layer with [Jetpack Compose](https://developer.android.com/jetpack/compose).
