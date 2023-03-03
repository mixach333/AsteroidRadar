<img src="https://img.shields.io/badge/kotlin-1C2149?style=for-the-badge&logo=kotlin&logoColor=orange"/>

## 
Technologies/libraries:

<img src="https://img.shields.io/badge/livedata-1C2149?style=for-the-badge&logo=google&logoColor=blue"/>   <img src="https://img.shields.io/badge/MVVM-1C2149?style=for-the-badge&logo=google&logoColor=blue"/>   <img src="https://img.shields.io/badge/NavComponent-1C2149?style=for-the-badge&logo=google&logoColor=blue"/>   <img src="https://img.shields.io/badge/data binding-1C2149?style=for-the-badge&logo=google&logoColor=blue"/>


<img src="https://img.shields.io/badge/Kotlin Coroutines-1C2149?style=for-the-badge&logo=kotlin&logoColor=blue"/>   <img src="https://img.shields.io/badge/Room-1C2149?style=for-the-badge&logo=kotlin&logoColor=blue"/>   <img src="https://img.shields.io/badge/Retrofit-1C2149?style=for-the-badge&logo=kotlin&logoColor=blue"/>
<img src="https://img.shields.io/badge/Picasso-1C2149?style=for-the-badge&logo=kotlin&logoColor=blue"/>   <img src="https://img.shields.io/badge/Use Cases-1C2149?style=for-the-badge&logo=kotlin&logoColor=blue"/>   <img src="https://img.shields.io/badge/Unit Tests-1C2149?style=for-the-badge&logo=kotlin&logoColor=blue"/>  

## Important note
### The app configuration
To use the Asteroid Radar app, you will need to obtain an API key from NASA Open APIs. Here are the steps to get your API key:

1. Go to https://api.nasa.gov/ and click on the "Generate API Key" button.
2. Fill out the required information to create an account. If you already have an account, you can skip this step.
3. Once you have created an account, you will receive an email with a link to confirm your account. Click on the link to confirm your account.
4. After you have confirmed your account, you will be redirected to the API key generation page. 
5. Your API key will be displayed on the screen and will be sent on your emeil. Copy the API key and paste it into your local.properties file under the variable name API_KEY.
6. In your ApiKey object, you can access your API key using the BuildConfig.API_KEY property, which reads the API_KEY variable from your local.properties file. If the API_KEY variable is empty, it will use a default value of "DEMO_KEY". Remember to replace this default value with your actual API key before deploying your app.


## Screenshots
![Screenshot1](https://video.udacity-data.com/topher/2020/June/5edeac1d_screen-shot-2020-06-08-at-2.21.53-pm/screen-shot-2020-06-08-at-2.21.53-pm.png)
![Screenshot2](https://video.udacity-data.com/topher/2020/June/5edeac35_screen-shot-2020-06-08-at-2.22.18-pm/screen-shot-2020-06-08-at-2.22.18-pm.png)

## Asteroid Radar
Asteroid Radar is an Android app that allows you to view the asteroids detected by NASA that pass near Earth. You can view all the detected asteroids given a period of time with data such as the size, velocity, distance to earth, and if they are potentially hazardous. The app also displays the NASA image of the day to make it more striking.

## Features
### The app includes the following features:

* Main screen with a list of clickable asteroids, using a RecyclerView with its adapter. 
* Details screen that displays the selected asteroid data, including the correct image depending on the isPotentiallyHazardous asteroid parameter. 
* Data fetching from NASA NeoWS (Near Earth Object Web Service) API. 
* Parsing of JSON data using Moshi and retrofit-converter-scalars. 
* Local database to store asteroids data. 
* Ability to mark asteroids as favorites using a favorites button for each asteroid in the list.
* Sorting of asteroids by date and filtering of asteroids from today onwards, filtering by favorites.
* Background worker to download and save today's asteroids once a day when the device is charging and Wi-Fi is enabled. The same worker deletes the asteroids from the database which are from the previous days(except the asteroids which added to favorites)
* Download and display of Picture of Day using Moshi and Picasso library. 
* Content descriptions added to the views to ensure accessibility with TalkBack. 
* Offline support. 



## Technologies
### The app was built using the following technologies:

* Kotlin programming language
* Retrofit library to download the data from the Internet
* Moshi to convert the JSON data to usable data in the form of custom classes
* Picasso library to download and cache images
* RecyclerView to display the asteroids in a list
* ViewModel to manage UI-related data in a lifecycle-conscious way
* Room to provide abstraction layer over SQLite database
* LiveData to observe changes in the data and update the UI accordingly
* Data Binding to bind UI components in the layouts to the app's data sources
* Navigation to handle navigation between screens
* WorkManager to manage the background worker for periodic data fetching and caching

## Getting Started
### To get started with the app, you can follow these steps:

Clone the repository to your local machine.
* Open the project in Android Studio.
* Run the app on an emulator or a physical device.
* Use the app to view the list of asteroids and their details, as well as the Picture of Day.
* You are free to use it in your personal or commercial projects. However, if you use this code, please credit me and link back to this repository.
