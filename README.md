# rn-video-watermark

## Installation
`npm install --save rn-video-watermark`

### Mostly automatic installation

`$ react-native link rn-video-watermark`

## iOS
After linking `cd ios` and `pod install`

## Usage

```javascript
import VideoWatermark from 'rn-video-watermark';

VideoWatermark.convert(videoUri, imgUri, watermarkPosition, destinationUri => {
    // use converted video here.
});
```
watermarkPosition is a string that you can use to define four possible positions for the watermark
`LEFT_TOP`, `LEFT_BOTTOM`, `RIGHT_TOP`, `RIGHT_BOTTOM` 
