# Next GIF 
The application allows you to look at random gifs telling about developers life.
### Architecture
  * MVVM
  * Kotlin, Coroutines
### Preview
<img src="https://github.com/dimaklekchyan/next-gif/blob/main/1.jpg" width="220" height="480" />    <img src="https://github.com/dimaklekchyan/next-gif/blob/main/2.jpg" width="220" height="480" />    <img src="https://github.com/dimaklekchyan/next-gif/blob/main/3.jpg" width="220" height="480" />  <img src="https://github.com/dimaklekchyan/next-gif/blob/main/4.jpg" width="220" height="480" /> 
1. Successfully loaded and cached gif.
2. Gif loading state.
3. You will see it when content won't be downloaded and cached completely and a connection will be lost. But the gif wil be added to queue.
4. The connection is lost. You can't to load new gif.

### Libraries
  * #### Architecture Component: LiveData, ViewModel.
  * #### Retrofit2. Gson
  * #### Dagger2
  * #### Glide
  * #### Timber
