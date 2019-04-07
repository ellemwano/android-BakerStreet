_This project was built with love and sweat by yours truly as part of the Android Developer Nanodegree by Udacity._

_You are more than welcome to look at it for inspiration and use it as per the MIT Licence included._

_However, if you feel like copying/pasting it and submitting it as your own work, remember that plagiarism is a violation of the Udacity Honor Code. The consequences of such act may include your expulsion from the ND program (without refund) and could go as far as having you banned for life from any Udacity course and/or scholarship offered in partnership with Udacity._
_Udacity can also revoke your graduation credential at anytime if plagiarism is detected after you graduate._

_Your call._

---

# BakerStreet
Projects #3 of the [Android Developer](https://eu.udacity.com/course/android-developer-nanodegree-by-google--nd801) Nanodegree by Udacity.

## Project Overview
Create an app that will allow a user to select a recipe and see video-guided steps for how to complete it. This also involves finding and handling error cases, adding accessibility features, allowing for localization, adding a widget, and adding a library.

## Why this Project
As a working Android developer, you often have to create and implement apps where you are responsible for designing and planning the steps you need to take to create a production-ready app. Unlike project #2 where we gave you an implementation guide, it will be up to you to figure things out for this app.

## Learning Objectives
- Use Exoplayer to display videos.
- Handle error cases in Android.
- Add a widget to your app experience.
- Leverage a third-party library in your app.
- Use Fragments to create a responsive design that works on phones and tablets.

## Completed Project - BakerStreet
![](https://github.com/ellemwano/android-BakerStreet/blob/master/pics/portPack.png)
![](https://github.com/ellemwano/android-BakerStreet/blob/master/pics/tabletPack.png)

### Android Framework        
- Fragments
- Room
- ViewModel and LiveData
- Repository
- SQLite
- Widgets
- Espresso

### Libraries
- [Exoplayer](https://github.com/google/ExoPlayer) v2.7.3
- [Picasso](https://square.github.io/picasso/) v2.71828
- [Butterknife](http://jakewharton.github.io/butterknife/) v8.8.1
- [Retrofit](https://square.github.io/retrofit/) v2.4.0
- [Page Indicator](https://github.com/romandanylyk/PageIndicatorView) 'com.romandanylyk:pageindicatorview:1.0.1@aar'
- [Stetho](http://facebook.github.io/stetho/) v1.5.0

Credits:
- App icon: [icons8.com](https://icons8.com/icons/set/donut)
- Baking placeholder photo: [freepik.com](https://www.freepik.com/free-photos-vectors/food) 


## Project Requirements
- **General App Usage**
  * Display recipes - App should display recipes from provided network resource.
  * App Navigation - App should allow navigation between individual recipes and recipe steps.
  * Utilization of RecyclerView - App uses RecyclerView and can handle recipe steps that include videos or images.
  * App conforms to common standards found in the [Android Nanodegree General Project Guidelines](http://udacity.github.io/android-nanodegree-guidelines/core.html).
- **Components and Libraries**
  * Master Detail Flow and Fragments - Application uses Master Detail Flow to display recipe steps and navigation between them.
  * Exoplayer to display videos - Application uses Exoplayer to display videos.
  * Proper utilization of video assets - Application properly initializes and releases video assets when appropriate.
  * Proper network asset utilization - Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
  * UI Testing - Application makes use of Espresso to test aspects of the UI.
  * Third-party libraries - Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with ContentProviders if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.
- **Homescreen Widget**
  * Application has a companion homescreen widget - Application has a companion homescreen widget.
  * Widget displays ingredient list for desired recipe - Widget displays ingredient list for desired recipe.
