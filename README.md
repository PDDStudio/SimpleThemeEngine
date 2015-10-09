# SimpleThemeEngine
A lightweight, fast and easy to use Android library for adding multiple themes and color schemes to your Android Application.
Change your application's look and feel during runtime with ease.

##Requirements
None. SimpleThemeEngine is a standalone library with support for **API Level 7 and higher.**

##Features
* Create pre-defined Themes and change between them during runtime
* Let your users theme your application as they want to.
* You decide what you want to be themed with the SimpleThemeEngine

##Limitations
This library does only serve themes and reacts on changes, in case you have dynamic themes or want to restore the last theme settings on your application's startup you have to do this on your own.
*For an example how to save and restore your themes have a look at the sample application*

##Importing

To use the SimpleThemeEngine you have to add it as dependency to your project.

####Gradle
`compile 'com.pddstudio:simplethemeengine:0.0.1'`

####Maven
```
<dependency>
    <groupId>com.pddstudio</groupId>
    <artifactId>simplethemeengine</artifactId>
    <version>0.0.1</version>
</dependency>
```
##Quickstart
**Note:** *For more detailed instructions and explanations have a look at the demo application or head over to the [wiki](https://github.com/PDDStudio/SimpleThemeEngine/wiki)*
####Initializing the Library
Add the ThemeEngine to your application's **startup** method.
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	//defaults
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	//initialize the SimpleThemeEngine
	ThemeEngine.initThemeEngine(this);
}
```

**Note:**
To prevent unwanted fc's you have to set a fallback color scheme after calling ```ThemeEngine.initThemeEngine(this);```. These colors should be hardcoded into your application's code or included in your ressources.
```java
ThemeEngine.setFallbackColors(fallbackPrimary, fallbackPrimaryDark, fallbackAccent, ApplicationTheme.THEME_LIGHT, ThemeFontColor.LIGHT_FONT_COLOR);
```
Register your application's ```IBackendNotificationInterface```, there you define what happens on several events.
*Head over to the demo application for an example, or take a look at the [wiki](https://github.com/PDDStudio/SimpleThemeEngine/wiki)*

Finally call
```java
ThemeEngine.loadSettingsThemeObject();
```

Done. The SimpleThemeEngine is now ready to use.

####Start Theming
After initializing the ThemeEngine, you can start customizing your application. Add an ```OnThemeChangedListener``` to the class you want to add theming support and register the interface at your ThemeEngine instance via ```ThemeEngine.registerThemeListener(identifier, OnThemeChangedListener);```
*Take a look at the demo application for an example or head over to the [wiki](https://github.com/PDDStudio/SimpleThemeEngine/wiki)*

##Showcase
####Video
Watch the demo video of the  SimpleThemeEngine implemented in an other project im working on:
[ThemeEngine Demo Video](https://www.youtube.com/watch?v=sU8DkjY57Jc) 
####Demo Application
[Download demo application](https://drive.google.com/open?id=0B1kHxIzwCehpVWxWbUVwX1lHdmM)
***or***
[Test it in your Browser with Appetize](https://appetize.io/app/9amf8jnqq3k3xhke2y56c15b1g)

##Bug Reports & Contributing
Feel free to report any bug or feature request you find/have using the [GitHub Issue Tracker](https://github.com/PDDStudio/SimpleThemeEngine/issues)
In case you want to contribute, feel free to create a pull request.

##Contact
Feel free to contact me on Google+ / Hangouts or via E-Mail
- Patrick J <patrick.pddstudio@gmail.com> 
- [Google+ Profile](https://plus.google.com/+PatrickJung42)

##License
    Copyright 2015 Patrick J

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.