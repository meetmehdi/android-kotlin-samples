# android-sample-projects

Sample Android projects inspired by Google's architecture blueprints, using the public Github API as my remote data source

In this project you'll find:

*   A **ViewModel** layer between the Fragment (View) and Repository layers
*   Reactive UIs using **LiveData** observables and Kotlin synthetics / view bindings
*   A **Repository** layer and Retrofit API service for remote data fetching
*   Kotlin **Coroutines** for asynchronous pulling of data from database and server

## Getting Started

 * Clone this repository
 * Open the Project with Android Studio
 * Checkout a branch - Note: view-binding branch is the most up-to-date and my preferred architecture right now!
 * Run the app

## Branches
 
 * **main** - a simple MVVM architecture using coroutines
 * **paging2** - added the Android Paging2 library
 * **view-binding** - replaced kotlin synthetics with view binding, also includes navigation and ViewModel unit testing
 * **widget** - added a very basic Widget to the app
 * **navigation** - added androidx Navigation

## Acknowledgements

Jose Alcerreca for his continuous support and effort providing Android architecture blueprints:

https://github.com/android/architecture-samples

Florina Muntenescu and all of the other contributors on the Android paging codelab repository:

https://github.com/googlecodelabs/android-paging
