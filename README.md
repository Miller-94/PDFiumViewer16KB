# PDFiumViewer16KB

Android PDF viewer library with integrated PDFium bindings. Supports **16KB page alignment** for Android 15+ and **form field rendering** (checkboxes, text fields).

[![](https://jitpack.io/v/Miller-94/PDFiumViewer16KB.svg)](https://jitpack.io/#Miller-94/PDFiumViewer16KB)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

## Features

- PDF rendering using PDFium
- **16KB page alignment** - Android 15+ compatibility
- **Form field rendering** - Checkboxes, text fields, radio buttons
- Annotation rendering
- Double tap to zoom
- Swipe horizontal/vertical
- Scroll handle
- Night mode
- Multiple PDF sources (assets, files, URIs, byte arrays, streams)

## Installation

Add JitPack repository to your `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:

```gradle
dependencies {
    implementation 'com.github.Miller-94:PDFiumViewer16KB:1.0.1'
}
```

## Usage

### Layout

```xml
<com.github.barteksc.pdfviewer.PDFView
    android:id="@+id/pdfView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

### Load PDF

```java
pdfView.fromAsset("sample.pdf")
    .enableSwipe(true)
    .swipeHorizontal(false)
    .enableDoubletap(true)
    .defaultPage(0)
    .enableAnnotationRendering(true)
    .password(null)
    .scrollHandle(null)
    .enableAntialiasing(true)
    .spacing(0)
    .onLoad(nbPages -> {
        // PDF loaded
    })
    .onError(t -> {
        // Error loading PDF
    })
    .load();
```

### PDF Sources

```java
// From assets
pdfView.fromAsset("document.pdf")

// From file
pdfView.fromFile(new File("/path/to/document.pdf"))

// From URI
pdfView.fromUri(uri)

// From byte array
pdfView.fromBytes(pdfBytes)

// From InputStream
pdfView.fromStream(inputStream)
```

### Configuration Options

| Method | Description |
|--------|-------------|
| `pages(int...)` | Show only specific pages |
| `enableSwipe(boolean)` | Enable/disable swipe gesture |
| `swipeHorizontal(boolean)` | Horizontal or vertical swipe |
| `enableDoubletap(boolean)` | Enable double-tap zoom |
| `defaultPage(int)` | Initial page to display |
| `onLoad(OnLoadCompleteListener)` | Called when PDF is loaded |
| `onPageChange(OnPageChangeListener)` | Called on page change |
| `onError(OnErrorListener)` | Called on error |
| `onPageError(OnPageErrorListener)` | Called on page load error |
| `onRender(OnRenderListener)` | Called after first render |
| `enableAnnotationRendering(boolean)` | Render annotations and form fields |
| `password(String)` | PDF password |
| `scrollHandle(ScrollHandle)` | Set scroll handle |
| `enableAntialiasing(boolean)` | Enable antialiasing |
| `spacing(int)` | Spacing between pages in dp |
| `autoSpacing(boolean)` | Auto spacing based on width |
| `pageFitPolicy(FitPolicy)` | Page fit policy (WIDTH, HEIGHT, BOTH) |
| `fitEachPage(boolean)` | Fit each page to view |
| `pageSnap(boolean)` | Snap to page when scrolling |
| `pageFling(boolean)` | Fling to page |
| `nightMode(boolean)` | Invert colors for night mode |

### Scroll Handle

```java
pdfView.fromAsset("document.pdf")
    .scrollHandle(new DefaultScrollHandle(this))
    .load();
```

### Page Navigation

```java
// Jump to page
pdfView.jumpTo(pageIndex);
pdfView.jumpTo(pageIndex, withAnimation);

// Get current page
int currentPage = pdfView.getCurrentPage();

// Get page count
int pageCount = pdfView.getPageCount();
```

### Zoom

```java
// Get/set zoom
float zoom = pdfView.getZoom();
pdfView.zoomTo(2.0f);
pdfView.zoomWithAnimation(2.0f);
pdfView.resetZoom();
pdfView.resetZoomWithAnimation();

// Min/max zoom
pdfView.setMinZoom(1.0f);
pdfView.setMaxZoom(5.0f);
pdfView.setMidZoom(2.5f);
```

## ProGuard

Add these rules to your `proguard-rules.pro`:

```proguard
-keep class com.shockwave.pdfium.** { *; }
-keep class com.github.barteksc.pdfviewer.** { *; }
```

## Requirements

- **minSdk**: 21
- **targetSdk**: 35
- **Java**: 17

## Technical Details

### 16KB Page Alignment

All native libraries are built with 16KB page alignment for Android 15+ compatibility:

```cmake
-Wl,-z,max-page-size=16384
```

### Form Field Rendering

Uses `FPDF_FFLDraw()` for rendering interactive form fields:
- Checkboxes
- Radio buttons
- Text fields
- Combo boxes

### Native Libraries

| Library | Description |
|---------|-------------|
| `libjniPdfium.so` | JNI bindings |
| `libmodpdfium.so` | PDFium core |
| `libmodft2.so` | FreeType2 |
| `libmodpng.so` | libpng |

## License

```
Copyright 2017 barteksc

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Credits

- [barteksc/AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewer) - Original project
- [mhiew/AndroidPdfViewer](https://github.com/mhiew/AndroidPdfViewer) - AndroidX migration
- [oothp/AndroidPdfViewer](https://github.com/oothp/AndroidPdfViewer) - 16KB alignment support
- [PDFium](https://pdfium.googlesource.com/pdfium/) - PDF rendering engine
