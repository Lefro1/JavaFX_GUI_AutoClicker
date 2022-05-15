<div id="top"></div>
<!--
*** Definitely just stole this from
*** https://github.com/othneildrew/Best-README-Template/blob/master/README.md
-->

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

[![Image]](/Resources/JavaFX_GUI_AutoClickerImage.png)

I just wanted to make a somewhat simple auto clicker using JavaFX as the user interface.

<p align="right">(<a href="#top">back to top</a>)</p>


### Built With

This program uses JavaFX to render all UI elements and track interaction with the app.
JNativeHook is used as a keyboard listener in order to allow hotkeys to stop and start the auto clicker.

* [JavaFX](https://openjfx.io/)
* [JNativeHook](https://github.com/kwhat/jnativehook)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

Follow these instructions if you want to use this app.

### Prerequisites
**(This onely applies if not using the jar file)**

This application relies on [JNativeHook 2.1.0](https://github.com/kwhat/jnativehook) or newer.
 - The most recent version of JNativeHook can be found [here](https://github.com/kwhat/jnativehook/releases). 
   Download the relevant jar file.

### Installation

 To run this auto clicker, you can either compile the files yourself in a program such as IntelliJ, or you can just run the attached jar file.

Compiling Locally (through IntelliJ):
 * `git clone` the repo.
 * On IntelliJ, go file -> Project Structure -> Modules -> Dependencies
 * Directly beneath "Module SDK" select the + icon and find your JNativeHook.jar file.
 * Add the file to the dependency list, ensure the box "Export" is checked, apply and close.
 * You can now compile `src/GUI/RenderGUI`'s main method which will run the program.

Running the jar:
 * Download the .jar file for the project
 * Run the file.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->
## Usage

This is just meant to be a simple auto clicker that allows custom inputs for speed.

Use it however you see fit, feel free to open issues or submit requests.

<p align="right">(<a href="#top">back to top</a>)</p>
