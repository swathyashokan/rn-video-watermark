# react-native-video-watermarker

### forked/cloned from https://github.com/sagark1510/react-native-video-watermark#1.0.2

## Installation
`npm install --save react-native-video-watermarker`

### Mostly automatic installation

`$ react-native link react-native-video-watermarker`

## iOS
After linking `cd ios` and `pod install`

## Usage

```javascript
import VideoWatermark from 'react-native-video-watermark';

VideoWatermark.convert(videoUri, imgUri, watermarkPosition, destinationUri => {
    // use converted video here.
});
```
watermarkPosition is a string that you can use to define four possible positions for the watermark
`LEFT_TOP`, `LEFT_BOTTOM`, `RIGHT_TOP`, `RIGHT_BOTTOM` 
