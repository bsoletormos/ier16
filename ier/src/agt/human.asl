inBed(false).
atHome(true).

+time(Time) : Time < 12 <-
    !lightsOn(true).

+time(Time) : 12 <= Time <-
    !lightsOn(false).

+!lightsOn(State) : true <-
    .send(env, lights_on, State).