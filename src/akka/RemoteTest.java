package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class RemoteTest {

	public static void main(String[] args) {
		ActorSystem sys=ActorSystem.create("mysys");
		ActorSelection as=sys.actorSelection("akka.tcp://sys/remote/akka.tcp/sys@127.0.0.1:2551/user/first");
		as.tell("ron said", ActorRef.noSender());
	}

}
