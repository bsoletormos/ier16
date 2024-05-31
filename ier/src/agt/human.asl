inBed(false).
atHome(true).

+!at(human,P) : at(human,P) <- true.
+!at(human,P) : not at(human,P) <-
    move_towards(P);
    !at(human,P).

+time(T) : 6 <= T & T < 22 <-
    .println("Human: Nappal van.");
    !inBed(false).

+time(T) : T < 6 & 22 <= T <-
    .println("Human: Éjjel van.");
    !inBed(true).

+time(T) : 8 <= T & T <= 18 <-
    .println("Human: Munkaidő van.");
    !at(human, work).

+time(T) : 18 < T & T < 20 <-
    ?weather(Weather);
    !considerStrolling.

+!considerStrolling : weather("sunny") <-
    .print("Human: Szép idő van.");
    !at(human, outside).

 +!inBed(true) : inBed(true) <-
    true.
+!inBed(true) : inBed(false) <-
    !at(human, bed).
+!inBed(false) : inBed(false) <-
    true.
+!inBed(false) : inBed(true) <-
    !at(human, livingroom).