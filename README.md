# lomo_vidaud_player
For Lomotif Android Test

# General Requirements

* Follow the Material Design guidelines when designing your app. Use ConstraintLayout as your root view.
* You may use any 3rd party libraries to help you complete the tasks.
* Support only Portrait orientation.
* Create a private repository (Github, Bitbucket, etc.) for each tasks. Commit often and add meaningful message.

# Code Tasks

## Video/Audio Player

1. UI
* Load all the Video and Audio from the user's device then display it in a list.
* Display the thumbnail or artwork of the media and its name (use song title / artist in metadata if available)
* Add an icon that shows if media is a Video or an Audio

2. Media Player
* Use ExoPlayer for playing the audio and video file. [https://developer.android.com/guide/topics/media/exoplayer]
* Play the selected media in a new Fragment/Dialog. When user exit/dismiss the Fragment/Dialog, the media player should also be stopped.
* Add playback controls (pause, play, stop, etc.)
