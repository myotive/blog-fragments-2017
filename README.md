Fragments: The Solution To (And Cause of) All Of Android's Problems
=====================================

Samples and links from my [blog post](https://realm.io/news/michael-yotive-state-of-fragments-2017/) regarding the state of Fragments in 2017.

![MVP Sample](https://raw.githubusercontent.com/myotive/blog-fragments-2017/master/screenshots/screen.gif)

# Building

To build, install and run a debug version, run this from the root of the project:

```
  ./gradlew assembleDebug
```

# Project
The sample code I have provided essentially doing the same thing in many different ways. This is a master -> detail example that displays a github user's repository and allows you to drill into the details.

All of the modules in this repository share code from the [`Common`](https://github.com/myotive/blog-fragments-2017/tree/master/common) module. This module has various components such as:
* [ApplicationComponent](https://github.com/myotive/blog-fragments-2017/blob/master/common/src/main/java/com/example/common/di/ApplicationComponent.java) - Base Dagger 2 application component which provides scoped instances of the GitHubApi and OkHttpClient.
* [BaseApplication](https://github.com/myotive/blog-fragments-2017/blob/master/common/src/main/java/com/example/common/BaseApplication.java) - Creates and stores the base Dagger 2 application component.
* [Adapters](https://github.com/myotive/blog-fragments-2017/tree/master/common/src/main/java/com/example/common/ui/adapters) - RecyclerView adapters that are shared amongst all the sample modules.  

Each project implements a `GITHUB_OWNER` key in their build.gradle file. This is the value passed into the GitHub api's call. [Here's an example](https://github.com/myotive/blog-fragments-2017/blob/master/fragment_sample_mvp/build.gradle#L16).

#### References
* [What the Fragment - Adam Powell](https://www.youtube.com/watch?v=k3IT-IJ0J98)
* [Square - Advocating Against Android Fragments](https://medium.com/square-corner-blog/advocating-against-android-fragments-81fd0b462c97#.oocb23bhn)
* [Square - Flow and Mortar](https://medium.com/square-corner-blog/simpler-android-apps-with-flow-and-mortar-5beafcd83761#.sp63y1ae7)
* [Google Samples - Android Architecture](https://github.com/googlesamples/android-architecture)

#### Blog Posts
* [Saying “No” to Fragments (and Activities)]( https://hackernoon.com/saying-no-to-fragments-and-activities-creating-view-driven-applications-with-flow-8f7d02315442)
* [Dianne Hackborn on Android Architecture](https://plus.google.com/+DianneHackborn/posts/FXCCYxepsDU)

#### Libraries
* [Simple-Stack](https://github.com/Zhuinden/simple-stack)
* [Conductor](https://github.com/bluelinelabs/Conductor)
* [Mosby](https://github.com/sockeqwe/mosby)
* [Flow](https://github.com/square/flow)
* [Mortar](https://github.com/square/mortar)
