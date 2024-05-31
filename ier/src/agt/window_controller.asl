!start.

+!start : true <- 
    .print("Window Controller started").

+weather(sunny)
    <- .print("Weather is sunny, opening window")
    ; !openWindow.

+weather(rainy)
    <- .print("Weather is rainy, closing window")
    ; !closeWindow.

+weather(windy)
    <- .print("Weather is windy, closing window")
    ; !closeWindow.

+weather(foggy)
    <- .print("Weather is calm, keeping window state")
    ; !closeWindow.

+weather(cloudy)
    <- .print("Weather is calm, keeping window state")
    ; !openWindow.

 +temperature(T)
     : T > 28
     <- .print("Temperature is above 28, opening window")
     ; !openWindow.

 +temperature(T)
     : T < 24
     <- .print("Temperature is below 24, closing window")
     ; !closeWindow.

+openWindow
    <- .send(environment, tell, openWindow(window)).

+closeWindow
    <- .send(environment, tell, closeWindow(window)).



+time(Time)
    : (Time >= 21 ; Time < 9)
    <- .print("Time is between 21:00 and 09:00, closing door")
    ; !closeDoor
    ; !closeWindow.

+time(Time)
    : (Time >= 9 & Time < 21)
    <- .print("Time is between 09:00 and 21:00, opening door")
    ; !openDoor
    ; !openWindow.

+openDoor
    <- .send(environment, tell, openDoor(door)).

+closeDoor
    <- .send(environment, tell, closeDoor(door)).






