# jotun
[![Build Status](https://travis-ci.com/wandersoncferreira/jotun.svg?branch=master)](https://travis-ci.com/wandersoncferreira/jotun)

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

Please provide you *client key* as a environment variable called `apikey`.
The package will manage to get the correct result and use it accordingly.


## Installation

#### Leiningen/Boot

``` shell
[jotun "0.2.0"]
```

#### Clojure CLI/deps.edn

``` shell
jotun {:mvn/version "0.2.0"}
```

#### Gradle

``` shell
compile 'jotun:jotun:0.2.0'
```

#### Maven

``` xml
<dependency>
  <groupId>jotun</groupId>
  <artifactId>jotun</artifactId>
  <version>0.2.0</version>
</dependency>
```


## Usage

``` clojure
(ns ex-jotun.core
  (:require [jotun.image-to-text.core :refer [solve]]
            [jotun.utils :as j-utils]))

(let [image-url "https://www.scienceabc.com/wp-content/uploads/2016/07/Captcha-ex.jpg"]
  (solve image-url))
;; => {:cost "0.000700", :ip "179.110.41.240", :url "http://209.212.146.169/570/153941121973340.jpg",
;;     :solution "smwm", :create-time 1539411219, :end-time 1539411225}

(j-utils/get-balance);; => 9.7952


(j-utils/get-queue-stats)
;; => {:waiting {:value 18, :message "Amount of idle workers online, waiting for a task"},
;;     :load {:value 98.42, :message "Queue load in percents"},
;;     :bid {:value 6.509939E-4, :message "Average task solution cost in USD"},
;;     :speed {:value 11.15, :message "Average task solution speed in seconds"},
;;     :total {:value 1138, :message "Total number of workers"}}

```

## License

Copyright © 2018 Wanderson Ferreira

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
