The Problem Reporter Exercise
=============================

This exercise concerns itself with application problem reporting.

Occasionally our application will generate an unhandled exception at runtime.
When this happens we want a formatted report sent by email to the application administrator.

The challenge with using TDD is that we don't want to send an email whenever we run a unit test.
To solve this problem we have to isolate our problem reporting code from the code that sends an email. 
