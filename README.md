# android-sample-projects

Sample Android projects inspired by Google's architecture blueprints, using the public Github API as my remote data source

In this project you'll find:

*   A **ViewModel** layer between the Fragment (View) and Repository layers
*   Reactive UIs using **LiveData** observables and Kotlin synthetics / view bindings
*   A data layer with a **Repository** and two data sources (Room for local storage and Retrofit for remote data fetching)
*   Kotlin **Coroutines** for asynchronous pulling of data from database and server
*   
## Getting Started

 * Clone this repository
 * Open the Project with Android Studio
 * Checkout a branch
 * Run the app

## Branches
 
 * **main** - a simple MVVM architecture using coroutines
 * **paging2** - added the Android Paging2 library
 * **viewbinding** - replaced kotlin synethics with view binding

## Acknowledgements

Jose Alcerreca for his continuous support and effort providing Android architecture blueprints:

https://github.com/android/architecture-samples

Florina Muntenescu and all of the other contributors on the Android paging codelab repository:

https://github.com/googlecodelabs/android-paging
