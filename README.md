# WeatherSelectionApp
Gives user the ability to check weather at their preferred location using different weather providers

The app supports four weather providers, OpenWeatherMap Api, Dark Sky Api, 5DayWeather Api, and WeatherBit Api. Based on user preference of weather provider the data will be fetched and presented to the user.

The application is written in Kotlin and follows an MVVM architectural pattern. LiveData has been used for the View to observe changes in ViewModel and for ViewModel to observe changes in Model.

View <LiveData> ViewModel <LiveData> Model

LiveData in brackets shows how it is used.

Retrofit has been used for network calls and a place picker api is used to select a place for Dark Sky Api. AlarmManager has been used to make the request every one hour.

Factory pattern is used to create the weather provider objects which reduces the effort of adding a new weather data provider.

Once the user selects a weather provider, it is persisted throughtout the app starts/stop. Similar is the case with user preferred location. This can be tested with notifications appearing from the selected weather provider for a selected place.

