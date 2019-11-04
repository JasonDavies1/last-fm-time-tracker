# Last FM Time Tracker 

I use Last.fm - [a lot](https://www.last.fm/user/JasonDavies_)

But, in my 6 years of scrobbling everything, I realised pretty quickly that some statistics are inaccurate. I mean, sure
at the time of writing I have listened to Taylor Swift a grand total of 930 times, but just **how long** is that? Have I
listened to more post-rock than Swifty? I need to know. 

There used to be a handy tool online called Lasttimer that would analyse your library and then report back on the length
of time that you'd spent listening to artists. It also had the functionality to export a handy barchart with your 
timings on it too. In short, I had a lot of time for this app - ba dum tss. But it has 2 flaws. 

The first of which is that from what I gather it only made estimations per artist rather than giving an actual time 
figure for each. They always seemed accurate enough so I never let this bother me. The second of which is that Lasttimer
is now gone. Last.FM users have been looking for time-based metrics for a long time, myself included. 

*So I'm going to rebuild it* 

# Disclaimer 

Every time I try to complete this project, something gets in the way. After finding a bit of useful information from the 
Last.FM developer API (Notably that the Album.getInfo API method returns track length) I'm keen to revisit and finally 
put an end to my time-tracking worries. 

# Anticipated Challenge

I've listened to a few artists that use UNICODE in their names. It's going to be interesting to see how that transfers
when making RESTful requests to the API for album information. Either way, it'll be fun, and by all accounts, I'd rather 
know how many days (literal days!) I've spent listening to The National than some strange vaporwave album.  
