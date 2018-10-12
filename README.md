# jotun

<img src="jotun.jpg"
title="giants" align="left" padding="15px"/>
<small>
 <br><br>
A Clojure library designed to interact with **Anti-Captcha** services
online. The idea is to have an unique interface to deal with providers
of anti-captcha utilities.


The first to be implemented will be the
[Anti-Captcha](https://anti-captcha.com/mainpage) from Coliseo. I've
being using this service for some time and I was missing a library to
provide interface for them and other facilities.

*jotun* are Nordic mythological creates with great-strength that are
often against the gods. The role and characteristics of the *giants*
are often unclear and contradictory, from brutal beasts with childish
intellect to wise ancient creatures. Peak your side.

<br clear=all /><br>

**Tasks implemented/available:**

 - [x] ImageToText: solve usual image captcha
 - [ ] NoCaptcha: Google Recaptcha puzzle solving
 - [ ] FunCaptcha: rotating captcha funcaptcha.com
 - [ ] SquareNetText: select objects on image with an overlay grid
 - [ ] GeeTest: sliding captcha from geetest.com
 - [ ] CustomCaptcha: image captcha with custom form.
</small>


There are still many decisions related to public interfaces and
project structure to be developed.

## Critical

The Anti-Captcha service is **paid**, therefore you need to get an
account at their WebSite and charge it with a few dolars. It's the
cheapest solution I found in the market.

Please insert your **client key** inside the
`config/{dev,prod}/config.edn` file.


## Installation

#### Leiningen/Boot

``` shell
[jotun "0.1.0"]
```

#### Clojure CLI/deps.edn

``` shell
jotun {:mvn/version "0.1.0"}
```

#### Gradle

``` shell
compile 'jotun:jotun:0.1.0'
```

#### Maven

``` xml
<dependency>
  <groupId>jotun</groupId>
  <artifactId>jotun</artifactId>
  <version>0.1.0</version>
</dependency>
```


## Usage


## License

Copyright © 2018 Wanderson Ferreira

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
