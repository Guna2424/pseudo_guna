// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies{
        classpath("com.android.tools.build:gradle:8.3.2")
        classpath("com.google.gms:google-services:4.4.1")
    }
}


plugins {
    id("com.google.gms.google-services") version "4.4.1" apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}