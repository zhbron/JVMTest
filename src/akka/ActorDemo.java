package akka;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ActorDemo {

	public static void main(String[] args) {
		Config cfg=ConfigFactory.load();
		ActorSystem sys=ActorSystem.create("mysys",cfg);
		ActorRef ref=sys.actorOf(Props.create(HelloActor.class),"first");
		//ref.tell("world", ActorRef.noSender());
		System.out.println(ref.path());
		//ActorSelection as=sys.actorSelection(ref.path());
		//as.tell("ron said", ActorRef.noSender());
		//ActorSelection as=sys.actorSelection("akka.tcp://sys/remote/akka.tcp/sys@127.0.0.1:2551/user/first");
		//as.tell("ron said", ActorRef.noSender());
	}

}

class HelloActor extends UntypedActor{

	@Override
	public void onReceive(Object obj) throws Exception {
		System.out.println("Hello,");
		//sender().tell("complete", self());
	}
	
}
